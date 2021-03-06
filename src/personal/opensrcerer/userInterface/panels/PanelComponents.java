/*
 * Made for the Final Project in CS106, due April 1st 2021. <br>
 * This work is licensed under the GNU General Public License v3.0 <br>
 * GNU © 2021 Daniel Stefani / OpenSrcerer
 */

package personal.opensrcerer.userInterface.panels;

import personal.opensrcerer.RunProject;
import personal.opensrcerer.actions.RollRequest;
import personal.opensrcerer.userInterface.MainWindow;
import personal.opensrcerer.util.ButtonType;
import personal.opensrcerer.util.SnakeEyes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is used to retrieve custom stylized JComponents
 * to match the ambiance and theme of the UI.
 *
 * Note: Any mention of "custom" in the methods of this class
 * refers to the description above.
 */
public final class PanelComponents {

    // -------- Stylizing Elements --------
    /**
     * Taken from Discord's color theme.
     */
    public static final Color
            discordLightGray = new Color(185, 187, 190),
            discordLessGray = new Color(71, 71, 71),
            discordGray = new Color(54, 57, 63),
            discordGrayer = new Color(47, 49, 54);

    /**
     * Font used for messages to the player.
     */
    public static final Font bigTitleFont = new Font("Century Gothic", Font.BOLD, 30);

    /**
     * Used for large buttons, etc.
     */
    public static final Font titleFont = new Font("Century Gothic", Font.BOLD, 20);

    /**
     * Used for small descriptions.
     */
    public static final Font descriptionFont = new Font("Century Gothic", Font.PLAIN, 14);

    /**
     * Used for small buttons.
     */
    private static final Font actionFont = new Font("Arial", Font.BOLD, 15);

    /**
     * Used for TextFields.
     */
    private static final Font outputFont = new Font("Arial", Font.ITALIC, 13);
    // -------------------------------------------

    // -------- Images --------
    /**
     * A list of static images that is used throughout the program.
     */
    static ImageIcon[] imagesList;
    // ------------------------

    /**
     * Retrieves a custom JButton.
     * @param buttonName Name and initial text of button.
     * @param type Type of button.
     * @return Customized JButton.
     */
    public static JButton getButton(String buttonName, ButtonType type) {
        JButton button = new JButton();
        button.setHorizontalAlignment(SwingConstants.CENTER);
        setButtonPalette(buttonName, button);
        setMouseListener(button);
        button.addActionListener(getListener(type));
        return button;
    }

    /**
     * @param buttonName Name of button.
     * @param type Type of button. See ButtonType.
     * @see ButtonType
     * @return A custom button inside a JPanel that has a border.
     */
    public static JPanel getBorderedButton(String buttonName, ButtonType type) {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JButton button = getButton(buttonName, type);
        button.setFocusPainted(false);
        panel.add(button);
        panel.setBorder(BorderFactory.createLineBorder(discordLessGray, 1));
        return panel;
    }

