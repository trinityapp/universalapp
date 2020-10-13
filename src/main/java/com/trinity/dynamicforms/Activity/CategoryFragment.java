package com.trinity.dynamicforms.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.trinity.dynamicforms.Adapter.CategoryTaskRecyclerAdapter;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Constant;
import com.trinity.dynamicforms.Utils.SharedpreferenceUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment{
    Context context;
    SwipeRefreshLayout pullToRefresh;
    Handler menuHandler = new Handler();
    Database db;
    CategoryViewModel viewModel;
    String base_url;
    String emp_id;
    String did;
    String role_id;
    String company;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context= getActivity();
        db = Database.getDatabase(getContext());
        Bundle intent = this.getArguments();
        base_url = intent.getString(Constant.Base_url);
        emp_id = intent.getString(Constant.Empid);
        role_id = intent.getString(Constant.RoleId);
        company = intent.getString(Constant.Company);
        did = intent.getString(Constant.Did);
        viewModel = new CategoryViewModel(context, base_url, emp_id, role_id,company, db);
        SharedpreferenceUtility.getInstance(getContext()).putString(Constant.Empid, emp_id);
        SharedpreferenceUtility.getInstance(getContext()).putString(Constant.Did, did);
        viewModel.getCheckList();
        viewModel.getMenuDetails(new CategoryViewModel.OnShareMenuClickedListener() {
            @Override
            public void menuSaved(Boolean isSuccess) {
                loadMenuData();
            }
        });
        pullToRefresh = getView().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                isResumed = false;
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    public void refreshData(){
        Log.d("Refresh","Refresh");
//        if(!isResumed) {
        viewModel.getCheckList();
        viewModel.getMenuDetails(new CategoryViewModel.OnShareMenuClickedListener() {
            @Override
            public void menuSaved(Boolean isSuccess) {
                loadMenuData();
            }
        });
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tappedOnUpload();
    }

    public void tappedOnUpload(){
        viewModel.SendData();
        viewModel.sendImage();
    }


    public void loadMenuData(){
        final List<MenuDetailModel> menu = SharedpreferenceUtility.getInstance(context).getArrayListMenuCategoryModel("menu");
        if(menu!=null) {
            RecyclerView _onlytaskrecyclerview = (RecyclerView) getView().findViewById(R.id.recyclerView);
//            _onlytaskrecyclerview.setNestedScrollingEnabled(false);
            GridLayoutManager onlylinearLayoutManager = new GridLayoutManager(context, 1);
            _onlytaskrecyclerview.setLayoutManager(onlylinearLayoutManager);
            CategoryTaskRecyclerAdapter onlycustomAdapter = new CategoryTaskRecyclerAdapter(context, menu, menuHandler);
            onlycustomAdapter.setOnShareClickedListener(new CategoryTaskRecyclerAdapter.OnShareClickedListener() {
                @Override
                public void ShareClicked(MenuDetailModel menu, String locationId, String mappingId, String distance, String latlong) {
                    if (menu.getSubCategory() != null) { //this is to check to go to subcategory or checklist
                        if(menu.getSubCategory().isEmpty()) {
                            Intent myIntent = new Intent(context, ViewPagerForms.class);
                            myIntent.putExtra("title", menu.getCaption());
                            myIntent.putExtra("locationId", locationId);
                            myIntent.putExtra("mappingid", mappingId);
                            myIntent.putExtra("distance", distance);
                            myIntent.putExtra("latlong", latlong);
                            myIntent.putExtra(Constant.SubCategoryKey, menu);
                            startActivityForResult(myIntent, 1);
                        }
                        else {
                            Intent myIntent = new Intent(context, SubCategoryActivity.class);
                            myIntent.putExtra(Constant.SubCategoryTitle, menu.getCaption());
                            myIntent.putExtra(Constant.SubCategoryKey, menu);
                            startActivityForResult(myIntent, 1);
                        }

                    } else {
                        Intent myIntent = new Intent(context, ViewPagerForms.class);
                        myIntent.putExtra("title",menu.getCaption());
                        myIntent.putExtra("locationId", locationId);
                        myIntent.putExtra("mappingid", mappingId);
                        myIntent.putExtra("distance", distance);
                        myIntent.putExtra("latlong", latlong);
                        myIntent.putExtra(Constant.SubCategoryKey, menu);
                        startActivityForResult(myIntent, 1);
                    }
                }
            });
            _onlytaskrecyclerview.setAdapter(onlycustomAdapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("submitted");
                if(result.equals("submitted")){
                    refreshData();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
