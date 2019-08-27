package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.FollowRequest;
import com.woowacourse.dsgram.service.dto.UserInfo;
import com.woowacourse.dsgram.service.facade.Facade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedApiController {
    private final Facade facade;

    public FeedApiController(Facade facade) {
        this.facade = facade;
    }

    @PostMapping("/follow")
    public ResponseEntity follow(@RequestBody FollowRequest followRequest) {
        facade.follow(followRequest.getFromNickName(), followRequest.getToNickName());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/followers/{nickName}")
    public ResponseEntity<List<UserInfo>> getFollowers(@PathVariable String nickName) {
        List<UserInfo> followers = facade.getFollowers(nickName);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings/{nickName}")
    public ResponseEntity<List<UserInfo>> getFollowings(@PathVariable String nickName) {
        List<UserInfo> followers = facade.getFollowings(nickName);
        return ResponseEntity.ok(followers);
    }
}
