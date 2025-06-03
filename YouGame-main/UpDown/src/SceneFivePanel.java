public class SceneFivePanel extends StoryScenePanel {

    public SceneFivePanel(SceneListener listener) {
        super(listener, "SCENE 5: The City Street at Dusk", new SceneMoment[] {
            new SceneMoment(
                "Posters line the street. They advertise opportunities.",
                "Jobs, causes, futures.",
                null, null,
                "/assets/images/scene5_1.png",
                null
            ),
            new SceneMoment(
                "All of them are scratched out, weathered.\nLike they were posted for someone else.",
                "You keep walking.",
                null, null,
                "/assets/images/scene5_1.png",
                null
            ),
            new SceneMoment(
                "Eventually, a bus stop.\nA digital board above it scrolls endlessly with lines.",
                "\"Be more.\"",
                null, null,
                "/assets/images/scene5_1.png",
                null
            ),
            new SceneMoment(
                "\"Did you ever know what it was all for?\"",
                "The voice is your own.",
                null, null,
                "/assets/images/scene5_1.png",
                null
            ),
            new SceneMoment(
                "But older. Tired. Curious, not cruel.",
                "You look down the road. Nothing comes.",
                null, null,
                "/assets/images/scene5_1.png",
                null
            ),
            new SceneMoment(
                "The screen above the bench glitches and freezes.",
                "It displays a question.",
                null, null,
                "/assets/images/black.jpg",
                null
            ),
            new SceneMoment(
                "\"Did you ever know what it was all for?\"",
                "\"???\"",
                "Did you have a purpose?",
                new String[] { "Builder", "Drifter" }
            )
        });
    }

    @Override
    protected void onChoiceMade(String choice) {
        if (choice.equalsIgnoreCase("Builder")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I tried to make something that mattered.", "Even if no one noticed"),
                new SceneMoment("The signs along the street flicker.\nOne glows faintly with something you wrote.\nA poem? A message? A post-it note memory.", "You don't remember writing it. But it's yours."),
                new SceneMoment("\"Someone saw it. Maybe just once. That was enough.\"", "A bus arrives. No driver. No destination."),
                new SceneMoment("You get on.", "You arrive at the Data Room.")
            };
        } else if (choice.equalsIgnoreCase("Drifter")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I went with it. I did what I had to.", "I never really knew."),
                new SceneMoment("The lights go out.\nThe street resets billboards and signs now showing people's names.", "None of them are yours."),
                new SceneMoment("\"That was enough, too,\"\n\"Just surviving. That counts.\"", "A bus arrives. No driver. No destination."),
                new SceneMoment("You get on.", "You arrive at the Data Room.")
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