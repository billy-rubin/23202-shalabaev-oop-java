package view;

import controller.ButtonType;
import controller.MenuButton;
import controller.SettingsController;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SettingsView extends JPanel {
    private MenuButton godModeButton;  // Изменен тип на MenuButton для консистентности
    private JButton exitButton;
    private Image backgroundImage;

    public SettingsView() {
        // Загрузка фонового изображения
        URL imageURL = getClass().getResource("/images/loadingScreen.png");
        backgroundImage = new ImageIcon(imageURL).getImage();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1525, 789));
        GridBagConstraints gbc = new GridBagConstraints();

        exitButton = new MenuButton(ButtonType.exit);
        godModeButton = new MenuButton(SettingsController.isNewState() ? ButtonType.godModeOn : ButtonType.godModeOff);

        Dimension mediumButtonSize = new Dimension(618, 250);
        Dimension smallButtonSize = new Dimension(350, 150);
        godModeButton.setPreferredSize(mediumButtonSize);
        exitButton.setPreferredSize(smallButtonSize);

        gbc.insets = new Insets(0, 20, 20, 0);
        gbc.gridy = 1;
        add(godModeButton, gbc);
        gbc.insets = new Insets(20, 20, 0, 0);
        gbc.gridy = 2;
        add(exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public AbstractButton getGodModeButton() {
        return godModeButton;
    }

    // Метод для обновления внешнего вида кнопки godMode
    public void updateGodModeButton(boolean isGodModeOn) {
        godModeButton.setType(isGodModeOn ? ButtonType.godModeOn : ButtonType.godModeOff);
        godModeButton.setIcon(new ImageIcon(getClass().getResource("/images/" + (isGodModeOn ? "godModeOn" : "godModeOff") + ".png")));
    }
}