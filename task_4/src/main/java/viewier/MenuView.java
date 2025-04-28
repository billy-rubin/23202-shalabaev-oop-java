package viewier;

import javax.swing.*;

import controller.MenuButton;

import java.awt.*;

public class MenuView extends JPanel {
    private MenuButton soloGameButton;
    private MenuButton exitButton;
    private MenuButton coopGameButton = new MenuButton("Кооперативная игра");

    public MenuView() {
        setLayout(new GridBagLayout());
        setBackground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        soloGameButton = new MenuButton("Одиночная игра");
        exitButton = new MenuButton("Выход");

        gbc.gridy = 0;
        add(soloGameButton, gbc);
        gbc.gridy = 1;
        add(exitButton, gbc);
    }

    public MenuButton getExitButton(){
        return exitButton;
    }

    public MenuButton getSoloGameButton(){
        return soloGameButton;
    }

    public MenuButton getCoopGameButton(){
        return coopGameButton;
    }
}
