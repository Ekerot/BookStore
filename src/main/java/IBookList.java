/**
 * Interface containing methods for a book store.
 * <p/>
 * Currently available <code>BookStore</code> implementations in the <code>BookStore</code> pakages are:
 * <ul>
 * <li>{@link BookStore}</li>
 * </ul>
 *
 * @author Daniel Ekerot
 * @since: 2006-06-24
 */

interface IBookList {

    Book[] list(String searchString);

    boolean add(Book book, int quantity);

    int[] buy(Book book);

}