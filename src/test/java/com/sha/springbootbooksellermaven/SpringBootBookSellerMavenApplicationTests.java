package com.sha.springbootbooksellermaven;

import com.sha.springbootbooksellermaven.model.Book;
import com.sha.springbootbooksellermaven.repository.IBookRepository;
import com.sha.springbootbooksellermaven.service.BookService;
import com.sha.springbootbooksellermaven.service.UserService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SpringBootBookSellerMavenApplicationTests {


	@Autowired
	private BookService bookService;

	@MockBean
	private IBookRepository bookRepository;

	@Test
	public void testSaveUser() {
		// setup
		Book book = new Book();
		book.setTitle("b");
		book.setDescription("b");
		book.setAuthor("b");
		book.setPrice(0.0);
		book.setCreateTime(LocalDateTime.parse("2023-02-26T02:42:49.811438300"));

		// mock the repository
		when(bookRepository.save(book)).thenReturn(book);

		// invoke the service method
		Book savedUser = bookService.saveBook(book);

		// assertions
		assertNull(savedUser.getId());
		assertEquals("b", savedUser.getTitle());
		assertEquals("b", savedUser.getDescription());
		assertEquals("b", savedUser.getAuthor());
		assertEquals(0.0, savedUser.getPrice());
		assertNotEquals(LocalDateTime.parse("2023-02-26T02:42:49.811438300"), savedUser.getCreateTime());
	}


	@Test
	public void deleteProductById() {
		// create a product
		Book book = new Book();
		book.setTitle("f");
		book.setDescription("f");
		book.setAuthor("f");
		book.setPrice(0.0);
		book.setCreateTime(LocalDateTime.parse("2023-02-26T02:42:49.811438300"));

		Book savedUser = bookService.saveBook(book);

		// delete the product
		bookService.deleteBook(savedUser.getId());


		// assert that the product is deleted
		Optional<Book> deletedProduct = bookRepository.findById(savedUser.getId());
		assertFalse(deletedProduct.isPresent());
	}

	@Test
	public void getAllProducts() {
		// create some products
		Book book = new Book();
		book.setTitle("f");
		book.setDescription("f");
		book.setAuthor("f");
		book.setPrice(0.0);
		book.setCreateTime(LocalDateTime.parse("2023-02-26T02:42:49.811438300"));
		Book savedUser = bookService.saveBook(book);

		Book book2 = new Book();
		book2.setTitle("h");
		book2.setDescription("h");
		book2.setAuthor("h");
		book2.setPrice(0.0);
		book2.setCreateTime(LocalDateTime.parse("2023-02-26T02:42:49.811438300"));
		Book savedUser2 = bookService.saveBook(book2);

		// retrieve all products
		List<Book> products = bookService.findAllBooks();

		// assert that both products are present in the list
		assertEquals(2, products.size());
		assertTrue(products.contains(savedUser));
		assertTrue(products.contains(savedUser2));
	}


	@Test
	void saveBook2() {
		// Arrange
		final var bookRepository = Mockito.mock(IBookRepository.class);
		final var bookservice = new BookService(bookRepository);
		final var book2 = new Book();
		book2.setTitle("h");
		book2.setDescription("h");
		book2.setAuthor("h");
		book2.setPrice(0.0);
		book2.setCreateTime(LocalDateTime.parse("2023-02-26T02:42:49.811438300"));

		Mockito.doReturn(book2)
				.when(bookRepository)
				.save(Mockito.any(Book.class));

		// Act
		final var actualResult = bookservice.saveBook(book2);

		// Assert
		assertSame(book2, actualResult);
	}
//	}
//
//	@RunWith(MockitoJUnitRunner.class)
//	public class UserServiceTests {
//
//		@InjectMocks
//		private UserService userService;
//
//		@Mock
//		private UserRepository userRepository;
//
//		@Test
//		public void getUserById() {
//			// create a mock user
//			User mockUser = new User();
//			mockUser.setId(1L);
//			mockUser.setUsername("testuser");
//
//			// define mock behavior for the user repository
//			Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
//
//			// retrieve the user by ID
//			Optional<User> retrievedUser = userService.findById(1L);
//
//			// assert that the retrieved user matches the mock user
//			assertTrue(retrievedUser.isPresent());
//			assertEquals(mockUser.getUsername(), retrievedUser.get().getUsername());
//		}
//	}

//	Bu örnekte, UserService'in UserRepository bağımlılığıyla alay etmek için MockitoJUnitRunner ve Mockito ek açıklamalarını kullanıyoruz. Sahte UserRepository'yi test edilmekte olan UserService örneğine enjekte etmek için @InjectMocks ek açıklamasını kullanıyoruz.
//
//	getUserById test yöntemi, sahte bir Kullanıcı örneği oluşturur ve Mockito.When kullanarak davranışını tanımlar. FindById yöntemi 1L ID ile çağrıldığında Mockito'ya sahte kullanıcıyı döndürmesini söylüyoruz.
//
//	Ardından, UserService'in findById yöntemini 1L kimliğiyle çağırır ve alınan kullanıcının sahte kullanıcıyla eşleştiğini iddia ederiz.
//
//	Bu test, sahte bir UserRepository kullanımı sayesinde, UserService'in findById yöntemini bağımlılıklarından ayrı olarak test ediyor. UserRepository ile alay etmek için Mockito'yu kullanarak, veritabanının ayrıntıları veya diğer dış bağımlılıklar hakkında endişelenmeden UserService mantığını test etmeye odaklanabiliyoruz.

}