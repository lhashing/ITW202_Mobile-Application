package edu.gcit.tshogyen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.VoteViewHolder> {
    public  Integer clickedPosition;
    List<Candidates> voteList;
    CCvote ccvote;
    LayoutInflater inflater;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FieldValue increment = FieldValue.increment(1);

    public VoteListAdapter(Context context, List<Candidates> votelist) {
        this.voteList = votelist;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vote = inflater.inflate(R.layout.vote_list, parent, false);
        VoteViewHolder viewHolder = new VoteViewHolder(vote);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {

        //Get the current sport
        Candidates currentVote = voteList.get(position);

        //Bind the data to the views
        holder.bindTo(currentVote);
        Picasso.get().load(currentVote.getImage())
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return voteList.size();
    }

    class VoteViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView t1;
        TextView t4;
        Button vote;
        Candidates CurrentVote;
        DocumentReference documentReference;
        public VoteViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t4 = itemView.findViewById(R.id.t4);
            image = itemView.findViewById(R.id.image_voteView);
            vote = itemView.findViewById(R.id.voteButton);

            vote.setOnClickListener(new View.OnClickListener() {
                @Nullable
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick: Voted for Candidate " + CurrentVote.FullName);
                    documentReference = fStore.collection(CurrentVote.CandidateRole).document(CurrentVote.getId());
                    documentReference.update("Votes", increment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("TAG", "onComplete: Vote Success");
                            Toast.makeText(inflater.getContext(), "Vote Successfully Counted.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Error!" + e.toString());
                            Toast.makeText(inflater.getContext(), "Error !" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(inflater.getContext(), R.style.Theme_AppCompat_Dialog_Alert);
//                    builder1.setTitle("Vote");
//                    builder1.setCancelable(true);
//                    builder1.setMessage("Are you sure to vote ?");
//                    ccvote.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//
//                    builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // close the dialog
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            vote.setEnabled(false);
//                            Log.d("TAG", "onClick: Voted for Candidate " + CurrentVote.FullName);
//                            documentReference = fStore.collection(CurrentVote.CandidateRole).document(CurrentVote.getId());
//                            documentReference.update("Votes", increment).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Log.d("TAG", "onComplete: Vote Success");
//                                    Toast.makeText(inflater.getContext(), "Vote Successfully Counted.", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("TAG", "onFailure: Error!"+e.toString());
//                                    Toast.makeText(inflater.getContext(), "Error !" + e.toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    });
//
//                    AlertDialog alertDialog = builder1.create();
//                    alertDialog.show();
//                }
            });
        }

        void bindTo(Candidates currentVote){
            //Populate the textviews with data
            t1.setText(currentVote.getFullName());
            t4.setText(currentVote.getCandidateID());

            //Get the current vote
            CurrentVote = currentVote;

        }
    }
}