package HumorBotGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    private JButton offlineMode;
    private JButton options;
    private JButton onlineMode;
    private JButton spectatorMode;
    private JPanel panelMenu;
    private boolean online_Mode;
    private boolean specMode;
    private String nameEntry;

    public MainGUI() {
    	this.online_Mode = this.specMode = false;
        offlineMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "OFFLINE MODE Has been Launched");
            }
        });
        onlineMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               flipOnlineMode();
               nameEntry = nameSet();
            }
        });
        spectatorMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipSpecMode();
                flipOnlineMode();
                nameEntry = nameSet();
            }
        });
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "OPTIONS MENU Has been Launched");
            }
        });
    }
    
    public void flipOnlineMode(){
    	this.online_Mode = !this.online_Mode;
    }
    
    public void flipSpecMode(){
    	this.specMode = !this.specMode;
    }

    public boolean isOnline_Mode(){
        return this.online_Mode;
    }

    public boolean isSpecMode() {
        return specMode;
    }

    public String getName(){
        return this.nameEntry;
    }

    public void launchStartMenu() {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainGUI().panelMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MainGUI m = new MainGUI();
        m.launchStartMenu();
    }

    public String nameSet(){
        return JOptionPane.showInputDialog(null, "Please input your name");
    }
}
