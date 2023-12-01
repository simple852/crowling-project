package com.teamproject.computerproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDto {
    private String titleClass;
    private String contentClass;
    private String priceClass;
    private Integer categoryId;
}
