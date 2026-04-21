package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.CustomerDBModel;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.sql.*;

@Log4j2
public class CustomerDao {
    private CustomerDao() {}
    private static final String CUSTOMER_DETAIL_QUERY = "select * from tr_customer where id = ?";

    @Step("Retrieving Customer Data from Database")
    public static CustomerDBModel getCustomerInfo(int customerId) {
        CustomerDBModel customerDBModel = null;
        try {
            log.info("Getting connection from the Database Manager");
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
            prepareStmt.setInt(1, customerId);
            log.info("Executing the SQL Query");
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                customerDBModel = new CustomerDBModel(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("mobile_number"), rs.getString("mobile_number_alt"),
                        rs.getString("email_id"), rs.getString("email_id_alt"),
                        rs.getInt("tr_customer_address_id"));
            }
        } catch (SQLException e) {
            log.error("Can not convert result set", e);
            throw new RuntimeException(e);
        }

        return customerDBModel;
    }
}
