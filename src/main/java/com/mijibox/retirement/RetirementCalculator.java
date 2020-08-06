package com.mijibox.retirement;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RetirementCalculator {

	private JFrame frame;

	RetirementCalculator() {
		this.frame = new JFrame("Retirement Calculator");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setContentPane(this.createContentPane());

		this.frame.pack();
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	private JPanel createContentPane() {
		JPanel pnl = new JPanel(new BorderLayout());

		JPanel pnlNorth = new JPanel(new GridBagLayout());
		pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		GridBagConstraints gbConst = new GridBagConstraints();
		gbConst.fill = GridBagConstraints.BOTH;
		gbConst.gridx = 0;
		gbConst.gridy = 0;
		gbConst.insets = new Insets(5, 5, 5, 5);
		pnlNorth.add(new JLabel("Current Age"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Starting Balance"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Annual Saving Before Retirement"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Initial Annual Drawing (Today's Dollar)"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Inflation Rate (%)"), gbConst);

		gbConst.gridx += 2;
		gbConst.gridy = 0;
		pnlNorth.add(new JLabel("Retire at Age"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Investment Return Rate (%)"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Annual Saving Adjustment (%)"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Annual Drawing Adjustment (%)"), gbConst);

		gbConst.gridy++;
		pnlNorth.add(new JLabel("Show Today's Dollar"), gbConst);

		gbConst.gridy = 0;
		gbConst.gridx = 1;
		gbConst.weightx = 0.5;
		JTextField tfCurrentAge = new JTextField("25");
		pnlNorth.add(tfCurrentAge, gbConst);

		gbConst.gridy++;
		JTextField tfBalance = new JTextField("10000");
		pnlNorth.add(tfBalance, gbConst);

		gbConst.gridy++;
		JTextField tfSaving = new JTextField("6000");
		pnlNorth.add(tfSaving, gbConst);

		gbConst.gridy++;
		JTextField tfDrawings = new JTextField("45000");
		pnlNorth.add(tfDrawings, gbConst);

		gbConst.gridy++;
		JTextField tfInflation = new JTextField("2.5");
		pnlNorth.add(tfInflation, gbConst);

		gbConst.gridx += 2;
		gbConst.gridy = 0;
		JTextField tfRetirementAge = new JTextField("67");
		pnlNorth.add(tfRetirementAge, gbConst);

		gbConst.gridy++;
		JTextField tfReturn = new JTextField("6.5");
		pnlNorth.add(tfReturn, gbConst);

		gbConst.gridy++;
		JTextField tfSavingAdjustment = new JTextField("2.5");
		pnlNorth.add(tfSavingAdjustment, gbConst);

		gbConst.gridy++;
		JTextField tfAdjustment = new JTextField("2");
		pnlNorth.add(tfAdjustment, gbConst);

		gbConst.gridy++;
		JCheckBox cbShowTodaysDollar = new JCheckBox("", false);
		pnlNorth.add(cbShowTodaysDollar, gbConst);

		gbConst.gridy++;
		gbConst.gridwidth = 2;
		gbConst.fill = GridBagConstraints.NONE;
		gbConst.anchor = GridBagConstraints.EAST;
		JPanel pnlButtons = new JPanel(new GridLayout(1, 2, 5, 5));
		JButton btnCalculate = new JButton("Calculate");
		pnlButtons.add(btnCalculate);
		JButton btnExport = new JButton("Export...");
//		pnlButtons.add(btnExport);

		pnlNorth.add(pnlButtons, gbConst);

		DefaultTableModel tableModel = new DefaultTableModel(
				new String[] { "Age", "Opening Balance", "Drawing", "Saving", "Investment Return", "Closing Balance" },
				0);

		JTable table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}

		};
		table.setRowHeight(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				lbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				lbl.setHorizontalAlignment(JLabel.RIGHT);
				return lbl;
			}
		};

		table.setDefaultRenderer(table.getColumnClass(0), renderer);

		pnl.add(pnlNorth, BorderLayout.NORTH);
		pnl.add(new JScrollPane(table), BorderLayout.CENTER);

		btnCalculate.addActionListener(e -> {
			double balance = Integer.parseInt(tfBalance.getText());
			double saving = Integer.parseInt(tfSaving.getText());
			double savingAdjustment = Double.parseDouble(tfSavingAdjustment.getText()) / (double) 100;
			int currentAge = Integer.parseInt(tfCurrentAge.getText());
			int retirementAge = Integer.parseInt(tfRetirementAge.getText());
			double returnRate = Double.parseDouble(tfReturn.getText()) / (double) 100;
			double adjustment = Double.parseDouble(tfAdjustment.getText()) / (double) 100;
			double inflation = Double.parseDouble(tfInflation.getText()) / (double) 100;
			double plannedDrawing = Integer.parseInt(tfDrawings.getText());
			double inflatedDrawing = plannedDrawing * Math.pow((1 + inflation), (retirementAge - currentAge));
			boolean showTodaysDollar = cbShowTodaysDollar.isSelected();

			for (int i = 0; i < 100; i++) {
				int colIdx = 0;
				tableModel.setRowCount(i + 1);

				tableModel.setValueAt((currentAge + i), i, colIdx++);
				// opening balance
				String strBalance = showTodaysDollar ? String.format("%,.2f", inflationAdjusted(balance, inflation, i))
						: String.format("%,.2f", balance);
				tableModel.setValueAt(strBalance, i, colIdx++);

				// adjusted drawing
				double drawing = currentAge + i >= retirementAge ? inflatedDrawing : 0;
				double adjustedDrawing = Math.min(balance,
						drawing * Math.pow((1 + adjustment), (i - (retirementAge - currentAge))));
				String strDrawing = showTodaysDollar
						? String.format("%,.2f", inflationAdjusted(adjustedDrawing, inflation, i))
						: String.format("%,.2f", adjustedDrawing);
				tableModel.setValueAt(strDrawing, i, colIdx++);

				// saving
				double adjustedSaving = currentAge + i < retirementAge ? saving * Math.pow((1 + savingAdjustment), i)
						: 0;
				String strSaving = showTodaysDollar
						? String.format("%,.2f", inflationAdjusted(adjustedSaving, inflation, i))
						: String.format("%,.2f", adjustedSaving);
				tableModel.setValueAt(strSaving, i, colIdx++);

				// investment return
				double investReturn = Math.max(0, (balance - adjustedDrawing)) * returnRate;
				String strReturn = showTodaysDollar
						? String.format("%,.2f", inflationAdjusted(investReturn, inflation, i + 1))
						: String.format("%,.2f", investReturn);
				tableModel.setValueAt(strReturn, i, colIdx++);

				// end balance
				balance = Math.max(0, balance - adjustedDrawing + adjustedSaving + investReturn);
				String strEndBalance = showTodaysDollar
						? String.format("%,.2f", inflationAdjusted(balance, inflation, i + 1))
						: String.format("%,.2f", balance);
				tableModel.setValueAt(strEndBalance, i, colIdx++);

				if (balance <= 0) {
					break;
				}
			}
		});
		
		btnExport.addActionListener(e->{
			
		});

		pnl.setPreferredSize(new Dimension(650, 700));
		return pnl;
	}

	public double inflationAdjusted(double inflated, double inflation, int year) {
		double original = inflated / Math.pow((1 + inflation), year);
		return original;
	}

	public static void main(String[] args) {
		new RetirementCalculator();
	}

}
