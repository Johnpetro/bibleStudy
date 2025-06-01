package com.beng22coe.biblestudy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DatabaseViewerActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TextView databaseContentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_viewer);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Database Viewer");

        // Initialize views
        databaseContentText = findViewById(R.id.database_content);
        
        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        
        // Load and display database content
        loadDatabaseContent();
    }

    private void loadDatabaseContent() {
        StringBuilder content = new StringBuilder();
        
        // Get readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        
        // Query users table
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS, null);
        
        // Append table header
        content.append("USERS TABLE:\n");
        content.append("ID | USERNAME | EMAIL | PASSWORD\n");
        content.append("--------------------------------\n");
        
        // Iterate through results
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
                
                content.append(id).append(" | ")
                       .append(username).append(" | ")
                       .append(email).append(" | ")
                       .append(password).append("\n");
            } while (cursor.moveToNext());
        } else {
            content.append("No users found in database.\n");
        }
        
        cursor.close();
        db.close();
        
        // Set text to TextView
        databaseContentText.setText(content.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}