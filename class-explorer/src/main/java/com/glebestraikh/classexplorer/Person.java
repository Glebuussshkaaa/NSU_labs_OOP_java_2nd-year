package com.glebestraikh.classexplorer;

public class Person {
    private String name;
    private int age;
    protected String address;
    public boolean isActive;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private void internalMethod() {
    }
}

