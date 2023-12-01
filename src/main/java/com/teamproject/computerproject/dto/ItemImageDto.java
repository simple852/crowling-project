package com.teamproject.computerproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.teamproject.computerproject.domain.ItemImage}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemImageDto implements Serializable {
    Integer itemId;
    String imageAddress;
}