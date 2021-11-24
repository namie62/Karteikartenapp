package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import java.util.ArrayList;

public class ListviewHelper {
    boolean[] checkchecker;
    ArrayList<String> checkeditems = new ArrayList<String>();

    public ListviewHelper(ListView listview, ArrayAdapter arrayAdapter, ArrayList<String> items) {

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            this.checkchecker = new boolean[items.size()];

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checkchecker[position] = !checkchecker[position];
                    if (checkchecker[position]){
                        checkeditems.add(items.get(position));
                        System.out.println("Das Item"+ items.get(position) + "wurde angeklickt");}
                    else
                        checkeditems.add(items.remove(position));
                    System.out.println("Das Item wurde deselected");
                }
            });
            listview.setAdapter(arrayAdapter);
        }
        public ArrayList<String> getCheckeditems(){
        return checkeditems;
        }
    }


