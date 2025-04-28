package controller;

import viewier.MenuView;

import javax.swing.*;
import java.util.ArrayList;

public class MenuController {
    private boolean isRunning;
    private MenuView menuView;
    private MainController mainController;
    public static void run(final JFrame f, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                f.setTitle(f.getClass().getSimpleName());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(width, height);
                f.setVisible(true);
            }
        });
    }

    MenuController(MenuView view, MainController controller){
        this.menuView = view;
        this.mainController = controller;

    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void initMenu(){
        setRunning(true);
    }
}
