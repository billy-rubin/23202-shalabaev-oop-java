package net;

import model.Obstacle;
import model.entities.*;
import java.io.Serializable;
import java.util.List;

public record GameState(
        List<Obstacle> obstacles,
        List<Movable> movables,
        List<Destructible> destructibles,
        boolean isRunning,
        int kills,
        int waveNumber,
        int score
) implements Serializable {
    private static final long serialVersionUID = 1L;
}