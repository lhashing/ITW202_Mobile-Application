package edu.gcit.todo_7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int num;
    private TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = findViewById(R.id.textView);

        if (savedInstanceState != null){
            num = savedInstanceState.getInt("count_number");
            count.setText(String.valueOf(num));
        }
    }
    public void Count(View view) {
        num++;
        count.setText(String.valueOf(num));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count_number", num);
    }
}