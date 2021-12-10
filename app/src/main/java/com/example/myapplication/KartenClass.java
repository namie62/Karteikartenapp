package com.example.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class KartenClass {
    private String frage;
    private String antwort;
    private Bitmap grafik;
    private Integer lernstufe;
    private String fach;
    private String thema;
    private String karte;

    public void Karte (String fachbezeichnung, String themenbezeichnung, String kartenbezeichnung) {
        thema = fachbezeichnung;
        fach = themenbezeichnung;
        karte = kartenbezeichnung;
    }

    // Ab hier erst mal alle Getter: Cornelia holt hier in denen aus der DB die relevanten Daten zur Karte zum Darstellen

    public String getFrage(){
        frage = "Themengebiet";  // eig Db Zugriff zu ensprechendem Fach und Thema und Karte (beides Variablen der Klasse Karte)
        return frage;
    }

    public String getAntwort(){
        antwort = "Inhalt";
        return antwort;
    }
    public String getGrafik(){    // noch umändern auf Bitmap später, wenn das mit dem Speichern geht
        String grafik = "hier grafik einfügen";
        return grafik;
    }
    public Integer getLernstufe(){
        lernstufe = 0;
        return lernstufe;
    }

    // Ab hier die Setter zum Abspeichern der Kartendaten
    public void setFrage(String frageinhalt){
        frage = frageinhalt;
        System.out.println(frage);
        // +abspeichern
    }

    public void setAntwort(String antwortinhalt){
        antwort = antwortinhalt;
        System.out.println(antwort);
        // +abspeichern
    }

    public void setGrafik(Bitmap grafikinhalt){
        grafik = grafikinhalt;
        // +abspeichern
    }

    public void setLernstufe(Integer stufe){
        lernstufe = stufe;
        // +abspeichern
    }

    public void dummyKarte(){
        antwort = "DummyAntwort";
        frage = "DummyFrage";
    }
}
