package com.example.infiny.pickup.Interfaces;

import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Model.AddToCartData;
import com.example.infiny.pickup.Model.CafeListingData;
import com.example.infiny.pickup.Model.CreateOrderData;
import com.example.infiny.pickup.Model.FooRequest;
import com.example.infiny.pickup.Model.ForgotPasswordData;
import com.example.infiny.pickup.Model.FpResetPasswordData;
import com.example.infiny.pickup.Model.LoginData;
import com.example.infiny.pickup.Model.MenuListData;
import com.example.infiny.pickup.Model.OrderData;
import com.example.infiny.pickup.Model.OrderListData;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.Model.SignUpData;
import com.example.infiny.pickup.Model.VerifyFpOtp;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by infiny on 9/18/17.
 */

public interface ApiIntegration {
    @FormUrlEncoded
    @POST("signup")
    Call<SignUpData> getsignup(@Field("firstname") String  firstname,
                           @Field("lastname") String  lastname,
                           @Field("email") String  email,
                             @Field("password") String  password,
                            @Field("dob") String  dob,
                            @Field("postalcode") String  postalcode,
                            @Field("address") String  address,
                            @Field("city") String  City);
    @FormUrlEncoded
    @POST("signin")
    Call<LoginData> getsignin(@Field("email") String  email,
                               @Field("password") String  password);
    @FormUrlEncoded
    @POST("cafelisting")
    Call<CafeListingData> getcafelisting(@Field("userToken") String  token,
                                    @Field("lat") String  lat,
                                         @Field("lng") String  lang);
    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ForgotPasswordData> getForgotPassword(@Field("email") String  email);

    @FormUrlEncoded
    @POST("verifyresetPasswordToken")
    Call<VerifyFpOtp> getVerifpOtp(@Field("token") String  token);
    @FormUrlEncoded
    @POST("resetPassword")
    Call<FpResetPasswordData> getResetpassword(@Field("token") String  token,
                                               @Field("password") String  password);

    @FormUrlEncoded
    @POST("menulisting")
    Call<MenuListData> getMenuListing(@Field("userToken") String  token,
                                      @Field("sid") String  sid);

    @FormUrlEncoded
    @POST("cartListing")
    Call<OrderListData> getOrderListing(@Field("userToken") String  token);

    @FormUrlEncoded
    @POST("addItemToCart")
    Call<AddToCartData> getAddtocart(@Field("userToken") String  token,
                                     @Field("itemId") String  itemId,
                                     @Field("itemSize") String  itemSize,
                                     @Field("itemName") String  itemName,
                                     @Field("itemPrice") String  itemPrice,
                                     @Field("itemQuantity") String  itemQuantity,
                                     @Field("itemCat") String  itemCat,
                                     @Field("shopDetail") String  shopDetail);
    @FormUrlEncoded
    @POST("addItemToCartwithOkclick")
    Call<AddToCartData> getDeleteCart(@Field("userToken") String  token,
                                     @Field("itemId") String  itemId,
                                     @Field("itemSize") String  itemSize,
                                     @Field("itemName") String  itemName,
                                     @Field("itemPrice") String  itemPrice,
                                     @Field("itemQuantity") String  itemQuantity,
                                     @Field("shopDetail") String  shopDetail);
    @FormUrlEncoded
    @POST("deleteItemFromCart")
    Call<AddToCartData> getDeleteCartItem(@Field("userToken") String  token,
                                      @Field("itemId") String  itemId,
                                       @Field("itemSize") String  itemSize,
                                      @Field("shopDetail") String  shopDetail);

  @POST("createOrder")
    Call<CreateOrderData> getCreateOrder(@Header("Content-Type") String token, @Body JSONObject body);
}
