package com.example.jasper.app.Models;

/**
 * Created by jasper on 11/05/2017.
 */

public class User {

    private static User _user;

    private User() {

    }

    public static User getUser() {
        if (_user == null) createUser();
        return _user;
    }

    private static synchronized void createUser() {
        if (_user == null) _user = new User();
    }

}
