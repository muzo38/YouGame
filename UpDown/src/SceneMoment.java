//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

// A data class, maybe better a record?
public class SceneMoment {
    public String storyText;
    public String continuationPrompt;
    public String finalQuestion; // optional
    public String[] finalChoices; // optional
    public String backgroundImagePath; // optional (future)
    public String ambientMusicPath;    // optional (future)

// Basic constructor for when not providing choices.
    public SceneMoment(String storyText, String continuationPrompt) {
        this.storyText = storyText;
        this.continuationPrompt = continuationPrompt;
    }

// Constructor for when you actually want the player to make a decision.
    public SceneMoment(String storyText, String continuationPrompt, String finalQuestion, String[] finalChoices) {
        this(storyText, continuationPrompt);
        this.finalQuestion = finalQuestion;
        this.finalChoices = finalChoices;
    }

// Extended constructor added for future features.
    public SceneMoment(String storyText, String continuationPrompt, String finalQuestion, String[] finalChoices, String imagePath, String musicPath) {
        this(storyText, continuationPrompt, finalQuestion, finalChoices);
        this.backgroundImagePath = imagePath;
        this.ambientMusicPath = musicPath;
    }
}
