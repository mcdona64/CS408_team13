package HumorBotGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import HumorBot.*;

public class onlineMode {
    private Frame frame;
    private JButton b;
    private MCF mcf;
    private menuGui menuGui;
    private boolean in_game;
    private short modey;
    private Thread r = new Thread(){
        @Override
        public void run() {
            mcf.doStuff();
        }
    };
    public onlineMode(MCF mcf) {
        this.mcf = mcf;
        this.modey = 0;
        this.in_game = false;
        this.frame = new JFrame("Online Mode");
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.frame.setSize(520, 300);
        //f.getContentPane().setBackground(Color.WHITE);
        JLabel title, instr, mode, rec;
        title = new JLabel("ONLINE MODE!", JLabel.CENTER);
        instr = new JLabel("In online mode, the BOT will play Cards Against Humanity on a Website", JLabel.CENTER);
        mode = new JLabel("Spectator Mode enabled", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));
        mode.setFont(new Font("Serif", Font.PLAIN, 14));

        title.setBounds(0, 20, 520, 20);
        //title.setSize(350,100);
        instr.setBounds(0, 100, 520, 20);
        mode.setBounds(0, 130, 520, 20);

        this.b = new JButton("Launch Online Mode");
        this.b.setBounds(150, 200, 200, 30);

        this.b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(modey == 1){
                    boolean[] g = {true,true};
                    mcf.initalize(g);
                }else{
                    boolean[] h = {true, false};
                    mcf.initalize(h);
                }
                set_line(true);
                in_game = true;
                r.start();
                //f.dispose();
                System.out.println("bollocks");
            }
        });

        JButton scat = new JButton("Return to menu");

        scat.setBounds(362, 238, 150, 30);

        scat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                set_line(false);
                menuGui menuGui = new menuGui(mcf);
                frame.dispose();
            }
        });

        JButton g = new JButton("Set Mode");

        g.setBounds(0, 238,150, 30 );

        g.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!in_game){
                    if(modey == 1){
                        boolean[] h = {true, true};
                        mcf.initalize(h);
                        modey = 0;
                        mode.setText("Spectator Mode Enabled");
                    }else {
                        boolean[] h = {true, false};
                        mcf.initalize(h);
                        modey = 1;
                        mode.setText("Player Mode Enabled");
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Cannot change mode while in play", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        this.frame.add(b);
        this.frame.add(scat);
        this.frame.add(g);
        //f.add(br);
        //f.add(rec);
        this.frame.add(title);
        this.frame.add(instr);
        this.frame.add(mode);
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
                    mcf.break_automation();
                    try {
                        r.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    in_game = false;
                    //f.dispose();
                }
            });
        }else {
            this.b.setText("Launch Online Mode");

            this.b.removeActionListener(this.b.getActionListeners()[0]);

            this.b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    set_line(true);
                    in_game = true;
                    try{
                    r.start();
                    }catch (IllegalThreadStateException y){
                        r = new Thread(){
                            @Override
                            public void run() {
                                mcf.doStuff();
                            }
                        };
                        mcf.initializeWebCrawler();
                        r.start();
                    }
                    //f.dispose();
                }
            });
        }
    }
}
