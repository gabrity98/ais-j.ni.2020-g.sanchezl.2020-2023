package es.codeurjc.ais;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import es.codeurjc.ais.book.Book;
import es.codeurjc.ais.book.BookDetail;
import es.codeurjc.ais.book.BookService;
import es.codeurjc.ais.book.OpenLibraryService;
import es.codeurjc.ais.book.OpenLibraryService.BookData;
import es.codeurjc.ais.notification.NotificationService;

class testBookService {
	
	private OpenLibraryService openLibraryService;
	private NotificationService notificationService;
	private BookService bookService;
	
	@BeforeEach
	void setUp() {
		openLibraryService = mock(OpenLibraryService.class);
		notificationService = mock(NotificationService.class);
		bookService = new BookService(openLibraryService, notificationService);
	}

	@Test
	void givenSubjectUrjc_thenReturnNoBooks() {
		
		when (openLibraryService.searchBooks("urjc", 10))
			.thenReturn(List.of());
		
		List<Book> books = bookService.findAll("urjc");
		
		assertTrue(books.isEmpty(), "Se debe devolver una lista vacía de libros");
		verify(openLibraryService).searchBooks("urjc", 10);
		verify(notificationService, times(1)).info("The books have been loaded: 0 books | query: urjc");
	}
	
	@Test
	void givenId_thenReturnBook() {
		
		Integer[] covers = new Integer[] {0};
		String[] subjects = new String[] {"subject"};
		BookData libro = new BookData("titulo","/works/0000","descripcion",covers,subjects);
		
		when (openLibraryService.getBook("0000"))
			.thenReturn(libro);
		Optional<BookDetail> book = bookService.findById("0000");
		
		assertTrue(book.isPresent(), "Se debe devolver un libro");
		assertEquals("0000", book.get().getId(), "id del libro devuelto");
		assertEquals("titulo", book.get().getTitle(), "título del libro devuelto");
		verify(openLibraryService).getBook("0000");
		verify(notificationService, times(1)).info("The book has been loaded: titulo | id: 0000");
	}

}
