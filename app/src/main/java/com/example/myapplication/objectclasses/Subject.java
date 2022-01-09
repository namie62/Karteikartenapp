package com.example.myapplication.objectclasses;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int sortOrder;
    private String name;


    public Subject(int sortOrder, String name) {
        this.sortOrder = sortOrder;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "sortOrder=" + sortOrder +
                ", name='" + name + '\'' +
                '}';
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sort_order) {
        this.sortOrder = sort_order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
