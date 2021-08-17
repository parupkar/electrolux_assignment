package com.sample.electroluxtest.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sample.electroluxtest.Helper.RetrofitClient;
import com.sample.electroluxtest.Listner.ApiInterface;
import com.sample.electroluxtest.Model.ConnectionResponse;
import com.sample.electroluxtest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tools {

    public static boolean IsNetworkAvailable(@NonNull final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public static void DisplayInternetConnection(Activity activity) {
        if (IsNetworkAvailable(activity)) {
            final Call<ConnectionResponse> connectionResponseCall = RetrofitClient.getRetrofitInstance().create(ApiInterface.class).Connection();
            connectionResponseCall.enqueue(new Callback<ConnectionResponse>() {
                @Override
                public void onResponse(@NonNull Call<ConnectionResponse> call, @NonNull Response<ConnectionResponse> response) {
                    //Toast.makeText(activity, "It's wroking!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(@NonNull Call<ConnectionResponse> call, @NonNull Throwable t) {
                    Toast.makeText(activity, R.string.server_connection_failed, Toast.LENGTH_LONG).show();
                }
            });

        } else {

            Toast.makeText(activity, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
        }
    }
}
