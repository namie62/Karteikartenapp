package com.example.myapplication.helperclasses;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.BundleCompat;

import com.example.myapplication.overviewactivities.ShowCardsActivity;
import com.example.myapplication.overviewactivities.ShowSubjectsActivity;
import com.example.myapplication.overviewactivities.ShowTopicsActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IntentHelper {
    Context packageContext;
    private static final int REQUESTCODE = 1;

    public IntentHelper(Context packageContext) {
        this.packageContext = packageContext;
    }

    public void goToStartMenu(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = putThree(checkedSubjects, checkedTopics, checkedCards);
        i.putExtras(b);
    }

    public void goToStartMenu(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = putTwo(checkedSubjects, checkedTopics);
        i.putExtras(b);
    }

    public void goToStartMenu(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowSubjectsActivity.class);
        Bundle b = putOne(checkedSubjects);
        i.putExtras(b);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        Bundle b = putThree(checkedSubjects, checkedTopics, checkedCards);
        i.putExtras(b);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        Bundle b = putTwo(checkedSubjects, checkedTopics);
        i.putExtras(b);
    }

    public void goToTopicOverview(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        Bundle b = putOne(checkedSubjects);
        i.putExtras(b);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        Bundle b = putThree(checkedSubjects, checkedTopics, checkedCards);
        i.putExtras(b);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        Bundle b = putTwo(checkedSubjects, checkedTopics);
        i.putExtras(b);
    }

    public void goToCardOverview(ArrayList<String> checkedSubjects) {
        Intent i = new Intent(this.packageContext, ShowTopicsActivity.class);
        Bundle b = putOne(checkedSubjects);
        i.putExtras(b);
    }

    public Bundle putOne(ArrayList<String> checkedSubjects){
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        return b;
    }

    public Bundle putTwo(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putStringArrayList("checkedTopics", checkedTopics);
        return b;
    }

    public Bundle putThree(ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        Bundle b = new Bundle();
        b.putStringArrayList("checkedSubjects", checkedSubjects);
        b.putStringArrayList("checkedTopics", checkedTopics);
        b.putStringArrayList("checkedCards", checkedCards);
        return b;
    }
}
