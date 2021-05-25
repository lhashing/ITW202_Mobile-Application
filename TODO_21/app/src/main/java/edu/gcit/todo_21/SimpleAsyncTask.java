package edu.gcit.todo_21;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

//Performs a very simple background task, in this case, just sleeps!
public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {

    // The weak reference TextView where we will show results
    private WeakReference<TextView> mTextView;

    // Constructor that provides a weak reference to the TextView from the MainActivity
    public SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }

    /*
     * Runs on the background thread.
     *
     * @param voids No parameters in this use case.
     * @return Returns the string including the amount of time that
     * the background thread slept.
     */
    @Override
    protected String doInBackground(Void... voids) {
        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have time to rotate the phone while it is running
        int s = n * 200;

        // Sleep for the random amount of time
        // Handle Exception
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    // Does something with the result on the UI thread; in this case updates the TextView.
    protected void onPostExecute(String s) {
        mTextView.get().setText(s);
    }
}
