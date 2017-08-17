package com.example.key.beekeepernote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.key.beekeepernote.database.Beehive;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Set;


/**
 *
 */

@EFragment
public class ToolsListFragment extends DialogFragment {
    Beehive mBeehive;
    View mView;
    String mNameApiary;
    Communicator mCommunicator;
    boolean cheсkMarkFew = false;
    Set<Beehive> mBeehiveSet = new ArraySet<>();
    Set<View> mViewSet = new ArraySet<>();


    @ViewById(R.id.buttonMark)
    Button buttonMark;

    @ViewById(R.id.buttonCopy)
    Button buttonCopy;

    @ViewById(R.id.buttonCut)
    Button buttonCut;

    @ViewById(R.id.buttonPaste)
    Button buttonPaste;

    @ViewById(R.id.buttonDelete)
    Button buttonDelete;



    public ToolsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_tools_list, container, false);
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!cheсkMarkFew){
            mView.setAlpha((float) 1);
        }
        super.onDismiss(dialog);

    }

    @Click(R.id.buttonMark)
    void buttonMarkWasClicked(){
        cheсkMarkFew = true;
        mBeehiveSet.add(mBeehive);
        mViewSet.add(mView);
        this.dismiss();
    }

    @Click(R.id.buttonCopy)
    void buttonCopyWasClicked(){
        cheсkMarkFew = false;
        this.dismiss();
    }

    @Click(R.id.buttonCut)
    void buttonCutWasClicked(){
        cheсkMarkFew = false;
        this.dismiss();
    }

    @Click(R.id.buttonPaste)
    void buttonPasteWasClicked(){
        cheсkMarkFew = false;
        this.dismiss();
    }

    @Click(R.id.buttonDelete)
    void buttonDeleteWasClicked(){
        mCommunicator = (Communicator)getActivity();
        mCommunicator.deleteBeehive(mBeehive, mNameApiary);
        this.dismiss();
    }
    void setData(Beehive beehive,View view, String nameApiary){
        this.mBeehive = beehive;
        this.mNameApiary = nameApiary;
        this.mView = view;
    }

    }
