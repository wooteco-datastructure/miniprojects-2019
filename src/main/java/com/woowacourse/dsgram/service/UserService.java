package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.UserRepository;
import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.oauth.OAuthUserInfoResponse;
import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import com.woowacourse.dsgram.service.dto.user.UserDto;
import com.woowacourse.dsgram.service.exception.DuplicatedAttributeException;
import com.woowacourse.dsgram.service.exception.NotFoundUserException;
import com.woowacourse.dsgram.service.oauth.GithubClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GithubClient githubClient;

    public UserService(UserRepository userRepository, GithubClient githubClient) {
        this.userRepository = userRepository;
        this.githubClient = githubClient;
    }

    public void save(SignUpUserRequest signUpUserRequest) {
        checkDuplicatedAttributes(signUpUserRequest.getNickName(), signUpUserRequest.getEmail());
        userRepository.save(UserAssembler.toEntity(signUpUserRequest));
    }

    private void checkDuplicatedAttributes(String nickName, String email) {
        if (userRepository.existsByNickName(nickName)) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedAttributeException("이미 사용중인 이메일입니다.");
        }
    }

    public User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
    }

    public UserDto findUserInfoById(long userId, LoggedInUser loggedInUser) {
        User user = findById(userId);
        user.checkEmail(loggedInUser.getEmail());
        return UserAssembler.toDto(findById(userId));
    }

    private User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);
    }

    @Transactional
    public LoggedInUser update(long userId, UserDto userDto, LoggedInUser loggedInUser) {
        User user = findById(userId);
        checkDuplicatedNickName(userDto, user);
        user.update(UserAssembler.toEntity(userDto), loggedInUser.getEmail());
        return UserAssembler.toLoginUser(user);
    }

    private void checkDuplicatedNickName(UserDto userDto, User user) {
        if (!user.equalsNickName(userDto.getNickName()) &&
                userRepository.existsByNickName(userDto.getNickName())) {
            throw new DuplicatedAttributeException("이미 사용중인 닉네임입니다.");
        }
    }

    public LoggedInUser login(AuthUserRequest authUserRequest) {
        User user = findByEmail(authUserRequest.getEmail())
                .orElseThrow(() -> new InvalidUserException("회원정보가 일치하지 않습니다."));
        user.checkPassword(authUserRequest.getPassword());
        return UserAssembler.toLoginUser(user);
    }

    private Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public LoggedInUser oauth(String code) {
        String accessToken = githubClient.getToken(code);
        String email = githubClient.getUserEmail(accessToken);

        Optional<User> optionalUser = findByEmail(email);
        if (optionalUser.isPresent()) {
            optionalUser.ifPresent(User::changeToOAuthUser);
            return UserAssembler.toLoginUser(optionalUser.get());
        }
        return UserAssembler.toLoginUser(saveOauthUser(accessToken, email));
    }

    private User saveOauthUser(String accessToken, String email) {
        OAuthUserInfoResponse userInfo = githubClient.getUserInformation(accessToken);
        return userRepository.save(UserAssembler.toEntity(email, userInfo));
    }

    public void deleteById(long id, LoggedInUser loggedInUser) {
        // TODO: 2019-08-20 OAUTH revoke?
        User user = findByEmail(loggedInUser.getEmail())
                .orElseThrow(NotFoundUserException::new);
        if (user.isNotSameId(id)) {
            throw new InvalidUserException("회원정보가 일치하지 않습니다.");
        }
        userRepository.deleteById(id);
    }
}
