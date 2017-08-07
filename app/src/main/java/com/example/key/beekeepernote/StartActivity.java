package com.example.key.beekeepernote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.ArraySet;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.key.beekeepernote.database.Apiary;
import com.example.key.beekeepernote.database.BeeColony;
import com.example.key.beekeepernote.database.BeeFrame;
import com.example.key.beekeepernote.database.Beehive;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@EActivity
public class StartActivity extends AppCompatActivity {
    public AlertDialog alertDialog;
    public  DatabaseReference myRef;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Set<String> apiaries = new ArraySet<>();
    private int mNumberBeehiveInt = 0;
    @ViewById (R.id.buttonAddApiary)
    Button buttonAddApiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewBlankFragment(), "ADD NEW");
        viewPager.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Query myPlace = myRef.child("apiary");
        myPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Apiary apiary = new Apiary();
                    apiary = postSnapshot.getValue(Apiary.class);
                    if (apiaries.add(apiary.getNameApiary())) {
                        ApiaryFragment apiaryFragment = new ApiaryFragment();
                        adapter.addFragment(apiaryFragment, apiary.getNameApiary());
                        apiaryFragment.setData(apiary);
                        viewPager.setAdapter(adapter);
                        addOnLongClickListener();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void addOnLongClickListener() {
        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);

        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setId(i);
            tabStrip.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDeleteDialog(String.valueOf(tabLayout.getTabAt(v.getId()).getText()), v);
                    return true;
                }
            });
        }

    }

    private void showDeleteDialog(String s, final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING");
        builder.setMessage("Do you want to delete " + s +" Apiary?" + "All data in this section will be lost forever" );
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteApiary(String.valueOf(tabLayout.getTabAt(v.getId()).getText()), v.getId());
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteApiary(String d, final int position) {
        if (tabLayout.getTabCount() >= 1 && position < tabLayout.getTabCount() && position != 0) {
            myRef.child("apiary").child(d).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Toast.makeText(StartActivity.this, "Apiary was delete", Toast.LENGTH_SHORT).show();
                    tabLayout.removeTabAt(position);

                }
            });
        }
    }

    @Click(R.id. buttonAddApiary)
    void buttonAddApiaryWasClicked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NEW APIARY");
        builder.setMessage("Please, Enter name and count beehive Apiary");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final TextInputEditText inputName = new TextInputEditText(this);
        inputName.setHint("Name");
        inputName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputName.setLayoutParams(lp1);
        builder.setView(inputName);
        final TextInputEditText inputCount = new TextInputEditText(this);
        inputCount.setHint("Count of beehive");
        inputCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputCount.setLayoutParams(lp2);
        linearLayout.addView(inputName);
        linearLayout.addView(inputCount);
        builder.setView(linearLayout);

        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputName.getText().toString().length() == 0) {
                    inputName.setError("Please enter Name");

                } else if (inputCount.getText().toString().equals("")) {
                    inputCount.setError("Please enter count");
                } else {
                    createNewApiary(inputName.getText().toString(), Integer.parseInt(inputCount.getText().toString()));
                    alertDialog.dismiss();
                }
            }
        });
    }
    void createNewApiary(String name, int number){
        if (!name.equals("")){
            mNumberBeehiveInt = Integer.parseInt(String.valueOf(number));

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


            BeeColony beeColony = new BeeColony();
            beeColony.setHaveFood(true);
            beeColony.setNoteBeeColony("kdjfjsd");
            beeColony.setOutput("lsfkdk");
            beeColony.setQueen(true);
            beeColony.setRiskOfSwaddling(true);
            beeColony.setWorm(true);
            beeColony.setBeeFrames(beeFrames);

            List<BeeColony> BeeColonyList = new ArrayList<>();
            BeeColonyList.add(beeColony);
            BeeColonyList.add(beeColony);




            List<Beehive> beehiveList = new ArrayList<>();
            if (mNumberBeehiveInt > 0) {
                for (int i = 1; i<= mNumberBeehiveInt; i++){
                    Beehive beehive = new Beehive();
                    beehive.setFounded(1209939);
                    beehive.setNameBeehive(String.valueOf(i));
                    beehive.setNoteBeehive("dkfsdlkj;k");
                    beehive.setTypeBeehive(1);
                    beehive.setBeeColonies(BeeColonyList);
                    beehiveList.add(beehive);
                }
            }


            Apiary apiary = new Apiary();
            apiary.setLocationApiary("flfsdldlk");
            apiary.setNameApiary(name);
            apiary.setNoteApiary("kdslfsdlk");
            apiary.setBeehives(beehiveList);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("apiary").child(name).setValue(apiary);


        }
    }

}
