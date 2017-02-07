package com.zengcanxiang.learning_notes_m.permission;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zengcanxiang.learning_notes_m.R;

import java.io.File;

/**
 * 录音工作界面
 */
public class AudioActivity extends AppCompatActivity {
    Button start, play, stop, delete;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testAudio";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        start = (Button) findViewById(R.id.audio_start);
        play = (Button) findViewById(R.id.audio_play);
        stop = (Button) findViewById(R.id.audio_stop);
        delete = (Button) findViewById(R.id.delete);
        mediaRecorder = new MediaRecorder();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioStart();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioStop();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = new File(path);
                File file = new File(dir, "test.amr");
                file.delete();
                dir.delete();
            }
        });
    }

    /**
     * 结束录音
     */
    private void audioStop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
        } catch (Exception e) {

        }
    }

    /**
     * 开始录音
     */
    public void audioStart() {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, "test.amr");

            // 设置来源为麦克风
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置音频格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            // 设置音频编码
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(file.getPath());
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Toast.makeText(this, "创建录音失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 播放录音
     */
    public void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mediaPlayer.reset();
                mediaPlayer = null;
                return false;
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放完成
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
        try {
            mediaPlayer.setDataSource(path + "/test.amr");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
