package com.teamproject.computerproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.teamproject.computerproject.domain.Item}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {
    Integer id;
    Long totalItemCount;
    String itemName;
    Integer itemPrice;
    String itemContent;
    String itemAddress;
    String itemImage;

    Integer itemGap;
//    List<ItemImageDto> imageList;
}