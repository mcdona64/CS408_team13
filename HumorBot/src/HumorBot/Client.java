package HumorBot; /**
 * 
 */
import HumorBot.*;
import HumorBotGUI.*;

import javax.swing.*;

/**
 * @author pxeros
 *
 */
public class Client {

	/**
	 * HumorBot.Client exists for this function, so yeah.
	 * @param args
	 */
	public static void main(String[] args) {
		String name = JOptionPane.showInputDialog("Before we get started: Please input a name for the bot:");
		name = "Humorbot";
		//check for good string
		while (name.length() < 3 || name.length() > 20 || name.matches("^.*[^a-zA-Z0-9 ].*$")){
			if (name.length() < 3){
				name = JOptionPane.showInputDialog("name is too short!: Please input a name for the bot:");
			} else if (name.length() > 20){
				name = JOptionPane.showInputDialog("name is too long!: Please input a name for the bot:");
			} else if (name.matches("^.*[^a-zA-Z0-9 ].*$")){
				name =  JOptionPane.showInputDialog("name does not only have letters and numbers!: Please input a name for the bot:");
			}
		}
		MCF mcf = new MCF(name);
		menuGui menuGui = new menuGui(mcf);
	}

}
