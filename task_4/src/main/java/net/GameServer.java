package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class GameServer {
    private final HashMap<String, PlayerHandler> playerHandlers = new HashMap<>();
    private final HostListener hostListener;
    private DatagramSocket serverSocket;
    private volatile boolean isRunning;
    private static final int PORT = 12345;
    private final int packetSize = 4096;
    private byte[] serializedSavedGame;

    public GameServer(HostListener hostListener) {
        this.hostListener = hostListener;
        isRunning = true;
        try {
            serverSocket = new DatagramSocket(PORT);
            System.out.println("Сервер запущен на порту " + PORT);
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            e.printStackTrace();
        }
        new Thread(this::start).start();
    }

    public void start() {
        while (isRunning) {
            try {
                byte[] buffer = new byte[packetSize];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(packet);
                System.out.println("Получен пакет от " + packet.getAddress() + ":" + packet.getPort());

                PlayerInput playerInput = (PlayerInput) PlayerHandler.receiveGameState(packet.getData());
                System.out.println("Десериализован PlayerInput: " + playerInput);

                String playerId = playerInput.getId();
                PlayerHandler playerHandler = playerHandlers.get(playerId);
                if (playerHandler == null) {
                    playerHandler = new PlayerHandler(playerId);
                    hostListener.addOnlinePlayer(playerHandler);
                    playerHandlers.put(playerId, playerHandler);
                    System.out.println("Новый игрок подключён: " + playerId);
                } else {
                    playerHandler.setPlayerInputInfo(playerInput);
                    System.out.println("Обновлён PlayerInput для игрока: " + playerId);
                }

                if (serializedSavedGame != null && serializedSavedGame.length > 0) {
                    DatagramPacket responsePacket = new DatagramPacket(serializedSavedGame, serializedSavedGame.length, packet.getAddress(), packet.getPort());
                    serverSocket.send(responsePacket);
                    System.out.println("Отправлен GameState клиенту: " + playerId);
                } else {
                    GameState emptyState = new GameState(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), true, 0, 0, 0);
                    byte[] emptyData = PlayerHandler.sendGameState(emptyState);
                    DatagramPacket emptyPacket = new DatagramPacket(emptyData, emptyData.length, packet.getAddress(), packet.getPort());
                    serverSocket.send(emptyPacket);
                    System.out.println("Отправлен пустой GameState клиенту: " + playerId);
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
            System.out.println("GameState сериализован для отправки, размер: " + serializedSavedGame.length + " байт");
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