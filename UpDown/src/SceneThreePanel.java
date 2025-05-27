public class SceneThreePanel extends StoryScenePanel {

    public SceneThreePanel(SceneListener listener) {
        super(listener, "SCENE 3: The Family Room", new SceneMoment[] {
            new SceneMoment(
                "You step into a living room lit only by a television.\nEverything is familiar, but...",
                "Off.",
                null, null,
                "/assets/images/scene3_1.jpg",
                null
            ),
            new SceneMoment(
                "The couch is your old couch. The wallpaper?\nSlightly faded but exactly how you remember.",
                "The clock ticks, but its hands move backwards.",
                null, null,
                "/assets/images/scene3_1.jpg",
                null
            ),
            new SceneMoment(
                "On the TV: home videos.",
                "You.",
                null, null,
                "/assets/images/scene3_1.jpg",
                null
            ),
            new SceneMoment(
                "At different ages. Laughing. Crying.\nWinning something. Losing something.",
                "You take a seat.",
                null, null,
                "/assets/images/scene3_2.jpg",
                null
            ),
            new SceneMoment(
                "The remote is in your hand before you realize you reached for it.\nEvery time you change the channel, the scene becomes more personal.",
                "A fight. A hug.",
                null, null,
                "/assets/images/scene3_2.jpg",
                null
            ),
            new SceneMoment(
                "A moment where your voice cracks and you storm out.\n",
                "You try to look away.",
                null, null,
                "/assets/images/scene3_2.jpg",
                null
            ),
            new SceneMoment(
                "\"When you hurt…\nHow did you let it out?\"",
                "\"???\"",
                "How did you express emotion?",
                new String[] { "Vocal", "Silent" }
            )
        });
    }

    @Override
    protected void onChoiceMade(String choice) {
        if (choice.equalsIgnoreCase("Vocal")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I said everything.", "I screamed when it burned."),
                new SceneMoment("The soundbar on the TV distorts your own voice returns in loops.\nFighting. Apologizing. Screaming into a pillow.", "\"They only listened when I broke.\""),
                new SceneMoment("The room begins to vibrate—like it remembers.", "You drop the remote."),
                new SceneMoment("The screen goes to static and then to black.\nA door opens, not in the wall, but in the floor.", "You descend.")
            };
        } else if (choice.equalsIgnoreCase("Silent")) {
            overrideMoments = new SceneMoment[] {
                new SceneMoment("I bottled it.", "I didn't want to make noise."),
                new SceneMoment("The TV mutes. The visuals keep playing, but you hear nothing.\nSubtitles appear:", "\"I'm fine.\""),
                new SceneMoment("You see yourself mouthing it, every year.", "Older each time."),
                new SceneMoment("The screen goes to static and then to black.\nA door opens, not in the wall, but in the floor.", "You descend.")
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