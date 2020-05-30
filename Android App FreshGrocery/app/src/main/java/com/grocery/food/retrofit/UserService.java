package com.grocery.food.retrofit;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST(APIClient.Append_URL + "cat.php")
    Call<JsonObject> getCat(@Body JsonObject object);

    @POST(APIClient.Append_URL + "subcategory.php")
    Call<JsonObject> getSubcategory(@Body JsonObject object);

    @POST(APIClient.Append_URL + "product.php")
    Call<JsonObject> getGetProduct(@Body JsonObject object);

    @POST(APIClient.Append_URL + "home.php")
    Call<JsonObject> getHome(@Body JsonObject object);

    @POST(APIClient.Append_URL + "login.php")
    Call<JsonObject> getLogin(@Body JsonObject object);

    @POST(APIClient.Append_URL + "forgot.php")
    Call<JsonObject> getForgot(@Body JsonObject object);

    @POST(APIClient.Append_URL + "pinmatch.php")
    Call<JsonObject> getPinmatch(@Body JsonObject object);

    @POST(APIClient.Append_URL + "register.php")
    Call<JsonObject> getRegister(@Body JsonObject object);

    @POST(APIClient.Append_URL + "timeslot.php")
    Call<JsonObject> getTimeslot(@Body JsonObject object);

    @POST(APIClient.Append_URL + "search.php")
    Call<JsonObject> getSearch(@Body JsonObject object);


    @POST(APIClient.Append_URL + "profile.php")
    Call<JsonObject> UpdateProfile(@Body JsonObject object);

    @POST(APIClient.Append_URL + "order.php")
    Call<JsonObject> Order(@Body JsonObject object);


    @POST(APIClient.Append_URL + "history.php")
    Call<JsonObject> getHistory(@Body JsonObject object);

    @POST(APIClient.Append_URL + "plist.php")
    Call<JsonObject> getPlist(@Body JsonObject object);

    @POST(APIClient.Append_URL + "feed.php")
    Call<JsonObject> SendFeed(@Body JsonObject object);

    @POST(APIClient.Append_URL + "rate.php")
    Call<JsonObject> Rates(@Body JsonObject object);

    @POST(APIClient.Append_URL + "status.php")
    Call<JsonObject> getStatus(@Body JsonObject object);

    @POST(APIClient.Append_URL + "noti.php")
    Call<JsonObject> getNoti(@Body JsonObject object);

    @POST(APIClient.Append_URL + "notiset.php")
    Call<JsonObject> getNotiSet(@Body JsonObject object);

    @POST(APIClient.Append_URL + "ocancle.php")
    Call<JsonObject> getOdercancle(@Body JsonObject object);

    @POST(APIClient.Append_URL + "area.php")
    Call<JsonObject> getArea(@Body JsonObject object);

    @POST(APIClient.Append_URL + "n_read.php")
    Call<JsonObject> readNoti(@Body JsonObject object);
}
