package com.ttt.net;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.ttt.ai.PlayerConfiguration;

public class NetworkService {
	private static final File configFolder = new File("conf");
	private static final File zip = new File(configFolder, "tmp.zip");
	private static final File tmp = new File(configFolder, "tmp");

	public static PlayerConfiguration pullConfiguration(URL host) throws IOException, ReflectiveOperationException {
		URLConnection con = host.openConnection();
		InputStream in = con.getInputStream();
		zip.delete();
		zip.createNewFile();
		tmp.mkdir();

		byte[] buffer = new byte[4096];
		int n;
		OutputStream output = new FileOutputStream(zip);
		while ((n = in.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
		output.close();
		unzip();

		File[] files = tmp.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cfg");
			}
		});
		files = new File[1];
		files[0] = new File(tmp, "minimax/Minimax.cfg");
		if (files.length > 0) {
			File config = files[0];
			String clazzName = config.getName().substring(0, config.getName().indexOf('.'));
			File clazzFile = new File(tmp, clazzName + "SimulationController.java");
			if (!clazzFile.exists()) {
				throw new PullException("Class file " + clazzName + "SimulationController.java does not exist");
			}
			Files.walk(tmp.toPath()).forEach((e) -> {
				System.out.println(e);
			});
			Class<?> cls = URLClassLoader.newInstance(new URL[] { clazzFile.toURI().toURL() })
					.loadClass(clazzName + "SimulationController");
			return new PlayerConfiguration(clazzName, cls);
		} else {
			throw new PullException("No .cfg file found");
		}
	}

	private static void unzip() throws IOException {
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zip));
		ZipEntry entry = zipIn.getNextEntry();
		while (entry != null) {
			if (entry.isDirectory()) {
				new File(tmp, entry.getName()).mkdir();
			} else {
				extractFile(zipIn, new File(tmp, entry.getName()));
			}

			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	private static void extractFile(ZipInputStream zipIn, File file) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		byte[] bytesIn = new byte[4096];
		int read;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}
