package controller;

import javax.swing.*;

public class MenuButton extends JButton {
    private ButtonType type;

    public MenuButton(ButtonType type) {
        this.type = type;
        setIcon(new ImageIcon(getClass().getResource("/images/" + type.toString().toLowerCase() + ".png")));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    public ButtonType getType() { return type; }

    public void setType(ButtonType type) {
        this.type = type;
    }
}
