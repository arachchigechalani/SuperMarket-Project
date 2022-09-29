package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;
import views.tm.OrderTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {
    public AnchorPane manageOrderContext;
    public TableView<OrderTM> tblPlaceOrder;
    public TableColumn colId;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colItemType;
    public TableColumn colUnitPrice;
    public TableColumn colTotalAmount;
    public ComboBox<String> cmbCustomerId;
    public TextField txtCustomerTitle;
    public TextField txtGender;
    public ComboBox<String> cmbItemId;
    public TextField txtItemDesc;
    public TextField txtItemPrice;
    public TextField txtItemQty;
    public TextField txtItemType;
    public TextField txtOrderQty;
    public JFXButton btnAddToCart;
    public Label lblOrderId;
    public JFXButton btnPlaceOrder;
    public TextField txtTotalAmount;

    public static CashierMenuFormController menu;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        tblPlaceOrder.getColumns().setAll(colId, colDesc, colQty, colItemType, colUnitPrice, colTotalAmount);

        try {
            loadCustomerIds();
            loadItemIds();
            lastOrderId();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        /*------------------addListners--------------------*/
        cmbCustomerId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        cmbItemId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setItemData(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        /*--------------------------------------------------*/
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = new OrderController().getCustomerIds();
        cmbCustomerId.getItems().addAll(customerIds);
    }

    private void setCustomerData(String custId) throws SQLException, ClassNotFoundException {
        Customer c1 = (Customer) new OrderController().getCustomerById(custId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtCustomerTitle.setText(c1.getCustTitle());
            txtGender.setText(c1.getCustGender());
        }
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = OrderController.getAllItemIds();
        cmbItemId.getItems().addAll(itemIds);
    }

    private void setItemData(String itemId) throws SQLException, ClassNotFoundException {
        Item i1 = new OrderController().getItemById(itemId);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtItemDesc.setText(i1.getDescription());
            txtItemPrice.setText(String.valueOf(i1.getUnitPrice()));
            int qty=i1.getQty();
            for (OrderTM tm : obList) {
                if(i1.getItemId().equals(tm.getId())){
                    qty-=tm.getQty();
                }
            }

            txtItemQty.setText(String.valueOf(qty));
            txtItemType.setText(i1.getItemType());
        }

    }

    public void lastOrderId() {


        try {
            String orderId = OrderController.getLastOrderId();//O-001
            String finalId = "O-001";

            if (orderId != null) {
                String[] splitString = orderId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "O-00" + id;
                } else if (id < 100) {
                    finalId = "O-0" + id;
                } else {
                    finalId = "O-" + id;
                }
                lblOrderId.setText(finalId);

            } else {
                lblOrderId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }


    ObservableList<OrderTM> obList= FXCollections.observableArrayList();

    public void btnAddToCart_OnAction(ActionEvent actionEvent) {
        addToCart();
    }

    public void addToCart() {
        if (txtOrderQty.getText().isEmpty() || Integer.parseInt(txtOrderQty.getText()) > Integer.parseInt(txtItemQty.getText())){
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            return;
        }

        String itemId = cmbItemId.getSelectionModel().getSelectedItem();
        String itemDesc = txtItemDesc.getText();
        int orderQty = Integer.parseInt(txtOrderQty.getText());
        String itemType = txtItemType.getText();
        double itemPrice = Double.parseDouble(txtItemPrice.getText());
        double itemTotal = Double.parseDouble(txtItemPrice.getText()) * Integer.parseInt(txtOrderQty.getText());

        OrderTM orderTM = new OrderTM(
                itemId,
                itemDesc,
                orderQty,
                itemType,
                itemPrice,
                itemTotal
        );
        int qty=Integer.parseInt(txtItemQty.getText())-orderQty;
        txtItemQty.setText(Integer.toString(qty));
        int index = findItemOnTable(itemId);
        if (index ==-1){
            obList.add(orderTM);
            tblPlaceOrder.setItems(obList);
        }else {
            OrderTM table =obList.get(index);
            int newQty = table.getQty() + orderQty;
            double newTotal = table.getTotalAmount() + itemTotal;
            orderTM.setQty(newQty);
            orderTM.setTotalAmount(newTotal);
            obList.remove(index);
            obList.add(orderTM);
            tblPlaceOrder.setItems(obList);
        }
        calculateCost();

    }

    public int findItemOnTable(String id){
        for (int i=0; i<obList.size(); i++){
            if (obList.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {

        String orderId = lblOrderId.getText();
        String custId = cmbCustomerId.getSelectionModel().getSelectedItem();

        try {

            BorderPane borderPane = (BorderPane) menu.cashierMenuFormContext.getChildren().get(0);
            AnchorPane topPane = (AnchorPane) borderPane.getChildren().get(0);

        }catch (Exception e){
            System.out.println("");
        }
        Pane topPane = null;
        try {
            String date = ((Label) topPane.getChildren().get(6)).getText();
            String time = ((Label) topPane.getChildren().get(7)).getText();

        }catch (Exception e){
            System.out.println("");
        }



        /*String date = CashierMenuFormController.getDate();
        String time = CashierMenuFormController.getTime();*/
        double totalAmount = Double.parseDouble(txtTotalAmount.getText().split(" ")[0]);

        List<OrderDetails> details = new ArrayList<>();
        for (OrderTM item : tblPlaceOrder.getItems()) {
            details.add(new OrderDetails(
                    orderId,
                    item.getId(),
                    item.getQty(),
                    item.getUnitPrice(),
                    item.getTotalAmount()
            ));

        }
        /*Order order=new Order(
                orderId,
                custId,
                date,
                time,
                totalAmount,
                details
        );*/


        String date = null;
        String time = null;
        boolean isSuccess = OrderController.placeOrder(new Order(
                orderId,
                custId,
                date,
                time,
                totalAmount,
                details
        ));

        if (isSuccess) {
            new Alert(Alert.AlertType.INFORMATION, "Success").show();
            lastOrderId();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();

        }
    }

    public void calculateCost(){
        double cost = 0;
        for (OrderTM item : obList) {
            cost+= item.getTotalAmount();
        }
        txtTotalAmount.setText(cost+" /=");
    }
}
