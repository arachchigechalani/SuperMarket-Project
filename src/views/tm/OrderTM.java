package views.tm;

public class OrderTM {
    private String id;
    private String description;
    private int qty;
    private String itemType;
    private double unitPrice;
    private double totalAmount;

    public OrderTM() {
    }

    public OrderTM(String id, String description, int qty, String itemType, double unitPrice, double totalAmount) {
        this.id = id;
        this.description = description;
        this.qty = qty;
        this.itemType = itemType;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
