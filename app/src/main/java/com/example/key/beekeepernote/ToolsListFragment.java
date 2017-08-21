package com.example.key.beekeepernote;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.key.beekeepernote.database.Beehive;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Set;


/**
 *
 */

@EFragment
public class ToolsListFragment extends DialogFragment {

    View mView;
    String mNameApiary;
    Communicator mCommunicator;
    boolean cheсkMarkFew = false;
    Set<Beehive> mBeehiveSet = new ArraySet<>();
    Set<View> mViewSet = new ArraySet<>();
    boolean moveChecker = false;


    @ViewById(R.id.buttonMark)
    LinearLayout buttonMark;

    @ViewById(R.id.buttonMove)
    LinearLayout buttonCopy;

    @ViewById(R.id.buttonDelete)
    LinearLayout buttonDelete;

    @ViewById(R.id.linearLayoutForTools)
    LinearLayout linearLayoutForTools;


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
        mViewSet.add(mView);
    }


    @Click(R.id.buttonMove)
    void buttonMoveWasClicked(){
        buttonDelete.setAlpha((float) 0.5);
        buttonDelete.setClickable(false);
        buttonMark.setAlpha((float) 0.5);
        buttonMark.setClickable(false);
        moveChecker = true;
        Toast.makeText(getContext(),
                "Будь ласка виберіть вулик біля якого ви хочете розмістити ці вулики "
                ,Toast.LENGTH_SHORT).show();
        cheсkMarkFew = false;
    }

    @Click(R.id.buttonDelete)
    void buttonDeleteWasClicked(View view){
        mCommunicator = (Communicator)getActivity();
        mCommunicator.deleteBeehive(mBeehiveSet, mNameApiary);
        this.dismiss();
    }


    void setData(Beehive beehive,View view, String nameApiary) {
        if (moveChecker) {
            mCommunicator = (Communicator) getActivity();
            mCommunicator.deleteBeehive(mBeehiveSet, mNameApiary);
            mCommunicator.moveBeehive(mBeehiveSet, beehive, mNameApiary, nameApiary);
            this.dismiss();
        } else {
            mBeehiveSet.add(beehive);
            this.mNameApiary = nameApiary;
            this.mView = view;
            if (!mViewSet.add(view)) {
                mViewSet.remove(view);
                mView.setAlpha((float) 1);
            }
        }
    }
}
