package com.tuannghia.andshop.controller;

import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.service.ClotheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClotheController {

    ClotheService clotheService;

    @GetMapping("/sort-clothes")
    public ModelAndView sortBooks(@RequestParam("sortBy") String sortBy,
                                  @RequestParam("categoryId") Long categoryId,
                                  Model model){
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
        model.addAttribute("clotheList", sortedClotheList);
        return new ModelAndView("fragments/clotheListFragment :: clotheList", model.asMap());
    }

}
