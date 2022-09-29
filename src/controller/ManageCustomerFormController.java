package controller;

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
import model.Customer;
import views.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomerFormController implements TableLoadEvent {
    public AnchorPane addCustomerContext;

    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colTitle;
    public TableColumn colGender;
    public TableColumn colUpdate;

    public Button btnAddCustomer;
    public TextField txtSearch;
    public Button btnDelete;

    private Button button;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("custTitle"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("custGender"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblCustomer.getColumns().setAll(colId,colTitle,colGender,colUpdate);

        //set data to table
        loadData();

    }



    public void showUpdateForm(CustomerTM table){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/UpdateCustomerForm.fxml"));
            Parent parent = loader.load();
            UpdateCustomerFormController controller = loader.<UpdateCustomerFormController>getController();
            controller.txtCustId.setText(table.getCustId());
            controller.txtCustTitle.setText(table.getCustTitle());
            controller.txtCustGender.setText(table.getCustGender());
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void btnAddCustomer_OnAction(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/SaveCustomerForm.fxml"));
            Parent parent = loader.load();
            SaveCustomerFormController controller = loader.<SaveCustomerFormController>getController();
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    ObservableList<CustomerTM> tableData = FXCollections.observableArrayList();
    @Override
    public void loadData() {
        //set data to table

        try {
            List<Customer> allCustomers = CustomerController.getAllCustomers();
            ArrayList<CustomerTM> customerTMs=new ArrayList<>();
            for (Customer customer : allCustomers) {
                Button update = new Button("Update");
                button = new Button("Update");
                update.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println("working");
                        CustomerTM item = tblCustomer.getSelectionModel().getSelectedItem();
                        showUpdateForm(item);
                    }
                });
                customerTMs.add(new CustomerTM(
                        customer.getCustId(),
                        customer.getCustTitle(),
                        customer.getCustGender(),
                        update
                ));
            }
            tableData.clear();
            tableData.addAll(customerTMs);
            tblCustomer.setItems(tableData);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }
    

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        try {
            String custId = tblCustomer.getSelectionModel().getSelectedItem().getCustId();
            boolean isDeleted = CustomerController.deleteCustomer(custId);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Successfully..").show();
                loadData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Deleted..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void txtSearch_OnAction(ActionEvent actionEvent) {
        try {
            List<Customer> customers = CustomerController.searchCustomer(txtSearch.getText());
            ArrayList<CustomerTM> customerTMs=new ArrayList<>();
            for (Customer customer : customers) {
                Button update = new Button("Update");
                button = new Button("Update");
                update.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println("working");
                        CustomerTM item = tblCustomer.getSelectionModel().getSelectedItem();
                        showUpdateForm(item);
                    }
                });

                customerTMs.add(new CustomerTM(
                        customer.getCustId(),
                        customer.getCustTitle(),
                        customer.getCustGender(),
                        update

                ));
            }
            tableData.clear();
            tableData.addAll(customerTMs);
            tblCustomer.setItems(tableData);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


}

