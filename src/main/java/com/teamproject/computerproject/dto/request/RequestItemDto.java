package com.teamproject.computerproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.teamproject.computerproject.domain.Item}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestItemDto implements Serializable {
    Integer categoryId;
    String itemAddress;
}