package edu.gcit.todo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    public static final String EXTRA_REPLY = "edu.gcit.todo_4.REPLY";

    private TextView textFromSend;
    private EditText ReplyEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textFromSend = findViewById(R.id.textRecieved);
        ReplyEditText = findViewById(R.id.editText_send3);

        Intent obj = getIntent();
        String msg = obj.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textFromSend.setText(msg);
    }

    public void Reply(View view) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        String replymsg = ReplyEditText.getText().toString();
        returnIntent.putExtra(EXTRA_REPLY, replymsg);
        setResult(RESULT_OK, returnIntent);
        finish();
    }


}