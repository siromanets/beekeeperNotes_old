package com.example.key.beekeepernote.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.key.beekeepernote.interfaces.Communicator;
import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.models.Apiary;
import com.example.key.beekeepernote.models.BeeColony;
import com.example.key.beekeepernote.models.Beehive;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.key.beekeepernote.fragments.ApiaryFragment.DADAN;
import static com.example.key.beekeepernote.fragments.ApiaryFragment.UKRAINIAN;

@EFragment
public class NewBeehiveFragment extends DialogFragment {
    public Apiary apiary;
    private int mSerialNumber = 0;
    private int mQuantityOfColonies = 0;
    private int mTypeBeehive;
    private Communicator mCommunicator;

    @ViewById(R.id.textViewSerialNumber)
    TextView textViewSerialNumber;

    @ViewById(R.id.buttonPlasSerialNumber)
    Button buttonPlusSerialNumber;

    @ViewById(R.id.buttonMinusSerialNumber)
    Button buttonMinusSerialNumber;

    @ViewById(R.id.radiogroupTypebeehive)
    RadioGroup radiogroupTypeBeehive;

    @ViewById(R.id.radiogroupQuantityColonies)
    RadioGroup radiogroupQuantityColonies;

    @ViewById(R.id.createNewBehive)
    Button createNewBeehive;

    public NewBeehiveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_beehive, container, false);

        return view;
    }

    @AfterViews
    void afterViews(){
        mSerialNumber = apiary.getBeehives().size() + 1;
        textViewSerialNumber.setText(String.valueOf(mSerialNumber));
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
    }
    @Click(R.id.buttonPlasSerialNumber)
    void buttonPlusSerialNumberWasClicked(View view){
        mSerialNumber++;
        textViewSerialNumber.setText(String.valueOf(mSerialNumber));
    }

    @Click(R.id.buttonMinusSerialNumber)
    void setButtonMinusSerialNumberSerialNumberWasClicked(View view){
        mSerialNumber--;
        textViewSerialNumber.setText(String.valueOf(mSerialNumber));
    }
    @Click(R.id.createNewBehive)
    void createNewBeehive(View view){
        if (mSerialNumber != 0 && mQuantityOfColonies != 0){
            Beehive mBeehive = new Beehive();
            mBeehive.setNumberBeehive(mSerialNumber);
            mBeehive.setTypeBeehive(mTypeBeehive);
            mBeehive.setFounded(Calendar.getInstance().getTime());
            mBeehive.setCheckedTime(Calendar.getInstance().getTime());
            List<BeeColony> beeColonyList = new  ArrayList<BeeColony>();
            for (int i = 0; i < mQuantityOfColonies; i++){
                BeeColony beeColony = new BeeColony();
                beeColony.setBeeEmptyFrame(5);
                beeColonyList.add(beeColony);
            }
            mBeehive.setBeeColonies(beeColonyList);
            mCommunicator = (Communicator)getActivity();
            mCommunicator.saveBeehive(mBeehive, apiary.getNameApiary());
            this.dismiss();
        }else {
            Toast.makeText(getContext(),"Please enter all necessary values ", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(Apiary apiary){
        this.apiary = apiary;

    }
}

