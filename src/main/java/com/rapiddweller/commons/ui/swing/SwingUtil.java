/*
 * Copyright (C) 2004-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.commons.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Rectangle2D;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreeModel;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.commons.format.Alignment;
import com.rapiddweller.commons.format.PadFormat;

/**
 * Provides Swing utilities.
 * Created: 23.04.2007 22:41:21
 * 
 * @since 0.5.13
 * @author Volker Bergmann
 */
public class SwingUtil {
	
	private static final String LF = SystemInfo.getLineSeparator();
	
	public static String formatTree(JTree tree) {
		StringBuilder builder = new StringBuilder();
		addTreeNode(tree.getModel().getRoot(), tree, "", new ArrayList<Object>(), builder);
		return builder.toString();
	}
	
	private static void addTreeNode(Object node, JTree tree, String indent, List<Object> ancestors, StringBuilder builder) {
		builder.append(indent);
		Component renderer = tree.getCellRenderer().getTreeCellRendererComponent(tree, node, false, true, false, 0, false);
		String text = String.valueOf(node);
		if (renderer instanceof JLabel)
			text = ((JLabel) renderer).getText();
		builder.append(text).append(LF);
		Object baseNode = node;
		if (node instanceof Map.Entry)
			baseNode = ((Map.Entry<?,?>) node).getValue(); 
		boolean recursion = ancestors.contains(baseNode);
		if (!recursion) {
			ancestors.add(baseNode);
			TreeModel model = tree.getModel();
			for (int i = 0; i < model.getChildCount(node); i++) {
				Object child = model.getChild(node, i);
				if (!ancestors.contains(child))
					addTreeNode(child, tree, indent + "  ", ancestors, builder);
			}
			ancestors.remove(ancestors.size() - 1);
		}
	}

	public static String formatTablePadded(JTable table) {
		// get table cells
		String[][] cells = parseTable(table);
		// determine column widths
		PadFormat[] fmts = new PadFormat[cells[0].length];
		for (int colnum = 0; colnum < cells[0].length; colnum++) {
			int maxWidth = cells[0][colnum].length();
			for (int rownum = 1; rownum < cells.length; rownum++) {
				String cell = cells[rownum][colnum];
				if (cell.length() > maxWidth)
					maxWidth = cell.length();
			}
			fmts[colnum] = new PadFormat("", maxWidth, Alignment.RIGHT, ' ');
		}
		// format cells
		StringWriter writer = new StringWriter();
		for (int rownum = 0; rownum < cells.length; rownum++) {
			for (int colnum = 0; colnum < cells[rownum].length; colnum++) {
				writer.write(fmts[colnum].format(cells[rownum][colnum]));
				if (colnum < cells[rownum].length - 1)
					writer.write(' ');
			}
			if (rownum < cells.length - 1)
				writer.write(LF);
		}
		return writer.toString();
	}

	public static void printTableTabsSeparated(JTable table) {
		String[][] cells = parseTable(table);
		for (int rownum = 0; rownum < cells.length; rownum++) {
			for (int colnum = 0; colnum < cells[rownum].length; colnum++)
				System.out.print(cells[rownum][colnum] + "\t");
			System.out.println();
		}
	}
	
	public static String[][] parseTable(JTable table) {
		String[][] result = new String[table.getRowCount() + 1][];
		TableModel model = table.getModel();
		result[0] = new String[table.getColumnCount()];
		for (int colnum = 0; colnum < model.getColumnCount(); colnum++)
			result[0][colnum] = model.getColumnName(colnum);
		for (int rownum = 0; rownum < model.getRowCount(); rownum++) {
			result[rownum + 1] = new String[table.getColumnCount()];
			for (int colnum = 0; colnum < model.getColumnCount(); colnum++) {
				TableCellRenderer renderer = table.getCellRenderer(rownum, colnum);
				Object value = model.getValueAt(rownum, colnum);
				Component renderComp = renderer.getTableCellRendererComponent(table, value, false, false, rownum, colnum);
				String formattedValue;
				if (renderComp instanceof JLabel)
					formattedValue = ((JLabel) renderComp).getText();
				else
					formattedValue = String.valueOf(value);	
				result[rownum + 1][colnum] = formattedValue;
			}
		}
		return result;
	}
	
