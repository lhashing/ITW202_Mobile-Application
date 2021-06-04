package edu.gcit.tshogyen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateViewHolder> {

    UpdateCandidatesActivity updateCandidatesActivity;
    List<Candidates> candidatesList;

    public UpdateAdapter(UpdateCandidatesActivity updateCandidatesActivity, List<Candidates> candidatesList) {
        this.updateCandidatesActivity = updateCandidatesActivity;
        this.candidatesList = candidatesList;
    }

    @NonNull
    @Override
    public UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidate_lists, parent, false);

        UpdateViewHolder updateViewHolder = new UpdateViewHolder(itemView);
        //handle item clicks here
        updateViewHolder.setOnClickListener(new UpdateViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item
                Toast.makeText(updateCandidatesActivity, "Long Press to Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                //this will be called when user long click item

                //creating AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(updateCandidatesActivity);
                //options to display in dialog
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            //update is clicked
                            Candidates candidates = candidatesList.get(position);
                            Bundle candidatebundle = new Bundle();
                            candidatebundle.putString("uid" , candidates.getId());
                            candidatebundle.putString("uFullName" , candidates.getFullName());
                            candidatebundle.putString("uCandidateEmail" , candidates.getCandidateEmail());
                            candidatebundle.putString("uCandidateID" , candidates.getCandidateID());
                            candidatebundle.putString("uCandidateRole" , candidates.getCandidateRole());
                            candidatebundle.putString("uUri", candidates.getImage());

                            Intent intent = new Intent(updateCandidatesActivity, RegisterCandidatesActivity.class);
                            intent.putExtras(candidatebundle);
                            updateCandidatesActivity.startActivity(intent);

                        }
                        if (which == 1){
                            //delete is clicked
                            Candidates candidates = candidatesList.get(position);
                            String candidate_role = candidates.getCandidateRole();
                            switch (candidate_role) {
                                case "Chief Councillor":
                                    updateCandidatesActivity.deleteChiefCouncillor(position);
                                    break;
                                case "Boy Resident Councillor":
                                    updateCandidatesActivity.deleteBoyResidentCouncillor(position);
                                    break;
                                case "Girl Resident Councillor":
                                    updateCandidatesActivity.deleteGirlResidentCouncillor(position);
                                    break;
                                case "Boy Cultural Coordinator":
                                    updateCandidatesActivity.deleteBoyCulturalCoordinator(position);
                                    break;
                                case "Girl Cultural Coordinator":
                                    updateCandidatesActivity.deleteGirlCulturalCoordinator(position);
                                    break;
                                case "Boy Games & Sports Coordinator":
                                    updateCandidatesActivity.deleteBoyGamesandSportsCoordinator(position);
                                    break;
                                case "Girl Games & Sports Coordinator":
                                    updateCandidatesActivity.deleteGirlGamesandSportsCoordinator(position);
                                    break;
                                case "Boy Prayer Coordinator":
                                    updateCandidatesActivity.deleteBoyPrayerCoordinator(position);
                                    break;
                                case "Girl Prayer Coordinator":
                                    updateCandidatesActivity.deleteGirlPrayerCoordinator(position);
                                    break;
                            }
                        }
                    }
                }).create().show();
            }
        });

        return updateViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateViewHolder holder, int position) {
        //bind views / set data
        holder.t1.setText(candidatesList.get(position).getFullName());
        holder.t2.setText(candidatesList.get(position).getCandidateEmail());
        holder.t3.setText(candidatesList.get(position).getCandidateID());
        holder.t4.setText(candidatesList.get(position).getCandidateRole());
        Picasso.get().load(candidatesList.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return candidatesList.size();
    }
}
