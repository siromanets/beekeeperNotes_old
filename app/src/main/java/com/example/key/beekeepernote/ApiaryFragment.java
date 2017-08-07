package com.example.key.beekeepernote;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.key.beekeepernote.database.Apiary;

/**
 *
 */
public class ApiaryFragment extends android.support.v4.app.Fragment {
    public static final int DADAN = 1;
    private static final int UKRAINIAN = 2 ;

    public RecyclerView recyclerView;
    public Apiary apiary;
    public android.app.AlertDialog alertDialog;
    public FloatingActionButton buttonAddNewBeehive;
    private RecyclerView.LayoutManager mLayoutManager;



    public ApiaryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_apiary, container, false);
        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        RecyclerAdapter dataAdapter = new RecyclerAdapter(apiary.getBeehives(), apiary.getNameApiary());
        recyclerView.setAdapter(dataAdapter);
        buttonAddNewBeehive = (FloatingActionButton)fragmentView.findViewById(R.id.buttonAddNewBeehive);
        buttonAddNewBeehive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                NewBeehiveFragment dialogFragment = new NewBeehiveFragment ();
                dialogFragment.show(fm, "Sample Fragment");
                /**
                final String[] mChooseBeehivesType = { "DADAN", "UKRAINIAN"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("NEW BEEHIVE");
                builder.setMessage("Please, Enter number and count bee's Colony in this beehive ");
                LinearLayoutCompat linearLayout = new LinearLayoutCompat(getActivity());
                linearLayout.setOrientation(LinearLayoutCompat.VERTICAL);
                final TextInputEditText inputName = new TextInputEditText(getActivity());
                inputName.setHint("Sequence number");
                inputName.setInputType(InputType.TYPE_CLASS_NUMBER);
                LinearLayoutCompat.LayoutParams lp1;
                lp1 = (new LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
                inputName.setLayoutParams(lp1);
                builder.setView(inputName);
                final TextInputEditText inputCount = new TextInputEditText(getActivity());
                inputCount.setHint("Count of colony");
                inputCount.setInputType(InputType.TYPE_CLASS_NUMBER);
                LinearLayoutCompat.LayoutParams lp2 =(new LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
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

                builder.setSingleChoiceItems(mChooseBeehivesType, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                Toast.makeText(getContext(),
                                        "Любимое имя кота: "
                                                + mChooseBeehivesType[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (inputName.getText().toString().length() == 0) {
                            inputName.setError("Please enter sequence number!");

                        } else if (inputCount.getText().toString().equals("")) {
                            inputCount.setError("Please enter count");
                        } else {
                            createNewBeehive(Integer.parseInt(inputName.getText().toString()), Integer.parseInt(inputCount.getText().toString()));
                            alertDialog.dismiss();
                        }
                    }
                });
                 **/
            }
        });

        return fragmentView;
    }

    private void createNewBeehive(int sequenceNumber, int countNumber) {

    }


    public void setData(Apiary apiary){
        this.apiary = apiary;

    }

}