public class SceneMoment {
    public String storyText;
    public String continuationPrompt;
    public String finalQuestion; // optional
    public String[] finalChoices; // optional
    public String backgroundImagePath; // optional (future)
    public String ambientMusicPath;    // optional (future)

    public SceneMoment(String storyText, String continuationPrompt) {
        this.storyText = storyText;
        this.continuationPrompt = continuationPrompt;
    }

    public SceneMoment(String storyText, String continuationPrompt, String finalQuestion, String[] finalChoices) {
        this(storyText, continuationPrompt);
        this.finalQuestion = finalQuestion;
        this.finalChoices = finalChoices;
    }

    // Optional extended constructor for media
    public SceneMoment(String storyText, String continuationPrompt, String finalQuestion, String[] finalChoices, String imagePath, String musicPath) {
        this(storyText, continuationPrompt, finalQuestion, finalChoices);
        this.backgroundImagePath = imagePath;
        this.ambientMusicPath = musicPath;
    }
}
