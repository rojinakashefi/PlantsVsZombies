package Miscs.Socket;

import java.io.IOException;
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

}
