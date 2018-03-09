package HumorBotGUI;

import HumorBot.MCF;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingConstants.*;

public class menuGui
{
    private MCF mcf;
    public menuGui(MCF mcf)
    {
        this.mcf = mcf;
        JFrame f= new JFrame("Main Menu");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f.setSize(500,500);
        f.getContentPane().setBackground(Color.WHITE);
        JLabel title, instr;
        title = new JLabel("Welcome to Humor Bot!",JLabel.CENTER);
        instr = new JLabel("Click on a way to play below!");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));
        //title.setHorizontalAlignment(SwingConstants.CENTER);
        //title.setVerticalAlignment(SwingConstants.CENTER);
        instr.setHorizontalAlignment(SwingConstants.CENTER);
        instr.setVerticalAlignment(SwingConstants.CENTER);
        //title.setSize(100,100);
        title.setBounds(0,20, 500,20);
        //title.setSize(350,100);
        instr.setBounds(0,40, 500,40);

        JLabel rbot = new JLabel("");
        //rbot.setIcon(new ImageIcon(img));
        rbot.setBounds(150,80,200,200);
        Image im = new ImageIcon(this.getClass().getResource("funnybot.jpg")).getImage();
        Image img = im.getScaledInstance(rbot.getWidth(), rbot.getHeight(), Image.SCALE_SMOOTH);
        //JLabel rbot = new JLabel("");
        rbot.setIcon(new ImageIcon(img));
        //rbot.setBounds(130,80,200,250);
        f.getContentPane().add(rbot);

        JButton b=new JButton("Offline Mode");
        b.setBounds(80,300,150,30);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                offlineMode om = new offlineMode(mcf);
                boolean[] g = {false, false};
                mcf.initalize(g);
                f.dispose();
            }
        });

        f.add(b);

        JButton onl=new JButton("Online Mode");
        onl.setBounds(270,300,150,30);

        onl.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                onlineMode olm = new onlineMode(mcf);
                f.dispose();
            }
        });

        f.add(onl);


        JLabel info = new JLabel("Info: Humor bot is an AI project that plays the popular card game,",JLabel.CENTER);
        JLabel info1 = new JLabel(" Cards Against Humanity! Humor bot uses its experience to help you",JLabel.CENTER);
        JLabel info2 = new JLabel("select the best card to win the round or the best card to give up",JLabel.CENTER);
        info.setFont(new Font("Serif", Font.PLAIN, 14));
        info1.setFont(new Font("Serif", Font.PLAIN, 14));
        info2.setFont(new Font("Serif", Font.PLAIN, 14));
        info.setBounds(0,320,500,100);
        info1.setBounds(0,340,500,100);
        info2.setBounds(0,360,500,100);
        f.add(info);
        f.add(info1);
        f.add(info2);
        //rbot.setBounds(130,60, 200,200);


        //f.add(rbot);
        f.add(title);
        f.add(instr);
        f.setLayout(null);
        f.setVisible(true);

        //we should move to room across, cause of above normal asian woman, you know, the one with the highlights


    }





}
