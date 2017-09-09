package com.example.key.beekeepernote.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.interfaces.Communicator;
import com.example.key.beekeepernote.models.BeeColony;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 *
 */

public class BeeColonyFragment extends Fragment{

    public BeeColony beeColony;
    public int nameBeehive;
    public String nameApiary;
    public String nameColony;
    public int quantityWormsFrames = 0;
    public int quantityHoneyFrames = 0;
    public int countFrames = 0;
    public int quantityEmptyFrames = 0;
    public TextView countFrameWithWorm;
    public TextView countFrameWithHoney;
    public TextView countEmptyFrame;
    public TextView countFramesOfColony;
    private Communicator mCommunicator;
    private DatabaseReference myRef;
    private int mNumberCollony;

    public BeeColonyFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_beehive, container, false);

        ImageView imageWormsFrame = (ImageView)view.findViewById(R.id.imageWormsFrame);
        ImageView imageHoneysFrame = (ImageView)view.findViewById(R.id.imageHoneysFrame);
        ImageView imageEmptyFrame = (ImageView)view.findViewById(R.id.imageEmptyFrame);
        FloatingActionButton buttonPlusForWorms = (FloatingActionButton)view
                .findViewById(R.id.buttonPlasForWorms);
        FloatingActionButton buttonMinusForWorms = (FloatingActionButton)view
                .findViewById(R.id.buttonMinusForWorms);
        FloatingActionButton buttonPlusForHoney = (FloatingActionButton)view
                .findViewById(R.id.buttonPlasForHoney);
        FloatingActionButton buttonMinusForHoney = (FloatingActionButton)view
                .findViewById(R.id.buttonMinusForHoney);
        FloatingActionButton buttonAddFrame = (FloatingActionButton)view
                .findViewById(R.id.buttonAddFrame);
        FloatingActionButton buttonDellFrame = (FloatingActionButton)view
                .findViewById(R.id.buttonDellFrame);
        countFrameWithWorm = (TextView)view.findViewById(R.id.countFrameWithWorm);
        countFrameWithHoney = (TextView)view.findViewById(R.id.countFrameWithHoney);
        countEmptyFrame = (TextView)view.findViewById(R.id.countEmptyFrame);
        countFramesOfColony = (TextView)view.findViewById(R.id.countOfFrames);
        quantityEmptyFrames = beeColony.getBeeEmptyFrame();
        quantityWormsFrames = beeColony.getBeeWormsFrame();
        quantityHoneyFrames = beeColony.getBeeHoneyFrame();
        countFrames = quantityEmptyFrames + quantityWormsFrames + quantityHoneyFrames;
        Button buttonSaveChanges = (Button)view.findViewById(R.id.saveChanges);
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        refreshTextViews();
        buttonPlusForWorms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityEmptyFrames != 0){
                    quantityEmptyFrames--;
                    quantityWormsFrames++;
                    refreshTextViews();
                }else{
                    Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonMinusForWorms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityWormsFrames != 0){
                    quantityEmptyFrames++;
                    quantityWormsFrames--;
                    refreshTextViews();
                }else{
                    Toast.makeText(getContext(), "You  dont have free frame. You must first add empty frame. ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonPlusForHoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityEmptyFrames != 0){
                    quantityEmptyFrames--;
                    quantityHoneyFrames++;
                    refreshTextViews();
                }else{
                    Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonMinusForHoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityHoneyFrames != 0){
                    quantityEmptyFrames++;
                    quantityHoneyFrames--;
                    refreshTextViews();
                }else{
                    Toast.makeText(getContext(), "You  don't have free frame. You must first add empty frame. ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonAddFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countFrames < 20){
                    quantityEmptyFrames++;
                    countFrames++;
                    refreshTextViews();
                }else{
                    Toast.makeText(getContext(), "This hive can not contain more frames",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonDellFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityEmptyFrames != 0){
                    quantityEmptyFrames--;
                    countFrames--;
                    refreshTextViews();
                }else{
                    Toast.makeText(getContext(), "Do not have empty frames ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }



    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }
    private void saveData() {
        BeeColony beeColony = new BeeColony();
        beeColony.setBeeEmptyFrame(quantityEmptyFrames);
        beeColony.setBeeWormsFrame(quantityWormsFrames);
        beeColony.setBeeHoneyFrame(quantityHoneyFrames);
        beeColony.setNoteBeeColony("");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("apiary").child(nameApiary).child("beehives").child(String.valueOf(nameBeehive -1))
                .child("beeColonies").child(nameColony).setValue(beeColony);
    }


    private void refreshTextViews() {
        countFrameWithWorm.setText(String.valueOf(quantityWormsFrames));
        countFrameWithHoney.setText(String.valueOf(quantityHoneyFrames));
        countEmptyFrame.setText(String.valueOf(quantityEmptyFrames));
        countFramesOfColony.setText(String.valueOf(countFrames));
    }

    public void setData(BeeColony beeColony, String nameApiary, int numberBeehive, String nameColony){
        this.beeColony = beeColony;
        this.nameApiary = nameApiary;
        this.nameBeehive = numberBeehive;
        this.nameColony = nameColony;

    }

}
