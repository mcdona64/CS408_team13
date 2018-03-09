/**
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
	 * Client exists for this function, so yeah.
	 * @param args
	 */
	public static void main(String[] args) {
		String name = JOptionPane.showInputDialog("Before we get started: Please input a name for the bot:");
		MCF mcf = new MCF(name);
		menuGui menuGui = new menuGui(mcf);
		//TODO: find a way to start actually running the damned application
	}

}
