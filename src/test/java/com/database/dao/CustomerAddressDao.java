package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.CustomerAddressDBModel;
import com.db.model.CustomerDBModel;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class CustomerAddressDao {
    private CustomerAddressDao() {}

    private static final String CUSTOMER_ADDRESS_DETAIL_QUERY = "select * from tr_customer_address where id = ?";

    public static CustomerAddressDBModel getCustomerAddressInfo(int customerAddressId) {
        CustomerAddressDBModel customerAddressDBModel = null;
        try {
            log.info("Getting connection from the Database Manager");
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(CUSTOMER_ADDRESS_DETAIL_QUERY);
            prepareStmt.setInt(1, customerAddressId);
            log.info("Executing the SQL Query");
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                customerAddressDBModel = new CustomerAddressDBModel(rs.getInt("id"), rs.getString("flat_number"), rs.getString("apartment_name"),
                        rs.getString("street_name"), rs.getString("landmark"),
                        rs.getString("area"), rs.getString("pincode"),
                        rs.getString("country"), rs.getString("state"));
            }
        } catch (SQLException e) {
            log.error("Can not convert result set", e);
            throw new RuntimeException(e);
        }

        return customerAddressDBModel;
    }
}
