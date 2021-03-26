package edu.gcit.todo_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    public static final String EXTRA_REPLY = "edu.gcit.todo_6.REPLY";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

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
        Log.d(LOG_TAG, "End MainActivity2");
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}