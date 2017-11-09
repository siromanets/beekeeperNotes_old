package com.example.key.beekeepernote.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.adapters.ViewPagerAdapter;
import com.example.key.beekeepernote.fragments.BeeColonyFragment;
import com.example.key.beekeepernote.fragments.BeeColonyFragment_;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.List;

import static com.example.key.beekeepernote.adapters.RecyclerAdapter.COLONY_NUMBER;
import static com.example.key.beekeepernote.adapters.RecyclerAdapter.NAME_APIARY;
import static com.example.key.beekeepernote.adapters.RecyclerAdapter.USER_SELECTED_BEEHIVE;


@EActivity
public class ActionActivity extends AppCompatActivity {
    Beehive beehive;
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

    @ViewById(R.id.toolbarActionActivity)
    Toolbar toolbar;
    private FirebaseUser mCurUseer;
    private int mColonyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.showOverflowMenu();
        viewPager = (ViewPager) findViewById(R.id.viewpagerActionActivity);

        tabLayout = (TabLayout) findViewById(R.id.tabsActionActivity);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        mCalendar = Calendar.getInstance();
         mCurUseer = FirebaseAuth.getInstance().getCurrentUser();

        beehive = (Beehive) getIntent().getSerializableExtra(USER_SELECTED_BEEHIVE);
        mNameApiary = getIntent().getStringExtra(NAME_APIARY);
        mColonyNumber = getIntent().getIntExtra(COLONY_NUMBER, -1);
        List<BeeColony> beeColonyList = beehive.getBeeColonies();
        if (beeColonyList != null && beeColonyList.size() > 0){
            for (int i = 0; i < beeColonyList.size(); i++){
                BeeColonyFragment colonyFragment = new BeeColonyFragment_();
                adapter.addFragment(colonyFragment, "colony " + (i + 1));
                colonyFragment.setData(beeColonyList.get(i), mNameApiary, beehive, i, mCurUseer.getUid());
                viewPager.setAdapter(adapter);
            }
        }
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
                //todo
                return true;
            case R.id.actionDeleteColony:
                //todo
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
