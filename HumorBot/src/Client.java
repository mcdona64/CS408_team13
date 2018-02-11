/**
 * 
 */
import HumorBot.*;
import HumorBotGUI.*;
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
		MainGUI screen = new MainGUI();
		MCF mcf = new MCF();
		screen.launchStartMenu();
		boolean[] init_flags = {screen.isOnline_Mode(), screen.isSpecMode(), false, false, false, false};
		mcf.initalize(init_flags);
		//TODO: find a way to start actually running the damned application
	}

}
