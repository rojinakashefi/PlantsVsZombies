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
        ArrayList<Player> players = Player.load();
        String[] receive = score.receive();
        switch (receive[0]) {
            case "Ranking" -> {
                score.send("Scoreboard");
                score.send("Ranking");
                assert players != null;
                if (!players.isEmpty()) {
                    score.send(String.valueOf(players.size() * 4));
                    for (Player value : players) {
                        score.send(value.username);
                        score.send(String.valueOf(value.score));
                        score.send(String.valueOf(value.wins));
                        score.send(String.valueOf(value.losses));
                    }
                } else score.send("0");
            }
            case "Accounts" -> {
                score.send("Scoreboard");
                score.send("Accounts");
                if (players != null) {
                    score.send(String.valueOf(players.size() * 5));
                    for (Player value : players) {
                        score.send(value.username);
                        score.send(String.valueOf(value.score));
                        score.send(String.valueOf(value.wins));
                        score.send(String.valueOf(value.losses));
                        score.send(String.valueOf(value.difficulty));
                    }
                } else score.send("0");
            }
            case "Main" -> {
                if (receive.length <= 2) {
                    score.send("Scoreboard");
                    score.send("Main");
                    ArrayList<GameSave> playerSaves = GameSave.load(receive[1]);
                    if (playerSaves != null) {
                        score.send(String.valueOf(playerSaves.size() *
                                (playerSaves.get(0).cards.size() +
                                        (playerSaves.get(0).objects.size() * 4) +
                                        5)));
                        score.send(String.valueOf(playerSaves.size()));
                        for (GameSave save : playerSaves) {
                            score.send(String.valueOf(save.cards.size()));
                            for (Integer card : save.cards) {
                                score.send(String.valueOf(card));
                            }
                            score.send(String.valueOf(save.objects.size()));
                            for (GameObjects object : save.objects) {
                                score.send(object.objectName);
                                score.send(String.valueOf(object.health));
                                score.send(String.valueOf(object.position.x));
                                score.send(String.valueOf(object.position.y));
                            }
                            score.send(String.valueOf(save.gameTime));
                            score.send(String.valueOf(save.suns));
                        }
                    } else score.send("0");
                } else {
                    int i = 1;
                    ArrayList<Player> savePlayers = new ArrayList<>();
                    int playerCount = Integer.parseInt(receive[i++]);
                    for (int j = 0; j < playerCount; j++) {
                        String name = receive[i++];
                        int scores = Integer.parseInt(receive[i++]);
                        int wins = Integer.parseInt(receive[i++]);
                        int losses = Integer.parseInt(receive[i++]);
                        int difficulty = Integer.parseInt(receive[i++]);
                        savePlayers.add(new Player(difficulty, wins, losses, scores, name));
                    }
                    Player.save(savePlayers);
                    ArrayList<Integer> cards = new ArrayList<>();
                    ArrayList<GameObjects> objects = new ArrayList<>();
                    ArrayList<GameSave> saves = new ArrayList<>();
                    int gamesCount = Integer.parseInt(receive[i++]);
                    for (int j = 0;j < gamesCount; j++) {
                        int cardsCount = Integer.parseInt(receive[i++]);
                        for (int k = 0; k < cardsCount; k++) {
                            cards.add(Integer.valueOf(receive[i++]));
                        }
                        int objectsCount = Integer.parseInt(receive[i++]);
                        for (int x = 0; x < objectsCount; x++) {
                            String name = receive[i++];
                            int health = Integer.parseInt(receive[i++]);
                            Point point = new Point(Integer.parseInt(receive[i++]), Integer.parseInt(receive[i++]));
                            GameObjects temp = new GameObjects(name, point, health);
                            objects.add(temp);
                        }
                        saves.add(new GameSave(objects, Integer.parseInt(receive[i++]), cards,
                                Integer.parseInt(receive[i++])));
                    }
                    GameSave.save(saves, receive[receive.length - 1]);
                }
            }
        }
        if (isServerUp) communicate(score);
    }

}
