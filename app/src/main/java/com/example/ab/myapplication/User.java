package com.example.ab.myapplication;

public class User {
    public String email;
    public String name;
    static User currentUser;

    private User() {
    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            System.out.println("User is not yet Logged In");
            return null;
        }
        return currentUser;
    }

    public static User createUser(String email, String name) {
        currentUser = new User();
        currentUser.email = email;
        currentUser.name = name;
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logOut() {
        currentUser = null;
    }
}
