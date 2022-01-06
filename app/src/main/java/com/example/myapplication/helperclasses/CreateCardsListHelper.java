package com.example.myapplication.helperclasses;

import androidx.activity.result.contract.ActivityResultContracts;

import com.example.myapplication.objectclasses.Flashcard;

import java.util.ArrayList;

public class CreateCardsListHelper {

    ArrayList<Flashcard> returnCards = new ArrayList();

    public CreateCardsListHelper(ArrayList<Flashcard> cards) {
        this.returnCards = cards;
    }

    public ArrayList<Flashcard> getReturnCards() {
        return returnCards;
    }
}
