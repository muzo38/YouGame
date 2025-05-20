//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import javax.swing.*;

public class GameWindow extends JFrame {


    public GameWindow() {
        setTitle("You");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        new SceneManager(this); // call SceneManager

        setVisible(true);
    }
}
