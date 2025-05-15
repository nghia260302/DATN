package com.tuannghia.andshop.dto;

import com.tuannghia.andshop.entity.Clothe;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Clothe}
 */
@Data
@Value
public class ClotheDto implements Serializable {
    String title;
    Double totalRevenue;

}