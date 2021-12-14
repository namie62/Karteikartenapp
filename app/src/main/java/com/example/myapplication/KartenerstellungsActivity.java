package com.example.myapplication;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class KartenerstellungsActivity extends AppCompatActivity {
    String themenname;
    private static final int REQUESTCODE = 1;
    KartenClass karte = new KartenClass();
    Bitmap grafik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartenerstellung);

        themenname = getIntent().getExtras().getString("Themenname");

        Button buttonabbrechen = (Button) findViewById(R.id.buttonabbrechen);
        buttonabbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPopUpWindow();
            }
        });
    }

    private void openPopUpWindow() {
        Intent popupwindow = new Intent(KartenerstellungsActivity.this, KartenerstellungAbbrechenPopupActivity.class);
        popupwindow.putExtra("Themenname", themenname);
        startActivity(popupwindow);
    }

    public void onClick(View view) {
        Intent i = new Intent(this, GrafikeinfuegenHelperClass.class);
        startActivityForResult(i, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageview = (ImageView) findViewById(R.id.imageView);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getParcelableExtra("pic");
                    if (uri != null){
                    this.grafik = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageview.setImageBitmap(this.grafik);
                    }else {
                        System.out.println("kein Bild ausgewählt");
                    }
                } catch (Exception e) {
                    Bitmap bitmap = null;
                    System.out.println("Bild konnte nicht geparsed werden"); // Stattdessen Errormessage dialog
                }
            }
        }
    }

    public void speichereInhalte(View view){    // setzt die Inhalte in Klasse Karte und sichert damit das Abspeichern
        karte.setFrage(getFrageText());
        karte.setAntwort(getAntwortText());
        karte.setLernstufe(getLernstufe());
        karte.setGrafik(getGrafik());
    }

    public String getFrageText(){    // Getter für speichereInhalt Methode
        EditText themengebietfeld = (EditText) findViewById(R.id.editTextThemengebiet);
        String frage = themengebietfeld.getText().toString();
        return frage;
    }

    public String getAntwortText(){
        EditText themengebietfeld = (EditText) findViewById(R.id.editTextInhalt);
        String antwort = themengebietfeld.getText().toString();
        return antwort;
    }

    public Integer getLernstufe(){
        Integer lernstufe = 0;
        return lernstufe;
    }

    public Bitmap getGrafik(){
        Bitmap grafik = this.grafik;
        return grafik;
    }
}