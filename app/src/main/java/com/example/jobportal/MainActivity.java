package com.example.jobportal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
    }

    public void Inscrivez(View view) {
        Intent it = new Intent(this, SignupActivity.class);
        startActivity(it);
    }

    public void Login(View view) {
        String username = "example"; // replace with actual username entered by user
        String password = "password"; // replace with actual password entered by user

        if (dbHelper.checkusernamepassword(username, password)) {
            // Login successful
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            // Proceed to next activity or perform other actions
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
        }
    }

    public class DBHelper extends SQLiteOpenHelper {
        public static final String DBNAME = "Login.db";

        public DBHelper(Context context) {
            super(context, "Login.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase MyDB) {
            MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
            MyDB.execSQL("drop Table if exists users");
        }

        public Boolean insertData(String username, String password) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", username);
            contentValues.put("password", password);
            long result = MyDB.insert("users", null, contentValues);
            return result != -1;
        }

        public Boolean checkusername(String username) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
            return cursor.getCount() > 0;
        }

        public Boolean checkusernamepassword(String username, String password) {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
            return cursor.getCount() > 0;
        }
}
}
