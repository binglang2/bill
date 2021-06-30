package com.banma.bill.service;

import com.banma.bill.dto.vo.CategoryTransCountVO;
import com.banma.bill.dto.vo.CategoryVO;
import java.util.List;

/**
 * @author binglang
 * @since 2019/8/14
 */
public interface CategoryService {

    List<CategoryVO> listCategory(Long userId, Integer transType, Long bookId);

    boolean saveCategory(CategoryVO categoryVO, Long userId);

    CategoryTransCountVO countTransByCategory(Long userId, Long id);

    boolean removeCategory(Long userId, Long id);
}
