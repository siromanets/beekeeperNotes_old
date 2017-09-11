package com.example.key.beekeepernote.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.interfaces.Communicator;
import com.example.key.beekeepernote.models.Beehive;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 *
 */

@EFragment
public class ToolsListFragment extends DialogFragment {

    public static final boolean BEHIND = false;
    public static final boolean IN_FRONT = true;
    View mView;
    String mFromApiary;
    String mInApiary;
    Communicator mCommunicator;
    boolean noMoreFlag = false;
    Set<Beehive> mBeehiveSet = new ArraySet<>();
    Beehive mBeehive;
    Set<View> mViewSet = new ArraySet<>();
    boolean moveChecker = false;
    boolean moveB = false;

    @ViewById(R.id.textViewSelectAll)
    TextView textViewSelectAll;

    @ViewById(R.id.buttonSelectAll)
    LinearLayout buttonMark;

    @ViewById(R.id.buttonMove)
    LinearLayout buttonMove;

    @ViewById(R.id.buttonDelete)
    LinearLayout buttonDelete;

    @ViewById(R.id.buttonReplace)
    LinearLayout buttonReplace;

    @ViewById(R.id.linearLayoutForTools)
    LinearLayout linearLayoutForTools;

    @ViewById(R.id.questionGroup)
    LinearLayout questionGroup;

    @ViewById(R.id.buttonYes)
    Button buttonYes;

    @ViewById(R.id.buttonNo)
    Button buttonNo;

    @ViewById(R.id.infoGroup)
    LinearLayout infoGroup;

    @ViewById(R.id.textMessage)
    TextView textMessage;

    @ViewById(R.id.toolsButtonGroup)
    LinearLayout toolsButtonGroup;
    private boolean selectAllChecker = false;

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
    public void onDetach() {
        clearViewsBackground();
        super.onDetach();

    }


    @Click(R.id.buttonSelectAll)
    void buttonSelectAllWasClicked(){
        if (!selectAllChecker){
            mCommunicator = (Communicator)getActivity();
            mCommunicator.selectAll();
            clearViewsBackground();
            mBeehiveSet.clear();
            mViewSet.clear();
            selectAllChecker = true;
            textViewSelectAll.setText("Unselected All");
        }else {
            clearViewsBackground();
            mBeehiveSet.clear();
            mViewSet.clear();
            buttonReplace.setAlpha((float) 0.5);
            buttonReplace.setClickable(false);
            buttonDelete.setAlpha((float) 0.5);
            buttonDelete.setClickable(false);
            buttonMove.setAlpha((float) 0.5);
            buttonMove.setClickable(false);
            textViewSelectAll.setText("Select All");
            selectAllChecker = false;
        }
    }

    @Click(R.id.buttonReplace)
    void buttonReplaceWasClicked(){
        buttonDelete.setAlpha((float) 0.5);
        buttonDelete.setClickable(false);
        buttonMark.setAlpha((float) 0.5);
        buttonMark.setClickable(false);
        buttonMove.setAlpha((float) 0.5);
        buttonMove.setClickable(false);
        showMessage("Будь ласка виберіть вулик для заміни");
        mCommunicator = (Communicator)getActivity();
        mCommunicator.multiSelectMod();
        moveChecker = true;
    }

    private void showMessage(String s) {
        toolsButtonGroup.setVisibility(View.GONE);
        questionGroup.setVisibility(View.GONE);
        infoGroup.setVisibility(View.VISIBLE);
        textMessage.setText(s);
    }

    @Click(R.id.buttonMove)
    void buttonMoveWasClicked(){
        buttonDelete.setAlpha((float) 0.5);
        buttonDelete.setClickable(false);
        buttonMark.setAlpha((float) 0.5);
        buttonMark.setClickable(false);
        moveChecker = true;
        moveB = true;
        showMessage("виберіть вулик біля якого ви хочете розмістити");
        mCommunicator = (Communicator)getActivity();
        mCommunicator.deleteBeehive(mBeehiveSet, mFromApiary, true );
        clearViewsBackground();
    }

    @Click(R.id.buttonDelete)
    void buttonDeleteWasClicked(View view){
        mCommunicator = (Communicator)getActivity();
        mCommunicator.deleteBeehive(mBeehiveSet, mFromApiary, true);
        this.dismiss();
    }

    @Click(R.id.buttonNo)
    void buttonNoWasClicked(){
        clearViewsBackground();
        if(moveB){
            mCommunicator = (Communicator) getActivity();
            mCommunicator.moveBeehive(mBeehiveSet, mBeehive, mFromApiary, mInApiary, BEHIND);
        }
        this.dismiss();
    }

    @Click(R.id.buttonYes)
    void buttonYesWasClicked(){
        clearViewsBackground();
        if(moveB){
            mCommunicator = (Communicator) getActivity();
            mCommunicator.moveBeehive(mBeehiveSet, mBeehive, mFromApiary, mInApiary, IN_FRONT);
        }else {
            mCommunicator = (Communicator) getActivity();
            mCommunicator.replaceBeehive(mBeehiveSet, mBeehive, mFromApiary, mInApiary);
        }
        this.dismiss();
    }

    private void clearViewsBackground() {
        mView.setBackgroundResource(0);

        List<View> view = new ArrayList<>(mViewSet);
        for(int i = 0; i < view.size(); i++){
            view.get(i).setBackgroundResource(0);
        }
    }

    public void setData(Beehive beehive, View view, String nameApiary) {
        if (!noMoreFlag) {
            if (moveChecker) {
                questionGroup.setVisibility(View.VISIBLE);
                infoGroup.setVisibility(View.GONE);
                toolsButtonGroup.setVisibility(View.GONE);
                buttonToolsGroup(false);
                mBeehive = beehive;
                mInApiary = nameApiary;
                mView = view;
                view.setBackgroundResource(R.drawable.yellow_frame);
                noMoreFlag = true;
                if (moveB){
                    buttonNo.setText("behind");
                    buttonYes.setText("in front");
                }else{

                }
            } else {
                mBeehiveSet.add(beehive);
                view.setBackgroundResource(R.drawable.green_frame);
                this.mFromApiary = nameApiary;
                this.mView = view;
                if (!mViewSet.add(view)) {
                    mViewSet.remove(view);
                    mView.setBackgroundResource(0);
                    mBeehiveSet.remove(beehive);
                }
                if (mBeehiveSet.size() == 1 && toolsButtonGroup != null) {
                    buttonReplace.setAlpha((float) 1);
                    buttonReplace.setClickable(true);
                    buttonDelete.setAlpha((float) 1);
                    buttonDelete.setClickable(true);
                    buttonMove.setAlpha((float) 1);
                    buttonMove.setClickable(true);
                }else if (mBeehiveSet.size() > 1) {
                    buttonReplace.setAlpha((float) 0.5);
                    buttonReplace.setClickable(false);
                } else {
                    if (buttonReplace != null) {
                        buttonReplace.setAlpha((float) 1);
                        buttonReplace.setClickable(true);
                    }
                }
            }
        }else if (beehive == mBeehive){
            view.setBackgroundResource(0);
            noMoreFlag = false;
        }
        else{
            Toast.makeText(getContext(),"Ви не можете вибрати два вулика",Toast.LENGTH_SHORT).show();
        }
    }

    private void buttonToolsGroup(boolean b) {
        if (b){
            buttonReplace.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);
            buttonMark.setVisibility(View.VISIBLE);
            buttonMove.setVisibility(View.VISIBLE);
        }else {
            buttonReplace.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
            buttonMark.setVisibility(View.GONE);
            buttonMove.setVisibility(View.GONE);
        }
    }
}
