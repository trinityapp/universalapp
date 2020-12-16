package com.trinity.dynamicforms.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.trinity.dynamicforms.Activity.ViewPagerForms;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.DetailsActivity.ImageActivity;
import com.trinity.dynamicforms.DetailsActivity.URLActivity;
import com.trinity.dynamicforms.DetailsActivity.VideoLandscapeActivity;
import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Models.MenuDetailModel;
import com.trinity.dynamicforms.Models.SaveChecklistModel;
import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.R;
import com.trinity.dynamicforms.Utils.Alerts;
import com.trinity.dynamicforms.Utils.Constant;
import com.trinity.dynamicforms.Utils.DecimalDigitsInputFilter;
import com.trinity.dynamicforms.Utils.EditTextInputFilter;
import com.trinity.dynamicforms.Utils.SharedpreferenceUtility;
import com.trinity.dynamicforms.Utils.SignatureView;
import com.trinity.dynamicforms.Utils.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TabAdapter extends PagerAdapter {
    Map<String, CheckPointsModel> list;
    String[] groupIDs;
    MenuDetailModel menuDetail;
    ArrayList<CheckPointsModel> checkList = new ArrayList<CheckPointsModel>();
    int counter = 1;
    Context context;
    LinearLayout dynamicView;
    LayoutInflater mLayoutInflater;
    ScrollView scrollView;
    int score = 0;
    String lastOnFocusChkptID = "";
    View lastOnFocusView;
    List<ArrayList<CheckPointsModel>> subcheckList = new ArrayList<ArrayList<CheckPointsModel>>();
    HashMap<String, Object> saveMap = new HashMap<>();
    HashMap<String, ArrayList<CheckPointsModel>> checkListLevel = new HashMap<>();
    String editable = "";
    String message = "";
    String timestamp;
    String  CheckPointcount;
    String imageQuality;
    ImageView showCamView;
    String imagePath;
    boolean loadedFirstTime = false;
    HashMap<String,Object>showDataHash=new HashMap<>();
    HashMap<String,Object>barcodeHash=new HashMap<>();
    ArrayList<SaveChecklistModel> Savecheckpoint = new ArrayList<SaveChecklistModel>();
    ArrayList<SaveChecklistModel> finalheckpointList = new ArrayList<SaveChecklistModel>();
    String Heading;
    String M_Id;
    private int TAKE_PHOTO = 65532;
    private int SCAN = 65533;
    public static final int MEDIA_TYPE_VIDEO =2;
    public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST = 200;
    View itemView;
    TabLayout tablayout;
    ViewPager viewPager;
    Button submit;
    ArrayList<ArrayList<String>> ids = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> idsCopy = new ArrayList<ArrayList<String>>();
    private IntentIntegrator qrScan;
    String[] editableArray;
    HashMap<Integer, Boolean> filledPages = new HashMap<Integer, Boolean>();
    HashMap<String, String[]> checkedBoxes = new HashMap<String, String[]>();
    boolean isNC;
    String locationId = "";
    String mappingId = "0";
    String assignId = "";
    String activityId = "";
    String uniqueId = "";
    String[] isDataSendArray;
    Database db;
    public TabAdapter(Context context, Map<String, CheckPointsModel> list, String[] groupIDs, MenuDetailModel menuDetail, ViewPager viewPager, String locationId, String mappingId, String distance, String latlong, String assignId, String activityId,String uniqueId, String isDataSend, boolean isNC ) {
        this.list = list;
        this.groupIDs = groupIDs;
        this.menuDetail = menuDetail;
        this.context = context;
        this.viewPager = viewPager;
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if(!menuDetail.getEditable().isEmpty()) {
//            editableArray = menuDetail.getEditable().split(":");
//        } else {
            editableArray = new String[]{};
//        }
        timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());
        loadedFirstTime = true;
        Heading = menuDetail.getCaption();
        M_Id = menuDetail.getMId();
        qrScan = new IntentIntegrator((ViewPagerForms) context);
        this.isNC = isNC;
        this.locationId = locationId;
        this.mappingId = mappingId;
        this.assignId = assignId;
        this.activityId = activityId;
        this.uniqueId = uniqueId;
        if(isDataSend != null){
            this.isDataSendArray = isDataSend.split(":");
        }
//        Util.activityCall("open", context, M_Id,locationId,mappingId, distance, latlong,infieldV5Db);

        for(int i=0;i<groupIDs.length;i++){
            String[] checkpoints = groupIDs[i].split(",");
            ArrayList<String> newArray = new ArrayList<String> (Arrays.asList(checkpoints));
            ids.add(newArray);
        }
        if(menuDetail.getActive() != null) {
            if (menuDetail.getActive().equals("1") && loadedFirstTime) {
                for (int i = 0; i < ids.size(); i++) {

//                    if (!checkList.isEmpty()) {
                        Collections.shuffle(ids.get(i));
//                    }
                }
            }
        }
        for(int i=0;i<ids.size();i++)
        {
                ArrayList<String> newArray = new ArrayList<String> (ids.get(i));
                idsCopy.add(newArray);

        }
        for(int i=0; i<ids.size();i++){
            filledPages.put(i,false);
        }

        db = Database.getDatabase(context);

    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
//        return super.instantiateItem(container, position);
        itemView = mLayoutInflater.inflate(R.layout.view_pager_content, container, false);
        dynamicView = (LinearLayout) itemView.findViewById(R.id.dynamicView);
        scrollView = (ScrollView) itemView.findViewById(R.id.scrollView);
        scrollView.clearFocus();
        dynamicView.setTag(position);
        submit = (Button) itemView.findViewById(R.id.submit);
        checkList = getTypeIds(ids.get(position),"0");

        LinearLayout line = (LinearLayout) itemView.findViewById(R.id.lineView);
        line.setFocusable(true);
//        if (ids.size() == 1) {
//            submit.setText("Submit");
//        }
        for(int counter = 0;counter<checkList.size();counter++) {
            createView(checkList.get(counter), counter,null, position);
        }
        if(lastOnFocusView != null) {
            lastOnFocusView.getParent().requestChildFocus(lastOnFocusView, lastOnFocusView);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submit.getText().equals("Submit")) {
                    showSubmitAlert();
                } else {
                    if(isDataSendArray != null) {
                        if (!isDataSendArray[position].equals("0")) {
                            checkList = getTypeIds(ids.get(position), "0");
                            for (int i = 0; i < checkList.size(); i++) {
                                if (!validate(checkList.get(i))) {
                                    break;
                                }

                            }
                            if (message != "") {
                                Savecheckpoint.clear();
                                Alerts.showSimpleAlert(context, "Error!!", "Please fill data for " + message);
                                message = "";
                            } else {
                                for (int i = 0; i < Savecheckpoint.size(); i++) {
                                    finalheckpointList.add(new SaveChecklistModel(Savecheckpoint.get(i).getChkpId(), Savecheckpoint.get(i).getValue(), Savecheckpoint.get(i).getDependent()));
                                }
                                Savecheckpoint.clear();
                                filledPages.put(position, true);
                                setDoneTabView(position);
                                loadedFirstTime = true;
                                if (submit.getText().equals("Submit")) {
                                    showSubmitAlert();
                                } else if (position < ids.size() - 1) {
                                    viewPager.setCurrentItem(position + 1);
                                }
                                if (position == ids.size() - 1) {
                                    for(Map.Entry<Integer, Boolean> entry : filledPages.entrySet()){
                                        if(!entry.getValue()){
                                            message = String.valueOf(entry.getKey()+1);
                                        }
                                    }
                                    if(message != ""){
                                        Alerts.showSimpleAlert(context, "Error!!", "Please fill data for form number " + message);
                                        message = "";
                                    }
                                    submit.setText("Submit");

                                }
                            }
                        } else {
                            Savecheckpoint.clear();
                            filledPages.put(position, true);
                            setDoneTabView(position);
                            loadedFirstTime = true;
                            if (submit.getText().equals("Submit")) {
                                showSubmitAlert();
                            } else if (position < ids.size() - 1) {
                                viewPager.setCurrentItem(position + 1);
                            }
                            if (position == ids.size() - 1) {
                                submit.setText("Submit");
                            }
                        }
                    } else {
                        checkList = getTypeIds(ids.get(position), "0");
                        for (int i = 0; i < checkList.size(); i++) {
                            if (!validate(checkList.get(i))) {
                                break;
                            }

                        }
                        if (message != "") {
                            Savecheckpoint.clear();
                            Alerts.showSimpleAlert(context, "Error!!", "Please fill data for " + message);
                            message = "";
                        } else {
                            for (int i = 0; i < Savecheckpoint.size(); i++) {
                                finalheckpointList.add(new SaveChecklistModel(Savecheckpoint.get(i).getChkpId(), Savecheckpoint.get(i).getValue(), Savecheckpoint.get(i).getDependent()));
                            }
                            Savecheckpoint.clear();
                            filledPages.put(position, true);
                            setDoneTabView(position);
                            loadedFirstTime = true;
                            if (submit.getText().equals("Submit")) {
                                showSubmitAlert();
                            } else if (position < ids.size() - 1) {
                                viewPager.setCurrentItem(position + 1);
                            }
                            if (position == ids.size() - 1) {
                                for(Map.Entry<Integer, Boolean> entry : filledPages.entrySet()){
                                    if(!entry.getValue()){
                                        message = String.valueOf(entry.getKey()+1);
                                    }
                                }
                                if(message != ""){
                                    Alerts.showSimpleAlert(context, "Error!!", "Please fill data for form number " + message);
                                    message = "";
                                }
                                submit.setText("Submit");
                            }
                        }
                    }
                }
            }
        });

        Button prev = (Button)itemView.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0) {
                    viewPager.setCurrentItem(position - 1);
                }
                submit.setText("Next");
            }
        });

        container.addView(itemView);

        return itemView;
    }

    private void showSubmitAlert() {
        Alerts.showAlertWithCompletionHandler(context, "Submission", "Your score is " + score + ". Are you sure you want to submit?", new Alerts.CompletionHandler() {
            @Override
            public void onCompletion(boolean submit) {
                if (submit) {

                            for (int i = 0; i < Savecheckpoint.size(); i++) {
                                finalheckpointList.add(new SaveChecklistModel(Savecheckpoint.get(i).getChkpId(), Savecheckpoint.get(i).getValue(), Savecheckpoint.get(i).getDependent()));
                            }
                            Savecheckpoint.clear();

                            String locatid[] = {locationId};
//                            Util.getDistance(locatid, new Handler(), context, 0.0, false, new Util.DistanceHandler() {
//                                @Override
//                                public void onCompletion(boolean isWithingGeofence, String locationId, String mappingId, String distance, String lat, String longi) {
                                    for (int i = 0; i < finalheckpointList.size(); i++) {
                                        String key = finalheckpointList.get(i).getChkpId();
                                        String value = finalheckpointList.get(i).getValue();
                                        String dependent = finalheckpointList.get(i).getDependent();
                                        // do something with key and/or tab
                                        Log.v("Save Data", key + " " + value + " " + dependent);
                                        SaveDataModel saveDataModel = new SaveDataModel();
                                        saveDataModel.setValue(value);
                                        saveDataModel.setEmp_id(SharedpreferenceUtility.getInstance(context).getString(Constant.Empid));
                                        saveDataModel.setDid(SharedpreferenceUtility.getInstance(context).getString(Constant.Did));
                                        saveDataModel.setM_Id(M_Id);
                                        saveDataModel.setCheckpoint(key);
                                        saveDataModel.setTimeStamp(timestamp);
                                        saveDataModel.setCaption(Heading);
                                        saveDataModel.setDependent(dependent);
                                        saveDataModel.setLocationId(locationId);
                                        saveDataModel.setMappingId(mappingId);
//                                        saveDataModel.setDistance(distance);
//                                        saveDataModel.setLatlong(lat+","+longi);
                                        saveDataModel.setEvent("Submit");
                                        saveDataModel.setMobiledatetime(Util.calculateMobileTime());
                                        saveDataModel.setAssignId(assignId);
                                        saveDataModel.setActivityId(activityId);
                                        saveDataModel.setUniqueId(uniqueId);
                                        if (isNC) {
                                            db.saveDataDao().insertAll(saveDataModel);
                                        } else {
                                            db.saveDataDao().insertAll(saveDataModel);
                                        }

                                    }
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("submitted", "submitted");
                                    ((ViewPagerForms) context).setResult(Activity.RESULT_OK, returnIntent);
                                    ((ViewPagerForms) context).finish();
                                }
//                            });


//                        }
//                    }
//                }
            }
        });
    }


    ArrayList<CheckPointsModel> getTypeIds(ArrayList checkpoints, String dependent) {
        ArrayList<CheckPointsModel> checkList = new ArrayList<CheckPointsModel>();

        for (int i = 0; i < checkpoints.size(); i++) {
            if( list.get(checkpoints.get(i)) != null) {
                checkList.add(list.get(checkpoints.get(i)));
                if (list.get(checkpoints.get(i)).getDependents() != null) {
                    if (list.get(checkpoints.get(i)).getDependents().size() > 0) {
                        for (CheckPointsModel model : list.get(checkpoints.get(i)).getDependents()) {
                            checkList.add(model);
                        }
                    }
                }
            }
        }
        for (int i=0;i<checkList.size();i++){
            checkList.get(i).setDependent(dependent);
        }

        Log.d("CheckList", String.valueOf(checkList));
        return checkList;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position+1);
    }

    public View setTabView(TabLayout tabLayout, int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.custom_lottie_tab, tabLayout, false);
        this.tablayout = tabLayout;
        TextView title = (TextView) view.findViewById(R.id.stepLabel);
        LottieAnimationView icon = (LottieAnimationView) view.findViewById(R.id.circle);
        icon.playAnimation();
        title.setText(this.getPageTitle(position));
        return view;
    }

    public void setDoneTabView(int position) {
        View view = tablayout.getTabAt(position).getCustomView();
        LottieAnimationView icon = (LottieAnimationView) view.findViewById(R.id.circle);
        LottieAnimationView icon2 = (LottieAnimationView) view.findViewById(R.id.success);
        icon.setVisibility(View.GONE);
        icon2.setVisibility(View.VISIBLE);
        icon2.playAnimation();
    }

    public void createView(final CheckPointsModel qustcontModel, int position, final CheckPointsModel parentModel, final int tabposition) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.removeAllViews();
        if(qustcontModel.getEditable() != null) {
            editable = qustcontModel.getEditable();
        } else {
            editable = "";
        }
        if(qustcontModel.getTypeId().equals(Constant.Caption_code)){
            View dyview = LayoutInflater.from(context).inflate(R.layout.caption_layout, null);
            TextView captionText = (TextView) dyview.findViewById(R.id.caption);
            captionText.setText(qustcontModel.getDescription().trim());
            if(qustcontModel.getSize() != null) {
                if (qustcontModel.getSize().equals("0")) {
                    captionText.setGravity(Gravity.RIGHT);
                } else if (qustcontModel.getSize().equals("1")) {
                    captionText.setGravity(Gravity.CENTER);
                } else {
                    captionText.setGravity(Gravity.LEFT);
                }
            } else {
                captionText.setGravity(Gravity.CENTER);
            }
            ll.addView(dyview);
            counter = 1;
        } else {
            TextView captionText = new TextView(context);
            String description = qustcontModel.getDescription().trim();
            if(qustcontModel.getIs_Dept() != null) {
                if (qustcontModel.getIs_Dept().equals("2")) {
                    if (parentModel != null) {
                        String[] temp = qustcontModel.getDescription().split("_");
                        if (temp.length > 0) {
                            description = temp[0] + parentModel.getAnswer() + temp[1];
                        } else {
                            description = temp[0] + parentModel.getAnswer();
                        }
                    }
                }
            }

            captionText.setText((counter) + "." + " " + description);
            counter++;

            captionText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen._10sdp));
            captionText.setTextColor(Color.BLACK);
            captionText.setTypeface(null, Typeface.NORMAL);
            LinearLayout.LayoutParams paramsk = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsk.setMargins(10, 30, 10, 0);
            captionText.setLayoutParams(paramsk);
            ll.addView(captionText, paramsk);

        }
        if (qustcontModel.getTypeId().equals(Constant.text)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.edittext_layout, null);
            EditText editText = (EditText) dyview.findViewById(R.id.edittext);
            InputFilter[] inputfilters;
