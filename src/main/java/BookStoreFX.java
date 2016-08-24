import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Graphical interface for a BookStore
 *
 * @author Daniel Ekerot
 * @since: 2006-06-24
 */

public class BookStoreFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {


        BookStore b = new BookStore();
        ArrayList<CartItem> cart = b.getCart();

        File file = new File("src/main/bookstoredata.txt");

        try {
            b.addBookList(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BorderPane borderPane = new BorderPane();

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.BOTTOM_LEFT);
        topBox.setPrefSize(800, 100);
        topBox.setPadding(new Insets(10, 10, 10, 10));
        topBox.setSpacing(5);

        Text text1 = new Text("Search:");
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setLineSpacing(10.0);

        final TextField textFieldSearch = new TextField();
        textFieldSearch.setPrefSize(100, 20);

        Button btn = new Button("OK");
        btn.setAlignment(Pos.CENTER);
        btn.setPrefSize(50, 20);
        btn.setLineSpacing(10.0);

        VBox listBox = new VBox();
        listBox.setAlignment(Pos.TOP_RIGHT);
        listBox.setPrefSize(500, 500);
        listBox.setPadding(new Insets(10, 10, 10, 10));
        listBox.setSpacing(5);

        ListView<Book> listView = new ListView<Book>();
        listView.setPrefSize(600, 500);
        listView.setStyle("-fx-font-family: monospaced;");
        listView.setCellFactory(lv -> new ListCell<Book>() {

            @Override
            public void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                } else {
                    String text = String.format("%-20s %-20s %s", item.getTitle(), item.getAuthor(), item.getPrice() + "kr");
                    setText(text);
                }
            }
        });

        VBox cartBox = new VBox();
        cartBox.setAlignment(Pos.TOP_RIGHT);
        cartBox.setPrefSize(500, 500);
        cartBox.setPadding(new Insets(10, 10, 10, 10));
        cartBox.setSpacing(5);

        Text text3 = new Text("Cart:");
        text3.setTextAlignment(TextAlignment.JUSTIFY);
        text3.setLineSpacing(5.0);

        ListView<CartItem> listCart = new ListView<CartItem>();
        listCart.setPrefSize(600, 500);
        listCart.setStyle("-fx-font-family: monospaced;");
        listCart.setCellFactory(lv -> new ListCell<CartItem>() {

            @Override
            public void updateItem(CartItem item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                } else {
                    String text = String.format("%-20s %-20s %s", item.getBook().getTitle(), item.getBook().getAuthor(), item.getAmount());
                    setText(text);
                }
            }
        });

        Text quantityText = new Text("Quantity:");
        quantityText.setTextAlignment(TextAlignment.CENTER);
        quantityText.setLineSpacing(10.0);

        final TextField textFieldQuantity = new TextField();
        textFieldQuantity.setAlignment(Pos.TOP_RIGHT);
        textFieldQuantity.setMaxSize(40, 20);

        textFieldQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldQuantity.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Button addButton = new Button("Add book to cart");
        addButton.setAlignment(Pos.CENTER);
        addButton.setPrefSize(150, 20);
        addButton.setLineSpacing(5.0);

        Text amountText = new Text();

        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                listView.refresh();

                                if (textFieldSearch.getText().isEmpty()) {

                                    ArrayList<Book> tmp = b.getArrayListBooks();

                                    ObservableList<Book> items = FXCollections.observableArrayList(tmp);
                                    listView.setItems(items);

                                } else {

                                    Book[] searchList = b.list(textFieldSearch.getText());

                                    ObservableList<Book> items = FXCollections.observableArrayList(searchList);
                                    listView.setItems(items);

                                }

                                textFieldSearch.setText("");


                            }

                        }

        );

        addButton.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent event) throws StringIndexOutOfBoundsException {

                                      if (textFieldQuantity.getText().equals("")) {
                                          Alert alert = new Alert(Alert.AlertType.ERROR);
                                          alert.setTitle("Error Dialog");
                                          alert.setHeaderText("No quantity declared!");
                                          alert.setContentText("Please enter a quantity");

                                          alert.showAndWait();
                                      } else {

                                          int quantity = Integer.parseInt(textFieldQuantity.getText());

                                          b.add(listView.getSelectionModel().getSelectedItem(), quantity);

                                          amountText.setText("Total: " + b.getNewPrice());

                                          ObservableList<CartItem> items = FXCollections.observableArrayList(cart);
                                          listCart.setItems(items);

                                      }

                                      textFieldQuantity.setText("");

                                  }

                              }

        );

        Button buyButton = new Button("Buy");
        buyButton.setAlignment(Pos.CENTER);
        buyButton.setPrefSize(150, 20);
        buyButton.setLineSpacing(5.0);

        buyButton.setOnAction(new EventHandler<ActionEvent>() {
                                  public void handle(ActionEvent event) throws StringIndexOutOfBoundsException {

                                      int[] tmp = new int[cart.size()];
                                      String answer = "Your payment is " + b.getNewPrice() + " Press OK to buy!";
                                      int i = 0;

                                      for (CartItem c : cart) {
                                          tmp = b.buy(c.getBook());

                                          if (tmp[i] == 1) {
                                              answer = "Book " + c.getBook().getTitle() + " is out of stock!";

                                          } else if (0 > c.getBook().getStock() - c.getAmount() && c.getBook().getStock() != 0) {
                                              answer = "We don´t have enough books of " + c.getBook().getTitle() + " in stock!";
                                          } else if (tmp[i] == 2) {
                                              answer = "Book " + c.getBook().getTitle() + " does not exist!";
                                          } else {

                                              i++;

                                          }

                                          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                          alert.setTitle("Confirmation Dialog");
                                          alert.setHeaderText("Press OK to confirm");
                                          alert.setContentText(answer);

                                          Optional<ButtonType> result = alert.showAndWait();
                                          if (result.get() == ButtonType.OK) {

                                          } else

                                          {

                                              alert.close();

                                          }

                                      }

                                  }

                              }

        );

        Button removeButton = new Button("Remove");
        removeButton.setAlignment(Pos.CENTER);
        removeButton.setPrefSize(150, 20);
        removeButton.setLineSpacing(5.0);

        removeButton.setOnAction(new EventHandler<ActionEvent>()

                                 {
                                     @Override
                                     public void handle(ActionEvent event) throws StringIndexOutOfBoundsException {

                                         b.removeBook(listCart.getSelectionModel().getSelectedItem());

                                         listCart.refresh();

                                         amountText.setText("Total: " + b.getNewPrice());

                                         ObservableList<CartItem> items = FXCollections.observableArrayList(b.getCart());
                                         listCart.setItems(items);

                                     }


                                 }

        );

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.TOP_LEFT);
        bottomBox.setPrefSize(800, 75);
        bottomBox.setPadding(new

                Insets(10, 10, 10, 10)

        );
        bottomBox.setSpacing(5);

        Text addNew = new Text("Add new book: ");

        TextField title = new TextField("Titel");
        TextField author = new TextField("Author");
        TextField price = new TextField("Price");
        price.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    price.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        TextField stock = new TextField("Stock");
        stock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    stock.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Button addNewBook = new Button();
        addNewBook.setText("Add");

        addNewBook.setOnAction(new EventHandler<ActionEvent>()

                               {
                                   @Override
                                   public void handle(ActionEvent event) {
                                       b.addNewBook(title.getText(), author.getText(), price.getText(), stock.getText());

                                       title.setText("Title");
                                       author.setText("Author");
                                       price.setText("Price");
                                       stock.setText("Stock");
                                   }
                               }

        );


        Scene scene = new Scene(borderPane, 1000, 700);

        topBox.getChildren().

                addAll(text1, textFieldSearch, btn);

        listBox.getChildren().

                addAll(listView, quantityText, textFieldQuantity, addButton);

        cartBox.getChildren().

                addAll(listCart, buyButton, removeButton, amountText);

        bottomBox.getChildren().

                addAll(addNew, title, author, price, stock, addNewBook);

        borderPane.setTop(topBox);
        borderPane.setLeft(listBox);
        borderPane.setBottom(bottomBox);
        borderPane.setRight(cartBox);


        primaryStage.setTitle("Book Store");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }

}