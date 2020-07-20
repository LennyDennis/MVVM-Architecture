package com.lennydennis.mvvmarchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID=
            "com.lennydennis.mvvmarchitecture.ID";
    public static final String EXTRA_TITLE =
            "com.lennydennis.mvvmarchitecture.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.lennydennis.mvvmarchitecture.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY=
            "com.lennydennis.mvvmarchitecture.EXTRA_PRIORITY";
    private EditText editNoteTitle;
    private EditText editNotedDescription;
    private NumberPicker noteNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editNoteTitle = findViewById(R.id.et_note_title);
        editNotedDescription = findViewById(R.id.et_note_description);
        noteNumberPicker = findViewById(R.id.note_priority);

        noteNumberPicker.setMinValue(0);
        noteNumberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editNoteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editNotedDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            noteNumberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

        }else{
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editNoteTitle.getText().toString();
        String description = editNotedDescription.getText().toString();
        int priority = noteNumberPicker.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Insert all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_DESCRIPTION,description);
        intent.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

    }
}