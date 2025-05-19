import javax.swing.*;;

public class GameWindow extends JFrame {

    private SceneManager sceneManager;

    public GameWindow() {
        setTitle("You");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        sceneManager = new SceneManager(this); // call SceneManager

        setVisible(true);
    }
}
