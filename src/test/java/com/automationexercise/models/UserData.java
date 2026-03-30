package com.automationexercise.models;

import java.util.HashMap;
import java.util.Map;

public class UserData {

    private String name;
    private String email;
    private String password;
    private String title;
    private String birthDate;
    private String birthMonth;
    private String birthYear;
    private String firstname;
    private String lastname;
    private String company;
    private String address1;
    private String address2;
    private String country;
    private String zipcode;
    private String state;
    private String city;
    private String phoneNumber;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getBirthMonth() { return birthMonth; }
    public void setBirthMonth(String birthMonth) { this.birthMonth = birthMonth; }

    public String getBirthYear() { return birthYear; }
    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Map<String, String> toFormData() {
        Map<String, String> formData = new HashMap<>();
        formData.put("name", name);
        formData.put("email", email);
        formData.put("password", password);
        formData.put("title", title);
        formData.put("birth_date", birthDate);
        formData.put("birth_month", birthMonth);
        formData.put("birth_year", birthYear);
        formData.put("firstname", firstname);
        formData.put("lastname", lastname);
        formData.put("company", company);
        formData.put("address1", address1);
        formData.put("address2", address2);
        formData.put("country", country);
        formData.put("zipcode", zipcode);
        formData.put("state", state);
        formData.put("city", city);
        formData.put("mobile_number", phoneNumber);
        return formData;
    }
}