    /**
     * Gets a custom Bordered Button with a listener that takes arguments.
     * @param playerBox The ComboBox that carries JPlayers.
     * @param roundBox The total number of round for the new game.
     * @return A custom Bordered button that starts a new game.
     */
    public static JPanel getPlayButton(PlayerComboBox<Integer> playerBox, JComboBox<Integer> roundBox) {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JButton button = new JButton();
        // Style Button
        button.setHorizontalAlignment(SwingConstants.CENTER);
        setButtonPalette("Play", button);
        setMouseListener(button);
        button.setFocusPainted(false);
        // Listener
        button.addActionListener(e -> {
            // Remove elements
            MainWindow.getWindowPane().removeAll();
            MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor());
            // Create a new game
            SnakeEyes.resetGame(playerBox.getPlayers(), roundBox.getSelectedIndex() + 1);
            // Switch window context
            GamePanel.setComponents(MainWindow.getWindowPane());
            MainWindow.updateJFrame();
            // If the first player is a bot, start automatically.
            if (SnakeEyes.getPlayerOnTurn().isCpu()) {
                new RollRequest(SnakeEyes.getPlayerOnTurn());
            }
        });
        // Panel Styling and return
        panel.add(button);
        panel.setBorder(BorderFactory.createLineBorder(discordLessGray, 1));
        return panel;
    }

    /**
     * Retrieves a custom Border.
     * @param borderTitle Title of border.
     * @return Customized Border.
     */
    public static Border getBorder(String borderTitle) {
        Border templateBorder = BorderFactory.createLineBorder(discordLightGray, 2, true);
        return BorderFactory.createTitledBorder(templateBorder, borderTitle,
                TitledBorder.CENTER, TitledBorder.TOP, descriptionFont, discordLightGray);
    }

    /**
     * Retrieves a custom dashed Border.
     * @return Customized Border.
     */
    public static Border getBorder() {
        return BorderFactory.createDashedBorder(discordLightGray, 2, 5, 5, false);
    }

    /**
     * @param iconIndex Index of the icon in the images array.
     * @return A selected ImageIcon.
     */
    public static ImageIcon getIcon(int iconIndex) {
        return imagesList[iconIndex];
    }

    /**
     * Changes a given button's theme to match the discord theme.
     * Takes advantage of Swing's HTML support.
     * @param buttonName String to change the button name to.
     * @param button Button to change.
     */
    public static void setButtonPalette(String buttonName, JButton button) {
        button.setText("<html><p style=\"text-align:center\">" + buttonName + "</p></html>");
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(discordGray);
        button.setForeground(discordLightGray);
        button.setFont(actionFont);
    }

    /**
     * A generified function that creates a custom JComboBox.
     * @param selections Number of selections on the JComboBox.
     * @param <W> Type of data that this JComboBox holds.
     * @return A Custom JComboBox.
     */
    public static <W> JComboBox<W> getComboBox(W[] selections) {
        JComboBox<W> box = new JComboBox<>(selections);
        box.setBackground(discordGrayer);
        box.setFont(descriptionFont);
        return box;
    }

    /**
     * @return A discord-themed JSeperator.
     */
    public static JSeparator getSeparator() {
        JSeparator separator = new JSeparator();
        separator.setBackground(discordGrayer);
        separator.setForeground(discordLightGray);
        return separator;
    }

    /**
     * @return A custom discord-themed JPanel.
     */
    public static JPanel getJPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(discordGrayer);
        return panel;
    }

    /**
     * @param manager A layout manager to attach to the
     *                created JPanel.
     * @return A custom discord-themed JPanel with
     * the given layout manager attached.
     */
    public static JPanel getJPanel(LayoutManager manager) {
        JPanel panel = new JPanel();
        panel.setLayout(manager);
        panel.setBackground(discordGrayer);
        return panel;
    }

    /**
     * @param layout int reperesented by a BoxLayout enum
     *               that shows the alignment of a BoxLayout
     * @return A JPanel with a set BoxLayout manager, with the
     * provided alignment.
     */
    public static JPanel getJPanel(int layout) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, layout));
        panel.setBackground(discordGrayer);
        return panel;
    }

    /**
     * @param defaultText Default placeholder text for JTextField.
     * @param cols Number of columns JTextField will be constructed with.
     * @return A custom themed JTextField.
     */
    public static JTextField getTextField(String defaultText, int cols) {
        JTextField field = new JTextField(defaultText, cols);
        field.setFont(outputFont);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(discordGrayer);
        field.setForeground(discordLightGray);
        field.setCaretColor(discordLightGray);

        // Key event interceptor
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (field.getText().length() >= 10 ) { // Limit TextField to 10 characters.
                    e.consume(); // Discard the event.
                }
            }
        });

        setMouseListener(field);
        return field;
    }

    /**
     * @param name Text of JLabel.
     * @param font Font to use with JLabel.
     * @return A custom themed JLabel.
     */
    public static JLabel getLabel(String name, Font font) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setForeground(discordLightGray);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * @param imageNumber Number of the image to use.
     * @return A custom JLabel that is constructed with
     * an image instead of text.
     */
    public static JLabel getImageLabel(int imageNumber) {
        if (imageNumber < 0 || imageNumber > imagesList.length - 1) {
            throw new IllegalArgumentException("Invalid image index");
        }
        JLabel picLabel = new JLabel(imagesList[imageNumber]);
        picLabel.setBackground(discordGrayer);
        return picLabel;
    }

    /**
     * @return A custom JLabel that is constructed with
     * a gif instead of text.
     */
    public static JLabel getLogo() {
        JLabel picLabel = new JLabel(imagesList[6]);
        picLabel.setBackground(discordGrayer);
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return picLabel;
    }

    /**
     * @return A custom JLabel that is constructed with
     * a gif instead of text.
     */
    public static JLabel getSkeletonLogo() {
        JLabel picLabel = new JLabel(imagesList[9]);
        picLabel.setBackground(discordGrayer);
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return picLabel;
    }

    /**
     * @return A custom JLabel that is constructed with
     * a png instead of text.
     */
    public static JLabel getCursor() {
        JLabel cursor = new JLabel(imagesList[12]);
        cursor.setBackground(discordGrayer);
        return cursor;
    }

    /**
     * @return A customized JSlider to use as the music slider.
     * @param listener The listener to attach to this slider.
     */
    public static JSlider getSlider(ChangeListener listener) {
        JSlider slider = new JSlider();
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue(100);
        slider.setExtent(2);
        slider.setBackground(discordGrayer);
        slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        slider.setPreferredSize(new Dimension(50, 25));
        slider.addChangeListener(listener);
        return slider;
    }

    /**
     * @param name Text attached to set box.
     * @param isSelected Whether the checkbox is originally
     *                   checked or not.
     * @return A custom checkbox.
     */
    public static JCheckBox getCheckBox(String name, boolean isSelected) {
        JCheckBox box = new JCheckBox(name, isSelected);
        box.setBackground(discordGray);
        box.setFont(actionFont);
        box.setForeground(discordLightGray);
        return box;
    }

    /**
     * Initializes all images retrieved from the resources project dir and dumps them into an array for later usage.
     * @throws NullPointerException If retrieved image is null.
     */
    public static void initializeImages() {
        imagesList = new ImageIcon[16];
        for (int index = 0; index < 6; ++index) {
            // Retrieve a resource stream using a base class as a reference point.
            imagesList[index] = new ImageIcon(RunProject.class.getResource("/resources/die" + (index + 1) + ".png"));
        }
        imagesList[6] = new ImageIcon(RunProject.class.getResource("/resources/logo.png"));
        imagesList[7] = new ImageIcon(RunProject.class.getResource("/resources/speaker32.png"));
        imagesList[8] = new ImageIcon(RunProject.class.getResource("/resources/speaker32m.png"));
        imagesList[9] = new ImageIcon(RunProject.class.getResource("/resources/skelly.png"));
        imagesList[10] = new ImageIcon(RunProject.class.getResource("/resources/dieq.png"));
        imagesList[11] = new ImageIcon(RunProject.class.getResource("/resources/roll.png"));
        imagesList[12] = new ImageIcon(RunProject.class.getResource("/resources/cursor.png"));
        imagesList[13] = new ImageIcon(RunProject.class.getResource("/resources/back.png"));
        imagesList[14] = new ImageIcon(RunProject.class.getResource("/resources/helpChart.png"));
        imagesList[15] = new ImageIcon(RunProject.class.getResource("/resources/goldenDie.png"));
    }

    /**
     * Function that sets a mouse listener to a component,
     * that changes its background when mouse enters and exits
     * it.
     * @param component The button to add a listener to.
     */
    public static void setMouseListener(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                MainWindow.getWindowPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                component.setBackground(discordLessGray);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor());
                component.setBackground(discordGray);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                component.setBackground(discordGray);
            }
        });
    }

    /**
     * Creates a listener aimed at a target field that changes
     * the cursor to a text cursor whenever this target
     * field is entered.
     * @param field Target field.
     */
    public static void setMouseListener(JTextField field) {
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                MainWindow.getWindowPane().setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    /**
     * Retrieves a listener according to what a specific button should do when clicked.
     * Used for GUI switch listeners.
     * @param type Type of button.
     * @return An ActionListener according to the button type.
     */
    static ActionListener getListener(ButtonType type) {
        // Variable "e" indicates a new lambda action listener
        // FORMAT:
        //        Remove elements from MainWindow WindowPane
        //        Reset user cursor
        //        Set specific components from the panels package
        //        Pack JFrame & Repaint

        return switch (type) {
            case HELP -> e -> {
                MainWindow.getWindowPane().removeAll(); // Remove elements
                MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor()); // Update cursor
                HelpPanel.setComponents(MainWindow.getWindowPane()); // Switch window context
                MainWindow.updateJFrame(); // Refresh frame
            };
            case CREDITS -> e -> {
                MainWindow.getWindowPane().removeAll(); // Remove elements
                MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor()); // Update cursor
                CreditsPanel.setComponents(MainWindow.getWindowPane()); // Switch window context
                MainWindow.updateJFrame(); // Refresh frame
            };
            case ROLL -> e -> {
                // Initiate a new roll request
                new RollRequest(SnakeEyes.getPlayerOnTurn());
            };
            case PLAY -> e -> {
                // Empty Listener
            };
            case BACK -> e -> {
                MainWindow.getWindowPane().removeAll(); // Remove elements
                MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor()); // Update cursor
                StartPanel.setComponents(MainWindow.getWindowPane()); // Switch window context
                MainWindow.updateJFrame(); // Refresh frame
            };
            case ROLL_BACK -> e -> {
                MainWindow.getWindowPane().removeAll(); // Remove elements
                MainWindow.getWindowPane().setCursor(Cursor.getDefaultCursor()); // Update cursor
                StartPanel.setComponents(MainWindow.getWindowPane()); // Switch window context
                SnakeEyes.getPlayers().forEach(player -> player.resetStatus(true)); // Reset the players' scores
                MainWindow.updateJFrame(); // Refresh frame
            };
        };
    }
}