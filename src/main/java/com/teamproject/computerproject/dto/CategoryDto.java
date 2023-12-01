package com.teamproject.computerproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.teamproject.computerproject.domain.Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {
    Integer id;
    String categoryName;
    Timestamp updateTime;
    List<ItemDto> itemDtoList;
}