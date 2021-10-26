package com.example.noteme_app;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList note_Title, note_SubTitle, note_Context;
    CustomAdapter( Context context,
                   ArrayList note_Title,
                   ArrayList note_SubTitle,
                   ArrayList note_Context){
        this.context = context;
       // this.note_id = note_id;
        this.note_Title = note_Title;
        this. note_SubTitle = note_SubTitle;
        this. note_Context = note_Context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
       View view =  inflater.inflate(R.layout.my_notes, parent,false);
        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.note_title_txt.setText(String.valueOf(note_Title.get(position)));
        holder.note_subTitle_txt.setText(String.valueOf(note_SubTitle.get(position)));
        holder.note_context_txt.setText(String.valueOf(note_Context.get(position)));
        holder.cardBackground.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.test2,null));
       // holder.real_layourt.setBackgroundColor(R.color.test1);
    }

    @Override
    public int getItemCount() {

        return note_Title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_id_txt, note_title_txt, note_subTitle_txt, note_context_txt;
       // Layout rel_layout;
        CardView cardBackground;
        RelativeLayout real_layourt;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title_txt = itemView.findViewById(R.id.viewTitle);
            note_subTitle_txt = itemView.findViewById(R.id.viewSubtitle);
            note_context_txt= itemView.findViewById(R.id.viewNote_context);
            cardBackground = itemView.findViewById(R.id.backgroundCard);
            real_layourt = itemView.findViewById(R.id.rel_layout);
            //rel_layout = itemView.findViewById(R.id.rel_layout);
        }
    }
}
