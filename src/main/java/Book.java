import java.math.BigDecimal;

/**
 * Class constructing a Book
 *
 * @author Daniel Ekerot
 * @since: 2006-06-24
 */

public class Book{

    private final String title;
    private final String author;
    private BigDecimal price;
    private int stock;

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    BigDecimal getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    Book(String t, String a, String p, int s) {

        title = t;
        author = a;
        price = new BigDecimal(p.replaceAll(",", ""));
        stock = s;

    }

    public Book(String input) {

        String[] theBook = input.split(";");
        String parsePrice = theBook[2];
        String parseStock = theBook[3];

        title = theBook[0];
        author = theBook[1];
        price = new BigDecimal(parsePrice.replaceAll(",", ""));
        stock = Integer.parseInt(parseStock);

    }

}