//            qustcontModel.setCorrect("[^+]");
            if(qustcontModel.getSize() != null && !qustcontModel.getSize().isEmpty()) {
                inputfilters = new InputFilter[2];
                inputfilters[0] = new InputFilter.LengthFilter(Integer.parseInt(qustcontModel.getSize()));
                inputfilters[1] = new EditTextInputFilter(qustcontModel.getCorrect() != null ? qustcontModel.getCorrect() : "");
            } else {
                inputfilters = new InputFilter[1];
                inputfilters[0] = new EditTextInputFilter(qustcontModel.getCorrect() != null ? qustcontModel.getCorrect() : "");
            }
            editText.setFilters(inputfilters);
            editText.setSingleLine(true);
            onFocusChanged(qustcontModel, editText);
            if(!qustcontModel.getAnswer().isEmpty()) {
                editText.setText(qustcontModel.getAnswer());
            }
            if(editable.equals("0")){
                editText.setEnabled(false);
            } else {
                if(position == 0){
                    editText.clearFocus();
                }
                editText.setEnabled(true);
            }
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(), editText);
        } else if (qustcontModel.getTypeId().equals(Constant.longtext)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.textview_layout, null);
            EditText editText = (EditText) dyview.findViewById(R.id.edittext);
            onFocusChanged(qustcontModel, editText);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.valueOf(qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ))});

            if(!qustcontModel.getAnswer().isEmpty()) {
                editText.setText(qustcontModel.getAnswer());
            }
            if(editable.equals("0")){
                editText.setEnabled(false);
            } else {
                if(position == 0){
                    editText.clearFocus();
                }
                editText.setEnabled(true);
            }
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(), editText);
        } else if (qustcontModel.getTypeId().equals(Constant.number)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.number_layout, null);
            EditText editText = (EditText) dyview.findViewById(R.id.edittext);
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            onFocusChanged(qustcontModel, editText);
            if(qustcontModel.getSize() != null) {
                String[] numDigits = qustcontModel.getSize().split(",");
                editText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Integer.parseInt(numDigits[0]), Integer.parseInt(numDigits[1]))});
            }
            if(!qustcontModel.getAnswer().isEmpty()) {
                editText.setText(qustcontModel.getAnswer());
            }
            if(editable.equals("0")){
                editText.setEnabled(false);
            } else {
                if(position == 0){
                    editText.clearFocus();;
                }
                editText.setEnabled(true);
            }
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(),editText);
        } else if (qustcontModel.getTypeId().equals(Constant.radio)) {

            if((qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ).equals("0")){
                final HashMap<Integer, CheckBox> checboxmap = new HashMap();
                String temp[] = qustcontModel.getValue().split(",");
                ArrayList<String> Option;
                if(loadedFirstTime){
                    Option = Util.shuffleOptionsGenerator(temp, qustcontModel.getActive());
                    qustcontModel.setValue(Util.combineByComma(Option));
                } else {
                    Option = Util.shuffleOptionsGenerator(temp, "0");
                }
                if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                    lastOnFocusView = ll;
                }
                Integer optionLength = Option.size();
                final String ans[] = qustcontModel.getAnswer().split(",");

                for (int i = 0; i < optionLength; i++) {
                    final AppCompatCheckBox checkBox = new AppCompatCheckBox(context);
                    checkBox.setText(Option.get(i));
                    checkBox.setTextColor(Color.BLACK);
                    checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen._9sdp));
                    checkBox.setPadding(10, 5, 0, 2);
                    checkBox.setId(i);
                    checboxmap.put(i, checkBox);
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{

                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {

                                    Color.rgb(123, 56, 182) //disabled
                                    ,Color.rgb(123, 56, 182) //enabled

                            }
                    );
                    checkBox.setSupportButtonTintList(colorStateList);
