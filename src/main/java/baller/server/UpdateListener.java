package baller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class UpdateListener implements Runnable {

    private static final Logger log = Logger.getLogger(UpdateListener.class.getName());

    private Server server;
    private BufferedReader in;
    private volatile boolean isRunning;

    public UpdateListener(Server server, Socket client) throws IOException {
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }


    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            try {
                String position = in.readLine();
                handleUpdate(position);
            } catch (IOException e) {
                // ?
            }
        }
    }

    private void handleUpdate(String position) {
        if (position.equals("start")) {
            server.stopListening();
        }

        else {
            log.info("read update: " + position);
            String[] parts = position.split(":", 2);
            int clientId = Integer.parseInt(parts[0]);
            server.setClientPosition(clientId, parts[1]);
        }
    }

}
