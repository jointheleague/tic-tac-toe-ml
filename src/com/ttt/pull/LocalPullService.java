package com.ttt.pull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.ttt.ai.PlayerConfiguration;

public class LocalPullService {
	static final File configFolder = new File("conf");
	private static File pullDirectory;

	private static class Obj<T> {
		T o;
	}

	public static PlayerConfiguration pullConfiguration(File file)
			throws IOException, ReflectiveOperationException, InterruptedException {
		pullDirectory = new File(configFolder, file.getName().split(Pattern.quote("."))[0]);
		if (pullDirectory.exists()) {
			throw new PullException("Already pulled AI named " + pullDirectory.getName());
		}

		Obj<Exception> h = new Obj<>();
		Obj<PlayerConfiguration> c = new Obj<>();
		Thread thread = new Thread(() -> {
			try {
				c.o = pullConfigurationUnthreaded(file);
			} catch (IOException | ReflectiveOperationException e) {
				h.o = e;
			}
		});
		thread.start();
		thread.join();

		if (h.o != null) {
			if (h.o instanceof IOException) {
				throw (IOException) h.o;
			} else if (h.o instanceof ReflectiveOperationException) {
				throw (ReflectiveOperationException) h.o;
			}
		}

		return c.o;
	}

	private static PlayerConfiguration pullConfigurationUnthreaded(File file)
			throws IOException, ReflectiveOperationException {
		pullDirectory.mkdir();
		unzip(file);

		return LocalImportService.handleDirectory(pullDirectory);
	}

	private static void unzip(File zip) throws IOException {
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zip));
		ZipEntry entry = zipIn.getNextEntry();
		while (entry != null) {
			if (entry.isDirectory()) {
				new File(pullDirectory, entry.getName()).mkdirs();
			} else {
				File file = new File(pullDirectory, entry.getName());
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				extractFile(zipIn, file);
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
