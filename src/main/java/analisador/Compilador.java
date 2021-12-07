package analisador;

import util.Token;

import java.util.List;

public class Compilador {

    AnalisadorLexico analisadorLexico;
    AnalisadorSintatico analisadorSintatico;

    public void compilar(String texto, Logger logger) {
        analisadorLexico = new AnalisadorLexico(logger);
        analisadorSintatico = new AnalisadorSintatico(logger);

        String retorno = null;
        try {
            List<Token> tokens = analisadorLexico.analisar(texto);
            retorno = analisadorSintatico.analisar(tokens);

        } catch (Exception e) {
            logger.log(e.getMessage());
        }

        if (retorno != null) {
            logger.log(retorno);
            logger.log("Código compilado com sucesso!");
        } else {
            logger.log("Erro ao compilar o Código!");
        }
    }
}
