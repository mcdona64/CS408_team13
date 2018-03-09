package HumorBotGUI;

import javax.swing.*;
import java.awt.*;

public class blackCard
{

    public JTextArea in;
    public blackCard()
    {
        JFrame f = new JFrame("Black Card");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(300,420);
        f.getContentPane().setBackground(Color.BLACK);

        in = new JTextArea();
        in.setBackground(Color.black);
        in.setLineWrap(true);
        in.setCaretColor(Color.WHITE);
        in.setWrapStyleWord(true);
        in.setSelectedTextColor(Color.white);
        in.setForeground(Color.white);
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
