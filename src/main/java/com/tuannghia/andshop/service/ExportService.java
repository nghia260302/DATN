package com.tuannghia.andshop.service;

import com.tuannghia.andshop.dto.ClotheDto;
import com.tuannghia.andshop.dto.CategoryDto;
import com.tuannghia.andshop.dto.OrderDTO;
import com.tuannghia.andshop.entity.User;

import java.util.List;

public interface ExportService {

    String exportOrderReport(User user, List<OrderDTO> orderDTOList, String keyword);

    String exportCategoryReport(User user, List<CategoryDto> categoryDTOList, String keyword);

    String exportBookReport(User user, List<ClotheDto> clotheDtoList, String keyword);

}
