package com.example.key.beekeepernote.utils;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.adapters.RecyclerAdapter;
import com.example.key.beekeepernote.models.Beehive;

import java.util.List;

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;

/**
 * Created by key on 10.11.17.
 */

public class ToolbarActionModeCallback implements ActionMode.Callback {

    private Context context;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter recyclerAdapter2;
    private List<Beehive> beehiveList;
    private List<Beehive> beehiveList2;
    private String nameApiary;
    private String nameApiary2;
    private int mType;
    private Beehive itemBeehive;
    private Menu menu;
    private int actionChecker = 0;


    public ToolbarActionModeCallback(Context context, RecyclerAdapter recyclerAdapter, List<Beehive> placeArrayList, String nameApiary, int type) {
        this.context = context;
        this.recyclerAdapter = recyclerAdapter;
        this.beehiveList = placeArrayList;
        this.nameApiary = nameApiary;
        this.mType = type;
    }

    public void setData(RecyclerAdapter recyclerAdapter,  List<Beehive> placeArrayList , Beehive itemBeehive , String nameApiary, int type){
        if (recyclerAdapter != this.recyclerAdapter ) {
            this.recyclerAdapter2 = recyclerAdapter;
            this.itemBeehive = itemBeehive;
            this.beehiveList2 = placeArrayList;
            this.nameApiary2 = nameApiary;
            this.mType = type;
        }else {
            this.itemBeehive = itemBeehive;
            this.nameApiary2 = nameApiary;
            recyclerAdapter2 = null;
        }
        if(actionChecker == 1){
            showActionOk();
        }else if (actionChecker ==2){
            showActionSelectPlace();
        }

    }

    public void showActionOk() {
        menu.findItem(R.id.actionOkBeehive).setVisible(true);
        menu.findItem(R.id.actionDeleteBeehive).setVisible(false);
        menu.findItem(R.id.actionReplaceBeehive).setVisible(false);
        menu.findItem(R.id.actionSellecktAllBee).setVisible(false);
        menu.findItem(R.id.actionMoveBeehive).setVisible(false);
        menu.findItem(R.id.actionBehide).setVisible(false);
        menu.findItem(R.id.actionInFront).setVisible(false);
    }
    public void showActionSelectPlace() {
        menu.findItem(R.id.actionOkBeehive).setVisible(false);
        menu.findItem(R.id.actionDeleteBeehive).setVisible(false);
        menu.findItem(R.id.actionReplaceBeehive).setVisible(false);
        menu.findItem(R.id.actionSellecktAllBee).setVisible(false);
        menu.findItem(R.id.actionMoveBeehive).setVisible(false);
        menu.findItem(R.id.actionBehide).setVisible(true);
        menu.findItem(R.id.actionInFront).setVisible(true);
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.action_mode_for_beehives, menu);//Inflate the menu over action mode
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.actionOkBeehive), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.actionDeleteBeehive), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.actionReplaceBeehive), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.actionSellecktAllBee), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.actionMoveBeehive), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.actionOkBeehive).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.actionDeleteBeehive).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.actionReplaceBeehive).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.actionSellecktAllBee).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.actionMoveBeehive).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionOkBeehive:
                recyclerAdapter.actionOk(itemBeehive, nameApiary2);
                if (recyclerAdapter2 != null) {
                    recyclerAdapter2.setNullToActionMode();
                    actionChecker = 0;
                }
                break;
            case R.id.actionReplaceBeehive:
                recyclerAdapter.replaceBeehives();
                actionChecker = 1;
                break;
            case R.id.actionSellecktAllBee:
                recyclerAdapter.sellecktAll();
                break;
            case R.id.actionMoveBeehive:
                recyclerAdapter.moveBeehive();
                actionChecker = 2;
                break;
            case R.id.actionDeleteBeehive:
                recyclerAdapter.deleteBeehives();
                break;
            case R.id.actionBehide:
                recyclerAdapter.moveBeehiveBehide(itemBeehive, nameApiary2);

                break;
            case R.id.actionInFront:
                recyclerAdapter.moveBeehiveInFront(itemBeehive, nameApiary2);
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
            recyclerAdapter.removeSelection();  // remove selection
            recyclerAdapter.setNullToActionMode();//Set action mode null

        if (recyclerAdapter2 != null){
            recyclerAdapter2.removeSelection();  // remove selection
            recyclerAdapter2.setNullToActionMode();
        }
    }

}
