package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SaveCustomerFormController {
    public JFXButton btnSave;
    public JFXButton btnClear;

    public TextField txtCustId;
    public TextField txtCustTitle;
    public TextField txtCustGender;
    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[C][0-9]{3}$");
    Pattern titlePattern = Pattern.compile("^[A-z][0-9]$");
    Pattern genderPattern = Pattern.compile("^[A-z ]{1,20}$");

    public void initialize() {
        btnSave.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtCustId, idPattern);
        map.put(txtCustTitle, titlePattern);
        map.put(txtCustGender, genderPattern);

    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        try {
            Customer customer = new Customer(
                    txtCustId.getText(),
                    txtCustTitle.getText(),
                    txtCustGender.getText()
            );
            boolean isSave = CustomerController.saveCustomer(customer);

            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION,"Success").show();
                event.loadData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();

            }


        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity...").show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnClear_OnAction(ActionEvent actionEvent) {

    }

    public void setEvent(TableLoadEvent event){
        this.event = event;
    }
}
