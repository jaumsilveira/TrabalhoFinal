package estado;

import util.Token;
import util.TokenType;

public class Estado {

    protected Token nextToken(Codigo codigo) throws Exception {
        Token t = null;
        try {
            t = codigo.next();
        } catch (Exception e) {
            gerarErro(e.getMessage());
        }
        return t;
    }

    protected Token currentToken(Codigo codigo) throws Exception {
        Token t = null;
        try {
            t = codigo.current();
        } catch (Exception e) {
            gerarErro(e.getMessage());
        }
        return t;
    }

    protected void lancarErroEsperado(Token token, String esperado) throws Exception {
        gerarErro("Esperado: " + esperado+ ". Encontrado: " + token.getText());
    }

    protected void lancarErroEsperado(Token token, TokenType esperado) throws Exception {
        gerarErro("Esperado: " + esperado.toString() + ". Encontrado: " + token.getType().toString() + " ("+token.getText()+")");
    }

    protected void lancarErro() throws Exception {
        gerarErro("");
    }

    protected void lancarErro(String erro) throws Exception {
        gerarErro(erro);
    }

    private void gerarErro(String s) throws Exception {
        if ("".equals(s)) {
            s = "Não identificado";
        }
        String erro = "Erro (" + this.getClass().getSimpleName() + "): " + s;
        throw new Exception(erro);
    }

    protected void validarTexto(Codigo codigo, Token token, String texto, String valor) throws Exception {
        if (!texto.equals(token.getText())) {
            lancarErroEsperado(token, texto);
        }
        if (valor != null) {
            codigo.appendCodigo(" " + valor);
        }
    }

    protected void validarTipo(Codigo codigo, Token token, TokenType type, String valor) throws Exception {
        if (!type.equals(token.getType())) {
            lancarErroEsperado(token, type);
        }
        if (valor != null) {
            codigo.appendCodigo(" " + valor);
        }
    }

    protected boolean isTexto(Token token, String texto) throws Exception {
        return texto.equals(token.getText());
    }

    protected boolean isTipo(Token token, TokenType type) throws Exception {
        return type.equals(token.getType());
    }
}
