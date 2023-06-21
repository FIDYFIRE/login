package com.example.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText User1, Pass1, User2, Pass2;
    Button inscrire, login;
    SQLiteOpenHelper helper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User1=findViewById(R.id.User1);
        User2=findViewById(R.id.User2);
        Pass1=findViewById(R.id.Pass1);
        Pass2=findViewById(R.id.Pass2);
        inscrire=findViewById(R.id.inscrire);
        login=findViewById(R.id.login);

        helper = new SQLiteOpenHelper(MainActivity.this, "Database3.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(" CREATE TABLE Utilisateurs (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, User TEXT, Pass TEXT)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };


        inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = helper.getWritableDatabase();
                database.execSQL("INSERT INTO Utilisateurs (User, Pass) VALUES ( '"+User1.getText()+"', '"+Pass1.getText()+"')");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = helper.getReadableDatabase();
                Cursor c = database.rawQuery("SELECT * FROM Utilisateurs WHERE User = '"+User2.getText()+"' AND Pass = '"+Pass2.getText()+"' ", null );
                if (c.moveToNext()) {
                    Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Nom d'utilisateur ou Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });
    }
}