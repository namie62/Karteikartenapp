package com.example.myapplication.objectclasses;

import android.graphics.Bitmap;

public class Flashcard {
    public String front;
    public String back;
    public String backImg, key;
    public int progress, sortOrder;

    public Flashcard() {}

    public Flashcard(String front, String back, String backImg) {
        this.front = front;
        this.back = back;
        this.backImg = backImg;
        this.progress = 1;
    }

    public Flashcard(String front, String back, String backImg, int progress, String key) {
        this.front = front;
        this.back = back;
        this.backImg = backImg;
        this.progress = progress;
        this.key = key;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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

    @Override
    public String toString() {
        return("front = " + front + "\nback = " + back + "\nprogress = " + progress);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
