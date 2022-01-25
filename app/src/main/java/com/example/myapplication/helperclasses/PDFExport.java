package com.example.myapplication.helperclasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PDFExport {

    private Document document;

    public void PDFExport(Context c, String user, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        try {
            document = createDocument();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        Paragraph selectedSubject = new Paragraph("Fach: " + subject).setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
                        document.add(selectedSubject);
                        for (String topic : checkedTopics) {
                            if (snapshot.child(subject).child(topic).getChildrenCount() > 0) {
                                Table table = createTable();
                                Paragraph selectedTopic = new Paragraph("Thema: " + topic).setUnderline().setFontSize(22).setTextAlignment(TextAlignment.CENTER);
                                document.add(selectedTopic);
                                int max = (int) snapshot.child(subject).child(topic).getChildrenCount();
                                for (int i=0; i<max; i++) {
                                    String cardpath = snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class);
                                    String nameFromDB = snapshot.child("cards").child(cardpath).child("front").getValue(String.class);
                                    String valueFromDB = snapshot.child("cards").child(cardpath).child("back").getValue(String.class);
                                    table.addCell(new Cell().add(new Paragraph(nameFromDB)));
                                    table.addCell(new Cell().add(new Paragraph(valueFromDB)));
                                }
                                document.add(table);
                            }
                        }
                    }
                }
                document.close();
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(c, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private Table createTable(){
        float[] width = {250f, 250f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(new Cell().add(new Paragraph("Thema")));
        table.addCell(new Cell().add(new Paragraph("Inhalt")));
        return table;
    }

    private Document createDocument() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        File file = new File(pdfPath, "Flashcards"+ts+".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(0,0,0,0);
        return document;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
