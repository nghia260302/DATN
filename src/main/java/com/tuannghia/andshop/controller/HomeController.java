package com.tuannghia.andshop.controller;

import com.tuannghia.andshop.controller.common.BaseController;
import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.service.ClotheService;
import com.tuannghia.andshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@AllArgsConstructor
@Controller

public class HomeController extends BaseController {

    private ClotheService clotheService;
    private CategoryService categoryService;

    @GetMapping("/")
    String getUserHomePage(Model model) {

        List<Clothe> top4BestSeller = clotheService.getTop4BestSeller();
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("top4BestSeller", top4BestSeller);
        List<Clothe> newProducts = clotheService.findAllOrderByCreatedDate();
        model.addAttribute("newProducts", newProducts);
        return "user/index";
    }


}
