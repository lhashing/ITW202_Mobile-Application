package edu.gcit.todo_19;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Initialize the views
        TextView sportsTitle = (TextView)findViewById(R.id.titleDetail);
        ImageView sportsImage = (ImageView)findViewById(R.id.sportsImageDetail);

        //Get the drawable
        Drawable drawable = ContextCompat.getDrawable
                (this,getIntent().getIntExtra(Sport.IMAGE_KEY, 0));

        //Create a placeholder gray scrim while the image loads
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.GRAY);

        //Make it the same size as the image
        if(drawable!=null) {
            gradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

        //Set the text from the Intent extra
        sportsTitle.setText(getIntent().getStringExtra(Sport.TITLE_KEY));

        //Load the image using the glide library and the Intent extra
        Glide.with(this).load(getIntent().getIntExtra(Sport.IMAGE_KEY,0))
                .placeholder(gradientDrawable).into(sportsImage);
    }
}