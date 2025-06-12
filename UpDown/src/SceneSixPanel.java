public class SceneSixPanel extends StoryScenePanel {

    public SceneSixPanel(SceneListener listener) {
        super(listener, "SCENE 6: The Data Room", new SceneMoment[] {
            new SceneMoment(
                "White noise.",
                "You open your eyes.",
                null, null,
                "/assets/images/scene6_1.png",
                null
            ),
            new SceneMoment(
                "Floor to ceiling hundreds of monitors, each one showing a moment from before.",
                "The carousel. The hallway. The couch. The concrete bench...",
                null, null,
                "/assets/images/scene6_1.png",
                null
            ),
            new SceneMoment(
                "Some monitors flicker. Some stutter. Some are frozen.",
                "You walk forward.",
                null, null,
                "/assets/images/scene6_1.png",
                null
            ),
            new SceneMoment(
                "There's a terminal in the center of the room.\nAn old computer glowing in a pool of light.",
                "\"Welcome back.\"",
                null, null,
                "/assets/images/scene6_1.png",
                null
            ),
            new SceneMoment(
                "\"Personality Profile: Compiled.\"",
                "\"Final prompt awaiting input.\"",
                null, null,
                "/assets/images/scene6_1.png",
                null
            ),
            new SceneMoment(
                "A keyboard waits under your fingertips.\nYour reflection glows faintly in the black screen.",
                "Words appear, slow.",
                null, null,
                "/assets/images/black.jpg",
                null
            ),
            new SceneMoment(
                "\"What do you believe defines you?\"",
                "\"???\"",
                "What defines you?",
                new String[] { "Change", "Pattern" }
            )
        });
    }

    @Override
    protected void onChoiceMade(String choice) {
        if (choice.equalsIgnoreCase("Change")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I am still becoming.", "The lights dim."),
                new SceneMoment("The screens begin to shut down, one by one.", "\"You chose to leave.\""),
                new SceneMoment("You chose to live.", "I chose to leave."),
                new SceneMoment("YOU decide what happens next.", "And that's it.")
            };
        } else if (choice.equalsIgnoreCase("Pattern")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I've always been this way.", "I never really knew."),
                new SceneMoment("The terminal stays on.\nThe monitors restart. Looping.", "You don't leave."),
                new SceneMoment("You chose to stay.", "I chose to stay."),
                new SceneMoment("But...", "YOU decide what happens next."),
                new SceneMoment("", "And that's it.")
            };
        }

        currentBlock = 0;
        showingChoices = false;
        showingContinue = false;
        showingStory = true;
        storyAlpha = 0f;
        repaint();
        startStoryFadeIn();
    }
}
