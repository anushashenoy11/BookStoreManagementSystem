//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

import java.math.BigInteger;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CartController implements Initializable {
    @FXML
    private TableView<CartItemModel> cartTable;
    @FXML
    private TableColumn<CartItemModel, BigInteger> cartid;
    @FXML
    private TableColumn<CartItemModel, String> cartTitle;
    @FXML
    private TableColumn<CartItemModel, Integer> cartPrice;
    @FXML
    public Button Back;
    @FXML
    private Label cost;
    @FXML
    public Button buy;
    private ObservableList<CartItemModel> cartItems = FXCollections.observableArrayList();

    public CartController() {
    }

    public void setCartData(List<Integer> cartID, List<String> cartTitles, List<Integer> cartPrices) {
        this.cartItems.clear();

        for(int i = 0; i < cartTitles.size(); ++i) {
            this.cartItems.add(new CartItemModel((Integer)cartID.get(i), (String)cartTitles.get(i), (Integer)cartPrices.get(i)));
        }

        this.cartTable.setItems(this.cartItems);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cartid.setCellValueFactory(new PropertyValueFactory("bookID"));
        this.cartTitle.setCellValueFactory(new PropertyValueFactory("title"));
        this.cartPrice.setCellValueFactory(new PropertyValueFactory("price"));
        this.populateCartTable();
        this.updateTotalCostLabel();
    }

    private void updateTotalCostLabel() {
        int totalCost = 0;

        CartItemModel cartItem;
        for(Iterator var2 = this.cartItems.iterator(); var2.hasNext(); totalCost += cartItem.getPrice()) {
            cartItem = (CartItemModel)var2.next();
        }

        this.cost.setText(String.valueOf(totalCost));
    }

    private void populateCartTable() {
        List<String> cartTitles = UserController.getCartTitles();
        List<Integer> cartPrices = UserController.getCartPrices();
        List<Integer> cartIDs = UserController.getCartIDs();
        this.cartItems.clear();

        for(int i = 0; i < cartTitles.size(); ++i) {
            this.cartItems.add(new CartItemModel((Integer)cartIDs.get(i), (String)cartTitles.get(i), (Integer)cartPrices.get(i)));
        }

        this.cartTable.setItems(this.cartItems);
    }

    public void backtomain() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("mainuser.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.Back.getScene().getWindow();
            stage.setScene(new Scene(root, 1080.0, 655.0));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void thankyou() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("thank.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.Back.getScene().getWindow();
            stage.setScene(new Scene(root, 520.0, 464.0));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}
