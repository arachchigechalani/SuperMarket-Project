package model;

public class Customer {
    private String  custId;
    private String custTitle;
    private String custGender;

    public Customer(String text, String txtDescriptionText, String txtUnitPriceText, String txtQtyText, String txtItemTypeText) {
    }

    public Customer(String custId, String custTitle, String custGender) {
        this.custId = custId;
        this.custTitle = custTitle;
        this.custGender = custGender;
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

    @Override
    public String toString() {
        return "Customer{" +
                "custId='" + custId + '\'' +
                ", custTitle='" + custTitle + '\'' +
                ", custGender='" + custGender + '\'' +
                '}';
    }
}
