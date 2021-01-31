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

        }
    }

    private void ServerReceiver() throws IOException {

        }
    }
}
