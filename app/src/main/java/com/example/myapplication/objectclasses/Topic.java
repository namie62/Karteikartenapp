package com.example.myapplication.objectclasses;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private int sortOrder;
    private String name;
    private List<Flashcard> flashcards = new ArrayList<>();

    public Topic() {}

    public Topic(int sortOrder, String name) {
        this.sortOrder = sortOrder;
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(Flashcard flashcard) {
        this.flashcards.add(flashcard);
    }
}
