package com.teamproject.computerproject.controller;


import com.teamproject.computerproject.dto.request.FCMNotificationRequestDto;
import com.teamproject.computerproject.service.FCMNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class FCMNotificationApiController {

    private final FCMNotificationService fcmNotificationService;


    @PostMapping("/check")
    public String sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }
}
