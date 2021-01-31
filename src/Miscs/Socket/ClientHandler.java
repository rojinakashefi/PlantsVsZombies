package Miscs.Socket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static Main.Main.TESTING;
import static Main.Main.isServerUp;


public class ClientHandler implements Runnable {
    Socket client;
    Server server;
    BufferedReader input;
    PrintWriter out;
    int num;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream());
    }

    @Override
    public void run() {
        if (TESTING) System.out.println("[SERVER] Looking For Data Connection...");
        if (isServerUp) {
            try {
                ServerReceiver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            run();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ServerReceiver() throws IOException {
        String SenderType;
        SenderType = input.readLine();
        if (Client.Names.size() >= 2) {
            String ReceiverType = input.readLine();
            if (ReceiverType != null) {
                if (!ReceiverType.equals("Close")) {
                    int Number = Integer.parseInt(input.readLine());
                    String[] Data = new String[Number];
                    for (int i = 0; i < Data.length; i++)
                        Data[i] = input.readLine();
                    server.send(SenderType, ReceiverType, Data);
                    ServerReceiver();
                } else {
                    if (TESTING) System.out.println("[HANDLER] Closing...");
                    server.deleteClient(SenderType);
                    Client.Names.remove(SenderType);
                }
            }
        } else {
            if (TESTING) System.out.println("[HANDLER] Failed To Send. Trying Again!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ServerReceiver();
        }
    }
}
