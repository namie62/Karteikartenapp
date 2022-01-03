package com.example.myapplication.objectclasses;

public class Flashcard {
    public int sortOrder;
    public String name;
    public int progress = 0;
    public String front;
    public String frontImg;
    public String back;
    public String backImg;

    public Flashcard() {}

    public Flashcard(int sortOrder, String front, String frontImg, String back, String backImg) {
        this.sortOrder = sortOrder;
        this.front = front;
        this.frontImg = frontImg;
        this.back = back;
        this.backImg = backImg;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }
}
