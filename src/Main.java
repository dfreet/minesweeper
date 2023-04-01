import controller.ConsolePlayer;
import controller.GuiController;
import view.GuiView;

public class Main {
    public static final boolean consoleMode = false;
    public static final boolean debug = false;

    public static void main(String[] args) {
        if (consoleMode) {
            ConsolePlayer.play(debug);
        } else {
            GuiView view = new GuiView();
            new GuiController(view, debug);
            view.setVisible(true);
        }
    }
}
