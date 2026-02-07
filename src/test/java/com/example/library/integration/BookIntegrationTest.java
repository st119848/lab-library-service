package com.example.library.integration;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // Load Full Application Context
@AutoConfigureMockMvc
class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll(); // Clean DB before each test
    }

    @Test
    void createBook_Flow_ShouldSaveToDatabase() throws Exception {
        // Arrange
        Book newBook = new Book("978-999", "Integration Testing");

        // Act (Call API via MockMvc)
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("978-999"));

        // Assert (Verify directly in Database)
        // ตรวจสอบว่าข้อมูลลง DB จริงๆ ไม่ใช่แค่ Mock
        Book savedBook = bookRepository.findByIsbn("978-999").orElseThrow();
        assert savedBook.getTitle().equals("Integration Testing");
    }
}