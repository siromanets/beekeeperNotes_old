package com.carpathianapiary.niko.beekeepernotes.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carpathianapiary.niko.beekeepernotes.R;

import com.carpathianapiary.niko.beekeepernotes.interfaces.Communicator;
import com.carpathianapiary.niko.beekeepernotes.models.Beehive;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Key on 09.07.2017.
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final String USER_SELECTED_BEEHIVE = "user_selected_beehive" ;
    public static final String NAME_APIARY = "name_apiary" ;
    public static final String COLONY_NUMBER = "colony_number";
    public List<Beehive> mBeehiveList;
    public int selectMode;
    public String nameApiary;
    public Communicator communicator;
        //use context to intent Url
    public Context context;
    public int mHheight ;
    public int mWigth;
    public ActionMode mActionMode;
    public SparseBooleanArray mSelectedItemsIds;
    public Communicator mCommunicator;
    public List<Beehive> selectedBeehives;
    public boolean replaceMode = false;


    public RecyclerAdapter(List<Beehive> beehiveList, String nameApiary, int mode) {
            this.mBeehiveList = beehiveList;
            this.nameApiary = nameApiary;
            this.selectMode = mode;
            mSelectedItemsIds = new SparseBooleanArray();
        }

        public void setList(List<Beehive> beehiveList, String nameApiary, int mode){
            this.mBeehiveList = beehiveList;
            this.nameApiary = nameApiary;
            this.selectMode = mode;
        }

    public void sellecktAll() {
            removeSelection();
        for (int i = 0; i < mBeehiveList.size(); i++) {
            toggleSelection(i);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
            public ImageView imageBeehive;
        public ClickListener mClickListener;
        public Beehive mBeehive;
        public TextView mBeehiveNumber;
        public View mItemView;
        public ProgressBar mCheckedTimeProgress1;
        public ProgressBar mCheckedTimeProgress2;
        public ProgressBar mFoodProgress;
        public CircleImageView imageEye;
        public int mPosition;


            public ViewHolder(View itemView, ClickListener listener) {
                super(itemView);

                mClickListener = listener;
                this.mItemView = itemView;
                imageBeehive = (ImageView)itemView.findViewById(R.id.imageBeeHive);
                mBeehiveNumber = (TextView)itemView.findViewById(R.id.textNumberBeeColony);
                mCheckedTimeProgress1 = (ProgressBar)itemView.findViewById(R.id.progressBarCheckedTime1);
                mCheckedTimeProgress1.setMax(10);
                mCheckedTimeProgress2 = (ProgressBar)itemView.findViewById(R.id.progressBarCheckedTime2);
                mCheckedTimeProgress2.setMax(10);
                mFoodProgress = (ProgressBar)itemView.findViewById(R.id.progressBarFood);
                mFoodProgress.setMax(10);
                imageEye = (CircleImageView)itemView.findViewById(R.id.imageEye);
                mBeehive = null;
                mPosition = 0;
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);

            }
            @Override
            public void onClick(View v) {
                mClickListener.onPressed(mPosition ,mBeehive, v);
            }

            @Override
            public boolean onLongClick(View view) {
                mClickListener.onLongPressed(mPosition, mBeehive, view);
                return false;
            }




            public interface ClickListener {
                void onPressed(int position, Beehive beehive, View view);
                void onLongPressed(int position, Beehive beehive, View view);
            }
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_beehive, parent, false);
            context = mView.getContext();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int wight = metrics.widthPixels;

            ViewGroup.LayoutParams params = mView.getLayoutParams();
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                params.width = (wight / 3) - 5;
                params.height = params.width;
            }else if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                params.width = (wight / 5) - 3;
                params.height = params.width;
            }
            mView.setLayoutParams(params);
            mWigth = (wight / 3) - 5;
            ViewHolder mHolder = new ViewHolder(mView, new ViewHolder.ClickListener() {
                @Override
                public void onPressed(int position, Beehive beehive, View view) {
                    onListItemSelect(position, beehive, 2);
                }

                @Override
                public void onLongPressed(int position, Beehive beehive, View view) {
                        onListItemSelect(position, beehive, 1);
/**
                    if (selectMode == MODE_CLEAN_ITEM ) {
                        communicator = (Communicator) context;
                        communicator.setDataForTools(beehive, view, nameApiary);
                        selectMode = MODE_MULTI_SELECT;
                    }*/
                }

            });

            return mHolder;
        }



    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            holder.mPosition = position;
            holder.mBeehive = mBeehiveList.get(position);
            holder.mCheckedTimeProgress1.setVisibility(View.GONE);
            holder.mCheckedTimeProgress2.setVisibility(View.GONE);
            setProgress(holder);
        /** Change background_image color of the selected items in list view  **/
        holder.itemView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);
        if (mWigth != 0){
                ViewGroup.LayoutParams params = holder.mBeehiveNumber.getLayoutParams();
                params.width = mWigth / 4;
                params.height = mWigth / 4;
                holder.mBeehiveNumber.setLayoutParams(params);
            }
            holder.mBeehiveNumber.setText(String.valueOf(holder.mBeehive.getNumberBeehive()));

            switch (holder.mBeehive.getTypeBeehive()){
                case 1: holder.imageBeehive.setImageResource(R.drawable.ic_beehive_one);
                    return;
                case 2: holder.imageBeehive.setImageResource(R.drawable.ic_beehive_left);
            }


        }

    private void setProgress(ViewHolder holder) {
        if(holder.mBeehive.getBeeColonies().size() == 1){
            long diff3 = holder.mBeehive.getBeeColonies().get(0).getCheckedTime() +
                    (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
            int deys3 = (int)diff3 /24 / 60 / 60 / 1000;
            holder.mCheckedTimeProgress1.setProgress(deys3);
            holder.mCheckedTimeProgress1.setVisibility(View.VISIBLE);

        }else if (holder.mBeehive.getBeeColonies().size() == 2){
            long diff = holder.mBeehive.getBeeColonies().get(0).getCheckedTime() +
                    (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
            int deys1 = (int)diff /24 / 60 / 60 / 1000;
            holder.mCheckedTimeProgress1.setProgress(deys1);
            holder.mCheckedTimeProgress1.setVisibility(View.VISIBLE);
            long diff2 = holder.mBeehive.getBeeColonies().get(1).getCheckedTime() +
                    (11 * 24 * 60 * 60 * 1000) - Calendar.getInstance().getTime().getTime();
            int deys2 = (int) diff2 / 24 / 60 / 60 / 1000;
            holder.mCheckedTimeProgress2.setProgress(deys2);
            holder.mCheckedTimeProgress2.setVisibility(View.VISIBLE);
        }

    }

    private void onListItemSelect(int position, Beehive beehive, int mode) {
        toggleSelection(position);//Toggle the selection

        boolean hasCheckedItems = getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null) {
            // there are some selected items, start the actionMode
            mCommunicator = (Communicator) context;

            mActionMode = mCommunicator.setDataForTools(this, mBeehiveList, mBeehiveList.get(position), nameApiary, mode);
        }else if (!hasCheckedItems && mActionMode != null) {
            // there no selected items, finish the actionMode
//            mActionMode.finish();
        }

//        if (mActionMode == null){
//            Intent actionActivityIntent = new Intent(context, ActionActivity_.class);
//            actionActivityIntent.putExtra(USER_SELECTED_BEEHIVE, beehive);
//            actionActivityIntent.putExtra(NAME_APIARY, nameApiary);
//            context.startActivity(actionActivityIntent);
//            removeSelection();
//        }else if (mActionMode != null && !replaceMode) {
//            //set action mode title on item selection
//            mActionMode.setTitle(String.valueOf(getSelectedCount()) + " selected");
//        }else if (mActionMode != null ) {
//            mActionMode = mCommunicator.setDataForTools(this, mBeehiveList, mBeehiveList.get(position), nameApiary, 1);
//
//        }

    }
    public void moveBeehiveInFront(Beehive itemBeehive, String nameApiary2) {
        mCommunicator = (Communicator)context;
        mCommunicator.deleteBeehive(selectedBeehives, nameApiary, false);
        mCommunicator.moveBeehive(selectedBeehives, itemBeehive, nameApiary, nameApiary2,true);


    }
    public void moveBeehiveBehide(Beehive itemBeehive, String nameApiary2) {
        mCommunicator = (Communicator)context;
        mCommunicator.deleteBeehive(selectedBeehives, nameApiary, false);
        mCommunicator.moveBeehive(selectedBeehives, itemBeehive, nameApiary, nameApiary2,false);

    }

    public void moveBeehive() {
        mActionMode.getMenu().findItem(R.id.actionOkBeehive).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionDeleteBeehive).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionReplaceBeehive).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionSellecktAllBee).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionMoveBeehive).setVisible(false);
        SparseBooleanArray selected = getSelectedIds();
        selectedBeehives = new ArrayList<>();
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                selectedBeehives.add(mBeehiveList.get(selected.keyAt(i)));

            }
        }
        Toast.makeText(context, selected.size() + " item selected ", Toast.LENGTH_SHORT).show();//Show Toast

        mActionMode.setTitle("Move");
        mActionMode.setSubtitle("Sellect beehive");
        replaceMode = true;
    }
    public void replaceBeehives() {
        mActionMode.getMenu().findItem(R.id.actionOkBeehive).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionDeleteBeehive).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionReplaceBeehive).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionSellecktAllBee).setVisible(false);
        mActionMode.getMenu().findItem(R.id.actionMoveBeehive).setVisible(false);
        SparseBooleanArray selected = getSelectedIds();
        selectedBeehives = new ArrayList<>();
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                selectedBeehives.add(mBeehiveList.get(selected.keyAt(i)));

            }
        }
        Toast.makeText(context, selected.size() + " item selected ", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.setTitle("Replace");
        mActionMode.setSubtitle("Sellect beehive");
        replaceMode = true;
    }

    public void actionOk(Beehive itemBeehive, String nameApiary2){
                mCommunicator = (Communicator) context;
                mCommunicator.replaceBeehive(selectedBeehives, itemBeehive, nameApiary, nameApiary2);
                mActionMode.finish();
                setNullToActionMode();

    }
    public void deleteBeehives() {
        SparseBooleanArray selected = getSelectedIds();//Get selected ids
        List<Beehive>deletedPlace = new ArrayList<>();
        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                deletedPlace.add(mBeehiveList.get(selected.keyAt(i)));
                mBeehiveList.remove(selected.keyAt(i));

               setNullToActionMode();//notify adapter
            }
        }
        Toast.makeText(context, selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mCommunicator = (Communicator)context;
        mCommunicator.deleteBeehive(deletedPlace, nameApiary, false);
//        mActionMode.finish();//Finish action mode after use
        setNullToActionMode();
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();

    }
    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null) {
            mActionMode.finish();
        }
        mCommunicator = (Communicator)context;
        mCommunicator.dissmisActionMode();
        mActionMode = null;
        replaceMode = false;

    }
    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
            return mBeehiveList.size();
        }
    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }
    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;

    }
    public boolean ismMode(){
        if (mActionMode != null){
            return true;
        }else{
            return false;
        }
    }

}