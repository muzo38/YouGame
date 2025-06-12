//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Toolkit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// audio utility
import javax.swing.*;

public abstract class StoryScenePanel extends BaseScenePanel {

    private SceneMoment[] moments;
    protected SceneMoment[] overrideMoments = null;

    protected int currentBlock = 0;
    protected float storyAlpha = 0f;
    private float continueAlpha = 0f;
    protected boolean showingContinue = false;
    protected boolean showingStory = true;

    private boolean promptHovered = false;

    private Clip clickSoundClip = null;
    private static final int CLICK_DELAY_MS = 300;
    private long lastClickTime = 0;

    protected String clickSoundPath = "/assets/sound/click.wav";

    private Timer storyFadeTimer;
    private Timer continueFadeTimer;

    protected boolean showingChoices = false;
    private float choiceAlpha = 0f;
    private Timer choiceFadeTimer;

    private Graphics cachedGraphics;

    private String currentBackgroundImagePath = null;
    private Image backgroundImage = null;

    private float backgroundAlpha = 0f;
    private Timer backgroundFadeTimer;
    private boolean backgroundFadedIn = false;
    private String currentMusicPath = null;

    public StoryScenePanel(SceneListener listener, String sceneTitle, SceneMoment[] moments) {
        super(listener, sceneTitle);
        this.moments = moments;
        this.backgroundAlpha = 0f;
        this.backgroundFadedIn = false;
        setClickSound(clickSoundPath);
    }

    private SceneMoment[] getActiveMoments() {
        return overrideMoments != null ? overrideMoments : moments;
    }

