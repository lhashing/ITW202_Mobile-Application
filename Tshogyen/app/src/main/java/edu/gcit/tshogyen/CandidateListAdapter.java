package edu.gcit.tshogyen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CandidateListAdapter extends RecyclerView.Adapter<CandidateListAdapter.CandidateViewHolder> {
    List<Candidates> candidatesList;
    Context mContext;

   // FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CandidateListAdapter(Context context, List<Candidates> candidateslist) {
        this.candidatesList = candidateslist;
        this.mContext = context;

    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.candidate_lists, parent, false);
        return new CandidateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {

        //Get the current sport
        Candidates currentCandidate = candidatesList.get(position);

        //Bind the data to the views
        holder.bindTo(currentCandidate);
        Picasso.get().load(currentCandidate.getImage())
                .fit()
                .centerCrop()
                .into(holder.image);

        ////

    }

    @Override
    public int getItemCount() {
        return candidatesList.size();
    }

    class CandidateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView t1, t2, t3, t4;
        ImageView image;
        Context mContext;
        Candidates CurrentCandidate;
        public CandidateViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            image = itemView.findViewById(R.id.image_recyclerView_id);

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this);
        }

        void bindTo(Candidates currentCandidate){
            //Populate the textviews with data
            t1.setText(currentCandidate.getFullName());
            t2.setText(currentCandidate.getCandidateEmail());
            t3.setText(currentCandidate.getCandidateID());
            t4.setText(currentCandidate.getCandidateRole());

            //Get the current sport
            CurrentCandidate = currentCandidate;

        }

        @Override
        public void onClick(View view) {
            //Set up the detail intent
            Intent manifestIntent = Candidates.starter(mContext, CurrentCandidate.getFullName());

            //Start the detail activity
            manifestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(manifestIntent);
        }
    }
}