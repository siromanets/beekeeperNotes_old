package com.example.key.beekeepernote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.androidannotations.annotations.EFragment;


/**
 *
 */
@EFragment
public class NewBlankFragment extends Fragment {
    View view;
    EditText nameApiary;
    EditText numberBeehive;
    String nameApiaryString;
    AlertDialog alertDialog;

    public NewBlankFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_blank, container, false);
        return view;
    }


}
