import javafx.embed.swing.JFXPanel;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class whiteCard {

    public JTextArea in;
    public int ind;
    public offlineMode ofmode;

    public whiteCard(int index, offlineMode om)
    {
        ind = index;
        ofmode = om;

        JFrame f = new JFrame("White Card" + " " + index);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().setBackground(Color.white);
        f.setSize(300,420);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                getOut();

            }
        });

        JLabel indx = new JLabel(index + "",JLabel.RIGHT);

        indx.setFont(new Font("Serif", Font.PLAIN, 14));
        indx.setBounds(0,400, 200,20);

        in = new JTextArea();
        in.setBackground(Color.white);
        in.setLineWrap(true);
        in.setWrapStyleWord(true);
        in.setSelectedTextColor(Color.black);
        in.setForeground(Color.black);
        in.setFont(new Font("Serif", Font.BOLD, 24));
        in.setBounds(24,26,250,350);

        f.add(in);
        //f.add(indx);
        f.setLayout(null);
        f.setVisible(true);


    }

    public String getTxt()
    {
        return in.getText();

    }

    public int getInd()
    {
        return ind;
    }

    public void getOut()
    {
        ofmode.removeWhiteCard(this);
    }


}
