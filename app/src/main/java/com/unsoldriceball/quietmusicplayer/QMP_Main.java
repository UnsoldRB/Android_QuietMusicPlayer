package com.unsoldriceball.quietmusicplayer;

import static com.unsoldriceball.quietmusicplayer.QMP_Utility.ShowMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.List;

public class QMP_Main extends AppCompatActivity {

    private View v_root = null;
    public SeekBar v_seekbar_vol = null;
    private Equalizer EQUALIZER;
    private short EQLV_MIN;
    private short EQLV_MAX;
    private int NUM_BAND;
    private MediaPlayer MP;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmp_main);

        //使用するViewを変数に格納する。
        v_root = findViewById(R.id.root);
        v_seekbar_vol = findViewById(R.id.seekbar_volume);

        //音量用Seekbarの初期化。
        v_seekbar_vol.setMax(100);
        v_seekbar_vol.setProgress(0);
        onInitListener_Seekbar(v_seekbar_vol);


        //Equalizerの初期化
        EQUALIZER = new Equalizer(0, 0);
        EQUALIZER.setEnabled(true);
        EQLV_MIN = EQUALIZER.getBandLevelRange()[0];
        EQLV_MAX = EQUALIZER.getBandLevelRange()[1];
        NUM_BAND = EQUALIZER.getNumberOfBands();

        //MediaPlayerを作成
        MP = new MediaPlayer();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        MP.release(); //アプリ終了時にMediaPlayerを開放する。
    }



    //Seekbarのイベントリスナー
    private void onInitListener_Seekbar(SeekBar sb) {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.getScrollY();

                final String MSG = i + "%";
                ShowMessage(v_root, MSG);

                adjustEqualizer((short) ((short) i * 0.01));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void adjustEqualizer(short volume) {

        final short VOLUME_LV = (short) (EQLV_MIN + ((EQLV_MAX - EQLV_MIN) * volume));
        for (short i = 0; i <= NUM_BAND; i++) {
            EQUALIZER.setBandLevel(i, VOLUME_LV);
        }
    }
}