package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class GameServer {
    private final HashMap<String, PlayerHandler> clientHandlers;
    private final HostListener hostListener;
    private DatagramSocket serverSocket;
    private volatile boolean isRunning;
    private static final int PORT = 12345;
    private final int packetSize = 4096;

    public GameServer(HostListener hostListener) {
        this.hostListener = hostListener;
        isRunning = true;
        clientHandlers = new HashMap<>();
        try {
            serverSocket = new DatagramSocket(PORT);
        } catch (IOException e) {
            System.err.println("error " + e.getMessage());
        }
        new Thread(this::start).start();
    }

    private byte[] serializedSavedGame;

    public void start() {
        while (isRunning) {
            try {
                byte[] buffer = new byte[packetSize];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(packet);

                PlayerInput inputInfo = (PlayerInput) PlayerHandler.receiveGameState(packet.getData());

                String playerId = inputInfo.getId();
                PlayerHandler playerHandler = clientHandlers.get(playerId);
                if (playerHandler == null) {
                    playerHandler = new PlayerHandler(playerId);
                    hostListener.addOnlinePlayer(playerHandler);
                    clientHandlers.put(playerId, playerHandler);
                    continue;
                }
                playerHandler.setPlayerInputInfo(inputInfo);

                if (serializedSavedGame == null) {
                    continue;
                }
                byte[] response = serializedSavedGame;
                DatagramPacket responsePacket = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
                serverSocket.send(responsePacket);
            } catch (IOException | ClassNotFoundException e) {
                closeConnection();
            }
        }
    }

    public void sendUpdate(GameState gameState) {
        try {
            serializedSavedGame = PlayerHandler.sendGameState(gameState);
        } catch (IOException e) {
            System.err.println("Ошибка сериализации: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace(); // Выводим полный stack trace для диагностики
            serializedSavedGame = null;
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void closeConnection() {
        setRunning(false);
        serverSocket.close();
    }
}