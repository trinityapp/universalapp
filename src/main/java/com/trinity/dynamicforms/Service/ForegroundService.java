package com.trinity.dynamicforms.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

//import com.example.largefilessendinglibrary.Api.Api;
//import com.example.largefilessendinglibrary.Api.ProgressRequestBody;
//import com.example.largefilessendinglibrary.Api.RetrofitClient;
import com.google.gson.JsonObject;
import com.trinity.dynamicforms.Activity.ViewPagerForms;
import com.trinity.dynamicforms.Api.Api;
import com.trinity.dynamicforms.Api.ApiInterface;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.Database.Model.SaveImageModel;
import com.trinity.dynamicforms.Models.ErrorModel;
import com.trinity.dynamicforms.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForegroundService extends Service implements ProgressRequestBody.UploadCallbacks {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final int ONGOING_NOTIFICATION_ID = 1;
    public static boolean hasStarted = false;
    Context context;
    //    String path;
    SaveImageModel imageModel;
    //    ArrayList<ImageModel> imageModels;
    int count;
    int PROGRESS_MAX = 100;
    int PROGRESS_CURRENT = 0;
    boolean hasSentData = false;
    HashMap<String, Integer> videos = new HashMap<String, Integer>();
    Notification notification;
    NotificationCompat.Builder builder;
    PendingIntent pendingIntent;
    NotificationManager mNotificationManager;
    Database db;
    String companyName, phonenumber;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        db = Database.getDatabase(ForegroundService.this);
        Bundle b = intent.getExtras();
        imageModel = (SaveImageModel) b.getSerializable("imageModel");
        companyName = b.getString("company");
        phonenumber = b.getString("phonenumber");
        hasStarted = true;

//        startForeground(ONGOING_NOTIFICATION_ID, getMyActivityNotification());
        count = 0;
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
                sendFile();
//            }
//        });
        return START_NOT_STICKY;
    }

    private void updateNotificationProgress(int progress) {

        builder.setProgress(100, progress, false);

//Send the notification:
        notification = builder.build();
        mNotificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
    }

    private void updateNotificationTitle(SaveImageModel imageModel) {
        builder.setContentText(imageModel.getCaption() + "-" + imageModel.getChk_Id() + " is uploading");

//Send the notification:
        notification = builder.build();
        mNotificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
    }

    private Notification getMyActivityNotification() {
        Intent notificationIntent = new Intent(this, getApplicationContext().getClass());
        pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification = builder
                .setContentTitle("Sending Data")
//                        .setContentText(imageModel.getCaption()+"-"+imageModel.getChk_Id()+ " is uploading")
                .setSmallIcon(R.drawable.camera)
                .setContentIntent(pendingIntent)
                .setProgress(100, 0, false)
                .setOnlyAlertOnce(true)
                .build();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
        return notification;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void startServiceNew(Context context, String companyName, String phonenumber) {
        Intent serviceIntent = new Intent(context, ForegroundService.class);
//        Bundle b = new Bundle();
//        b.putSerializable("imageModel", imageModel);
//        serviceIntent.putExtras(b);
        serviceIntent.putExtra("company", companyName);
        serviceIntent.putExtra("phonenumber", phonenumber);
        this.context = context;
        ContextCompat.startForegroundService(context, serviceIntent);
    }

    public void stopService(Context context) {
        Intent serviceIntent = new Intent(context, ForegroundService.class);
        try {
            if (hasSentData) {
                hasSentData = false;
                sendFile();
            }
        } catch (Exception e) {
            hasStarted = false;
            stopService(serviceIntent);
            Log.d("Error", e.getLocalizedMessage());
        }
    }

    public void sendFile() {
        List<SaveImageModel> imageModels = db.saveImageDao().getAll();
        for (int i = 0; i < imageModels.size(); i++) {
            String tempDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + "/" + companyName + "/" + phonenumber + "/" + imageModels.get(i).getCaption() + "/" + imageModels.get(i).getTimeStamp();
            Log.d("Files", "Path: " + tempDir);
            File directory = new File(tempDir);
            if (directory.exists()) {
                final File[] files = directory.listFiles();
                Log.d("Files", "Size: " + files.length);


                for (int j = 0; j < files.length; j++) {
                    uploadData(tempDir + "/" + files[j].getName(), imageModels.get(i));
                    updateNotificationTitle(imageModels.get(i));
                    break;
                }
            } else {
                hasSentData = true;
                stopService(getApplicationContext());
                db.saveImageDao().delete(imageModels.get(i));
                break;
            }
        }
    }

    private void uploadData(String file, final SaveImageModel data) {
        startForeground(ONGOING_NOTIFICATION_ID, getMyActivityNotification());
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        // MultipartBody.Part is used to send also the actual file name
        String fileUrl = file.substring(file.lastIndexOf("/") + 1);
        if (fileUrl.indexOf(".") > 0)
            fileUrl = fileUrl.substring(0, fileUrl.lastIndexOf("."));
        Log.d("trans_id", data.getTrans_id());
        Log.d("chk_id", fileUrl);
        String[] fileName = fileUrl.split("-");
        RequestBody trans_id =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), data.getTrans_id());
        RequestBody chk_id =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), fileName[0]);
        RequestBody dependent =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), fileName[1]);
        RequestBody company =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), companyName);

        RequestBody timestamp =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), data.getTimeStamp());

        final RequestBody caption =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), data.getCaption());

        MultipartBody.Part body = null;
        if (!file.equals("")) {
            File fileSend = new File(file);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileSend);
            body = MultipartBody.Part.createFormData("attachment", fileSend.getName(), requestFile);
        }

        Call<ErrorModel> call = apiInterface.saveImg(body, trans_id, company, chk_id, dependent, timestamp, caption);

        call.enqueue(new Callback<ErrorModel>() {
            @Override
            public void onResponse(Call<ErrorModel> call, retrofit2.Response<ErrorModel> response) {
                try {
                    ErrorModel errorModels = (ErrorModel) response.body();
                    if (errorModels.getError().equals("200")) {
                        String timestamp1 = errorModels.getTimestamp();
                        String filename = errorModels.getFileName();
                        String caption = errorModels.getCaption();
                        final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                + "/" + companyName + "/" + phonenumber + "/" + caption + "/" + timestamp1 + "/" + filename;
                        File fdelete = new File(path);
                        count++;
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
//                                surveyDb.deleteImageFile(imageModel.getTaskId(), imageModel.getSubTaskId(), timestamp1, imageModel.getCheckPointId());
                            }
                        }
                    }
                    hasSentData = true;
                    stopService(getApplicationContext());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ErrorModel> call, Throwable t) {
                Log.d("Falire", "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void onProgressUpdate(int percentage) {
        PROGRESS_CURRENT = percentage;
        updateNotificationProgress(percentage);
        Log.d("Percentage", String.valueOf(percentage));
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hasStarted = false;
    }
}
