package com.carpathianapiary.niko.beekeepernotes.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.carpathianapiary.niko.beekeepernotes.R;
import com.carpathianapiary.niko.beekeepernotes.adapters.NotifactionReciclerAdapter;
import com.carpathianapiary.niko.beekeepernotes.models.Notifaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.ArrayList;


/**
 *
 */
@EFragment
public class HistoryFragment extends Fragment {
    View view;
    @FragmentArg
    ArrayList<Notifaction> notificationList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private NotifactionReciclerAdapter adapter;
    private LinearLayout emptyView;

    public HistoryFragment() {
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
        view = inflater.inflate(R.layout.fragment_history, container, false);
        emptyView = (LinearLayout)view.findViewById(R.id.emptyViewForNot);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHistory);
        mLayoutManager = new LinearLayoutManager(getContext());
        Log.d("debugMode", "The application stopped after this");
        recyclerView.setLayoutManager(mLayoutManager);
        if (notificationList != null) {
            adapter = new NotifactionReciclerAdapter(notificationList);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @AfterViews
    void afterViews(){

    }
    public void setDate(ArrayList<Notifaction> notificationList){
        this.notificationList = notificationList;
        if (adapter != null & notificationList != null) {
            adapter.setList(notificationList);
            adapter.notifyDataSetChanged();
            if (notificationList.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        }
    }
}
