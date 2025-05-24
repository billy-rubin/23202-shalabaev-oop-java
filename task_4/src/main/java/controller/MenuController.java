package controller;

import model.Game;
import view.MenuView;
import view.SettingsView;
import javax.swing.*;

public class MenuController {
    private MenuView menuView;
    private MainController mainController;
    private Game game;// Assume Game model is passed or accessible
    private SettingsView settingsView;

    public MenuController(MenuView menuView, MainController mainController, Game game) {
        this.menuView = menuView;
        settingsView = new SettingsView(game);
        this.mainController = mainController;
        this.game = game;

        menuView.getSoloGameButton().addActionListener(e -> mainController.startGame());
        menuView.getCoopGameButton().addActionListener(e -> JOptionPane.showMessageDialog(menuView, "Co-op mode not implemented yet."));
        menuView.getSettingsButton().addActionListener(e -> openSettings());
        menuView.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void openSettings() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(menuView);
        parentFrame.setVisible(false); // Скрыть меню

        JDialog settingsDialog = new JDialog(parentFrame, "Settings", true);
        settingsDialog.setContentPane(settingsView);
        settingsDialog.pack();
        settingsDialog.setLocationRelativeTo(parentFrame);

        new SettingsController(settingsView, game, parentFrame); // Инициализация контроллера

        settingsDialog.setVisible(true);
    }
}