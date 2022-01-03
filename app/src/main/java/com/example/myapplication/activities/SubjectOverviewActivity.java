package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.myfirebasehelper.MyFirebaseHelper;
import com.google.firebase.FirebaseApiNotAvailableException;

import java.util.ArrayList;

public class SubjectOverviewActivity extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    boolean[] checkchecker;
    ArrayList<String> checkeditems = new ArrayList<String>();

    MyFirebaseHelper mfh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fachuebersicht);

        this.mfh = new MyFirebaseHelper("cornelia");
        ListView listView = (ListView) findViewById(R.id.fachliste);
        Context applicationContext = getApplicationContext();
        mfh.showSubjects(listView, applicationContext);
        // hier jetzt noch im prinzip in die nächste ansicht eine ArrayListe mit den ausgewählten Fächern nehmen (Strings wären ideal)
        // außerdem bitte mfh in die nächste ansicht immer überall mit übergeben, den muss es überall hin mit ziehen
    }


    public void fillListView() {
        ListView listview = (ListView) findViewById(R.id.fachliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        ListviewHelperClass fachview = new ListviewHelperClass(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
    }

    public void goToPrevious(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void goToNext(View view){
        if (checkeditems.size() == 0) {
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte ein Fach auswählen.");
            startActivity(popupwindow);
        }
        else if(checkeditems.size() >0 ){
            Intent i = new Intent(this, TopicOverviewActivity.class);
            // i.putExtra("Fachname", checkeditems.get(0));
            i.putExtra("Fachname", checkeditems);
            System.out.println(checkeditems);
            startActivityForResult(i, REQUESTCODE);

        } /*else if (checkeditems.size() > 1) {
            System.out.println("hadskfhäa");
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte nur 1 Fach auswählen.");
            startActivity(popupwindow);
        }*/
    }


    public void startStudyMode(View view){
        if (checkeditems.size() != 0){
            //KartensammlerfürAnzeigeClass studyMode = new KartensammlerfürAnzeigeClass();
            //studyMode.starteLernmodus("Fachuebersicht", checkeditems, themen, karten);
            Intent studyMode = new Intent(this, StudyModeShowCards.class);
            studyMode.putStringArrayListExtra("Faecherliste", checkeditems);
            studyMode.putExtra("Abfrage", "Fachuebersicht");
            startActivityForResult(studyMode, REQUESTCODE);
        }
        else{
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Fach auswählen.");
            // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }
    }

    public void startTestMode(View view){
        if (checkeditems.size() != 0){
            Intent testMode = new Intent(this, QuizModeShowCards.class);
            testMode.putStringArrayListExtra("Faecherliste", checkeditems);
            testMode.putExtra("Abfrage", "Fachuebersicht");
            startActivityForResult(testMode, REQUESTCODE);
    }
        else {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Fach auswählen.");
           // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }
    }
    public void newSubject(View view){
        Intent entryPopup = new Intent(this, CreateSubjectOrTopicPopUpActivity.class);
        entryPopup.putExtra("Kategorie", "Fach");
        startActivity(entryPopup);
    }
}