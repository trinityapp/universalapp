package com.trinity.dynamicforms.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Alerts;
import com.trinity.dynamicforms.Utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Office on 2/14/2018.
 */

public class CategoryTaskRecyclerAdapter extends RecyclerView.Adapter<CategoryTaskRecyclerAdapter.MyViewHolder> {
    public interface OnShareClickedListener {
        public void ShareClicked(MenuDetailModel menu, String locationId, String mappingId, String distance, String assignId, String activityId,String uniqueId, String isDataSend);
    }
    OnShareClickedListener mCallback;
    Context context;
    List<MenuDetailModel> menuList;
    List<MenuDetailModel> menuListSearch;
    ArrayList<String> urls = new ArrayList<String>();
    private static final long CLICK_TIME_INTERVAL = 300;
    Handler handler;
    String locationId = "";
    String mappingId = "0";
    final String distance = "";
    private long mLastClickTime = System.currentTimeMillis();
    public CategoryTaskRecyclerAdapter(Context context, List<MenuDetailModel> menuList, Handler handler ) {
        this.context = context;
        this.menuList=menuList;
        this.menuListSearch = new ArrayList<MenuDetailModel>();
        this.menuListSearch.addAll(menuList);
        this.handler = handler;
    }

    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }
    @Override
    public CategoryTaskRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recycler, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CategoryTaskRecyclerAdapter.MyViewHolder vh = new CategoryTaskRecyclerAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final CategoryTaskRecyclerAdapter.MyViewHolder holder, final int position) {
        final MenuDetailModel assignedModel = menuListSearch.get(position);
        holder.taskName.setText(assignedModel.getCaption());
        Glide.with(context)
                .asBitmap()
                .load(assignedModel.getIcon())
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.icon.setImageBitmap(resource);
                        String fileUrl=assignedModel.getIcon().substring(assignedModel.getIcon().lastIndexOf("/")+1);
                        String pathname = Util.saveImage(resource, fileUrl);
//                        urls.add(position,pathname);
                        Log.d("Trial", pathname);
                    }

                });

            holder.Layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//            Log.d("assignedModel",assignedModel.getGeoFence());
                    if (!assignedModel.getChpId().isEmpty()) {
                        long now = System.currentTimeMillis();
                        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                            return;
                        }
                        mLastClickTime = now;
                        if (assignedModel.getGeoFence() != null) {
                            String locationIds[] = assignedModel.getGeoCoordinate().split(",");
                            Double geofence = 0.0;
                            if (!assignedModel.getGeoFence().equals("") || !assignedModel.getGeoFence().equals("0")) {
                                geofence = Double.parseDouble(assignedModel.getGeoFence());
                            }
                            final Double finalGeofence = geofence;
                            Util.getDistance(locationIds, handler, context, geofence, true, new Util.DistanceHandler() {
                                @Override
                                public void onCompletion(boolean isWithingGeofence, String locationId, String mappingId, String distance, String lat, String longi) {
                                    if (isWithingGeofence) {
                                        mCallback.ShareClicked(menuList.get(position), locationId, mappingId, distance, menuList.get(position).getAssignId(), menuList.get(position).getActivityId(),menuList.get(position).getUniqueId(), menuList.get(position).getIsDataSend());
                                    } else {
                                        Alerts.showSimpleAlert(context, "Error!", "You are far from the required location. You need to be within the radius of " + finalGeofence);
//                                    Toast.makeText(context, "You are far from the required location. You need to be within the radius of " + finalGeofence, Toast.LENGTH_LONG).show();
//                                            Util.activityCall("start", context, menuList.get(position).getMId(), locationId,mappingId,distance);
                                    }
                                }
                            });

                        } else {
                            Util.setCompletionHandler(new Handler(), 0, context, new Util.CompletionHandler() {
                                @Override
                                public void onCompletion(Location location, boolean canGetLatLong) {
                                    mCallback.ShareClicked(menuList.get(position), menuList.get(position).getLocationId(),"0","",menuList.get(position).getAssignId(), menuList.get(position).getActivityId(),menuList.get(position).getUniqueId(),menuList.get(position).getIsDataSend());
                                }
                            });

                        }

                    }
                }

            });

        }

        @Override
        public int getItemCount () {

            return menuListSearch.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView taskName;
            RelativeLayout Layout_click;
            ImageView icon;
            // ImageView img;

            public MyViewHolder(final View itemView) {
                super(itemView);
                // get the reference of item view's

                taskName = itemView.findViewById(R.id.taskName);
                Layout_click = itemView.findViewById(R.id.Layout_click);
                icon = itemView.findViewById(R.id.icon);
            }
        }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        menuListSearch.clear();
        if (charText.length() == 0) {
            menuListSearch.addAll(menuList);
        } else {
            for (MenuDetailModel wp : menuList) {
                if (wp.getCaption().toLowerCase(Locale.getDefault()).contains(charText)) {
                    menuListSearch.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
