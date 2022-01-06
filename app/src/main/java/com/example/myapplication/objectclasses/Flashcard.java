package com.example.myapplication.objectclasses;

import android.graphics.Bitmap;

public class Flashcard {
    public int progress = 0;
    public String front;
    public String back;
    public Bitmap backImg;

    public Flashcard() {}

    public Flashcard(String front, String back, Bitmap backImg) {
        this.front = front;
        this.back = back;
        this.backImg = backImg;
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

    public Bitmap getBackImg() {
        return backImg;
    }

    public void setBackImg(Bitmap backImg) {
        this.backImg = backImg;
    }
}
