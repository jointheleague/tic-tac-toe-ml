package com.ttt.pull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.ttt.ai.PlayerConfiguration;
import com.ttt.control.SimulationController;

public class LocalImportService {
	private static HashMap<String, SimulationController> controllers = new HashMap<>();

	public static SimulationController getController(String name) {
		return controllers.get(name);
	}

	private static class Obj<T> {
		T o;
	}

	@SuppressWarnings("unchecked")
	public static PlayerConfiguration handleDirectory(File dir) throws IOException, ReflectiveOperationException {
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { dir.toURI().toURL() });
		Obj<Class<? extends SimulationController>> cls = new Obj<>();
		Obj<ReflectiveOperationException> ex = new Obj<>();

		Files.walk(dir.toPath()).forEach(path -> {
			try {
				File f = path.toFile();
				if (f.getName().endsWith(".class")) {
					String fullyQualifiedClassName = f.getPath().split(Pattern.quote(File.separator), 3)[2]
							.replace(File.separatorChar, '.').replace(".class", "");
					Class<?> loaded = classLoader.loadClass(fullyQualifiedClassName);
					if (SimulationController.class.isAssignableFrom(loaded)) {
						cls.o = (Class<? extends SimulationController>) loaded;
					}
				}
			} catch (ReflectiveOperationException e) {
				ex.o = e;
			}
		});

		if (ex.o != null) {
			throw ex.o;
		}
		if (cls.o == null) {
			throw new PullException("No SimulationController found");
		}
		PlayerConfiguration conf = new PlayerConfiguration(dir.getName(), cls.o);
		controllers.put(conf.getPlayerName(), conf.getSimulationController());
		return conf;
	}
}
