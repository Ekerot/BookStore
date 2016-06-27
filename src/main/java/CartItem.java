
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