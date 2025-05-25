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
            System.out.println("Сервер запущен на порту " + PORT);
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            e.printStackTrace();
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
                System.out.println("Получен пакет от " + packet.getAddress() + ":" + packet.getPort());

                PlayerInput inputInfo = (PlayerInput) PlayerHandler.receiveGameState(packet.getData());
                System.out.println("Десериализован PlayerInput: " + inputInfo);

                String playerId = inputInfo.getId();
                PlayerHandler playerHandler = clientHandlers.get(playerId);
                if (playerHandler == null) {
                    playerHandler = new PlayerHandler(playerId);
                    hostListener.addOnlinePlayer(playerHandler);
                    clientHandlers.put(playerId, playerHandler);
                    System.out.println("Новый игрок подключён: " + playerId);
                } else {
                    playerHandler.setPlayerInputInfo(inputInfo);
                    System.out.println("Обновлён PlayerInput для игрока: " + playerId);
                }

                if (serializedSavedGame != null) {
                    byte[] response = serializedSavedGame;
                    DatagramPacket responsePacket = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
                    serverSocket.send(responsePacket);
                    System.out.println("Отправлен GameState клиенту: " + playerId);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Ошибка обработки пакета: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void sendUpdate(GameState gameState) {
        try {
            serializedSavedGame = PlayerHandler.sendGameState(gameState);
            System.out.println("GameState сериализован для отправки");
        } catch (IOException e) {
            System.err.println("Ошибка сериализации GameState: " + e.getMessage());
            e.printStackTrace();
            serializedSavedGame = null;
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void closeConnection() {
        setRunning(false);
        serverSocket.close();
        System.out.println("Сервер остановлен");
    }
}