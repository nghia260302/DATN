package com.tuannghia.andshop.controller.rest.impl;

import com.tuannghia.andshop.controller.rest.IGetRevenueController;
import com.tuannghia.andshop.controller.rest.base.RestApiV1;
import com.tuannghia.andshop.controller.rest.base.VsResponseUtil;
import com.tuannghia.andshop.service.ClotheService;
import com.tuannghia.andshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestApiV1
@AllArgsConstructor
public class GetRevenueControllerImpl implements IGetRevenueController {

    private ClotheService clotheService;
    private CategoryService categoryService;

    @Override
    public ResponseEntity<?> getProductRevenueByMonth(@PathVariable("selectedMonth") int selectedMonth) throws UnsupportedEncodingException {
        return VsResponseUtil.ok(HttpStatus.OK, clotheService.getTop10BestSellerByMonth(selectedMonth));
    }

    @Override
    public ResponseEntity<?> getMonthRevenueByYear(@PathVariable("selectedYear") int selectedYear) throws UnsupportedEncodingException {
        return VsResponseUtil.ok(HttpStatus.OK, clotheService.getMonthRevenuePerYear(selectedYear));
    }

}
