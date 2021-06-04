package edu.gcit.tshogyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private static final Pattern EMAIL_ADDRESS = Pattern.compile
            ("^[0-9]+\\.gcit@rub\\.edu\\.bt",Pattern.CASE_INSENSITIVE);
    EditText userid, userName, userEmail, userPassword, confirmPassword;
    TextView gotologin;
    private CheckBox showpassword;
    Button userregister_Btn;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    ProgressDialog pd;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        userid = findViewById(R.id.uid);
        userName = findViewById(R.id.user_fullname);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        confirmPassword = findViewById(R.id.confirm_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TSHOGYEN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userregister_Btn = findViewById(R.id.userregister_btn);
        gotologin = findViewById(R.id.login_text);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        userregister_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = userid.getText().toString();
                String user_email = userEmail.getText().toString();
                String user_password = userPassword.getText().toString();
                String confirm_password = confirmPassword.getText().toString();
                String user_Name = userName.getText().toString();

                if (TextUtils.isEmpty(user_id)) {
                    userid.setError("User ID is Required.");
                    return;
                }
                if (user_id.length() < 8) {
                    userid.setError("UID Must be >= 8 Characters");
                    return;
                }

                if (TextUtils.isEmpty(user_Name)) {
                    userName.setError("Full Name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(user_email)) {
                    userEmail.setError("Email is Required.");
                    return;
                }
                if (!EMAIL_ADDRESS.matcher(user_email).matches()) {
                    userEmail.setError("Please enter a valid email address");
                    return;
                }

                if (TextUtils.isEmpty(user_password)) {
                    userPassword.setError("Password is Required.");
                    return;
                }

                if (user_password.length() < 6) {
                    userPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                if (TextUtils.isEmpty(confirm_password)) {
                    confirmPassword.setError("Confirm Password is Required.");
                    return;
                }

                if (!user_password.equals(confirm_password)) {
                    confirmPassword.setError("Password Do not match.");
                }

                pd.setTitle("User Registering...");
                pd.show();

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(RegistrationActivity.this, "User Registered. Verification Email Has been Sent." +
                                            "\nVerify Your Email To Login.", Toast.LENGTH_LONG).show();
                                    userid.setText("");
                                    userName.setText("");
                                    userEmail.setText("");
                                    userPassword.setText("");
                                    confirmPassword.setText("");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("UserID", user_id);
                            user.put("FullName", user_Name);
                            user.put("UserEmail", user_email);
                            user.put("isUser", "0");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user Profile is created");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: " + e.toString());
                                }
                            });

                        } else {
                            pd.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
    }

}