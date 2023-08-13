package com.hyperloop;

public class PassengerDetails {
    String name;
    int age;
    String destination;

    public PassengerDetails(String name, int age, String destination) {
        this.name = name;
        this.age = age;
        this.destination = destination;
    }
    public int getAge() {
        return age;
    }
}
