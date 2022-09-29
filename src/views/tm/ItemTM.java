package views.tm;

import javafx.scene.control.Button;

public class ItemTM {
    private String itemId;
    private String description;
    private double unitPrice;
    private int qty;
    private Button button;

    public ItemTM() {
    }

    public ItemTM(String itemId, String description, double unitPrice, int qty, String itemType, Button button) {
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.itemType = itemType;
        this.button = button;

    }

    private String itemType;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", itemType='" + itemType + '\'' +
                '}';
    }
}
