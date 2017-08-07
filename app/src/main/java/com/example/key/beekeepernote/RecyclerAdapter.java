package com.example.key.beekeepernote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.key.beekeepernote.database.Beehive;

import java.util.List;

/**
 * Created by Key on 09.07.2017.
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final String USER_SELECTED_BEEHIVE = "user_selected_beehive" ;
    public static final String NAME_APIARY = "name_apiary" ;
    private List<Beehive> mBeehiveList;
        public String nameApiary;

        /**
         * use context to intent Url
         */
        public Context context;




        public RecyclerAdapter(List<Beehive> beehiveList, String nameApiary) {
            this.mBeehiveList = beehiveList;
            this.nameApiary = nameApiary;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public ImageView imageBeehive;
            private ClickListener mClickListener;
            private Beehive mBeehive;
            private TextView mBeehiveNumber;
            private TextView mCountBeecolony;

            public ViewHolder(View itemView, ClickListener listener) {
                super(itemView);
                mClickListener = listener;
                imageBeehive = (ImageView)itemView.findViewById(R.id.imageBeeHive);
                mBeehiveNumber = (TextView)itemView.findViewById(R.id.textNumberBeeColony);
                mCountBeecolony = (TextView)itemView.findViewById(R.id.textCountBeecolony);
                mBeehive = null;

                itemView.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                mClickListener.onPressed(mBeehive);
            }
            public interface ClickListener {
                void onPressed(Beehive beehive);
            }
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_beehive, parent, false);
            context = mView.getContext();

            ViewHolder mHolder = new ViewHolder(mView, new ViewHolder.ClickListener() {
                @Override
                public void onPressed(Beehive beehive) {
                    if (beehive != null){
                        Intent actionActivityIntent = new Intent(context, ActionActivity_.class);
                        actionActivityIntent.putExtra(USER_SELECTED_BEEHIVE, beehive);
                        actionActivityIntent.putExtra(NAME_APIARY, nameApiary);
                        context.startActivity(actionActivityIntent);
                    }
                }

            });

            return mHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            holder.mBeehive = mBeehiveList.get(position);
            holder.mBeehiveNumber.setText(String.valueOf(holder.mBeehive.getNameBeehive()));
            holder.mCountBeecolony.setText(String.valueOf(holder.mBeehive.getBeeColonies().size()));

            switch (holder.mBeehive.getTypeBeehive()){
                case 1: holder.imageBeehive.setImageResource(R.drawable.ic_beehive_one);
                    return;
                case 2: holder.imageBeehive.setImageResource(R.drawable.ic_beehive_left);
            }


        }

        @Override
        public int getItemCount() {
            return mBeehiveList.size();
        }



    }