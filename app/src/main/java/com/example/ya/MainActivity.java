package com.example.ya;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.MediaInformation;
import com.example.ya.video.VideoListener;
import com.example.ya.video.VideoPlayer;

import java.io.File;
import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;

import static com.arthenica.mobileffmpeg.FFmpeg.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.FFmpeg.RETURN_CODE_SUCCESS;

public class MainActivity extends AppCompatActivity implements VideoListener {

    private VideoPlayer videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
//        videoPlayer = findViewById(R.id.video);
//
//        videoPlayer.setVideoListener(this);
//        videoPlayer.setPath("http://video.u-touch.co.jp/live1/livestream_ff.m3u8");
//        try {
//            videoPlayer.load();
//        } catch (IOException e) {
//            Toast.makeText(this,"播放失败",Toast.LENGTH_SHORT);
//            e.printStackTrace();
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        File file = Environment.getExternalStoragePublicDirectory("PicInPic.mp4");
//        FFmpeg.execute("-i " + file.getAbsolutePath() + " -c:v mpeg4 " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/test2.mp4");
        FFmpeg.execute("-i " + file.getAbsolutePath() + " -i " + Environment.getExternalStoragePublicDirectory("live.png").getPath() + " -filter_complex overlay=W-w " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/output.mp4");
//        FFmpeg.execute("-re -i " + file.getAbsolutePath() + " -vcodec copy -acodec copy -f flv rtmp://video.u-touch.co.jp/live1/livestream");
//        if (file.exists()) {
//            Log.i(Config.TAG, file.getName());
//            MediaInformation info = FFmpeg.getMediaInformation(file.getPath());
//            Log.i(Config.TAG, info.getFormat());
//        }


    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        videoPlayer.start();
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

    }
}
