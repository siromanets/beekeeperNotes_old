package com.example.key.beekeepernote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.models.Notifaction;

import java.util.List;

/**
 * Created by key on 04.11.17.
 */

public class NotifactionReciclerAdapter extends RecyclerView.Adapter<NotifactionReciclerAdapter.NotifactionViewHolder> {
private int mMode;
public Context context;
private List<Notifaction> notifactions;


public NotifactionReciclerAdapter(List<Notifaction> notifactions) {
        this.notifactions = notifactions;

        }
public void setList(List<Notifaction> notifactionList){
        this.notifactions = notifactionList;
        }

@Override
public NotifactionReciclerAdapter.NotifactionViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.lis_item_for_notifaction, parent, false);
        context = mView.getContext();

        NotifactionViewHolder mHolder = new NotifactionViewHolder(mView, new NotifactionViewHolder.ClickListener() {
@Override
public void onPressed(int position, Notifaction routObject) {
        if ( routObject != null) {

        }else{

        }
        }

@Override
public void onLongPressed(int position, Notifaction mRout, View view) {
        if (mMode > 1  ){

        /**
         CommunicatorStartActivity communicatorStartActivity = (CommunicatorStartActivity)context;
         communicatorStartActivity.deletedFromFavoriteList(notifaction.getNameRout(), ROUT);
         */
        }
        }

        });

        return mHolder;
        }

@Override
public void onBindViewHolder(NotifactionViewHolder holder, int position) {
    holder.mPosition = position;
    holder.notifaction = notifactions.get(position);
    holder.textNameNot.setText(holder.notifaction.getNameNotifaction());
    holder.textTitleNot.setText(holder.notifaction.getTextNotifaction());
    holder.textPathNot.setText(holder.notifaction.getPathNotifaction());
        }
@Override
public int getItemCount() {
        return notifactions.size();
        }



public static class NotifactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public static final String PUT_EXTRA_ROUT = "routName";
    private int mPosition;
    public TextView textNameNot;
    public TextView textTitleNot;
    public TextView textPathNot;
    private Notifaction notifaction;
    private ClickListener mClickListener;


    NotifactionViewHolder(View itemView, ClickListener listener) {
        super(itemView);
        textNameNot = (TextView)itemView.findViewById(R.id.textNameNot);
        textTitleNot = (TextView)itemView.findViewById(R.id.textTitleNote);
        textPathNot = (TextView)itemView.findViewById(R.id.textPathNot);
        mClickListener = listener;

        notifaction = null;
        mPosition = 0;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mClickListener.onPressed(mPosition, notifaction);
    }

    @Override
    public boolean onLongClick(View view) {
        mClickListener.onLongPressed(mPosition, notifaction, view);
        return true;
    }

    interface ClickListener {
        void onPressed(int position, Notifaction nameRout);

        void onLongPressed(int position, Notifaction mRout, View view);
    }
}


}