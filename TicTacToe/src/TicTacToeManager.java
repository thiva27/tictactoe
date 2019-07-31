import java.util.Random;
import java.util.Scanner;


/**
 * This manager class is responsible for managing the tic tac toe game.
 * It will mediation layer between player and game board.
 * */
public class TicTacToeManager {
	Grid grid;
	Player player1;
	Player player2;
	
	public TicTacToeManager(Grid grid, Player player1,Player player2) {
		this.grid = grid;
		this.player1 = player1;
		this.player2 = player2;
	}

	/**
	 * This method is responsible to check if the game has been won.
	 * It checks all three possible outcomes: across, vertical and diagonal.
	 * */
	public boolean isGameOver() {
		return checkAcross() || checkVertical() || checkDiagonal();
	}

	private boolean checkAcross() {
		int[] lastPosition = this.grid.getLastPosition();
		int row = lastPosition[0];

		for(int j = 1; j < this.grid.getColumns(); j++) {
			if(!this.grid.getGridValue(row, j-1).equals(this.grid.getGridValue(row, j))) {
				return false;
			}
		}
		return true;
	}

	private boolean checkVertical() {
		int[] lastPosition = this.grid.getLastPosition();
		int col = lastPosition[1];

		for(int i = 1; i < this.grid.getColumns(); i++) {
			if(!this.grid.getGridValue(i-1, col).equals(this.grid.getGridValue(i, col))) {
				return false;
			}
		}
		return true;
	}

	private boolean checkDiagonal() {
		//use top two corners of the grid to see if you can create a slope with the lastPosition on grid.
		//IF yes, then abs((y2-y1)/(x2-x1)) = 1 in order for it to create a diagonal
		
		//1. (0,0) 
		//2. row = 0, col = 3
		
		int[] lastPosition = this.grid.getLastPosition();
		int row = lastPosition[0];
		int col = lastPosition[1];
		
		if((row == 0 && col == 0)
				|| (row == (this.grid.getRows() - 1) && (col == 0))
				|| (row == (this.grid.getRows() - 1) && col == (this.grid.getColumns() - 1))
				|| (row == 0 && (col == this.grid.getColumns() - 1))
				|| checkSlope(row,col)) {
			return checkFromZeroZero() || checkFromZeroColumn();
		}
		
		return false;
	}

	/**
	 * Check the slope using the coordinate: row = this.getRows()-1 and col = 0
	 * and work its way upwards to row = 0 and col = this.getColumns()-1
	 * */
	private boolean checkSlope(int row, int col) {
		try {
			return Math.abs((col - 0)/(row - 0)) == 1 || Math.abs((col - (this.grid.getColumns() - 1))/(row - 0)) == 1;
		}catch (Exception e) {

		}
		return false;
	}

	private boolean checkFromZeroZero() {
		for(int i = 1; i < this.grid.getRows(); i++) {
			if(this.grid.getGridValue(i - 1, i - 1) != this.grid.getGridValue(i, i)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkFromZeroColumn() {
		for(int i = (this.grid.getRows() - 1); i > 0; i--) {
			if(this.grid.getGridValue(i, (this.grid.getRows()-1) - i) != this.grid.getGridValue(i - 1, (this.grid.getColumns()-1) - i + 1)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method will check if position is valid and then populate the position.
	 * */
	public boolean isValidAndPopulate(String position, String value) {
		int pos = Integer.parseInt(position);
		//1. check if position exists on grid
		if(pos >= 0 && pos < (this.grid.getRows() * this.grid.getColumns())) {
			//2. check if position is available to be occupied
			return this.grid.fillPosition(position, value);
		}
		return false;
	}
	
	/**
	 * Switch player
	 * */
	private Player switchPlayer(Player currentPlayer, Player user, Player computer) {
		if(currentPlayer.equals(user)) {
			return computer;
		}
		return user;
	}

	/**
	 *This method is responsible to fill the position.
	 * */
	private boolean fillPosition(String positionToFillInGrid, Player currentPlayer) {
		if(isValidAndPopulate(positionToFillInGrid, currentPlayer.getSymbol())) {
			this.grid.printGrid();
			return true;
		}
		System.out.println("The position you entered is either already occupied or does not exist.");
		return false;
	}

	/**
	 * This method orchestrates the computers turn.
	 * 1. generate the coordinates
	 * 2. loop until a valid coordinate is found
	 * 3. set the value
	 * 4. print the grid
	 * */
	private void computersTurn(Player currentPlayer) {
		System.out.println("Computers turn.");
		//1. generate coordinates
		Random rand = new Random();

		while(true) {
			// generate a number between [0 - (grid.rows()-1)].
			int row = rand.nextInt(this.grid.getRows());
			// generate a number between [0 - (grid.columns()-1)].
			int col = rand.nextInt(this.grid.getColumns());

			String position = this.grid.getGridValue(row, col);
			try {
				Integer.parseInt(position);
				this.grid.setGridValue(row, col, currentPlayer.getSymbol());
				this.grid.printGrid();
				break;
			} catch(Exception e) {
				//means a letter exists on the position already (i.e. X or O)
			}
		}
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public static void main(String[] args) {
		//1. initialize user
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter username");
		String userName = scanner.nextLine();
		Player user = new Player(userName, "X");

		//2. initialize computer
		Player computer = new Player("Computer", "O");
		
		//3. create Gird
		Grid grid = new Grid(3, 3);
		
		//4. initialize tic tac toe manager
		TicTacToeManager ticTacToeManager = new TicTacToeManager(grid, user, computer);
		
		//5. initialize number of turns
		int turnsleft = ticTacToeManager.getGrid().getRows() * ticTacToeManager.getGrid().getColumns();
		
		//6. let user know tic tac toe is about to start
		System.out.println("Get ready to play tic tac toe " + user.getName() + "!");
		System.out.println("You will be " + user.getSymbol() + ".");
		System.out.println("Press 'a' to rest the game at any time or 'b' to exit the game completely.");
		
		//7. initialize grid
		ticTacToeManager.getGrid().initializeGrid();
		Player currentPlayer = user;

		boolean isGameOver = ticTacToeManager.isGameOver();
		//8. Start game
		while(!isGameOver) {
			
			if(!currentPlayer.equals(computer)) {
				//a. ask currentPlayer which position he/she wants to place their symbol
				System.out.println(currentPlayer.getName() + ", which position do you want to place an " + currentPlayer.getSymbol() + "?");
				String positionToFillInGrid = scanner.nextLine();
				if(positionToFillInGrid.equals("a")) {
					System.out.println("Board has been reset");
					ticTacToeManager.getGrid().initializeGrid();
					continue;
				} else if(positionToFillInGrid.equals("b")) {
					scanner.close();
					System.out.println("Thank you for playing!");
					System.exit(0);
				}
				
				//b. check if valid position and fill
				if(!ticTacToeManager.fillPosition(positionToFillInGrid, currentPlayer)) {
					continue;
				}
				turnsleft--;
			} else {
				//this means computer is playing.
				ticTacToeManager.computersTurn(currentPlayer);
				turnsleft--;
			}

			//c. check if game has been won
			isGameOver = ticTacToeManager.isGameOver();
			if(isGameOver) {
				scanner.close();
				System.out.println(currentPlayer.getName() + " won the game!");
				System.exit(0);
			} else if(turnsleft == 0) {
				System.out.println("Its a draw!");
			}
			currentPlayer = ticTacToeManager.switchPlayer(currentPlayer,user,computer);
		}
	}
}