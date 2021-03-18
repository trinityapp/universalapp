package com.trinity.dynamicforms.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Models.MappingModel;
import com.trinity.dynamicforms.Models.MenuDetailModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.regex.Pattern;

public class Util {

    public interface CompletionHandler {
        void onCompletion(Location location, boolean canGetLatLong);
    }
    public interface DistanceHandler {
        void onCompletion(boolean isWithingGeofence, String locationId, String mappingId, String distance, String lat, String longi);
    }
    public static String calculateDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    public static String calculateMobileTime(){
        long timestamp = Calendar.getInstance().getTimeInMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        String datetime = DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()).toString();
        return datetime;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getOs(){
        String deviceOs = String.valueOf(Build.VERSION.RELEASE);
        return deviceOs;
    }

    public static String getVersion(Context context){
        String version="";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
            Log.v("Versio",version+context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

//    public static void showInformationdialog(final Context ctx, final String msg) {
//
//        ((Activity) ctx).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                final Dialog dialog = new Dialog(ctx);
//                //dialog.setTitle("Select Site ID for Incident Reporting");
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.alertdialog);
//                // set values for custom dialog components - text, image and button
//                TextView alerttext=(TextView)dialog.findViewById(R.id.alerttext);
//                alerttext.setText(msg);
//
//                Button cancel=(Button)dialog.findViewById(R.id.alertcancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//
//
//                    }
//                });
//                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT );
//
//                dialog.show();
//            }
//        });
//    }
//
//    public static boolean isValidEmail(String email) {
//
//        String emailExp = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
//        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//                "\\@" +
//                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//                "(" +
//                "\\." +
//                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
//                ")+"
//        );
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

   public static String getCapturePathAsset(Context ctx,String subtaskId,String timestamp,String AssetID, String dependent){
        try {
            String employeeID = SharedpreferenceUtility.getInstance(ctx).getString(Constant.Mobile);
            String compName=SharedpreferenceUtility.getInstance(ctx).getString(Constant.Company);
            String tempDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + "/" +compName + "/" + employeeID+ "/"+ subtaskId+"/"+timestamp
                    ;
            File tempdir = new File(tempDir);
            if (!tempdir.exists())
                tempdir.mkdirs();

            return tempDir+ "/"+ AssetID+"-"+dependent +".jpg";
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(
                    ctx,
                    "Could not initiate File System.. Is Sdcard mounted properly?",
                    Toast.LENGTH_LONG).show();
            return "";
        }
    }

    public static String getCapturePathVedio(Context ctx,String subtaskId,String timestamp, String AssetID, String dependent){
        try {
            //String employeeID = SharedpreferenceUtility.getInstance(ctx).getString(Constant.EmpId);
                String employeeID = SharedpreferenceUtility.getInstance(ctx).getString(Constant.Mobile);
                String compName=SharedpreferenceUtility.getInstance(ctx).getString(Constant.Company);
                String tempDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/" +compName + "/" + employeeID+"/"+ subtaskId +"/"+timestamp
                        ;
                File tempdir = new File(tempDir);
                if (!tempdir.exists())
                    tempdir.mkdirs();

            return tempDir+ "/"+ AssetID +"-"+dependent +".mp4";
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(
                    ctx,
                    "Could not initiate File System.. Is Sdcard mounted properly?",
                    Toast.LENGTH_LONG).show();
            return "";
        }
    }

    public static Bitmap getImageFromUrl(String url){
        File imgFile = new  File(url);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;

        }
        return null;
    }

//    public static String getNetworkType(Context context) {
//        TelephonyManager mTelephonyManager = (TelephonyManager)
//                context.getSystemService(Context.TELEPHONY_SERVICE);
//        int networkType = mTelephonyManager.getNetworkType();
//        switch (networkType) {
//            case TelephonyManager.NETWORK_TYPE_GPRS:
//            case TelephonyManager.NETWORK_TYPE_EDGE:
//            case TelephonyManager.NETWORK_TYPE_CDMA:
//            case TelephonyManager.NETWORK_TYPE_1xRTT:
//            case TelephonyManager.NETWORK_TYPE_IDEN:
//                return "2g";
//            case TelephonyManager.NETWORK_TYPE_UMTS:
//            case TelephonyManager.NETWORK_TYPE_EVDO_0:
//            case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                /**
//                 From this link https://en.wikipedia.org/wiki/Evolution-Data_Optimized ..NETWORK_TYPE_EVDO_0 & NETWORK_TYPE_EVDO_A
//                 EV-DO is an evolution of the CDMA2000 (IS-2000) standard that supports high data rates.
//
//                 Where CDMA2000 https://en.wikipedia.org/wiki/CDMA2000 .CDMA2000 is a family of 3G[1] mobile technology standards for sending voice,
//                 data, and signaling data between mobile phones and cell sites.
//                 */
//            case TelephonyManager.NETWORK_TYPE_HSDPA:
//            case TelephonyManager.NETWORK_TYPE_HSUPA:
//            case TelephonyManager.NETWORK_TYPE_HSPA:
//            case TelephonyManager.NETWORK_TYPE_EVDO_B:
//            case TelephonyManager.NETWORK_TYPE_EHRPD:
//            case TelephonyManager.NETWORK_TYPE_HSPAP:
//                //Log.d("Type", "3g");
//                //For 3g HSDPA , HSPAP(HSPA+) are main  networktype which are under 3g Network
//                //But from other constants also it will 3g like HSPA,HSDPA etc which are in 3g case.
//                //Some cases are added after  testing(real) in device with 3g enable data
//                //and speed also matters to decide 3g network type
//                //https://en.wikipedia.org/wiki/4G#Data_rate_comparison
//                return "3g";
//            case TelephonyManager.NETWORK_TYPE_LTE:
//                //No specification for the 4g but from wiki
//                //I found(LTE (Long-Term Evolution, commonly marketed as 4G LTE))
//                //https://en.wikipedia.org/wiki/LTE_(telecommunication)
//                return "4g";
//            default:
//                return "Notfound";
//        }
//    }
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @param //type
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @param //type
     * @return
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static String getNetworkType(Context context) {
        TelephonyManager mTelephony = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        int netType=-1;
        try {
            netType = info.getType();
        }catch (Exception e){
            return "No internet";
        }
        if (netType == ConnectivityManager.TYPE_WIFI) {
            return info.getTypeName();
        } else if (netType == ConnectivityManager.TYPE_MOBILE){
            return info.getSubtypeName();
        }else if(info.isRoaming()) {
            return "Roaming";
        } else{
            return "";
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String saveImage( Bitmap bitmap, String setfileName) {
//        BitmapDrawable draw = (BitmapDrawable) image.;
//        if(draw!=null) {
//            Bitmap bitmap = draw.getBitmap();

            FileOutputStream outStream = null;
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/Downloaded");
            dir.mkdirs();
            String fileName = String.format(setfileName);
            File outFile = new File(dir, fileName);
            String filelocation = outFile.getAbsolutePath();
            try {
                outStream = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filelocation;
//        }
//        return "";

    }

    public static boolean hasSpace(String text) {
        String string = "\\s+";
        return Pattern.compile(string).matcher(text).matches();
    }

    public static String getGPSInfo(Context context){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return "GPS OFF";
        } else {
            return "GPS ON";
        }
    }

    public static void setCompletionHandler(Handler handler,int locCount, Context context,CompletionHandler events) {
//        events.onCompletion("test");
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        checkDist(handler,events, context, locCount, mProgressDialog);
    }

    public static void setCompletionHandlerOnlyGPS(Handler handler,int locCount, Context context,CompletionHandler events) {
//        events.onCompletion("test");
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        checkDistOnlyGps(handler,events, context, locCount, mProgressDialog);
    }

    public static void checkDist(Handler handler, CompletionHandler events, Context context, int locCount, ProgressDialog progressDialog) {
        if (Util.getGPSInfo(context).equals("GPS ON")) {
            locCount++;
            GpsLatLong(handler,events,context,locCount,progressDialog);
            if (locCount > 10 && locCount < 20) {
                NetLatLong(handler, events,context,locCount,progressDialog);
            } else {
                progressDialog.dismiss();
                if (CheckGpsStatus(context) == false) {
                    handler.removeCallbacksAndMessages(null);
                    Toast.makeText(context, "Your gps is Off, Please switch on Gps", Toast.LENGTH_LONG).show();
                    events.onCompletion(null, false);
                }
            }
        } else {
            locCount++;
            NetLatLong(handler, events,context,locCount,progressDialog);
            if (locCount > 10) {
                progressDialog.dismiss();
                handler.removeCallbacksAndMessages(null);
                Toast.makeText(context, "Your gps is Off, Please switch on Gps", Toast.LENGTH_LONG).show();
                events.onCompletion(null, false);

            }
        }
    }

    public static void checkDistOnlyGps(Handler handler, CompletionHandler events, Context context, int locCount, ProgressDialog progressDialog) {
        if (Util.getGPSInfo(context).equals("GPS ON")) {
            locCount++;
            GpsLatLong(handler,events,context,locCount,progressDialog);
//            if (locCount > 10 && locCount < 20) {
//                NetLatLong(handler, events,context,locCount,progressDialog);
//            } else {
            if(locCount>10){
                if (CheckGpsStatus(context) == false) {
                    progressDialog.dismiss();
                    handler.removeCallbacksAndMessages(null);
                    Toast.makeText(context, "Your gps is Off, Please switch on Gps", Toast.LENGTH_LONG).show();
                    events.onCompletion(null, false);
                }
            }
            if(locCount>20){
                progressDialog.dismiss();
                handler.removeCallbacksAndMessages(null);
                Toast.makeText(context, "Please try after sometime", Toast.LENGTH_LONG).show();
                events.onCompletion(null, false);
            }
        } else {
//            locCount++;
//            NetLatLong(handler, events,context,locCount,progressDialog);
//            if (locCount > 10) {
            progressDialog.dismiss();
//                handler.removeCallbacksAndMessages(null);
            Toast.makeText(context, "Your gps is Off, Please switch on Gps", Toast.LENGTH_LONG).show();
            events.onCompletion(null, false);

//            }
        }
    }

    public static void GpsLatLong(final Handler handler,final CompletionHandler events, final Context context, final int locCount, final ProgressDialog progressDialog) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        GPSTrackerGps gps = new GPSTrackerGps(context);
        if (gps.canGetLocation()) {
            Location location = new Location("");
            location.setLatitude(gps.getLatitude());
            location.setLongitude(gps.getLongitude());
            handler.removeCallbacksAndMessages(null);
            events.onCompletion(location, true);

        } else {
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setMessage("Getting Lat-Long from Gps");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkDist(handler,events, context, locCount, progressDialog);
                }
            }, 1000);

        }

    }

    public static void NetLatLong(final Handler handler, final CompletionHandler events, final Context context, final int locCount, final ProgressDialog progressDialog) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        GPSTrackerNetwork networkLoc = new GPSTrackerNetwork(context);
        if (networkLoc.canGetLocation()) {
            Location location = new Location("");
            location.setLatitude(networkLoc.getLatitude());
            location.setLongitude(networkLoc.getLongitude());
            handler.removeCallbacksAndMessages(null);
            events.onCompletion(location, true);
        } else {
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setMessage("Getting Lat-Long from Network");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkDist(handler, events, context, locCount, progressDialog);
                }
            }, 1000);
        }
    }

    public static Boolean CheckGpsStatus(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return GpsStatus;
    }

    public static void getDistance(final String[] locationId, Handler handler, final Context context, final Double geoFence, final Boolean hasGeofence, final DistanceHandler event){
//        if(!geoCoordinate.equals("0") && !geoCoordinate.equals("")) {
            Util.setCompletionHandler(handler, 0, context, new Util.CompletionHandler() {
                @Override
                public void onCompletion(Location location, boolean canGetLatLong) {
                    if (canGetLatLong) {
                        boolean check = false;
                        ArrayList<MappingModel> mapList = SharedpreferenceUtility.getInstance(context).getArrayListMappingModel(Constant.MappingData);
                        if (mapList != null) {
                            for (String locationId : locationId) {
                                for (MappingModel mapping : mapList) {
                                    if (mapping.getLocationId().equals(locationId)) {
                                        String geoCoordinate = mapping.getLatlong();
                                        String latlong[] = geoCoordinate.split(",");
                                        if(latlong.length == 2) {
                                            Double lat = Double.parseDouble(latlong[0]);
                                            Double longi = Double.parseDouble(latlong[1]);
                                            Location second = new Location("");
                                            second.setLatitude(lat);
                                            second.setLongitude(longi);
                                            Float distance = location.distanceTo(second);
                                            if (hasGeofence) {
                                                if (distance <= geoFence) {
                                                    check = true;
                                                    event.onCompletion(true, locationId, mapping.getMappingId(), String.valueOf(distance), String.valueOf(lat), String.valueOf(longi));
                                                    break;
                                                }
                                            } else {
                                                check = true;
                                                event.onCompletion(true, locationId, mapping.getMappingId(), String.valueOf(distance), String.valueOf(lat), String.valueOf(longi));
                                                break;
                                            }
                                        } else {
                                            event.onCompletion(false, "", "0", "","","");
                                        }
                                    }
                                }
                                if(check){
                                    break;
                                }
                            }
                        }

                        if (!check) {
                            event.onCompletion(false, "", "0", "","","");
                        }
                    }
                }
            });

    }

    public static void getDistanceForSingleLocation(final MenuDetailModel locations, Handler handler, Context context, final Double geoFence, final DistanceHandler event){
//        if(!geoCoordinate.equals("0") && !geoCoordinate.equals("")) {
        Util.setCompletionHandler(handler, 0, context, new Util.CompletionHandler() {
            @Override
            public void onCompletion(Location location, boolean canGetLatLong) {
                if (canGetLatLong) {
                    boolean check = false;
                    String geoCoordinate = locations.getLatlong();
                    String latlong[] = geoCoordinate.split(",");
                    Double lat = Double.parseDouble(latlong[0]);
                    Double longi = Double.parseDouble(latlong[1]);
                    Location second = new Location("");
                    second.setLatitude(lat);
                    second.setLongitude(longi);
                    Float distance = location.distanceTo(second);
                    if (distance <= geoFence) {
                        check = true;
                        event.onCompletion(true, locations.getLocationId(), "", String.valueOf(distance), String.valueOf(lat), String.valueOf(longi));
                    }

                    if (!check) {
                        event.onCompletion(false, locations.getLocationId(), "", "", "","");
                    }
                }
            }
        });

//        }

    }


    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

