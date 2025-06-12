public class SceneFourPanel extends StoryScenePanel {

    public SceneFourPanel(SceneListener listener) {
        super(listener, "SCENE 4: The Brutalist Building", new SceneMoment[] {
            new SceneMoment(
                "You descend into a cavernous lobby made of nothing but concrete.\nNo windows.",
                "Everything echoes. Even your thoughts.",
                null, null,
                "/assets/images/scene4_1.png",
                null
            ),
            new SceneMoment(
                "The walls are smooth, but the cracks in them are deliberate.\nLike someone tried to repair something already broken.",
                "You walk.",
                null, null,
                "/assets/images/scene4_1.png",
                null
            ),
            new SceneMoment(
                "Eventually, you see a bench.\nSomeone is sitting on it.",
                "It's them.",
                null, null,
                "/assets/images/scene4_1.png",
                null
            ),
            new SceneMoment(
                "You know who they are.\nYou don't need their face to remember.",
                "They look up, but don't smile.",
                null, null,
                "/assets/images/scene4_1.png",
                null
            ),
            new SceneMoment(
                "\"It's been a while,\" they say. \"Feels like the silence got heavier.\"",
                "You sit next to them.",
                null, null,
                "/assets/images/scene4_1.png",
                null
            ),
            new SceneMoment(
                "\"I still think about it,\" they continue. \"Not what happened. But what it meant.\"",
                "You don't respond. Not yet.",
                null, null,
                "/assets/images/black.jpg",
                null
            ),
            new SceneMoment(
                "\"So… tell me.\"\n\"What did you break?\"",
                "\"???\"",
                "What did you break?",
                new String[] { "Trust", "Distance" }
            )
        });
    }

    @Override
    protected void onChoiceMade(String choice) {
        if (choice.equalsIgnoreCase("Trust")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I did something.", "They couldn't forgive."),
                new SceneMoment("\"I wanted to believe you.\"", "They look away."),
                new SceneMoment("\"We built this place together.\"", "Takes one to shatter the foundation."),
                new SceneMoment("A door appears in the wall. Concrete grinds open, painfully slow.", "And the concrete begins to dissolve.")
            };
        } else if (choice.equalsIgnoreCase("Distance")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I let it all drift.", "I didn't try hard enough to hold on."),
                new SceneMoment("They nod, slowly.", "The air grows colder."),
                new SceneMoment("They reach into their coat and pull out a photograph.", "It's just a blur."),
                new SceneMoment("A door appears in the wall. Concrete grinds open, painfully slow.", "And the concrete begins to dissolve.")
            };
        }

        resetAfterChoice();
    }
}
