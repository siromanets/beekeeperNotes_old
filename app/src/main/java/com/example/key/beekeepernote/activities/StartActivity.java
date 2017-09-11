package com.example.key.beekeepernote.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.adapters.ViewPagerAdapter;
import com.example.key.beekeepernote.fragments.ApiaryFragment;
import com.example.key.beekeepernote.fragments.NewBlankFragment;
import com.example.key.beekeepernote.fragments.ToolsListFragment;
import com.example.key.beekeepernote.fragments.ToolsListFragment_;
import com.example.key.beekeepernote.interfaces.Communicator;
import com.example.key.beekeepernote.models.Apiary;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;
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

import static com.example.key.beekeepernote.fragments.ApiaryFragment.DADAN;

@EActivity
public class StartActivity extends AppCompatActivity implements Communicator {
    public static final int MODE_CLEAN_ITEM = 0;
    public static final int MODE_SELECT_ALL = 3;
    public static final int MODE_MULTI_SELECT = 2;
    public AlertDialog alertDialog;
    public  DatabaseReference myRef;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int mNumberBeehiveInt = 0;
    private int startPageNumber = 0;
    private int pasteSettingsChecker;
    private boolean multiSelectMode = false;
    private DataSnapshot mDataSnapshot;
    private ToolsListFragment mDialogFragment = null;

    @ViewById (R.id.buttonAddApiary)
    Button buttonAddApiary;

