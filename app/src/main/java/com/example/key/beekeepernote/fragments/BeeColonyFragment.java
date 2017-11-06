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
import com.example.key.beekeepernote.interfaces.Communicator;
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

import static com.example.key.beekeepernote.utils.AlarmService.CHECKING;
import static com.example.key.beekeepernote.utils.AlarmService.NOTATION;
import static com.example.key.beekeepernote.utils.AlarmService.QUEEN;
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
    public Beehive nameBeehive;
    public String nameApiary;
    public int nameColony;
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
    private String mUserUId;
    private boolean complitText;
    private AlertDialog alertDialogReminder;


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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
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
        enabledMessagesSender(getContext());
        long time =  Calendar.getInstance().getTime().getTime();
        Notifaction notifaction = new Notifaction();
        notifaction.setTypeNotifaction(QUEEN);
        notifaction.setNameNotifaction("Beequeen exist");
        notifaction.setSchowTime(time);
        int ir = (int) (nameBeehive.getFounded());
        int id = ir /nameBeehive.getNumberBeehive() / (nameColony + 1) / QUEEN_CONSTANT_NUMBER;
        notifaction.setuId(id);
        notifaction.setPathNotifaction(notifaction.createPath(nameApiary, String.valueOf(nameBeehive), String.valueOf(nameColony)));
        notifaction.setTextNotifaction(" In beeColony number "+ nameColony + "was seeing beeQuen " + nameApiary +". " + nameBeehive);
        setNotificationToFirebase(notifaction);
        Intent i = new Intent(getContext(), AlarmService.class);
        i.putExtra(TO_SERVICE_COMMANDS, notifaction);
        getContext().startService(i);
    }
    @Click(R.id.buttonHavaChecked)
    public void buttonWasCheckedWasClicked(){
        enabledMessagesSender(getContext());
        long time =  Calendar.getInstance().getTime().getTime();
        Notifaction notifaction = new Notifaction();
        notifaction.setTypeNotifaction(CHECKING);
        notifaction.setNameNotifaction("Colony was checking");
        notifaction.setSchowTime(time);
        int ir = (int) (nameBeehive.getFounded());
        int id = ir /nameBeehive.getNumberBeehive() / (nameColony + 1) / CHECKING_CONSTANT_NUMBER;
        notifaction.setuId(id);
        notifaction.setPathNotifaction(notifaction.createPath(nameApiary, String.valueOf(nameBeehive), String.valueOf(nameColony)));
        notifaction.setTextNotifaction(" In beeColony number "+ nameColony + "was seeing beeQuen " + nameApiary +". " + nameBeehive);
        setNotificationToFirebase(notifaction);
        Intent i = new Intent(getContext(), AlarmService.class);
        i.putExtra(TO_SERVICE_COMMANDS, notifaction);
        getContext().startService(i);
    }
    @Click(R.id.buttonCreateMessage)
    public void buttonCreateMessageWasClicked(){
        showCreateNoteDialog();

    }

    private void showCreateNoteDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Remind");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_new_remindeer, null);
        builder.setView(dialogView);

        final DatePicker datePicker = (DatePicker)dialogView.findViewById(R.id.dataPickerReminder);
        datePicker.setMinDate(Calendar.getInstance().getTime().getTime());
        final EditText textReminder = (EditText)dialogView.findViewById(R.id.editTextReminder);
        final FloatingActionButton fabSendReminder = (FloatingActionButton)dialogView.findViewById(R.id.fabSendReminder);

        fabSendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textReminder.getText().toString().length() != 0 ){
                    BeeColony beeColony = nameBeehive.getBeeColonies().get(nameColony);
                    beeColony.setNoteBeeColony( textReminder.getText().toString());
                    beeColony.setCheckedTime(Calendar.getInstance().getTime().getTime());
                    long t =  Calendar.getInstance().getTime().getTime();
                    FirebaseDatabase.getInstance().getReference().child("apiary").child(nameApiary)
                            .child("beehives").child(String.valueOf(nameBeehive.getNumberBeehive() -1))
                            .child("beeColonies").child(String.valueOf(nameColony)).setValue(beeColony);
                    Notifaction notifaction = new Notifaction();
                    notifaction.setTypeNotifaction(NOTATION);
                    notifaction.setNameNotifaction("REMINDER");
                    notifaction.setTextNotifaction(textReminder.getText().toString());
                    notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
                    int ir = (int) (nameBeehive.getFounded());
                    int id = ir /nameBeehive.getNumberBeehive() / (nameColony + 1) / NOTATION_CONSTANT_NUMBER;
                    notifaction.setuId(id);
                    notifaction.setPathNotifaction(notifaction.createPath(nameApiary, String.valueOf(nameBeehive), String.valueOf(nameColony)));
                    setNotificationToFirebase(notifaction);
                    Calendar calendar = Calendar.getInstance();
                    long currentTime = calendar.getTime().getTime();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 9, 00);
                    if (currentTime < calendar.getTime().getTime()) {
                        notifaction.setSchowTime(calendar.getTime().getTime());
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
        myRef.child("apiary").child(nameApiary).child("beehives").child(String.valueOf(nameBeehive.getNumberBeehive() -1))
                .child("beeColonies").child(String.valueOf(nameColony)).setValue(beeColony);
    }


    private void refreshTextViews() {
        countFrameWithWorm.setText(String.valueOf(quantityWormsFrames));
        countFrameWithHoney.setText(String.valueOf(quantityHoneyFrames));
        countEmptyFrame.setText(String.valueOf(quantityEmptyFrames));
        countFramesOfColony.setText(String.valueOf(countFrames));
    }

    public void setData(BeeColony beeColony, String nameApiary, Beehive numberBeehive, int nameColony, String userUId){
        this.beeColony = beeColony;
        this.nameApiary = nameApiary;
        this.nameBeehive = numberBeehive;
        this.nameColony = nameColony;
        this.mUserUId = userUId;
    }

    public void setNotificationToFirebase(Notifaction notificationToFirebase) {
        myRef.child(mUserUId).child("history").child(String.valueOf(notificationToFirebase.getSchowTime())).setValue(notificationToFirebase);
    }
}
