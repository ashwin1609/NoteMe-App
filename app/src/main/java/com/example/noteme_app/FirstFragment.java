package com.example.noteme_app;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteme_app.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    MyDatabase database;
    ArrayList<String> note_id, note_Title, note_SubTitle, note_Context, note_color;
    CustomAdapter customAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        database = new MyDatabase(getContext());


        note_id = new ArrayList<>();
        note_Title = new ArrayList<>();
        note_SubTitle = new ArrayList<>();
        note_Context = new ArrayList<>();
        note_color = new ArrayList<>();

        DisplayNote();

        callAdapter(view);
        EditText search_txt = (EditText) view.findViewById(R.id.searchView);

        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //customAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String target = search_txt.getText().toString();
                DisplaySearchNote(target);
                callAdapter(view);
            }
        });

    }
    public void callAdapter(View view){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        customAdapter = new CustomAdapter(getContext(),note_Title, note_SubTitle, note_Context, note_color);
        recyclerView.setAdapter(customAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void DisplayNote() {
         Cursor cursor = database.getData();

        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "No available data in the database", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_Title.add(cursor.getString(1));
                note_SubTitle.add(cursor.getString(2));
                note_Context.add(cursor.getString(3));
                note_color.add(cursor.getString(4));

            }
          }
    }

    public void DisplaySearchNote(String target) {
        Cursor cursor = database.getSearchData(target);

        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "No available data in the database", Toast.LENGTH_SHORT).show();
        }else{
            note_id.clear();
            note_Title.clear();
            note_Context.clear();
            note_SubTitle.clear();
            note_color.clear();

            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_Title.add(cursor.getString(1));
                note_SubTitle.add(cursor.getString(2));
                note_Context.add(cursor.getString(3));
                note_color.add(cursor.getString(4));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}