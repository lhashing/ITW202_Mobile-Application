package edu.gcit.todo_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.gcit.todo_4.MESSAGE";

    public static final int TEXT_REQUEST = 1;

    private EditText EditTextSend;
    private TextView RepliedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTextSend = findViewById(R.id.editText_send);
        RepliedText = findViewById(R.id.ReplyText);

        Intent obj1 = getIntent();
        String replymsg = obj1.getStringExtra(MainActivity2.EXTRA_REPLY);
        RepliedText.setText(replymsg);
    }

    public void Send(View view) {
        Intent sendIntent = new Intent(this, MainActivity2.class);
        String message = EditTextSend.getText().toString();
        sendIntent.putExtra(EXTRA_MESSAGE, message);
        startActivity(sendIntent);

    }
}