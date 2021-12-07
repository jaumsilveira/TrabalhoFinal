package util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Token {
    private TokenType type;
    private String text;
    private int    line;
    private int    column;

    @Override
    public String toString() {
        return "Token ["+line+","+column+"] => type: '" + type + "', text: '" + text + "'";
    }
}
