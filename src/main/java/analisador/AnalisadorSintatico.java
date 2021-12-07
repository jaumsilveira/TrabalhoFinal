package analisador;

import estado.Codigo;
import estado.Goal;
import util.Token;

import java.util.List;

public class AnalisadorSintatico {

    private Logger logger;

    public AnalisadorSintatico(Logger logger) {
        this.logger = logger;
    }

    public String analisar(List<Token> tokens) throws Exception {
        Codigo codigo = new Codigo(tokens);
        codigo = new Goal().analisar(codigo);
        return codigo.getCodigo();
    }

}
