//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// audio utility

public abstract class BaseScenePanel extends JPanel {

    protected SceneListener listener;
    protected String sceneTitle;

    protected String titleSfxPath = "/assets/sound/title_sfx.wav";

    protected float titleAlpha = 0f;
    private Timer titleFadeTimer;
    private int titlePhase = 0;

    public BaseScenePanel(SceneListener listener, String sceneTitle) {
        this.listener = listener;
        this.sceneTitle = sceneTitle;

        setBackground(Color.BLACK);
        setFocusable(true);

        SoundManager.stopMusic();
        setupKeyBindings();
        setupTitleFade();
    }

    private void setupKeyBindings() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    private void setupTitleFade() {
        titleFadeTimer = new Timer(50, e -> handleTitleFadeStep());
        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
            SoundManager.playEffect(titleSfxPath);
            titleFadeTimer.start();
        });
    }

    private void handleTitleFadeStep() {
        switch (titlePhase) {
            case 0: // Fade in
                titleAlpha += 0.05f;
                if (titleAlpha >= 1f) {
                    titleAlpha = 1f;
                    titlePhase = 1;
                    startHoldTimer();
                }
                break;

            case 2: // Fade out
                titleAlpha -= 0.03f;
                if (titleAlpha <= 0f) {
                    titleAlpha = 0f;
                    titleFadeTimer.stop();
                    onTitleFadeComplete();
                }
                break;
        }

        repaint();
    }

    private void startHoldTimer() {
        Timer holdTimer = new Timer(2000, e -> titlePhase = 2);
        holdTimer.setRepeats(false);
        holdTimer.start();
    }

    protected void onTitleFadeComplete() {
        // Override in subclass
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (sceneTitle != null && !sceneTitle.isEmpty()) {
            SceneToolkit.Fade.sceneTitle(
                g2d,
                sceneTitle,
                FontManager.TITLE_FONT,
                Color.WHITE,
                titleAlpha,
                getWidth(),
                getHeight()
            );
        }

        paintScene(g2d);
        g2d.dispose();
    }

    protected abstract void paintScene(Graphics2D g2d);
}
