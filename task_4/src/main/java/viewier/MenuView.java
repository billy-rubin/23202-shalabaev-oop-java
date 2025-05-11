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
        // Load background image
        URL imageURL = getClass().getResource("/images/loadingScreen.png");
        if (imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            backgroundImage = null;
            setBackground(Color.DARK_GRAY); // Fallback background
        }

        // Set layout
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1525, 789)); // Match game window size
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 20, 20, 20); // Padding between buttons
        gbc.anchor = GridBagConstraints.CENTER;

        // Add vertical spacer to reserve space for title (0 to 165)
        gbc.gridy = 0;
        gbc.weighty = 0; // No extra vertical space
        add(Box.createVerticalStrut(165), gbc);

        // Initialize buttons
        soloGameButton = new MenuButton(ButtonType.singleGame);
        coopGameButton = new MenuButton(ButtonType.coopGame);
        exitButton = new MenuButton(ButtonType.exit);

        // Set button sizes
        Dimension largeButtonSize = new Dimension(769, 190);
        Dimension smallButtonSize = new Dimension(350, 150);
        soloGameButton.setPreferredSize(largeButtonSize);
        coopGameButton.setPreferredSize(largeButtonSize);
        exitButton.setPreferredSize(smallButtonSize);

        // Add buttons to layout
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