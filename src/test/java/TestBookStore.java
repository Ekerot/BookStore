import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ekerot on 2016-08-14.
 */
public class TestBookStore {

    BookStore testBook = new BookStore();

    @Test
    public void testAddNewBook(){

        testBook.addNewBook("Hej", "Hej", "123", "123");

        assertEquals("Hej", testBook.getBooks(0).getTitle());
        assertEquals("Hej", testBook.getBooks(0).getAuthor());
        assertEquals(BigDecimal.valueOf(123), testBook.getBooks(0).getPrice());
        assertEquals(123, testBook.getBooks(0).getStock());

    }

    @Test
    public void testAddBook(){

        File file = new File("/Users/ekerot/Documents/bookstoredata.txt");

        try {
            testBook.addBookList(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        testBook.add(testBook.getBooks(2),2);

        assertEquals(testBook.getBooks(2).getTitle(), testBook.getCart().get(0).getBook().getTitle());
        assertEquals(2, testBook.getCart().get(0).getAmount());
    }

    @Test
    public void testRemoveBook(){

        File file = new File("/Users/ekerot/Documents/bookstoredata.txt");

        try {
            testBook.addBookList(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        testBook.add(testBook.getBooks(2),2);

        assertEquals(false, testBook.getCart().isEmpty());
        testBook.removeBook(testBook.getCart().get(0));
        assertEquals(true, testBook.getCart().isEmpty());
    }

    @Test (expected = FileNotFoundException.class)
    public void testFileNotFoundException() throws FileNotFoundException{

        File file;
        file = new File("/Users/ekerot/Documents/");
        testBook.addBookList(file);

    }

}
