package HumorBotGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class onlineMode {

    public onlineMode()
    {
        Frame frame= new JFrame("Online Mode");
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(520,300);
        //f.getContentPane().setBackground(Color.WHITE);
        JLabel title, instr, rec;
        title = new JLabel("ONLINE MODE!",JLabel.CENTER);
        instr = new JLabel("In online mode, the BOT will play Cards Against Humanity on a Website",JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));

        title.setBounds(0,20, 520,20);
        //title.setSize(350,100);
        instr.setBounds(0,100, 520,20);

        JButton b=new JButton("Launch Online Mode");
        b.setBounds(150,200,200,30);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                offlineMode om = new offlineMode();
                //f.dispose();
            }
        });


        frame.add(b);

        //f.add(br);
        //f.add(rec);
        frame.add(title);
        frame.add(instr);
        frame.setLayout(null);
        frame.setVisible(true);


    }

    public static void main(String[]args)
    {
        onlineMode om = new onlineMode();
    }

}
