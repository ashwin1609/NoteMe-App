package com.example.noteme_app;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.noteme_app.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    MyDatabase database;
    ArrayList<String> note_id, note_Title, note_SubTitle, note_Context;

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

        DisplayNote();
    }

        void DisplayNote() {
             Cursor cursor = database.getData();

            if(cursor.getCount() == 0){
                Toast.makeText(getContext(), "NO availabe data in the database", Toast.LENGTH_SHORT).show();
            }else{
                while  (cursor.moveToNext()){
                    note_id.add(cursor.getString(0));
                    note_Title.add(cursor.getString(1));
                    note_SubTitle.add(cursor.getString(2));
                    note_Context.add(cursor.getString(3));
                }
            }

        }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}