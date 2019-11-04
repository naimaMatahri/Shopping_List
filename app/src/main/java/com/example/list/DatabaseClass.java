package com.example.list;

public class DatabaseClass {

    ThisApplication myApplication = new ThisApplication();
    private static final DatabaseClass database = new DatabaseClass();

    public static DatabaseClass getDatabase() {
        return database;
    }
}
