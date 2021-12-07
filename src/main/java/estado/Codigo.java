package estado;

import util.Token;

import java.util.List;

public class Codigo {
    private List<Token> tokens;
    private StringBuilder codigo;

    private int pos;

    public Codigo(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = -1;
        codigo = new StringBuilder();
    }

    public boolean isEOF() {
        return tokens.isEmpty() || tokens.size() <= pos;
    }

    public Token next() throws Exception {
        pos++;
        return current();
    }

    public Token current() throws Exception {
        if (isEOF()) {
            throw new Exception("Token não localizado na posição " + pos);
        }
        return tokens.get(pos);
    }

    public Token back() throws Exception{
        pos--;
        return current();
    }

    public void appendCodigo(String text) {
        codigo.append(text);
    }

    public String getCodigo() {
        return codigo.toString();
    }
}
