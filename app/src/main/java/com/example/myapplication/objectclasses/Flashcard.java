package com.example.myapplication.objectclasses;

public class Flashcard {
    public String front, back, img_uri, key;
    public int progress;

    public Flashcard() {}

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
        this.progress = 1;
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

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String backImg) {
        this.img_uri = backImg;
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
