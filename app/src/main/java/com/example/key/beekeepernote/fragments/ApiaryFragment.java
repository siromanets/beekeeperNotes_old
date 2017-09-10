package com.example.key.beekeepernote.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.adapters.RecyclerAdapter;
import com.example.key.beekeepernote.models.Apiary;

import static com.example.key.beekeepernote.activities.StartActivity.MODE_CLEAN_ITEM;

/**
 *
 */
public class ApiaryFragment extends android.support.v4.app.Fragment {
    public static final int DADAN = 1;
    public static final int UKRAINIAN = 2 ;
    private int mFragId;
    public RecyclerView recyclerView = null;
    public Apiary apiary;
    public FloatingActionButton buttonAddNewBeehive;
    private RecyclerView.LayoutManager mLayoutManager;
    public View fragmentView;
    private int modeType = MODE_CLEAN_ITEM;
    private RecyclerAdapter dataAdapter;

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
        fragmentView = inflater.inflate(R.layout.fragment_apiary, container, false);
         int id = fragmentView.getId();
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
       createList(modeType);
        return fragmentView;
    }

    private void createList(int mode) {
        if (apiary != null) {
            dataAdapter = new RecyclerAdapter(apiary.getBeehives(), apiary.getNameApiary(), mode);
            recyclerView.setAdapter(dataAdapter);
            buttonAddNewBeehive = (FloatingActionButton) fragmentView.findViewById(R.id.buttonAddNewBeehive);
            buttonAddNewBeehive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getFragmentManager();
                    NewBeehiveFragment dialogFragment = new NewBeehiveFragment_();
                    dialogFragment.setData(apiary);
                    dialogFragment.show(fm, "Sample Fragment");
                }
            });
        }
    }


    public void setData(Apiary apiary, int id){
        this.apiary = apiary;
        this.mFragId = id;

    }

    public  void selectMode(int mode){

        if (dataAdapter != null){
            modeType = mode;
       dataAdapter.notifyDataSetChanged();
        }else{
           modeType = mode;
        }
    }
}