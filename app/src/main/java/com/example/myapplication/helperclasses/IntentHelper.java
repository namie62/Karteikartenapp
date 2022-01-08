package com.example.myapplication.helperclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.createactivities.ChooseSubjectAndTopicForNewCardActivity;
import com.example.myapplication.createactivities.CreateNewCardActivity;
import com.example.myapplication.createactivities.CreateSubjectActivity;
import com.example.myapplication.createactivities.CreateTopicActivity;
import com.example.myapplication.modesofoperation.QuizModeShowCards;
import com.example.myapplication.modesofoperation.StudyModeShowCards;
import com.example.myapplication.overviewactivities.ShowCardsActivity;
import com.example.myapplication.overviewactivities.ShowSubjectsActivity;
import com.example.myapplication.overviewactivities.ShowTopicsActivity;
import com.example.myapplication.popups.CancelNewCardPopupActivity;
import com.example.myapplication.popups.HintPopUpActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IntentHelper {
    private Context packageContext;
    private ArrayList<String> infoText = new ArrayList<>();
    private static final String infoText1 = "Bitte mindestens 1 Fach auswählen!";
    private static final String infoText2 = "Bitte ein Fach auswählen!";
    private static final String infoText3 = "Bitte mindestens 1 Thema auswählen!";
    private static final String infoText4 = "Bitte mindestens 1 Karte auswählen!";
    private static final String cancelInfoText = "Sicher, dass Sie abbrechen wollen?";
    private static final int REQUESTCODE = 1;

    public IntentHelper(Context packageContext) {
        this.packageContext = packageContext;
        this.infoText.add(infoText1);
        this.infoText.add(infoText2);
        this.infoText.add(infoText3);
        this.infoText.add(infoText4);
    }

    public void goToStartMenu(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics, checkedCards);
    }

    public void goToStartMenu(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics);
    }

    public void goToStartMenu(String user, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        putLists(i, user, checkedSubjects);
    }

    public void goToStartMenu(String user) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = new Bundle();
        b.putString("user", user);
        start(i,b);
    }

    public void goToTopicOverview(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics, checkedCards);
    }

    public void goToTopicOverview(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics);
    }

    public void goToTopicOverview(String user, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, user, checkedSubjects);
    }

    public void goToCardOverview(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics, checkedCards);
    }

    public void goToCardOverview(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics);
    }

    public void goToCardOverview(String user, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, user, checkedSubjects);
    }

    public void startStudyMode(int index, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, StudyModeShowCards.class);
        putLists(i, index, user, checkedSubjects, checkedTopics, checkedCards);
    }

    public void startStudyMode(int index, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, StudyModeShowCards.class);
        putLists(i, index, user, checkedSubjects, checkedTopics);
    }

    public void startStudyMode(int index, String user, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, StudyModeShowCards.class);
        putLists(i, index, user, checkedSubjects);
    }

    public void startQuizmode(int index, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, QuizModeShowCards.class);
        putLists(i, index, user, checkedSubjects, checkedTopics, checkedCards);
    }

    public void startQuizmode(int index, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, QuizModeShowCards.class);
        putLists(i, index, user, checkedSubjects, checkedTopics);
    }

    public void startQuizmode(int index, String user, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, QuizModeShowCards.class);
        putLists(i, index, user, checkedSubjects);
    }

    public void newSubject(String user) {
        Intent i = new Intent(this.packageContext, CreateSubjectActivity.class);
        Bundle b = new Bundle();
        b.putString("user", user);
        i.putExtras(b);
        start(i, b);
    }

    public void newTopic(String user, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, CreateTopicActivity.class);
        putLists(i, user, checkedSubjects);
    }

    public void chooseCategoriesForNewCard(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ChooseSubjectAndTopicForNewCardActivity.class);
        putLists(i, user, checkedSubjects, checkedTopics);
    }

    public void newCard(String user, String selectedSubject, String selectedTopic, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, CreateNewCardActivity.class);
        Bundle b = addLists(user, checkedSubjects, checkedTopics);
        b.putString("selectedSubject", selectedSubject);
        b.putString("selectedTopic", selectedTopic);
        b.putString("user", user);
        start(i, b);
    }

    public void openPopUp(int index) {
        Intent i = new Intent(this.packageContext, HintPopUpActivity.class);
        i.putExtra("InfotextPopUp", infoText.get(index));
        packageContext.startActivity(i);
    }

    public void cancelCardPopUp() {
        Intent i = new Intent(this.packageContext, CancelNewCardPopupActivity.class);
        packageContext.startActivity(i);
    }

    public void insertImg() {
        Intent i = new Intent(this.packageContext, InsertImgHelperClassActivity.class);
        packageContext.startActivity(i);
    }

    public void putLists(Intent i, String user, ArrayList<String> checkedSubjects){
        Bundle b = addLists(user, checkedSubjects);
        start(i, b);
    }

    public void putLists(Intent i, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = addLists(user, checkedSubjects, checkedTopics);
        start(i, b);
    }

    public void putLists(Intent i, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = addLists(user, checkedSubjects, checkedTopics, checkedCards);
        start(i, b);
    }

    public void putLists(Intent i, int index, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = addLists(user, checkedSubjects, checkedTopics, checkedCards);
        b.putInt("index", index);
        start(i, b);
    }

    public void putLists(Intent i, int index, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = addLists(user, checkedSubjects, checkedTopics);
        b.putInt("index", index);
        start(i, b);
    }

    public void putLists(Intent i, int index, String user, ArrayList<String> checkedSubjects) {
        Bundle b = addLists(user, checkedSubjects);
        b.putInt("index", index);
        b.putString("user", user);
        start(i, b);
    }

    public Bundle addLists(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putStringArrayList("checkedTopics", checkedTopics);
        b.putStringArrayList("checkedCards", checkedCards);
        b.putString("user", user);
        return b;
    }

    public Bundle addLists(String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putStringArrayList("checkedTopics", checkedTopics);
        b.putString("user", user);
        return b;
    }
    public Bundle addLists(String user, ArrayList<String> checkedSubjects) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putString("user", user);
        return b;
    }

    public void start(Intent i, Bundle b) {
        i.putExtras(b);
        packageContext.startActivity(i);
    }
}