//                    if(ans.length>i) {
//                        if (ans[i].equals(Option.get(i))) {
//                            checkBox.setChecked(true);
//                        }
//                    }
                    putCheckedString(checkBox, ans);
                    if(editable.equals("0")){
                        checkBox.setEnabled(false);
                    } else {
                        checkBox.setEnabled(true);
                    }
                    final int checkid = i;
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                String temp[] = qustcontModel.getValue().split(",");
                                String checkedString = getCheckedString(qustcontModel,temp);
                                if(isChecked){
                                    if(checkListLevel.get(qustcontModel.getChkpId()) == null) {
    //
                                        setDependentViews(checkid, qustcontModel, checkedString, tabposition, true);
    //
                                    } else {
                                        qustcontModel.setAnswer(checkedString);
                                        counter = 1;
                                        loadedFirstTime = false;
                                        getNextView(tabposition);
                                    }
                                } else {
                                    if(ans.length == 1) {
                                        setDependentViews(checkid, qustcontModel,checkedString , tabposition, false);
                                    } else {
                                        qustcontModel.setAnswer(checkedString);
                                        counter = 1;
                                        loadedFirstTime = false;
                                        getNextView(tabposition);
                                    }
                                }
                                lastOnFocusChkptID = qustcontModel.getChkpId();
                            }
                        }
                    );
                    ll.addView(checkBox);
                }
//                setCheckedString(qustcontModel);
                saveMap.put(qustcontModel.getChkpId(), checboxmap);
            } else {
                final String temp[] = qustcontModel.getValue().split(",");
                ArrayList<String> Option;
                if(loadedFirstTime){
                    Option = Util.shuffleOptionsGenerator(temp, qustcontModel.getActive());
                    qustcontModel.setValue(Util.combineByComma(Option));
                } else {
                    Option = Util.shuffleOptionsGenerator(temp, "0");
                }
                Integer optionLength = Option.size();
                final RadioButton[] rb = new RadioButton[optionLength];
                final RadioGroup rg = new RadioGroup(context);
                rg.setOrientation(RadioGroup.VERTICAL);
                if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                    lastOnFocusView = ll;
                }
                for (int i = 0; i < Option.size(); i++) {
                    rb[i] = new RadioButton(context);
                    rb[i].setId(i);
                    rg.addView(rb[i]);
                    rb[i].setText(Option.get(i));
                    rb[i].setPadding(10, 5, 0, 5);
                    rb[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen._9sdp));
                    rb[i].setTextColor(Color.BLACK);
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{

                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {

                                    Color.rgb(123, 56, 182) //disabled
                                    ,Color.rgb(123, 56, 182) //enabled

                            }
                    );

                    rb[i].setButtonTintList(colorStateList);
                    if(qustcontModel.getAnswer().equals(Option.get(i))) {
                        rb[i].setChecked(true);
                    }
                    if(editable.equals("0")){
                        rb[i].setEnabled(false);
                    } else {
                        rb[i].setEnabled(true);
                    }
                }


                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                        if(!checkedRadioButton.isChecked()){
                            checkedRadioButton.setChecked(true);
                        }
                        lastOnFocusChkptID = qustcontModel.getChkpId();
                        setDependentViews(checkedId, qustcontModel, checkedRadioButton.getText().toString(), tabposition, true);
                    }
                });
                ll.addView(rg);
                saveMap.put(qustcontModel.getChkpId(), rg);
            }
        } else if (qustcontModel.getTypeId().equals(Constant.camera)) {
            final View dyview = LayoutInflater.from(context).inflate(R.layout.camera_layout, null);
            ImageView cam_icon = (ImageView) dyview.findViewById(R.id.cam_icon);
            final ImageView cam_img = (ImageView) dyview.findViewById(R.id.camImg);
            ImageView cam_icon2 = (ImageView) dyview.findViewById(R.id.cam_icon2);
            final ImageView cam_img2 = (ImageView) dyview.findViewById(R.id.camImg2);
            ImageView cam_icon3 = (ImageView) dyview.findViewById(R.id.cam_icon3);
            final ImageView cam_img3 = (ImageView) dyview.findViewById(R.id.camImg3);
            ImageView cam_icon4 = (ImageView) dyview.findViewById(R.id.cam_icon4);
            final ImageView cam_img4 = (ImageView) dyview.findViewById(R.id.camImg4);
            ImageView cam_icon5 = (ImageView) dyview.findViewById(R.id.cam_icon5);
            final ImageView cam_img5 = (ImageView) dyview.findViewById(R.id.camImg5);
            if(qustcontModel.getSize() != null) {
                if (!qustcontModel.getSize().equals("")) {
                    if (Integer.parseInt(qustcontModel.getSize()) < 5) {
                        cam_icon5.setVisibility(View.GONE);
                    }
                    if (Integer.parseInt(qustcontModel.getSize()) < 4) {
                        cam_icon4.setVisibility(View.GONE);
                    }
                    if (Integer.parseInt(qustcontModel.getSize()) < 3) {
                        cam_icon3.setVisibility(View.GONE);
                    }
                    if (Integer.parseInt(qustcontModel.getSize()) < 2) {
                        cam_icon2.setVisibility(View.GONE);
                    }
                }

            }
            if(!qustcontModel.getAnswer().isEmpty()){
                String urls[] = qustcontModel.getAnswer().split(",");
                for (int i=0;i<urls.length;i++) {
                    if(!urls[i].contains("http")) {
                        String temp = urls[i].substring(urls[i].lastIndexOf("_") + 1);
                        String num[] = temp.split("-");
                        switch (num[0]) {
                            case "1":
                                cam_img.setImageBitmap(getImageThumb(urls[i], qustcontModel.getValue()));
                                saveMap.put(qustcontModel.getChkpId() + "_1", qustcontModel.getAnswer());
                                break;
                            case "2":
                                cam_img2.setImageBitmap(getImageThumb(urls[i], qustcontModel.getValue()));
                                saveMap.put(qustcontModel.getChkpId() + "_2", qustcontModel.getAnswer());
                                break;
                            case "3":
                                cam_img3.setImageBitmap(getImageThumb(urls[i], qustcontModel.getValue()));
                                saveMap.put(qustcontModel.getChkpId() + "_3", qustcontModel.getAnswer());
                                break;
                            case "4":
                                cam_img4.setImageBitmap(getImageThumb(urls[i], qustcontModel.getValue()));
                                saveMap.put(qustcontModel.getChkpId() + "_4", qustcontModel.getAnswer());
                                break;
                            case "5":
                                cam_img5.setImageBitmap(getImageThumb(urls[i], qustcontModel.getValue()));
                                saveMap.put(qustcontModel.getChkpId() + "_5", qustcontModel.getAnswer());
                                break;
                        }
                    } else {
                        cam_icon.setVisibility(View.GONE);
                        cam_icon2.setVisibility(View.GONE);
                        cam_icon3.setVisibility(View.GONE);
                        cam_icon4.setVisibility(View.GONE);
                        cam_icon5.setVisibility(View.GONE);
                        switch (i) {
                            case 0:
                                setDownloadedImage(urls[i], cam_img);
                                break;
                            case 1:
                                setDownloadedImage(urls[i], cam_img2);
                                break;
                            case 2:
                                setDownloadedImage(urls[i], cam_img3);
                                break;
                            case 3:
                                setDownloadedImage(urls[i], cam_img4);
                                break;
                            case 4:
                                setDownloadedImage(urls[i], cam_img5);
                                break;
                        }
                    }
                }

            }
            if(editable.equals("0")){
                cam_icon5.setEnabled(false);
                cam_icon4.setEnabled(false);
                cam_icon3.setEnabled(false);
                cam_icon2.setEnabled(false);
                cam_icon.setEnabled(false);
            } else {
                cam_icon5.setEnabled(true);
                cam_icon4.setEnabled(true);
                cam_icon3.setEnabled(true);
                cam_icon2.setEnabled(true);
                cam_icon.setEnabled(true);

            }

            cam_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCamView=cam_img;
                    imagePath=Util.getCapturePathAsset(context,Heading,timestamp,qustcontModel.getChkpId()+"_1", qustcontModel.getDependent());
                    CheckPointcount=qustcontModel.getChkpId()+"_1";
                    if(qustcontModel.getValue() != null) {
                        imageQuality = qustcontModel.getValue();
                    } else {
                        imageQuality = "0";
                    }
                    lastOnFocusView = dyview;
                    clickImage();
                    if(qustcontModel.getAnswer().isEmpty()){
                        qustcontModel.setAnswer(imagePath);
                    } else {
                        qustcontModel.setAnswer(qustcontModel.getAnswer() + "," + imagePath);
                    }
                }
            });
            cam_icon2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCamView=cam_img2;
                    imagePath=Util.getCapturePathAsset(context,Heading,timestamp,qustcontModel.getChkpId()+"_2", qustcontModel.getDependent());
                    CheckPointcount=qustcontModel.getChkpId()+"_2";
                    if(qustcontModel.getValue() != null) {
                        imageQuality = qustcontModel.getValue();
                    } else {
                        imageQuality = "0";
                    }
                    lastOnFocusView = dyview;
                    clickImage();
                    if(qustcontModel.getAnswer().isEmpty()){
                        qustcontModel.setAnswer(imagePath);
                    } else {
                        qustcontModel.setAnswer(qustcontModel.getAnswer() + "," + imagePath);
                    }
                }
            });
            cam_icon3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCamView=cam_img3;
                    imagePath=Util.getCapturePathAsset(context,Heading,timestamp,qustcontModel.getChkpId()+"_3", qustcontModel.getDependent());
                    CheckPointcount=qustcontModel.getChkpId()+"_3";
                    if(qustcontModel.getValue() != null) {
                        imageQuality = qustcontModel.getValue();
                    } else {
                        imageQuality = "0";
                    }
                    lastOnFocusView = dyview;
                    clickImage();
                    if(qustcontModel.getAnswer().isEmpty()){
                        qustcontModel.setAnswer(imagePath);
                    } else {
                        qustcontModel.setAnswer(qustcontModel.getAnswer() + "," + imagePath);
                    }
                }
            });
            cam_icon4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCamView=cam_img4;
                    imagePath=Util.getCapturePathAsset(context,Heading,timestamp,qustcontModel.getChkpId()+"_4", qustcontModel.getDependent());
                    CheckPointcount=qustcontModel.getChkpId()+"_4";
                    if(qustcontModel.getValue() != null) {
                        imageQuality = qustcontModel.getValue();
                    } else {
                        imageQuality = "0";
                    }
                    lastOnFocusView = dyview;
                    if(qustcontModel.getAnswer().isEmpty()){
                        qustcontModel.setAnswer(imagePath);
                    } else {
                        qustcontModel.setAnswer(qustcontModel.getAnswer() + "," + imagePath);
                    }
                    clickImage();
                }
            });
            cam_icon5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCamView=cam_img5;
                    imagePath=Util.getCapturePathAsset(context,Heading,timestamp,qustcontModel.getChkpId()+"_5", qustcontModel.getDependent());
                    CheckPointcount=qustcontModel.getChkpId()+"_5";
                    if(qustcontModel.getValue() != null) {
                        imageQuality = qustcontModel.getValue();
                    } else {
                        imageQuality = "0";
                    }
                    lastOnFocusView = dyview;
                    clickImage();
                    if(qustcontModel.getAnswer().isEmpty()){
                        qustcontModel.setAnswer(imagePath);
                    } else {
                        qustcontModel.setAnswer(qustcontModel.getAnswer() + "," + imagePath);
                    }
                }
            });
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(),"");
        } else if (qustcontModel.getTypeId().equals(Constant.sign)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.signature_layout, null);
            final SignatureView signatureView = new SignatureView(context);
            if(editable.equals("0")){
                signatureView.isDrawEnabled(false);
            } else {
                signatureView.isDrawEnabled(true);
            }

            final RelativeLayout signaturelayout = (RelativeLayout) dyview.findViewById(R.id.signLin);
            signaturelayout.setVisibility(View.VISIBLE);
            final RelativeLayout signView = (RelativeLayout) dyview.findViewById(R.id.sinRlin);
            ImageView cross = (ImageView) dyview.findViewById(R.id.cross);
            View dyviewImage = null;
            cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if((showDataHash.get(qustcontModel.getChkpId())) != null) {
                        ((SignatureView) Objects.requireNonNull(showDataHash.get(qustcontModel.getChkpId()))).setBackgroundResource(R.drawable.edittext_border_layout);
                        ((SignatureView) Objects.requireNonNull(showDataHash.get(qustcontModel.getChkpId()))).clearSignature();
                        saveMap.remove(qustcontModel.getChkpId());
                        signatureView.removeSign(qustcontModel.getAnswer());
                        qustcontModel.setAnswer("");
                    }
                }
            });
            signatureView.setBackgroundResource(R.drawable.edittext_border_layout);
            signView.addView(signatureView,
            signatureView.getLayoutParams().width,
            signatureView.getLayoutParams().height);

            if(!qustcontModel.getAnswer().isEmpty()){
                if(qustcontModel.getAnswer().contains("http")){
                    dyviewImage = addImage(qustcontModel.getAnswer());
                } else {
                    try {
                        signatureView.add(qustcontModel.getAnswer());
                    }catch (Exception e){

                    }
                }
            }


            signatureView
                    .setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            imagePath=Util.getCapturePathAsset(context,Heading,timestamp,qustcontModel.getChkpId(), qustcontModel.getDependent());
                            showDataHash.put(qustcontModel.getChkpId(),signatureView);
                            createSignatureImage(signatureView,signaturelayout, imagePath);
                            saveMap.put(qustcontModel.getChkpId(), imagePath);
                            int action = event.getAction();
                            switch (action) {
                                case MotionEvent.ACTION_DOWN:
                                    v.getParent().requestDisallowInterceptTouchEvent(true);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    v.getParent().requestDisallowInterceptTouchEvent(false);
                                    break;
                            }
                            v.onTouchEvent(event);
                            qustcontModel.setAnswer(imagePath);
                            return true;
                        }
                    });