    private void loadBackgroundImage() {
        SceneMoment moment = getActiveMoments()[currentBlock];
        
        if (moment.backgroundImagePath != null && !moment.backgroundImagePath.equals(currentBackgroundImagePath)) {
            currentBackgroundImagePath = moment.backgroundImagePath;
            
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(currentBackgroundImagePath));
            } catch (Exception e) {
                backgroundImage = null;
            }
        }

        if (!backgroundFadedIn) {
            startBackgroundFadeIn();
        }

        updateMusic();
    }

    private void updateMusic() {
        SceneMoment moment = getActiveMoments()[currentBlock];
        if (moment.ambientMusicPath != null && !moment.ambientMusicPath.equals(currentMusicPath)) {
            currentMusicPath = moment.ambientMusicPath;
            SoundManager.playMusicLoop(currentMusicPath);
        } else if (moment.ambientMusicPath == null && currentMusicPath != null) {
            currentMusicPath = null;
            SoundManager.stopMusic();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (backgroundImage != null) {
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundAlpha));
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g2d.setComposite(originalComposite);
        }

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

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) return;

        long now = System.currentTimeMillis();
        if (now - lastClickTime < CLICK_DELAY_MS) return;
        lastClickTime = now;

        playClickSound();
        if (showingContinue) {
            if (!isPromptClicked(e)) return;

            if (currentBlock < getActiveMoments().length - 1) {
                currentBlock++;
                storyAlpha = 0f;
                continueAlpha = 0f;
                showingContinue = false;
                loadBackgroundImage();
                repaint();
                startStoryFadeIn();
            } else {
                SceneMoment moment = getActiveMoments()[currentBlock];
                if (moment.finalQuestion != null && moment.finalChoices != null && moment.finalChoices.length == 2) {
                    startChoiceFadeIn();
                } else {
                    if (listener != null) {
                        listener.onSceneFinished();
                    }
                }
            }
        }

        if (showingChoices) {
            SceneMoment moment = getActiveMoments()[currentBlock];
            if (moment.finalChoices == null || moment.finalChoices.length != 2) return;

                int mouseX = e.getX();
                int mouseY = e.getY();

                int choiceY = getHeight() / 2 + 20;
                int leftCenterX = getWidth() / 4;
                int rightCenterX = (getWidth() * 3) / 4;

            Rectangle leftBounds = SceneToolkit.Measure.getCenteredTextBounds(
                cachedGraphics,
                moment.finalChoices[0],
                FontManager.CHOICE_FONT,
                leftCenterX,
                choiceY
            );

            Rectangle rightBounds = SceneToolkit.Measure.getCenteredTextBounds(
                cachedGraphics,
                moment.finalChoices[1],
                FontManager.CHOICE_FONT,
                rightCenterX,
                choiceY
            );

            if (leftBounds.contains(mouseX, mouseY)) {
                onChoiceMade(moment.finalChoices[0]);
                return;
            }
            if (rightBounds.contains(mouseX, mouseY)) {
                onChoiceMade(moment.finalChoices[1]);
                return;
            }
        }
    }

    protected void startStoryFadeIn() {
        SceneMoment moment = getActiveMoments()[currentBlock];

        storyFadeTimer = new Timer(50, e -> {
            storyAlpha += 0.02f;
            if (storyAlpha >= 1f) {
                storyAlpha = 1f;
                storyFadeTimer.stop();

                if (moment.continuationPrompt != null) {
                    startContinueFadeIn();
                }
            }
            repaint();
        });

        storyFadeTimer.start();
    }

    private void startContinueFadeIn() {
        showingContinue = true;
        continueFadeTimer = new Timer(50, e -> {
            continueAlpha += 0.03f;
            if (continueAlpha >= 1f) {
                continueAlpha = 1f;
                continueFadeTimer.stop();
            }
            repaint();
        });
        continueFadeTimer.start();
    }

    private void startBackgroundFadeIn() {
        backgroundAlpha = 0f;
        backgroundFadeTimer = new Timer(50, e -> {
            backgroundAlpha += 0.03f;
            if (backgroundAlpha >= 1f) {
                backgroundAlpha = 1f;
                backgroundFadeTimer.stop();
                backgroundFadedIn = true;
            }
            repaint();
        });
        backgroundFadeTimer.start();
    }

    protected void paintScene(Graphics2D g2d) {
        this.cachedGraphics = g2d;
        SceneMoment moment = getActiveMoments()[currentBlock];

        if (showingStory && moment.storyText != null) {
            SceneToolkit.Fade.storyText(
                g2d,
                moment.storyText,
                FontManager.STORY_FONT,
                Color.LIGHT_GRAY,
                storyAlpha,
                getWidth(),
                getHeight()
            );
        }

        if (showingContinue && moment.continuationPrompt != null) {
            SceneToolkit.Draw.clickableText(
                g2d,
                moment.continuationPrompt,
                FontManager.CONTINUE_FONT,
                Color.WHITE,
                continueAlpha,
                getWidth(),
                getHeight() - 400,
                promptHovered
            );
        }

        if (showingChoices && moment.finalQuestion != null && moment.finalChoices != null) {
            SceneToolkit.Draw.centeredText(
                g2d,
                moment.finalQuestion,
                FontManager.QUESTION_FONT,
                Color.WHITE,
                getWidth(),
                getHeight() / 2 - 100
            );

            SceneToolkit.Draw.sideBySideChoices(
                g2d,
                moment.finalChoices[0],
                moment.finalChoices[1],
                FontManager.CHOICE_FONT,
                Color.LIGHT_GRAY,
                choiceAlpha,
                getWidth(),
                getHeight() / 2 + 20
            );
        }
    }

    private void startChoiceFadeIn() {
        showingChoices = true;
        showingContinue = false;
        showingStory = false;
        choiceAlpha = 0f;

        choiceFadeTimer = new Timer(50, e -> {
            choiceAlpha += 0.03f;
            if (choiceAlpha >= 1f) {
                choiceAlpha = 1f;
                choiceFadeTimer.stop();
            }
            repaint();
        });

        choiceFadeTimer.start();
    }

    protected void onChoiceMade(String choice) {
        if (listener != null) {
            listener.onSceneFinished();
        }
    }

    protected void resetAfterChoice() {
        currentBlock = 0;
        showingChoices = false;
        showingContinue = false;
        showingStory = true;
        storyAlpha = 0f;
        loadBackgroundImage();
        repaint();
        startStoryFadeIn();
    }

    private boolean isPromptClicked(MouseEvent e) {
        SceneMoment moment = getActiveMoments()[currentBlock];
        if (moment.continuationPrompt == null) return false;

        return getPromptBounds().contains(e.getX(), e.getY());
    }

    private Rectangle getPromptBounds() {
        SceneMoment moment = getActiveMoments()[currentBlock];
        if (moment.continuationPrompt == null) return new Rectangle();

        Font font = promptHovered
            ? FontManager.CONTINUE_FONT.deriveFont((float)FontManager.CONTINUE_FONT.getSize() + 4)
            : FontManager.CONTINUE_FONT;

        return SceneToolkit.Measure.getCenteredTextBounds(
            getGraphics(),
            moment.continuationPrompt,
            font,
            getWidth() / 2,
            getHeight() - 400
        );
    }

    protected void setClickSound(String resourcePath) {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                getClass().getResource(resourcePath)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clickSoundClip = clip;
        } catch (Exception e) {
            clickSoundClip = null;
        }
    }

    private void playClickSound() {
        if (clickSoundClip != null) {
            if (clickSoundClip.isRunning()) {
                clickSoundClip.stop();
            }
            clickSoundClip.setFramePosition(0);
            clickSoundClip.start();
        }
    }

    public void mouseMoved(MouseEvent e) {
        boolean hover = showingContinue && getPromptBounds().contains(e.getX(), e.getY());
        if (hover != promptHovered) {
            promptHovered = hover;
            setCursor(Cursor.getPredefinedCursor(hover ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
            repaint();
        }
    }

    @Override
    protected void onTitleFadeComplete() {
        showingStory = true;
        loadBackgroundImage();
        startStoryFadeIn();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StoryScenePanel.this.mouseClicked(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                StoryScenePanel.this.mouseMoved(e);
            }
        });
    }
}
