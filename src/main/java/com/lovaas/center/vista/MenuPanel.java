package com.lovaas.center.vista;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MenuPanel() {
		setOpaque(false);
	}

	@SuppressWarnings("unchecked")

	/**
	 * Método para crear un degradado para el fondo del JPanel
	 */
	@Override
	protected void paintComponent(Graphics grphcs) {
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint g = new GradientPaint(0, 0, Color.decode("#a8e063"), 0, getHeight(), Color.decode("#106B15"));
		g2.setPaint(g);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
		super.paintComponent(grphcs);
	}

}
