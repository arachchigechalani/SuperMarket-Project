package views.tm;

import javafx.scene.control.Button;

public class CustomerTM {
    private String  custId;
    private String custTitle;
    private String custGender;
    private Button button;

    public CustomerTM(String itemId, String description, double unitPrice, int qtyOnHand, int qtyInStock, String itemType, Button update) {
    }

    public CustomerTM(String custId, String custTitle, String custGender, Button button) {
        this.custId = custId;
        this.custTitle = custTitle;
        this.custGender = custGender;
        this.button = button;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustTitle() {
        return custTitle;
    }

    public void setCustTitle(String custTitle) {
        this.custTitle = custTitle;
    }

    public String getCustGender() {
        return custGender;
    }

    public void setCustGender(String custGender) {
        this.custGender = custGender;
    }

    public Button getButton() { return button;  }

    public void setButton(Button button) { this.button = button;  }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "custId='" + custId + '\'' +
                ", custTitle='" + custTitle + '\'' +
                ", custGender='" + custGender + '\'' +
                ", button=" + button +
                '}';
    }
}
