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
    private TextView ReplyHeaderText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditTextSend = findViewById(R.id.editText_send);
        RepliedText = findViewById(R.id.ReplyText);
        ReplyHeaderText = findViewById(R.id.ReplyHeader);

    }

    public void Send(View view) {
        Intent sendIntent = new Intent(this, MainActivity2.class);
        String message = EditTextSend.getText().toString();
        sendIntent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(sendIntent, TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String message = data.getStringExtra(MainActivity2.EXTRA_REPLY);
                ReplyHeaderText.setVisibility(View.VISIBLE);
                RepliedText.setText(message);
                RepliedText.setVisibility(View.VISIBLE);
            }
        }
    }
}