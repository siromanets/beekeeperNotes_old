package com.example.key.beekeepernote;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.database.BeeColony;
import com.example.key.beekeepernote.database.Beehive;

import org.androidannotations.annotations.EActivity;

import java.util.List;

import static com.example.key.beekeepernote.RecyclerAdapter.NAME_APIARY;
import static com.example.key.beekeepernote.RecyclerAdapter.USER_SELECTED_BEEHIVE;


@EActivity
public class ActionActivity extends AppCompatActivity {
    Beehive beehive;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
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
        beehive = (Beehive) getIntent().getSerializableExtra(USER_SELECTED_BEEHIVE);
        String mNameApiary = getIntent().getStringExtra(NAME_APIARY);
        List<BeeColony> beeColonyList = beehive.getBeeColonies();
        if (beeColonyList != null && beeColonyList.size() > 0){
            for (int i = 0; i < beeColonyList.size(); i++){
                BeeColonyFragment colonyFragment = new BeeColonyFragment();
                adapter.addFragment(colonyFragment, "colony " + i);
                colonyFragment.setData(beeColonyList.get(i), mNameApiary, beehive.getNameBeehive(), String.valueOf(i));
                viewPager.setAdapter(adapter);
            }

        }
    }
}
