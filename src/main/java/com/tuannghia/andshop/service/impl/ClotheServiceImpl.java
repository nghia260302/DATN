package com.tuannghia.andshop.service.impl;

import com.tuannghia.andshop.constant.SortType;
import com.tuannghia.andshop.dto.ClotheDto;
import com.tuannghia.andshop.dto.ClotheSearchDTO;
import com.tuannghia.andshop.dto.MonthlyRevenueDTO;
import com.tuannghia.andshop.dto.UserSearchDTO;
import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.entity.Category;
import com.tuannghia.andshop.entity.OrderDetail;
import com.tuannghia.andshop.entity.User;
import com.tuannghia.andshop.repository.ClotheRepository;
import com.tuannghia.andshop.repository.CategoryRepository;
import com.tuannghia.andshop.repository.OrderDetailRepository;
import com.tuannghia.andshop.repository.UserRepository;
import com.tuannghia.andshop.service.ClotheService;
import com.tuannghia.andshop.service.CategoryService;
import com.tuannghia.andshop.service.FileUploadService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ClotheServiceImpl implements ClotheService {

    ClotheRepository clotheRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    CategoryService categoryService;
    FileUploadService fileUploadService;
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<Clothe> findAll() {
        return clotheRepository.findAll();
    }

    @Override
    public List<Clothe> findAllActive() {
        return clotheRepository.findAllByActiveFlag(true);
    }

    @Override
    public void addBook(Clothe clothe, MultipartFile coverImage) throws IOException {
        Clothe savedClothe = clotheRepository.save(clothe);
        savedClothe.setCoverImage(fileUploadService.uploadFile(coverImage));
        clotheRepository.save(clothe);
    }

    @Override
    public void editBook(Clothe clothe, MultipartFile coverImage) throws IOException {
        Clothe savedClothe = clotheRepository.save(clothe);
        if (!coverImage.isEmpty()) {
            savedClothe.setCoverImage(fileUploadService.uploadFile(coverImage));
            clotheRepository.save(clothe);
        }
    }

    @Override
    public void deleteBook(Long id) throws Exception {
        List<OrderDetail> orderDetailsFindByBookId = orderDetailRepository.findByClotheId((id));
        if(!orderDetailsFindByBookId.isEmpty()){
            throw new Exception("Sách đã có trong các đơn hàng , vui lòng xoá các đơn hàng có sách trước");
        }
        clotheRepository.deleteById(id);
    }

    @Override
    public void setActiveFlag(Long id, boolean activeFlag) throws Exception {
        Clothe clotheById = clotheRepository.findById(id).orElse(null);
        if(clotheById == null){
            throw new Exception("Không tìm thấy sách với id này");
        }
        clotheById.setActiveFlag(activeFlag);
        clotheRepository.save(clotheById);
    }

    @Override
    public Clothe getBookById(Long id) {
        Optional<Clothe> bookOptional = clotheRepository.findById(id);
        return bookOptional.orElse(null);
    }

    @Override
    public Clothe getBookByName(String name) {
        return clotheRepository.findByTitleAndActiveFlag(name, true);
    }
    private String generateUniqueFileName(String originalFileName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + "_" + originalFileName;
    }


    @Override
    public Page<Clothe> searchBooks(ClotheSearchDTO search, Pageable pageable) {
        Long categoryId = search.getCategoryId();
        String keyword = search.getKeyword();

        // Lấy dữ liệu phân trang dựa trên categoryId, keyword và các điều kiện tìm kiếm khác (nếu có)
        if (categoryId != null && keyword != null) {
            return clotheRepository.findByCategory_IdAndTitleContainingAndActiveFlag(categoryId, keyword, true, pageable);
        } else if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            return clotheRepository.findByCategoryAndActiveFlag(category, true, pageable);

        } else if (keyword != null) {
            return clotheRepository.findByTitleContainingAndActiveFlag(keyword, true, pageable);
        } else {
            return clotheRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Clothe> searchBooksUser(UserSearchDTO search, Pageable pageable) {
        Long categoryId = search.getCategoryId();
        String keyword = search.getKeyword();
        if (keyword == null) keyword = "";
        String sortBy = search.getSortBy();
        String amountGap = search.getAmountGap();
        String[] temp = amountGap.split(" ");
        Double startAmount = Double.parseDouble(temp[0].replace(".", ""));
        Double endAmount = Double.parseDouble(temp[3].replace(".", ""));


        Page<Clothe> booksPage = clotheRepository.findAll(pageable);
        if (categoryId != null) {
            if (sortBy.equals(SortType.oldest)) {
                booksPage = clotheRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtAsc(categoryId, keyword, startAmount, endAmount, true, pageable);
            } else if (sortBy.equals(SortType.newest)) {
                booksPage = clotheRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(categoryId, keyword, startAmount, endAmount, true, pageable);

            } else if (sortBy.equals(SortType.priceLowToHigh)) {
                booksPage = clotheRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceAsc(categoryId, keyword, startAmount, endAmount, true, pageable);

            } else {
                booksPage = clotheRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceDesc(categoryId, keyword, startAmount, endAmount, true, pageable);
            }
        } else {
            if (sortBy.equals(SortType.oldest)) {
                booksPage = clotheRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtAsc(keyword, startAmount, endAmount, true, pageable);
            } else if (sortBy.equals(SortType.newest)) {
                booksPage = clotheRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(keyword, startAmount, endAmount, true, pageable);

            } else if (sortBy.equals(SortType.priceLowToHigh)) {
                booksPage = clotheRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceAsc(keyword, startAmount, endAmount, true, pageable);

            } else {
                booksPage = clotheRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceDesc(keyword, startAmount, endAmount, true, pageable);
            }
        }
        if (sortBy == null) {
            booksPage = clotheRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(categoryId, keyword, startAmount, endAmount, true, pageable);
        }
        return booksPage;
    }


    @Override
    public Page<Clothe> getAllBooksForUsers(Pageable pageable) {
        return clotheRepository.findAll(pageable);
    }

    @Override
    public List<Clothe> getTop4BestSeller() {
        return clotheRepository.findTop4ByActiveFlagOrderByBuyCountDesc(true);
    }

    @Override
    public List<Clothe> getTop10BestSeller() {
        return clotheRepository.findTop10ByActiveFlagOrderByTotalRevenueDesc(true);
    }

    @Override
    public List<ClotheDto> getTop10BestSellerByMonth(int month) {
        List<Object[]> result = clotheRepository.findTop10BestSellerByMonth(month);
        List<ClotheDto> resultConvertedToDto = new ArrayList<>();
        for (Object[] item : result) {
            resultConvertedToDto.add(new ClotheDto(item[0].toString(), Double.parseDouble(item[1].toString())));
        }
        return resultConvertedToDto;
    }

    @Override
    public List<Clothe> findAllOrderByCreatedDate() {
        return clotheRepository.findByActiveFlagOrderByCreatedAtDesc(true);
    }

    @Override
    public Set<Clothe> getfavoriteClothesByUserId(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user.getFavoriteClothes();
        }
        return Collections.emptySet();
    }

    @Override
    public Long countBook() {
        return clotheRepository.count();
    }

    @Override
    public List<Clothe> getAllBooksByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return clotheRepository.findAllByCategoryAndActiveFlag(category, true);
    }

    @Override
    public Page<Clothe> getAllBooksPaginatedByCategoryId(Long categoryId, Pageable pageable) {
        return clotheRepository.findByCategoryIdAndActiveFlag(categoryId, true, pageable);
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthRevenuePerYear(int year) {
        List<Object[]> result = clotheRepository.findMonthlyRevenue(year);
        List<MonthlyRevenueDTO> resultConvertedToDto = new ArrayList<>();
        for (Object[] item : result) {
            resultConvertedToDto.add(new MonthlyRevenueDTO(Integer.parseInt(item[0].toString()), Double.parseDouble(item[1].toString())));
        }
        return resultConvertedToDto;
    }


}
