package baller.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private static Logger log = Logger.getLogger(ClientHandler.class.getName());

    private Server server;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int clientId;

    public ClientHandler(Server server, Socket client) throws IOException {

        this.server = server;
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientId = server.getNextID();
    }


    public void init() {

        log.info("writing id to client");
        out.println("" + clientId);
        log.info("done writing id");

    }

    public void run() {

        out.println("starting");
        while (true) {
            writePositions();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.info(e.toString());
            }
        }

    }

    private void writePositions() {
        String allPositions = server.getPositions();
        out.println(allPositions);
    }
}
