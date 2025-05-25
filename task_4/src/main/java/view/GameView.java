package view;

import model.Game;
import model.Obstacle;
import model.entities.Movable;
import model.entities.Sprite;
import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private Game game;
    private Image background;
    private HUDView hud;

    public GameView(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(1525, 789));
        background = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        hud = new HUDView(game);
        setLayout(null);
        hud.setBounds(10, 10, 200, 100);
        add(hud);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(background, 0, 0, this);

        for (Movable movable : game.getMovables()) {
            if (((Sprite) movable).isVisible()){
                graphics2D.drawImage(((Sprite) movable).getImage(), ((Sprite) movable).getX(), ((Sprite) movable).getY(), ((Sprite) movable).getWidth(), ((Sprite) movable).getHeight(), this);
            }
        }

        for (Obstacle obstacle : game.getObstacles()) {
            if (obstacle.isVisibility()) {
                graphics2D.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
            }
        }
        hud.update(game.getGameState(), game.getPlayer().getId());
        if (!game.isRunning()) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", Font.BOLD, 50));
            graphics2D.drawString("Game Over", 600, 400);
        }
    }
}