//             saveMap.put(qustcontModel.getChkpId(), imagePath);
            if(dyviewImage != null){
                ll.addView(dyviewImage);
            } else {
                ll.addView(dyview);
            }
        } else if (qustcontModel.getTypeId().equals(Constant.date)) {
            if(qustcontModel.getSize() != null) {
                if (qustcontModel.getSize().equals("0")) {
                    View dyview = LayoutInflater.from(context).inflate(R.layout.clock_layout, null);
                    ImageView clockImg = (ImageView) dyview.findViewById(R.id.clockImg);
                    final TextView clockText = (TextView) dyview.findViewById(R.id.clockText);

                    if(!qustcontModel.getAnswer().isEmpty()) {
                        clockText.setText(qustcontModel.getAnswer());
                    }
                    if(editable.equals("0")){
                        clockText.setEnabled(false);
                        clockImg.setEnabled(false);

                    } else {
                        clockText.setEnabled(true);
                        clockImg.setEnabled(true);
                    }
                    clockImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showTime(clockText, qustcontModel);
                        }
                    });
                    ll.addView(dyview);
                    saveMap.put(qustcontModel.getChkpId(), clockText);
                } else if(qustcontModel.getSize().equals("1")) {
                    View dyview = LayoutInflater.from(context).inflate(R.layout.date_layout, null);
                    ImageView calImg = (ImageView) dyview.findViewById(R.id.calImg);
                    final TextView calText = (TextView) dyview.findViewById(R.id.calText);

                    if(!qustcontModel.getAnswer().isEmpty()) {
                        calText.setText(qustcontModel.getAnswer());
                    }
                    if(editable.equals("0")){
                        calText.setEnabled(false);
                        calImg.setEnabled(false);

                    } else {
                        calText.setEnabled(true);
                        calImg.setEnabled(true);
                    }
                    calImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showCalender(calText, qustcontModel);
                        }
                    });
                    ll.addView(dyview);
                    saveMap.put(qustcontModel.getChkpId(), calText);
                } else {
                    View dyview = LayoutInflater.from(context).inflate(R.layout.timestamp_layout, null);
                    ImageView clockImg = (ImageView) dyview.findViewById(R.id.clockImg);
                    final TextView clockText = (TextView) dyview.findViewById(R.id.clockText);

                    if (!qustcontModel.getAnswer().isEmpty()) {
                        clockText.setText(qustcontModel.getAnswer());
                    }
                    if (editable.equals("0")) {
                        clockImg.setEnabled(false);
                        clockText.setEnabled(false);

                    } else {
                        clockImg.setEnabled(true);
                        clockText.setEnabled(true);
                    }
                    clockImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clockText.setText(Util.calculateDate());
                            qustcontModel.setAnswer(clockText.getText().toString());
                        }
                    });
                    ll.addView(dyview);
                    saveMap.put(qustcontModel.getChkpId(), clockText);
                }
            }
        } else if (qustcontModel.getTypeId().equals(Constant.rating)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.rating_layout, null);
            RatingBar rating = (RatingBar) dyview.findViewById(R.id.ratingBar);
            rating.setNumStars(Integer.valueOf(qustcontModel.getSize() != null ? qustcontModel.getSize() : "5" ));

            if(!qustcontModel.getAnswer().isEmpty()) {
                rating.setRating(Float.parseFloat(qustcontModel.getAnswer()));
            }
            if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                lastOnFocusView = ll;
            }
            if(editable.equals("0")){
                rating.setEnabled(false);
            } else {
                rating.setEnabled(true);
            }
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    float percent = rating / ratingBar.getNumStars() * 100;
                    float threshold = Float.valueOf(qustcontModel.getCorrect() != null ? qustcontModel.getCorrect() : "0");
                    lastOnFocusChkptID = qustcontModel.getChkpId();
                    if (percent < threshold) {
                        setDependentViews(0, qustcontModel, String.valueOf(rating), tabposition, true);
                    } else {
                        setDependentViews(1, qustcontModel, String.valueOf(rating), tabposition, true);
                    }

                }
            });
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(), rating);
        } else if (qustcontModel.getTypeId().equals(Constant.seekbarVertical)) {
            if (qustcontModel.getActive().equals("0")) {
                View dyview = LayoutInflater.from(context).inflate(R.layout.seek_veritical_layout, null);
                SeekBar seekBar = (SeekBar) dyview.findViewById(R.id.seekBar);
                final TextView textshw = (TextView) dyview.findViewById(R.id.textshw);
                seekBar.setMax(Integer.valueOf(qustcontModel.getSize() != null ? qustcontModel.getSize() : "100" ));
                if(!qustcontModel.getAnswer().isEmpty()){
                    textshw.setText(qustcontModel.getAnswer());
                    try {
                        seekBar.setProgress(Integer.valueOf(qustcontModel.getAnswer()));
                    } catch (Exception e){
                        seekBar.setProgress((Float.valueOf(qustcontModel.getAnswer()).intValue()));
                    }

                }
                if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                    lastOnFocusView = ll;
                }
                if(editable.equals("0")){
                    textshw.setEnabled(false);
                    seekBar.setEnabled(false);
                } else {
                    textshw.setEnabled(true);
                    seekBar.setEnabled(true);
                }
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        saveMap.put(qustcontModel.getChkpId(), progress);
                        textshw.setText(String.valueOf(progress));
                        float max = seekBar.getMax();
                        float percent = (progress/max) * 100;
                        float threshold = Float.valueOf((qustcontModel.getCorrect() != null && !qustcontModel.getCorrect().isEmpty()) ? qustcontModel.getCorrect() : "0");
                        lastOnFocusChkptID = qustcontModel.getChkpId();
                        if (percent < threshold) {
                            setDependentViews(0, qustcontModel, String.valueOf(progress), tabposition, true);
                        } else {
                            setDependentViews(1, qustcontModel, String.valueOf(progress), tabposition, true);
                        }

                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                ll.addView(dyview);
                saveMap.put(qustcontModel.getChkpId(), textshw);
            } else {
                View dyview = LayoutInflater.from(context).inflate(R.layout.hr_seekbar_layout, null);
                SeekBar seekBar = (SeekBar) dyview.findViewById(R.id.seekBar);
                final TextView textshw = (TextView) dyview.findViewById(R.id.textshw);

                seekBar.setMax(Integer.valueOf(qustcontModel.getSize() != null ? qustcontModel.getSize() : "100" ));

                if(!qustcontModel.getAnswer().isEmpty()){
                    textshw.setText(qustcontModel.getAnswer());
                    try {
                        seekBar.setProgress(Integer.valueOf(qustcontModel.getAnswer()));
                    } catch (Exception e){
                        seekBar.setProgress((Float.valueOf(qustcontModel.getAnswer()).intValue()));
                    }
                }
                if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                    lastOnFocusView = ll;
                }
                if(editable.equals("0")){
                    textshw.setEnabled(false);
                    seekBar.setEnabled(false);
                } else {
                    textshw.setEnabled(true);
                    seekBar.setEnabled(true);
                }
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        saveMap.put(qustcontModel.getChkpId(), progress);
                        textshw.setText(String.valueOf(progress));
                        float max = seekBar.getMax();
                        float percent = (progress/max) * 100;
                        float threshold = Float.parseFloat((qustcontModel.getCorrect() != null && !qustcontModel.getCorrect().isEmpty())? qustcontModel.getCorrect() : "0");
                        lastOnFocusChkptID = qustcontModel.getChkpId();
                        if (percent < threshold) {
                            setDependentViews(0, qustcontModel, String.valueOf(progress), tabposition, true);
                        } else {
                            setDependentViews(1, qustcontModel, String.valueOf(progress), tabposition, true);
                        }
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                ll.addView(dyview);
                saveMap.put(qustcontModel.getChkpId(), textshw);
            }

        }  else if (qustcontModel.getTypeId().equals(Constant.dropdown)) {
            if((qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ).equals("0")){
                View dyview;

                dyview = LayoutInflater.from(context).inflate(R.layout.normal_spinner_layout, null);
                final Spinner dyspinner = (Spinner) dyview.findViewById(R.id.dynspinner);
                String temp[] = qustcontModel.getValue().split(",");
                final ArrayList<String> Option;
                if(loadedFirstTime){
                    Option = Util.shuffleOptionsGenerator(temp, qustcontModel.getActive());
                    qustcontModel.setValue(Util.combineByComma(Option));
                } else {
                    Option = Util.shuffleOptionsGenerator(temp, "0");
                }
                final ArrayList<String> spinnerValue = new ArrayList<>();

//                if(editable.equals("1") || editable.equals("")) {
                    spinnerValue.add("Select");
//                }
                for (int i = 0; i < Option.size(); i++) {
                    spinnerValue.add(Option.get(i));
                }
                if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                    lastOnFocusView = ll;
                }
                if(editable.equals("0")){

                    dyspinner.setEnabled(false);
                } else {
                    dyspinner.setEnabled(true);
                }
                Boolean AudioRecord = false;
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, R.id.spintext, spinnerValue);
                dyspinner.setAdapter(spinnerArrayAdapter);
                AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> spinner, View container,
                                               int position, long id) {
                        Log.d("item ", String.valueOf(position));
                        if(position != 0) {
                            lastOnFocusChkptID = qustcontModel.getChkpId();
                            if(!qustcontModel.getAnswer().isEmpty()) {
                                Log.d("Position", String.valueOf(position));
                                Log.d("index", String.valueOf(getIndex(qustcontModel, Option)));
                                if (position != getIndex(qustcontModel, Option)) {
                                    setDependentViews(position-1, qustcontModel, spinner.getSelectedItem().toString(), tabposition, true);
                                }
                            } else {
                                setDependentViews(position-1, qustcontModel, spinner.getSelectedItem().toString(), tabposition, true);
                            }

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                };
                dyspinner.setOnItemSelectedListener(listener);
//                if(!qustcontModel.getAnswer().isEmpty()){
//                    int position = Integer.parseInt(qustcontModel.getAnswer());
//                    dyspinner.setSelection(position);
//                }
                if(!qustcontModel.getAnswer().isEmpty()){
                    int index = getIndex(qustcontModel, Option);
                    if(index != -1) {
                        try
                        {
                            dyspinner.setSelection(index);
                        } catch(Exception e){
                            Toast.makeText(context, "Smething wrong", Toast.LENGTH_SHORT);
                        }
                    }
                }
                ll.addView(dyview);
                saveMap.put(qustcontModel.getChkpId(),dyspinner);
            } else {
                View dyview = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null);
                final Spinner dyspinner = (Spinner) dyview.findViewById(R.id.dynspinner);
                String temp[] = qustcontModel.getValue().split(",");
                final ArrayList<String> Option;
                if(loadedFirstTime){
                    Option = Util.shuffleOptionsGenerator(temp, qustcontModel.getActive());
                    qustcontModel.setValue(Util.combineByComma(Option));
                } else {
                    Option = Util.shuffleOptionsGenerator(temp, "0");
                }
                final ArrayList<String> spinnerValue = new ArrayList<>();
