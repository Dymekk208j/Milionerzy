package pl.damiandziura.milionerzy;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by Dymek on 16.10.2017.
 */

public class BackgroundSoundService extends Service {

    private MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true); // Set looping
        player.setVolume(70,70);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_NOT_STICKY;
    }

    public void onMute() {
        player.setVolume(0,0);
    }
    public void onUnmute() {
        player.setVolume(70,70);
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

}
