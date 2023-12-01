package com.teamproject.computerproject.controller;


import com.teamproject.computerproject.domain.User;
import com.teamproject.computerproject.dto.UserDto;
import com.teamproject.computerproject.dto.request.FCMNotificationRequestDto;
import com.teamproject.computerproject.service.FCMNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class FCMNotificationApiController {

    private final FCMNotificationService fcmNotificationService;


    @PostMapping("/check")
    public String sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }

//
//    @GetMapping("/users")
//    public ResponseEntity<Object> getUsers() {
////        List<User> list = fcmNotificationService.getUsers();
////        return ResponseEntity.ok().body(list);
//    }
}
