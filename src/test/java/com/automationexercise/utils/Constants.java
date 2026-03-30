package com.automationexercise.utils;

public final class Constants {

    private Constants() {}

    public static final class Endpoints {
        public static final String CREATE_ACCOUNT = "/createAccount";
        public static final String GET_USER_DETAIL_BY_EMAIL = "/getUserDetailByEmail";
        public static final String UPDATE_ACCOUNT = "/updateAccount";
        public static final String DELETE_ACCOUNT = "/deleteAccount";
    }

    public static final class FormFields {
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TITLE = "title";
        public static final String BIRTH_DATE = "birth_date";
        public static final String BIRTH_MONTH = "birth_month";
        public static final String BIRTH_YEAR = "birth_year";
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String COMPANY = "company";
        public static final String ADDRESS1 = "address1";
        public static final String ADDRESS2 = "address2";
        public static final String COUNTRY = "country";
        public static final String ZIPCODE = "zipcode";
        public static final String STATE = "state";
        public static final String CITY = "city";
        public static final String MOBILE_NUMBER = "mobile_number";
    }

    public static final class Messages {
        public static final String USER_CREATED = "User created!";
        public static final String USER_UPDATED = "User updated!";
        public static final String ACCOUNT_DELETED = "Account deleted!";

        public static String forKey(String keyOrLiteral) {
            if (keyOrLiteral == null) return null;
            switch (keyOrLiteral) {
                case "USER_CREATED":
                    return USER_CREATED;
                case "USER_UPDATED":
                    return USER_UPDATED;
                case "ACCOUNT_DELETED":
                    return ACCOUNT_DELETED;
                default:
                    return keyOrLiteral;
            }
        }
    }
}
