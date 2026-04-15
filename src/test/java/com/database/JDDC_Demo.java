package com.database;

import com.database.dao.CreateJobPayloadDao;

import java.sql.SQLException;

public class JDDC_Demo {
    public static void main(String[] args) {
        System.out.println("==========> Dao value " + CreateJobPayloadDao.getCreateJobPayloadData());
    }
}
