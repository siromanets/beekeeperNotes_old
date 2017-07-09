package com.example.key.beekeepernote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.key.beekeepernote.database.Apiary;

/**
 *
 */
public class ApiaryFragment extends android.support.v4.app.Fragment {
    GridView galleryOfBeehive;
    public Apiary apiary;


    public ApiaryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_apiary, container, false);
        galleryOfBeehive = (GridView) fragmentView.findViewById(R.id.galleryOfBeehive);

        return fragmentView;

    }

    public void setData(Apiary apiary){
        this.apiary = apiary;
        DataAdapter adapter = new DataAdapter(getContext(), apiary.getBeehives() );
        galleryOfBeehive.setAdapter(adapter);
    }

}
