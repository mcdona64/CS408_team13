package HumorBotGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import HumorBot.*;

public class onlineMode {
    private Frame frame;
    private JButton b;
    public onlineMode() {
        this.frame = new JFrame("Online Mode");
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.frame.setSize(520, 300);
        //f.getContentPane().setBackground(Color.WHITE);
        JLabel title, instr, rec;
        title = new JLabel("ONLINE MODE!", JLabel.CENTER);
        instr = new JLabel("In online mode, the BOT will play Cards Against Humanity on a Website", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));

        title.setBounds(0, 20, 520, 20);
        //title.setSize(350,100);
        instr.setBounds(0, 100, 520, 20);

        this.b = new JButton("Launch Online Mode");
        this.b.setBounds(150, 200, 200, 30);

        this.b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                set_line(true);
                //f.dispose();
            }
        });




        this.frame.add(b);

        //f.add(br);
        //f.add(rec);
        this.frame.add(title);
        this.frame.add(instr);
        this.frame.setLayout(null);
        this.frame.setVisible(true);


    }

    public void set_line(boolean online){
        if(online){
            //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.b.setText("Exit Online Mode");

            this.b.removeActionListener(this.b.getActionListeners()[0]);

            this.b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    set_line(false);
                    //f.dispose();
                }
            });
        }else {
            this.b.setText("Launch Online Mode");

            this.b.removeActionListener(this.b.getActionListeners()[0]);

            this.b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    set_line(true);
                    //f.dispose();
                }
            });
        }
    }

    public static void main(String[]args)
    {
        onlineMode om = new onlineMode();
    }

}
