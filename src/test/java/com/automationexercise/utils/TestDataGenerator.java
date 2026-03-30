package com.automationexercise.utils;

import com.automationexercise.models.UserData;

public class TestDataGenerator {

    private static final String BASE_URL = System.getProperty(
            "api.base.url", "https://automationexercise.com/api");

    public static String baseUrl() {
        return BASE_URL;
    }

    public static UserData validUser() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String uniqueEmail = "autoqa" + timestamp + "@test.com";

        UserData user = new UserData();
        user.setName("Auto QA " + timestamp);
        user.setEmail(uniqueEmail);
        user.setPassword("Test1234");
        user.setTitle("Mr");
        user.setBirthDate("15");
        user.setBirthMonth("6");
        user.setBirthYear("1990");
        user.setFirstname("Auto");
        user.setLastname("QA");
        user.setCompany("TestCorp");
        user.setAddress1("123 Main St");
        user.setAddress2("");
        user.setCountry("United States");
        user.setZipcode("90001");
        user.setState("California");
        user.setCity("Los Angeles");
        user.setPhoneNumber("5551234567");
        return user;
    }
}
