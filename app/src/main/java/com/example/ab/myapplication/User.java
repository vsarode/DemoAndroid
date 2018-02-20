package com.example.ab.myapplication;

public class User {
    String email;
    String name;
    static User currentUser;

    private User() {
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            System.out.println("User is not yet Logged In");
            return null;
        }
        return currentUser;
    }

    public static User createUser(String email, String name) {
        if (currentUser != null) {
            System.out.println("User already Logged In");
            return currentUser;
        }
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
