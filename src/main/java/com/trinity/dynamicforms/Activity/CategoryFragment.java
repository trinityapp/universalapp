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
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.trinity.dynamicforms.Adapter.CategoryTaskRecyclerAdapter;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.Models.MenuModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Constant;
import com.trinity.dynamicforms.Utils.SharedpreferenceUtility;
import com.trinity.dynamicforms.Utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements SearchView.OnQueryTextListener {

    public interface onResponseEventListener {
        void onApiResponse(Boolean isSuccess, String tid);
    }

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
    String tid;
    String phoneNumber;
    CategoryTaskRecyclerAdapter onlycustomAdapter;
    onResponseEventListener eventListener;
    public static CategoryFragment newInstance(String baseUrl, String emp_id, String role_id,String did, String company, String phoneNumber,MenuModel menu, String tid) {
        CategoryFragment f = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.Base_url, baseUrl);
        bundle.putString(Constant.Empid, emp_id);
        bundle.putString(Constant.RoleId, role_id);
        bundle.putString(Constant.Did,did );
        bundle.putString(Constant.Tid,tid );
        bundle.putString(Constant.Company, company);
        bundle.putString(Constant.Mobile, phoneNumber);
        if(menu != null) {
            bundle.putSerializable(Constant.Menu, menu.getMenu());
        }
        f.setArguments(bundle);
        return f;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            eventListener = (onResponseEventListener) context;
        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Database.getDatabase(getContext());

//        pullToRefresh = getView().findViewById(R.id.pullToRefresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData(); // your code
//                pullToRefresh.setRefreshing(false);
//            }
//        });

        SearchView searchView = (SearchView) getView().findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        context= getActivity();
        Bundle intent = this.getArguments();
        base_url = intent.getString(Constant.Base_url);
        emp_id = intent.getString(Constant.Empid);
        role_id = intent.getString(Constant.RoleId);
        company = intent.getString(Constant.Company);
        phoneNumber = intent.getString(Constant.Mobile);
        did = intent.getString(Constant.Did);
        tid = intent.getString(Constant.Tid);
        ArrayList<MenuDetailModel> menu = (ArrayList<MenuDetailModel>) intent.getSerializable(Constant.Menu);

        viewModel = new CategoryViewModel(context, base_url, emp_id, role_id, tid,company,phoneNumber, db);
        SharedpreferenceUtility.getInstance(getContext()).putString(Constant.Empid, emp_id);
        SharedpreferenceUtility.getInstance(getContext()).putString(Constant.Did, did);
        SharedpreferenceUtility.getInstance(getContext()).putString(Constant.Company, company);
        SharedpreferenceUtility.getInstance(getContext()).putString(Constant.Mobile, phoneNumber);
        viewModel.getCheckList();
        viewModel.getLocations();
        if(menu != null) {
            SharedpreferenceUtility.getInstance(context).putArrayListMenuCategoryModel(tid, menu);
            loadMenuData();
        } else {
            viewModel.getMenuDetails(new CategoryViewModel.OnShareMenuClickedListener() {
                 @Override
                public void menuSaved(Boolean isSuccess) {
                     loadMenuData();
                }
            });
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // load data here

        }else{
            // fragment is no longer visible
        }
    }

    public void refreshData(){
        Log.d("Refresh","Refresh");
        viewModel.getCheckList();
        viewModel.getLocations();

//        viewModel.getMenuDetails(new CategoryViewModel.OnShareMenuClickedListener() {
//            @Override
//            public void menuSaved(Boolean isSuccess) {
                loadMenuData();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void tappedOnUpload(){
        viewModel.SendData();
        viewModel.sendImage();
    }


    public void loadMenuData(){
        final List<MenuDetailModel> menu = SharedpreferenceUtility.getInstance(context).getArrayListMenuCategoryModel(tid);
        if(menu!=null) {
            RecyclerView _onlytaskrecyclerview = (RecyclerView) getView().findViewById(R.id.recyclerView);
//            _onlytaskrecyclerview.setNestedScrollingEnabled(false);
            GridLayoutManager onlylinearLayoutManager = new GridLayoutManager(context, 1);
            _onlytaskrecyclerview.setLayoutManager(onlylinearLayoutManager);
            onlycustomAdapter = new CategoryTaskRecyclerAdapter(context, menu, menuHandler, base_url, emp_id, role_id);
            onlycustomAdapter.setOnShareClickedListener(new CategoryTaskRecyclerAdapter.OnShareClickedListener() {
                @Override
                public void ShareClicked(MenuDetailModel menu, String locationId, String mappingId, String distance, String assignId, String activityId,String uniqueId, String isDataSend) {
                    if (menu.getSubCategory() != null) { //this is to check to go to subcategory or checklist
                        if(menu.getSubCategory().isEmpty()) {
                            Intent myIntent = new Intent(context, ViewPagerForms.class);
                            myIntent.putExtra("title",menu.getCaption());
                            myIntent.putExtra("locationId", locationId);
                            myIntent.putExtra("mappingid", mappingId);
                            myIntent.putExtra("distance", distance);
                            myIntent.putExtra("assignId", assignId);
                            myIntent.putExtra("isDataSend", isDataSend);
                            myIntent.putExtra("activityId", activityId);
                            myIntent.putExtra("uniqueId", uniqueId);
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
                        myIntent.putExtra("assignId", assignId);
                        myIntent.putExtra("isDataSend", isDataSend);
                        myIntent.putExtra("activityId", activityId);
                        myIntent.putExtra("uniqueId", uniqueId);
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
//                    refreshData();
                    if(Util.isConnected(context)) {
                        tappedOnUpload();
                        if(eventListener != null) {
                            eventListener.onApiResponse(true, tid);
                        }
                    } else {
                        loadOfflineMenuData();
                    }

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                if(eventListener != null) {
                    eventListener.onApiResponse(false, tid);
                }
            }
        }
    }

    public void loadOfflineMenuData(){
        final List<MenuDetailModel> menu = SharedpreferenceUtility.getInstance(context).getArrayListMenuCategoryModel(tid);
        for (int j=0;j<menu.size();j++) {
            List<SaveDataModel> data = db.saveDataDao().getDataForUniqueId(menu.get(j).getUniqueId());
            if(data.size() > 0){
                    menu.remove(j);

            }
        }

        if(menu!=null) {
            RecyclerView _onlytaskrecyclerview = (RecyclerView) getView().findViewById(R.id.recyclerView);
//            _onlytaskrecyclerview.setNestedScrollingEnabled(false);
            GridLayoutManager onlylinearLayoutManager = new GridLayoutManager(context, 1);
            _onlytaskrecyclerview.setLayoutManager(onlylinearLayoutManager);
            onlycustomAdapter = new CategoryTaskRecyclerAdapter(context, menu, menuHandler, base_url, emp_id, role_id );
            onlycustomAdapter.setOnShareClickedListener(new CategoryTaskRecyclerAdapter.OnShareClickedListener() {
                @Override
                public void ShareClicked(MenuDetailModel menu, String locationId, String mappingId, String distance, String assignId, String activityId, String uniqueId, String isDataSend) {
                    if (menu.getSubCategory() != null) { //this is to check to go to subcategory or checklist
                        if(menu.getSubCategory().isEmpty()) {
                            Intent myIntent = new Intent(context, ViewPagerForms.class);
                            myIntent.putExtra("title",menu.getCaption());
                            myIntent.putExtra("locationId", locationId);
                            myIntent.putExtra("mappingid", mappingId);
                            myIntent.putExtra("distance", distance);
                            myIntent.putExtra("assignId", assignId);
                            myIntent.putExtra("isDataSend", isDataSend);
                            myIntent.putExtra("activityId", activityId);
                            myIntent.putExtra("uniqueId", uniqueId);
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
                        myIntent.putExtra("assignId", assignId);
                        myIntent.putExtra("isDataSend", isDataSend);
                        myIntent.putExtra("activityId", activityId);
                        myIntent.putExtra("uniqueId", uniqueId);
                        myIntent.putExtra(Constant.SubCategoryKey, menu);
                        startActivityForResult(myIntent, 1);
                    }
                }

            });
            _onlytaskrecyclerview.setAdapter(onlycustomAdapter);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        if(onlycustomAdapter != null) {
            onlycustomAdapter.filter(text);
        }
        return false;
    }
}