	public static void repaintLater(final Component component) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				component.repaint();
			}
		});
	}

	public static void center(Component component) {
		Dimension screenSize = getScreenSize();
		int x = (screenSize.width - component.getWidth()) / 2;
		int y = (screenSize.height - component.getHeight()) / 2;
		component.setLocation(x, y);
	}

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public static <T extends Component> T showInModalDialog(T mainComponent, String title, boolean cancellable,
			Component parentComponent) {
		return SimpleDialog.showModalDialog(mainComponent, title, cancellable, parentComponent);
	}

	public static void showInFrame(Component component, String title) {
		JFrame frame = new JFrame(title);
		frame.getContentPane().add(component, BorderLayout.CENTER);
		frame.pack();
		center(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static Rectangle fitRectangles(Dimension imageSize, Dimension size) {
		double aspectX = (double) size.width / imageSize.width;
		double aspectY = (double) size.height / imageSize.height;
		double aspect = Math.min(aspectX, aspectY);
		int paintedWidth = (int) (imageSize.width * aspect);
		int paintedHeight = (int) (imageSize.height * aspect);
		int x = (size.width - paintedWidth) / 2;
		int y = (size.height - paintedHeight) / 2;
		return new Rectangle(x, y, paintedWidth, paintedHeight);
	}

	public static boolean isLookAndFeelNative() {
		return UIManager.getSystemLookAndFeelClassName().equals(UIManager.getLookAndFeel().getClass().getName());
	}

	public static Window getWindowForComponent(Component parentComponent) {
		if (parentComponent == null)
			return null;
		if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return getWindowForComponent(parentComponent.getParent());
	}

	public static void equalizeButtonSizes(Graphics g, JButton... buttons) {

		String[] labels = BeanUtil.extractProperties(buttons, "text", String.class);

		// Get the largest width and height
		Dimension maxSize = new Dimension(0, 0);
		Rectangle2D textBounds = null;
		JButton button0 = buttons[0];
		FontMetrics metrics = button0.getFontMetrics(button0.getFont());
		for (int i = 0; i < labels.length; ++i) {
			textBounds = metrics.getStringBounds(labels[i], g);
			maxSize.width = Math.max(maxSize.width, (int) textBounds.getWidth());
			maxSize.height = Math.max(maxSize.height, (int) textBounds.getHeight());
		}

		Insets insets = button0.getBorder().getBorderInsets(button0);
		maxSize.width += insets.left + insets.right;
		maxSize.height += insets.top + insets.bottom;

		// reset preferred and maximum size since BoxLayout takes both into account
		for (JButton button : buttons) {
			button.setPreferredSize((Dimension) maxSize.clone());
			button.setMaximumSize((Dimension) maxSize.clone());
		}
	}
	
	public static JButton insertTransparentIconButton(Action action, JToolBar toolBar) {
		return insertTransparentButton(action, false, toolBar);
	}

	public static JButton insertTransparentButton(Action action, boolean withText, JToolBar toolBar) {
		JButton button = toolBar.add(action);
		configureTransparentButton(button, withText);
		return button;
	}

	public static void insertTransparentIconButton(JButton button, JToolBar toolBar) {
		insertTransparentButton(button, false, toolBar);
	}

	public static void insertTransparentButton(JButton button, boolean withText, JToolBar toolBar) {
		configureTransparentButton(button, withText);
		toolBar.add(button);
	}

	private static JButton configureTransparentButton(JButton button, boolean withText) {
		if (withText) {
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
		} else {
			button.setText("");
			button.setMargin(new Insets(0, 0, 0, 0));
		}
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		return button;
	}
 
	public static Color getUIPanelBackground() {
		return getUIColor("Panel.background");
	}

	public static Color getUIColor(String code) {
		Color color = UIManager.getColor(code);
		// workaround for issue with com.apple.laf.AquaNativeResources$CColorPaintUIResource which seems to be rendered with alpha=0
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public static Color semiTransparentColor(Color color) {
		return applyAlpha(color, 128);
	}
	
	public static Color applyAlpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}
	
	public static void bindKeyToAction(int keyCode, int modifiers, Action action, JComponent component) {
		bindKeyToAction(keyCode, modifiers, action, component, JComponent.WHEN_FOCUSED);
	}
 
	public static void bindKeyToAction(int keyCode, int modifiers, Action action, JComponent component, int condition) {
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers);
		component.getInputMap(condition).put(keyStroke, action);
	}
 
	public static void autoSizeTableColumns(JTable table) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			int columnWidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				columnWidth = Math.max(comp.getPreferredSize().width, columnWidth);
			}
			table.getColumnModel().getColumn(column).setPreferredWidth(columnWidth);
		}
	}

	public static void applyRowSorter(JTable table) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		TableRowSorter<?> sorter = new TableRowSorter(table.getModel());
		sorter.setSortsOnUpdates(true);
		table.setRowSorter(sorter);
	}

	public static void scrollToTableCell(JTable table, int rowIndex, int colIndex) {
		if (!(table.getParent() instanceof JViewport))
			return;
		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(rowIndex, colIndex, true);
		Point p = viewport.getViewPosition();
		rect.setLocation(rect.x - p.x, rect.y - p.y);
		table.scrollRectToVisible(rect);
	}
	
	public static Icon getDirectoryIcon() {
		return UIManager.getIcon("FileView.directoryIcon");
	}

	public static Icon getHardDriveIcon() {
		return UIManager.getIcon("FileView.hardDriveIcon");
	}

	public static JSplitPane createSymmetricSplitPane(int orientation, Component comp1, Component comp2) {
		JSplitPane pane = new JSplitPane(orientation, comp1, comp2);
		pane.setOneTouchExpandable(true);
		pane.setDividerLocation(0.5);
		pane.setResizeWeight(0.5);
		return pane;
	}
	
}
