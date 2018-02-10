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

    public MainGUI() {
        offlineMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "OFFLINE MODE Has been Launched");
            }
        });
        onlineMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "ONLINE MODE Has been Launched");
            }
        });
        spectatorMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "SPECTATOR MODE Has been Launched");
            }
        });
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "OPTIONS MENU Has been Launched");
            }
        });
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
}
