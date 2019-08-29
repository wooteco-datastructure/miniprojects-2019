package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Follow;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.FollowRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.FollowRelation;
import com.woowacourse.dsgram.service.dto.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public FollowRelation isFollowed(User guest, User feedOwner) {
        Follow follow = getFollow(guest, feedOwner);
        return FollowRelation.getRelation(follow, guest, feedOwner);
    }

    public List<UserInfo> findFollowers(User user) {
        List<UserInfo> followers = followRepository.findAllByTo(user)
                .stream().map(Follow::getFrom)
                .map(UserAssembler::toUserInfo)
                .collect(Collectors.toList());

        return followers;
    }

    public List<UserInfo> findFollowings(User user) {
        List<UserInfo> followings = followRepository.findAllByFrom(user)
                .stream().map(Follow::getTo)
                .map(UserAssembler::toUserInfo)
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
}
