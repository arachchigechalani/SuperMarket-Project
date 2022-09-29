package controller;

import com.sun.deploy.panel.RuleSetViewerDialog;
import com.sun.deploy.uitoolkit.impl.awt.ui.SwingConsoleWindow;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class CashierMenuFormController {
    public AnchorPane cashierMenuFormContext;
    public AnchorPane cashierDashboardFormContext;
    public Label lblDate;
    public Label lblTime;

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;
    private RuleSetViewerDialog addCustomerContext;
    private RuleSetViewerDialog manageOrderContext;
    private RuleSetViewerDialog allReportFormContext;
    private SwingConsoleWindow CDashboardContext;
    private RuleSetViewerDialog systemReportFormContext;
    private RuleSetViewerDialog itemManageFormContext;

    static ArrayList<Customer> customerList = new ArrayList();

    public void initialize(){
        loadDateAndTime();

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(-190);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-190);

            slide.setOnFinished(this::handle);
        });


        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-190);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished(this::handle2);
        });
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    private void handle(ActionEvent e) {
        Menu.setVisible(false);
        MenuBack.setVisible(true);
    }

    private void handle2(ActionEvent e) {
        Menu.setVisible(true);
        MenuBack.setVisible(false);
    }

    public void CDashboardFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/CashierAllReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashboardFormContext.getChildren().clear();
        cashierDashboardFormContext.getChildren().add(load);

        try {
            if (CDashboardContext.isVisible()){

            }else {
                addCustomerContext.setVisible(false);
                manageOrderContext.setVisible(false);
                systemReportFormContext.setVisible(false);
                itemManageFormContext.setVisible(false);
                CDashboardContext.setVisible(true);

            }
        }catch(NullPointerException e){
            System.out.println("");
        }
    }

    public void itemManageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/ItemManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashboardFormContext.getChildren().clear();
        cashierDashboardFormContext.getChildren().add(load);

        try {
            if (itemManageFormContext.isVisible()){

            }else {
                addCustomerContext.setVisible(false);
                manageOrderContext.setVisible(false);
                systemReportFormContext.setVisible(false);
                CDashboardContext.setVisible(false);
                itemManageFormContext.setVisible(true);

            }
        }catch(NullPointerException e){
            System.out.println("");
        }
    }

    public void systemReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashboardFormContext.getChildren().clear();
        cashierDashboardFormContext.getChildren().add(load);

        try {
            if (systemReportFormContext.isVisible()){

            }else {
                addCustomerContext.setVisible(false);
                manageOrderContext.setVisible(false);
                itemManageFormContext.setVisible(false);
                CDashboardContext.setVisible(false);
                systemReportFormContext.setVisible(true);

            }
        }catch(NullPointerException e){
            System.out.println("");
        }
    }

    public void manageCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/ManageCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashboardFormContext.getChildren().clear();
        cashierDashboardFormContext.getChildren().add(load);

        try {
            if (addCustomerContext.isVisible()){

            }else {
                systemReportFormContext.setVisible(false);
                manageOrderContext.setVisible(false);
                itemManageFormContext.setVisible(false);
                CDashboardContext.setVisible(false);
                addCustomerContext.setVisible(true);

            }
        }catch(NullPointerException e){
            System.out.println("");
        }
    }

    public void manageOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../views/PlaceOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierDashboardFormContext.getChildren().clear();
        cashierDashboardFormContext.getChildren().add(load);

        try {
            if (manageOrderContext.isVisible()){

            }else {
                systemReportFormContext.setVisible(false);
                addCustomerContext.setVisible(false);
                itemManageFormContext.setVisible(false);
                CDashboardContext.setVisible(false);
                manageOrderContext.setVisible(true);

            }
        }catch(NullPointerException e){
            System.out.println("");
        }
    }

    public  String getDate(){
        String date = lblDate.getText();
        return date;
    }

    public  String getTime(){
        String time = lblTime.getText();
        return time;
    }


    public void backUp(ActionEvent actionEvent) throws IOException {
        Stage stage;
        stage = (Stage) cashierMenuFormContext.getScene().getWindow();
        stage.close();

        Parent load = FXMLLoader.load(getClass().getResource("../views/LoginForm.fxml"));
        Scene scene = new Scene(load);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
