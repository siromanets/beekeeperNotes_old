package com.example.key.beekeepernote.fragments;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.adapters.RecyclerAdapter;
import com.example.key.beekeepernote.models.Apiary;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.id;
import static com.example.key.beekeepernote.activities.StartActivity.MODE_CLEAN_ITEM;

/**
 *
 */
public class ApiaryFragment extends android.support.v4.app.Fragment {
    public static final int DADAN = 1;
    public static final int UKRAINIAN = 2 ;
    private int mFragId;
    public RecyclerView recyclerView = null;
    public Apiary apiary;
    public FloatingActionButton buttonAddNewBeehive;
    private RecyclerView.LayoutManager mLayoutManager;
    public View fragmentView;
    private int modeType = MODE_CLEAN_ITEM;
    private RecyclerAdapter dataAdapter;
    private int mQuantityOfColonies = 0;
    private int mTypeBeehive;
    private AlertDialog alertDialog;

    public ApiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_apiary, container, false);
         int id = fragmentView.getId();
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
       createList(modeType);
        return fragmentView;
    }

    private void createList(int mode) {
        if (apiary != null) {
            dataAdapter = new RecyclerAdapter(apiary.getBeehives(), apiary.getNameApiary(), mode);
            recyclerView.setAdapter(dataAdapter);
            buttonAddNewBeehive = (FloatingActionButton) fragmentView.findViewById(R.id.buttonAddNewBeehive);
            buttonAddNewBeehive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogNewBeehive();
                    /**
                    FragmentManager fm = getFragmentManager();
                    NewBeehiveFragment dialogFragment = new NewBeehiveFragment_();
                    dialogFragment.setData(apiary);
                    dialogFragment.show(fm, "Sample Fragment"); */
                }
            });

        }
    }


    private void showDialogNewBeehive() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("New Beehive");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_new_beehive, null);
        builder.setView(dialogView);
        final RadioGroup radiogroupTypeBeehive = (RadioGroup)dialogView.findViewById(R.id.radiogroupTypebeehive);
        final RadioGroup radiogroupQuantityColonies = (RadioGroup)dialogView.findViewById(R.id.radiogroupQuantityColonies);
        final Button createNewBeehive = (Button)dialogView.findViewById(R.id.buttonCreateNewBehive);
        final NumberPicker numberPicker = (NumberPicker)dialogView.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(apiary.getBeehives().size() + 1);
        numberPicker.setValue(apiary.getBeehives().size() + 1);
        radiogroupTypeBeehive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radioButtonDadan:
                        Toast.makeText(getContext(), "Type beehive is dadan.",
                                Toast.LENGTH_SHORT).show();
                        mTypeBeehive = DADAN;
                        break;
                    case R.id.radioButtonUkrainian:
                        Toast.makeText(getContext(), "Type beehive is ukrainian.",
                                Toast.LENGTH_SHORT).show();
                        mTypeBeehive = UKRAINIAN;
                        break;
                    default:
                        break;
                }
            }
        });
        radiogroupQuantityColonies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radioButtonOneColony:
                        Toast.makeText(getContext(), "Type beehive is dadan.",
                                Toast.LENGTH_SHORT).show();
                        mQuantityOfColonies = 1;
                        break;
                    case R.id.radioButtonTwoColony:
                        Toast.makeText(getContext(), "Type beehive is ukrainian.",
                                Toast.LENGTH_SHORT).show();
                        mQuantityOfColonies = 2;
                        break;
                    default:
                        break;
                }
            }
        });
        createNewBeehive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberPicker.getValue() != 0 && mQuantityOfColonies != 0){
                    Beehive mBeehive = new Beehive();
                    mBeehive.setNumberBeehive(numberPicker.getValue());
                    mBeehive.setTypeBeehive(mTypeBeehive);
                    mBeehive.setFounded(Calendar.getInstance().getTime().getTime());

                    List<BeeColony> beeColonyList = new ArrayList<BeeColony>();
                    for (int i = 0; i < mQuantityOfColonies; i++){
                        BeeColony beeColony = new BeeColony();
                        beeColony.setBeeEmptyFrame(5);
                        beeColony.setCheckedTime(Calendar.getInstance().getTime().getTime());
                        beeColonyList.add(beeColony);
                    }
                    mBeehive.setBeeColonies(beeColonyList);

                   saveBeehive(mBeehive, apiary);
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(),"Please enter all necessary values ", Toast.LENGTH_SHORT).show();
                }
            }
        });

alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveBeehive(Beehive mBeehive, Apiary apiary) {
        apiary.getBeehives().add(mBeehive.getNumberBeehive() - 1, mBeehive);
        for (int i = 0; i < apiary.getBeehives().size(); i ++ ){
            apiary.getBeehives().get(i).setNumberBeehive(i - 1);
        }
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("apiary").child(apiary.getNameApiary())
                .setValue(apiary);

    }


    public void setData(Apiary apiary, int mode){
        this.apiary = apiary;
        this.mFragId = id;
        if (dataAdapter != null){
            dataAdapter.setList(apiary.getBeehives(), apiary.getNameApiary(), mode);
            dataAdapter.notifyDataSetChanged();
            recyclerView.invalidate();
        }else if(fragmentView != null){
            createList(modeType);
        }
    }
    public String getName(){
	   return apiary.getNameApiary();
    }

    public  void selectMode(int mode){

        if (dataAdapter != null) {
            dataAdapter.setList(apiary.getBeehives(), apiary.getNameApiary(), mode);
            dataAdapter.notifyDataSetChanged();

        }
    }
}