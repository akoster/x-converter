package xcon.stackoverflow.dbcontext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbContext {

    // use constants, not String literals
    public static final String PLAYERS_FILE = "players.json";

    // use interfaces where possible rather than concrete implementation type
    private List<Player> players;

    public DbContext() {
        // the initialization was always overwritten
        // just assign directly from the method
        this.players = readPlayers();
    }

    public Optional<Player> getPlayerByIds(long id, long boardId) {
        for (Player p : players) {
            if (p.getBoardId() == boardId && p.getId() == id) {
                // no need to write players here since there is no change
                // writePlayers();
                return Optional.of(p);
            }
        }
        // do not return null from a method, use Optional instead
        return Optional.empty();
    }

    public void updatePlayer(Player player) {

        // this will not work. player will not get added in the list if you reassign a local variable
        // for (Player p : players) {
        //    if (p.getId() == player.getId()) {
        //        p = player;

        Optional<Integer> playerIndex = findPlayer(player);
        if (playerIndex.isPresent()) {
            players.set(playerIndex.get(), player);
        } else {
            // if the player is not found you can either add the player
            addPlayer(player);
            // ... or throw an exception
            throw new RuntimeException(String.format("Could not find player to update: %s", player));
            // returning a boolean to indicate failure is sort of evil
        }
    }

    private Optional<Integer> findPlayer(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == player.getId()) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public void addPlayer(Player player) {
        // if you want players to be unique in the list, you should do some checking here
        players.add(player);
        writePlayers();
    }

    // never used
    //    private boolean playerExists(Player player) {
    //        for (Player p : players) {
    //            if (p.getId() == player.getId()) {
    //                return true;
    //            }
    //        }
    //        return false;
    //    }

    // refactored methods below so each method does only one thing, or at least operates at a single level of abstraction

    private List<Player> readPlayers() {
        try (BufferedReader br = new BufferedReader(new FileReader(PLAYERS_FILE))) {
            return readPlayers(br);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not read players from %s", PLAYERS_FILE), e);
        }
    }

    private List<Player> readPlayers(BufferedReader br) throws IOException {
        List<Player> players;
        if (br.read() > 0) {
            players = readPlayersAvailable(br);
        } else {
            players = new ArrayList<>();
        }
        return players;
    }

    private List<Player> readPlayersAvailable(BufferedReader br) {
        List<Player> players;
        Gson gson = new Gson();
        // this looks hacky. in Gson 2.8.0 you can use TypeToken.getParameterized(ArrayList.class, Player.class).getType()
        Type type = new TypeToken<ArrayList<Player>>() {
        }.getType();
        players = gson.fromJson(br, type);
        return players;
    }

    private void writePlayers() {
        try (FileWriter writer = new FileWriter(PLAYERS_FILE)) {
            writePlayers(writer);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not write players to %s", PLAYERS_FILE), e);
        }
    }

    private void writePlayers(FileWriter writer) throws IOException {
        Gson gson = new Gson();
        gson.toJson(players, writer);
        writer.flush();
    }
}