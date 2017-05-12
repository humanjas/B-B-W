package nl.tudelft.b_b_w.Models;

/**
 * Singleton class for creating a user
 * Created by jasper on 11/05/2017.
 */

public class User {

    // static variable user
    private static User _user;

    /**
     * private constructor to guarantee that no other
     * users are initialised
     */
    private User() {

    }

    /**
     * Getter for a user, singleton way
     * @return a user
     */
    public static User getUser() {
        if (_user == null) createUser();
        return _user;
    }

    /**
     * method to create a user
     */
    private static synchronized void createUser() {
        if (_user == null) _user = new User();
    }

}
