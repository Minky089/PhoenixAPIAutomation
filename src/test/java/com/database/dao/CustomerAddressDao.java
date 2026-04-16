package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.CustomerAddressDBModel;
import com.db.model.CustomerDBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAddressDao {
    private CustomerAddressDao() {}

    private static final String CUSTOMER_ADDRESS_DETAIL_QUERY = "select * from tr_customer_address where id = ?";

    public static CustomerAddressDBModel getCustomerAddressInfo(int customerAddressId) {
        CustomerAddressDBModel customerAddressDBModel = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(CUSTOMER_ADDRESS_DETAIL_QUERY);
            prepareStmt.setInt(1, customerAddressId);
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                customerAddressDBModel = new CustomerAddressDBModel(rs.getInt("id"), rs.getString("flat_number"), rs.getString("apartment_name"),
                        rs.getString("street_name"), rs.getString("landmark"),
                        rs.getString("area"), rs.getString("pincode"),
                        rs.getString("country"), rs.getString("state"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return customerAddressDBModel;
    }
}