//    public static boolean matchesHolidayList(Context context, String date){
//        String[] lists = SharedpreferenceUtility.getInstance(context).getArrayList("holidayList");
//        for (int i = 0; i < lists.length; i++){
//            if(date.equals(lists[i])){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public String[] getArrayList(String key){
//        String[] text;
//        try {
//            Gson gson = new Gson();
//            String jsonText = mPref.getString(key, null);
//            text = gson.fromJson(jsonText, String[].class);  //EDIT: gso to gson
//            return text;
//        }catch (Exception e) {
//            e.printStackTrace();
//            text = new String[0];
//            return text;
//        }
//    }

    public static String getOnlyDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    public static boolean isNotSunday(Context context){
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            Alerts.showSimpleAlert(context, "Error!!", "Cannot apply leave for Sunday");
            return false;
        }
        return true;
    }

    public static int getBatteryInfo(Context context){
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if(level == -1 || scale == -1) {
            return (int)50.0f;
        }
        //New code 29-10
        float mybattery_level = ((float)level / (float)scale) * 100.0f;
        int battery = (int)mybattery_level;

        Log.e("Test", " Device batteryLevel :: " + level);

        return  level;
    }

//    public static void activityCall(String event, Context context, String M_id, String locationId, String mappingId, String distance, String latlong, InfieldV5Db db){
//        ActivityModel model = new ActivityModel();
//        model.setEvent(event);
//        model.setLatlong(latlong);
//        model.setMobile_time(Util.calculateMobileTime());
//        model.setM_id(M_id);
//        model.setEmp_id(SharedpreferenceUtility.getInstance(context).getString(Constant.Empid));
//        model.setLocationId(locationId);
//        model.setDistance(distance);
//        model.setMappingId(mappingId);
//        model.setDid(SharedpreferenceUtility.getInstance(context).getString(Constant.Did));
//        Api.setHost(SharedpreferenceUtility.getInstance(context).getString(Constant.Base_url));
//        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
//        Call<ActivityModelResponse> call = apiInterface.sendActivity(model);
//        call.enqueue(new Callback<ActivityModelResponse>() {
//            @Override
//            public void onResponse(Call<ActivityModelResponse> call, retrofit2.Response<ActivityModelResponse> response) {
//                ActivityModelResponse res=response.body();
////                                Log.e("res activity", res.toString());
//                if(!res.getStatus().equals("success")){
//                    Toast.makeText(context,res.getStatus(),Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ActivityModelResponse> call, Throwable t) {
//                db.insertActivityValues(model, db);
//            }
//        });
//    }

     public static ArrayList<CheckPointsModel> shuffleCheckpointsGenerator(ArrayList<CheckPointsModel> set){
         ArrayList<CheckPointsModel> solution = new ArrayList<>();
         if(set.get(0).getTypeId().equals(Constant.Caption_code)){
             for (int i = 1; i < set.size(); i++) {
                 solution.add(set.get(i));
             }
             Collections.shuffle(solution);
             solution.add(0,set.get(0));
         } else {
             for (int i = 0; i < set.size(); i++) {
                 solution.add(set.get(i));
             }
             Collections.shuffle(solution);
         }
        return solution;
    }


    public static ArrayList<String> shuffleOptionsGenerator(String[] set, String shuffle){
        ArrayList<String> solution = new ArrayList<>();
        for (int i = 0; i < set.length; i++) {
            solution.add(set[i]);
        }
        if(shuffle.equals("1")) {
            Collections.shuffle(solution);
        }
        return solution;
    }

    public static String combineByComma(ArrayList<String> data){
        String combinedString = "";
        for(int i=0;i<data.size();i++){
            if(i!= data.size()-1){
                combinedString+=data.get(i)+",";
            } else {
                combinedString+=data.get(i);
            }
        }
        return combinedString;
    }

    public static boolean isGoogleMapsInstalled(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void openMaps(String latlong, Context context){
        if (Util.isGoogleMapsInstalled(context)) {
            String latlongSplit[] = latlong.split(",");
            if (!latlongSplit[0].equals("0")
                    && !latlongSplit[0].equals("0.0")
                    && !latlongSplit[1].equals("0")
                    && !latlongSplit[1].equals("0.0")) {
                String uriString = "http://maps.google.com/maps?daddr=" + latlongSplit[0] + "," + latlongSplit[1];
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(
                        Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            } else {
                Toast.makeText(context,
                        "Location Detail not found",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Google Map is not Installed",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
