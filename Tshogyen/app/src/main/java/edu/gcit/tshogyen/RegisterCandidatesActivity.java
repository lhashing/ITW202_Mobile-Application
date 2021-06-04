package edu.gcit.tshogyen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class RegisterCandidatesActivity extends AppCompatActivity {
    private static final Pattern EMAIL_ADDRESS = Pattern.compile("^[0-9]+\\.gcit@rub\\.edu\\.bt",Pattern.CASE_INSENSITIVE);
    EditText candidateID, candidateName, candidateEmail, candidateRole;
    Button register_Btn, show_list_btn;
    FirebaseFirestore db;
    ImageButton imageButton;
    ProgressDialog pd;
    private static final int PICK_IMAGE = 1;
    Uri imageUrl = null;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    String uid, uFullName, uCandidateID, uCandidateEmail, uCandidateRole;
    UploadTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_candidates);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TSHOGYEN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageButton = findViewById(R.id.imageButton);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Candidate_Images");

        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);

        register_Btn = findViewById(R.id.register_btn);

        candidateID = findViewById(R.id.user_id);
        candidateName = findViewById(R.id.user_fullname);
        candidateEmail = findViewById(R.id.user_email);
        candidateRole = findViewById(R.id.candidate_role);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //Update Data
            toolbar.setTitle("Update Candidate");
            register_Btn.setText("Update");
            //get data
            uid = bundle.getString("uid");
            uFullName = bundle.getString("uFullName");
            uCandidateEmail = bundle.getString("uCandidateEmail");
            uCandidateID = bundle.getString("uCandidateID");
            uCandidateRole = bundle.getString("uCandidateRole");

            //set data
            candidateName.setText(uFullName);
            candidateEmail.setText(uCandidateEmail);
            candidateID.setText(uCandidateID);
            candidateRole.setText(uCandidateRole);
        }
        else {
            //New Data
            register_Btn.setText("Register");
        }

        show_list_btn = findViewById(R.id.show_list);
        show_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View  v) {
                pd.setTitle("Loading Candidates...");
                startActivity(new Intent(getApplicationContext(), UpdateCandidatesActivity.class));
                finish();
            }
        });

        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String candidate_id = candidateID.getText().toString();
                String candidate_name = candidateName.getText().toString();
                String candidate_email = candidateEmail.getText().toString();
                String candidate_role = candidateRole.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    //updating
                    //input candidate
                    String id = uid;

                    //function call to update candidate
                    switch (candidate_role) {
                        case "Chief Councillor":
                            updateChiefCouncillor(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Boy Resident Councillor":
                            updateBoyResidentCouncillor(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Girl Resident Councillor":
                            updateGirlResidentCouncillor(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Boy Cultural Coordinator":
                            updateBoyCulturalCoordinator(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Girl Cultural Coordinator":
                            updateGirlCulturalCoordinator(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Boy Games & Sports Coordinator":
                            updateBoyGamesandSportsCoordinator(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Girl Games & Sports Coordinator":
                            updateGirlGamesandSportsCoordinator(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Boy Prayer Coordinator":
                            updateBoyPrayerCoordinator(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                        case "Girl Prayer Coordinator":
                            updateGirlPrayerCoordinator(id, candidate_id, candidate_name, candidate_email, candidate_role);
                            break;
                    }

                }
                else {
                    //adding new input data
                    String id = UUID.randomUUID().toString();
                    registerCandidate(id, candidate_id, candidate_name, candidate_email, candidate_role);
                }
            }
        });
    }

    private void updateGirlPrayerCoordinator(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Girl Prayer Coordinator").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGirlGamesandSportsCoordinator(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Girl Games & Sports Coordinator").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGirlCulturalCoordinator(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Girl Cultural Coordinator").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ChooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
        data != null || data.getData() != null) {
            imageUrl = data.getData();

            Picasso.get().load(imageUrl).into(imageButton);
        }
    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void registerCandidate(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        pd.setTitle("Registering Candidates...");
        pd.show();

        final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUrl));

        uploadTask = reference.putFile(imageUrl);

        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    Map<String, Object> candidate = new HashMap<>();
                    candidate.put("id", id); //id of candidate
                    candidate.put("CandidateID", candidate_id);
                    candidate.put("FullName", candidate_name);
                    candidate.put("CandidateEmail", candidate_email);
                    candidate.put("CandidateRole", candidate_role);
                    candidate.put("Uri", downloadUri.toString());
                    candidate.put("Votes", 0);

                    //register this candidate
                    db.collection(candidateRole.getText().toString()).document(id).set(candidate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //this will be called when candidate is registered successfully
                                    pd.dismiss();
                                    Toast.makeText(RegisterCandidatesActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //this will be called if there is any error while registering
                            pd.dismiss();
                            Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void updateBoyPrayerCoordinator(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Boy Prayer Coordinator").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBoyGamesandSportsCoordinator(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Boy Games & Sports Coordinator").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBoyCulturalCoordinator(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Boy Cultural Coordinator").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBoyResidentCouncillor(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Boy Resident Councillor").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateChiefCouncillor(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Chief Councillor").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGirlResidentCouncillor(String id, String candidate_id, String candidate_name, String candidate_email, String candidate_role) {
        if(TextUtils.isEmpty(candidate_id)){
            candidateID.setError("Candidate ID is Required.");
            return;
        }
        if (candidate_id.length() < 8) {
            candidateID.setError("UID Must be >= 8 Characters");
            return;
        }
        if(TextUtils.isEmpty(candidate_name)){
            candidateName.setError("Candidate Name is Required.");
            return;
        }
        if(TextUtils.isEmpty(candidate_email)){
            candidateEmail.setError("Candidate Email is Required.");
            return;
        }
        if (!EMAIL_ADDRESS.matcher(candidate_email).matches()) {
            candidateEmail.setError("Please enter a valid email address");
            return;
        }
        if(TextUtils.isEmpty(candidate_role)){
            candidateRole.setError("Candidate Email is Required.");
            return;
        }
        if(!candidateRole.getText().toString().equals("Chief Councillor")){
            if(!candidateRole.getText().toString().equals("Boy Resident Councillor")){
                if(!candidateRole.getText().toString().equals("Girl Resident Councillor")){
                    if(!candidateRole.getText().toString().equals("Boy Cultural Coordinator")){
                        if(!candidateRole.getText().toString().equals("Girl Cultural Coordinator")){
                            if(!candidateRole.getText().toString().equals("Boy Games & Sports Coordinator")){
                                if(!candidateRole.getText().toString().equals("Girl Games & Sports Coordinator")){
                                    if(!candidateRole.getText().toString().equals("Boy Prayer Coordinator")){
                                        if(!candidateRole.getText().toString().equals("Girl Prayer Coordinator")){
                                            candidateRole.setError("Enter Valid Role");
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        pd.setTitle("Updating Candidates...");
        pd.show();

        db.collection("Girl Resident Councillor").document(id)
                .update("CandidateID", candidate_id,"FullName", candidate_name,  "CandidateEmail", candidate_email, "CandidateRole", candidate_role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, "Candidate Successfully Updated!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(RegisterCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        if(item.getItemId() == R.id.resetPassword){
            startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
        }
        if(item.getItemId() == R.id.user_profile){
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }
        if(item.getItemId() == R.id.about){
            startActivity(new Intent(getApplicationContext(), AboutPage.class));
        }
        return super.onOptionsItemSelected(item);
    }

}