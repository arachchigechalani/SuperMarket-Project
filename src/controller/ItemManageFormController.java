package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import views.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemManageFormController implements TableLoadEvent {
    public AnchorPane itemManageFormContext;
    public JFXButton btnAddItem;
    public JFXButton btnDeleteItem;

    public TableView<ItemTM> tblItem;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;

    public TableColumn colItemType;
    public TableColumn colUpdate;
    public TextField txtSearchItem;

    public void initialize(){
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblItem.getColumns().setAll(colItemId,colDescription,colUnitPrice,colQty,colItemType,colUpdate);

        //set data to table
        loadData();

    }


    public void showUpdateForm(ItemTM table){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/UpdateItemForm.fxml"));
            Parent parent = loader.load();
            UpdateItemFormController controller = loader.<UpdateItemFormController>getController();
            controller.txtItemId.setText(table.getItemId());
            controller.txtDescription.setText(table.getDescription());
            controller.txtUnitPrice.setText(String.valueOf(table.getUnitPrice()));
            controller.txtQty.setText(String.valueOf(table.getQty()));
            controller.txtItemType.setText(table.getItemType());
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void btnAddItem_OnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/SaveItemForm.fxml"));
            Parent parent = loader.load();
            SaveItemFormController controller = loader.<SaveItemFormController>getController();
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void btnDeleteItem_OnAction(ActionEvent actionEvent) {
        try {
            String itemId = tblItem.getSelectionModel().getSelectedItem().getItemId();
            boolean isDeleted = ItemController.deleteItem(itemId);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully..").show();
                loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Deleted..").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void loadData() {
        //set data to table
        try {
            List<Item> allItems = ItemController.getAllItems();
            ArrayList<ItemTM> itemTMs=new ArrayList<>();
            for (Item item : allItems) {
                Button update = new Button("Update");
                update.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println("working");
                        ItemTM item = tblItem.getSelectionModel().getSelectedItem();
                        showUpdateForm(item);
                    }
                });
                itemTMs.add(new ItemTM(
                        item.getItemId(),
                        item.getDescription(),
                        item.getUnitPrice(),
                        item.getQty(),
                        item.getItemType(),
                        update

                ));
            }
            tableData.clear();
            tableData.addAll(itemTMs);
            tblItem.setItems(tableData);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    ObservableList<ItemTM> tableData = FXCollections.observableArrayList();

}
