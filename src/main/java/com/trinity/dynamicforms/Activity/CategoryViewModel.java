package com.trinity.dynamicforms.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.trinity.dynamicforms.Api.Api;
import com.trinity.dynamicforms.Api.ApiInterface;
import com.trinity.dynamicforms.Database.Database;
import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.Database.Model.SaveImageModel;
import com.trinity.dynamicforms.Models.ErrorModel;
import com.trinity.dynamicforms.Models.MappingModel;
import com.trinity.dynamicforms.Models.MenuModel;
import com.trinity.dynamicforms.Models.SaveChecklistModel;
import com.trinity.dynamicforms.Service.ForegroundService;
import com.trinity.dynamicforms.Utils.Constant;
import com.trinity.dynamicforms.Utils.SharedpreferenceUtility;
import com.trinity.dynamicforms.Utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.trinity.dynamicforms.Utils.Constant.Company;
import static com.trinity.dynamicforms.Utils.Constant.Empid;
import static com.trinity.dynamicforms.Utils.Constant.Mobile;


public class CategoryViewModel {
    String Base_url;
    String emp_id;
    String role_id, tid;
    String companyName;
    String phonenumber;
    Database db;
    Context context;
//    ForegroundService service = new ForegroundService();
    public interface OnShareClickedListener {
        void checkPointsSaved(Boolean isSuccess);
    }
    public interface OnShareMenuClickedListener {
        void menuSaved(Boolean isSuccess);
    }
    public CategoryViewModel(Context context, String base_url, String emp_id, String role_id, String tid, String company, String phonenumber, Database db) {
        Base_url = base_url;
        this.emp_id = emp_id;
        this.role_id = role_id;
        this.context = context;
        this.companyName = company;
        this.phonenumber = phonenumber;
        this.db = db;
        this.tid = tid;
        Api.setHost(Base_url);
    }

    public void getCheckList() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ArrayList<CheckPointsModel>> call = apiInterface.getCheckListData(emp_id, role_id);
        call.enqueue(new Callback<ArrayList<CheckPointsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckPointsModel>> call, retrofit2.Response<ArrayList<CheckPointsModel>> response) {
                ArrayList<CheckPointsModel> mLogin = response.body();
                for(CheckPointsModel model: mLogin) {
                    db.checkpointsDao().insertAll(model);
                }
//                completion.checkPointsSaved(true);
            }

