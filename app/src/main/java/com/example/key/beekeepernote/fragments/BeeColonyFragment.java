package com.example.key.beekeepernote.fragments;

import android.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;
import com.example.key.beekeepernote.models.Notifaction;
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

import static com.example.key.beekeepernote.utils.AlarmService.CHECKING_INT;
import static com.example.key.beekeepernote.utils.AlarmService.NOTATION_INT;
import static com.example.key.beekeepernote.utils.AlarmService.QUEEN_INT;
import static com.example.key.beekeepernote.utils.AlarmService.TO_SERVICE_COMMANDS;


/**
 *
 */
@EFragment
public class BeeColonyFragment extends Fragment{

    public static final int CHECKING_CONSTANT_NUMBER = 2;
    public static final int QUEEN_CONSTANT_NUMBER = 3;
    public static final int NOTATION_CONSTANT_NUMBER = 4;
    public BeeColony beeColony;
    public Beehive beehive;
    public String nameApiary;
    private int mNameColony;
    private int mQuantityWormsFrames = 0;
    private int mQuantityHoneyFrames = 0;
    private int mCountFrames = 0;
    private int mQuantityEmptyFrames = 0;


    private DatabaseReference myRef;
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
    private String mUserUId;
    private boolean complitText;
    private AlertDialog alertDialogReminder;
    private long mCheckedTimeByQueen;
    private long mCheckedTimeByColony;


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
        mQuantityEmptyFrames = beeColony.getBeeEmptyFrame();
        mQuantityWormsFrames = beeColony.getBeeWormsFrame();
        mQuantityHoneyFrames = beeColony.getBeeHoneyFrame();
        mCheckedTimeByQueen = beeColony.isQueen();
        mCheckedTimeByColony = beeColony.getCheckedTime();
        mCountFrames = mQuantityEmptyFrames + mQuantityWormsFrames + mQuantityHoneyFrames;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        refreshTextViews();
        refreshProgressViews();
    }

    private void refreshProgressViews() {
        progressIsBeeQueen.setMaxProgress(10);
        long diff = mCheckedTimeByQueen+ (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
        int deys = (int)diff /24 / 60 / 60 / 1000;
        progressIsBeeQueen.setProgress(deys);
        progressHaveChecked.setMaxProgress(10);
        long diff1 = mCheckedTimeByColony + (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
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
        enabledMessagesSender(getContext());
        long time =  Calendar.getInstance().getTime().getTime();
        Notifaction notifaction = new Notifaction();
        notifaction.setTypeNotifaction(QUEEN_INT);
        notifaction.setNameNotifaction("Beequeen exist");
        notifaction.setSchowTime(time);
        notifaction.setuId((int) (beehive.getFounded())/ beehive.getNumberBeehive() / (mNameColony + 1) / QUEEN_CONSTANT_NUMBER);
        notifaction.setPathNotifaction(notifaction.createPath(nameApiary, String.valueOf(beehive.getNumberBeehive()), String.valueOf(mNameColony)));
        notifaction.setTextNotifaction(" In beeColony number "+ mNameColony + "was seeing beeQuen " + nameApiary +". " + beehive);
        setNotificationToFirebase(notifaction);
        Intent i = new Intent(getContext(), AlarmService.class);
        i.putExtra(TO_SERVICE_COMMANDS, notifaction);
        getContext().startService(i);
        mCheckedTimeByQueen = time;
        saveData();
    }
    @Click(R.id.buttonHavaChecked)
    public void buttonWasCheckedWasClicked(){
        enabledMessagesSender(getContext());
        long time =  Calendar.getInstance().getTime().getTime();
        Notifaction notifaction = new Notifaction();
        notifaction.setTypeNotifaction(CHECKING_INT);
        notifaction.setNameNotifaction("Colony was checking");
        notifaction.setSchowTime(time);
        notifaction.setuId((int) (beehive.getFounded()) / beehive.getNumberBeehive() / (mNameColony + 1) / CHECKING_CONSTANT_NUMBER);
        notifaction.setPathNotifaction(notifaction.createPath(nameApiary, String.valueOf(beehive.getNumberBeehive()), String.valueOf(mNameColony)));
        notifaction.setTextNotifaction(" In beeColony number "+ mNameColony + "was seeing beeQuen " + nameApiary +". " + beehive);
        setNotificationToFirebase(notifaction);
        Intent i = new Intent(getContext(), AlarmService.class);
        i.putExtra(TO_SERVICE_COMMANDS, notifaction);
        getContext().startService(i);
        mCheckedTimeByColony = time;
        saveData();
    }
    @Click(R.id.buttonCreateMessage)
    public void buttonCreateMessageWasClicked(){
        showCreateNoteDialog();
    }

    private void showCreateNoteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Remind");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_new_remindeer, null);
        builder.setView(dialogView);

        final DatePicker datePicker = (DatePicker)dialogView.findViewById(R.id.dataPickerReminder);
        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);
        final EditText textReminder = (EditText)dialogView.findViewById(R.id.editTextReminder);
        final FloatingActionButton fabSendReminder = (FloatingActionButton)dialogView.findViewById(R.id.fabSendReminder);



        fabSendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long showTime = 0;
                if (textReminder.getText().toString().length() != 0 ){
                    if (Calendar.getInstance().getTime().getTime() < getSelectedTime(datePicker)) {
                        showTime = getSelectedTime(datePicker);

                    }
                    BeeColony beeColony = beehive.getBeeColonies().get(mNameColony);
                    beeColony.setTimeReminder(showTime);
                    beeColony.setNoteBeeColony( textReminder.getText().toString());
                    beeColony.setCheckedTime(Calendar.getInstance().getTime().getTime());
                    FirebaseDatabase.getInstance().getReference().child("apiary").child(nameApiary)
                            .child("beehives").child(String.valueOf(beehive.getNumberBeehive() -1))
                            .child("beeColonies").child(String.valueOf(mNameColony)).setValue(beeColony);
                    Notifaction notifaction = new Notifaction();
                    notifaction.setTypeNotifaction(NOTATION_INT);
                    notifaction.setNameNotifaction("REMINDER");
                    notifaction.setTextNotifaction(textReminder.getText().toString());
                    notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
                    notifaction.setuId((int)beehive.getFounded() / beehive.getNumberBeehive() / (mNameColony + 1) / NOTATION_CONSTANT_NUMBER);
                    notifaction.setPathNotifaction(notifaction.createPath(nameApiary, String.valueOf(beehive), String.valueOf(mNameColony)));
                    setNotificationToFirebase(notifaction);
                   if (showTime != 0){
                       notifaction.setSchowTime(showTime);
                       Intent i = new Intent(getContext(), AlarmService.class);
                       i.putExtra(TO_SERVICE_COMMANDS, notifaction);
                       getContext().startService(i);
                   }
                    alertDialogReminder.dismiss();
                }else{
                    textReminder.setError("Enter text");
                }

            }

        });
       alertDialogReminder =  builder.create();
           alertDialogReminder.show();
    }

    @Click(R.id.buttonPlasForWorms)
    public void buttonPlusForWormsWasClicked(){
        if (mQuantityEmptyFrames != 0){
            mQuantityEmptyFrames--;
            mQuantityWormsFrames++;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonMinusForWorms)
    public void buttonMinusForWormsWasClicked(){
        if (mQuantityWormsFrames != 0){
            mQuantityEmptyFrames++;
            mQuantityWormsFrames--;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  dont have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Click(R.id.buttonPlasForHoney)
   public void buttonPlusForHoneyWasClicked(){
        if (mQuantityEmptyFrames != 0){
            mQuantityEmptyFrames--;
            mQuantityHoneyFrames++;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonMinusForHoney)
    public void  buttonMinusForHoneyWasClicked(){
        if (mQuantityHoneyFrames != 0){
            mQuantityEmptyFrames++;
            mQuantityHoneyFrames--;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonAddFrame)
    public void buttonAddFrameWasClicked(){
        if (mCountFrames < 20){
            mQuantityEmptyFrames++;
            mCountFrames++;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "This hive can not contain more frames",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonDellFrame)
    public void buttonDellFrameWasClicked(){
        if (mQuantityEmptyFrames != 0){
            mQuantityEmptyFrames--;
            mCountFrames--;
            refreshTextViews();
        }else{
            Toast.makeText(getContext(), "Do not have empty frames ",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void saveData() {
        beeColony.setBeeEmptyFrame(mQuantityEmptyFrames);
        beeColony.setQueen(mCheckedTimeByQueen);
        beeColony.setCheckedTime(mCheckedTimeByColony);
        beeColony.setBeeWormsFrame(mQuantityWormsFrames);
        beeColony.setBeeHoneyFrame(mQuantityHoneyFrames);
        beeColony.setCheckedTime(Calendar.getInstance().getTime().getTime());
        beeColony.setNoteBeeColony("");
        myRef.child(mUserUId).child("apiary").child(nameApiary).child("beehives").child(String.valueOf(beehive.getNumberBeehive() -1))
                .child("beeColonies").child(String.valueOf(mNameColony)).setValue(beeColony);
        refreshProgressViews();
    }


    private void refreshTextViews() {
        countFrameWithWorm.setText(String.valueOf(mQuantityWormsFrames));
        countFrameWithHoney.setText(String.valueOf(mQuantityHoneyFrames));
        countEmptyFrame.setText(String.valueOf(mQuantityEmptyFrames));
        countFramesOfColony.setText(String.valueOf(mCountFrames));
    }

    public void setData(BeeColony beeColony, String nameApiary, Beehive numberBeehive, int nameColony, String userUId){
        this.beeColony = beeColony;
        this.nameApiary = nameApiary;
        this.beehive = numberBeehive;
        this.mNameColony = nameColony;
        this.mUserUId = userUId;
    }

    public void setNotificationToFirebase(Notifaction notificationToFirebase) {
        myRef.child(mUserUId).child("history").child(String.valueOf(notificationToFirebase.getSchowTime())).setValue(notificationToFirebase);
    }

    public long getSelectedTime(DatePicker datePicker) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 9, 00);
        return mCalendar.getTime().getTime();
    }
}
