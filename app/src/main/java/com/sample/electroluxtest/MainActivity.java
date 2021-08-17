package com.sample.electroluxtest;

import static android.widget.Toast.LENGTH_SHORT;

import static com.sample.electroluxtest.Utils.Tools.DisplayInternetConnection;
import static com.sample.electroluxtest.Utils.Tools.IsNetworkAvailable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sample.electroluxtest.Adapter.GridImagesAdapter;
import com.sample.electroluxtest.Helper.RetrofitClient;
import com.sample.electroluxtest.Listner.ApiInterface;
import com.sample.electroluxtest.Model.GetFlickerResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView photosGridView; //RecyclerView to display images Grid
    private ApiInterface apiInterface; //API interface for Retrofit
    private ProgressBar progressBar; //loader on load images
    boolean doubleBackToExitPressedOnce = false; //confirm to close the App
    private ArrayList<GetFlickerResponse.Data.PhotoDataItem> getFlickerResponseArrayList; //Arraylist for getting data from API
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        setContentView(R.layout.activity_main); //main layout

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        photosGridView = findViewById(R.id.photosGridView);
        if (IsNetworkAvailable(MainActivity.this)) { //check if phone have enabled internet
            getFlickerData();
        } else {
            Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
    private void getFlickerData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<GetFlickerResponse> apicall = apiInterface.GetFlickerData("efbd9ab2ffdce6bfe540d32e50e1ff62", "flickr.photos.search", "electrolux", "json", "true", "media", "url_sq", "url_m", 20, 1);
        apicall.enqueue(new Callback<GetFlickerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetFlickerResponse> call, @NonNull Response<GetFlickerResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body().getPhotos().getTotal() > 0) {

                    getFlickerResponseArrayList = new ArrayList<>();
                    getFlickerResponseArrayList = (ArrayList<GetFlickerResponse.Data.PhotoDataItem>) response.body().getPhotos().getPhoto();

                    GridImagesAdapter courseAdapter = new GridImagesAdapter(MainActivity.this, getFlickerResponseArrayList);

                    photosGridView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    photosGridView.setAdapter(courseAdapter);

                } else {
                    Toast.makeText(getApplicationContext(), R.string.no_data, LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetFlickerResponse> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        DisplayInternetConnection(MainActivity.this);

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.to_exit, Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}