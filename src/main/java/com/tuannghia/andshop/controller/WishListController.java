package com.tuannghia.andshop.controller;

import com.tuannghia.andshop.controller.common.BaseController;
import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.entity.User;
import com.tuannghia.andshop.service.ClotheService;
import com.tuannghia.andshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/wishlist")
public class WishListController extends BaseController {

    private final UserService userService;
    private final ClotheService clotheService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addToWishList(@RequestParam Long clotheId) {
        User currentUser = getCurrentUser();
        Clothe clothe = clotheService.getBookById(clotheId);

        if (clothe != null) {
            userService.addBookToUser(currentUser.getId(), clotheId);
            return ResponseEntity.ok("ok");
        }

        return ResponseEntity.badRequest().body("Clothe not found");
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<String> removeFromWishList(@RequestParam Long clotheId) {
        User currentUser = getCurrentUser();
        Clothe clothe = clotheService.getBookById(clotheId);

        if (clothe != null) {
            userService.removeBookFromUser(currentUser.getId(), clotheId);
            return ResponseEntity.ok("ok");
        }

        return ResponseEntity.badRequest().body("Clothe not found");
    }

    @GetMapping
    public String getWishList(Model model) {
        Set<Clothe> favoritesList = clotheService.getfavoriteClothesByUserId(getCurrentUser().getId());
        model.addAttribute("favoritesList", favoritesList);
        return "user/wishlist";
    }


}
