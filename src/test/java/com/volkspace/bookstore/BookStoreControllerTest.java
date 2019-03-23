package com.volkspace.bookstore;

import com.volkspace.bookstore.sync.BookStoreSynchronizer;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class BookStoreControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private BookStoreSynchronizer bookStoreSynchronizer;

	@Before
	public void setUp() throws Exception {
		bookStoreSynchronizer.forceSync();
	}

	@Test
	public void getBooksWithRestApi() throws Exception {
		mvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.book").isArray())
				.andExpect(jsonPath("$.book.length()", Matchers.equalTo(12)))
				.andExpect(jsonPath("$.book[?(@.name == 'Before We Were Yours: A Novel')].author",
						Matchers.equalTo(Arrays.asList("Lisa Wingate"))));
	}

}
