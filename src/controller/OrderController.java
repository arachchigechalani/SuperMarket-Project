package controller;

import db.DbConnection;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    public static List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        ResultSet rst = con.prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> customersId = new ArrayList<>();
        while (rst.next()) {
            customersId.add(
                    rst.getString("custId")
            );
        }
        return customersId;
    }


    public static List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> Ids = new ArrayList<>();
        while (rst.next()) {
            Ids.add(
                    rst.getString("itemId")
            );
        }
        return Ids;
    }

    public static Object getCustomerById(String custId) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Customer WHERE custId=?");
        pstm.setObject(1, custId);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;
    }

    public static Item getItemById(String itemId) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Item WHERE itemId=?");
        pstm.setObject(1, itemId);

        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return rst.getString("orderId");
        }
        return null;
    }

    public static boolean placeOrder(Order order)  {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement("INSERT INTO `Order` VALUES (?,?,?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getCustId());
            pstm.setObject(3, order.getOrderDate());
            pstm.setObject(4, order.getOrderTime());
            pstm.setObject(5, order.getTotalAmount());

            if (pstm.executeUpdate() > 0){
                if (addOrderDetails(order.getDetails())){
                    if (updateQty(order.getDetails())){
                        con.commit();
                    }else{
                        con.rollback();
                        return false;
                    }
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

         finally {
            try {
                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;

    }

    private static boolean addOrderDetails(List<OrderDetails> list) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        for (OrderDetails details : list) {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO `Order Item` VALUES (?,?,?,?,?)");
            pstm.setObject(1, details.getOrderId());
            pstm.setObject(2, details.getItemId());
            pstm.setObject(3, details.getQty());
            pstm.setObject(4, details.getUnitPrice());
            pstm.setObject(5, details.getTotalPrice());
            boolean isAdded = pstm.executeUpdate() > 0;
            if (isAdded) {
                return true;
            }
        }
        return false;
    }

    public static boolean updateQty(List<OrderDetails> list){
        try {
            Connection con = DbConnection.getInstance().getConnection();
            int affectedRows=0;
            for(OrderDetails detail:list){
                Item item=new ItemController().getItem(detail.getItemId());
                int newQty=item.getQty()-detail.getQty();
                PreparedStatement pstm = con.prepareStatement("UPDATE Item SET qty=? WHERE itemId=?");
                pstm.setObject(1,newQty);
                pstm.setObject(2,item.getItemId());
                if(pstm.executeUpdate()>0){
                    affectedRows++;
                }else{
                    return false;
                }
            }
            return (list.size()==affectedRows);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}







