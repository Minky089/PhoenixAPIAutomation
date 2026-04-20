package com.dataproviders.api.bean;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class UserBean {
    @CsvBindByName(column = "username")
    private String username;
    @CsvBindByName(column = "password")
    private String password;

    public UserBean() {}

    @Override
    public String toString() {
        return "UserPOJO{" +
                "username='" + username + '\'' +
                ", password= ********'" + '\'' +
                '}';
    }
}
