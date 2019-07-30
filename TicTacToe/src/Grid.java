
public class Grid {
	
	private int rows;
	private int columns;
	private String[][] grid;
	private int[] lastPosition;
	
	public Grid(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.grid = new String[rows][columns];
		this.lastPosition = new int[2];
	}

	public void initializeGrid() {
		int counter = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				setGridValue(i,j,Integer.toString(counter));
				counter++;
			}
		}
		printGrid();
	}

	public void printGrid() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(getGridValue(i,j));
				if(j != (columns - 1)) {
					System.out.print("|");
				}
			}
			System.out.println();
		}
	}

	public boolean fillPosition(String position, String value) {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(position.equals(getGridValue(i,j))) {
					setGridValue(i,j,value);
					return true;
				}
			}
		}
		return false;
	}

	public void setGridValue(int row, int column, String value) {
		if(row >= 0 && (row < this.rows) 
				&& column >= 0 && (column < this.columns)) {
			this.grid[row][column] = value;
			this.lastPosition[0] = row;
			this.lastPosition[1] = column;
		}
	}

	public String getGridValue(int row,int column) {
		if(row >= 0 && (row < this.rows) 
				&& column >= 0 && (column < this.columns)) {
			return this.grid[row][column];
		}
		return "";
	}

	public String[][] getGrid() {
		return grid;
	}

	public void setGrid(String[][] grid) {
		this.grid = grid;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int[] getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(int[] lastPosition) {
		this.lastPosition = lastPosition;
	}
	
	public static void main(String[] args) {
		Grid grid = new Grid(3,3);
		grid.initializeGrid();
		
		System.out.println(grid.getGridValue(2,2) + "(2,2)");
		System.out.println(grid.getGridValue(0,0) + "(0,0)");
		
		grid.setGridValue(2,2,"T");
		grid.printGrid();
		grid.setGridValue(0,0,"Y");
		grid.printGrid();
		grid.setGridValue(3,3,"Y");
		System.out.println("last filled position: " + grid.lastPosition[0] + ", " + grid.lastPosition[1]);
	}
}
