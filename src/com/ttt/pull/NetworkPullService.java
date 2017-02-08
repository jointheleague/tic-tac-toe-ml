package com.ttt.pull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.ttt.ai.PlayerConfiguration;

public class NetworkPullService {
	public static PlayerConfiguration pullConfiguration(URL host)
			throws IOException, ReflectiveOperationException, InterruptedException {
		URLConnection con = host.openConnection();
		InputStream in = con.getInputStream();

		File zip = new File(LocalPullService.configFolder, new File(host.getFile()).getName());
		byte[] buffer = new byte[4096];
		int n;
		OutputStream output = new FileOutputStream(zip);
		while ((n = in.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
		output.close();

		PlayerConfiguration config = LocalPullService.pullConfiguration(zip);
		zip.delete();
		return config;
	}
}
