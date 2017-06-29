package com.example.key.beekeepernote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
/**
        BeeBox beeBox = new BeeBox();
        beeBox.setNoteFieldBox("skskjf");
        beeBox.setShapeBox(3);
        beeBox.setTypeBox(1);

        BeeBox beeBox1 = new BeeBox();
        beeBox1.setNoteFieldBox("skskjf");
        beeBox1.setShapeBox(3);
        beeBox1.setTypeBox(1);

        BeeBox beeBox2 = new BeeBox();
        beeBox2.setNoteFieldBox("skskjf");
        beeBox2.setShapeBox(3);
        beeBox2.setTypeBox(1);
        List<BeeBox> beeBoxes = new ArrayList<>();
        beeBoxes.add(beeBox);
        beeBoxes.add(beeBox1);
        beeBoxes.add(beeBox2);


        Bike bike = new Bike();
        bike.setHaveFood(true);
        bike.setNoteBike("kdjfjsd");
        bike.setOutput("lsfkdk");
        bike.setQueen(true);
        bike.setRiskOfSwaddling(true);
        bike.setWorm(true);
        bike.setBeeBoxes(beeBoxes);

        Bike bike1 = new Bike();
        bike1.setHaveFood(true);
        bike1.setNoteBike("kdjfjsd");
        bike1.setOutput("lsfkdk");
        bike1.setQueen(true);
        bike1.setRiskOfSwaddling(true);
        bike1.setWorm(true);
        bike1.setBeeBoxes(beeBoxes);

        Bike bike2 = new Bike();
        bike2.setHaveFood(true);
        bike2.setNoteBike("kdjfjsd");
        bike2.setOutput("lsfkdk");
        bike2.setQueen(true);
        bike2.setRiskOfSwaddling(true);
        bike2.setWorm(true);
        bike2.setBeeBoxes(beeBoxes);

        Bike bike3 = new Bike();
        bike3.setHaveFood(true);
        bike3.setNoteBike("kdjfjsd");
        bike3.setOutput("lsfkdk");
        bike3.setQueen(true);
        bike3.setRiskOfSwaddling(true);
        bike3.setWorm(true);
        bike3.setBeeBoxes(beeBoxes);

        List<Bike> bikeList = new ArrayList<>();
        bikeList.add(bike);
        bikeList.add(bike1);
        bikeList.add(bike2);
        bikeList.add(bike3);

        Beehive beehive = new Beehive();
        beehive.setFounded(1209939);
        beehive.setNameBeehive("fkjdsfkdk");
        beehive.setNoteBeehive("dkfsdlkj;k");
        beehive.setTypeBeehive(1);
        beehive.setBikes(bikeList);

        Beehive beehive1 = new Beehive();
        beehive1.setFounded(1209934449);
        beehive1.setNameBeehive("fkjdsfkfdfdk");
        beehive1.setNoteBeehive("dkfsdlkfdsfj;k");
        beehive1.setTypeBeehive(2);
        beehive1.setBikes(bikeList);

        List<Beehive> beehiveList = new ArrayList<>();
        beehiveList.add(beehive);
        beehiveList.add(beehive1);

        Apiary apiary = new Apiary();
        apiary.setLocationApiary("flfsdldlk");
        apiary.setNameApiary("nikolka");
        apiary.setNoteApiary("kdslfsdlk");
        apiary.setBeehives(beehiveList);


        myRef.child("apiary").child("apiaryId").setValue(apiary);
 **/

        final List<Apiary> apiaryList = new ArrayList<>();
        Query myPlace = myRef.child("apiary").child("apiaryId");
        myPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apiary apiary = new Apiary();
                apiary = dataSnapshot.getValue(Apiary.class);
                apiaryList.add(apiary);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
