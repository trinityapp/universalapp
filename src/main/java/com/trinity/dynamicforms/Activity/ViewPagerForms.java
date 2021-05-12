package com.trinity.dynamicforms.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.trinity.dynamicforms.Adapter.TabAdapter;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.Models.SaveChecklistModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Alerts;
import com.trinity.dynamicforms.Utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ViewPagerForms extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Map<String, CheckPointsModel> checkListModels;
    ArrayList<CheckPointsModel> checkList = new ArrayList<CheckPointsModel>();
    MenuDetailModel menuDetail;
    String[] groupChecklistId;
    TextView title;
    ImageButton backButton;
    String titleString;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_forms);
        title = findViewById(R.id.Head);
        backButton = findViewById(R.id.back);
        db = Database.getDatabase(ViewPagerForms.this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        menuDetail = (MenuDetailModel) intent.getSerializableExtra(Constant.SubCategoryKey);
        titleString = intent.getStringExtra("title");
        String locationId = intent.getStringExtra("locationId");
        String mappingid = intent.getStringExtra("mappingid");
        String distance = intent.getStringExtra("distance");
        String latlong = intent.getStringExtra("latlong");
        String assignId = intent.getStringExtra("assignId");
        String activityId = intent.getStringExtra("activityId");
        String uniqueId = intent.getStringExtra("uniqueId");
        String isDataSend = intent.getStringExtra("isDataSend");


        if(menuDetail.getValue() != null){
            //To be reviewed data
            checkListModels = setupCheckpoints(menuDetail.getValue());
        } else {
            //To be shown data
            List<CheckPointsModel> list = db.checkpointsDao().getAll();
            checkListModels = new HashMap<>();
            for (CheckPointsModel str : list) {
                checkListModels.put(str.getChkpId(), str);
            }
        }
        groupChecklistId = getGroupCheckIds(menuDetail.getChpId());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        if(titleString.equals("NC")) {
            adapter = new TabAdapter(ViewPagerForms.this, checkListModels, groupChecklistId, menuDetail, viewPager, locationId, mappingid,distance, latlong,assignId, activityId,uniqueId,isDataSend,true);
        } else {
            adapter = new TabAdapter(ViewPagerForms.this, checkListModels, groupChecklistId, menuDetail, viewPager,locationId, mappingid,distance, latlong,assignId, activityId,uniqueId,isDataSend,false);
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        int length = tabLayout.getTabCount();
        for (int i = 0; i < length; i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.setTabView(tabLayout,i));
        }

        title.setText(menuDetail.getCaption());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alerts.showBackAlert(ViewPagerForms.this,"Alert!!","All the data will be lost, are you sure you want to exit?");
            }
        });

    }

    String[] getGroupCheckIds(String checkPoints) {
        String[] checkpoints = checkPoints.split(":");
        return checkpoints;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            new AlertDialog.Builder(ViewPagerForms.this)
                    .setTitle("Alert!!")
                    .setMessage("All the data will be lost, are you sure you want to exit?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no,null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    Map<String, CheckPointsModel> setupCheckpoints(ArrayList<SaveChecklistModel> values){

        List<CheckPointsModel> list = db.checkpointsDao().getAll();
        Map<String, CheckPointsModel> temp  = new HashMap<>();
        for (CheckPointsModel str : list) {
            temp.put(str.getChkpId(), str);
        }

        Map<String, CheckPointsModel> checklists = new HashMap<>();
        for (SaveChecklistModel model: values) {
            if (temp.get(model.getChkpId()) != null) {
                CheckPointsModel data = temp.get(model.getChkpId());
                CheckPointsModel newData = new CheckPointsModel(model.getChkpId(), data.getDescription(), data.getValue(), data.getTypeId(), data.getMandatory(), data.getCorrect(),
                        data.getSize(), data.getScore(),data.getLanguage(), data.getActive(), data.getIs_Dept(), data.getLogic(), data.getDependent(), data.getDependents(), model.getValue(),
                        model.getEditable(),data.isSkipped());
                checklists.put(model.getChkpId(), newData);
                List<CheckPointsModel> dependents = new ArrayList<CheckPointsModel>();
                if (model.getDependents() != null) {
                    if (model.getDependents().size() > 0) {
                        for (SaveChecklistModel dependent : model.getDependents()) {
//                    Log.d("subchecklist", String.valueOf(checkList.get(counter).getDependents().get(j)));
                            if (temp.get(dependent.getChkpId()) != null) {
                                CheckPointsModel dependentData = temp.get(dependent.getChkpId());
                                CheckPointsModel newdependentData = new CheckPointsModel(dependent.getChkpId(), dependentData.getDescription(), dependentData.getValue(), dependentData.getTypeId(), dependentData.getMandatory(), dependentData.getCorrect(),
                                        dependentData.getSize(), dependentData.getScore(),dependentData.getLanguage(), dependentData.getActive(), dependentData.getIs_Dept(), dependentData.getLogic(), dependentData.getDependent(), dependentData.getDependents(), dependent.getValue(),
                                        dependent.getEditable(),dependentData.isSkipped());
                                dependents.add(newdependentData);
                            }
                        }
                    }
                }
                checklists.get(model.getChkpId()).setDependents(dependents);
            }
        }

        return checklists;
    }
}
