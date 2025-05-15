package com.tuannghia.andshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClotheSearchDTO {
    private Long categoryId;
    private String keyword;

}

