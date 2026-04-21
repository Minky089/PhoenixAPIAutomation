package com.database.dao;

import com.api.constant.Product;
import com.api.request.model.CreateJobPayload;
import com.database.DatabaseManager;
import com.dataproviders.api.bean.CreateJobBean;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class CreateJobPayloadDao {
    private final static String SQL_QUERY =
            """
                    SELECT
                      mst_service_location_id,
                      mst_platform_id,
                      mst_warrenty_status_id,
                      mst_oem_id,
                      first_name,
                      last_name,
                      mobile_number,
                      mobile_number_alt,
                      email_id,
                      email_id_alt,
                      flat_number,
                      apartment_name,
                      street_name,
                      landmark,
                      area,
                      pincode,
                      country,
                      state,
                      dop,
                      serial_number,
                      imei1,
                      imei2,
                      popurl,
                      mst_model_id,
                      mst_problem_id,
                      remark
                    
                    FROM tr_customer
                    join tr_customer_address
                    ON tr_customer.tr_customer_address_id = tr_customer_address.id
                    
                    join tr_customer_product
                    on tr_customer.tr_customer_address_id = tr_customer_product.id
                    
                    join tr_job_head
                    on tr_customer.tr_customer_address_id = tr_job_head.id
                    
                    join map_job_problem
                    on map_job_problem.tr_job_head_id = tr_job_head.id
                    
                    
                    LIMIT 5;
                    """;

    @Step("Retrieving CreateJob Payload Data from Database")
    public static CreateJobBean getCreateJobPayloadData() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        CreateJobBean bean = null;
        try {
            log.info("Getting connection from the Database Manager");
            conn = DatabaseManager.getConnection();
            stmt = conn.createStatement();
            log.info("Executing the SQL Query");
            resultSet = stmt.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                bean = new CreateJobBean();
                bean.setMst_service_location_id(resultSet.getString("mst_service_location_id"));
                bean.setMst_platform_id(resultSet.getString("mst_platform_id"));
                bean.setMst_warrenty_status_id(resultSet.getString("mst_warrenty_status_id"));
                bean.setMst_oem_id(resultSet.getString("mst_oem_id"));
                bean.setCustomer__first_name(resultSet.getString("first_name"));
                bean.setCustomer__last_name(resultSet.getString("last_name"));
                bean.setCustomer__mobile_number(resultSet.getString("mobile_number"));
                bean.setCustomer__mobile_number_alt(resultSet.getString("mobile_number_alt"));
                bean.setCustomer__email_id(resultSet.getString("email_id"));
                bean.setCustomer__email_id_alt(resultSet.getString("email_id_alt"));
                bean.setCustomer_address__flat_number(resultSet.getString("flat_number"));
                bean.setCustomer_address__apartment_name(resultSet.getString("apartment_name"));
                bean.setCustomer_address__street_name(resultSet.getString("street_name"));
                bean.setCustomer_address__landmark(resultSet.getString("landmark"));
                bean.setCustomer_address__area(resultSet.getString("area"));
                bean.setCustomer_address__pincode(resultSet.getString("pincode"));
                bean.setCustomer_address__country(resultSet.getString("country"));
                bean.setCustomer_address__state(resultSet.getString("state"));
                bean.setCustomer_product__dop(resultSet.getString("dop"));
                bean.setCustomer_product__serial_number(resultSet.getString("serial_number"));
                bean.setCustomer_product__imei1(resultSet.getString("imei1"));
                bean.setCustomer_product__imei2(resultSet.getString("imei2"));
                bean.setCustomer_product__popurl(resultSet.getString("popurl"));
                bean.setCustomer_product__product_id(String.valueOf(Product.NEXUS_2.getId()));
                bean.setCustomer_product__mst_model_id(resultSet.getString("mst_model_id"));
                bean.setProblems_id(resultSet.getString("mst_problem_id"));
                bean.setProblems_remark(resultSet.getString("remark"));
            }
      } catch (SQLException e) {
            log.error("Can not convert result set to the bean", e);
          throw new RuntimeException(e);
      }
        return bean;
    }
}
