package com.example.key.beekeepernote;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
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

import static com.example.key.beekeepernote.ApiaryFragment.DADAN;

@EActivity
public class StartActivity extends AppCompatActivity implements Communicator {
    public AlertDialog alertDialog;
    public  DatabaseReference myRef;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int mNumberBeehiveInt = 0;
    private int startPageNumber = 0;
    private int pasteSettingsChecker;
    private Beehive mCopyBeehive = null;
    private Beehive mCutBeehive = null;



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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
              if (tab.getPosition() > 0) {
                  startPageNumber = tab.getPosition();
              }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
               int er = tab.getPosition();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewBlankFragment(), "ADD NEW");
        viewPager.setAdapter(adapter);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Query myApiaries = myRef.child("apiary");
        myApiaries.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int d = tabLayout.getTabCount();
                if (d >= 2 ) {
                    for (int i = 1; i < d; i++) {
                        tabLayout.removeTabAt(1);
                        adapter.mFragmentList.remove(1);
                        adapter.mFragmentTitleList.remove(1);
                        adapter.notifyDataSetChanged();
                    }
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Apiary apiary = postSnapshot.getValue(Apiary.class);
                        ApiaryFragment apiaryFragment = new ApiaryFragment();
                        adapter.addFragment(apiaryFragment, apiary.getNameApiary());
                        apiaryFragment.setData(apiary);
                        viewPager.setAdapter(adapter);
                        addOnLongClickListener();
                }
                viewPager.setCurrentItem(startPageNumber);
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

    private void showDeleteDialog(final String s, final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING");
        builder.setMessage("Do you want to delete " + s +" Apiary?" + "All data in this section will be lost forever" );
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteApiary(s, v.getId());
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

    private void deleteApiary(String name, final int position) {
        if (tabLayout.getTabCount() >= 1 && position < tabLayout.getTabCount() && position != 0) {
            myRef.child("apiary").child(name).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Toast.makeText(StartActivity.this, "Apiary was delete", Toast.LENGTH_SHORT).show();
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
            BeeColony beeColony = new BeeColony();
            beeColony.setHaveFood(true);
            beeColony.setNoteBeeColony("ffd");
            beeColony.setOutput("ff");
            beeColony.setQueen(true);
            beeColony.setRiskOfSwaddling(false);
            beeColony.setWorm(true);
            beeColony.setBeeEmptyFrame(5);
            beeColony.setBeeHoneyFrame(0);
            beeColony.setBeeWormsFrame(0);


            List<BeeColony> BeeColonyList = new ArrayList<>();
            BeeColonyList.add(beeColony);
            BeeColonyList.add(beeColony);
            List<Beehive> beehiveList = new ArrayList<>();
            if (mNumberBeehiveInt > 0) {
                for (int i = 1; i<= mNumberBeehiveInt; i++){
                    Beehive beehive = new Beehive();
                    beehive.setFounded(1209939);
                    beehive.setNumberBeehive(i);
                    beehive.setNoteBeehive("dkfsdlkj;k");
                    beehive.setTypeBeehive(DADAN);
                    beehive.setBeeColonies(BeeColonyList);
                    beehiveList.add(beehive);
                }
            }


            Apiary apiary = new Apiary();
            apiary.setLocationApiary("flfsdldlk");
            apiary.setNameApiary(name);
            apiary.setNoteApiary("kdslfsdlk");
            apiary.setBeehives(beehiveList);

            myRef.child("apiary").child(name).setValue(apiary);


        }
    }


    @Override
    public void saveBeehive(Beehive beehive, String nameApiary) {
        myRef.child("apiary").child(nameApiary).child("beehives").child(String.valueOf(beehive.getNumberBeehive() - 1)).setValue(beehive);

    }

    @Override
    public void saveColony(BeeColony beeColony, String nameApiary, int nameBeehive) {
        myRef.child("apiary").child(nameApiary).child("beehives").child(String.valueOf(nameBeehive))
                .child("beeColonies").child(beeColony.getNoteBeeColony()).setValue(beeColony);

    }

    @Override
    public void deleteBeehive( Set<Beehive> beehives, final String nameApiary) {
       final List<Beehive> deletedBeehives = new ArrayList<Beehive>() {};
        deletedBeehives.addAll(beehives);
        Query apiary = myRef.child("apiary").child(nameApiary);
        apiary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apiary apiary = dataSnapshot.getValue(Apiary.class);
                if (deletedBeehives.size() != 0){
                    int k = 0;
                    for (int i = 0; k < apiary.getBeehives().size();i++){
                        if (deletedBeehives.get(k).getNumberBeehive() == apiary.getBeehives().get(i).getNumberBeehive()){
                            apiary.getBeehives().remove(i);
                            k++;
                            i = 0;
                        }

                        if (i == apiary.getBeehives().size()){

                        }
                    }
                    int count = apiary.getBeehives().size();
                    for (int a = 0; a < count; a++ ){
                        apiary.getBeehives().get(a).setNumberBeehive(a + 1);
                    }

                }


                myRef.child("apiary").child(nameApiary).setValue(apiary);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void moveBeehive( final Set<Beehive> copiedBeehivesSet, final Beehive itemBeehive, final String fromWhichApiary,
                            final String inWhichApiary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Paste");
        builder.setMessage("Where to paste?");
        builder.setPositiveButton("IN FRONT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        pasteSettingsChecker = 1;
                        changeData(copiedBeehivesSet, itemBeehive, fromWhichApiary, inWhichApiary);
                        dialog.cancel();

                    }
                });
        builder.setNegativeButton("BEHIND",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        pasteSettingsChecker = 2;
                        dialog.cancel();
                        changeData(copiedBeehivesSet, itemBeehive, fromWhichApiary, inWhichApiary);
                    }
                });
        builder.setNeutralButton("Swap places",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        pasteSettingsChecker = 0;
                        dialog.cancel();
                        changeData(copiedBeehivesSet, itemBeehive, fromWhichApiary, inWhichApiary);
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();



    }



    private void changeData(final Set<Beehive> copiedBeehivesSet, final Beehive itemBeehive, final String fromWhichApiary,
                            final String inWhichApiary) {


        Query apiary = myRef.child("apiary").child(inWhichApiary);
        apiary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (pasteSettingsChecker == 0 ){
                    Apiary apiary = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    List<Beehive> changedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(copiedBeehivesSet);
                    for (int x = 0; x < pastedBeehives.size(); x++){
                        pastedBeehives.get(x).setNumberBeehive(itemBeehive.getNumberBeehive()  + x);
                    }
                    apiary.getBeehives().remove(itemBeehive.getNumberBeehive() - 1);
                    for (int i = itemBeehive.getNumberBeehive(); i < apiary.getBeehives().size(); i++) {
                        apiary.getBeehives().get(i).setNumberBeehive(i + pastedBeehives.size() + 1);
                        changedBeehives.add(apiary.getBeehives().get(i));
                        apiary.getBeehives().remove(i);
                    }
                    apiary.getBeehives().addAll(pastedBeehives);
                    apiary.getBeehives().addAll(changedBeehives);
                    myRef.child("apiary").child(inWhichApiary).setValue(apiary);

                }else if (pasteSettingsChecker == 1) {
                    Apiary apiary = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    List<Beehive> changedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(copiedBeehivesSet);
                    for (int x = 0; x < pastedBeehives.size(); x++){
                        pastedBeehives.get(x).setNumberBeehive(itemBeehive.getNumberBeehive() + x);
                    }
                    int count = apiary.getBeehives().size();
                    for (int i = itemBeehive.getNumberBeehive() - 1; i < count; i++) {
                        apiary.getBeehives().get(itemBeehive.getNumberBeehive()-1).setNumberBeehive(i + 1 + pastedBeehives.size());
                        changedBeehives.add(apiary.getBeehives().get(itemBeehive.getNumberBeehive()-1));
                        apiary.getBeehives().remove(itemBeehive.getNumberBeehive()-1);
                    }
                    apiary.getBeehives().addAll(pastedBeehives);
                    apiary.getBeehives().addAll(changedBeehives);
                    myRef.child("apiary").child(fromWhichApiary).setValue(apiary);
                }else if (pasteSettingsChecker == 2){
                    Apiary apiary = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    List<Beehive> changedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(copiedBeehivesSet);
                    for (int x = 0; x < pastedBeehives.size(); x++){
                        pastedBeehives.get(x).setNumberBeehive(itemBeehive.getNumberBeehive() + 1 + x);
                    }
                    apiary.getBeehives().addAll(pastedBeehives);
                    for (int i = itemBeehive.getNumberBeehive(); i < apiary.getBeehives().size(); i++) {
                        apiary.getBeehives().get(i).setNumberBeehive(i + pastedBeehives.size() + 1);
                        changedBeehives.add(apiary.getBeehives().get(i));
                        apiary.getBeehives().remove(i);
                    }
                    apiary.getBeehives().addAll(pastedBeehives);
                    apiary.getBeehives().addAll(changedBeehives);
                    myRef.child("apiary").child(fromWhichApiary).setValue(apiary);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


}
