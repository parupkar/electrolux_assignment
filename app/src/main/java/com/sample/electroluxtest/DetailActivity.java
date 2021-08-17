package com.sample.electroluxtest;

import static com.sample.electroluxtest.Utils.Tools.IsNetworkAvailable;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    private ImageView photo;
    private TextView title;
    private Button downloadBtn;
    private String fileUri;
    private static final int PERMISSION_WRITE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_page);

        String titleStr = getIntent().getStringExtra("title");
        String photoUrl = getIntent().getStringExtra("photo");

        photo = findViewById(R.id.photo);
        title = findViewById(R.id.title);
        downloadBtn = findViewById(R.id.downloadBtn);

        Picasso.get().load(photoUrl).into(photo);
        title.setText(titleStr);

        checkPermission();
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    if (IsNetworkAvailable(DetailActivity.this)) {
                        DownloadImage(photoUrl);
                    } else {
                        Toast.makeText(DetailActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void DownloadImage(String url) { //Funtion to save image in /DCIM/electrolux directory
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/electrolux");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir + File.separator + System.currentTimeMillis() + ".jpg";
                    Log.d("fileUri:: ", fileUri);
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), R.string.save_success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }
    public boolean checkPermission() { //check the permission for Read and write storeage to save image in mobile storage
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }
}
