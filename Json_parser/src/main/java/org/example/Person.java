package org.example;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter @Setter
public class Person {
    private String name;
    private boolean isStudent;
    private ArrayList<String> courses;
    private int age;
    private ArrayList<String> siblings;
    private boolean isWorker;

    @Override
    public String toString() {
        return "Name: " + name + ", Is student: " + isStudent + ", Courses: " + courses + ", Age: " + age +
                ", Siblings " + siblings + ", Is worker: " + isWorker;
    }
}

