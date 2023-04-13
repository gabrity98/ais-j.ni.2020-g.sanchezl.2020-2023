package es.codeurjc.ais;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.ais.book.OpenLibraryService;
import es.codeurjc.ais.book.OpenLibraryService.BookData;

class testOpenLibraryService {
	private OpenLibraryService openLibraryService;

	@BeforeEach
	void setUp() {
		openLibraryService = new OpenLibraryService();
	}
	
	@Test
	void givenTopicUrjc_whenLimitIs15_thenReturn0books() {
		List<BookData> books = openLibraryService.searchBooks("urjc", 15);
		assertTrue(books.isEmpty(), "Se debe devolver una lista vacía de libros");
	}
	
	@Test
	void givenIdUrjc_ThrowException() {
		HttpClientErrorException ex = assertThrows(HttpClientErrorException.class,()->{
			openLibraryService.getBook("urjc");
		});
		assertEquals("404 Not Found: \"{\"error\": \"notfound\", \"key\": \"/works/urjc\"}\"", ex.getMessage(), 
		"Mensaje de la excepción http");
	}

}
