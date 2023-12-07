//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserController implements Initializable {
    @FXML
    public TableView<SearchModel> bookTable;
    @FXML
    public TableColumn<SearchModel, BigInteger> bookID;
    @FXML
    public TableColumn<SearchModel, String> bookTitle;
    @FXML
    public TableColumn<SearchModel, String> bookAuthor;
    @FXML
    public TableColumn<SearchModel, Integer> bookPrice;
    @FXML
    public TableColumn<SearchModel, Integer> bookStock;
    @FXML
    public TableColumn<SearchModel, String> bookDescription;
    @FXML
    public TextField keywords;
    @FXML
    public Button logout;
    ObservableList<SearchModel> SearchmodelObservableList = FXCollections.observableArrayList();
    private PrintWriter out;
    private BufferedReader in;
    private static List<String> cartTitles = new ArrayList();
    private static List<Integer> cartPrices = new ArrayList();
    private static List<Integer> cartID = new ArrayList();

    public UserController() {
    }

    public static List<String> getCartTitles() {
        return cartTitles;
    }

    public static List<Integer> getCartPrices() {
        return cartPrices;
    }

    public static List<Integer> getCartIDs() {
        return cartID;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Thread networkThread = new Thread(() -> {
                try {
                    Socket clientSocket = new Socket("localhost", 1234);
                    this.out = new PrintWriter(clientSocket.getOutputStream(), true);
                    this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    System.out.println("Client has connected to the server");
                    String query = "SELECT bookId, bookTitle, bookAuthor, bookPrice, bookStock, bookDescription FROM book";
                    this.out.println(query);

                    String response;
                    while((response = this.in.readLine()) != null) {
                        System.out.println("Received response: " + response);
                        String[] data = response.split("\t");
                        if (data.length == 6) {
                            String bookId = data[0];
                            String bookTitle = data[1];
                            String bookAuthor = data[2];
                            String bookPrice = data[3];
                            String bookStock = data[4];
                            String bookDescription = data[5];
                            Platform.runLater(() -> {
                                this.SearchmodelObservableList.add(new SearchModel(new BigInteger(bookId), bookTitle, bookAuthor, Integer.parseInt(bookPrice), Integer.parseInt(bookStock), bookDescription));
                            });
                        }
                    }
                } catch (Exception var11) {
                    var11.printStackTrace();
                }

            });
            networkThread.setDaemon(true);
            networkThread.start();
            this.bookID.setCellValueFactory(new PropertyValueFactory("bookID"));
            this.bookTitle.setCellValueFactory(new PropertyValueFactory("bookTitle"));
            this.bookAuthor.setCellValueFactory(new PropertyValueFactory("bookAuthor"));
            this.bookPrice.setCellValueFactory(new PropertyValueFactory("bookPrice"));
            this.bookStock.setCellValueFactory(new PropertyValueFactory("bookStock"));
            this.bookDescription.setCellValueFactory(new PropertyValueFactory("bookDescription"));
            FilteredList<SearchModel> filteredData = new FilteredList(this.SearchmodelObservableList, (b) -> {
                return true;
            });
            this.keywords.textProperty().addListener((observable, oldvalue, newvalue) -> {
                filteredData.setPredicate((SearchModel) -> {
                    if (!newvalue.isEmpty() && !newvalue.isBlank() && newvalue != null) {
                        String searchKeyword = newvalue.toLowerCase();
                        if (SearchModel.getBookTitle().toLowerCase().contains(searchKeyword)) {
                            return true;
                        } else if (SearchModel.getBookDescription().toLowerCase().contains(searchKeyword)) {
                            return true;
                        } else if (SearchModel.getBookAuthor().toLowerCase().contains(searchKeyword)) {
                            return true;
                        } else {
                            return SearchModel.getBookPrice().toString().contains(searchKeyword);
                        }
                    } else {
                        return true;
                    }
                });
            });
            SortedList<SearchModel> sortedData = new SortedList(filteredData);
            sortedData.comparatorProperty().bind(this.bookTable.comparatorProperty());
            this.bookTable.setItems(sortedData);
            this.setupDoubleClickEvent();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void setupDoubleClickEvent() {
        this.bookTable.setRowFactory((tv) -> {
            TableRow<SearchModel> row = new TableRow();
            row.setOnMouseClicked((event) -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    SearchModel rowData = (SearchModel)row.getItem();
                    this.addToCart(rowData);
                }

            });
            return row;
        });
    }

    private void addToCart(SearchModel selectedBook) {
        cartID.add(selectedBook.getBookID().intValue());
        cartTitles.add(selectedBook.getBookTitle());
        cartPrices.add(selectedBook.getBookPrice());
        PrintStream var10000 = System.out;
        String var10001 = selectedBook.getBookTitle();
        var10000.println("Book added to cart: " + var10001 + " - Price: " + selectedBook.getBookPrice());
        System.out.println(cartTitles);
        System.out.println(cartPrices);
    }

    public void CartView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("cart.fxml"));
            Parent root = (Parent)loader.load();
            CartController cartController = (CartController)loader.getController();
            cartController.setCartData(cartID, cartTitles, cartPrices);
            Stage stage = (Stage)this.logout.getScene().getWindow();
            stage.setScene(new Scene(root, 700.0, 693.0));
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void backToLogin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("login.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.logout.getScene().getWindow();
            stage.setScene(new Scene(root, 520.0, 464.0));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public void setCartArraysToNull() {
        cartTitles.clear();
        cartPrices.clear();
        cartID.clear();
    }
}
