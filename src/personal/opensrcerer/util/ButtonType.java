/*
 * Made for the Final Project in CS105, due December 4th 2020. <br>
 * This work is licensed under the GNU General Public License v3.0 <br>
 * GNU © 2020 Daniel Stefani / OpenSrcerer
 */

package personal.opensrcerer.util;

/**
 * This enum is used to distinguish button types,
 * when creating different button action listeners.
 * For example, a Help type button will show a help JPanel, and
 * a Start button will initialize the factorial calculation JPanel.
 *
 * Using an enum makes it easy to distinguish these buttons by assigning
 * a ButtonType value to them.
 */
public enum ButtonType {

    /**
     * Represents the "Help" button in the UI.
     */
    HELP,

    /**
     * Represents the "Credits" button in the UI.
     */
    CREDITS,

    /**
     * Represents the "Start Game" button in the UI.
     */
    START,

    /**
     * Represents the "Roll" button in the UI.
     */
    ROLL,

    /**
     * Represents the "Exit" button in the UI.
     */
    EXIT,
}