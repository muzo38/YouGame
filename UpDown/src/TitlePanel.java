import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitlePanel extends JPanel {

    private String titleText = "You";
    private String promptText = "Press any key.";

    private float alpha = 0f;
    private Timer fadeInTimer;
    private boolean keyPressed = false;
    private SceneListener listener;

    public TitlePanel(SceneListener listener) {
        this.listener = listener;
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (keyPressed) return;
                keyPressed = true;

                // Use the SceneManager callback instead of System.exit
                if (TitlePanel.this.listener != null) {
                TitlePanel.this.listener.onSceneFinished();
                }
            }
        });

        fadeInTimer = new Timer(33, e -> {
            alpha += 0.04f;
            alpha = Math.min(alpha, 1f);
            repaint();
            if (alpha >= 1f) {
                fadeInTimer.stop();
            }
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

        // Title fade-in
        SceneToolkit.fadeInText(g2d, titleText,
            new Font("Garamond", Font.BOLD, 128),
            Color.WHITE, alpha,
            (getWidth() - g2d.getFontMetrics(new Font("Garamond", Font.BOLD, 128)).stringWidth(titleText)) / 2,
            getHeight() / 2 - 120);

        // Prompt text, always visible
        SceneToolkit.drawCenteredText(g2d, promptText,
            new Font("Garamond", Font.PLAIN, 24),
            Color.GRAY,
            getWidth(),
            getHeight() - 50);

        g2d.dispose();
    }
}
