package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.FollowRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.follow.FollowInfo;
import com.woowacourse.dsgram.service.dto.follow.FollowRelation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public FollowRelation isFollowed(User guest, User feedOwner) {
        Follow follow = getFollow(guest, feedOwner);
        return FollowRelation.getRelation(follow, guest, feedOwner);
    }

    public List<FollowInfo> findFollowers(User user) {
        List<FollowInfo> followers = followRepository.findAllByTo(user)
                .stream().map(Follow::getFrom)
                .map(UserAssembler::toFollowInfo)
                .collect(Collectors.toList());

        return followers;
    }

    public List<FollowInfo> findFollowings(User user) {
        List<FollowInfo> followings = followRepository.findAllByFrom(user)
                .stream().map(Follow::getTo)
                .map(UserAssembler::toFollowInfo)
                .collect(Collectors.toList());

        return followings;
    }

    public long getCountOfFollowers(User user) {
        return followRepository.countByTo(user);
    }

    public long getCountOfFollowings(User user) {
        return followRepository.countByFrom(user);
    }

    private Follow getFollow(User guest, User feedOwner) {
        return followRepository.findByFromAndTo(guest, feedOwner);
    }

    @Transactional
    public Follow save(User guest, User feedOwner) {
        return followRepository.save(new Follow(guest, feedOwner));
    }

    @Transactional
    public void delete(User guest, User feedOwner) {
        Follow follow = followRepository.findByFromAndTo(guest, feedOwner);
        followRepository.delete(follow);
    }

    public boolean existRelation(User guest, User feedOwner) {
        return followRepository.existsByFromAndTo(guest, feedOwner);
    }

    public void follow(String fromNickName, String toNickName) {
        User guest = userService.findByNickName(fromNickName);
        User feedOwner = userService.findByNickName(toNickName);

        if (!existRelation(guest, feedOwner)) {
            save(guest, feedOwner);
            return;
        }
        delete(guest, feedOwner);
    }

    public List<FollowInfo> getFollowers(String nickName) {
        User user = userService.findByNickName(nickName);

        return findFollowers(user);
    }

    public List<FollowInfo> getFollowings(String nickName) {
        User user = userService.findByNickName(nickName);

        return findFollowings(user);
    }
}
