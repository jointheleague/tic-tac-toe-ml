package com.ttt.pull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

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

		File[] config = { new File(pullDirectory, "minimax/Minimax.cfg") };
		if (config.length > 0) {
			System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_101");

			List<File> files = new ArrayList<>();
			Files.walk(pullDirectory.toPath()).forEach((e) -> {
				File f = e.toFile();
				if (f.getName().endsWith(".java")) {
					files.add(f);
				}
			});
			compile(files);

			return LocalImportService.handleDirectory(pullDirectory);
		} else {
			throw new PullException("No .cfg file found");
		}
	}

	private static void compile(List<File> files) throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<String> optionList = new ArrayList<String>();
		optionList.addAll(Arrays.asList("-classpath", System.getProperty("java.class.path")));
		StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> fileObjects = sjfm
				.getJavaFileObjects((File[]) files.toArray(new File[files.size()]));
		JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, optionList, null, fileObjects);
		task.call();
		sjfm.close();
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
