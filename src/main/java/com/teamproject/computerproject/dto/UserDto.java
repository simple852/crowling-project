package com.teamproject.computerproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.teamproject.computerproject.domain.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    Integer id;
    String memberId;
    String memberPw;
    String snsToken;
}