package com.ttt.ai;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.ttt.control.SimulationController;

public class PlayerConfiguration {
	private String playerName;
	private SimulationController simulationController;

	public PlayerConfiguration(String name, SimulationController clazz) {
		this.playerName = name;
		this.simulationController = clazz;
	}

	public String getPlayerName() {
		return playerName;
	}

	public SimulationController getSimulationController() {
		return simulationController;
	}

	public static PlayerConfiguration load(File config) throws IOException {
		String name = null;
		SimulationController sim = null;
		for (String line : new String(Files.readAllBytes(config.toPath()), "ISO-8859-1").split("\n")) {
			String[] split = line.trim().split(" ", 2);
			if (split[0].equalsIgnoreCase("name")) {
				name = split[1];
			} else if (split[0].equalsIgnoreCase("class")) {
				try {
					sim = (SimulationController) Class.forName(split[1]).newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					throw new IOException(e);
				}
			}
		}
		return new PlayerConfiguration(name, sim);
	}
	
	@Override
	public String toString() {
		return playerName;
	}
}