//                if(editable.equals("1") || editable.equals("")){
                    spinnerValue.add("Select");
//                }
                for (int i = 0; i < Option.size(); i++) {
                    spinnerValue.add(Option.get(i));
                }
                if(lastOnFocusChkptID.equals(qustcontModel.getChkpId())){
                    lastOnFocusView = ll;
                }

                if(editable.equals("0")){
                    dyspinner.setEnabled(false);
                } else {
                    dyspinner.setEnabled(true);
                }
                Boolean AudioRecord = false;
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, R.id.spintext, spinnerValue);
                dyspinner.setAdapter(spinnerArrayAdapter);
                AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> spinner, View container,
                                               int position, long id) {
                        Log.d("item ", String.valueOf(position));
                        if(position != 0) {
                            lastOnFocusChkptID = qustcontModel.getChkpId();
                            if(!qustcontModel.getAnswer().isEmpty()) {
                                if (position != getIndex(qustcontModel, Option)) {
                                    setDependentViews(position-1, qustcontModel, spinner.getSelectedItem().toString(), tabposition, true);
                                }
                            } else {
                                setDependentViews(position-1, qustcontModel, spinner.getSelectedItem().toString(), tabposition, true);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                };

                dyspinner.setOnItemSelectedListener(listener);
//                if(!qustcontModel.getAnswer().isEmpty()){
//                    int position = Integer.parseInt(qustcontModel.getAnswer());
//                    dyspinner.setSelection(position);
//                }
                if(!qustcontModel.getAnswer().isEmpty()){
                    int index = getIndex(qustcontModel, Option);
                    if(index != -1) {
                        try
                        {
                            dyspinner.setSelection(index);
                        } catch(Exception e){
                            Toast.makeText(context, "Smething wrong", Toast.LENGTH_SHORT);
                        }
                    }
                }
                ll.addView(dyview);
                saveMap.put(qustcontModel.getChkpId(),dyspinner);
            }

        } else if (qustcontModel.getTypeId().equals(Constant.fingerPrint)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.fingerprint_layout, null);
            Button fcapture = (Button) dyview.findViewById(R.id.fcapture);
            fcapture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Currently fingerprint not supported",Toast.LENGTH_LONG);
                }
            });
            final ImageView imgFingerIn = (ImageView) dyview.findViewById(R.id.imgFinger);
            Button match = (Button) dyview.findViewById(R.id.match);
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(),"");
        } else if (qustcontModel.getTypeId().equals(Constant.video)) {
            final View dyview = LayoutInflater.from(context).inflate(R.layout.vedio_view, null);
            ImageView capVedio = (ImageView) dyview.findViewById(R.id.capVedio);
            final ImageView showVedio = (ImageView) dyview.findViewById(R.id.showVedio);
            ImageView cancel = (ImageView) dyview.findViewById(R.id.cancel);
            View dyviewVideo = null;
            if(!qustcontModel.getAnswer().isEmpty()){
                if(qustcontModel.getAnswer().contains("http")){
                    dyviewVideo = addVideo(qustcontModel.getAnswer());
                } else {
                    showVedio.setImageBitmap(getVideoBitmap(qustcontModel.getAnswer()));
                    saveMap.put(qustcontModel.getChkpId(),qustcontModel.getAnswer());
                }

            }
            if(editable.equals("0")){
                capVedio.setEnabled(false);
            } else {
                capVedio.setEnabled(true);
            }
            capVedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCamView=showVedio;
                    showDataHash.put(qustcontModel.getChkpId(),showCamView);
                    imagePath=Util.getCapturePathVedio(context,Heading,timestamp,qustcontModel.getChkpId(),qustcontModel.getDependent());
                    CheckPointcount=qustcontModel.getChkpId();
                    qustcontModel.setAnswer(imagePath);
                    lastOnFocusView = dyview;
                    startCaptureVedio(qustcontModel.getValue());
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ((ImageView) showDataHash.get(qustcontModel.getChkpId())).setImageResource(android.R.color.transparent);
                    }catch (Exception e){

                    }
                }
            });
            if(dyviewVideo != null){
                ll.addView(dyviewVideo);
            } else {
                ll.addView(dyview);
            }
