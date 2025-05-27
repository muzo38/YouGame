public class SceneOnePanel extends StoryScenePanel {

    public SceneOnePanel(SceneListener listener) {
        super(listener, "SCENE 1: The Theme Park", new SceneMoment[] {
            new SceneMoment(
                "Rusted gates swing open.\nA sign above reads \"World of Wonder.\"\nPaint is peeling.",
                "The air smells like wet leaves and iron.",
                null, null,
                "/assets/images/scene1_2.jpg",
                null
            ),
            new SceneMoment(
                "Cotton candy machines lie on their sides.\nEmpty ride carts sway in place.",
                "You step through the gates.",
                null, null,
                "/assets/images/scene1_2.jpg",
                null
            ),
            new SceneMoment(
                "Somewhere inside,\ncarousel music plays an off-key chorus.",
                "Ahead, the carousel spins.",
                null, null,
                "/assets/images/scene1_2.jpg",
                null
            ),
            new SceneMoment(
                "On it sits a younger version of you.\nThey're mouthing the words to a song.\nYou remember that song.",
                "You loved it."
            ),
            new SceneMoment(
                "The melody plays over and over.",
                "They don't see you yet."
            ),
            new SceneMoment(
                "\"When you were small… were you kind? Or were you honest?\"",
                "\"???\""
            ),
            new SceneMoment(
                "The mirrors begin to twist your memories.\nEach shows something you don't remember doing.",
                "You step forward.",
                "What kind of child were you?",
                new String[] { "Innocent", "Rebellious" }
            )
        });
    }

    @Override
    protected void onChoiceMade(String choice) {
        if (choice.equalsIgnoreCase("Innocent")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I always tried to do good.", "I wanted to make people proud."),
                new SceneMoment("The young you turns to face you.\nThey smile wide, but their eyes are glassy.\n\"I was happy, right?\" they ask.", "The mirrors crack slightly."),
                new SceneMoment("One mirror shows you holding a trophy.\nAnother shows you crying in secret.", "The carousel slows."),
                new SceneMoment("You take one final look at yourself the version\nthat once was and step off the ride.", "Somewhere, a gate opens.")
            };
        } else if (choice.equalsIgnoreCase("Rebellious")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I didn't follow rules well.", "But I was always real."),
                new SceneMoment("The young you drops something in the dirt.\nA slingshot?\nA diary?", "They laugh."),
                new SceneMoment("\"They always said I was difficult.\"\nThe mirrors show stormy days.", "The carousel slows."),
                new SceneMoment("You take one final look at yourself the version\nthat once was and step off the ride.", "Somewhere, a gate opens.")
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