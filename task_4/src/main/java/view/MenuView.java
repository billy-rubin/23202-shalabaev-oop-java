package view;

import controller.ButtonType;
import controller.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuView extends JPanel {
    private MenuButton soloGameButton;
    private MenuButton coopGameButton;
    private MenuButton settingsButton;
    private MenuButton exitButton;
    private Image backgroundImage;

    public MenuView() {
        URL imageURL = getClass().getResource("/images/loadingScreen.png");
        backgroundImage = new ImageIcon(imageURL).getImage();

        // Set layout
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1525, 789)); // Match game window size
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 20, 0, 20); // Padding between buttons
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.weighty = 0;
        add(Box.createVerticalStrut(165), gbc);

        soloGameButton = new MenuButton(ButtonType.singleGame);
        coopGameButton = new MenuButton(ButtonType.coopGame);
        settingsButton = new MenuButton(ButtonType.settings);
        exitButton = new MenuButton(ButtonType.exit);

        Dimension largeButtonSize = new Dimension(769, 150);
        Dimension smallButtonSize = new Dimension(350, 120);
        soloGameButton.setPreferredSize(largeButtonSize);
        coopGameButton.setPreferredSize(largeButtonSize);
        settingsButton.setPreferredSize(smallButtonSize);
        exitButton.setPreferredSize(smallButtonSize);

        gbc.insets = new Insets(0, 20, 20, 0); // No top inset for first button
        gbc.gridy = 1;
        add(soloGameButton, gbc);
        gbc.insets = new Insets(0, 20, 0, 0); // Restore top inset for subsequent buttons
        gbc.gridy = 2;
        add(coopGameButton, gbc);
        gbc.gridy = 3;
        add(settingsButton, gbc);
        gbc.insets = new Insets(0, 20, 20, 20);
        gbc.gridy = 4;
        add(exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public MenuButton getSoloGameButton() { return soloGameButton; }
    public MenuButton getCoopGameButton() { return coopGameButton; }
    public MenuButton getSettingsButton() { return settingsButton; }
    public MenuButton getExitButton() { return exitButton; }
}