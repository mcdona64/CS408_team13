package HumorBotGUI;

import HumorBot.ConnectionNotEstablishedException;
import HumorBot.DatabaseInterface;
import HumorBot.MCF;
import HumorBot.WhiteCard;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class offlineMode
{
    ArrayList<whiteCard> wcards = new ArrayList<whiteCard>();
    blackCard blc;
    int cnt = 0;
    private MCF mcf;
    private menuGui menuGui;
    public offlineMode(MCF mcf)
    {
        this.mcf = mcf;
        JFrame f= new JFrame("Offline Mode");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        f.setSize(520,400);
        f.getContentPane().setBackground(Color.WHITE);
        JLabel title, instr, rec;
        title = new JLabel("OFFLINE MODE!",JLabel.CENTER);
        instr = new JLabel("In offline mode, the GUI will emulate your hand and give you advice.",JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        instr.setFont(new Font("Serif", Font.PLAIN, 14));

        rec = new JLabel("(Bot Recomendations will be shown here)!",JLabel.CENTER);
        rec.setFont(new Font("Serif", Font.PLAIN, 14));

        rec.setBounds(0,150,500,20);;
        title.setBounds(0,20, 500,20);
        //title.setSize(350,100);
        instr.setBounds(0,50, 500,20);

        JButton b=new JButton("Add White Card");
        b.setBounds(185,300,150,30);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                //f.dispose();
                addWhiteCard();
            }
        });

        JButton br=new JButton("Be Funny!");
        br.setBounds(185,200,150,20);

        br.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //offlineMode om = new offlineMode();
                //f.dispose()
                //use blackcard text and arraylist of white card texts to return best card index and worst card index
                String blackcardtext = blc.getTxt();
                ArrayList<String> whitecardtext = new ArrayList<String>();
                ArrayList<WhiteCard> cardz = new ArrayList<WhiteCard>();
                for(int i = 0; i < wcards.size();i++)
                {
                    whitecardtext.add(wcards.get(i).getTxt());
                    cardz.add(new WhiteCard(wcards.get(i).getTxt()));
                }
                mcf.setHand(cardz);
                int[] stuff = mcf.makeDecision(blackcardtext);

                //Insert MOFO function that gives best card index and worst card index
                //use white card function get index to actually get the index, not the index in the arrayList.

                rec.setText(getRecs(blackcardtext,stuff));

                if(JOptionPane.showConfirmDialog(null, "Did You win?",
                        "Success", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    mcf.handleWin(stuff);
                }else {
                    try {
                        DatabaseInterface db = new DatabaseInterface();
                        db.inclosses();
                    } catch (ConnectionNotEstablishedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        JButton scat = new JButton("Return to menu");

        scat.setBounds(361, 330, 150, 30);

        scat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blc.f.dispose();
                for (int i = 0; i < wcards.size(); i++)
                    wcards.get(i).f.dispose();
                wcards.clear();
                menuGui menuGui = new menuGui(mcf);
                f.dispose();
            }
        });




        f.add(b);
        f.add(scat);

        f.add(br);
        f.add(rec);
        f.add(title);
        f.add(instr);
        f.setLayout(null);
        f.setVisible(true);

        blackCard bc = new blackCard();
        blc = bc;



    }

    public String getRecs(String blctxt, int[] stuff)
    {
        String rec = "Card(s) to pick is/are: ";
        for(int i = 0; i < stuff.length; i++){
            if(i == 0){
                rec = rec + "" + wcards.get(stuff[i]).getInd();
            }else{
                rec = rec + ", " + wcards.get(stuff[i]).getInd();
            }
        }
        return rec;
    }

    public void removeWhiteCard(whiteCard i)
    {
        wcards.remove(i);
    }

    public void addWhiteCard()
    {
        cnt++;
        whiteCard whte = new whiteCard(cnt,this);
        wcards.add(whte);
    }


}
