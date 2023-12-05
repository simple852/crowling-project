package com.teamproject.computerproject.dto.request;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    String title; // 가격 변동 되었습니다
    String body; // 상품명
    String itemUrl; // url
}
