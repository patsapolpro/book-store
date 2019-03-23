package com.volkspace.bookstore.external;

public interface BookExternalService {
    BookResponse fetchAll();

    BookResponse fetchRecommendation();
}
