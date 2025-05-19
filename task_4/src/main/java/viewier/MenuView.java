package viewier;

import controller.ButtonType;
import controller.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuView extends JPanel {
    private MenuButton soloGameButton;
    private MenuButton coopGameButton;
    private MenuButton exitButton;
    private Image backgroundImage;

    public MenuView() {
        URL imageURL = getClass().getResource("/images/loadingScreen.png");
        if (imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            backgroundImage = null;
            setBackground(Color.DARK_GRAY);
        }

        // Set layout
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1525, 789)); // Match game window size
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 20, 20, 20); // Padding between buttons
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.weighty = 0;
        add(Box.createVerticalStrut(165), gbc);

        soloGameButton = new MenuButton(ButtonType.singleGame);
        coopGameButton = new MenuButton(ButtonType.coopGame);
        exitButton = new MenuButton(ButtonType.exit);

        Dimension largeButtonSize = new Dimension(769, 190);
        Dimension smallButtonSize = new Dimension(350, 150);
        soloGameButton.setPreferredSize(largeButtonSize);
        coopGameButton.setPreferredSize(largeButtonSize);
        exitButton.setPreferredSize(smallButtonSize);

        gbc.insets = new Insets(0, 20, 20, 0); // No top inset for first button
        gbc.gridy = 1;
        add(soloGameButton, gbc);
        gbc.insets = new Insets(20, 20, 0, 0); // Restore top inset for subsequent buttons
        gbc.gridy = 2;
        add(coopGameButton, gbc);
        gbc.insets = new Insets(20, 20, 0, 20); // Restore top inset for subsequent buttons
        gbc.gridy = 3;
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
    public MenuButton getExitButton() { return exitButton; }
}