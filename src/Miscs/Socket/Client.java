package Miscs.Socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static Main.Main.*;

public class Client {
    public static ArrayList<String> Names = new ArrayList<>();
    Socket socket;

    public Client(String Name) {
        try {
            socket = new Socket(host, port);
            Names.add(Name);
        } catch (IOException ignored) {
            if (TESTING) System.out.println("    [Client] Server is out");
        }
    }
    public void send(String Data) {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream());
            out.println(Data);
            out.flush();
            if (TESTING) System.out.println("    [CLIENT] Data sent to Server: " + Data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            send("Client");
            send("Close");
            send("0");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
