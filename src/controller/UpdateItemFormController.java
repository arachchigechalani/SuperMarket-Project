package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Item;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateItemFormController {
    public TextField txtItemId;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TextField txtItemType;

    public JFXButton btnUpdateItem;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[I][-][0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{3,}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{1,20}$");
    Pattern qtyPattern = Pattern.compile("^[0-9]{1,10}$");
    Pattern itemTypePattern = Pattern.compile("^[A-z ]{2,}$");


    public void initialize(){
        btnUpdateItem.setDisable(true);
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
        Object response = ValidationUtil.validate(map,btnUpdateItem);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void btnUpdateItem_OnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdate = ItemController.updateItem(new Item(
                    txtItemId.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQty.getText()),
                    txtItemType.getText()
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

    public void setEvent(TableLoadEvent event) {
        this.event=event;
    }

}
