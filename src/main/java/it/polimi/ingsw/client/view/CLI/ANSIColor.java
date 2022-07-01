package it.polimi.ingsw.client.view.CLI;


/**
 * Collection of escape coded console text colors
 */
public class ANSIColor {

    public static final String RESET = "\u001B[0m";
    public static final String UNDERLINE = "\u001b[4m";


    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String PINK = "\033[38;5;206m";
    public static final String BLUE = "\u001B[34m";
    public static final String WHITE = "\u001b[37;1m";


    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String ORANGE_BACKGROUND = "\033[48;2;255;165;0m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    public static final String CYAN_BOLD = "\033[1;36m";

    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    public static final String YELLOW_BOLD_BRIGHT ="\033[1;93m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";


}
