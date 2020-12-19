package com.example.createanote;

import android.content.Context;
import android.location.GnssAntennaInfo;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private onItemClickedListener mListener;
    Context context;
    List<Notes> listOfNotes;

    public interface onItemClickedListener{
        void onItemClick(int Position);
        void onDeleteClick(int Position);
    }

    public void setOnItemClickListener(onItemClickedListener listener){
          mListener = listener;
    }


    public RecyclerViewAdapter(Context context, List<Notes> listOfNotes)
    {
        this.context=  context;
        this.listOfNotes = listOfNotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes notes = listOfNotes.get(position);

        holder.titleTextView.setText(notes.getTitle());
        holder.notesTextView.setText(notes.getText());
    }

    @Override
    public int getItemCount() {
        return listOfNotes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView titleTextView;
        MaterialTextView notesTextView;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            titleTextView = itemView.findViewById(R.id.TitleGet);
            notesTextView = itemView.findViewById(R.id.NotesGet);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null)
                    {
                        int Position = getAdapterPosition();

                        if(Position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(Position);
                        }
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null)
                    {
                        int Position = getAdapterPosition();

                        if(Position != RecyclerView.NO_POSITION){
                            mListener.onDeleteClick(Position);
                        }
                    }
                }
            });


        }
    }

}
