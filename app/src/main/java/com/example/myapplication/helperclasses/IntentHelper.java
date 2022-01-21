package com.example.myapplication.helperclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity;
import com.example.myapplication.editactivities.EditCardOrderActivity;
import com.example.myapplication.editactivities.EditSubjectActivity;
import com.example.myapplication.editactivities.EditTopicActivity;
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

import java.util.ArrayList;

public class IntentHelper {
    private final Context packageContext;
    private String user;
    
    public IntentHelper(Context packageContext, String user) {
        this.packageContext = packageContext;
        this.user = user;
    }

    public IntentHelper(Context packageContext) {
        this.packageContext = packageContext;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void goToLogin() {
        Intent i = new Intent(this.packageContext, MainActivity.class);
        packageContext.startActivity(i);
    }

    public void goToStartMenu() {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = new Bundle();
        start(i,b);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        putLists(i, checkedSubjects);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowCardsActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void startStudyMode(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, checkedSubjects, checkedTopics, checkedCards);
    }

    public void startStudyMode( ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void startStudyMode( ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, StudyModeActivity.class);
        putLists(i, checkedSubjects);
    }

    public void startQuizmode(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, QuizModeActivity.class);
        putLists(i, checkedSubjects, checkedTopics, checkedCards);
    }

    public void startQuizmode(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, QuizModeActivity.class);
        putLists(i, checkedSubjects, checkedTopics);
    }

    public void startQuizmode(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, QuizModeActivity.class);
        putLists(i, checkedSubjects);
    }

    public void newSubject() {
        Intent i = new Intent(this.packageContext, CreateSubjectActivity.class);
        Bundle b = new Bundle();
        i.putExtras(b);
        start(i, b);
    }

    public void editSubject(ArrayList<String> allSubjects, String selectedSubject) {
        Intent i = new Intent(this.packageContext, EditSubjectActivity.class);
        putLists(i, allSubjects, selectedSubject);
    }

    public void newTopic(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, CreateTopicActivity.class);
        putLists(i, checkedSubjects);
    }

    public void editTopic(ArrayList<String> allTopics, String selectedSubject, String selectedTopic) {
        Intent i = new Intent(this.packageContext, EditTopicActivity.class);
        Bundle b = new Bundle();
        b.putStringArrayList("allTopics", allTopics);
        b.putString("selectedSubject", selectedSubject);
        b.putString("selectedTopic", selectedTopic);
        start(i,b);
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

    public void editCardOrder(ArrayList<String> allCards, String selectedSubject, String selectedTopic, String selectedCard){
        Intent i = new Intent(this.packageContext, EditCardOrderActivity.class);
        Bundle b = new Bundle();
        b.putStringArrayList("allCards", allCards);
        b.putString("selectedSubject", selectedSubject);
        b.putString("selectedTopic", selectedTopic);
        b.putString("selectedCard", selectedCard);
        start(i, b);
    }

    public void shareCard(String front, String back, String img) {
        Intent i = new Intent(this.packageContext, EmailExportActivity.class);
        Bundle b = new Bundle();
        b.putString("front", front);
        b.putString("back", back);
        b.putString("img", img);
        start(i,b);
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
    public void putLists(Intent i, ArrayList<String> checkedSubjects, String selectedSubject){
        Bundle b = addLists(checkedSubjects);
        b.putString("selectedSubject", selectedSubject);
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
