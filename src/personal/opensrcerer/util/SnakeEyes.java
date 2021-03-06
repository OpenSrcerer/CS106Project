/*
 * Made for the Final Project in CS106, due April 1st 2021. <br>
 * This work is licensed under the GNU General Public License v3.0 <br>
 * GNU © 2021 Daniel Stefani / OpenSrcerer
 */

package personal.opensrcerer.util;

import personal.opensrcerer.actions.RollRequest;
import personal.opensrcerer.userInterface.panels.Banner;
import personal.opensrcerer.userInterface.panels.Diceboard;
import personal.opensrcerer.userInterface.panels.RollButton;
import personal.opensrcerer.userInterface.panels.Scoreboard;
import personal.opensrcerer.util.circularList.CircularLinkedList;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Represents one full SnakeEyes game.
 * Everything is static as instantiation does not make sense here.
 */
public abstract class SnakeEyes {

    /**
     * The Roll Button for this game.
     */
    private static RollButton rollButton;

    /**
     * The banner that contains announcement text for this game.
     */
    private static Banner banner;

    /**
     * The Diceboard for this game.
     */
    private static Diceboard diceboard;

    /**
     * The Scoreboard for this game.
     */
    private static Scoreboard scoreboard;

    /**
     * A custom Circular Linked List that contains all the JPlayers in the game.
     */
    private static CircularLinkedList<Player> players;

    /**
     * The total rounds in this game.
     */
    private static int totalRounds;

    /**
     * This game's current round.
     */
    private static int currentRound;

    /**
     * Used to show if the ongoing game has finished.
     */
    private static boolean finished;

    /**
     * Resets the data for the ongoing game.
     * @param players Players that will play in the new game.
     * @param totalRounds The total number of rounds in the new game.
     */
    public static void resetGame(Player[] players, int totalRounds) {
        // Values first
        SnakeEyes.finished = false;
        SnakeEyes.players = new CircularLinkedList<>(players);
        SnakeEyes.totalRounds = totalRounds;
        SnakeEyes.currentRound = 1;
        // Instantiate GUI Elements last
        SnakeEyes.rollButton = new RollButton();
        SnakeEyes.banner = new Banner();
        SnakeEyes.diceboard = new Diceboard();
        SnakeEyes.scoreboard = new Scoreboard();
    }

    /**
     * @return The player whose turn it is.
     */
    public static Player getPlayerOnTurn() {
        return players.getCurrent();
    }

    /**
     * @return All the players in the game as a Stream.
     */
    public static Stream<Player> getPlayers() {
        return players.getAll().stream();
    }

    /**
     * @return The Scoreboard for this game.
     */
    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * @return The Diceboard for this game.
     */
    public static Diceboard getDiceboard() {
        return diceboard;
    }

    /**
     * @return The Banner for this game.
     */
    public static Banner getBanner() {
        return banner;
    }

    /**
     * @return The current round of the game.
     */
    public static int getCurrentRound() {
        return currentRound;
    }

    /**
     * @return The total rounds of the game.
     */
    public static int getTotalRounds() {
        return totalRounds;
    }

    /**
     * @return This game's RollButton.
     */
    public static RollButton getRollButton() {
        return rollButton;
    }

    /**
     * Advances the turn to the next player.
     * If it's the turn of the last player on the list, the round advances, or the game ends.
     */
    public static void nextTurn() {
        Player previousPlayer = getPlayerOnTurn();

        nextPlayer(); // Go to the next player

        if (!finished) {
            // If previous player is not computer-player but the current one is, give the previous one time to read score
            if (!previousPlayer.isCpu() && getPlayerOnTurn().isCpu()) {
                RequestDispatcher.schedule(() -> new RollRequest(getPlayerOnTurn()), 5, TimeUnit.SECONDS);
            } else if (getPlayerOnTurn().isCpu()) { // Otherwise just make a new roll request if only the current one is
                RequestDispatcher.schedule(() -> new RollRequest(getPlayerOnTurn()), 100, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * Checks if the round is about to end, then advances the turn to the next player.
     */
    private static void nextPlayer() {
        // Filters the Stream of Players to contain only the players who have finished and then counts the total
        int finishedPlayers = players.getElementsThat(player -> player.getStatus().equals(PlayerStatus.FINISHED_ROUND));
        if (finishedPlayers == size()) { // If all players have finished rolling
            nextRound(); // Go to the next round, or finish the game
        } else {
            // Advance the turn to the next AVAILABLE player that hasn't finished
            players.advanceTo(player -> !player.getStatus().equals(PlayerStatus.FINISHED_ROUND));
        }
    }

    /**
     * Advance the game to the next round.
     * If there are no more rounds, the game finishes.
     */
    private static void nextRound() {
        if (currentRound < totalRounds) { // If the current round is at a lesser value than the total rounds
            currentRound++; // Advance to the next round
            getPlayers().forEach(player -> player.resetStatus(false)); // Resets the players' statuses to unrolled
            players.setToFirst(); // Give the turn to the first player
        } else {
            rollButton.convertToBackButton();
            finished = true;
        }
    }

    /**
     * @return The number of players in this game.
     */
    public static int size() {
        return players.size();
    }

    /**
     * @return Whether this game has finished.
     */
    public static boolean isFinished() {
        return finished;
    }
}
