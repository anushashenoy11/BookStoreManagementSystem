//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField emaill;
    @FXML
    private PasswordField passwordd;
    @FXML
    private Hyperlink signup;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public LoginController() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void signupAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("register.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.signup.getScene().getWindow();
            stage.setScene(new Scene(root, 520.0, 599.0));
            stage.setResizable(false);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public void guestAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("search.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.signup.getScene().getWindow();
            stage.setScene(new Scene(root, 1080.0, 645.0));
            stage.setResizable(false);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public void mainuser() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("mainuser.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.signup.getScene().getWindow();
            stage.setScene(new Scene(root, 1080.0, 655.0));
            stage.setResizable(false);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void loginButtonOnAction(ActionEvent event) {
        String enteredEmail = this.emaill.getText();
        String enteredPassword = this.passwordd.getText();
        if (this.isInputValid(enteredEmail, enteredPassword)) {
            this.sendLoginRequestToServer(enteredEmail, enteredPassword);
        }

    }

    private boolean isInputValid(String email, String password) {
        if (!email.isBlank() && !password.isBlank()) {
            return true;
        } else {
            this.loginMessageLabel.setText("Please Enter Email and Password");
            return false;
        }
    }

    public void sendLoginRequestToServer(String email, String password) {
        try {
            Socket clientSocket = new Socket("localhost", 1234);

            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    try {
                        out.println("LOGIN " + email + " " + password);
                        String response = in.readLine();
                        if (response.equals("LOGIN_SUCCESS")) {
                            this.loginMessageLabel.setText("Login successful");
                            this.mainuser();
                        } else if (response.equals("LOGIN_FAILURE")) {
                            this.loginMessageLabel.setText("Invalid Login. Please Try again.");
                        } else {
                            this.loginMessageLabel.setText("Unknown response from the server.");
                        }
                    } catch (Throwable var11) {
                        try {
                            in.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }

                        throw var11;
                    }

                    in.close();
                } catch (Throwable var12) {
                    try {
                        out.close();
                    } catch (Throwable var9) {
                        var12.addSuppressed(var9);
                    }

                    throw var12;
                }

                out.close();
            } catch (Throwable var13) {
                try {
                    clientSocket.close();
                } catch (Throwable var8) {
                    var13.addSuppressed(var8);
                }

                throw var13;
            }

            clientSocket.close();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

    }
}
