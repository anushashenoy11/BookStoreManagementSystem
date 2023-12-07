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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegisterController implements Initializable {
    @FXML
    private Scene loginScene;
    @FXML
    private ImageView shieldImageView;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private Label confirmpasswordLabel;
    @FXML
    private Button backButton;
    @FXML
    private TextField firstname;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField PhoneTextField;

    public RegisterController() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void registerButtonOnAction(ActionEvent event) {
        if (this.setPasswordField.getText().equals(this.confirmPasswordField.getText())) {
            this.registerUser();
            this.confirmpasswordLabel.setText("");
        } else {
            this.confirmpasswordLabel.setText("Passwords does not match");
            this.registrationMessageLabel.setText("Registration failed!");
        }

    }

    public void registerUser() {
        try {
            Socket clientSocket = new Socket("localhost", 1234);

            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    try {
                        String name = this.firstname.getText();
                        String email = this.emailTextField.getText();
                        String password = this.setPasswordField.getText();
                        String phone = this.PhoneTextField.getText();
                        String registrationRequest = "REGISTER " + name + " " + email + " " + phone + " " + password;
                        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                            if (this.isValidEmail(email) && this.isValidPhoneNumber(phone)) {
                                out.println(registrationRequest);
                                String response = in.readLine();
                                if (response.equals("REGISTRATION_SUCCESS")) {
                                    this.registrationMessageLabel.setText("User has been registered successfully!");
                                } else if (response.equals("REGISTRATION_FAILURE")) {
                                    this.registrationMessageLabel.setText("Registration failed. Email is already in use.");
                                } else {
                                    this.registrationMessageLabel.setText("Unknown response from the server.");
                                }
                            } else {
                                this.registrationMessageLabel.setText("Invalid Email or Phone number format.");
                            }
                        } else {
                            this.registrationMessageLabel.setText("Name, email, and password cannot be empty.");
                        }
                    } catch (Throwable var13) {
                        try {
                            in.close();
                        } catch (Throwable var12) {
                            var13.addSuppressed(var12);
                        }

                        throw var13;
                    }

                    in.close();
                } catch (Throwable var14) {
                    try {
                        out.close();
                    } catch (Throwable var11) {
                        var14.addSuppressed(var11);
                    }

                    throw var14;
                }

                out.close();
            } catch (Throwable var15) {
                try {
                    clientSocket.close();
                } catch (Throwable var10) {
                    var15.addSuppressed(var10);
                }

                throw var15;
            }

            clientSocket.close();
        } catch (IOException var16) {
            var16.printStackTrace();
        }

    }

    public void backToLogin() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("login.fxml"));

        try {
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)this.backButton.getScene().getWindow();
            stage.setScene(new Scene(root, 520.0, 464.0));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{10}");
    }
}
