package edu.gcit.todo_25;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private EditText Fname, Lname, Marks, id;
    private Button addData, viewAll, update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        Fname = findViewById(R.id.firstName);
        Lname = findViewById(R.id.lastName);
        Marks = findViewById(R.id.marks);
        id = findViewById(R.id.idNumber);

        addData = findViewById(R.id.addData);
        viewAll = findViewById(R.id.viewAll);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
    }

    public void AddData(View view) {
        boolean isInserted = myDb.insertData(id.getText().toString(),
                Fname.getText().toString(),
                Lname.getText().toString(),
                Marks.getText().toString());

        if(isInserted == true) {
            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
        }
    }
}