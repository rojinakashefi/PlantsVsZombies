package Main;
import Menus.AccountMenu;
import Menus.Game;
import Miscs.GameSave;
import Miscs.Player;
import Miscs.Socket.Server;
import java.util.ArrayList;

/**
 * Main class,the program starts running from here
 * loadedPlayers as list of all players playing game
 * loadedPlayers list of all games which has been saved
 * port=25252(better to be tcp for transformation of data)
 * @author RojinaKashefi && HeliaHashemipour
 * @version 1.0
 */
public class Main {

    public static ArrayList<Player> loadedPlayers = new ArrayList<>();
    public static ArrayList<GameSave> saves = new ArrayList<>();
    public static final boolean TESTING = true;
    public static final int port = 25252;
    public static final String host = "127.0.0.1";
    public static boolean  isServerUp = false;
    public static boolean addedShotDownHook = false;

    public static void main(String[] args) {
        //making server thread using :: and calling its start method
        new Thread(Server::new).start();
        isServerUp = true;
        //calling AccountMenu run method
        new AccountMenu().run();
    }

    /**
     * finds the player index in the indexed player list
     * @param name the player name
     * @return the player index in the loadedPlayer list
     */
    public static int findPlayerIndex(String name) {
        for (int i = 0; i < loadedPlayers.size(); i++) {
            if (loadedPlayers.get(i).username.equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
