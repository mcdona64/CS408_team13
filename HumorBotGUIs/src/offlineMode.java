import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class offlineMode
{
    public offlineMode()
    {
        JFrame f= new JFrame("Offline Mode");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f.setSize(500,400);
        f.getContentPane().setBackground(Color.WHITE);
        JLabel title, instr, rec;
        title = new JLabel("OFFLINE MODE!",JLabel.CENTER);
        instr = new JLabel("In offline mode, the GUI will emulate your hand and give you advice",JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));

        rec = new JLabel("(Bot Recomendations will be shown here)!",JLabel.CENTER);
        rec.setFont(new Font("Serif", Font.PLAIN, 14));

        rec.setBounds(0,150,500,20);;
        title.setBounds(0,20, 500,20);
        //title.setSize(350,100);
        instr.setBounds(0,50, 500,20);

        JButton b=new JButton("Add White Card");
        b.setBounds(175,300,150,30);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                offlineMode om = new offlineMode();
                //f.dispose();
            }
        });

        JButton br=new JButton("Be Funny!");
        br.setBounds(200,200,100,20);

        br.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                offlineMode om = new offlineMode();
                //f.dispose();
            }
        });

        f.add(b);

        f.add(br);
        f.add(rec);
        f.add(title);
        f.add(instr);
        f.setLayout(null);
        f.setVisible(true);


    }

    public static void main(String[]args)
    {
        offlineMode om = new offlineMode();
    }

}
