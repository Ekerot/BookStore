import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

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
        int choice;
        float pris = 0;

        do {
            System.out.println("Välj ditt val i menyn genom att ange en siffra:\n\n" +
                    "1. Sök bok\n" +
                    "2. Lägg till bok i varukorg\n" +
                    "3. Ta bort bok från varukorg\n" +
                    "4. Köp\n" +
                    "5. Visa kundkorg\n" +
                    "6. Lägg till ny bok i lista\n" +
                    "0. Avsluta");

            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();


            switch (choice) {
                case 1:

                    System.out.println("Gör en sökning: ");

                    Scanner scn = new Scanner(System.in);
                    String searchString = scn.nextLine();
                    System.out.println();

                    System.out.println("Din sökning ger resultat: ");
                    System.out.println();

                    for (Book c : b.getArrayListBooks()) {

                        if (c.getTitle().contains(searchString) || c.getAuthor().contains(searchString)) {
                            System.out.println("Titel: " + c.getTitle() + ", Författare:" + c.getAuthor() + ", Pris:" + c.getPrice() + ", Stock:" + c.getStock());
                        }

                    }

                    System.out.println();


                    break;
                case 2:
                    System.out.println("Tryck respektive siffra för den bok du vill lägga i din kundkorg: ");
                    System.out.println();

                    int siffra = 0;
                    for (Book c : b.getArrayListBooks()) {

                        siffra++;
                        System.out.println(siffra + ". " + "Titel: " + c.getTitle() + ", Författare:" + c.getAuthor() + ", Pris:" + c.getPrice() + ", Stock:" + c.getStock());

                    }
                    System.out.println();

                    Scanner scnAdd = new Scanner(System.in);
                    int valBok = scnAdd.nextInt();

                    System.out.println("Välj antal böcker: ");

                    Scanner scnAntal = new Scanner(System.in);
                    int antal = scnAntal.nextInt();

                    b.add(b.getBooks(valBok - 1), antal);

                    if (antal > b.getBooks(valBok - 1).getStock()) {
                        System.out.println("Vi har inte så många böcker i lager!");
                    }

                    else {
                        float temp = b.getBooks(valBok - 1).getPrice().floatValue() * antal;
                        System.out.println("Boken är tillagd i din kundvagn!");
                        pris = pris + temp;
                        System.out.println();
                        System.out.println("Böckerna i din kundvagn kostar " + pris + "kr");

                    }

                    break;
                case 3:
                    System.out.println("Tryck respektive siffra för den bok du vill ta bort från din kundkorg: ");
                    System.out.println();

                    int siffraRemove = 0;
                    for (CartItem c : b.getCart()) {

                        siffraRemove++;
                        System.out.println(siffraRemove + ". " + "Titel: " + c.getBook().getTitle() + ", Författare:" + c.getBook().getAuthor() + ", Pris:" + c.getBook().getPrice() + ", Antal i kundvagn:" + c.getAmount());

                    }
                    System.out.println();

                    Scanner scnRemove = new Scanner(System.in);
                    int removeBok = scnRemove.nextInt();

                    BigDecimal price = b.getCart().get(removeBok-1).getBook().getPrice();
                    float temp = price.floatValue();
                    pris = pris - (b.getCart().get(removeBok-1).getAmount() * temp);

                    b.removeBook(b.getCart().get(removeBok - 1));

                    System.out.println("Boken är borttagen!");
                    System.out.println();
                    System.out.println("Böckerna i din kundvagn kostar " + pris + "kr");
                    System.out.println();


                    break;
                case 4:


                    int i = 0;
                    int[] tmp = new int[b.getCart().size()];

                    for (CartItem c : b.getCart()) {
                        tmp = b.buy(c.getBook());

                        if (tmp[i] == 1) {
                            System.out.println("Boken " + c.getBook().getTitle() + " finns inte på lager!");

                        } else if (0 > c.getBook().getStock() - c.getAmount() && c.getBook().getStock() != 0) {
                            System.out.println("Vi har inte tillräckligt många böcker av " + c.getBook().getTitle() + " i lager!");
                        } else if (tmp[i] == 2) {
                            System.out.println("Boken " + c.getBook().getTitle() + " finns inte!");
                        } else {

                            i++;

                        }

                    }

                    System.out.println("Köpet är slutfört");
                    System.out.println("Din betalning blir: " + pris);
                    
                    break;
                case 5:

                    System.out.println("Din kundkorg:");
                    System.out.println();

                    int siffraVisa = 0;
                    for (CartItem c : b.getCart()) {

                        siffraVisa++;
                        System.out.println(siffraVisa + ". " + "Titel: " + c.getBook().getTitle() + ", Författare:" + c.getBook().getAuthor() + ", Pris:" + c.getBook().getPrice() + ", Antal i kundvagn:" + c.getAmount());

                    }

                    System.out.println();
                    System.out.println("Kostnad: " + pris);
                    System.out.println();


                    break;
                case 6:

                    Scanner scnNyBok = new Scanner(System.in);

                    System.out.println("Registrera ny bok!");
                    System.out.println();

                    System.out.println("Registrera titel: ");
                    String titel = scnNyBok.nextLine();
                    System.out.println("Registrera författare");
                    String author = scnNyBok.nextLine();
                    System.out.println("Registrera pris");
                    String prisBok = scnNyBok.nextLine();
                    System.out.println("Registrera antal i lager");
                    String stock = scnNyBok.nextLine();

                    b.addNewBook(titel, author, prisBok, stock);


                    break;
                case 0:
                    break;
                default:
                    // The user input an unexpected choice.


            }



        }while (choice != 0) ;
    }
}





/*
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
*/



