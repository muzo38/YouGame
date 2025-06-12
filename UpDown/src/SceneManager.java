//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import javax.swing.SwingUtilities;

// A manager that just repeats the same initialization code for each scene.
public class SceneManager {

    private GameWindow window;

// Constructor that immediately forces you to look at the title screen.
    public SceneManager(GameWindow window) {
        this.window = window;
        showTitle();
    }

// Shows the title panel because we need to start somewhere.
    public void showTitle() {
    TitlePanel titlePanel = new TitlePanel(() -> {
        showSceneOne();
    }, "/assets/images/title_bg.png", "/assets/sound/title_music.wav");
    
    window.setContentPane(titlePanel);
    window.revalidate();

    SwingUtilities.invokeLater(() -> titlePanel.requestFocusInWindow());
}


// Shows scene one, what else did you expect?
    public void showSceneOne() {
        SceneOnePanel sceneOne = new SceneOnePanel(() -> {
            showSceneTwo();
        });
        window.setContentPane(sceneOne);
        SoundManager.playEffect("/assets/sound/title_sfx.wav");
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneOne.requestFocusInWindow());
    }

// Shows scene two, because one scene isn't enough for our masterpiece.
    public void showSceneTwo() {
        SceneTwoPanel sceneTwo = new SceneTwoPanel(() -> {
            showSceneThree();
        });
        window.setContentPane(sceneTwo);
        SoundManager.playEffect("/assets/sound/title_sfx.wav");
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneTwo.requestFocusInWindow());
    }

// Shows scene three, the plot get interesting.
    public void showSceneThree() {
        SceneThreePanel sceneThree = new SceneThreePanel(() -> {
            showSceneFour();
        });
        window.setContentPane(sceneThree);
        SoundManager.playEffect("/assets/sound/title_sfx.wav");
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneThree.requestFocusInWindow());
    }

// Shows scene four, halfway there!
    public void showSceneFour() {
        SceneFourPanel sceneFour = new SceneFourPanel(() -> {
            showSceneFive();
        });
        window.setContentPane(sceneFour);
        SoundManager.playEffect("/assets/sound/title_sfx.wav");
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneFour.requestFocusInWindow());
    }

// Shows scene five.
    public void showSceneFive() {
        SceneFivePanel sceneFive = new SceneFivePanel(() -> {
            showSceneSix();
        });
        window.setContentPane(sceneFive);
        SoundManager.playEffect("/assets/sound/title_sfx.wav");
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneFive.requestFocusInWindow());
    }

// Shows scene six, the last one!
    public void showSceneSix() {
        SceneSixPanel sceneSix = new SceneSixPanel(() -> {
            showTitle(); // go back to title.
        });
        window.setContentPane(sceneSix);
        SoundManager.playEffect("/assets/sound/title_sfx.wav");
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneSix.requestFocusInWindow());
    }
}
