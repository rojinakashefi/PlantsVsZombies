
package Miscs.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import static Main.Main.TESTING;
import static Main.Main.isServerUp;

/**
 * This class handles the work of clients
 * each client has its own clientHandler which is a thread
 * @author RojinaKashefi && HeliaHashemipour
 */
public class ClientHandler implements Runnable {
    Socket client;
    Server server;
    BufferedReader input;
    PrintWriter out;
    int num;

    /**
     * Constructor
     * read input and making printWriter for writing
     * @param client as client socket we are doing it tasks
     */
    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream());
    }

    /**
     * When client thread starts this method will run
     * which it continues till the server up is true
     * serverReceiver is the next step
     */
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

    /**
     *Receives information of client from server
     */
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
