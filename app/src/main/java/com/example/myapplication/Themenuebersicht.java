package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Themenuebersicht extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> checkeditems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themenuebersicht);
        getListItems();
        fillListView();

        String fachname = getIntent().getExtras().getString("Fachname");
        TextView textview = (TextView) findViewById(R.id.fachname);
        textview.setText(fachname);

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
        ListviewHelper fachview = new ListviewHelper(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
    }

    public void zurueck(View view) {
        Intent i = new Intent(this, Fachuebersicht.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        if (checkeditems.size() == 0) {
            System.out.println("errormessage: Bitte  ein Fach auswählen");
        }
        else if(checkeditems.size() == 1 ){
            System.out.println("Should open");
            Intent i = new Intent(this, Kartenerstellung.class);
            i.putExtra("Fachname", checkeditems.get(0));
            startActivityForResult(i, REQUESTCODE);
        }else{
            System.out.println("errormessage: Bitte nur ein Fach auswählen");
        }
    }
}