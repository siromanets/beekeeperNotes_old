package com.example.key.beekeepernote;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.key.beekeepernote.database.Apiary;

/**
 *
 */
public class ApiaryFragment extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    public Apiary apiary;
    RecyclerView.LayoutManager mLayoutManager;


    public ApiaryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_apiary, container, false);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        Log.d("debugMode", "The application stopped after this");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        RecyclerAdapter dataAdapter = new RecyclerAdapter(apiary.getBeehives());
        recyclerView.setAdapter(dataAdapter);
        return fragmentView;

    }


    public void setData(Apiary apiary){
        this.apiary = apiary;

    }

}