            @Override
            public void onFailure(Call<ArrayList<CheckPointsModel>> call, Throwable t) {
                if (Util.isConnected(context)) {
                    Toast.makeText(context, "Please Try Again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "You are not connected to internet", Toast.LENGTH_LONG).show();
                }
//                completion.checkPointsSaved(false);
            }
        });
    }

    public void getMenuDetails(final OnShareMenuClickedListener completion) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<MenuModel> call = apiInterface.categoryData(emp_id, role_id);//SharedpreferenceUtility.getInstance(this).getString(Constant.Mobile));
        call.enqueue(new Callback<MenuModel>() {
            @Override
            public void onResponse(Call<MenuModel> call, retrofit2.Response<MenuModel> response) {
                MenuModel mLogin = response.body();
                SharedpreferenceUtility.getInstance(context).putArrayListMenuCategoryModel(tid,mLogin.getMenu());
                completion.menuSaved(true);
//                loadMenuData();
            }

            @Override
            public void onFailure(Call<MenuModel> call, Throwable t) {
                if (Util.isConnected(context)) {
                    Toast.makeText(context, "Please Try Again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "You are not connected to internet", Toast.LENGTH_LONG).show();
                }
                completion.menuSaved(false);
            }
        });
    }

    public void getLocations(){
        Api.setHost(SharedpreferenceUtility.getInstance(context).getString(Constant.Base_url));
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ArrayList<MappingModel>> call = apiInterface.mappingData(SharedpreferenceUtility.getInstance(context).getString(Constant.Empid));
        call.enqueue(new Callback<ArrayList<MappingModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MappingModel>> call, retrofit2.Response<ArrayList<MappingModel>> response) {
                ArrayList<MappingModel> res=response.body();
                Log.e("res activity", res.toString());
                SharedpreferenceUtility.getInstance(context).putArrayListMappingModel(Constant.MappingData, res);
            }

            @Override
            public void onFailure(Call<ArrayList<MappingModel>> call, Throwable t) {
//                Toast.makeText(context,"Please Try Again",Toast.LENGTH_LONG).show();
            }
        });
    }

    public int getCount() {
        List<String> data = db.saveDataDao().get_timestamp();
        return  data.size();
    }

    public void SendData(){
        List<String>data=db.saveDataDao().get_timestamp();
        for(int i=0;i<data.size();i++) {
            String timestmp = null;
            String heading=null;
            String chk_id = null;
            List<SaveDataModel>savedata=db.saveDataDao().getData(data.get(i));
            SaveDataModel saveDataModel=new SaveDataModel();
            List<SaveChecklistModel> saveChecklistModelList=new ArrayList<>() ;
            for (int j = 0;j < savedata.size(); j++) {
                SaveChecklistModel saveChecklistModels=new SaveChecklistModel();
                saveDataModel.setM_Id(savedata.get(j).getM_Id());
                saveDataModel.setEmp_id(savedata.get(j).getEmp_id());
                saveChecklistModels.setChkpId(savedata.get(j).getCheckpoint());
                saveChecklistModels.setValue(savedata.get(j).getValue());
                saveChecklistModels.setDependent(savedata.get(j).getDependent());
                saveChecklistModelList.add(saveChecklistModels);
                saveDataModel.setChecklist(saveChecklistModelList);
                saveDataModel.setDid(savedata.get(j).getDid());
                saveDataModel.setTimeStamp(savedata.get(j).getTimeStamp());
                saveDataModel.setCaption(savedata.get(j).getCaption());

                saveDataModel.setMobiledatetime(savedata.get(i).getMobiledatetime());
                saveDataModel.setEvent(savedata.get(i).getEvent());
                saveDataModel.setGeolocation(savedata.get(i).getGeolocation());
                saveDataModel.setDistance(savedata.get(i).getDistance());
                saveDataModel.setMappingId(savedata.get(i).getMappingId());
                saveDataModel.setLocationId(savedata.get(i).getLocationId());
                saveDataModel.setAssignId(savedata.get(i).getAssignId());
                saveDataModel.setActivityId(savedata.get(i).getActivityId());
                timestmp=savedata.get(j).getTimeStamp();
                heading=savedata.get(j).getCaption();
            }
            sendDataToServer(saveDataModel,timestmp,heading);
        }


    }

    public void sendDataToServer(final SaveDataModel saveData, final String timestmp, final String heading){
        final ArrayList<SaveDataModel>saveDataModel=new ArrayList<>();
        saveDataModel.add(saveData);
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ErrorModel> call = apiInterface.saveData(saveDataModel);
        call.enqueue(new Callback<ErrorModel>() {
            @Override
            public void onResponse(Call<ErrorModel> call, retrofit2.Response<ErrorModel> response) {
                ErrorModel errorModels= (ErrorModel) response.body();
                if(errorModels.getError().equals("200")){
                    Toast.makeText(context,"Successful Form Submission", Toast.LENGTH_SHORT).show();
                    SaveImageModel saveImageModel=new SaveImageModel();
                    saveImageModel.setCaption(heading);
                    saveImageModel.setTimeStamp(timestmp);
                    saveImageModel.setTrans_id(errorModels.getTransID());
                    saveImageModel.setChk_Id("11"); //not required
                    db.saveImageDao().insertAll(saveImageModel);
//                    try{
//                        service.startServiceNew(context, SharedpreferenceUtility.getInstance(context).getString(Company), SharedpreferenceUtility.getInstance(context).getString(Mobile));
//                    }catch (Exception e){
//                        Log.v("Exception",""+e);
//                    }
                    sendImage();
                    db.saveDataDao().deleteAll();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("activity", thisactivity);
//                    receiver.send(STATUS_FINISHED, bundle);
                }
            }

            @Override
            public void onFailure(Call<ErrorModel> call, Throwable t) {
                Log.d("call", t.getLocalizedMessage());
//                receiver.send(STATUS_ERROR, Bundle.EMPTY);
//                if(Util.isConnected(context)) {
//                    Toast.makeText(context, "Please Try Again", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

//    public void sendImage() {
//        service.startServiceNew(context,SharedpreferenceUtility.getInstance(context).getString(Company), SharedpreferenceUtility.getInstance(context).getString(Mobile));
//    }
    public void sendImage() {
        List<SaveImageModel>imageModels= db.saveImageDao().getAll();
        for(int i=0;i<imageModels.size();i++){
            String tempDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + "/" + companyName + "/" + phonenumber + "/" + imageModels.get(i).getCaption() + "/" + imageModels.get(i).getTimeStamp();
            Log.d("Files", "Path: " + tempDir);
            File directory = new File(tempDir);
            if(directory.exists()){
                final File[] files = directory.listFiles();
                Log.d("Files", "Size: "+ files.length);


                for (int j = 0; j < files.length; j++) {
                    uploadData(tempDir + "/" + files[j].getName(),imageModels.get(i));
                }
            }
            else {
                db.saveImageDao().delete(imageModels.get(i));
                //surveyDb.deleteImage(imageModels.get(i).getTaskId(),imageModels.get(i).getSubTaskId());
            }
        }
    }

    private void uploadData(String file, final SaveImageModel data) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        // MultipartBody.Part is used to send also the actual file name
        String fileUrl=file.substring(file.lastIndexOf("/")+1);
        if (fileUrl.indexOf(".") > 0)
            fileUrl = fileUrl.substring(0, fileUrl.lastIndexOf("."));
        Log.d("trans_id",data.getTrans_id());
        Log.d("chk_id",fileUrl);
        String[] fileName = fileUrl.split("-");
        RequestBody trans_id =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),  data.getTrans_id());
        RequestBody chk_id =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),  fileName[0]);
        RequestBody dependent =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),  fileName[1]);
        RequestBody company =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), companyName);
        RequestBody timestamp =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), data.getTimeStamp());

        final RequestBody caption =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), data.getCaption());

        MultipartBody.Part body=null;
        if(!file.equals("")) {
            File fileSend=new File(file);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileSend);
            body =MultipartBody.Part.createFormData("attachment", fileSend.getName(), requestFile);
        }

        Call<ErrorModel> call = apiInterface.saveImg(body,trans_id,company,chk_id, dependent, timestamp, caption);

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
//                        count++;
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
//                                surveyDb.deleteImageFile(imageModel.getTaskId(), imageModel.getSubTaskId(), timestamp1, imageModel.getCheckPointId());
                            }
                        }
                    }
//                    hasSentData = true;
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ErrorModel> call, Throwable t) {
                Log.d("Falire", "onFailure: "+t.getLocalizedMessage());
            }
        });

    }


}
