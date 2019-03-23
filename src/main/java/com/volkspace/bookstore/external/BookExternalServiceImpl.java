package com.volkspace.bookstore.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class BookExternalServiceImpl implements BookExternalService {

    public static final Logger logger = LoggerFactory.getLogger(BookExternalServiceImpl.class);

    public static final String BOOK_DATA_URL = "https://scb-test-book-publisher.herokuapp.com/books";
    public static final String BOOK_RECOMMENDATION_DATA_URL = "https://scb-test-book-publisher.herokuapp.com/books/recommendation";

    @Autowired
    private RestOperations restTemplate;

    @Override
    public BookResponse fetchAll() {
        BookResponse bookResponse = new BookResponse();
        try{
            ResponseEntity<BookResponse> response = restTemplate.getForEntity(BOOK_DATA_URL, BookResponse.class);
            if(response.getStatusCodeValue() == 200) {
                bookResponse = response.getBody();
            }
        } catch(Exception e) {
            logger.debug("Cannot fetchAll : {}", e.getMessage());
        }
        return bookResponse;
    }

    @Override
    public BookResponse fetchRecommendation() {
        BookResponse bookResponse = new BookResponse();
        try{
            ResponseEntity<BookResponse> response = restTemplate.getForEntity(BOOK_RECOMMENDATION_DATA_URL, BookResponse.class);
            if(response.getStatusCodeValue() == 200) {
                bookResponse = response.getBody();
            }
        } catch(Exception e) {
            logger.debug("Cannot fetchRecommendation : {}", e.getMessage());
        }
        return bookResponse;
    }
}
