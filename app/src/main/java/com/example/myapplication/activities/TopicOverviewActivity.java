package com.example.myapplication.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class TopicOverviewActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> checkeditems = new ArrayList<String>();
    ArrayList<String> subjectName = new ArrayList<String>(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        getListItems();
        fillListView();

        //this.fachname = getIntent().getExtras().getStringArrayList("Fachname");
        TextView textView = (TextView) findViewById(R.id.subjectName);
        textView.setText("Themen");
    }

    public void getListItems(){
        items.add("Thema1");
        items.add("Thema2");
        items.add("Thema3");
        items.add("Thema4");
        items.add("Thema5");
    }

    public void fillListView(){
        ListView listview = (ListView) findViewById(R.id.themenliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        ListviewHelperClass fachview = new ListviewHelperClass(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
    }

    public void goToPrevious(View view) {
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void goToNext(View view) {  //öffnet Kartenerstellung
        if (checkeditems.size() == 0) {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte ein Thema auswählen.");
            startActivity(popupWindow);
        }
        else if(checkeditems.size() > 0 ){
            System.out.println("Should open");
            Intent i = new Intent(this, CardsOverviewActivity.class);
            i.putExtra("Themenname", checkeditems);
            i.putExtra("Fachname", subjectName);
            startActivityForResult(i, REQUESTCODE);
        }
    }

    public void startStudyMode(View view){
        if (checkeditems.size() != 0){
            Intent studyMode = new Intent(this, StudyModeShowCards.class);
            studyMode.putStringArrayListExtra("Themenliste", checkeditems);
            studyMode.putExtra("Fachname", this.subjectName);
            studyMode.putExtra("Abfrage", "Themenuebersicht");
            startActivityForResult(studyMode, REQUESTCODE);
        }
        else{
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            //popupWindow.putExtra("Abfrage", "Themenuebersicht");
            startActivity(popupWindow);
        }
    }
    public void startQuiz(View view){
        if (checkeditems.size() != 0){
            Intent quizMode = new Intent(this, QuizModeShowCards.class);
            quizMode.putStringArrayListExtra("Themenliste", checkeditems);
            quizMode.putExtra("Fachname", this.subjectName);
            quizMode.putExtra("Abfrage", "Themenuebersicht");
            startActivityForResult(quizMode, REQUESTCODE);
        }
        else{
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }
    }
    public void newTopic(View view){
        Intent entryPopup = new Intent(this, CreateSubjectOrTopicPopUpActivity.class);
        entryPopup.putExtra("Kategorie", "Thema");
        startActivity(entryPopup);
    }

    public void editTopic(View view){
        if (checkeditems.size() == 0){
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }else if (checkeditems.size() == 1){
            Intent editTopic = new Intent(this, CardsOverviewActivity.class);
            editTopic.putExtra("Thema", checkeditems.get(0));
            startActivity(editTopic);
        }else {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte nur 1 Thema zur Bearbeitung auswählen.");
        }
    }
}