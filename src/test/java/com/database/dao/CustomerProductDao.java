package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.CustomerDBModel;
import com.db.model.CustomerProductDBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProductDao {
    private CustomerProductDao() {}
    private static final String CUSTOMER_DETAIL_QUERY = "select * from tr_customer_product where tr_customer_id = ?";

    public static CustomerProductDBModel getCustomerProductInfo(int customerId) {
        CustomerProductDBModel customerAddressDBModel = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
            prepareStmt.setInt(1, customerId);
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                customerAddressDBModel = new CustomerProductDBModel(rs.getString("dop"), rs.getString("serial_number"),
                        rs.getString("imei1"), rs.getString("imei2"),
                        rs.getString("popurl"), 1,
                        1);
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return customerAddressDBModel;
    }
}
