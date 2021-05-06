package edu.gcit.todo_14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] phone = {"Home", "Work", "Mobile", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Get the intent and its data.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.order_message);
        textView.setText(message);

        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, phone);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);

//        Intent phone = new Intent(Intent.ACTION_DIAL);
//        phone.setData(Uri.parse("tel:+975 17242393"));
//        startActivity(phone);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Toast.makeText(getApplicationContext(), phone[pos], Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.option1:
                if (checked)
                    Toast.makeText(OrderActivity.this, R.string.some_day_messenger_service, Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:
                if (checked)
                    Toast.makeText(OrderActivity.this, R.string.next_day_ground_delivery, Toast.LENGTH_SHORT).show();
                break;
            case R.id.option3:
                if (checked)
                    Toast.makeText(OrderActivity.this, R.string.pick_up, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}