package com.ttt.ai;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class JNetworkPicture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9050528061914777228L;
	private ArrayList<JLayer> layers;
	private ArrayList<JWeightGroup> weights;
	private Color background = Color.WHITE;
	private Color nodes = Color.BLACK;
	private Color lines = Color.BLACK;
	private Color text = Color.WHITE;
	private NumberFormat nf;

	public JNetworkPicture(JNeuralNetwork nn) {
		update(nn);
		nf = NumberFormat.getInstance();
	}

	public void update(JNeuralNetwork nn) {
		this.layers = nn.getLayers();
		this.weights = nn.getWeightGroups();
	}
	
	/**
	 * {@code public void setMaximumFractionDigits(int num)}
	 * 
	 * @param num - The maximum fraction digits.
	 */
	public void setMaximumFractionDigits(int num){
		nf.setMaximumFractionDigits(num);
	}
	
	/**
	 * {@code public void setMinimumFractionDigits(int num)}
	 * 
	 * @param num - The minimum fraction digits.
	 */
	public void setMinimumFractionDigits(int num){
		nf.setMinimumFractionDigits(num);
	}
	
	/**
	 * {@code public void setBackground(Color c)}
	 * 
	 * @param c - The Color of the background.
	 */
	public void setBackground(Color c){
		background = c;
	}
	
	/**
	 * {@code public void setNodes(Color c)}
	 * 
	 * @param c - The Color of the nodes.
	 */
	public void setNodes(Color c){
		nodes = c;
	}

	/**
	 * {@code public void setLines(Color c)}
	 * 
	 * @param c - The Color of the lines.
	 */
	public void setLines(Color c){
		lines = c;
	}
	
	/**
	 * {@code public void setText(Color c)}
	 * 
	 * @param c - The Color of the text.
	 */
	public void setText(Color c){
		text = c;
	}
	
	/**
	 * {@code public void saveImage(BufferedImage image, String path)}
	 * 
	 * @param image - The image to save.
	 * @param path - The path to where to save the file.
	 */
	public void saveImage(BufferedImage image, String path){
		try {
			File outputfile = new File(path);
			ImageIO.write(image, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@code public BufferedImage getNetworkImage(int width, int height, int nodeDiameter)}
	 * 
	 * @param width - The width of the image.
	 * @param height - The height of the image.
	 * @param nodeDiameter - The size of the nodes.
	 * @return A BufferedImage that represents the NeuralNetwork.
	 */
	public BufferedImage getNetworkImage(int width, int height, int nodeDiameter) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = (Graphics2D) image.createGraphics();
		g.setColor(background);
		g.fillRect(0, 0, width, height);
		g.setColor(nodes);

		int ySpacing = (height - nodeDiameter * (layers.size()+1)) / layers.size();
		int y = ySpacing / 2;

		for (int layer = 0; layer < layers.size() - 1; layer++) {
			JLayer l = layers.get(layer);
			JWeightGroup wg = weights.get(layer);
			int curXSpacing = (width - nodeDiameter * l.size()) / l.size();
			int curX = curXSpacing / 2;

			int nextXSpacing = (width - nodeDiameter * layers.get(layer + 1).size()) / layers.get(layer + 1).size();
			int nextX = nextXSpacing / 2;
			double[] ws = wg.getWeights();
			for (int w = 0; w < ws.length; w++) {
				if (w % l.size() == 0 && w != 0) {
					nextX += nextXSpacing + nodeDiameter;
					curX = curXSpacing / 2;
				}

				g.setColor(lines);
				g.setStroke(new BasicStroke((int) (ws[w] * nodeDiameter / 10)));
				g.draw(new Line2D.Float(curX + nodeDiameter / 2, y + nodeDiameter / 2, nextX + nodeDiameter / 2,
						y + ySpacing + nodeDiameter + nodeDiameter / 2));

				g.setColor(nodes);
				g.fillOval(curX, y, nodeDiameter, nodeDiameter);
				g.fillOval(nextX, y + ySpacing + nodeDiameter, nodeDiameter, nodeDiameter);
				g.setColor(text);

				g.drawString(nf.format(l.getNeuron(w % l.size()).getInput()), curX + nodeDiameter / 2,
						y + nodeDiameter / 2);
				g.drawString(nf.format(layers.get(layer + 1).getNeuron(w / l.size()).getInput()),
						nextX + nodeDiameter / 2, y + ySpacing + nodeDiameter + nodeDiameter / 2);


				curX += curXSpacing + nodeDiameter;
			}
			y += ySpacing + nodeDiameter;
		}
		return image;
	}

}
