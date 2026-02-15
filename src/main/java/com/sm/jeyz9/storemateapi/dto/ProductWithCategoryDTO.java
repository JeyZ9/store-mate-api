package com.sm.jeyz9.storemateapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductWithCategoryDTO {
    private List<ProductDTO> promotion;
    private List<ProductDTO> soap;
    private List<ProductDTO> drinks;
    private List<ProductDTO> shampoo;
}
