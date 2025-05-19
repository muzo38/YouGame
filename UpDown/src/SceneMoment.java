//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

// A bloated data class that should have been a record instead.
public class SceneMoment {
    public String storyText;
    public String continuationPrompt;
    public String finalQuestion; // optional
    public String[] finalChoices; // optional
    public String backgroundImagePath; // optional (future)
    public String ambientMusicPath;    // optional (future)

// Basic constructor for when you're too lazy to provide choices.
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

// Extended constructor that I added for future features we'll probably never implement.
    public SceneMoment(String storyText, String continuationPrompt, String finalQuestion, String[] finalChoices, String imagePath, String musicPath) {
        this(storyText, continuationPrompt, finalQuestion, finalChoices);
        this.backgroundImagePath = imagePath;
        this.ambientMusicPath = musicPath;
    }
}