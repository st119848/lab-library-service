package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // เปิดใช้งาน Mockito
class BookServiceTest {

    @Mock // จำลอง Repository (ไม่ต้องต่อ DB จริง)
    private BookRepository bookRepository;

    @InjectMocks // ฉีด Mock เข้าไปใน Service
    private BookService bookService;

    @Test
    void createBook_ShouldThrowException_WhenIsbnExists() {
        // Arrange
        Book existingBook = new Book("978-1", "Java TDD");
        // บอก Mock ว่า: ถ้ามีคนเรียก findByIsbn("978-1") ให้คืนค่า existingBook กลับไป
        when(bookRepository.findByIsbn("978-1")).thenReturn(Optional.of(existingBook));

        Book newBook = new Book("978-1", "Advanced Java");

        // Act & Assert
        // คาดหวังว่าต้องโยน Exception ถ้า ISBN ซ้ำ
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.createBook(newBook);
        });

        // Verify: ตรวจสอบว่าไม่มีการเรียก save() จริงๆ
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void createBook_ShouldSaveBook_WhenIsbnIsNew() {
        // Arrange
        when(bookRepository.findByIsbn("978-2")).thenReturn(Optional.empty());
        Book newBook = new Book("978-2", "Spring Boot");
        
        // Mock ว่า save แล้วได้ object กลับมา
        when(bookRepository.save(newBook)).thenReturn(newBook);

        // Act
        Book savedBook = bookService.createBook(newBook);

        // Assert
        assertNotNull(savedBook);
        assertEquals("Spring Boot", savedBook.getTitle());
        
        // Verify: ตรวจสอบว่ามีการเรียก save 1 ครั้ง
        verify(bookRepository, times(1)).save(newBook);
    }
}
