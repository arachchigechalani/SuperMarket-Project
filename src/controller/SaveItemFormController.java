package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Item;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SaveItemFormController {
    public AnchorPane addItemFormContext;
    public JFXButton btnSaveItem;
    public JFXButton btnClear;

    public TextField txtItemId;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TextField txtItemType;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[I][-][0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{3,}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{1,20}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,10}$");
    Pattern itemTypePattern = Pattern.compile("^[A-z ]{2,}$");

    public void initialize() {
        btnSaveItem.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtItemId, idPattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtUnitPrice, unitPricePattern);
        map.put(txtQty, qtyPattern);
        map.put(txtItemType, itemTypePattern);
    }

    public void textFields_Key_Realeased(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveItem);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void btnSaveItem_OnAction(ActionEvent actionEvent) {
        try {
            Item item = new Item(
                    txtItemId.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQty.getText()),
                    txtItemType.getText()
            );

            if (ItemController.saveItem(item)) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                event.loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Duplicate Entity...").show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnClear_OnAction(ActionEvent actionEvent) {

    }

    public void setEvent(TableLoadEvent event) {
        this.event = event;
    }

}