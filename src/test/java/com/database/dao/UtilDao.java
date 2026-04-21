package com.database.dao;

import com.database.DatabaseManager;
import com.db.model.JobHeadDBModel;
import com.db.model.JobProblemDBModel;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class UtilDao {
    private UtilDao() {}
    private static final String PROBLEM_QUERY = "select * from map_job_problem where tr_job_head_id = ?";
    private static final String JOB_HEAD_QUERY = "select * from tr_job_head where tr_customer_id = ?";

    @Step("Retrieving Problem List Data from Database")
    public static List<JobProblemDBModel> getJobProblemInfo(int jobHeadId) {
        List<JobProblemDBModel> problemList = new ArrayList<>();
        try {
            log.info("Getting connection from the Database Manager");
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(PROBLEM_QUERY);
            prepareStmt.setInt(1, jobHeadId);
            log.info("Executing the SQL Query");
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                JobProblemDBModel jobProblem = new JobProblemDBModel(rs.getInt("id"), rs.getInt("tr_job_head_id"),
                        rs.getInt("mst_problem_id"), rs.getString("remark"));
                problemList.add(jobProblem);
            }
        } catch (SQLException e) {
            log.error("Can not convert result set", e);
            throw new RuntimeException(e);
        }

        return problemList;
    }

    @Step("Retrieving Job Head Data from Database")
    public static JobHeadDBModel getJobHeadInfo(int customerId) {
        JobHeadDBModel jobHead = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement prepareStmt = conn.prepareStatement(JOB_HEAD_QUERY);
            prepareStmt.setInt(1, customerId);
            ResultSet rs = prepareStmt.executeQuery();
            while (rs.next()) {
                jobHead = new JobHeadDBModel(rs.getInt("id"), rs.getString("job_number"),
                        rs.getInt("tr_customer_id"), rs.getInt("tr_customer_product_id"),
                        rs.getInt("mst_service_location_id"), rs.getInt("mst_platform_id"),
                        rs.getInt("mst_warrenty_status_id"), rs.getInt("mst_oem_id"));
            }
        } catch (SQLException e) {
            System.err.print(e.getMessage());
        }

        return jobHead;
    }

}
