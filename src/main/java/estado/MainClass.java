package estado;

import util.TokenType;

public class MainClass extends Estado implements MaquinaEstado {

    @Override
    public Codigo analisar(Codigo codigo) throws Exception {
        validarTexto(codigo, nextToken(codigo), "class", "public class");
        validarTipo(codigo, nextToken(codigo), TokenType.IDENTIFIER, currentToken(codigo).getText());
        validarTexto(codigo, nextToken(codigo), "{", currentToken(codigo).getText());
        validarTexto(codigo, nextToken(codigo), "main", "public static void main");
        validarTexto(codigo, nextToken(codigo), "(", "(String[] args");
        validarTexto(codigo, nextToken(codigo), ")", currentToken(codigo).getText());
        validarTexto(codigo, nextToken(codigo), "{", currentToken(codigo).getText());
        codigo = new Statement().analisar(codigo);
        validarTexto(codigo, nextToken(codigo), "}", currentToken(codigo).getText());
        validarTexto(codigo, nextToken(codigo), "}", currentToken(codigo).getText());
        return codigo;
    }
}
