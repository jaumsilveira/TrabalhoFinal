import ui.CompiladorFrameView;
import ui.CompiladorWindowView;

public class Main {

    public static void main (String[] args) {
        CompiladorWindowView compiladorView = new CompiladorWindowView(new CompiladorFrameView());
        compiladorView.setVisible(true);
    }
}
