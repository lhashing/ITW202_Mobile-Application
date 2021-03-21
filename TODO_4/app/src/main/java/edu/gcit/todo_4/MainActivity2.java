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
//    private TextView RepliedHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textFromSend = findViewById(R.id.textRecieved);
        ReplyEditText = findViewById(R.id.editText_send3);
//        RepliedHeader = findViewById(R.id.ReplyHeader);

        Intent obj = getIntent();
        String msg = obj.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textFromSend.setText(msg);
    }

    public void Reply(View view) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        String reply = ReplyEditText.getText().toString();
        returnIntent.putExtra(EXTRA_REPLY, reply);
//        RepliedHeader.setVisibility(view.VISIBLE);
        startActivity(returnIntent);
    }


}