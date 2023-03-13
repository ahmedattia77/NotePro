package com.example.notepro;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    RecycleAdapterOnClickListener recycleAdapterOnClickListener;
//    RecycleAdapteronLongeClick recycleAdapteronLongeClick;
    public Adapter(List<Note>notes , RecycleAdapterOnClickListener recycleAdapterOnClickListener /*, RecycleAdapteronLongeClick recycleAdapteronLongeClick*/) {
        this.notes = notes;
        this.recycleAdapterOnClickListener = recycleAdapterOnClickListener;
//        this.recycleAdapteronLongeClick = recycleAdapteronLongeClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ContentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custon_note , null ,false);
        MyViewHolder myViewHolder = new MyViewHolder(ContentView);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext() , android.R.anim.slide_in_left);
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext() , android.R.anim.fade_in);

        Note notes  = this.notes.get(position);

        holder.title.setText(notes.getTitle());
        holder.description.setText(notes.getDescription());
        holder.date.setText(notes.getDate());
        holder.title.setTag(notes.getId());

        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.custom_title_tv);
            description = itemView.findViewById(R.id.custom_description_tv);
            date = itemView.findViewById(R.id.custom_date_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int currentNote = (int) title.getTag();
                    recycleAdapterOnClickListener.onClickItem(currentNote);
                }
            });
        }
    }
}

