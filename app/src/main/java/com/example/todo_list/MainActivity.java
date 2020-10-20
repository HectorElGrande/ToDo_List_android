package com.example.todo_list;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static String FILE_NAME;
    SharedPreferences sharedPrefs;
    File file;
    TextView list_tasks;
    EditText task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        file = new File("/data/data/com.example.todo_list/files/" + FILE_NAME);
        sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        FILE_NAME = sharedPrefs.getString("edit_text_preference_1", "");
        task = findViewById(R.id.editTextTask);
        list_tasks = findViewById(R.id.list_tasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFileName();
        setFilePath();
        list_tasks.setText("");
        loadFile();
    }

    private void setFileName(){
        FILE_NAME = sharedPrefs.getString("edit_text_preference_1", "");
    }

    private void setFilePath(){
        file = new File("/data/data/com.example.todo_list/files/" + FILE_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcion1:
                file.delete();
                list_tasks.setText("");
                return true;
            case R.id.opcion2:
                startActivity(new Intent(this, Preference.class));
                return true;
            default:
                Toast.makeText(this, "OTRA OPCIÓN!!!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void saveFile(View v) {
        String text = task.getText().toString()+"\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(text.getBytes());
            list_tasks.append(task.getText()+"\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadFile() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            list_tasks.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
}