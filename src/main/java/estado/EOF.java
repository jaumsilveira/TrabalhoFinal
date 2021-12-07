package estado;

public class EOF extends Estado implements MaquinaEstado {

    @Override
    public Codigo analisar(Codigo codigo) throws Exception {
        try {
            codigo.next();
            lancarErro("Fim do texto não localizado! Token inexperado: " + codigo.current().getText());
        } catch (Exception e) {
            if (!codigo.isEOF()) {
                lancarErro("Fim do texto espeado!");
            }
        }
        return codigo;
    }
}
