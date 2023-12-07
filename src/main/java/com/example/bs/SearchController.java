//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchController implements Initializable {
    @FXML
    private TableView<SearchModel> bookTable;
    @FXML
    private TableColumn<SearchModel, BigInteger> bookID;
    @FXML
    private TableColumn<SearchModel, String> bookTitle;
    @FXML
    private TableColumn<SearchModel, String> bookAuthor;
    @FXML
    private TableColumn<SearchModel, Integer> bookPrice;
    @FXML
    private TableColumn<SearchModel, Integer> bookStock;
    @FXML
    private TableColumn<SearchModel, String> bookDescription;
    @FXML
    private TextField keywords;
    @FXML
    private Button back;
    ObservableList<SearchModel> SearchmodelObservableList = FXCollections.observableArrayList();
    private PrintWriter out;
    private BufferedReader in;

    public SearchController() {
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
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void backtomain() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("login.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.back.getScene().getWindow();
            stage.setScene(new Scene(root, 520.0, 464.0));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}
