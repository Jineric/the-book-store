package com.pluralsight.bookstore.repository;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Test(expected = Exception.class)
    public void findWithInvalidId(){
        bookRepository.find(null);
    }

    @Test(expected = Exception.class)
    public void createInvalidBook(){
        Book book = new Book("isbn", null, 12F, 123, Language.ENGLISH, new Date(),"hptt://", "description");
        bookRepository.create(book);
    }

    @Test
    public void create() throws Exception {
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

        //create book
        Book book = new Book("isbn", "a  title", 12F, 123, Language.ENGLISH, new Date(),"hptt://", "description");
        book = bookRepository.create(book);
        Long bookId = book.getId();

        //check for created book
        assertNotNull(bookId);

        //find the created book
        Book bookFound = bookRepository.find(bookId);

        //check the created book
        assertEquals("a title",bookFound.getTitle());
        assertTrue(bookFound.getIsbn().startsWith("13"));

        //test for book counting
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(1, bookRepository.findAll().size());

        //delete the book
        bookRepository.delete(bookId);

        //test for book counting after delete
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }
}
