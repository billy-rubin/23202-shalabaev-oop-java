package net;

import model.entities.Player;

import java.io.*;

public class PlayerHandler {
    private final String playerId;
    private Player player;
    private PlayerInput input;

    public PlayerHandler(String playerId) {
        this.playerId = playerId;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayerInputInfo(PlayerInput input) {
        this.input = input;
        player.setActiveCommands(input.getCommands());
    }

    public static byte[] sendGameState(Object info) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(info);
        return out.toByteArray();
    }

    public static Object receiveGameState(byte[] input) throws IOException, ClassNotFoundException{
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        ObjectInputStream ois = new ObjectInputStream(in);
        return ois.readObject();
    }

    public String getPlayerId() {
        return playerId;
    }
}