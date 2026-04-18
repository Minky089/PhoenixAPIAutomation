package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.CustomerDBModel;
import com.db.model.CustomerProductDBModel;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class CustomerProductDao {
    private CustomerProductDao() {}
    private static final String CUSTOMER_DETAIL_QUERY = "select * from tr_customer_product where tr_customer_id = ?";

    public static CustomerProductDBModel getCustomerProductInfo(int customerId) {
        CustomerProductDBModel customerAddressDBModel = null;
        try {
            log.info("Getting connection from the Database Manager");
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
            prepareStmt.setInt(1, customerId);
            log.info("Executing the SQL Query");
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                customerAddressDBModel = new CustomerProductDBModel(rs.getString("dop"), rs.getString("serial_number"),
                        rs.getString("imei1"), rs.getString("imei2"),
                        rs.getString("popurl"), 1,
                        1);
            }
        } catch (SQLException e) {
            log.error("Can not convert result set", e);
            throw new RuntimeException(e);
        }

        return customerAddressDBModel;
    }
}
