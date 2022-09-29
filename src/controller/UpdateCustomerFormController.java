package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Customer;

import java.sql.SQLException;

public class UpdateCustomerFormController {
    public TextField txtCustId;
    public TextField txtCustTitle;
    public TextField txtCustGender;
    public JFXButton btnUpdate;

    private TableLoadEvent event;


    public void initialize(){

    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdate = CustomerController.updateCustomer(new Customer(
                    txtCustId.getText(),
                    txtCustTitle.getText(),
                    txtCustGender.getText()
            ));

            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Success").show();
                event.loadData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void setEvent(TableLoadEvent event){
        this.event = event;
    }

}
