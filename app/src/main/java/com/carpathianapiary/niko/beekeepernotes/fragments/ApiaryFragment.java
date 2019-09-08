package com.carpathianapiary.niko.beekeepernotes.fragments;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carpathianapiary.niko.beekeepernotes.R;
import com.carpathianapiary.niko.beekeepernotes.adapters.RecyclerAdapter;
import com.carpathianapiary.niko.beekeepernotes.models.Apiary;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import static android.R.attr.id;
import static com.carpathianapiary.niko.beekeepernotes.activities.StartActivity.MODE_CLEAN_ITEM;

/**
 *
 */
@EFragment
public class ApiaryFragment extends Fragment {
    public static final int DADAN = 1;
    public static final int UKRAINIAN = 2 ;
    private int mFragId;
    public RecyclerView recyclerView = null;
    @FragmentArg
    Apiary apiary;
    public View fragmentView;
    private int modeType = MODE_CLEAN_ITEM;
    private RecyclerAdapter recyclerAdapter;
    private int mQuantityOfColonies = 0;
    private int mTypeBeehive;
    private AlertDialog alertDialog;

    public ApiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_apiary, container, false);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        return fragmentView;
    }

    private void updateLayoutManager() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
            recyclerView.setHasFixedSize(true);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setHasFixedSize(true);
        }
    }

    @AfterViews
    public void afterViews(){
        updateLayoutManager();
        createList(modeType);
    }
    private void createList(int mode) {
        if (apiary != null) {
            recyclerAdapter = new RecyclerAdapter(apiary.getBeehives(), apiary.getNameApiary(), mode);
            recyclerView.setAdapter(recyclerAdapter);

        }
    }
    public void setData(Apiary apiary, int mode){
        this.apiary = apiary;
        this.mFragId = id;
        if (recyclerAdapter != null){
            recyclerAdapter.setList(apiary.getBeehives(), apiary.getNameApiary(), mode);
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.removeAllViews();
        }

    }
    public String getName(){
	   return apiary.getNameApiary();
    }

    public  void selectMode(int mode){

        if (recyclerAdapter != null) {
            recyclerAdapter.setList(apiary.getBeehives(), apiary.getNameApiary(), mode);
            recyclerAdapter.notifyDataSetChanged();


        }
    }
    public void dismissActionMode() {

        if (recyclerAdapter.ismMode()) {
            recyclerAdapter.removeSelection();  // remove selection
            recyclerAdapter.setNullToActionMode();
        }
    }

    public void showItem(int numberBeehive) {
        recyclerView.scrollToPosition(numberBeehive);

    }
}