//            saveMap.put(qustcontModel.getChkpId(),"");
        } else if (qustcontModel.getTypeId().equals(Constant.location)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            View dyview = LayoutInflater.from(context).inflate(R.layout.location_layout, null);
            final EditText editText = (EditText) dyview.findViewById(R.id.location);
            onFocusChanged(qustcontModel, editText);
            ImageView locpic = (ImageView) dyview.findViewById(R.id.locpic);
            ll.addView(dyview);
            if(!qustcontModel.getAnswer().isEmpty()) {
                editText.setText(qustcontModel.getAnswer());
            }
            if(editable.equals("0")){
                editText.setEnabled(false);
                locpic.setEnabled(false);
            } else {
                if(position == 0){
                    editText.clearFocus();;
                }
                editText.setEnabled(true);
                locpic.setEnabled(true);
            }
            locpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    locationText = editText;
                    dialog.show();
                    dialog.setMessage("Please wait..");
                    Util.setCompletionHandler(new Handler(), 0, context, new Util.CompletionHandler() {
                        @Override
                        public void onCompletion(Location location, boolean canGetLatLong) {
                            dialog.dismiss();
                            getCompleteAddressString(location.getLatitude(), location.getLongitude(), qustcontModel, editText);
                        }
                    });
                }
            });
            saveMap.put(qustcontModel.getChkpId(), editText);
        } else if (qustcontModel.getTypeId().equals(Constant.email)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.edittext_layout, null);
            EditText editText = (EditText) dyview.findViewById(R.id.edittext);
            onFocusChanged(qustcontModel, editText);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            if(!qustcontModel.getAnswer().isEmpty()) {
                editText.setText(qustcontModel.getAnswer());
            }
            if(editable.equals("0")){
                editText.setEnabled(false);
            } else {
                if(position == 0){
                    editText.clearFocus();;
                }
                editText.setEnabled(true);
            }
            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(), editText);
        } else if (qustcontModel.getTypeId().equals(Constant.QRCode)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.qr_layout, null);
            RadioGroup radioGroup = dyview.findViewById(R.id.radioGroupBarCode);
            RadioButton button1 = dyview.findViewById(R.id.scannable);
            RadioButton button2 = dyview.findViewById(R.id.not_scannable);
            final EditText qrCode_Edit =(EditText)dyview.findViewById(R.id.qrCode_Edit);
            onFocusChanged(qustcontModel,qrCode_Edit);
            if(!qustcontModel.getAnswer().isEmpty()) {
                qrCode_Edit.setText(qustcontModel.getAnswer());
            }
            if(editable.equals("0")){
                qrCode_Edit.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
            } else {
                qrCode_Edit.setEnabled(true);
                if(position == 0){
                    qrCode_Edit.clearFocus();;
                }
                radioGroup.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(true);
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // find which radio button is selected
                    if(checkedId == R.id.scannable) {
                        qrCode_Edit.setEnabled(false);
                        qrCode_Edit.setFocusable(false);
                        qrCode_Edit.setFocusableInTouchMode(false);
                        CheckPointcount=qustcontModel.getChkpId();
                        qrScan.setPrompt("Place a QR code inside the viewfinder rectangle to scan it.");
                        qrScan.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.QR_CODE_MODE);
                        qrScan.setRequestCode(SCAN).initiateScan();
                    } else if(checkedId == R.id.not_scannable) {
                        qrCode_Edit.setEnabled(true);
                        qrCode_Edit.setText("");
                        qrCode_Edit.setFocusable(true);
                        qrCode_Edit.setFocusableInTouchMode(true);

                    }
                }
            });
            ll.addView(dyview);
            barcodeHash.put(qustcontModel.getChkpId(),qrCode_Edit);
            saveMap.put(qustcontModel.getChkpId(),qrCode_Edit);
        } else if (qustcontModel.getTypeId().equals(Constant.URL)) {
            View dyview = LayoutInflater.from(context).inflate(R.layout.url_layout, null);
            final ImageButton urlButton = (ImageButton) dyview.findViewById(R.id.urlButton);
            Button url = (Button)dyview.findViewById(R.id.url);
            if(qustcontModel.getLogic() != null && !qustcontModel.getLogic().isEmpty()) {
                url.setVisibility(View.GONE);
                Glide.with(context)
                        .asBitmap()
                        .load(qustcontModel.getLogic())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                urlButton.setImageBitmap(resource);
                                String fileUrl = qustcontModel.getLogic().substring(qustcontModel.getLogic().lastIndexOf("/") + 1);
                                String pathname = Util.saveImage(resource, fileUrl);
//                        urls.add(position,pathname);
                                Log.d("Trial", pathname);
                            }
                        });

                urlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(qustcontModel.getCorrect() != null) {
                            if (qustcontModel.getCorrect().equals("0")) {
                                Uri uriUrl = Uri.parse(qustcontModel.getValue());
                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                (context).startActivity(launchBrowser);
                            } else {
                                Intent intent = new Intent(context, URLActivity.class);
                                intent.putExtra("url", qustcontModel.getValue());
                                (context).startActivity(intent);
                            }
                        }

                    }
                });
            } else {
                url.setText(qustcontModel.getValue() != null ? qustcontModel.getValue() : "");
                urlButton.setVisibility(View.GONE);
                url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(qustcontModel.getCorrect() != null) {
                            if (qustcontModel.getCorrect().equals("0")) {
                                Uri uriUrl = Uri.parse(qustcontModel.getValue());
                                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                (context).startActivity(launchBrowser);
                            } else {
                                Intent intent = new Intent(context, URLActivity.class);
                                intent.putExtra("url", qustcontModel.getValue());
                                (context).startActivity(intent);
                            }
                        }

                    }
                });
            }


            ll.addView(dyview);
            saveMap.put(qustcontModel.getChkpId(), urlButton);

        }  else if (qustcontModel.getTypeId().equals(Constant.VideoDisplay)) {
            View dyview = addVideo(qustcontModel.getValue());
            ll.addView(dyview);
//            saveMap.put(qustcontModel.getChkpId(),videoView);
        } else if (qustcontModel.getTypeId().equals(Constant.ImageDisplay)) {
            View dyview = addImage(qustcontModel.getValue());
            ll.addView(dyview);
//            saveMap.put(qustcontModel.getChkpId(),image);
        }

        dynamicView.addView(ll);
        addSubCheckpoint(qustcontModel, tabposition);

    }

    private View addVideo(final String url) {
        View dyview = LayoutInflater.from(context).inflate(R.layout.video_display_layout, null);
        ImageButton fullscreen = (ImageButton)dyview.findViewById(R.id.fullScreenButton);
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoLandscapeActivity.class);
                intent.putExtra("url",url);
                (context).startActivity(intent);
            }
        });
        return dyview;
    }

    private View addImage(String url) {
        View dyview = LayoutInflater.from(context).inflate(R.layout.image_display_layout, null);
        final ImageView image = (ImageView)dyview.findViewById(R.id.image_view);
        setDownloadedImage(url, image);
        return dyview;
    }

    private void setDownloadedImage(final String url, final ImageView image) {
        final Bitmap[] bmp = new Bitmap[1];
        Glide.with(context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        image.setImageBitmap(resource);
                        bmp[0] = resource;
                        String fileUrl = url.substring(url.lastIndexOf("/") + 1);
                        String pathname = Util.saveImage(resource, fileUrl);
                        Log.d("Trial", pathname);
                    }
                });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bmp[0] != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp[0].compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putExtra("picture", byteArray);
                    (context).startActivity(intent);
                }

            }
        });
    }

        private int getIndex(CheckPointsModel qustcontModel, ArrayList<String> option) {
        int index = -1;
        for (int i = 0; i< option.size(); i++){
            if(option.get(i).equals(qustcontModel.getAnswer())){
                index = i+1;
                break;
            }
        }
        return index;
    }

    public boolean validate(CheckPointsModel qustcontModel) {

        if (qustcontModel.getTypeId().equals(Constant.text)) {
            EditText editText = (EditText) saveMap.get(qustcontModel.getChkpId());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
            } else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }
        } else if (qustcontModel.getTypeId().equals(Constant.longtext)) {
            EditText editText = (EditText) saveMap.get(qustcontModel.getChkpId());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
            } else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }
        } else if (qustcontModel.getTypeId().equals(Constant.number)) {
            EditText editText = (EditText) saveMap.get(qustcontModel.getChkpId());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
            } else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }

        } else if (qustcontModel.getTypeId().equals(Constant.radio)) {
            if((qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ).equals("0")){
                String optionValue[] = qustcontModel.getValue().split(",");
                String checkboxString = getCheckedString(qustcontModel, optionValue);

                if (checkboxString.equals("")) {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                } else {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), checkboxString, qustcontModel.getDependent()));
                }
            } else {
                String optionValue[] = qustcontModel.getValue().split(",");
                if(saveMap.get(qustcontModel.getChkpId()) != null) {
                    RadioGroup radioGroup = (RadioGroup) saveMap.get(qustcontModel.getChkpId());
                    //

                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        if (mandatoryValidation(qustcontModel, qustcontModel.getDescription()))
                            return false;
                    } else {
                        int selectedId = (radioGroup.getCheckedRadioButtonId());
                        String selected = optionValue[selectedId];
                        Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), selected, qustcontModel.getDependent()));
                    }
                }
            }


        } else if (qustcontModel.getTypeId().equals(Constant.camera)) {
            int count = 0;
            if(saveMap.get(qustcontModel.getChkpId()+"_1")!=null&&!saveMap.get(qustcontModel.getChkpId()+"_1").equals("")){
//                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId()+"_1", (String) saveMap.get(qustcontModel.getChkpId())+"_1", qustcontModel.getDependent()));
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId())+"_1", qustcontModel.getDependent()));
                count++;
            }
            if(saveMap.get(qustcontModel.getChkpId()+"_2")!=null&&!saveMap.get(qustcontModel.getChkpId()+"_2").equals("")){
//                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId()+"_2", (String) saveMap.get(qustcontModel.getChkpId())+"_2", qustcontModel.getDependent()));
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId())+"_2", qustcontModel.getDependent()));
                count++;
            }
            if(saveMap.get(qustcontModel.getChkpId()+"_3")!=null&&!saveMap.get(qustcontModel.getChkpId()+"_3").equals("")){
//                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId()+"_3", (String) saveMap.get(qustcontModel.getChkpId())+"_3", qustcontModel.getDependent()));
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId())+"_3", qustcontModel.getDependent()));
                count++;
            }
            if(saveMap.get(qustcontModel.getChkpId()+"_4")!=null&&!saveMap.get(qustcontModel.getChkpId()+"_4").equals("")){
//                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId()+"_4", (String) saveMap.get(qustcontModel.getChkpId())+"_4", qustcontModel.getDependent()));
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId())+"_4", qustcontModel.getDependent()));
                count++;
            }
            if(saveMap.get(qustcontModel.getChkpId()+"_5")!=null&&!saveMap.get(qustcontModel.getChkpId()+"_5").equals("")){
//                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId()+"_5", (String) saveMap.get(qustcontModel.getChkpId())+"_5", qustcontModel.getDependent()));
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId())+"_5", qustcontModel.getDependent()));
                count++;
            }
            int threshold = Integer.valueOf((qustcontModel.getCorrect() != null && !qustcontModel.getCorrect().isEmpty()) ? qustcontModel.getCorrect() : "0");
            if(count<threshold) {
                if (mandatoryValidation(qustcontModel, qustcontModel.getChkpId())) return false;
            }
            //saveMap.put(qustcontModel.getChkpId(),editText);
        } else if (qustcontModel.getTypeId().equals(Constant.sign)) {
            Log.v("save", qustcontModel.getChkpId());

            if(saveMap.get(qustcontModel.getChkpId())!=null&&!saveMap.get(qustcontModel.getChkpId()).equals("")){
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId()), qustcontModel.getDependent()));
            }
            else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getChkpId())) return false;
            }
        } else if (qustcontModel.getTypeId().equals(Constant.date)) {
            if((qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ).equals("0")){
                TextView editText = (TextView) saveMap.get(qustcontModel.getChkpId());
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
                } else {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                }
            } else if((qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ).equals("1")){
                TextView editText = (TextView) saveMap.get(qustcontModel.getChkpId());
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
                } else {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                }
            } else {
                TextView editText = (TextView) saveMap.get(qustcontModel.getChkpId());
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
                } else {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                }
            }

        } else if (qustcontModel.getTypeId().equals(Constant.rating)) {
            RatingBar rate = (RatingBar) saveMap.get(qustcontModel.getChkpId());
            if (rate.getRating() != 0.0) {
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), String.valueOf(rate.getRating()), qustcontModel.getDependent()));
            } else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }

        } else if (qustcontModel.getTypeId().equals(Constant.seekbarVertical)) {
            TextView text = (TextView) saveMap.get(qustcontModel.getChkpId());
            if(qustcontModel.getActive().equals("0")){
                if (saveMap.get(qustcontModel.getChkpId()) != null) {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), text.getText().toString(), qustcontModel.getDependent()));
                } else {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                    //  checkPointAns.put(listModels.getCheckPointId(), "");
                }
            } else {
                if (saveMap.get(qustcontModel.getChkpId()) != null) {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), text.getText().toString(), qustcontModel.getDependent()));
                } else {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                    //  checkPointAns.put(listModels.getCheckPointId(), "");
                }
            }

        } else if (qustcontModel.getTypeId().equals(Constant.dropdown)) {
            if((qustcontModel.getSize() != null ? qustcontModel.getSize() : "0" ).equals("0")){
                String optionValue[] = qustcontModel.getValue().split(",");
                ArrayList<String> spinnerValue = new ArrayList<>();
                spinnerValue.add("Select");
                for(int j=0;j<optionValue.length;j++){
                    spinnerValue.add(optionValue[j]);
                }
                Spinner dynmicvspinner = (Spinner) saveMap.get(qustcontModel.getChkpId());
                int kl = dynmicvspinner.getSelectedItemPosition();


                if (optionValue.length > 0) {

                    if (!spinnerValue.get(kl).contains("Select")) {
                        Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), spinnerValue.get(kl), qustcontModel.getDependent()));
                    } else {
                        if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                    }
                }
                else{
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                }
            } else {
                String optionValue[] = qustcontModel.getValue().split(",");
                ArrayList<String> spinnerValue = new ArrayList<>();
                spinnerValue.add("Select");
                for(int j=0;j<optionValue.length;j++){
                    spinnerValue.add(optionValue[j]);
                }
                Spinner dynmicvspinner = (Spinner) saveMap.get(qustcontModel.getChkpId());
                int kl = dynmicvspinner.getSelectedItemPosition();


                if (optionValue.length > 0) {

                    if (!spinnerValue.get(kl).contains("Select")) {
                        Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), spinnerValue.get(kl), qustcontModel.getDependent()));
                    } else {
                        if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                    }
                }
                else{
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                }
            }

        } else if (qustcontModel.getTypeId().equals(Constant.fingerPrint)) {

        } else if (qustcontModel.getTypeId().equals(Constant.video)) {
            if(saveMap.get(qustcontModel.getChkpId())!=null&&!saveMap.get(qustcontModel.getChkpId()).equals("")){
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), (String) saveMap.get(qustcontModel.getChkpId()), qustcontModel.getDependent()));
            }
            else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getChkpId())) return false;
            }

        } else if (qustcontModel.getTypeId().equals(Constant.location)) {
            EditText editText = (EditText) saveMap.get(qustcontModel.getChkpId());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
            } else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }
        } else if (qustcontModel.getTypeId().equals(Constant.email)) {
            EditText editText = (EditText) saveMap.get(qustcontModel.getChkpId());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                Boolean isValid = Util.isValidEmail(editText.getText().toString());
                if(isValid) {
                    Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
                } else {
                    if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
                }
            } else {
                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }
        } else if (qustcontModel.getTypeId().equals(Constant.QRCode)) {
            EditText editText = (EditText) saveMap.get(qustcontModel.getChkpId());
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), editText.getText().toString(), qustcontModel.getDependent()));
            } else {

                if (mandatoryValidation(qustcontModel,qustcontModel.getDescription())) return false;
            }
        }
        validateSubCheckpoint(qustcontModel.getChkpId());
        return true;

    }

    private String getCheckedString(CheckPointsModel qustcontModel, String[] optionValue) {
        String checkboxString = "";
        HashMap<Integer, CheckBox> Checkboxlistinner = (HashMap<Integer, CheckBox>) saveMap.get(qustcontModel.getChkpId());
        if(Checkboxlistinner != null) {
            for (int j = 0; j < Checkboxlistinner.size(); j++) {
                CheckBox checkBoxmGetTheVlue = Checkboxlistinner.get(j);
                if (checkBoxmGetTheVlue.isChecked()) {
                    checkboxString = checkboxString + optionValue[j] + ",";

                }
            }
        }
        return checkboxString;
    }

    private void putCheckedString(CheckBox checkBox, String[] optionValue) {
            for (int j = 0; j < optionValue.length; j++) {
                if (checkBox.getText().toString().equals(optionValue[j])) {
                    checkBox.setChecked(true);

                }
            }
    }

    private String setCheckedString(CheckPointsModel qustcontModel) {
        String[] optionValue = qustcontModel.getAnswer().split(",");
        String checkboxString = "";
        HashMap<Integer, CheckBox> Checkboxlistinner = (HashMap<Integer, CheckBox>) saveMap.get(qustcontModel.getChkpId());
        if(Checkboxlistinner != null) {
            for (int j = 0; j < Checkboxlistinner.size(); j++) {
                CheckBox checkBoxmGetTheVlue = Checkboxlistinner.get(j);
                if(optionValue.length>j) {
                    if (checkBoxmGetTheVlue.getText().equals(optionValue[j])) {
                        checkBoxmGetTheVlue.setChecked(true);

                    }
                }
            }
        }
        return checkboxString;
    }

    private void onFocusChanged(final CheckPointsModel qustcontModel, final EditText editText) {  //for saving edittext values
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.clearFocus();
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!editText.getText().toString().isEmpty()) {
                        qustcontModel.setAnswer(editText.getText().toString());
                    }
                }
            }
        });
    }

    void addSubCheckpoint(CheckPointsModel checkptID, int tabposition){
        if(checkListLevel.get(checkptID.getChkpId()) != null){
            ArrayList<CheckPointsModel> temp = checkListLevel.get(checkptID.getChkpId());
            for(int i=0;i<temp.size();i++){
                if(!temp.get(i).isSkipped()) {
                    createView(temp.get(i), -1, checkptID, tabposition);
                }
            }
        }
    }
    void validateSubCheckpoint(String checkptID){
        if(checkListLevel.get(checkptID) != null){
            ArrayList<CheckPointsModel> temp = checkListLevel.get(checkptID);
            for(int i=0;i<temp.size();i++){
                validate(temp.get(i));
            }
        }
    }

    private void setDependentViews(int position, CheckPointsModel qustcontModel, String s, int tabposition, Boolean isChecked) { //isChecked is only for checkbox
        Log.d("Checked_id", String.valueOf(position));
        qustcontModel.setAnswer(s);
        subcheckList.clear();
        int[] scoreset = getPopUpData(qustcontModel);
        if (scoreset != null) {
            if(scoreset.length > position) {
                score += scoreset[position];
            }
        }
        if (subcheckList.size() > position) {
            Integer parentIndex = null;
            Integer index = null;

            for(int i=0;i< ids.get(tabposition).size();i++){
                for(int j=0;j<subcheckList.get(position).size();j++){
                    if(ids.get(tabposition).get(i).equals(subcheckList.get(position).get(j).getChkpId())){
                        index = i;
                        if(subcheckList.get(position).get(j).isSkipped()){
                            subcheckList.get(position).get(j).setSkipped(false);
                            index = null;
                            break;
                        } else {
                            subcheckList.get(position).get(j).setSkipped(true);
                        }
                    }
                }

                if(ids.get(tabposition).get(i).equals(qustcontModel.getChkpId())){
                    parentIndex = i;
                }
            }

            if (index != null && parentIndex != null) {
                if(parentIndex<index) {
                    for (int i = parentIndex + 1; i < index; i++) {
                        ids.get(tabposition).remove(parentIndex+1);
                    }
                }
            } else {
                ids = new ArrayList<>(idsCopy);
            }
            checkListLevel.put(qustcontModel.getChkpId(), subcheckList.get(position));
            counter = 1;
//            nextId--;
            loadedFirstTime = false;
            getNextView(tabposition);
        } else {
            if(checkListLevel.get(qustcontModel.getChkpId()) != null){
                checkListLevel.remove(qustcontModel.getChkpId());
                counter = 1;
//                nextId--;
                loadedFirstTime = false;
                getNextView(tabposition);
            }
        }
        if(!isChecked){
            if(checkListLevel.get(qustcontModel.getChkpId()) != null) {
                checkListLevel.remove(qustcontModel.getChkpId());
                counter = 1;
//                nextId--;
                loadedFirstTime = false;
                getNextView(tabposition);
            }
        }

    }
    private void getNextView(int tabposition) {
        dynamicView = viewPager.findViewWithTag(tabposition);
//        if(dynamicView.tag tabposition) {
            dynamicView.removeAllViews();
            dynamicView.clearFocus();
//        }
        if(loadedFirstTime) {
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fling(0);
                    scrollView.smoothScrollTo(0, 0);
                }
            });
        }
        checkList = getTypeIds(ids.get(tabposition), "0");
        for(int counter = 0;counter<checkList.size();counter++) {
            createView(checkList.get(counter), -1,null, tabposition);
        }
        if(lastOnFocusView != null) {
            lastOnFocusView.getParent().requestChildFocus(lastOnFocusView, lastOnFocusView);
        }

