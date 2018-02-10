package HumorBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Web {
	private String url;
	
	public Web(String url) {
		this.url = url;
	}

	public void grabWebpage() {
		URL u;
		InputStream is = null;
		BufferedReader br;
		String line;
		String fileName = "./web/index.html";
		
		File f = new File(fileName);
		if(f.delete()) {
			System.out.println("File deleted successfully.");
		} else {
			System.out.println("Failed to delete file.");
		}
		
		try {
			u = new URL(this.url);
			URLConnection connection = u.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			while((line = br.readLine()) != null) {
				writer.write(line);
			}
			writer.close();
			System.out.println("Downloaded webpage.");
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
