package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {

    public TextField txtName;
    public PasswordField txtPassword;
    public AnchorPane logInContext;
    private AnchorPane cashierMenuFormContext;

    public void closeStage(AnchorPane customerOrderContext){
        this.cashierMenuFormContext = customerOrderContext;
    }
    Stage stage;

    public void closeOnAction(ActionEvent actionEvent) {
        stage = (Stage) logInContext.getScene().getWindow();
        stage.close();
    }

    public void menuFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logInContext.getScene().getWindow();
        stage.close();

        if (txtName.getText().equalsIgnoreCase("q" ) && txtPassword.getText().equalsIgnoreCase("q")) {

            URL resource = getClass().getResource("../views/CashierMenuForm.fxml");
            Parent load= FXMLLoader.load(resource);
            Scene scene=new Scene(load);
            stage.setTitle("CashierMenu Form");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("login fail");
        }
    }


    //private AnchorPane administratorFormContext;

    /*public void closeStage(AnchorPane customerOrderContext){
        this.administratorFormContext = customerOrderContext;
    }
    Stage stage;*/

    /*public void closeOnAction(ActionEvent actionEvent) {
        stage = (Stage) logInContext.getScene().getWindow();
        stage.close();
    }*/

    /*public void administratorFormOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logInContext.getScene().getWindow();
        stage.close();

     if (txtName.getText().equalsIgnoreCase("q" ) && txtPassword.getText().equalsIgnoreCase("q")) {
           // Stage window = (Stage) administratorFormContext .getScene().getWindow();
            //window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdministratorForm.fxml"))));
                URL resource = getClass().getResource("../view/AdministratorForm.fxml");
                Parent load= FXMLLoader.load(resource);
                Scene scene=new Scene(load);
                stage.setTitle("Administrator Form");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
        } else {

           // Stage window = (Stage) logInContext.getScene().getWindow();
            //window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LogInForm.fxml"))));
            System.out.println("login fail");
        }
    }*/
}
