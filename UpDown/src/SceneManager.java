import javax.swing.SwingUtilities;

public class SceneManager {

    private GameWindow window;

    public SceneManager(GameWindow window) {
        this.window = window;
        showTitle();
    }

    public void showTitle() {
        TitlePanel titlePanel = new TitlePanel(() -> {
            showSceneOne();
        });
        window.setContentPane(titlePanel);
        window.revalidate();

        SwingUtilities.invokeLater(() -> titlePanel.requestFocusInWindow());
    }

    public void showSceneOne() {
        SceneOnePanel sceneOne = new SceneOnePanel(() -> {
            showSceneTwo();
        });
        window.setContentPane(sceneOne);
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneOne.requestFocusInWindow());
    }

    public void showSceneTwo() {
        SceneTwoPanel sceneTwo = new SceneTwoPanel(() -> {
            showSceneThree();
        });
        window.setContentPane(sceneTwo);
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneTwo.requestFocusInWindow());
    }

    public void showSceneThree() {
        SceneThreePanel sceneThree = new SceneThreePanel(() -> {
            showSceneFour();
        });
        window.setContentPane(sceneThree);
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneThree.requestFocusInWindow());
    }

    public void showSceneFour() {
        SceneFourPanel sceneFour = new SceneFourPanel(() -> {
            showSceneFive();
        });
        window.setContentPane(sceneFour);
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneFour.requestFocusInWindow());
    }

    public void showSceneFive() {
        SceneFivePanel sceneFive = new SceneFivePanel(() -> {
            showSceneSix();
        });
        window.setContentPane(sceneFive);
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneFive.requestFocusInWindow());
    }

    public void showSceneSix() {
        SceneSixPanel sceneSix = new SceneSixPanel(() -> {
            showTitle(); // go back to title.
        });
        window.setContentPane(sceneSix);
        window.revalidate();

        SwingUtilities.invokeLater(() -> sceneSix.requestFocusInWindow());
    }
}
