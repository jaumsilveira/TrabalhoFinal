package estado;

import util.Token;
import util.TokenType;

public class Statement extends Estado implements MaquinaEstado {

    int fecharColchetes = 1;

    @Override
    public Codigo analisar(Codigo codigo) throws Exception {
        Token token;
        do {
            nextToken(codigo);
            if (isTexto(currentToken(codigo), "}")) {
                fecharColchetes--;
            } else if (isTexto(currentToken(codigo), "{")) {
                fecharColchetes++;
            } else if (isTipo(currentToken(codigo), TokenType.DATA_TYPE)) {
                validarTipo(codigo, currentToken(codigo), TokenType.DATA_TYPE, currentToken(codigo).getText());
                validarTipo(codigo, nextToken(codigo), TokenType.IDENTIFIER, currentToken(codigo).getText());
                validarTipo(codigo, nextToken(codigo), TokenType.OPERATOR, currentToken(codigo).getText());
                validarTipo(codigo, nextToken(codigo), TokenType.NUMBER, currentToken(codigo).getText());
                validarTexto(codigo, nextToken(codigo), ";", currentToken(codigo).getText());

            } else if (isTipo(currentToken(codigo), TokenType.OUTPUT_WORDS)) {
                String aux = currentToken(codigo).getText();
                validarTipo(codigo, currentToken(codigo), TokenType.OUTPUT_WORDS, "System.out.println");
                validarTexto(codigo, nextToken(codigo), "(", currentToken(codigo).getText());
                if ("imprimir.texto".equals(aux)) {
                    validarTipo(codigo, nextToken(codigo), TokenType.IDENTIFIER, "\"" + currentToken(codigo).getText() + "\"");
                } else if ("imprimir.variavel".equals(aux)){
                    validarTipo(codigo, nextToken(codigo), TokenType.IDENTIFIER, currentToken(codigo).getText());
                }
                validarTexto(codigo, nextToken(codigo), ")", currentToken(codigo).getText());
                validarTexto(codigo, nextToken(codigo), ";", currentToken(codigo).getText());
            }
        } while (fecharColchetes > 0);
        codigo.back();
        return codigo;
    }
}
