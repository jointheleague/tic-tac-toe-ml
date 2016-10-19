package org.ai;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class NetworkDiagram {
	private static final int layerInterval = 100;
	private static final int neuronRadius = 50;

	private static final int width = 800;
	private static final int height = 600;

	public static BufferedImage diagram(NeuralNetwork network) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setFont(new Font("Courier", Font.BOLD, 12));

		int y = 0;
		for (Layer layer : network) {
			drawLayer(layer, g2d, y);
			y += layerInterval;
		}

		g2d.dispose();

		return image;
	}

	private static void drawLayer(Layer layer, Graphics2D g2d, int y) {
		final int half = neuronRadius / 2;
		for (Neuron neuron : layer) {
			int x = getNeuronX(neuron, y);

			g2d.setColor(Color.BLUE);
			g2d.fill(new Ellipse2D.Float(x, y, neuronRadius, neuronRadius));

			if (layer.getDepth() > NeuralNetwork.INPUT_LAYER_DEPTH) {
				for (Synapse in : neuron.getIncomingSynapses()) {
					g2d.setStroke(new BasicStroke((float) (Math.abs(in.getWeight()) * 10), BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_MITER));

					if (in.getWeight() < 0) {
						g2d.setColor(Color.BLACK);
					} else if (in.getWeight() > 0) {
						g2d.setColor(Color.GRAY);
					} else {
						g2d.setColor(Color.WHITE);
					}

					int fromY = y - layerInterval;
					int fromX = getNeuronX(in.getFiringNeuron(), fromY);

					Polygon line = new Polygon(new int[] { fromX + half, x + half },
							new int[] { fromY + half, y + half }, 2);
					g2d.draw(line);
				}
			}
			g2d.setColor(Color.WHITE);

			String str = String.format("%.3f", neuron.isInputNeuron() ? neuron.getValue() : neuron.getOutput());
			g2d.drawString(str, x, y + half);
		}
	}

	private static int getNeuronX(Neuron neuron, int y) {
		return ((width / 2) + (-75 * (neuron.getLayer().size() / 2))) + (75 * neuron.getIndexInLayer());
	}
}
