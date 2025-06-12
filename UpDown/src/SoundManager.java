import javax.sound.sampled.*;
import javax.swing.*;

public class SoundManager {
    private static Clip currentClip = null;
    private static FloatControl volumeControl = null;
    private static String currentPath = null;
    private static Timer fadeTimer = null;

    public static synchronized void playMusicLoop(String resourcePath) {
        if (resourcePath == null) {
            stopMusic();
            return;
        }
        if (resourcePath.equals(currentPath)) {
            return;
        }
        stopMusic();
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                SoundManager.class.getResource(resourcePath)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            FloatControl ctrl = null;
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                ctrl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(ctrl, 0f);
            }
            currentClip = clip;
            volumeControl = ctrl;
            currentPath = resourcePath;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            if (ctrl != null) fadeVolume(ctrl, 0f, 1f, null);
        } catch (Exception e) {
            currentClip = null;
            volumeControl = null;
            currentPath = null;
        }
    }

    public static synchronized void stopMusic() {
        if (currentClip == null) return;
        final Clip clip = currentClip;
        final FloatControl ctrl = volumeControl;
        currentClip = null;
        volumeControl = null;
        currentPath = null;
        if (ctrl != null) {
            fadeVolume(ctrl, volumeFromControl(ctrl), 0f, () -> {
                clip.stop();
                clip.close();
            });
        } else {
            clip.stop();
            clip.close();
        }
    }

    public static void playEffect(String resourcePath) {
        if (resourcePath == null) return;
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                SoundManager.class.getResource(resourcePath)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (Exception e) {
            // ignore
        }
    }

    private static void fadeVolume(FloatControl ctrl, float from, float to, Runnable done) {
        if (fadeTimer != null && fadeTimer.isRunning()) fadeTimer.stop();
        int steps = 20;
        final float delta = (to - from) / steps;
        final float[] vol = { from };
        fadeTimer = new Timer(50, null);
        fadeTimer.addActionListener(e -> {
            vol[0] += delta;
            setVolume(ctrl, vol[0]);
            if ((delta > 0 && vol[0] >= to) || (delta < 0 && vol[0] <= to)) {
                setVolume(ctrl, to);
                fadeTimer.stop();
                if (done != null) done.run();
            }
        });
        fadeTimer.start();
    }

    private static void setVolume(FloatControl ctrl, float volume) {
        volume = Math.max(0f, Math.min(volume, 1f));
        float min = ctrl.getMinimum();
        float max = ctrl.getMaximum();
        ctrl.setValue(min + (max - min) * volume);
    }

    private static float volumeFromControl(FloatControl ctrl) {
        float min = ctrl.getMinimum();
        float max = ctrl.getMaximum();
        return (ctrl.getValue() - min) / (max - min);
    }
}
