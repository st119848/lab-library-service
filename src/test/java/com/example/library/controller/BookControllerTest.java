package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class) // Focus only on Controller
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to simulate HTTP Requests

    @MockBean // Mock the dependency
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper; // Convert Object <-> JSON

    @Test
    void createBook_ShouldReturn201_WhenValidInput() throws Exception {
        // Arrange
        Book inputBook = new Book("978-1", "TDD with Spring");
        Book savedBook = new Book("978-1", "TDD with Spring");
        // Mock Service behavior
        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBook))) // Body
                .andExpect(status().isCreated()) // Expect HTTP 201
                .andExpect(jsonPath("$.isbn").value("978-1")) // Check JSON response
                .andExpect(jsonPath("$.title").value("TDD with Spring"));
    }
}