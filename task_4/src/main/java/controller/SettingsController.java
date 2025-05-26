package controller;

import model.Game;
import view.SettingsView;

import javax.swing.*;

public class SettingsController {
    private SettingsView settingsView;
    private JFrame parentFrame;
    private static boolean newState = false;

    public SettingsController(SettingsView settingsView, JFrame parentFrame) {
        this.settingsView = settingsView;
        this.parentFrame = parentFrame;

        settingsView.getGodModeButton().addActionListener(e -> {
            newState = !newState;  // Переключаем состояние
            //game.setGodMode(newState);             // Обновляем состояние в игре
            settingsView.updateGodModeButton(newState);  // Обновляем кнопку
        });

        settingsView.getExitButton().addActionListener(e -> {
            parentFrame.setVisible(true); // Показать меню
            SwingUtilities.getWindowAncestor(settingsView).dispose(); // Закрыть настройки
        });
    }

    public static boolean isNewState() {
        return newState;
    }
}