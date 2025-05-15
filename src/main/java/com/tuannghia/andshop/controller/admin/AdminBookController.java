package com.tuannghia.andshop.controller.admin;

import com.tuannghia.andshop.controller.common.BaseController;
import com.tuannghia.andshop.dto.ClotheSearchDTO;
import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.entity.Category;
import com.tuannghia.andshop.service.ClotheService;
import com.tuannghia.andshop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/books_management")
public class AdminBookController extends BaseController {

    private final ClotheService clotheService;
    private final CategoryService categoryService;


    @GetMapping
    public String showBooksPage(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "4") int size,
                                @ModelAttribute("search") ClotheSearchDTO search) {
        Page<Clothe> bookPage = clotheService.searchBooks(search, PageRequest.of(page - 1, size));
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());

        return "admin/books";
    }


    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("clothe", new Clothe());
        return "admin/books_add_or_update";
    }

    @PostMapping("/add")
    public String addOrUpdateBook(@ModelAttribute("clothe") @Valid Clothe clothe,
                                  BindingResult bindingResult,
                                  @RequestParam("cover_image") MultipartFile coverImage,
                                  Model model
            , RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("error", "Đã có lỗi xảy ra vui lòng nhập lại");
            return "admin/books_add_or_update";
        }


        if (clothe.getId() != null) {
            // Check if there is an existing clothe with the given ID
            Clothe existingClothe = clotheService.getBookById(clothe.getId());
            if (existingClothe != null) {
                // Update the clothe with new data
                if (clothe.getPublishedDate() == null) {
                    clothe.setPublishedDate(existingClothe.getPublishedDate());
                }
                if (coverImage.isEmpty()) {
                    clothe.setCoverImage(existingClothe.getCoverImage());
                }

                clotheService.editBook(clothe, coverImage);
                Clothe editedClothe = clotheService.getBookById(clothe.getId());
                model.addAttribute("clothe", editedClothe);
                redirectAttributes.addFlashAttribute("message", "Sửa thông tin sách thành công!");
            }
        } else {
            Clothe exist = clotheService.getBookByName(clothe.getTitle());

            if (exist != null) {
                model.addAttribute("error", "Đã tồn tại sách với tên này");
                return "admin/books_add_or_update";
            } else clotheService.addBook(clothe, coverImage);
            redirectAttributes.addFlashAttribute("message", "Thêm sách thành công!");
        }

        return "redirect:/admin/books_management/add";
    }


    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Clothe clothe = clotheService.getBookById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("clothe", clothe);
        model.addAttribute("categories", categories);

        return "admin/books_add_or_update";
    }


    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean activeFlag, RedirectAttributes redirectAttributes) {
        try{
            clotheService.setActiveFlag(id, activeFlag);
            // Add a success message to the model
            String action = activeFlag ? "kích hoạt lại trạng thái đang bán cho" : "cập nhật trạng thái không bán cho";
            redirectAttributes.addFlashAttribute("message", action + " sách thành công!");

            return "redirect:/admin/books_management";
        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/books_management";
        }


    }


}
