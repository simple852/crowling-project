package com.teamproject.computerproject.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private Integer memberIndex;
    private String title;
    private String body;
    // private String image;
    // private Map<String, String> data;

    @Builder
    public FCMNotificationRequestDto(Integer memberIndex, String title, String body) {
        this.memberIndex = memberIndex;
        this.title = title;
        this.body = body;
        // this.image = image;
        // this.data = data;
    }
}
