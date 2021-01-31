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


    public void deleteClient(String Name) {

    }
}
