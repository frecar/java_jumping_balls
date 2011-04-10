package baller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UpdateListener implements Runnable
{

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
        String[] parts = position.split(":", 2);
        int clientId = Integer.parseInt(parts[0]);
        server.setClientPosition(clientId, parts[1]);
    }

}
