package Miscs.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public String[] receive() {
        BufferedReader input;
        int number;
        ArrayList<String> FinalResult = new ArrayList<>();
        String[] ReturnedInput = null;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            FinalResult.add(input.readLine());
            if (TESTING) System.out.print(" " + FinalResult.get(0));
            ReturnedInput = new String[number = (Integer.parseInt(input.readLine())) + 1];
            ReturnedInput[0] = FinalResult.get(0);
            for (int i = 1; i < number; i++) {
                ReturnedInput[i] = input.readLine();
                if (TESTING) System.out.print(" " + ReturnedInput[i]);
            }
            if (TESTING) System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TESTING) System.out.println("    [CLIENT] Prompting Client");
        return ReturnedInput;
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
