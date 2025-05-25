package controller;

import model.Game;
import view.MenuView;
import view.SettingsView;
import javax.swing.*;

public class MenuController {
    private MenuView menuView;
    private MainController mainController;
    private SettingsView settingsView;
    private SettingsController settingsController;

    public MenuController(MenuView menuView, MainController mainController, Game game) {
        this.menuView = menuView;
        settingsView = new SettingsView();
        this.mainController = mainController;

        menuView.getSoloGameButton().addActionListener(e -> mainController.startGame());
        //menuView.getCoopGameButton().addActionListener(e -> JOptionPane.showMessageDialog(menuView, "Co-op mode not implemented yet."));
        menuView.getSettingsButton().addActionListener(e -> openSettings());
        menuView.getExitButton().addActionListener(e -> System.exit(0));
        menuView.getCoopGameButton().addActionListener(e -> {
            String[] options = {"Host", "Join"};
            int choice = JOptionPane.showOptionDialog(menuView, "Choose mode", "Co-op Game",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (choice == 0) { // Host
                mainController.startHost();
            } else if (choice == 1) { // Join
                String hostIp = JOptionPane.showInputDialog(menuView, "Enter host IP:");
                if (hostIp != null && !hostIp.trim().isEmpty()) {
                    mainController.startClient(hostIp);
                }
            }
        });

    }

    private void openSettings() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(menuView);
        parentFrame.setVisible(false); // Скрыть меню

        JDialog settingsDialog = new JDialog(parentFrame, "Settings", true);
        settingsDialog.setContentPane(settingsView);
        settingsDialog.pack();
        settingsDialog.setLocationRelativeTo(parentFrame);

        settingsController = new SettingsController(settingsView, parentFrame); // Инициализация контроллера

        settingsDialog.setVisible(true);
    }
}