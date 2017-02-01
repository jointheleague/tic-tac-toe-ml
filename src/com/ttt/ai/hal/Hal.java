package com.ttt.ai.hal;

import static com.ttt.ai.hal.HALMainController.size;

import java.util.HashMap;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

import com.ttt.ai.minimax.Minimax;
import com.ttt.ai.rando.Rando;
import com.ttt.control.GameController;
import com.ttt.model.Board;
import com.ttt.model.Brain;
import com.ttt.model.Player;
import com.ttt.model.Tile;
import com.ttt.model.TilePosition;

public class Hal {
	private BasicNetwork network;
	private HashMap<Integer, int[]> map = new HashMap<>();

	public Hal(BasicNetwork network) {
		this.network = network;
		int key = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int l[] = { i, j };
				map.put(key, l);
				key++;
			}
		}
	}

	private TilePosition compute(Tile[][] tiles, double[] value) {
		int maxIndex = 0;
		double max = value[0];
		for (int i = 0; i < value.length; i++) {
			if (value[i] >= max) {
				max = value[i];
				maxIndex = i;
			}
		}
		TilePosition tile = new TilePosition(maxIndex % 3, maxIndex / 3);
		if (tiles[tile.getX()][tile.getY()] == Tile.EMPTY) {
			return tile;
		}
		return null;
	}

	public double scorePilot() {
		Board board = new Board();

		Brain brain = new Minimax(1, Tile.X);
		//Brain brain = new Rando();
		GameController controller = new GameController(new Player("Opposer", Tile.X, brain),
				new Player("HAL 9000", Tile.O, new Brain() {
					@Override
					public TilePosition getNextMove(Tile[][] tiles) {
						MLData input = new BasicMLData(size * size);
						int times = 0;
						for (int i = 0; i < size; i++) {
							for (int j = 0; j < size; j++) {
								input.add(times, board.getTile(i, j).getIndex());
								times++;
							}
						}

						MLData output = network.compute(input);
						double[] value = output.getData();
						TilePosition tile = compute(tiles, value);
						if (tile == null) {
							for (int x = 0; x < size; x++) {
								for (int y = 0; y < size; y++) {
									if (board.getTile(x, y) == Tile.EMPTY) {
										return new TilePosition(x, y);
									}
								}
							}
						}
						return tile;
					}
				}), board);
		Player winner = controller.playGame();
		if (winner == null) {
			log("Tie");
			return scoreFactor / 15 + (won * 10);
		} else if (winner.getLabel().equals("HAL 9000")) {
			log("Winner");
			return scoreFactor + (won * 25);
		} else {
			log("Loser");
			return -scoreFactor / 500;
		}
	}

	private static final int scoreFactor = Integer.MAX_VALUE / 50;

	private static float won = 0;
	private static float total = 0;

	private static void log(String str) {
		total++;
		if (str.equals("Winner")) {
			won++;
		}
		System.out.println(str + " (" + ((won / total) * 100) + "% won)");
	}
}
