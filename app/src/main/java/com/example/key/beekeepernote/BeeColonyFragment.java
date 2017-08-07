package com.example.key.beekeepernote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.key.beekeepernote.database.BeeColony;
import com.example.key.beekeepernote.database.BeeFrame;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

public class BeeColonyFragment extends Fragment {

    public BeeColony beeColony;
    public String nameBeehive;
    public String nameApiary;
    public String nameColony;
    public int countWormsFrames = 0;
    public int countHoneysFrames = 0;
    public int countFrames = 0;
    public int countEmptyFrames = 0;
    public TextView countFrameWithWorm;
    public TextView countFrameWithHoney;
    public TextView countEmptyFrame;
    public TextView countFramesOfColony;


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
        countFrames = beeColony.getBeeFrames().size();
        List<BeeFrame> beeFrameList = beeColony.getBeeFrames();
        for (int i = 0; i < beeFrameList.size(); i++){
            if (beeFrameList.get(i).getTypeBox() == 1){
                countWormsFrames++;

            }else if (beeFrameList.get(i).getTypeBox() == 2){
                countHoneysFrames++;
            }else if (beeFrameList.get(i).getTypeBox() == 3){
                countEmptyFrames++;
            }
        }
        refreshTextViews();
        buttonPlusForWorms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countEmptyFrames != 0){
                    countEmptyFrames--;
                    countWormsFrames++;
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
                if (countWormsFrames != 0){
                    countEmptyFrames++;
                    countWormsFrames--;
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
                if (countEmptyFrames != 0){
                    countEmptyFrames--;
                    countHoneysFrames++;
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
                if (countHoneysFrames != 0){
                    countEmptyFrames++;
                    countHoneysFrames--;
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
                    countEmptyFrames++;
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
                if (countEmptyFrames != 0){
                    countEmptyFrames--;
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
        saveData();
        super.onPause();
    }

    private void saveData() {
        BeeColony beeColony = new BeeColony();
        List<BeeFrame> allFrame = new ArrayList<>();
        if (countEmptyFrames != 0){
            for(int i = 0; i <= countEmptyFrames; i++){
                BeeFrame beeFrame = new BeeFrame();
                beeFrame.setTypeBox(3);
                allFrame.add(beeFrame);
            }
        }
        if (countWormsFrames != 0){
            for(int i = 0; i <= countWormsFrames; i++){
                BeeFrame beeFrame = new BeeFrame();
                beeFrame.setTypeBox(1);
                allFrame.add(beeFrame);
            }
        }
        if (countHoneysFrames != 0){
            for(int i = 0; i <= countHoneysFrames; i++){
                BeeFrame beeFrame = new BeeFrame();
                beeFrame.setTypeBox(2);
                allFrame.add(beeFrame);
            }
        }
        beeColony.setBeeFrames(allFrame);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("apiary").child(nameApiary).child("beehives").child(nameBeehive)
                .child("beeColonies").child(nameColony).setValue(beeColony);
    }


    private void refreshTextViews() {
        countFrameWithWorm.setText(String.valueOf(countWormsFrames));
        countFrameWithHoney.setText(String.valueOf(countHoneysFrames));
        countEmptyFrame.setText(String.valueOf(countEmptyFrames));
        countFramesOfColony.setText(String.valueOf(countFrames));
    }

    public void setData(BeeColony beeColony, String nameApiary, String nameBeehive, String nameColony){
        this.beeColony = beeColony;
        this.nameApiary = nameApiary;
        this.nameBeehive = nameBeehive;
        this.nameColony = nameColony;
    }
}
