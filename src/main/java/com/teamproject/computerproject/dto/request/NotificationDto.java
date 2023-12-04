package com.teamproject.computerproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    String title;
    String body;
    String itemUrl;
    String userId;
    String type;
    String token;
}
