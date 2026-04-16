package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.CustomerDBModel;

import java.sql.*;

public class CustomerDao {
    private CustomerDao() {}
    private static final String CUSTOMER_DETAIL_QUERY = "select * from tr_customer where id = ?";

    public static CustomerDBModel getCustomerInfo(int customerId) {
        CustomerDBModel customerDBModel = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
            prepareStmt.setInt(1, customerId);
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                customerDBModel = new CustomerDBModel(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("mobile_number"), rs.getString("mobile_number_alt"),
                        rs.getString("email_id"), rs.getString("email_id_alt"),
                        rs.getInt("tr_customer_address_id"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return customerDBModel;
    }
}
