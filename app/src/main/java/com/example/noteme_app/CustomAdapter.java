package com.example.noteme_app;

import android.content.Context;
import android.graphics.Color;
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

    private ArrayList note_Title, note_SubTitle, note_Context, note_color;

    CustomAdapter( Context context,
                   ArrayList note_Title,
                   ArrayList note_SubTitle,
                   ArrayList note_Context,
                   ArrayList note_color){
        this.context = context;
        this.note_Title = note_Title;
        this. note_SubTitle = note_SubTitle;
        this. note_Context = note_Context;
        this.note_color = note_color;

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
        if ( position >= 0 && position <= note_color.size() - 1) {
            holder.cardBackground.setBackgroundColor(Color.parseColor(String.valueOf(note_color.get(position))));
        }
    }

    @Override
    public int getItemCount() {

        return note_Title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_title_txt, note_subTitle_txt, note_context_txt;
        CardView cardBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title_txt = itemView.findViewById(R.id.viewTitle);
            note_subTitle_txt = itemView.findViewById(R.id.viewSubtitle);
            note_context_txt= itemView.findViewById(R.id.viewNote_context);
            cardBackground = itemView.findViewById(R.id.backgroundCard);
        }
    }
}
