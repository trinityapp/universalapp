package com.trinity.dynamicforms.DetailsActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.trinity.dynamicforms.R;


public class VideoLandscapeActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_landscape);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        videoView = findViewById(R.id.videoView);
        String fullScreen =  getIntent().getStringExtra("fullScreenInd");
        if("y".equals(fullScreen)){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }

        Uri videoUri = Uri.parse(url);

        videoView.setVideoURI(videoUri);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();
    }

}
