package com.volkspace.bookstore.external;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookExternalServiceImplTest {

    @Autowired
    private BookExternalServiceImpl bookExternalService;

    @Test
    public void testFetchAll() {
        BookResponse bookResponse = bookExternalService.fetchAll();
        Assert.assertThat(bookResponse.size(), Matchers.equalTo(12));
    }

    @Test
    public void testFetchRecommendation() {
        BookResponse bookResponse = bookExternalService.fetchRecommendation();
        Assert.assertThat(bookResponse.size(), Matchers.equalTo(2));
    }

    @Test
    public void testMappingDataCorrectly() {
        BookResponse bookResponse = bookExternalService.fetchAll();
        Optional<BookData> optionalBookData = bookResponse.stream()
                                                    .filter(b -> b.getBook_name().equals("Before We Were Yours: A Novel"))
                                                    .findFirst();
        Assert.assertThat(optionalBookData.isPresent(), Matchers.equalTo(true));
        Assert.assertThat(optionalBookData.get().getId(), Matchers.equalTo(1));
        Assert.assertThat(optionalBookData.get().getAuthor_name(), Matchers.equalTo("Lisa Wingate"));
        Assert.assertThat(optionalBookData.get().getPrice(), Matchers.equalTo(340d));
    }
}
