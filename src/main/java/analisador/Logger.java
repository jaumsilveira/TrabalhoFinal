package analisador;

import ui.CompiladorFrameView;

public class Logger {

    CompiladorFrameView view;

    public Logger(CompiladorFrameView view) {
        this.view = view;
    }

    public void log(String mensagem) {
        view.appendOutput(mensagem);
    }
}
