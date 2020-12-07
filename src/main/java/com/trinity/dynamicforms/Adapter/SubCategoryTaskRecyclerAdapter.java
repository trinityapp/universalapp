package com.trinity.dynamicforms.Adapter;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Alerts;
import com.trinity.dynamicforms.Utils.Util;


import java.util.ArrayList;


/**
 * Created by Office on 2/14/2018.
 */


public class SubCategoryTaskRecyclerAdapter extends RecyclerView.Adapter<SubCategoryTaskRecyclerAdapter.MyViewHolder> {
    public interface OnShareClickedListener {
        public void ShareClicked(MenuDetailModel menu, String locationId, String mappingId, String distance, String latong);
    }
    OnShareClickedListener mCallback;
    Context context;
    ArrayList<MenuDetailModel> menuList;
    private static final long CLICK_TIME_INTERVAL = 300;
    private long mLastClickTime = System.currentTimeMillis();
    Handler handler;
    public SubCategoryTaskRecyclerAdapter(Context context, ArrayList<MenuDetailModel> menuList, Handler handler) {
        this.context = context;
        this.menuList=menuList;
        this.handler = handler;

    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public SubCategoryTaskRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recycler, parent, false);
        // set the view's size, margins, paddings and layout parameters
        SubCategoryTaskRecyclerAdapter.MyViewHolder vh = new SubCategoryTaskRecyclerAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final SubCategoryTaskRecyclerAdapter.MyViewHolder holder, final int position) {
    final MenuDetailModel assignedModel=menuList.get(position);
    holder.taskName.setText(assignedModel.getCaption());
    try{
        Glide.with(context).load(assignedModel.getIcon()).into(holder.icon);
    }catch (Exception e){}

    holder.Layout_click.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            long now = System.currentTimeMillis();
            if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                return;
            }
            mLastClickTime = now;
            if (assignedModel.getGeoFence() != null) {
                String locationIds[] = assignedModel.getGeoCoordinate().split(",");
                Double geofence = 0.0;
                if(!assignedModel.getGeoFence().equals("") || !assignedModel.getGeoFence().equals("0")) {
                    geofence = Double.parseDouble(assignedModel.getGeoFence());
                }
                final Double finalGeofence = geofence;

                Util.getDistance(locationIds, handler, context, geofence, true, new Util.DistanceHandler() {
                    @Override
                    public void onCompletion(boolean isWithingGeofence, String locationId, String mappingId, String distance, String lat, String longi) {
                        if (isWithingGeofence) {
                            mCallback.ShareClicked(menuList.get(position), locationId, mappingId, distance, lat+","+longi);
                        } else {
                            Alerts.showSimpleAlert(context, "Error!","You are far from the required location. You need to be within the radius of " + finalGeofence);
//                            Toast.makeText(context, "You are far from the required location. You need to be within the radius of " + finalGeofence, Toast.LENGTH_LONG).show();
//                                            Util.activityCall("start", context, menuList.get(position).getMId(), locationId,mappingId,distance);
                        }
                    }
                });
            } else {
                Util.setCompletionHandler(new Handler(), 0, context, new Util.CompletionHandler() {
                    @Override
                    public void onCompletion(Location location, boolean canGetLatLong) {
                        if(location != null) {
                            mCallback.ShareClicked(menuList.get(position), "", "0", "", location.getLatitude() + "," + location.getLongitude());
                        } else {
                            mCallback.ShareClicked(menuList.get(position), "", "0", "", " , ");

                        }
                    }
                });
            }

        }
    });

    }


    @Override
    public int getItemCount() {
        if(menuList != null) {
            return menuList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        RelativeLayout Layout_click;
        ImageView icon;
        // ImageView img;

        public MyViewHolder(final View itemView) {
            super(itemView);
            // get the reference of item view's

            taskName =  itemView.findViewById(R.id.taskName);
            Layout_click=itemView.findViewById(R.id.Layout_click);
            icon=itemView.findViewById(R.id.icon);
        }
    }

}
