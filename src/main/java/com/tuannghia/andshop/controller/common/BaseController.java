package com.tuannghia.andshop.controller.common;

import com.tuannghia.andshop.dto.CartDTO;
import com.tuannghia.andshop.entity.User;
import com.tuannghia.andshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public abstract class BaseController {

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartItemCount")
    public int cartItemCount(HttpSession session) {
        CartDTO cart = cartService.getCart(session);
        return cart.getCartItems().size();
    }

    @ModelAttribute("currentUser") // Add this method to provide current user to the view
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

}