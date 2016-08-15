import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMethodOrder {

    @Test
    public void list() {
        System.out.println("List method");
    }
    @Test
    public void addNewBook() {
        System.out.println("AddNewBook method");
    }
    @Test
    public void removeBook() {
        System.out.println("RemoveBook method");
    }

    @Test
    public void add() {
        System.out.println("Add method");
    }
    @Test
    public void buy() {
        System.out.println("Buy method");
    }
    @Test
    public void getPrize() {
        System.out.println("get prize method");
    }
}
