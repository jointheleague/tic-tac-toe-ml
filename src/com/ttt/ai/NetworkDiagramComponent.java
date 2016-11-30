package com.ttt.ai;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class NetworkDiagramComponent extends JPanel {
	private static final long serialVersionUID = -5674641193361891780L;

	private static final int layerInterval = 100;
	private static final int neuronRadius = 50;
	private static final int half = neuronRadius / 2;

	private static final int width = 800;
	private static final int height = 600;

	private NeuralNetwork network;

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public NetworkDiagramComponent(NeuralNetwork network) {
		super(null);
		this.network = network;
		reset();
	}

	private void reset() {
		removeAll();

		setPreferredSize(new Dimension(width, height));
		setSize(getPreferredSize());

		int y = 0;
		for (Layer layer : network) {
			for (JComponent component : createLayer(layer, y)) {
				add(component);
			}
			y += layerInterval;
		}
	}

	private ArrayList<JComponent> createLayer(Layer layer, int y) {
		ArrayList<JComponent> components = new ArrayList<>();

		for (Neuron neuron : layer) {
			int x = getNeuronX(neuron, y);

			components.add(new NeuronComponent(neuron, x, y));

			if (layer.getDepth() > NeuralNetwork.INPUT_LAYER_DEPTH) {
				for (Synapse in : neuron.getIncomingSynapses()) {
					int fromY = y - layerInterval;
					int fromX = getNeuronX(in.getFiringNeuron(), fromY);

					int xDistance = x - fromX;
					int yDistance = y - fromY;

					components.add(new SynapseComponent(in, fromX + half, fromY + half, x, y, xDistance, yDistance));
				}
			}
		}
		return components;
	}

	private int getNeuronX(Neuron neuron, int y) {
		return ((width / 2) + (-75 / (neuron.getLayer().size() % 2 == 0 ? 2 : 1) * (neuron.getLayer().size() / 2)))
				+ (75 * neuron.getIndexInLayer());
	}

	private class NeuronComponent extends JComponent {
		private static final long serialVersionUID = -8066320545361991554L;
		private Neuron neuron;
		private boolean isHovered;

		private Component box;

		public NeuronComponent(Neuron n, int x, int y) {
			this.neuron = n;

			setBounds(x, y, neuronRadius, neuronRadius);
			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					isHovered = true;
					setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
					invalidate();
					repaint();

					String type;
					if (neuron.getLayer().isInput()) {
						type = "input";
					} else if (neuron.getLayer().isLogic()) {
						type = "logic";
					} else {
						type = "output";
					}

					box = NetworkDiagramComponent.this.add(new PopupComponent(width - 250, height - 100,
							"Location: " + neuron.getIndexInLayer() + " in L" + neuron.getLayer().getDepth(),
							"Type: " + type, "Value: " + neuron.getValue(), "f(Sigmoid): " + neuron.getOutput()));
					NetworkDiagramComponent.this.invalidate();
					NetworkDiagramComponent.this.repaint();
				}

				public void mouseExited(MouseEvent e) {
					isHovered = false;
					setBorder(null);
					invalidate();
					repaint();

					NetworkDiagramComponent.this.remove(box);
					NetworkDiagramComponent.this.invalidate();
					NetworkDiagramComponent.this.repaint();
				}
			});
		}

		@Override
		protected void paintComponent(Graphics ag) {
			super.paintComponent(ag);

			Graphics2D g = (Graphics2D) ag;
			g.setColor(isHovered ? Color.LIGHT_GRAY : Color.GRAY);
			g.fillOval(0, 0, getWidth(), getHeight());

			String str = String.format("%.3f", neuron.getValue());
			int strWidth = g.getFontMetrics().stringWidth(str);
			g.setColor(Color.BLACK);
			g.drawString(str, (neuronRadius - strWidth) / 2, neuronRadius / 2);
		}
	}

	private class SynapseComponent extends JComponent {
		private static final long serialVersionUID = -2908062289251778400L;

		private Synapse synapse;

		private int startX;
		private int endX;

		private boolean isHovered;

		private Component box;

		public SynapseComponent(Synapse synapse, int fromX, int fromY, int x, int y, int relX, int relY) {
			this.synapse = synapse;

			this.endX = relX;

			int xpos = fromX;
			int w = x - fromX;
			if (w < 0) {
				xpos = xpos - Math.abs(w) + half;
				w = Math.abs(w);
			} else {
				w += half;
			}

			if (endX < startX) {
				w -= half;
				startX = w;
				endX = 0;
			}
			if (startX == endX) {
				xpos -= half;
				w += half;
				startX += half;
				endX = startX;
			}

			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					if (lineContains(e.getX(), e.getY(), (float) (Math.abs(synapse.getWeight()) * 10))) {
						isHovered = true;
						setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
						invalidate();
						repaint();

						String type;
						if (synapse.getFiringNeuron().getLayer().isInput()) {
							type = "input to ";
						} else if (synapse.getFiringNeuron().getLayer().isLogic()) {
							type = "logic to ";
						} else {
							type = "output to ";
						}
						if (synapse.getTargetNeuron().getLayer().isInput()) {
							type += "input";
						} else if (synapse.getTargetNeuron().getLayer().isLogic()) {
							type += "logic";
						} else {
							type += "output";
						}

						box = NetworkDiagramComponent.this.add(new PopupComponent(width - 250, height - 100,
								"From: " + synapse.getFiringNeuron().getIndexInLayer() + " in L"
										+ synapse.getFiringNeuron().getLayer().getDepth(),
								"To: " + synapse.getTargetNeuron().getIndexInLayer() + " in L"
										+ synapse.getTargetNeuron().getLayer().getDepth(),
								"Type: " + type, "Weight: " + synapse.getWeight()));
						NetworkDiagramComponent.this.invalidate();
						NetworkDiagramComponent.this.repaint();
					} else {
						undo();
					}
				}

				private boolean lineContains(int x, int y, float width) {
					double rise = 100;
					double run = endX - startX;
					double slope = rise / run;
					if (slope == Double.POSITIVE_INFINITY) {
						slope = 0;
					}
					double line;
					if (slope > 0) {
						line = x * slope;
					} else if (slope < 0) {
						line = rise + x * slope;
					} else {
						line = y;
					}

					return line + 10 > y && line - 10 < y;
				}
			});
			addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					undo();
				}
			});

			setBounds(xpos, fromY, w, layerInterval);
		}

		private void undo() {
			isHovered = false;
			setBorder(null);
			invalidate();
			repaint();

			if (box != null) {
				NetworkDiagramComponent.this.remove(box);
				NetworkDiagramComponent.this.invalidate();
				NetworkDiagramComponent.this.repaint();
			}
		}

		@Override
		protected void paintComponent(Graphics ag) {
			super.paintComponent(ag);

			Graphics2D g = (Graphics2D) ag;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setStroke(new BasicStroke((float) (Math.abs(synapse.getWeight()) * 10), BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER));

			if (synapse.getWeight() < 0) {
				g.setColor(Color.BLACK);
			} else if (synapse.getWeight() > 0) {
				g.setColor(Color.GRAY);
			} else {
				g.setColor(Color.WHITE);
			}
			if (isHovered) {
				g.setColor(g.getColor().brighter());
			}

			g.drawLine(startX, 0, endX, 100);
		}
	}

	private class PopupComponent extends JComponent {
		private static final long serialVersionUID = 7450830033007618915L;
		String[] lines;

		public PopupComponent(int x, int y, String... lines) {
			this.lines = lines;

			setBounds(x, y, 250, 100);
		}

		@Override
		protected void paintComponent(Graphics ag) {
			super.paintComponent(ag);

			Graphics2D g = (Graphics2D) ag;
			g.setColor(Color.WHITE);
			g.fill3DRect(0, 0, getWidth(), getHeight(), true);
			g.setColor(Color.BLACK);

			int y = 15;
			for (String line : lines) {
				g.drawString(line, 5, y);
				y += 15;
			}
		}
	}
}
