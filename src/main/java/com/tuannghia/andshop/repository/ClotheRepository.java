package com.tuannghia.andshop.repository;

import com.tuannghia.andshop.entity.Clothe;
import com.tuannghia.andshop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClotheRepository extends JpaRepository<Clothe, Long> {

    Page<Clothe> findByTitleContainingAndActiveFlag(String keyword, boolean activeFlag, Pageable pageable);

    Clothe findByTitleAndActiveFlag(String title, boolean activeFlag);

    Page<Clothe> findByCategoryAndActiveFlag(Category category, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategoryIdAndOriginalPriceBetweenAndActiveFlag(Long categoryId, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategory_IdAndTitleContainingAndOriginalPriceBetweenAndActiveFlag(Long categoryId, String keyword, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceDesc(Long categoryId, String keyword, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceAsc(Long categoryId, String keyword, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtAsc(Long categoryId, String keyword, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(Long categoryId, String keyword, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtAsc(String title, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(String title, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceAsc(String title, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceDesc(String title, double minPrice, double maxPrice, boolean activeFlag, Pageable pageable);

    List<Clothe> findTop4ByActiveFlagOrderByBuyCountDesc(boolean activeFlag);

    List<Clothe> findByActiveFlagOrderByCreatedAtDesc(boolean activeFlag);

    List<Clothe> findTop10ByActiveFlagOrderByTotalRevenueDesc(boolean activeFlag);

    List<Clothe> findAllByActiveFlag(boolean activeFlag);

    List<Clothe> findAllByCategoryAndActiveFlag(Category category, boolean activeFlag);

    Page<Clothe> findByCategoryIdAndActiveFlag(Long categoryId, boolean activeFlag, Pageable pageable);

    Page<Clothe> findByCategory_IdAndTitleContainingAndActiveFlag(Long categoryId, String keyword, boolean activeFlag, Pageable pageable);

    @Query(value = "SELECT b.title, SUM(od.price * od.quantity) AS total_revenue FROM clothes b " +
            "JOIN order_details od ON b.id = od.clothe_id " +
            "JOIN orders o ON od.order_id = o.id " +
            "WHERE MONTH(o.create_date) = :month " +
            "GROUP BY b.title " +
            "ORDER BY total_revenue " +
            "DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10BestSellerByMonth(@Param("month") int month);

    @Query("SELECT MONTH(od.createdAt) AS month, SUM(od.totalPrice) AS totalRevenue " +
            "FROM Order od " +
            "WHERE YEAR(od.createdAt) = :year " +
            "GROUP BY MONTH(od.createdAt) " +
            "ORDER BY MONTH(od.createdAt)")
    List<Object[]> findMonthlyRevenue(@Param("year") int year);
}