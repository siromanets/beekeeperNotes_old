package com.example.key.beekeepernote.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.adapters.ViewPagerAdapter;
import com.example.key.beekeepernote.fragments.BeeColonyFragment;
import com.example.key.beekeepernote.interfaces.CommunicatorActionActivity;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.EActivity;

import java.util.Calendar;
import java.util.List;

import static com.example.key.beekeepernote.adapters.RecyclerAdapter.NAME_APIARY;
import static com.example.key.beekeepernote.adapters.RecyclerAdapter.USER_SELECTED_BEEHIVE;


@EActivity
public class ActionActivity extends AppCompatActivity implements CommunicatorActionActivity{
    Beehive beehive;
    Toolbar toolbar;
    Calendar mCalendar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    String mNameApiary;
    private int[] tabIcons = {
            R.drawable.ic_beehive_left,
            R.drawable.ic_beehive_right,
            R.drawable.ic_beehive_one
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        toolbar = (Toolbar) findViewById(R.id.toolbarActionActivity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpagerActionActivity);

        tabLayout = (TabLayout) findViewById(R.id.tabsActionActivity);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        mCalendar = Calendar.getInstance();

        beehive = (Beehive) getIntent().getSerializableExtra(USER_SELECTED_BEEHIVE);
        mNameApiary = getIntent().getStringExtra(NAME_APIARY);
        List<BeeColony> beeColonyList = beehive.getBeeColonies();
        if (beeColonyList != null && beeColonyList.size() > 0){
            for (int i = 0; i < beeColonyList.size(); i++){
                BeeColonyFragment colonyFragment = new BeeColonyFragment();
                adapter.addFragment(colonyFragment, "colony " + (i + 1));
                colonyFragment.setData(beeColonyList.get(i), mNameApiary, beehive.getNumberBeehive(), String.valueOf(i));
                viewPager.setAdapter(adapter);
            }
        }
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(beeColonyList.size());
    }

    private void setupTabIcons(int count) {
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                tabLayout.getTabAt(i).setIcon(tabIcons[i]);
            }
        }else {
            tabLayout.getTabAt(0).setIcon(tabIcons[2]);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void writeCheckedTimeForBeehive() {
        beehive.setCheckedTime(Calendar.getInstance().getTime());
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("apiary").child(mNameApiary).child("beehives").child(String.valueOf(beehive.getNumberBeehive() -1)).setValue(beehive);
    }
}
