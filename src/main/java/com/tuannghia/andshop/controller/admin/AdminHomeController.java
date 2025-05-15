package com.tuannghia.andshop.controller.admin;

import com.tuannghia.andshop.controller.common.BaseController;
import com.tuannghia.andshop.dto.ClotheDto;
import com.tuannghia.andshop.dto.CategoryDto;
import com.tuannghia.andshop.dto.OrderDTO;
import com.tuannghia.andshop.entity.Order;
import com.tuannghia.andshop.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminHomeController extends BaseController {
    private OrderService orderService;
    private UserService userService;
    private ClotheService clotheService;
    private CategoryService categoryService;
    private ExportService exportService;

    @GetMapping
    public String getAdminHomePage(Model model) {
        List<Order> orders = orderService.getTop10orders();
        model.addAttribute("orders", orders);
        BigDecimal totalRevenue = orderService.getTotalRevenue();

        Long numberOfUsers = userService.countUser();
        Long numberOfBooks = clotheService.countBook();
        Long numberOfOrders = orderService.countOrder();


        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("numberOfBooks", numberOfBooks);
        model.addAttribute("numberOfOrders", numberOfOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        return "admin/index";
    }

    @ResponseBody
    @GetMapping("/export/order/{keyword}")
	public ResponseEntity<?> generateOrderReport(@PathVariable String keyword) throws FileNotFoundException {
        List<Order> orders = orderService.getTop10orders();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order item : orders){
            OrderDTO orderDTO = new OrderDTO(item.getReciever(), item.getPhoneNumber(), item.getEmailAddress(), item.getCreatedAt().toString(), item.getTotalPrice(), item.getStatus(), item.getPaymentMethod());
            orderDTOList.add(orderDTO);
        }
        return ResponseEntity.ok().body(exportService.exportOrderReport(this.getCurrentUser(), orderDTOList, keyword));
	}

    @ResponseBody
    @GetMapping("/export/category/{selectedMonth}/{keyword}")
    public ResponseEntity<?> generateCategoryReport(@PathVariable int selectedMonth, @PathVariable String keyword) throws FileNotFoundException {
        List<CategoryDto> categoryDtoList = categoryService.getTop10BestSellerByMonth(selectedMonth);
        return ResponseEntity.ok().body(exportService.exportCategoryReport(this.getCurrentUser(),categoryDtoList, keyword));
    }

    @ResponseBody
    @GetMapping("/export/clothe/{selectedMonth}/{keyword}")
    public ResponseEntity<?> generateBookReport(@PathVariable int selectedMonth, @PathVariable String keyword) throws FileNotFoundException {
        List<ClotheDto> clotheDtoList = clotheService.getTop10BestSellerByMonth(selectedMonth);
        return ResponseEntity.ok().body(exportService.exportBookReport(this.getCurrentUser(), clotheDtoList, keyword));
    }
}
