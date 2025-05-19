// cia centralizuot tuos sriftus... "garimond".

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public abstract class StoryScenePanel extends BaseScenePanel {

    private SceneMoment[] moments;
    protected SceneMoment[] overrideMoments = null;

    protected int currentBlock = 0;
    protected float storyAlpha = 0f;
    private float continueAlpha = 0f;
    protected boolean showingContinue = false;
    protected boolean showingStory = true;

    private Timer storyFadeTimer;
    private Timer continueFadeTimer;

    protected boolean showingChoices = false;
    private float choiceAlpha = 0f;
    private Timer choiceFadeTimer;
    private boolean waitingForChoice = false;

    private Graphics cachedGraphics;


    public StoryScenePanel(SceneListener listener, String sceneTitle, SceneMoment[] moments) {
    super(listener, sceneTitle);
    this.moments = moments;
    }

    private SceneMoment[] getActiveMoments() {
        return overrideMoments != null ? overrideMoments : moments;
    }

    public void mouseClicked(MouseEvent e) {
        if (showingContinue) {
            if (!isPromptClicked(e)) return;

            if (currentBlock < getActiveMoments().length - 1) {
                currentBlock++;
                storyAlpha = 0f;
                continueAlpha = 0f;
                showingContinue = false;
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

            Font font = new Font("Garamond", Font.PLAIN, 28);
            int choiceY = getHeight() / 2 + 20;

            int leftCenterX = getWidth() / 4;
            int rightCenterX = (getWidth() * 3) / 4;

            Rectangle leftBounds = SceneToolkit.getCenteredTextBounds(cachedGraphics, moment.finalChoices[0], font, leftCenterX, choiceY);
            Rectangle rightBounds = SceneToolkit.getCenteredTextBounds(cachedGraphics, moment.finalChoices[1], font, rightCenterX, choiceY);

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
        storyFadeTimer = new Timer(50, e -> {
            storyAlpha += 0.02f;
            if (storyAlpha >= 1f) {
                storyAlpha = 1f;
                storyFadeTimer.stop();

                if (currentBlock < getActiveMoments().length && getActiveMoments()[currentBlock].continuationPrompt != null) {
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

    @Override
    protected void paintScene(Graphics2D g2d) {
        this.cachedGraphics = g2d;

        SceneMoment moment = getActiveMoments()[currentBlock];

        if (showingStory && moment.storyText != null) {
            SceneToolkit.fadeInStoryText(
                g2d,
                moment.storyText,
                new Font("Garamond", Font.PLAIN, 48),
                Color.LIGHT_GRAY,
                storyAlpha,
                getWidth(),
                getHeight()
            );
        }

        if (showingContinue && moment.continuationPrompt != null) {
            SceneToolkit.drawClickableText(
                g2d,
                moment.continuationPrompt,
                new Font("Garamond", Font.ITALIC, 32),
                Color.WHITE,
                continueAlpha,
                getWidth(),
                getHeight() - 400
            );
        }

        if (showingChoices && moment.finalQuestion != null && moment.finalChoices != null) {
            SceneToolkit.drawCenteredText(
                g2d,
                moment.finalQuestion,
                new Font("Garamond", Font.BOLD, 36),
                Color.WHITE,
                getWidth(),
                getHeight() / 2 - 100
            );

            SceneToolkit.drawSideBySideChoices(
                g2d,
                moment.finalChoices[0],
                moment.finalChoices[1],
                new Font("Garamond", Font.PLAIN, 28),
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
        showingStory = false; // 👈 Hide the story block
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
        System.out.println("User chose: " + choice);

        // Default behavior: end the scene
        if (listener != null) {
            listener.onSceneFinished();
        }
    }

    private boolean isPromptClicked(MouseEvent e) {
        SceneMoment moment = getActiveMoments()[currentBlock];
        if (moment.continuationPrompt == null) return false;

        String prompt = moment.continuationPrompt;
        Font font = new Font("Garamond", Font.ITALIC, 32);
        FontMetrics fm = getFontMetrics(font);

        int centerX = getWidth() / 2;
        int promptY = getHeight() - 400;

        Rectangle bounds = SceneToolkit.getCenteredTextBounds(getGraphics(), prompt, font, centerX, promptY);
        return bounds.contains(e.getX(), e.getY());
    }


    @Override
        protected void onTitleFadeComplete() {
            startStoryFadeIn();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    StoryScenePanel.this.mouseClicked(e);
                }
            });
        }

}
