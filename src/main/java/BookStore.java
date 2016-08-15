import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Containing methods for a book store.
 * <p/>
 * Currently available <code>BookStore</code> implementations in the <code>BookStore</code> pakages are:
 * <ul>
 * <li>{@link BookStoreFX}</li>
 * <li> {@link Book}</li>
 * <li> {@link CartItem}</li>
 * <li> {@link IBookList}</li>
 * </ul>
 *
 * @author Daniel Ekerot
 * @since: 2006-06-24
 */

class BookStore implements IBookList {

    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<CartItem> cart = new ArrayList<CartItem>();
    private Book[] searchList = new Book[20];

    public Book getBooks(int i) {
        return books.get(i);
    }

    void addBookList(File file) throws FileNotFoundException {

        Scanner newBook = new Scanner(file);

        while (newBook.hasNext()) {

            String output = newBook.nextLine();

            Book book = new Book(output);

            books.add(book);

        }

    }

    @Override
    public Book[] list(String searchString) {

        for (int k = 0; k <= searchList.length - 1; k++) {

            removeElement(searchList, k);

        }

        int j = 0;


        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getTitle().contains(searchString) || books.get(i).getAuthor().contains(searchString)) {
                searchList[j] = getBooks(i);
                j++;


            }
        }


        return searchList;
    }

    public void addNewBook(String title, String author, String price, String stock) {

        Book book = new Book(title, author, price, Integer.parseInt(stock));

        books.add(book);

    }

    public boolean add(Book book, int quantity) {

        CartItem cart1 = new CartItem(book, quantity);

        cart.add(cart1);

        return true;
    }

    public void removeBook(CartItem cartItem) {


        for (int i = 0; i < cart.size(); i++) {

            if (cart.get(i) == cartItem) {

                cart.remove(i);

            }
        }
    }

    @Override
    public int[] buy(Book book) {

        int[] inStock = new int[cart.size()];

        for (int i = 0; i < cart.size(); i++) {

            if (cart.get(i).getBook().getStock() == 0) {

                inStock[i] = 1;

            } else {

                inStock[i] = 0;

            }


        }

        return inStock;
    }

    private void removeElement(Book[] b, int del) {
        System.arraycopy(b, del + 1, b, del, b.length - 1 - del);
    }

    public ArrayList<Book> getArrayListBooks() {

        return books;
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public String getNewPrice() {

        double sum = 0;


        for (CartItem c : cart) {
            double price = c.getBook().getPrice().doubleValue();
            double quantity = (double) c.getAmount();
            sum = sum + (price * quantity);
        }


        return String.valueOf(sum);

    }

}