package com.example.key.beekeepernote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.key.beekeepernote.database.Beehive;

import java.util.List;

/**
 * Created by Key on 09.07.2017.
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<Beehive> apiaries;


        /**
         * use context to intent Url
         */
        public Context context;




        public RecyclerAdapter(List<Beehive> apiaries) {
            this.apiaries = apiaries;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public ImageView imageBeehive;
            private  ClickListener mClickListener;
            public Beehive mApiary;

            public ViewHolder(View itemView, ClickListener listener) {
                super(itemView);
                mClickListener = listener;
                imageBeehive = (ImageView)itemView.findViewById(R.id.imageBeeHive);
                mApiary = null;
                itemView.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                mClickListener.onPressed(mApiary);
            }
            public interface ClickListener {
                void onPressed(Beehive namePlace);
            }
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_beehive, parent, false);
            context = mView.getContext();

            ViewHolder mHolder = new ViewHolder(mView, new ViewHolder.ClickListener() {
                @Override
                public void onPressed(Beehive placeName) {
                    if (placeName != null){

                    }
                }

            });

            return mHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

            holder.mApiary = apiaries.get(position);
        }

        @Override
        public int getItemCount() {
            return apiaries.size();
        }



    }