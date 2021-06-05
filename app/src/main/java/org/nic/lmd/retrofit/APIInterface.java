package org.nic.lmd.retrofit;

import org.nic.lmd.entities.MarketInspectionDetail;
import org.nic.lmd.retrofitPojo.DashboardResponse;
import org.nic.lmd.retrofitPojo.ManufacturerPoso;
import org.nic.lmd.retrofitPojo.MyResponse;
import org.nic.lmd.retrofitPojo.RequestForRevenueData;
import org.nic.lmd.retrofitPojo.VendorDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("/app/dashboard/m/all")
    Call<DashboardResponse> doGetDashboardAllData();


    @GET("/app/dashboard/m/all/{locationType}/{location}")
    Call<DashboardResponse> doGetDashboardAllData(@Path("locationType") String locationType, @Path("location") String location);

    @GET("/app/vendor/{vendorId}")
    Call<VendorDataResponse> doGetVender(@Path("vendorId") String vendorId);

    @GET("/app/manufacturer/{manufacurerId}")
    Call<ManufacturerPoso> doGetManufacture(@Path("manufacurerId") String manufacurerId);

    @GET("/lmd-api/lmd/getMarketInspectionDetails/{monthSelected}/{yearSelected}/{userid}")
    Call<MyResponse<List<MarketInspectionDetail>>> doGetMarketInspectionDetails(@Path("monthSelected")int monthSelected, @Path("yearSelected")int yearSelected, @Path("userid")String userid);

    @POST("/lmd-api/lmd/saveMarketInspectionDetails")
    Call<MyResponse<String>> saveMarketInspectionDetails(@Body List<MarketInspectionDetail> marketInspectionDetail);

    @GET("/lmd-api/lmd/getMonthlyRevenueDetails/{monthSelected}/{yearSelected}/{userid}")
    Call<MyResponse<RequestForRevenueData>> doGetRevenueReportDetails(@Path("monthSelected")int monthSelected, @Path("yearSelected")int yearSelected, @Path("userid")String userid);

    @POST("/lmd-api/lmd/saveRevenueReportDetails")
    Call<MyResponse<String>> saveRevenueReport(@Body RequestForRevenueData requestForRevenueData);
    /* @GET("/api/manufacturer/11112")
    Call<ManufacturerPoso> doGetManufacture();*/

    //locationType,loginRole,loginLocation
    //@GET("/api/dashboard/m/all")
    //Call<DashboardResponse> doGetDashboardData();

     /*@POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
