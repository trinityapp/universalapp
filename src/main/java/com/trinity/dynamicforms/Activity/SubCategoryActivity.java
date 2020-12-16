package com.trinity.dynamicforms.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trinity.dynamicforms.Adapter.SubCategoryTaskRecyclerAdapter;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Constant;

public class SubCategoryActivity extends AppCompatActivity implements SubCategoryTaskRecyclerAdapter.OnShareClickedListener {
    Context context;
    Boolean serviceStarted;
    ImageButton backButton;
    MenuDetailModel list;
    Handler handler;
    TextView subcategoryTitle;
    String uniqueId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        handler = new Handler();
        context= SubCategoryActivity.this;
//        SharedpreferenceUtility.getInstance(this).putBoolean("serviceStarted", false);
//        serviceStarted = SharedpreferenceUtility.getInstance(this).getBoolean("serviceStarted");

        backButton = (ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        subcategoryTitle = (TextView)findViewById(R.id.subcategoryTitle);
        getMenuDetails();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    public void getMenuDetails() {
        Intent i = getIntent();
        list = (MenuDetailModel) i.getSerializableExtra(Constant.SubCategoryKey);
        subcategoryTitle.setText(i.getStringExtra(Constant.SubCategoryTitle));

        if(list == null){
            list = new MenuDetailModel();
        }

        RecyclerView _onlytaskrecyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager onlylinearLayoutManager = new GridLayoutManager(SubCategoryActivity.this, 1);
        _onlytaskrecyclerview.setLayoutManager(onlylinearLayoutManager);
        SubCategoryTaskRecyclerAdapter onlycustomAdapter = new SubCategoryTaskRecyclerAdapter(SubCategoryActivity.this, list.getSubCategory(),handler );
        onlycustomAdapter.setOnShareClickedListener(this);
        _onlytaskrecyclerview.setAdapter(onlycustomAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("submitted");
                if(result.equals("submitted")){
                    finish();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("submitted", "submitted");
                    setResult(Activity.RESULT_OK, returnIntent);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void ShareClicked(MenuDetailModel menu, String locationId, String mappingId, String distance, String assignId, String activityId, String isDataSend) {
        if (menu.getSubCategory() != null){
            if(menu.getSubCategory().isEmpty()){
                Intent myIntent = new Intent(context, ViewPagerForms.class);
                myIntent.putExtra("title",menu.getCaption());
                myIntent.putExtra("locationId", locationId);
                myIntent.putExtra("mappingid", mappingId);
                myIntent.putExtra("distance", distance);
                myIntent.putExtra("assignId", assignId);
                myIntent.putExtra("isDataSend", isDataSend);
                myIntent.putExtra("activityId", activityId);
                myIntent.putExtra("uniqueId", uniqueId);
                startActivityForResult(myIntent, 1);
            } else {
                list = menu;
                subcategoryTitle.setText(menu.getCaption());
                RecyclerView _onlytaskrecyclerview = (RecyclerView) findViewById(R.id.recyclerView);
                GridLayoutManager onlylinearLayoutManager = new GridLayoutManager(SubCategoryActivity.this, 1);
                _onlytaskrecyclerview.setLayoutManager(onlylinearLayoutManager);
                SubCategoryTaskRecyclerAdapter onlycustomAdapter = new SubCategoryTaskRecyclerAdapter(SubCategoryActivity.this, list.getSubCategory(), handler);
                onlycustomAdapter.setOnShareClickedListener(this);
                _onlytaskrecyclerview.setAdapter(onlycustomAdapter);
            }
        }else{
            Intent myIntent = new Intent(context, ViewPagerForms.class);
            myIntent.putExtra(Constant.GroupChecklistKey, menu);
            myIntent.putExtra("title",menu.getCaption());
            myIntent.putExtra("locationId", locationId);
            myIntent.putExtra("mappingid", mappingId);
            myIntent.putExtra("distance", distance);
            myIntent.putExtra("assignId", assignId);
            myIntent.putExtra("isDataSend", isDataSend);
            myIntent.putExtra("activityId", activityId);
            myIntent.putExtra("uniqueId", uniqueId);
            startActivityForResult(myIntent, 1);
        }
    }
}
