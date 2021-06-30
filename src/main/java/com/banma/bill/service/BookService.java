package com.banma.bill.service;

import com.banma.bill.dto.vo.BookVO;
import java.util.List;

/**
 * @author binglang
 * @since 2019-08-14
 */
public interface BookService {

    default void addDefaultBook(Long userId, String name) {
        addBook(userId, name, 1);
    }

    Long addBook(Long userId, String name, int status);

    List<BookVO> listBook(Long userId);

    boolean saveBook(Long userId, BookVO bookVO);

    boolean removeBook(Long userId, Long id);
}

