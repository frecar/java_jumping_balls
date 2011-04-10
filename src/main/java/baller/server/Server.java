package baller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

class Server {

    private static Logger log = Logger.getLogger(Server.class.getName());

    private Executor threadPool;
    private volatile boolean gameStarted;
    private volatile int nextID;

    private ConcurrentMap<Integer, String> clientPositions;
    private List<ClientHandler> clientHandlers;

    private static final int port = 1234;


    public Server() {
        threadPool = Executors.newCachedThreadPool();
        clientPositions = new ConcurrentHashMap<Integer, String>();
        clientHandlers = new ArrayList<ClientHandler>();
    }

    public void listen() throws IOException {

        PusherServer pusherServer = new PusherServer(4321);
        threadPool.execute(pusherServer);

        nextID = 1;
        ServerSocket serverSocket = new ServerSocket(port);
        gameStarted = false;
        while (!gameStarted) {
            Socket clientSocket = serverSocket.accept();
            log.info("New connection:" + clientSocket);
            ClientHandler clientHandler = new ClientHandler(this, clientSocket);
            clientHandler.init();
            clientHandlers.add(clientHandler);
        }

        startGame();

    }

    public void stopListening() {
        gameStarted = true;
    }

    public void startGame() {
        for (ClientHandler clientHandler : clientHandlers) {
            threadPool.execute(clientHandler);
        }
    }

    public synchronized int getNextID() {
        return nextID++;
    }

    public void setClientPosition(int clientId, String pos) {
        clientPositions.put(clientId, pos);
    }

    public String getPositions() {
        StringBuilder res = new StringBuilder();
        for(Map.Entry pos : clientPositions.entrySet()) {
            res.append(pos.getKey());
            res.append(":");
            res.append(pos.getValue());
            res.append("|");
        }
        return res.toString();
    }


    private class PusherServer implements Runnable {

        final int port;
        boolean listening;

        public PusherServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            listening = true;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (listening) {
                    Socket client = serverSocket.accept();
                    UpdateListener updateListener = new UpdateListener(Server.this, client);
                    Server.this.threadPool.execute(updateListener);
                }


            } catch (IOException e) {
                // ?
            }
        }
    }

    public static void main(String... args) throws IOException{

        new Server().listen();
    }

}



