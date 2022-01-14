package com.example.myapplication.helperclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity;
import com.example.myapplication.exportactivities.EmailExportActivity;
import com.example.myapplication.createactivities.ChooseSubjectAndTopicForNewCardActivity;
import com.example.myapplication.createactivities.CreateNewCardActivity;
import com.example.myapplication.createactivities.CreateSubjectActivity;
import com.example.myapplication.createactivities.CreateTopicActivity;
import com.example.myapplication.modesofoperation.QuizModeActivity;
import com.example.myapplication.modesofoperation.StudyModeActivity;
import com.example.myapplication.overviewactivities.ShowCardsActivity;
import com.example.myapplication.overviewactivities.ShowSubjectsActivity;
import com.example.myapplication.overviewactivities.ShowTopicsActivity;
import com.example.myapplication.popups.CancelNewCardPopupActivity;
import com.example.myapplication.popups.HintPopUpActivity;

import java.util.ArrayList;

public class IntentHelper {
    private Context packageContext;
    private ArrayList<String> infoText = new ArrayList<>();
    private String user; 
    private static final String infoText0 = "Bitte mindestens 1 Fach auswählen!";
    private static final String infoText1 = "Bitte ein Fach auswählen!";
    private static final String infoText2 = "Bitte mindestens 1 Thema auswählen!";
    private static final String infoText3 = "Bitte mindestens 1 Karte auswählen!";
    private static final String infoText4 = "Bitte exakt 1 Karte auswählen!";
    private static final String infoText5 = "Sicher, dass Sie abbrechen wollen?";
    private static final String infoText6 = "Username leider schon vergeben!";
    private static final String infoText7 = "Bitte Benutzername und Passwort eingeben!";

    
    public IntentHelper(Context packageContext, String user) {
        this.packageContext = packageContext;
        this.user = user;
        this.infoText.add(infoText0);
        this.infoText.add(infoText1);
        this.infoText.add(infoText2);
        this.infoText.add(infoText3);
        this.infoText.add(infoText4);
        this.infoText.add(infoText5);
        this.infoText.add(infoText6);
        this.infoText.add(infoText7);
    }

    public IntentHelper(Context packageContext) {
        this.packageContext = packageContext;
        this.infoText.add(infoText1);
        this.infoText.add(infoText2);
        this.infoText.add(infoText3);
        this.infoText.add(infoText4);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void goToLogin() {
        Intent i = new Intent(this.packageContext, MainActivity.class);
        packageContext.startActivity(i);
    }

    public void goToStartMenu(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        putLists(i, checkedSubjects, checkedTopics, checkedCards);
    }

    public void goToStartMenu(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void goToStartMenu(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        putLists(i, checkedSubjects);
    }

    public void goToStartMenu() {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = new Bundle();
        start(i,b);
    }

    public void goToStartMenu(String user) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = new Bundle();
        start(i,b);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, checkedSubjects, checkedTopics, checkedCards);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, checkedSubjects);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, checkedSubjects, checkedTopics, checkedCards);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, checkedSubjects);
    }

    public void startStudyMode(int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, index, checkedSubjects, checkedTopics, checkedCards);
    }

    public void startStudyMode(int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, index, checkedSubjects, checkedTopics);
    }

    public void startStudyMode(int index, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, index, checkedSubjects);
    }

    public void startQuizmode(int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, QuizModeActivity.class);
        putLists(i, index, checkedSubjects, checkedTopics, checkedCards);
    }

    public void startQuizmode(int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, QuizModeActivity.class);
        putLists(i, index, checkedSubjects, checkedTopics);
    }

    public void startQuizmode(int index, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, QuizModeActivity.class);
        putLists(i, index, checkedSubjects);
    }

    public void newSubject(String user) {
        Intent i = new Intent(this.packageContext, CreateSubjectActivity.class);
        Bundle b = new Bundle();
        i.putExtras(b);
        start(i, b);
    }

    public void newTopic(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, CreateTopicActivity.class);
        putLists(i, checkedSubjects);
    }

    public void chooseCategoriesForNewCard(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ChooseSubjectAndTopicForNewCardActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void editCard(String selectedSubject, String selectedTopic, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, CreateNewCardActivity.class);
        Bundle b = addLists(checkedSubjects, checkedTopics);
        b.putString("selectedSubject", selectedSubject);
        b.putString("selectedTopic", selectedTopic);
        start(i, b);
    }

    public void editCard(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedKeys) {
        Intent i = new Intent(this.packageContext, CreateNewCardActivity.class);
        putLists(i, checkedSubjects, checkedTopics, checkedKeys);
    }

    public void shareCard(String front, String back, String img) {
        Intent i = new Intent(this.packageContext, EmailExportActivity.class);
        Bundle b = new Bundle();
        b.putString("front", front);
        b.putString("back", back);
        b.putString("img", img);
        start(i,b);
    }
    
    public void nextCardStudyMode(int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, index, checkedSubjects, checkedTopics, checkedCards);
    }

    public void nextCardStudyMode(int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, index, checkedSubjects, checkedTopics);
    }

    public void nextCardStudyMode(int index, ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, index, checkedSubjects);
    }

    public void openPopUp(int index) {
        Intent i = new Intent(this.packageContext, HintPopUpActivity.class);
        i.putExtra("InfotextPopUp", infoText.get(index));
        packageContext.startActivity(i);
    }

    public void openPopUp(String infotext) {
        Intent i = new Intent(this.packageContext, HintPopUpActivity.class);
        i.putExtra("InfotextPopUp", infotext);
        packageContext.startActivity(i);
    }

    public void cancelCardPopUp(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, CancelNewCardPopupActivity.class);
        Bundle b = addLists( checkedSubjects, checkedTopics);
        start(i, b);
    }

    public void insertImg() {
        Intent i = new Intent(this.packageContext, InsertImgHelperClassActivity.class);
        packageContext.startActivity(i);
    }

    public void putLists(Intent i, ArrayList<String> checkedSubjects){
        Bundle b = addLists(checkedSubjects);
        start(i, b);
    }

    public void putLists(Intent i, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = addLists(checkedSubjects, checkedTopics);
        start(i, b);
    }

    public void putLists(Intent i, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = addLists(checkedSubjects, checkedTopics, checkedCards);
        start(i, b);
    }

    public void putLists(Intent i, int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = addLists(checkedSubjects, checkedTopics, checkedCards);
        b.putInt("index", index);
        start(i, b);
    }

    public void putLists(Intent i, int index, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = addLists(checkedSubjects, checkedTopics);
        b.putInt("index", index);
        start(i, b);
    }

    public void putLists(Intent i, int index, ArrayList<String> checkedSubjects) {
        Bundle b = addLists(checkedSubjects);
        b.putInt("index", index);
        start(i, b);
    }

    public Bundle addLists(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putStringArrayList("checkedTopics", checkedTopics);
        b.putStringArrayList("checkedCards", checkedCards);
        return b;
    }

    public Bundle addLists(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putStringArrayList("checkedTopics", checkedTopics);
        return b;
    }
    public Bundle addLists(ArrayList<String> checkedSubjects) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        return b;
    }

    public void start(Intent i, Bundle b) {
        b.putString("user", user);
        i.putExtras(b);
        packageContext.startActivity(i);
    }
}
