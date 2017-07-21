package com.example.key.beekeepernote;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.key.beekeepernote.database.Apiary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewBlankFragment(), "ADD NEW");
        viewPager.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        final List<Apiary> apiaryList = new ArrayList<>();
        Query myPlace = myRef.child("apiary").child("apiaryId");
        myPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apiary apiary = new Apiary();
                apiary = dataSnapshot.getValue(Apiary.class);
                ApiaryFragment apiaryFragment = new ApiaryFragment();
                apiaryFragment.setData(apiary);
                adapter.addFragment(apiaryFragment, "RakivFarm");
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/**
        BeeFrame beeBox = new BeeFrame();
        beeBox.setNoteFieldBox("skskjf");
        beeBox.setShapeBox(3);
        beeBox.setTypeBox(1);

        BeeFrame beeBox1 = new BeeFrame();
        beeBox1.setNoteFieldBox("skskjf");
        beeBox1.setShapeBox(3);
        beeBox1.setTypeBox(1);

        BeeFrame beeBox2 = new BeeFrame();
        beeBox2.setNoteFieldBox("skskjf");
        beeBox2.setShapeBox(3);
        beeBox2.setTypeBox(1);
        List<BeeFrame> beeFrames = new ArrayList<>();
        beeFrames.add(beeBox);
        beeFrames.add(beeBox1);
        beeFrames.add(beeBox2);


        BeeColony BeeColony = new BeeColony();
        BeeColony.setHaveFood(true);
        BeeColony.setNoteBeeColony("kdjfjsd");
        BeeColony.setOutput("lsfkdk");
        BeeColony.setQueen(true);
        BeeColony.setRiskOfSwaddling(true);
        BeeColony.setWorm(true);
        BeeColony.setBeeFrames(beeFrames);

        BeeColony BeeColony1 = new BeeColony();
        BeeColony1.setHaveFood(true);
        BeeColony1.setNoteBeeColony("kdjfjsd");
        BeeColony1.setOutput("lsfkdk");
        BeeColony1.setQueen(true);
        BeeColony1.setRiskOfSwaddling(true);
        BeeColony1.setWorm(true);
        BeeColony1.setBeeFrames(beeFrames);

        BeeColony BeeColony2 = new BeeColony();
        BeeColony2.setHaveFood(true);
        BeeColony2.setNoteBeeColony("kdjfjsd");
        BeeColony2.setOutput("lsfkdk");
        BeeColony2.setQueen(true);
        BeeColony2.setRiskOfSwaddling(true);
        BeeColony2.setWorm(true);
        BeeColony2.setBeeFrames(beeFrames);

        BeeColony BeeColony3 = new BeeColony();
        BeeColony3.setHaveFood(true);
        BeeColony3.setNoteBeeColony("kdjfjsd");
        BeeColony3.setOutput("lsfkdk");
        BeeColony3.setQueen(true);
        BeeColony3.setRiskOfSwaddling(true);
        BeeColony3.setWorm(true);
        BeeColony3.setBeeFrames(beeFrames);

        List<BeeColony> BeeColonyList = new ArrayList<>();
        BeeColonyList.add(BeeColony);
        BeeColonyList.add(BeeColony1);
        BeeColonyList.add(BeeColony2);
        BeeColonyList.add(BeeColony3);
        BeeColonyList.add(BeeColony);
        BeeColonyList.add(BeeColony1);
        BeeColonyList.add(BeeColony2);
        BeeColonyList.add(BeeColony3);
        BeeColonyList.add(BeeColony);
        BeeColonyList.add(BeeColony1);
        BeeColonyList.add(BeeColony2);
        BeeColonyList.add(BeeColony3);
        BeeColonyList.add(BeeColony);
        BeeColonyList.add(BeeColony1);
        BeeColonyList.add(BeeColony2);
        BeeColonyList.add(BeeColony3);
        BeeColonyList.add(BeeColony);
        BeeColonyList.add(BeeColony1);
        BeeColonyList.add(BeeColony2);
        BeeColonyList.add(BeeColony3);

        Beehive beehive = new Beehive();
        beehive.setFounded(1209939);
        beehive.setNameBeehive("fkjdsfkdk");
        beehive.setNoteBeehive("dkfsdlkj;k");
        beehive.setTypeBeehive(1);
        beehive.setBeeColonies(BeeColonyList);

        Beehive beehive1 = new Beehive();
        beehive1.setFounded(1209934449);
        beehive1.setNameBeehive("fkjdsfkfdfdk");
        beehive1.setNoteBeehive("dkfsdlkfdsfj;k");
        beehive1.setTypeBeehive(2);
        beehive1.setBeeColonies(BeeColonyList);

        List<Beehive> beehiveList = new ArrayList<>();
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);
         beehiveList.add(beehive);
         beehiveList.add(beehive1);


        Apiary apiary = new Apiary();
        apiary.setLocationApiary("flfsdldlk");
        apiary.setNameApiary("nikolka");
        apiary.setNoteApiary("kdslfsdlk");
        apiary.setBeehives(beehiveList);


        myRef.child("apiary").child("apiaryId").setValue(apiary);
        myRef.child("apiary").child("kldfkld").setValue(apiary);

*/


    }

}
