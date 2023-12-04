package com.teamproject.computerproject.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.teamproject.computerproject.domain.BackupDatum}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupDatumDto implements Serializable {
//    Integer id;


    Integer id;
    @Size(max = 255)
    String itemName;
    Integer itemPrice;
    String itemContent;
    Integer categoryId;
    @Size(max = 255)
    String itemAddress;
    @Size(max = 255)
    String itemImage;
}