//        nextId++;
    }

    int[] getPopUpData(CheckPointsModel checkpoint){
        if (checkpoint.getLogic() != null) {
            String[] array = checkpoint.getLogic().split(":");
            Log.d("array", String.valueOf(array.length));
            for (int i = 0; i < array.length; i++) {
                if (!array[i].isEmpty()) {
                    String[] tempString = array[i].split(",");
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.addAll(Arrays.asList(tempString));
                    ArrayList<CheckPointsModel> temp = getTypeIds(arrayList, checkpoint.getChkpId());
                    if (temp != null) {
                        subcheckList.add(temp);
                    }
                }
            }
//        }
            Log.d("SubCheckList", String.valueOf(subcheckList));
            if(checkpoint.getScore() != null) {
                if (checkpoint.getScore().isEmpty()) {
                    Log.d("Score of options:", "no score");
                    return null;
                } else {
                    array = checkpoint.getScore().split(",");
                    int[] scoreset = new int[array.length];
                    for (int i = 0; i < array.length; i++) {
                        scoreset[i] = Integer.valueOf(array[i]);
                    }
                    Log.d("Score of options:", scoreset.toString());
                    return scoreset;
                }
            }
        }return null;
    }

    public void createSignatureImage(SignatureView signatureView,RelativeLayout Signlayout,String imagePath){
        signatureView.save(Signlayout,imagePath);

    }

    public Bitmap getImageThumb(String path, String quality) {
        Bitmap imgthumBitmap = null;
        try {

            final int THUMBNAIL_SIZE = 64;

            FileInputStream fis = new FileInputStream(path);
            imgthumBitmap = BitmapFactory.decodeStream(fis);

//            imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
//                    THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

//            ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream();
            int qualityAmount;
            switch (quality){
                case "1":
                    qualityAmount = 80;
                    break;
                case "2":
                    qualityAmount = 60;
                    break;
                case "3":
                    qualityAmount = 40;
                    break;
                case "4":
                    qualityAmount = 20;
                    break;
                case "5":
                    qualityAmount = 10;
                    break;
                default:
                    qualityAmount = 100;

            }

            FileOutputStream outputStream = new FileOutputStream(path);
            imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, qualityAmount,
                    outputStream);

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return imgthumBitmap;
    }
    public void clickImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = Uri.fromFile(new File(imagePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        ((ViewPagerForms)context).startActivityForResult(intent, TAKE_PHOTO);


    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE, CheckPointsModel type, EditText locationText) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                switch(type.getCorrect()){
                    case "1":
                        for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        }
                        break;
                    case "2":
                        strReturnedAddress.append(returnedAddress.getThoroughfare());
                        break;
                    case "3":
                        strReturnedAddress.append(returnedAddress.getSubLocality());
                        break;
                    case "4":
                        strReturnedAddress.append(returnedAddress.getLocality());
                        break;
                    case "5":
                        strReturnedAddress.append(returnedAddress.getAdminArea());
                        break;
                    case "6":
                        strReturnedAddress.append(returnedAddress.getPostalCode());
                        break;
                    case "7":
                        strReturnedAddress.append(returnedAddress.getCountryName());
                        break;
                }


                strAdd = strReturnedAddress.toString();
                locationText.setText(strReturnedAddress.toString());
                type.setAnswer(locationText.getText().toString());
                Log.w("My Current", strReturnedAddress.toString());
            } else {
                locationText.setText("");
                Log.w("My Current", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current", "Canont get Address!");
        }
        return strAdd;
    }

    //Tme
    public void showTime(final TextView value, final CheckPointsModel model) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);


        // Launch Time Picker Dialog

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM = " AM";
                        String mm_precede = "";
                        if (hourOfDay >= 12) {
                            AM_PM = " PM";
                            if (hourOfDay >= 13 && hourOfDay < 24) {
                                hourOfDay -= 12;
                            } else {
                                hourOfDay = 12;
                            }
                        } else if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }
                        if (minute < 10) {
                            mm_precede = "0";
                        }
                        value.setText(hourOfDay + ":" + minute + AM_PM);
                        model.setAnswer(value.getText().toString());
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    //Calender Data
    public void showCalender(final TextView value, final CheckPointsModel type) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        value.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                        type.setAnswer(value.getText().toString());

                    }
                }, mYear, mMonth, mDay);
        if(type.getCorrect().equals("1")){ //prev
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        if(type.getCorrect().equals("2")){ //forward
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
        datePickerDialog.show();
    }

    public void startCaptureVedio(String videoquality){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if(videoquality.equals("1")) {
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        }else {
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        ((ViewPagerForms)context).startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST);
    }
    private  Uri getOutputMediaFileUri(int type){

        return Uri.fromFile(getOutputMediaFile(type));
    }
    private  File getOutputMediaFile(int type){

        File mediaFile=new File(imagePath);

        return mediaFile;
    }

    private Bitmap getVideoBitmap(String imagePath) {
        Uri videoUri=Uri.parse(imagePath);

        Bitmap bmThumbnail;
        File file = new File(getRealPathFromURI(videoUri));

        bmThumbnail = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        return bmThumbnail;
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = (context).getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private boolean mandatoryValidation(CheckPointsModel qustcontModel, String description) {
        if (qustcontModel.getMandatory().equals(Constant.Mandatory)) {
            if (message == ""){
                message = qustcontModel.getDescription();
            } else {
                message = message + "\n " + qustcontModel.getDescription();
            }
            return true;
        } else {
            Savecheckpoint.add(new SaveChecklistModel(qustcontModel.getChkpId(), "", qustcontModel.getDependent()));

        }
        return false;
    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MyAdapter", "onActivityResult");
        if(lastOnFocusView != null) {
            lastOnFocusView.getParent().requestChildFocus(lastOnFocusView, lastOnFocusView);
        }
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            saveMap.put(CheckPointcount, imagePath);
            showCamView.setImageBitmap(getImageThumb(imagePath, imageQuality));
//            if(CheckPointcount != null) {
//                if (CheckPointcount.contains("-")) {
//                    String[] array = CheckPointcount.split("-");
//                    if (array.length > 1) {
//                        saveMap.put(array[0], imagePath);
//                        showCamView.setImageBitmap(getImageThumb(imagePath, array[1]));
//                    } else {
//                        saveMap.put(CheckPointcount, imagePath);
//                        showCamView.setImageBitmap(getImageThumb(imagePath, "0"));
//                    }
//                } else {
//
//                }
//            }
        }else if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST) {

            if (resultCode == RESULT_OK) {
                saveMap.put(CheckPointcount,imagePath);
                showCamView.setImageBitmap(getVideoBitmap(imagePath));
            } else if (resultCode == RESULT_CANCELED) {

                // User cancelled the video capture
                Toast.makeText(context, "User cancelled the video capture.",
                        Toast.LENGTH_LONG).show();

            } else {
                // Video capture failed, advise user
                Toast.makeText(context, "Video capture failed.",
                        Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == SCAN) {
            IntentResult result = IntentIntegrator.parseActivityResult( resultCode, data);
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(context, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                EditText qr_edit = ((EditText) barcodeHash.get(CheckPointcount));
                assert qr_edit != null;
                qr_edit.setText(result.getContents());
                qr_edit.setEnabled(false);
            }
        }
    }
}