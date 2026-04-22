package org.eternity.adventure.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;


public class GuiGame extends JFrame implements ActionListener, GameLoop, Output{
    private JTextArea display;
    private JTextField input;
    private JButton enter;
    private Game game;

    public GuiGame() {
        super("텍스트 어드벤처 게임");
        buildComponents();
    }

    private void buildComponents() {
        buildFrame();
        buildDisplay();
        buildInput();
    }

    private void buildFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());
    }

    private void buildInput() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        input = new JTextField();
        inputPanel.add(input, BorderLayout.CENTER);

        enter = new JButton("입력");
        inputPanel.add(enter, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        enter.addActionListener(this::actionPerformed);
        input.addActionListener(this::actionPerformed);
    }

    private void buildDisplay() {
        display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        add(new JScrollPane(display), BorderLayout.CENTER);
    }

    // Deprecated
    public void run(Game game) {
        this.game = game;
        this.game.initialize(this);
        this.game.run();
    }

    @Override
    public void play() {
        showLine("");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.executeCommand(input.getText());
        showLine("");
        display.setCaretPosition(display.getDocument().getLength()); 
        input.setText("");
    }

    @Override
    public void stop() {
        new Timer(1000,
                event -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING))).start();
    }
    
    @Override
    public void showLine(String text) {
        display.append(text + "\n");
    }

    @Override
    public void show(String text) {
       display.append(text);
    }
    
}
