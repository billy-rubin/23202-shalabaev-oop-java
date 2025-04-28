package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static controller.SwingConsole.run;

public class MenuButton extends JButton {
    private JButton button;
    private JTextField txt = new JTextField(10);
    private ButtonListener listener = new ButtonListener();

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = ((JButton)e.getSource()).getText();
            txt.setText(name + " was pressed");
        }
    }

    public MenuButton(String text) {
        button = new JButton(text);
        button.addActionListener(listener);
        setLayout(new FlowLayout());
        add(button);
        add(txt);
    }
}
