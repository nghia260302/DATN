package com.tuannghia.andshop.service;

import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.dto.ClotheDto;
import com.tuannghia.andshop.dto.ClotheSearchDTO;
import com.tuannghia.andshop.dto.MonthlyRevenueDTO;
import com.tuannghia.andshop.dto.UserSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ClotheService {

    List<Clothe> findAll();

    List<Clothe> findAllActive();

    void addBook(Clothe clothe, MultipartFile coverImage) throws IOException;

    void editBook(Clothe clothe, MultipartFile coverImage) throws IOException;

    void deleteBook(Long id) throws Exception;

    void setActiveFlag(Long id, boolean activeFlag) throws Exception;

    Clothe getBookById(Long id);

    Clothe getBookByName(String name);

    Page<Clothe> searchBooks(ClotheSearchDTO search, Pageable pageable);

    Page<Clothe> searchBooksUser(UserSearchDTO search, Pageable pageable);

    Page<Clothe> getAllBooksForUsers(Pageable pageable);

    List<Clothe> getTop4BestSeller();

    List<Clothe> getTop10BestSeller();

    List<ClotheDto> getTop10BestSellerByMonth(int month);

    List<MonthlyRevenueDTO> getMonthRevenuePerYear(int year);

    List<Clothe> findAllOrderByCreatedDate();

    Set<Clothe> getfavoriteClothesByUserId(Long id);

    Long countBook();

    List<Clothe> getAllBooksByCategoryId(Long id);

    Page<Clothe> getAllBooksPaginatedByCategoryId(Long categoryId, Pageable pageable);


}
