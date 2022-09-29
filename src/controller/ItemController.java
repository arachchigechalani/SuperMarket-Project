package controller;

import db.DbConnection;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    public static boolean saveItem(Item item) throws SQLException, ClassNotFoundException{
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?)");
        pstm.setObject(1,item.getItemId());
        pstm.setObject(2,item.getDescription());
        pstm.setObject(3,item.getUnitPrice());
        pstm.setObject(4,item.getQty());
        pstm.setObject(5,item.getItemType());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateItem(Item item) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE `Item` SET description=?,unitPrice=?,qty=?,itemType=? WHERE itemId=?");
        pstm.setObject(1,item.getDescription());
        pstm.setObject(2,item.getUnitPrice());
        pstm.setObject(3,item.getQty());
        pstm.setObject(4,item.getItemType());
        pstm.setObject(5,item.getItemId());


        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM `Item` WHERE itemId=?");
        pstm.setObject(1,itemId);

        return pstm.executeUpdate() > 0;
    }


    public static List<Item> getAllItems() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Item");
        ResultSet rs = pstm.executeQuery();
        List<Item> items = new ArrayList<>();
        while (rs.next()) {
            items.add(new Item(
                    rs.getString("itemId"),
                    rs.getString("description"),
                    rs.getDouble("unitPrice"),
                    rs.getInt("qty"),
                    rs.getString("itemType")
            ));
        }

        return items;

    }

    public Item getItem(String itemId) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Item WHERE itemId=?");
        pstm.setObject(1,itemId);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new Item(
                    rs.getString("itemId"),
                    rs.getString("description"),
                    rs.getDouble("unitPrice"),
                    rs.getInt("qty"),
                    rs.getString("itemType")
            );
        }
        return null;
    }
}
