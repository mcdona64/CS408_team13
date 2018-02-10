package HumorBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Web {
	private String url;
	
	public Web(String url) {
		this.url = url;
	}

	public void grabWebPage() {
		URL u;
		InputStream is = null;
		BufferedReader br;
		String line;
		
		
		
		try {
			u = new URL(this.url);
			is = u.openStream();
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if(is != null) is.close();
			} catch (IOException ioe) {
				
			}
		}
		
	}
	
}
