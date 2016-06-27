

/**
 * Class constructing a CartItem
 *
 * @author Daniel Ekerot
 * @since: 2006-06-24
 */

class CartItem {

    private Book book;
    private int amount;

    public CartItem(Book b, int a) {

        book = b;
        amount = a;

    }

    public Book getBook() {
        return book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}