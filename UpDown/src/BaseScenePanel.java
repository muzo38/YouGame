import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class BaseScenePanel extends JPanel {

    protected SceneListener listener;
    protected String sceneTitle;
    protected float titleAlpha = 0f;
    private Timer titleFadeTimer;
    private int titlePhase = 0;

    public BaseScenePanel(SceneListener listener, String sceneTitle) {
        this.listener = listener;
        this.sceneTitle = sceneTitle;
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0); // Clean exit on ESC
                }
            }
        });

        setupTitleFade();
    }

    private void setupTitleFade() {
    titleFadeTimer = new Timer(50, e -> {
        switch (titlePhase) {
            case 0: // Fade in
                titleAlpha += 0.05f;
                if (titleAlpha >= 1f) {
                    titleAlpha = 1f;
                    titlePhase = 1;

                // Fix: start the hold timer
                    Timer holdTimer = new Timer(2000, e2 -> titlePhase = 2);
                    holdTimer.setRepeats(false);
                    holdTimer.start();
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
    });

    SwingUtilities.invokeLater(() -> {
        requestFocusInWindow();
        titleFadeTimer.start();
    });
}

// Override this in subclasses
protected void onTitleFadeComplete() {
    // Default: do nothing
}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (sceneTitle != null && !sceneTitle.isEmpty()) {
            SceneToolkit.drawSceneTitle(g2d,
                sceneTitle,
                new Font("Garamond", Font.BOLD, 48),
                Color.WHITE,
                titleAlpha,
                getWidth(),
                getHeight());
        }

        paintScene(g2d);

        g2d.dispose();
    }

    protected abstract void paintScene(Graphics2D g2d);
}
