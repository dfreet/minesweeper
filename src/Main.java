import controller.ConsolePlayer;
import view.GuiView;

public class Main {
    public static final boolean consoleMode = true;
    public static final boolean debug = true;

    public static void main(String[] args) {
        if (consoleMode) {
            ConsolePlayer.play(debug);
        } else {
            GuiView view = new GuiView();
            view.setVisible(true);
        }
    }
}
