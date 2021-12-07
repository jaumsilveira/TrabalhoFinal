package ui;

import analisador.Compilador;
import analisador.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompiladorFrameView extends JFrame {

    JTextArea textAreaInput = null;
    JTextArea textAreaOutput = null;

    Compilador compilador;

    public CompiladorFrameView() {

        compilador = new Compilador();

        // Proripedades do Frame

        setTitle("Compilador");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width/2, screenSize.height/2);
        setLocationRelativeTo(null);

        // Painel de entrada

        JPanel panelInput = new JPanel();
        textAreaInput = criarTextArea();
        panelInput.add(criarScrollPane(textAreaInput));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input"));
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.PAGE_AXIS));

        // Painel de saída

        JPanel panelOutput = new JPanel();
        textAreaOutput = criarTextArea();
        panelOutput.add(criarScrollPane(textAreaOutput));
        panelOutput.setBorder(BorderFactory.createTitledBorder("Output"));
        panelOutput.setLayout(new BoxLayout(panelOutput, BoxLayout.PAGE_AXIS));

        add(panelInput);
        add(panelOutput);
        add(criarBotoes());
    }

    private JScrollPane criarScrollPane(JTextArea item) {
        JScrollPane scroll = new JScrollPane(item);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private JTextArea criarTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createEmptyBorder());
        return textArea;
    }

    private JComponent criarBotoes() {
        JPanel retorno = new JPanel();
        retorno.setLayout(new GridLayout(1,2));
        retorno.setLayout(new BoxLayout(retorno, BoxLayout.X_AXIS));
        retorno.setBackground(Color.gray);

        JButton botaoCompilar = new JButton("Compilar");
        botaoCompilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compilar();
            }
        });

        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextInput("");
                setTextOutput("");
            }
        });

        retorno.add(botaoCompilar);
        retorno.add(botaoLimpar);
        return retorno;
    }

    private void compilar() {
        compilador.compilar(getTextInput(), new Logger(this));
    }

    public String getTextInput() {
        return textAreaInput.getText();
    }

    public void setTextInput(String texto) {
        textAreaInput.setText(texto);
    }

    public void setTextOutput(String texto) {
        textAreaOutput.setText(texto);
    }

    public void appendOutput(String retorno) {
        LocalDateTime data = LocalDateTime.now();
        String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        if (textAreaOutput.getText().length() > 0) {
            textAreaOutput.append("\n");
        }
        textAreaOutput.append(dataFormatada + " - " + retorno);
    }
}
