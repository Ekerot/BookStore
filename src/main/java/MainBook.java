import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class to execute program without GUI in console
 *
 * @author Daniel Ekerot
 * @since: 2006-06-24
 */

class MainBook {

    public static void main(String[] args) throws FileNotFoundException {

        BookStore b = new BookStore();

        File file = new File("/Users/ekerot/Documents/bookstoredata.txt");

        b.addBookList(file);

        System.out.println();

        System.out.println(b.toString());

        System.out.println();

        b.addNewBook("Bengans mekko", "Bengan Lökberg", "199.00", "20");

        System.out.println(b.toString());

        b.list("B");

        System.out.println();

        System.out.println();

        System.out.println(b.toString());

        System.out.println();

        b.list("Bengan");

        System.out.println();

        b.add(b.getBooks(7), 3);

        b.printCart();

        System.out.println();

        b.add(b.getBooks(3), 6);

        b.printCart();

        System.out.println();

        b.printCart();

        System.out.println();


    }

}