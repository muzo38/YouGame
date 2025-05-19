//mantas.lamauskas@mif.stud.vu.lt
//šarūnas.gromavičius@mif.stud.vu.lt

import javax.swing.*;

// A glorified container that does almost nothing but takes credit for everything.
public class GameWindow extends JFrame {


// Constructor that does the bare minimum it can get away with.
    public GameWindow() {
        setTitle("You");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        new SceneManager(this); // call SceneManager

        setVisible(true);
    }
}