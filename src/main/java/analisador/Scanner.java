package analisador;

import util.Token;
import util.TokenType;

public class Scanner {
    private String content;

    private int pos;
    private int line;
    private int column;

    private String auxOperador;

    private static int ESTADO_INICIAL = 0;
    private static int ESTADO_LENDO_TEXTO = 1;
    private static int ESTADO_LENDO_DIGITO = 2;
    private static int ESTADO_LENDO_OPERADOR = 3;
    private static int ESTADO_LENDO_COMENTARIO = 4;

    private String[] tipoDeDados = {
            "int",
            "boolean"
    };

    private String[] outputWords = {
        "imprimir.texto",
        "imprimir.variavel",
    };

    private String[] palavrasReservadas = {
        "class",
        "extends",
        "public",
        "static",
        "void",
        "main",
        "String",
        "return",
        "if",
        "else",
        "while",
        "length",
        "true",
        "false",
        "this",
        "new",
        "null"
    };

    private String[] opeadores = {
            "(",
            ")",
            "[",
            "]",
            "{",
            "}",
            ";",
            ".",
            ",",
            "==",
            "=",
            "<",
            "!=",
            "+",
            "-",
            "*",
            "/",
            "&&",
            "!"
    };

    public Scanner (String texto) {
        setContent(texto);
    }

    public void setContent(String texto) {
        this.content = texto;
        this.pos = 0;
        this.line = 0;
        this.column = 0;
    }

    public Token nextToken() throws Exception {
        char currentChar = '\0';
        String term = "";

        if (isEOF()) {
            return null;
        }

        int estado = ESTADO_INICIAL;
        while(true) {
            currentChar = nextChar();
            column++;

            switch(estado) {
                case 0:
                    if (isChar(currentChar)) {
                        term += currentChar;
                        estado = ESTADO_LENDO_TEXTO;
                    }
                    else if (isDigit(currentChar)) {
                        estado = ESTADO_LENDO_DIGITO;
                        term += currentChar;
                    }
                    else if (isSpace(currentChar)) {
                        estado = ESTADO_INICIAL;
                    }
                    else if (isOperator()) {
                        estado = ESTADO_INICIAL;
                        if (auxOperador.length() > 1) {
                            pos++;
                        }
                        term += auxOperador;
                        return Token.builder()
                                .type(TokenType.OPERATOR)
                                .text(term)
                                .line(line)
                                .column(column - term.length())
                                .build();
                    }
                    else {
                        throw new Exception("Símbolo não reconhecido");
                    }
                    break;
                case 1:
                    if (isChar(currentChar) || isDigit(currentChar) || currentChar == '.') {
                        estado = 1;
                        term += currentChar;
                    }
                    else if (isSpace(currentChar) || isOperator() || isEOF(currentChar)){
                        if (!isEOF(currentChar))
                            back();
                        TokenType identifier = TokenType.IDENTIFIER;
                        if (isTipoDeDado(term)) {
                            identifier = TokenType.DATA_TYPE;
                        } else if (isPalavraReservada(term)) {
                            identifier = TokenType.RESERVED_IDENTIFIER;
                        } else if (isOutputWords(term)) {
                            identifier = TokenType.OUTPUT_WORDS;
                        }
                        return Token.builder()
                                .type(identifier)
                                .text(term)
                                .line(line)
                                .column(column - term.length())
                                .build();
                    }
                    else {
                        throw new Exception("Identificador não reconhecido");
                    }
                    break;
                case 2:
                    if (isDigit(currentChar) || currentChar == '.') {
                        estado = 2;
                        term += currentChar;
                    }
                    else if (!isChar(currentChar) || isEOF(currentChar)) {
                        if (!isEOF(currentChar))
                            back();
                        return Token.builder()
                                .type(TokenType.NUMBER)
                                .text(term)
                                .line(line)
                                .column(column - term.length())
                                .build();
                    }
                    else {
                        throw new Exception("Número não reconhecido");
                    }
                    break;
            }
        }
    }

    private boolean isTipoDeDado(String texto) {
        for (String s : tipoDeDados) {
            if (texto.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPalavraReservada(String texto) {
        for (String s : palavrasReservadas) {
            if (texto.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutputWords(String texto) {
        for (String s : outputWords) {
            if (texto.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
    }

    private boolean isOperator() {
        String parte = content.substring(pos-1);
        for (String s : opeadores) {
            if (parte.startsWith(s)) {
                auxOperador = s;
                return true;
            }
        }
        return false;
    }
    private boolean isSpace(char c) {
        if (c == '\n' || c== '\r') {
            line++;
            column=0;
        }
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private char nextChar() {
        if (isEOF()) {
            return '\0';
        }
        return content.charAt(pos++);
    }
    private boolean isEOF() {
        return pos >= content.length();
    }

    private void back() {
        pos--;
        column--;
    }

    private boolean isEOF(char c) {
        return c == '\0';
    }
}
