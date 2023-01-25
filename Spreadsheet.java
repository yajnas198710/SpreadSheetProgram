package com.org.simple;

import java.util.HashMap;
import java.util.Map;

public class Spreadsheet {
	private Map<String, Cell> cells;

	public Spreadsheet() {
		cells = new HashMap<>();
	}

	public void setCellValue(String cell, String value) {
		// Create a new cell if it doesn't already exist
		if (!cells.containsKey(cell)) {
			cells.put(cell, new Cell());
		}
		cells.get(cell).setValue(value);
	}

	public String getCellValue(String cell) {
		if (!cells.containsKey(cell)) {
			throw new IllegalArgumentException("Cell does not exist: " + cell);
		}
		Cell cell2 = cells.get(cell);
		String form = cell2.formulas.get(cell);
		form = form.replace("=", "");
//        System.out.println(form);
		int value = 0;
		if (form.substring(2, 3).contains("+")) {

			value = Integer.parseInt(cells.get(form.substring(3)).value)
					+ Integer.parseInt(cells.get(form.substring(0, 2)).value);

			cells.get(cell).setValue(Integer.toString(value));

		} else if (form.substring(2, 3).contains("-")) {
			value = Integer.parseInt(cells.get(form.substring(3)).value)
					- Integer.parseInt(cells.get(form.substring(0, 2)).value);

			cells.get(cell).setValue(Integer.toString(value));

		} else if (form.substring(2, 3).contains("*")) {
			value = Integer.parseInt(cells.get(form.substring(3)).value)
					* Integer.parseInt(cells.get(form.substring(0, 2)).value);

			cells.get(cell).setValue(Integer.toString(value));

		} else if (form.substring(2, 3).contains("/")) {
			value = Integer.parseInt(cells.get(form.substring(3)).value)
					/ Integer.parseInt(cells.get(form.substring(0, 2)).value);

			cells.get(cell).setValue(Integer.toString(value));
		}

		return cells.get(cell).getValue();
	}

	class Cell {
		private String value;
		private Map<String, String> formulas;

		public Cell() {
			this.value = "";
			this.formulas = new HashMap<>();
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setFormula(String formulaName, String formula) {
			this.formulas.put(formulaName, formula);
			Cell cell = new Cell();
			cell.formulas = formulas;
			cell.value = formulaName;
			cells.put(formulaName, cell);
		}

		public String getFormula(String formulaName) {
			return formulas.get(formulaName);
		}
	}

	public static void main(String[] args) {
		Spreadsheet spreadsheet = new Spreadsheet();
		spreadsheet.setCellValue("A1", "10");
		spreadsheet.setCellValue("A2", "20");

		Spreadsheet.Cell cell = spreadsheet.new Cell();

		cell.setFormula("A3", "=A1*A2");

		String val = spreadsheet.getCellValue("A3");
		System.out.println(val); // Will print 30

	}

}