    @ViewById(R.id.appBarlayout)
    AppBarLayout appBarLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();

    }


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
                if (mDialogFragment != null && !multiSelectMode){
                    mDialogFragment.dismiss();
                    mDialogFragment = null;
                    multiSelectMode = false;
                    loadDataSnapshot(mDataSnapshot);
                    Toast.makeText(StartActivity.this, "Ви не можете вибирати одразу в обох вкладках", Toast.LENGTH_SHORT).show();

                }

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
                if(dataSnapshot != null) {
                    mDataSnapshot = dataSnapshot;
                    loadDataSnapshot(mDataSnapshot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadDataSnapshot(DataSnapshot dataSnapshot) {
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
            int id = adapter.getItemPosition(apiaryFragment);
            apiaryFragment.setData(apiary, id);
            viewPager.setAdapter(adapter);
            addOnLongClickListener();
            if (mDialogFragment != null && multiSelectMode) {
                apiaryFragment.selectMode(MODE_MULTI_SELECT);
            }

        }
        viewPager.setCurrentItem(startPageNumber);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount() -1);
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
        mDialogFragment = null;
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


    }

    @Override
    public void setDataForTools(Beehive beehive, View view, String nameApiary) {
        if (mDialogFragment == null) {
            FragmentManager fm = getSupportFragmentManager();
            mDialogFragment = new ToolsListFragment_();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fm

                    .beginTransaction();
            fragmentTransaction.add(R.id.containerForToolsGroup, mDialogFragment);
            fragmentTransaction.commit();
            mDialogFragment.setData(beehive, view, nameApiary);
        }else {
            mDialogFragment.setData(beehive, view, nameApiary);
        }

    }

    @Override
    public void selectAll() {
        multiSelectMode = true;
        loadDataSnapshot(mDataSnapshot);
        ApiaryFragment fr = (ApiaryFragment) adapter.getItem(tabLayout.getSelectedTabPosition());
        fr.selectMode(MODE_SELECT_ALL);

    }

    @Override
    public void multiSelectMod() {
        multiSelectMode = true;
    }

    @Override
    public void deleteBeehive( Set<Beehive> beehives, final String nameApiary, final boolean refreshBeehivesListItem) {

       final List<Beehive> deletedBeehives = new ArrayList<Beehive>() {};
        deletedBeehives.addAll(beehives);
        Query apiary = myRef.child("apiary").child(nameApiary);
        apiary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apiary apiary = dataSnapshot.getValue(Apiary.class);
                if (deletedBeehives.size() != 0){
                    int numberDeletedBeehives = 0;
                    for (int numberBeehivesInDatabase = 0; numberDeletedBeehives < deletedBeehives.size();numberBeehivesInDatabase++){
                        if (deletedBeehives.get(numberDeletedBeehives).getNumberBeehive() == apiary.getBeehives().get(numberBeehivesInDatabase).getNumberBeehive()){
                            apiary.getBeehives().remove(numberBeehivesInDatabase);
                            numberDeletedBeehives++;
                            numberBeehivesInDatabase = 0;
                        }
                    }

                }
                    int finalNumberBeehives = apiary.getBeehives().size();
                    for (int a = 0; a < finalNumberBeehives; a++ ) {
                        apiary.getBeehives().get(a).setNumberBeehive(a + 1);

                }
                myRef.child("apiary").child(nameApiary).setValue(apiary);
                if (refreshBeehivesListItem){
                    multiSelectMode = true;
                }else{
                    mDialogFragment = null;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void moveBeehive(final Set<Beehive> moveBeehives, final Beehive itemBeehive,
                            final String fromWhichApiary, final String inWhichApiary, final boolean inFront) {

            Query apiary = myRef.child("apiary").child(inWhichApiary);
            apiary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiaryInWhich = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> beehives = apiaryInWhich.getBeehives();
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(moveBeehives);

                    if (inFront){
                        for (int i = 0; i < pastedBeehives.size(); i++) {
                            beehives.add(itemBeehive.getNumberBeehive() - 1 + i, new Beehive(pastedBeehives.get(i)));
                        }
                    }else {
                        for (int i = 0; i < pastedBeehives.size(); i++) {
                            beehives.add(itemBeehive.getNumberBeehive() + i, new Beehive(pastedBeehives.get(i)));
                        }
                    }
                    for (int a = 0; a < beehives.size(); a++) {
                        beehives.get(a).setNumberBeehive(a + 1);
                    }
                    apiaryInWhich.setBeehives(beehives);
                    myRef.child("apiary").child(inWhichApiary).setValue(apiaryInWhich);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        multiSelectMode = false;
        mDialogFragment = null;
    }


    @Override
    public void replaceBeehive(final Set<Beehive> replaceBeehives, final Beehive itemBeehive, final String fromWhichApiary,
                               final String inWhichApiary) {
        multiSelectMode = false;
        if (fromWhichApiary.equals(inWhichApiary)) {
            Query apiary = myRef.child("apiary").child(inWhichApiary);
            apiary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiary = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();

                    pastedBeehives.addAll(replaceBeehives);
                    if (pastedBeehives.size() != 0) {
                        int eqwalsBeehivesApiary = apiary.getBeehives().size();
                        for (int numberBeehivesInDatabase = 0; numberBeehivesInDatabase < eqwalsBeehivesApiary; numberBeehivesInDatabase++) {
                            if (itemBeehive.getNumberBeehive() == apiary.getBeehives().get(numberBeehivesInDatabase).getNumberBeehive()) {
                                apiary.getBeehives().remove(numberBeehivesInDatabase);
                                for (int x = 0; x < pastedBeehives.size(); x++) {
	                                Beehive addBeehive = new Beehive(pastedBeehives.get(x));
	                                addBeehive.setNumberBeehive(itemBeehive.getNumberBeehive());
                                    apiary.getBeehives().add(itemBeehive.getNumberBeehive() - 1 + x, new Beehive(addBeehive));
                                }
                            }
                            if (pastedBeehives.get(0).getNumberBeehive() == apiary.getBeehives().get(numberBeehivesInDatabase).getNumberBeehive()) {
                                apiary.getBeehives().remove(numberBeehivesInDatabase);
	                            Beehive addBeehive = new Beehive(itemBeehive);
	                            addBeehive.setNumberBeehive(pastedBeehives.get(0).getNumberBeehive());
                                apiary.getBeehives().add(pastedBeehives.get(0).getNumberBeehive() - 1, new Beehive(addBeehive));
                            }
                        }
                    }
                    myRef.child("apiary").child(fromWhichApiary).setValue(apiary);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else{
            Query apiary = myRef.child("apiary").child(inWhichApiary);
            apiary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiaryInWhich = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> beehives = apiaryInWhich.getBeehives();
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(replaceBeehives);

                    beehives.remove(itemBeehive.getNumberBeehive() - 1);
                    beehives.add(itemBeehive.getNumberBeehive() -1, new Beehive(pastedBeehives.get(0)));
                    for (int a = 0; a < beehives.size(); a++) {
                        beehives.get(a).setNumberBeehive(a + 1);
                    }
                    apiaryInWhich.setBeehives(beehives);
                    myRef.child("apiary").child(inWhichApiary).setValue(apiaryInWhich);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            Query apiary1 = myRef.child("apiary").child(fromWhichApiary);
            apiary1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiaryFromWhich = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> beehives = apiaryFromWhich.getBeehives();
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(replaceBeehives);
                    beehives.remove(pastedBeehives.get(0).getNumberBeehive() - 1);
                    beehives.add(pastedBeehives.get(0).getNumberBeehive() -1, new Beehive(itemBeehive));
                    for (int a = 0; a < beehives.size(); a++) {
                        beehives.get(a).setNumberBeehive(a + 1);
                    }
                    apiaryFromWhich.setBeehives(beehives);
                    myRef.child("apiary").child(fromWhichApiary).setValue(apiaryFromWhich);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        mDialogFragment = null;
    }

    @Override
    public void onBackPressed() {
        if (mDialogFragment != null){
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(mDialogFragment).commit();
	        mDialogFragment = null;
            multiSelectMode = false;
            loadDataSnapshot(mDataSnapshot);
        }
    }
}
