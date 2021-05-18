package edu.gcit.todo_19;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.DrawableRes;

public class Sport {

    //Member variables representing the title, image and information about the sport
    private final String title;
    private final String info;
    private final int imageResource;

    static final String TITLE_KEY = "Title";
    static final String IMAGE_KEY = "Image Resource";

    public Sport(String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getImageResource() {
        return imageResource;
    }

    public static Intent starter(Context context, String title, @DrawableRes int imageResId) {
        Intent detailIntent = new Intent(context, DetailActivity.class);
        detailIntent.putExtra(TITLE_KEY, title);
        detailIntent.putExtra(IMAGE_KEY, imageResId);
        return detailIntent;
    }
}
