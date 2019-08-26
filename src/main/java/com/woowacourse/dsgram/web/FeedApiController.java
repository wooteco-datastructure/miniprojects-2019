package com.woowacourse.dsgram.web;

import com.woowacourse.dsgram.service.dto.FollowRequest;
import com.woowacourse.dsgram.service.facade.Facade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
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
}
