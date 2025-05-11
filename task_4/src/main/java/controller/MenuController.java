package controller;

import viewier.MenuView;

import javax.swing.*;
import java.util.ArrayList;
import javax.swing.*;

public class MenuController {
    private MenuView menuView;
    private MainController mainController;

    public MenuController(MenuView menuView, MainController mainController) {
        this.menuView = menuView;
        this.mainController = mainController;
        menuView.getSoloGameButton().addActionListener(e -> mainController.startGame());
        menuView.getCoopGameButton().addActionListener(e -> JOptionPane.showMessageDialog(menuView, "Co-op mode not implemented yet."));
        menuView.getExitButton().addActionListener(e -> System.exit(0));
    }
}
