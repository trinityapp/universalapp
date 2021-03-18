package com.trinity.dynamicforms.Api;

import com.trinity.dynamicforms.Database.Model.CheckPointsModel;
import com.trinity.dynamicforms.Database.Model.SaveDataModel;
import com.trinity.dynamicforms.Models.ErrorModel;
import com.trinity.dynamicforms.Models.MappingModel;
import com.trinity.dynamicforms.Models.MenuModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("checklist.php?")     // API's endpoints
    Call<MenuModel> categoryData(@Query("empId") String empid,
                                 @Query("roleId") String roleId);//, @Query("emp_id") String emp_id);


    @GET("checkpoint.php")     // API's endpoints
    Call<ArrayList<CheckPointsModel>> getCheckListData(@Query("empId") String empid,
                                                       @Query("roleId") String roleId);

    @Multipart
    @POST("save_img.php")
    Call<ErrorModel> saveImg(@Part MultipartBody.Part image,
                                        @Part("trans_id") RequestBody trans_id,
                                        @Part("company") RequestBody company,
                                        @Part("chk_id") RequestBody chk_id,
                                        @Part("depend_upon") RequestBody depend_upon,
                                        @Part("timestamp") RequestBody timestamp,
                                        @Part("caption") RequestBody caption

    );

    @Headers({"Accept: application/json"})
    @POST("saveCheckpoint.php")     // API's endpoints
    Call<ErrorModel> saveData(@Body List<SaveDataModel> data);

    @GET("mapping.php?")     // API's endpoints
    Call<ArrayList<MappingModel>> mappingData(@Query("empId") String empid);
}
