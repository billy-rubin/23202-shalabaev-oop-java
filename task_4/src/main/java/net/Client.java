package net;

import model.entities.Player;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private final DatagramSocket server;
    private final InetAddress ip;
    private final int port = 12345;
    private final int packetSize = 4096;
    private final int timeout = 1000;

    public Client(String ip) throws Exception {
        this.ip = InetAddress.getByName(ip);
        server = new DatagramSocket();
        server.setSoTimeout(timeout);
        System.out.println("Клиент запущен, подключение к " + ip + ":" + port);
    }

    public void sendPlayerData(Player player) {
        PlayerInput playerInputInfo = new PlayerInput(player.getId(), player.getActiveCommands());
        sendPlayerData(playerInputInfo);
    }

    public void sendPlayerData(PlayerInput playerInput) {
        try {
            byte[] data = PlayerHandler.sendGameState(playerInput);
            DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
            server.send(packet);
            System.out.println("Отправлен PlayerInput: " + playerInput);
        } catch (IOException e) {
            System.err.println("Ошибка отправки данных: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public GameState receiveSavedData() {
        byte[] data = new byte[packetSize];
        DatagramPacket packet = new DatagramPacket(data, packetSize);
        try {
            server.receive(packet);
            GameState gameState = (GameState) PlayerHandler.receiveGameState(packet.getData());
            System.out.println("Получен GameState: " + gameState);
            return gameState;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка получения данных: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        server.close();
        System.out.println("Клиент отключён");
    }
}