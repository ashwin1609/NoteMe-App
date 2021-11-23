package com.example.noteme_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    int pos;
    private final ArrayList note_Title;
    private final ArrayList note_SubTitle;
    private final ArrayList note_Context;
    private final ArrayList note_color;
    private final ArrayList note_id;
    private final ArrayList note_image;
    Activity activity;
    CustomAdapter( Activity activity, Context context,
                   ArrayList note_id,
                   ArrayList note_Title,
                   ArrayList note_SubTitle,
                   ArrayList note_Context,
                   ArrayList note_color,
                   ArrayList note_image){
        this.activity = activity;
        this.context = context;
        this.note_id = note_id;
        this.note_Title = note_Title;
        this. note_SubTitle = note_SubTitle;
        this. note_Context = note_Context;
        this.note_color = note_color;
        this.note_image = note_image;
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
           // holder.cardBackground.setBackgroundColor(Color.parseColor(String.valueOf(note_color.get(position))));
            holder.relativeLayout.setBackgroundColor(Color.parseColor(String.valueOf(note_color.get(position))));
        }
        holder.cardBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = holder.getAdapterPosition();
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id", String.valueOf(note_id.get(pos)));
                intent.putExtra("title", String.valueOf(note_Title.get(pos)));
                intent.putExtra("subtitle", String.valueOf(note_SubTitle.get(pos)));
                intent.putExtra("context", String.valueOf(note_Context.get(pos)));
                intent.putExtra("color", String.valueOf(note_color.get(pos)));

                //context.startActivity(intent);
                activity.startActivityForResult(intent, 1);
            }
        });
        if (!note_image.get(position).equals("NoImage")){
            holder.imageSelection.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(note_image.get(position))));
            holder.imageSelection.setVisibility(View.VISIBLE);
        }else {
            holder.imageSelection.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return note_Title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_title_txt, note_subTitle_txt, note_context_txt;
        CardView cardBackground;
        ImageView imageSelection;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title_txt = itemView.findViewById(R.id.viewTitle);
            note_subTitle_txt = itemView.findViewById(R.id.viewSubtitle);
            note_context_txt= itemView.findViewById(R.id.viewNote_context);
            cardBackground = itemView.findViewById(R.id.backgroundCard);
            imageSelection = itemView.findViewById(R.id.image_selected_view);
            relativeLayout = itemView.findViewById(R.id.rel_layout);

        }
    }
}
