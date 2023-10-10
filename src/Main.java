import controller.ConsolePlayer;
import controller.GuiController;
import view.GuiView;

public class Main {
    enum Mode {
        CONSOLE, GUI, WEB
    }
    public static final Mode defMode = Mode.GUI;
    public static final boolean debug = false;

    public static void main(String[] args) {
        Mode mode;
        if (args.length > 0) {
            mode = switch (args[0].toLowerCase()) {
                case "console" -> Mode.CONSOLE;
                case "gui" -> Mode.GUI;
                case "web" -> Mode.WEB;
                default -> defMode;
            };
        } else {
            mode = defMode;
        }

        if (mode == Mode.CONSOLE) {
            ConsolePlayer.play(debug);
        } else if (mode == Mode.GUI) {
            GuiView view = new GuiView();
            new GuiController(view, debug);
            view.setVisible(true);
        }
    }
}
