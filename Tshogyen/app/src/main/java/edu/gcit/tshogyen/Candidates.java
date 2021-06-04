package edu.gcit.tshogyen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Candidates {

    String id, FullName, CandidateID, CandidateEmail, CandidateRole, Image;

    static final String NAME_KEY = "CandidateName";

    public Candidates() {
    }

    public Candidates(String id, String FullName, String CandidateEmail, String CandidateID, String CandidateRole, String Image) {
        this.id = id;
        this.FullName = FullName;
        this.CandidateEmail = CandidateEmail;
        this.CandidateID = CandidateID;
        this.CandidateRole = CandidateRole;
        this.Image = Image;
    }

    public String getImage(){
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getCandidateID() {
        return CandidateID;
    }

    public void setCandidateID(String candidateID) {
        this.CandidateID = candidateID;
    }

    public String getCandidateEmail() {
        return CandidateEmail;
    }

    public void setCandidateEmail(String candidateEmail) {
        this.CandidateEmail = candidateEmail;
    }

    public String getCandidateRole() {
        return CandidateRole;
    }

    public void setCandidateRole(String candidateRole) {
        this.CandidateRole = candidateRole;
    }

    public static Intent starter(Context context, String CandidateName) {
        Intent detailIntent = new Intent(context, ManifestActivity.class);
        detailIntent.putExtra(NAME_KEY, CandidateName);
        return detailIntent;
    }
}
