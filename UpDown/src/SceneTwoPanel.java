public class SceneTwoPanel extends StoryScenePanel {

    public SceneTwoPanel(SceneListener listener) {
        super(listener, "SCENE 2: The School Hallway", new SceneMoment[] {
            new SceneMoment(
                "You recognize it instantly.\nThere's a sticker peeling off the side.\nA lock with no combination.",
                "A note stuck in the slit.",
                null, null,
                "/assets/images/scene2_1.jpg",
                null
            ),
            new SceneMoment(
                "You pull it out. It's your handwriting, but the words don't make sense.\nScribbles, diagrams, half-finished sentences.",
                "Suddenly, the silence shatters.",
                null, null,
                "/assets/images/scene2_1.jpg",
                null
            ),
            new SceneMoment(
                "The bell rings once. But no one comes.\nOnly footsteps, echoing. Faster than yours.",
                "You start walking.",
                null, null,
                "/assets/images/scene2_1.jpg",
                null
            ),
            new SceneMoment(
                "Every locker whispers as you pass.",
                "\"Were you enough?\"",
                null, null,
                "/assets/images/scene2_2.jpg",
                null
            ),
            new SceneMoment(
                "\"You should've spoken up.\"",
                "You stop at a fork in the hallway. Two doors.",
                null, null,
                "/assets/images/scene2_2.jpg",
                null
            ),
            new SceneMoment(
                "One reads: \"Top of the Class.\"\nThe other: \"Didn't Even Show Up.\"",
                "You hesitate.",
                null, null,
                "/assets/images/scene2_2.jpg",
                null
            ),
            new SceneMoment(
                "\"Everyone wanted something from you.\nWhat did you do with the weight?\"",
                "\"???\"",
                "How did you deal with pressure?",
                new String[] { "Performer", "Avoider" }
            )
        });
    }

    @Override
    protected void onChoiceMade(String choice) {
        if (choice.equalsIgnoreCase("Performer")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I wore masks.\nI said what they wanted to hear", "I worked until I cracked."),
                new SceneMoment("The door to \"Top of the Class\" creaks open.\nA classroom, every desk filled by people with your face.", "They're all writing."),
                new SceneMoment("One of them looks up and says:\n\"If we stop, they'll know we're empty.\"", "The chalkboard reads: \"WHO ARE YOU WITHOUT PRAISE?\""),
                new SceneMoment("The hallway begins to collapse inward, lockers twisting like bones breaking.\nYou run", "It opens into warm, flickering light.")
            };
        } else if (choice.equalsIgnoreCase("Avoider")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I stopped caring.\nI avoided the test.", "If I didn't try, I couldn't fail."),
                new SceneMoment("The \"Didn't Even Show Up\" door leads to an auditorium.\nYour name is written on a spotlight, but it never turns on.", "Lights off. Empty stage."),
                new SceneMoment("In the silence, someone from the dark says:\n\"They stopped asking. Eventually, even you stopped asking.\"", "A single chair lies on its side."),
                new SceneMoment("The hallway begins to collapse inward, lockers twisting like bones breaking.\nYou run", "It opens into warm, flickering light.")
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