package com.tuannghia.andshop.controller.rest.impl;

import com.tuannghia.andshop.controller.rest.IClotheResource;
import com.tuannghia.andshop.controller.rest.base.RestApiV1;
import com.tuannghia.andshop.controller.rest.base.VsResponseUtil;
import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.service.ClotheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClotheResourceImpl implements IClotheResource {

    ClotheService clotheService;

    @Override
    public ResponseEntity<?> getBookById(Long clotheId) {
        return VsResponseUtil.ok(HttpStatus.OK, clotheService.getBookById(clotheId));
    }

    @Override
    public ResponseEntity<?> getclotheListPaginatedAndSorted(String sortBy, Long categoryId, int page, int size) {
        List<Clothe> clotheList;
        if(categoryId != null){
            clotheList = clotheService.getAllBooksByCategoryId(categoryId);
        }
        else {
            clotheList = clotheService.findAll();
        }

        List<Clothe> sortedClotheList;

        switch (sortBy){
            case "price-low-to-high":
                sortedClotheList = clotheList.stream()
                        .sorted(Comparator.comparing(Clothe::getSalePrice))
                        .collect(Collectors.toList());
            case "price-high-to-low":
                sortedClotheList = clotheList.stream()
                        .sorted(Comparator.comparing(Clothe::getSalePrice).reversed())
                        .collect(Collectors.toList());
            case "newest":
                sortedClotheList = clotheList.stream()
                        .sorted(Comparator.comparing(Clothe::getPublishedDate))
                        .collect(Collectors.toList());
            case "oldest":
                sortedClotheList = clotheList.stream()
                        .sorted(Comparator.comparing(Clothe::getPublishedDate).reversed())
                        .collect(Collectors.toList());
            default:
                sortedClotheList = clotheList;
        }
        return VsResponseUtil.ok(HttpStatus.OK, sortedClotheList);
    }
}
