package com.example.myapplication.objectclasses;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int sortOrder;
    private String name;
    private List<Topic> topics;

    public Subject() {}

    public Subject(int sortOrder, String name) {
        this.sortOrder = sortOrder;
        this.name = name;
        this.topics = new ArrayList<>();
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

    public List<Topic> getTopics() {
        return topics;
    }

    public void addTopic(Topic topic) {
        this.topics.add(topic);
    }
}
