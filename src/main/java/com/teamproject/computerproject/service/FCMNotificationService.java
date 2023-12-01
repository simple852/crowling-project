package com.teamproject.computerproject.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.teamproject.computerproject.domain.User;
import com.teamproject.computerproject.dto.UserDto;
import com.teamproject.computerproject.dto.request.FCMNotificationRequestDto;
import com.teamproject.computerproject.repositery.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
@Slf4j
public class FCMNotificationService {
    private final ModelMapper modelMapper;

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository usersRepository;

    public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {

        UserDto user = modelMapper.map(usersRepository.getReferenceById(requestDto.getMemberIndex()), UserDto.class);

        if (!user.getMemberId().isEmpty()) {
            if (user.getSnsToken() != null) {
                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody())
                        // .setImage(requestDto.getImage())
                        .build();

                Message message = Message.builder()
                        .setToken(user.getSnsToken())
                        .setNotification(notification)
                        // .putAllData(requestDto.getData())
                        .build();

                try {
                    firebaseMessaging.send(message);
                    return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getMemberIndex();
                } catch (FirebaseMessagingException e) {
                    e.printStackTrace();
                    return "알림 보내기를 실패하였습니다. targetUserId=" + requestDto.getMemberIndex();
                }
            } else {
                return "서버에 저장된 해당 유저의 FirebaseToken이 존재하지 않습니다. targetUserId=" + requestDto.getMemberIndex();
            }

        } else {
            return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getMemberIndex();
        }


    }

//    public List<User> getUsers() throws ExecutionException, InterruptedException {
//        return userDao.getUsers();
//    }
}
