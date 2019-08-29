package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.FollowService;
import com.woowacourse.dsgram.service.dto.FollowInfo;
import com.woowacourse.dsgram.service.dto.FollowRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedApiController {
    private final FollowService followService;

    public FeedApiController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public ResponseEntity follow(@RequestBody FollowRequest followRequest) {
        followService.follow(followRequest.getFromNickName(), followRequest.getToNickName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/{nickName}")
    public ResponseEntity<List<FollowInfo>> getFollowers(@PathVariable String nickName) {
        List<FollowInfo> followers = followService.getFollowers(nickName);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings/{nickName}")
    public ResponseEntity<List<FollowInfo>> getFollowings(@PathVariable String nickName) {
        List<FollowInfo> followers = followService.getFollowings(nickName);
        return ResponseEntity.ok(followers);
    }
}
