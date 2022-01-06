package com.example.myapplication.helperclasses;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import java.util.ArrayList;

public class ListviewHelperClass {
    boolean[] checkchecker;
    ArrayList<String> checkeditems = new ArrayList<String>();

    public ListviewHelperClass(ListView listview, ArrayList<String> items) {

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            this.checkchecker = new boolean[items.size()];

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checkchecker[position] =! checkchecker[position];
                    if (checkchecker[position]){
                        checkeditems.add(items.get(position));
                        }
                    else
                        checkeditems.remove(items.get(position));
                }
            });
        }
        public ArrayList<String> getCheckeditems(){
        return checkeditems;
        }
    }


