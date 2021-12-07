package analisador;

import util.Token;

import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

    private Logger logger;

    public AnalisadorLexico(Logger logger) {
        this.logger = logger;
    }

    public List<Token> analisar(String texto) throws Exception {
        String retorno = removerComentarios(texto);
        List<Token> tokens = gerarTokens(retorno);
        return tokens;
    }

    private List<Token> gerarTokens(String texto) throws Exception {
        List<Token> retorno = new ArrayList<>();
        Scanner scanner = new Scanner(texto);
        Token t = null;
        do {
            t = scanner.nextToken();
            if (t != null) {
                retorno.add(t);
                logger.log(t.toString());
            }
        } while (t != null);
        return retorno;
    }

    private String removerComentarios(String texto) throws Exception {

        StringBuilder builder = new StringBuilder();

        for (int i = 0 ; i < texto.length() ; i++) {
            char c = texto.charAt(i);
            char n = '\0';
            if (texto.length() != i+1) {
                n = texto.charAt(i + 1);
            }

            if (isCaractereAberturaComentarioBloco(c,n)) {
                int pos = procurarFimComentarioBloco(texto, i);
                i = pos -1;
                continue;
            }

            if (isCaractereComentarioSimples(c, n)) {
                int pos = procurarProximaQuebraLinha(texto, i);
                if (pos < 0) {
                    pos = texto.length();
                    i = pos - 1;
                } else {
                    i = pos - 2;
                }
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private boolean isCaractereComentarioSimples(char c, char n) {
        if (n == '\0') {
            return false;
        }
        return c == '/' && n == '/';
    }

    private boolean isCaractereAberturaComentarioBloco(char c, char n) {
        if (n == '\0') {
            return false;
        }
        return c == '/' && n == '*';
    }

    private boolean isCaractereFimComentarioBloco(char c, char n) {
        if (n == '\0') {
            return false;
        }
        return c == '*' && n == '/';
    }

    private int procurarProximaQuebraLinha(String texto, int pos) {
        String substring = texto.substring(pos);
        int index = substring.indexOf('\n');
        if (index < 0) {
            return index;
        }
        return index + pos + 1;
    }

    private int procurarFimComentarioBloco(String texto, int pos) throws Exception {
        for (int i = pos ; i < texto.length() ; i++) {
            char c = texto.charAt(i);
            char n = '\0';
            if (texto.length() != i+1) {
                n = texto.charAt(i + 1);
            }
            if (isCaractereFimComentarioBloco(c, n)) {
                return i + 2;
            }
        }
        throw new Exception("Comentário localizado na posição '" + pos + "' não fechado corretamente!");
    }
}
