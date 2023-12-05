package com.teamproject.computerproject.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.teamproject.computerproject.domain.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    Integer id;
    String memberId;
    String memberPw;
    String snsToken;
}