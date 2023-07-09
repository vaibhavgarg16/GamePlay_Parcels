package com.game.playparcels.SevicesApi;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static final String BASE_URL="https://www.gameplayparcels.co.uk/index.php/wp-json/booking/api/v1/";
            //"http://readyforyourreview.com/HamzaM/index.php/wp-json/booking/api/v1/";

    public static final String SUBSCRIBE_URL="https://gameplayparcels.co.uk/packages/?";
    public static final String PRIVACY_URL="https://gameplayparcels.co.uk/privacy-policy/?";

    @Nullable
    private static Retrofit retrofit=null;

    @NonNull
    public  static  Retrofit getApiClient()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(getRequestHeader()).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    private static OkHttpClient getRequestHeader() {
        return new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                   .connectTimeout(60, TimeUnit.SECONDS)
                   .build();
    }

    public static final Retrofit getRetrofitInstance() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().
                connectTimeout(5, TimeUnit.MINUTES);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    //---------------------------------------------------------

}