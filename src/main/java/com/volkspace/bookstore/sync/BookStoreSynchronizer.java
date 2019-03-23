package com.volkspace.bookstore.sync;

import com.volkspace.bookstore.external.BookData;
import com.volkspace.bookstore.external.BookExternalService;
import com.volkspace.bookstore.external.BookResponse;
import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class BookStoreSynchronizer {

    @Autowired
    private BookExternalService bookExternalService;

    @Autowired
    private BookStoreRepository bookStoreRepository;


    @Transactional
    public void forceSync() {
        BookResponse bookResponse = bookExternalService.fetchAll();

        Map<Integer, BookData> mapBookRecommendation = new HashMap<>();
        BookResponse bookRecommendation = bookExternalService.fetchRecommendation();
        for (BookData bookDataRecommendation: bookRecommendation) {
            mapBookRecommendation.put(bookDataRecommendation.getId(), bookDataRecommendation);
        }
        for (BookData bookData: bookResponse) {
            Book book = new Book();
            book.setId(bookData.getId());
            book.setName(bookData.getBook_name());
            book.setAuthor(bookData.getAuthor_name());
            book.setPrice(bookData.getPrice());

            if(mapBookRecommendation.containsKey(bookData.getId())){
                book.setIs_recommended(true);
            } else {
                book.setIs_recommended(false);
            }

            bookStoreRepository.save(book);
        }
    }
}
