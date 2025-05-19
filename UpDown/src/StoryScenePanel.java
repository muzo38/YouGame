//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

// A massive class that tries to handle everything about story scenes because I hate modular code.
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

    private Graphics cachedGraphics;

// Constructor that takes more parameters than I wanted to type.
    public StoryScenePanel(SceneListener listener, String sceneTitle, SceneMoment[] moments) {
        super(listener, sceneTitle);
        this.moments = moments;
    }

// Returns active moments because we need to overcomplicate everything.
    private SceneMoment[] getActiveMoments() {
        return overrideMoments != null ? overrideMoments : moments;
    }

// Handles mouse clicks but only cares about specific areas.
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

            int choiceY = getHeight() / 2 + 20;
            int leftCenterX = getWidth() / 4;
            int rightCenterX = (getWidth() * 3) / 4;

            Rectangle leftBounds = SceneToolkit.getCenteredTextBounds(cachedGraphics, moment.finalChoices[0], 
                FontManager.CHOICE_FONT, leftCenterX, choiceY);
            Rectangle rightBounds = SceneToolkit.getCenteredTextBounds(cachedGraphics, moment.finalChoices[1], 
                FontManager.CHOICE_FONT, rightCenterX, choiceY);

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

// Starts the story fade in animation because static text isn't dramatic enough.
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

// Starts the continue prompt fade in because one fade animation wasn't enough.
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

// Paints the scene with too many conditional statements.
    @Override
    protected void paintScene(Graphics2D g2d) {
        this.cachedGraphics = g2d;

        SceneMoment moment = getActiveMoments()[currentBlock];

        if (showingStory && moment.storyText != null) {
            SceneToolkit.fadeInStoryText(
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
            SceneToolkit.drawClickableText(
                g2d,
                moment.continuationPrompt,
                FontManager.CONTINUE_FONT,
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
                FontManager.QUESTION_FONT,
                Color.WHITE,
                getWidth(),
                getHeight() / 2 - 100
            );

            SceneToolkit.drawSideBySideChoices(
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

// Starts another fade in animation for choices because we can't get enough of those.
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

// Called when a choice is made but I'll probably override it anyway.
    protected void onChoiceMade(String choice) {
        System.out.println("User chose: " + choice);

        // Default behavior: end the scene
        if (listener != null) {
            listener.onSceneFinished();
        }
    }

// Checks if the prompt was clicked with unnecessarily complex math.
    private boolean isPromptClicked(MouseEvent e) {
        SceneMoment moment = getActiveMoments()[currentBlock];
        if (moment.continuationPrompt == null) return false;

        String prompt = moment.continuationPrompt;
        Rectangle bounds = SceneToolkit.getCenteredTextBounds(getGraphics(), prompt, 
            FontManager.CONTINUE_FONT, getWidth() / 2, getHeight() - 400);
        return bounds.contains(e.getX(), e.getY());
    }

// Called when the title fade is complete because we can't just do everything at once.
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