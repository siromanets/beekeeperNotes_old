package com.example.key.beekeepernote.fragments;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.interfaces.Communicator;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.utils.AlarmService;
import com.example.key.beekeepernote.utils.TimeNotification;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackandphantom.circularprogressbar.CircleProgressbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 *
 */
@EFragment
public class BeeColonyFragment extends Fragment{

    public BeeColony beeColony;
    public int nameBeehive;
    public String nameApiary;
    public String nameColony;
    public int quantityWormsFrames = 0;
    public int quantityHoneyFrames = 0;
    public int countFrames = 0;
    public int quantityEmptyFrames = 0;

    private Communicator mCommunicator;
    private DatabaseReference myRef;
    private int mNumberCollony;
    private PendingIntent mAlarmSender;
    @ViewById(R.id.countFrameWithWorm)
    TextView countFrameWithWorm;
    @ViewById(R.id.countFrameWithHoney)
    TextView countFrameWithHoney;
    @ViewById(R.id.countEmptyFrame)
    TextView countEmptyFrame;
    @ViewById(R.id.countOfFrames)
    TextView countFramesOfColony;

    @ViewById(R.id.buttonPlasForWorms)
    FloatingActionButton buttonPlusForWorms;

    @ViewById(R.id.buttonMinusForWorms)
    FloatingActionButton buttonMinusForWorms;

    @ViewById(R.id.buttonPlasForHoney)
    FloatingActionButton buttonPlusForHoney;

    @ViewById(R.id.buttonMinusForHoney)
    FloatingActionButton buttonMinusForHoney;

    @ViewById(R.id.buttonAddFrame)
    FloatingActionButton buttonAddFrame;

    @ViewById(R.id.buttonDellFrame)
    FloatingActionButton buttonDellFrame;

    @ViewById(R.id.buttonIsBeeQueen)
    CircleImageView buttonIsBeeQueen;

    @ViewById(R.id.progressHaveChecked)
    CircleProgressbar progressHaveChecked;

    @ViewById(R.id.progressIsBeeQueen)
    CircleProgressbar progressIsBeeQueen;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_beehive, container, false);
        return view;

    }
    @AfterViews
    public void afterViews(){
        mAlarmSender = PendingIntent.getBroadcast(getContext(), 0, new Intent(getContext(), TimeNotification.class), 0);
        quantityEmptyFrames = beeColony.getBeeEmptyFrame();
        quantityWormsFrames = beeColony.getBeeWormsFrame();
        quantityHoneyFrames = beeColony.getBeeHoneyFrame();
        countFrames = quantityEmptyFrames + quantityWormsFrames + quantityHoneyFrames;
        refreshTextViews();
        refreshProgressViews();
    }

    private void refreshProgressViews() {
        progressIsBeeQueen.setMaxProgress(10);
        long diff = beeColony.isQueen() + (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
        int deys = (int)diff /24 / 60 / 60 / 1000;
        progressIsBeeQueen.setProgress(deys);
        progressHaveChecked.setMaxProgress(10);
        long diff1 = beeColony.getCheckedTime() + (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
        int deys1 = (int)diff1 /24 / 60 / 60 / 1000;
        progressHaveChecked.setProgress(deys1);
    }
    public void enabledMessagesSender(Context context){
        ComponentName receiver = new ComponentName(context, TimeNotification.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }
    public void disabledMessagesSender(Context context){

        ComponentName receiver = new ComponentName(context, TimeNotification.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Click(R.id.buttonIsBeeQueen)
    public void buttonIsBeeQueenWasClicked(){
        //todo need add logic
    }
    @Click(R.id.buttonHavaChecked)
    public void buttonWasCheckedWasClicked(){
        saveData();
        AlarmService alarmService = new AlarmService(getContext());
        int time = 10;
        alarmService.startAlarm(time);
    }

    @Click(R.id.buttonPlasForWorms)
    public void buttonPlusForWormsWasClicked(){
        if (quantityEmptyFrames != 0){
            quantityEmptyFrames--;
            quantityWormsFrames++;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonMinusForWorms)
    public void buttonMinusForWormsWasClicked(){
        if (quantityWormsFrames != 0){
            quantityEmptyFrames++;
            quantityWormsFrames--;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  dont have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Click(R.id.buttonPlasForHoney)
   public void buttonPlusForHoneyWasClicked(){
        if (quantityEmptyFrames != 0){
            quantityEmptyFrames--;
            quantityHoneyFrames++;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonMinusForHoney)
    public void  buttonMinusForHoneyWasClicked(){
        if (quantityHoneyFrames != 0){
            quantityEmptyFrames++;
            quantityHoneyFrames--;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonAddFrame)
    public void buttonAddFrameWasClicked(){
        if (countFrames < 20){
            quantityEmptyFrames++;
            countFrames++;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "This hive can not contain more frames",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonDellFrame)
    public void buttonDellFrameWasClicked(){
        if (quantityEmptyFrames != 0){
            quantityEmptyFrames--;
            countFrames--;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "Do not have empty frames ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }
    private void saveData() {
        beeColony.setBeeEmptyFrame(quantityEmptyFrames);
        beeColony.setBeeWormsFrame(quantityWormsFrames);
        beeColony.setBeeHoneyFrame(quantityHoneyFrames);
        beeColony.setCheckedTime(Calendar.getInstance().getTime().getTime());
        beeColony.setNoteBeeColony("");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("apiary").child(nameApiary).child("beehives").child(String.valueOf(nameBeehive -1))
                .child("beeColonies").child(nameColony).setValue(beeColony);
    }


    private void refreshTextViews() {
        countFrameWithWorm.setText(String.valueOf(quantityWormsFrames));
        countFrameWithHoney.setText(String.valueOf(quantityHoneyFrames));
        countEmptyFrame.setText(String.valueOf(quantityEmptyFrames));
        countFramesOfColony.setText(String.valueOf(countFrames));
    }

    public void setData(BeeColony beeColony, String nameApiary, int numberBeehive, String nameColony){
        this.beeColony = beeColony;
        this.nameApiary = nameApiary;
        this.nameBeehive = numberBeehive;
        this.nameColony = nameColony;

    }

}
