package com.example.noteme_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.noteme_app.databinding.FragmentSecondBinding;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    Spinner mySpinner;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        // getting all the information;
        TextView title = view.findViewById(R.id.Title);
        TextView  subtitle=  view.findViewById(R.id.Subtitle);
        TextView note_Context = view.findViewById(R.id.type_notes);

        mySpinner =  view.findViewById(R.id.dropDown);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dropDrownMenu));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        binding.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note_color;
                String title1 = title.getText().toString().trim();

                if(title1.length() == 0){
                    title.setError("Title missing");
                }else{
                    MyDatabase database = new MyDatabase(getContext());
                    note_color = getColor(mySpinner.getSelectedItem().toString().trim());
                    title.setError(note_color);
                    database.addNote(title1,
                            subtitle.getText().toString().trim(),
                            note_Context.getText().toString().trim(),
                            note_color);
                    NavHostFragment.findNavController(SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            }
        });
    }

    public String getColor( String color ){
        String temp = " ";
        if( color.equals("Yellow")){
            temp = "#ffff99";
        } else if( color.equals("Purple")){
            temp = "#cc99ff";
        } else if( color.equals("Red")){
             temp = "#ff9999";
        } else if( color.equals("Green")){
            temp = "#b3e6b3";
        }
        return temp;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}