package controller;

import model.Game;
import view.SettingsView;

import javax.swing.*;

public class SettingsController {
    private SettingsView settingsView;
    private Game game;
    private JFrame parentFrame;

    public SettingsController(SettingsView settingsView, Game game, JFrame parentFrame) {
        this.settingsView = settingsView;
        this.game = game;
        this.parentFrame = parentFrame;

        settingsView.getGodModeButton().addActionListener(e -> {
            boolean newState = !game.isGodMode();  // Переключаем состояние
            game.setGodMode(newState);             // Обновляем состояние в игре
            settingsView.updateGodModeButton(newState);  // Обновляем кнопку
        });

        settingsView.getExitButton().addActionListener(e -> {
            parentFrame.setVisible(true); // Показать меню
            SwingUtilities.getWindowAncestor(settingsView).dispose(); // Закрыть настройки
        });
    }
}