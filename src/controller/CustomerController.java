package controller;

import db.DbConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public static boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
        pstm.setObject(1,customer.getCustId());
        pstm.setObject(2,customer.getCustTitle());
        pstm.setObject(3,customer.getCustGender());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE Customer SET custTitle=?,custGender=? WHERE custId=?");
        pstm.setObject(1,customer.getCustTitle());
        pstm.setObject(2,customer.getCustGender());
        pstm.setObject(3,customer.getCustId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteCustomer(String custId) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Customer WHERE custId=?");
        pstm.setObject(1,custId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> searchCustomer(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Customer WHERE custId LIKE '%"+value+"%'");
        pstm.setObject(1,value);
        ResultSet rs = pstm.executeQuery();

        List<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString("custId"),
                    rs.getString("custTitle"),
                    rs.getString("custGender")
            ));
        }

        return customers;
    }

    public static List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Customer");
        ResultSet rs = pstm.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString("custId"),
                    rs.getString("custTitle"),
                    rs.getString("custGender")
            ));
        }

        return customers;

    }
}
