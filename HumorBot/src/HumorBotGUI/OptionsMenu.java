package HumorBotGUI;

import HumorBot.MCF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsMenu {
    private Frame frame;
    private MCF mcf;

    //constructor
    public OptionsMenu(MCF mcf) {
        // set the main variables and make the frame
        this.mcf = mcf;
        this.frame = new JFrame("Options");
        this.frame.setSize(520, 300);


        // add main instructions label and title
        JLabel title, instr, mode, rec;
        title = new JLabel("Options Menu!", JLabel.CENTER);
        instr = new JLabel("<html><center>In the options menu you can change the way<br/> the AI plays the game, and see how many wins<br/> and losses it has.<center></html>", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));
        title.setBounds(0, 20, 520, 20);
        instr.setBounds(0, 70, 520, 50);
        this.frame.add(title);
        this.frame.add(instr);

        // add label for wins and losses
        JLabel wins = new JLabel("Wins: " + mcf.getwins(), JLabel.RIGHT);
        wins.setFont(new Font("Serif", Font.BOLD, 14));
        wins.setBounds(100, 140, 100, 15);
        this.frame.add(wins);
        JLabel losses = new JLabel("Losses: " + mcf.getlosses(), JLabel.RIGHT);
        losses.setFont(new Font("Serif", Font.BOLD, 14));
        losses.setBounds(260, 140, 100, 15);
        this.frame.add(losses);



        // Add back button
        JButton back_button = new JButton("Return to menu");
        back_button.setBounds(362, 238, 150, 30);

        back_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuGui menuGui = new menuGui(mcf);
                frame.dispose();
            }
        });
        this.frame.add(back_button);



        // add option for do not edit the db
        JLabel learn = new JLabel("Have the AI learn from games?", JLabel.RIGHT);
        learn.setFont(new Font("Serif", Font.BOLD, 14));
        learn.setBounds(20, 180, 300, 15);
        this.frame.add(learn);
        JCheckBox learncb = new JCheckBox();
        learncb.setSelected(true);
        learncb.setBounds(330,180,20,15);
        this.frame.add(learncb);


        // add option for playing in naive mode
        JLabel naive = new JLabel("Have the AI play in naive mode?", JLabel.RIGHT);
        naive.setFont(new Font("Serif", Font.BOLD, 14));
        naive.setBounds(20, 210, 300, 15);
        this.frame.add(naive);
        JCheckBox naivecb = new JCheckBox();
        naivecb.setSelected(false);
        naivecb.setBounds(330,210,20,15);
        this.frame.add(naivecb);


        // Add set button
        JButton set_button = new JButton("Apply Changes");
        set_button.setBounds(10, 238, 150, 30);
        set_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mcf.setupdatedb(learncb.isSelected());
                mcf.setnaivemode(naivecb.isSelected());
            }
        });
        this.frame.add(set_button);




        // make the frame
        this.frame.setLayout(null);
        this.frame.setVisible(true);
    }


}
