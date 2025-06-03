//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitlePanel extends JPanel {

    private String titleText = "You";
    private String promptText = "Press any key.";
    private String backgroundImagePath;
    private Image backgroundImage;

    private float alpha = 0f;
    private Timer fadeInTimer;
    private boolean keyPressed = false;

    private SceneListener listener;

    public TitlePanel(SceneListener listener, String backgroundImagePath) {
        this.listener = listener;
        this.backgroundImagePath = backgroundImagePath;

        setBackground(Color.BLACK);
        setFocusable(true);

        loadBackgroundImage();
        setupKeyListener();
        startFadeIn();
    }

    private void loadBackgroundImage() {
        if (backgroundImagePath != null) {
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(backgroundImagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!keyPressed) {
                    keyPressed = true;
                    if (listener != null) {
                        listener.onSceneFinished();
                    }
                }
            }
        });
    }

    private void startFadeIn() {
        fadeInTimer = new Timer(33, e -> {
            alpha += 0.04f;
            if (alpha >= 1f) {
                alpha = 1f;
                fadeInTimer.stop();
            }
            repaint();
        });

        SwingUtilities.invokeLater(() -> {
            requestFocusInWindow();
            fadeInTimer.start();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 40, 0, getWidth(), getHeight(), this);
        }

        // Title fade-in
        SceneToolkit.Fade.text(
            g2d,
            titleText,
            FontManager.LARGE_TITLE_FONT,
            Color.WHITE,
            alpha,
            (getWidth() - g2d.getFontMetrics(FontManager.LARGE_TITLE_FONT).stringWidth(titleText)) / 2,
            getHeight() / 2 - 120
        );

        // Prompt text
        SceneToolkit.Draw.centeredText(
            g2d,
            promptText,
            FontManager.PROMPT_FONT,
            Color.GRAY,
            getWidth(),
            getHeight() - 50
        );

        g2d.dispose();
    }
}
