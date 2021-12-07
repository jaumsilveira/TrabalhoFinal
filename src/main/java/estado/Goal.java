package estado;

public class Goal extends Estado implements MaquinaEstado {

    @Override
    public Codigo analisar(Codigo codigo) throws Exception {
        codigo = new MainClass().analisar(codigo);
        codigo = new EOF().analisar(codigo);
        return codigo;
    }
}
