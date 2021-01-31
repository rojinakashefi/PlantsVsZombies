package Miscs.Socket;



import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Main.Main.*;

@SuppressWarnings("FieldMayBeFinal")
public class Server {
    private static HashMap<String, ClientHandler> clients = new HashMap<>();
    private static ExecutorService ClientPool = Executors.newFixedThreadPool(5);
    private static int pointer = 0;
    static ServerSocket listener;
    int i = 0;



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
    }

    public void send(String SenderType, String ReceiverType, String[] Data) {

    }


    public void deleteClient(String Name) {

    }
}
