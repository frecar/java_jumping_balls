package baller.server;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {

    private static Logger log = Logger.getLogger(ClientHandler.class.getName());

    private Server server;
    private PrintWriter out;
    private int clientId;

    public ClientHandler(Server server, Socket client) throws IOException {
        this.server = server;
        out = new PrintWriter(client.getOutputStream(), true);
        clientId = server.getNextID();
    }


    public void init() {

        log.info("writing id to client");
        out.println("" + clientId);
        log.info("done writing id");

    }

    public void run() {

        out.println("starting");
        writeIds();
        while (true) {
            writePositions();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info(e.toString());
                return;
            }
        }

    }

    private void writeIds() {
        StringBuilder ids = new StringBuilder();
        Iterator<Integer> iterator = server.getClientIds().iterator();
        while (iterator.hasNext()) {
            ids.append(iterator.next());
            if (iterator.hasNext()) ids.append(":");
        }

        log.info("writing ids: " + ids);
        out.println(ids);
    }

    private void writePositions() {
        String allPositions = server.getPositions();
    //    log.info("writing position update: " + allPositions);
        out.println(allPositions);
    }
}
