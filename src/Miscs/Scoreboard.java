package Miscs;

import Miscs.Socket.Client;

import java.awt.*;
import java.util.ArrayList;

import static Main.Main.isServerUp;


public class Scoreboard {

    /**
     * return the saved data on the server
     */
    public Scoreboard() {
        Client score = new Client("Scoreboard");
        communicate(score);
    }

    /**
     * waits for a call, then returns the requested data
     */

    private void communicate(Client score) {

    }

}
