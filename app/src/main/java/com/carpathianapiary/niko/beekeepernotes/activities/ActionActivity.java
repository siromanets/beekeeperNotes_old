package com.carpathianapiary.niko.beekeepernotes.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.carpathianapiary.niko.beekeepernotes.R;
import com.carpathianapiary.niko.beekeepernotes.adapters.ViewPagerAdapter;

import com.carpathianapiary.niko.beekeepernotes.models.BeeColony;
import com.carpathianapiary.niko.beekeepernotes.models.Beehive;
import com.carpathianapiary.niko.beekeepernotes.models.Notifaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

import static com.carpathianapiary.niko.beekeepernotes.adapters.RecyclerAdapter.COLONY_NUMBER;
import static com.carpathianapiary.niko.beekeepernotes.adapters.RecyclerAdapter.NAME_APIARY;
import static com.carpathianapiary.niko.beekeepernotes.adapters.RecyclerAdapter.USER_SELECTED_BEEHIVE;
import static com.carpathianapiary.niko.beekeepernotes.utils.AlarmService.HISTORY_INT;


@EActivity
public class ActionActivity extends AppCompatActivity {
    Beehive beehive;
    Calendar mCalendar;


    ViewPagerAdapter adapter;
    String mNameApiary;
    private int[] tabIcons = {
            R.drawable.ic_beehive_left,
            R.drawable.ic_beehive_right,
            R.drawable.ic_beehive_one
    };

    @ViewById(R.id.toolbarActionActivity)
    Toolbar toolbar;
    @ViewById(R.id.tabsActionActivity)
    TabLayout tabLayout;
    @ViewById(R.id.viewpagerActionActivity)
    ViewPager viewPager;

    private FirebaseUser mCurUseer;
    private int mColonyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.showOverflowMenu();
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        mCalendar = Calendar.getInstance();
        mCurUseer = FirebaseAuth.getInstance().getCurrentUser();

        beehive = (Beehive) getIntent().getSerializableExtra(USER_SELECTED_BEEHIVE);
        mNameApiary = getIntent().getStringExtra(NAME_APIARY);
        mColonyNumber = getIntent().getIntExtra(COLONY_NUMBER, -1);
        setupTabs(beehive);

    }

    private void setupTabs(Beehive beehive) {
        List<BeeColony> beeColonyList = beehive.getBeeColonies();
        if (beeColonyList != null && beeColonyList.size() > 0){
            for (int i = 0; i < beeColonyList.size(); i++){
                if ( tabLayout.getTabAt(i) == null ){
//                    BeeColonyFragment colonyFragment = new BeeColonyFragment_();
//                    adapter.addFragment(colonyFragment, "colony " + (i + 1));
//                    colonyFragment.setData(beeColonyList.get(i), mNameApiary, beehive, i, mCurUseer.getUid());

                }

            }
            viewPager.setAdapter(adapter);
        }
        toolbar.setTitle(mNameApiary);
        toolbar.setSubtitle("BeehiveActivity № " + beehive.getNumberBeehive() );
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(beeColonyList.size());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionNewColony:
                if (beehive.getTypeBeehive() == 2 && beehive.getBeeColonies().size() < 2){
                    BeeColony beeColony = new BeeColony();
                    beeColony.setTimeReminder(0);
                    beeColony.setCheckedTime(Calendar.getInstance().getTimeInMillis());
                    beeColony.setQueen(Calendar.getInstance().getTimeInMillis());
                    beeColony.setBeeEmptyFrame(5);

                    beehive.getBeeColonies().add(1, beeColony );
                    FirebaseDatabase.getInstance().getReference()
                            .child(mCurUseer.getUid())
                            .child("apiary").child(mNameApiary).child("beehives")
                            .child(String.valueOf(beehive.getNumberBeehive() - 1)).setValue(beehive);
                    setupTabs(beehive);
                    Notifaction notifaction = new Notifaction();
                    notifaction.setTypeNotifaction(HISTORY_INT);
                    notifaction.setNameNotifaction("Created new Colony");
                    notifaction.setSchowTime(mCalendar.getTimeInMillis());
                    notifaction.setuId((int) (beehive.getFounded())/ beehive.getNumberBeehive());
                    notifaction.setPathNotifaction(notifaction.createPath(mNameApiary, String.valueOf(beehive.getNumberBeehive() - 1), String.valueOf(0)));
                    notifaction.setTextNotifaction(" In beeColony number "+ beehive.getNumberBeehive() + "was seeing beeQuen " + mNameApiary +". " + beehive);
                    setNotificationToFirebase(notifaction);
                }

                return true;
            case R.id.actionDeleteColony:
                showDeleteColonyDialog(beehive.getNumberBeehive(), tabLayout.getSelectedTabPosition());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupTabIcons(int count) {
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            }
        }else {
            tabLayout.getTabAt(0).setIcon(tabIcons[2]);
        }
        if (mColonyNumber != -1) {
            viewPager.setCurrentItem(mColonyNumber, true);
        }
    }

    private void showDeleteColonyDialog(final int s, final int position) {
        if (position > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("WARNING");
            builder.setMessage("Do you want to delete Colony N " + s + " ?  " + "All data in this section will be lost forever");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteColony(s - 1, position);
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

            builder.create().show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Action");
            builder.setMessage("You can not delete history page");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

             builder.create().show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setNotificationToFirebase(Notifaction notificationToFirebase) {
        FirebaseDatabase.getInstance().getReference().child(mCurUseer.getUid()).child("history").child(String.valueOf(notificationToFirebase.getSchowTime())).setValue(notificationToFirebase);

    }
    private void deleteColony(int name, final int position) {
        if (tabLayout.getTabCount() >= 1 && position < tabLayout.getTabCount() && position != 0) {
            // tabLayout.removeTabAt(position);
            adapter.deleteFragment(position);
            adapter.notifyDataSetChanged();
            FirebaseDatabase.getInstance().getReference().child(mCurUseer.getUid()).child("apiary").child(mNameApiary).child("beehives").child(String.valueOf(name)).child("beeColonies").child(String.valueOf(position -1)).removeValue();
            Toast.makeText(ActionActivity.this, "Colony was delete", Toast.LENGTH_SHORT).show();
        }
    }
}
