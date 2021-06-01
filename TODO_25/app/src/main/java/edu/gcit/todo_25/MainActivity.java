package edu.gcit.todo_25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private EditText Fname, Lname, Marks, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        Fname = findViewById(R.id.firstName);
        Lname = findViewById(R.id.lastName);
        Marks = findViewById(R.id.marks);
        id = findViewById(R.id.idNumber);

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


    public void ViewData(View view) {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Student Id : "+res.getString(0)+"\n");
            buffer.append("First Name : "+res.getString(1)+"\n");
            buffer.append("Last Name : "+res.getString(2)+"\n");
            buffer.append("ITW202 Marks : "+res.getString(3)+"\n\n");
        }

        //show all data
        showMessage("List of Students", buffer.toString());
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public void UpdateData(View view) {
        boolean isUpdate = myDb.updateData(id.getText().toString(),
                Fname.getText().toString(),
                Lname.getText().toString(),
                Marks.getText().toString());

        if (isUpdate == true){
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Data Not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteData(View view) {
        Integer deletedRows = myDb.deleteData(id.getText().toString());

        if (deletedRows > 0)
            Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
    }
}