package Miscs.Socket;

import Miscs.Scoreboard;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static Main.Main.*;

/**
 * This class is a server which all the clients are connected to it .
 * it contains sending and deletingclient method
 * @author RojinaKashefi && HeliaHashemipour
 */
@SuppressWarnings("FieldMayBeFinal")
public class Server {
    private static HashMap<String, ClientHandler> clients = new HashMap<>();
    private static ExecutorService ClientPool = Executors.newFixedThreadPool(5);
    private static int pointer = 0;
    static ServerSocket listener;
    int i = 0;

    /**
     *This thread creates a welcoming socket and connection socket
     * and pass a client to it client handler
     * and then make a hashmap for each client and its own clientHandler
     */
    public Server() {
        Thread server_activation = new Thread(() -> {
            try {
                listener = new ServerSocket(port);
                if (TESTING) System.out.println("[SERVER] Server Initialized.");
                while (isServerUp) {
                    Socket client = listener.accept();
                    if (TESTING) System.out.println("[SERVER] Connected To " + Client.Names.get(pointer) + "!");
                    ClientHandler clientThread = new ClientHandler(client);
                    clients.put(Client.Names.get(pointer++), clientThread);
                    ClientPool.execute(clientThread);
                    clientThread.server = this;
                    clientThread.num = i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        server_activation.start();
        new Thread(Scoreboard::new).start();
    }

    /**
     * Sends data to the client
     * @param SenderType The name of the sender that stored in client class
     * @param ReceiverType Name of the destination class that stored in client class
     * @see Client#Names for more information
     * @param Data Data that transferred between two clients
     */
    public void send(String SenderType, String ReceiverType, String[] Data) {
        if(TESTING) System.out.println("[SERVER] Sending...");
        try {
            Thread.sleep(100);
            PrintWriter out = new PrintWriter(clients.get(ReceiverType).client.getOutputStream());
            out.println(SenderType);
            if (TESTING) System.out.print("[SERVER] Data Sent To " + ReceiverType + ": " + SenderType);
            out.println(Data.length);
            if (TESTING) System.out.print(" " + Data.length);
            for (String data : Data) {
                out.println(data);
                if (TESTING) System.out.print(" " + data);
            }
            if (TESTING) System.out.println();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes the client from the server property
     * @param Name the name of the server
     */
    public void deleteClient(String Name) {
        pointer--;
        clients.remove(Name);
        if (TESTING) System.out.println("[SERVER] Closing Connection...");
    }
}
