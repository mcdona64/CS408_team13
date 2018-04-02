package HumorBotGUI;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

public class blackCard
{

    public JTextArea in;
    public JFrame f;
    public blackCard()
    {
        f = new JFrame("Black Card");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(300,420);
        f.getContentPane().setBackground(Color.WHITE);

        in = new JTextArea();
        in.setBackground(Color.WHITE);
        in.setLineWrap(true);
        in.setCaretColor(Color.BLACK);
        in.setWrapStyleWord(true);
        in.setSelectedTextColor(Color.BLACK);
        in.setForeground(Color.BLACK);
        in.setFont(new Font("Serif", Font.BOLD, 24));
        in.setBounds(24,24,250,350);

        f.add(in);
        f.setLayout(null);
        f.setVisible(true);
    }

    public String getTxt()
    {
        return in.getText();
    }
}
