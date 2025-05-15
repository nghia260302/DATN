package com.tuannghia.andshop.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@Tag(name = "Get revenue controller", description = "Operations related to clothe's management")
public interface IClotheResource {

    @Operation(summary = "Get clothe by id", description = "Get clothe's information based on the given id in path variable")
    @GetMapping("/books/get/{clotheId}")
    ResponseEntity<?> getBookById(@PathVariable("clotheId") Long clotheId);

    @Operation(summary = "Get paginated clothe list by  category id and sorting key", description = "Get paginated clothe list's information by  category id and sorting key")
    @GetMapping("/books/get")
    ResponseEntity<?> getclotheListPaginatedAndSorted(@RequestParam("sortBy") String sortBy,
                                                    @RequestParam("categoryId") Long categoryId,
                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                    @RequestParam("size") int size);

}
