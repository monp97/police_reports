package com.example.policestation;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>{

    List<Transaction> persons;

    RecyclerViewAdapter(List<Transaction> persons){
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {

        personViewHolder.date.setText(persons.get(i).date);
        personViewHolder.ccd_done.setText(persons.get(i).done_ccd+"");
        personViewHolder.ccd_pending.setText(persons.get(i).pending_ccd+"");
        personViewHolder.summon_pending.setText(persons.get(i).pending_summon+"");
        personViewHolder.summon_done.setText(persons.get(i).done_summon+"");

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView date;
            TextView ccd_done;
            TextView ccd_pending;
            TextView summon_done;
            TextView summon_pending;

            PersonViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                date=(TextView)itemView.findViewById(R.id.date);
                ccd_done = (TextView)itemView.findViewById(R.id.done_ccd_val);
                ccd_pending= (TextView)itemView.findViewById(R.id.pending_ccd_val);
                summon_done=(TextView)itemView.findViewById(R.id.done_summon_val);
                summon_pending=(TextView)itemView.findViewById(R.id.pending_summon_val);

            }
        }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    }

