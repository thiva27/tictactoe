
public class Player {
	private String name;
	private String symbol;
	
	public Player(String name, String symbol) {
		this.setName(name);
		this.setSymbol(symbol);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String toString() {
		return "Name: " + this.name + ", symbol: " + this.symbol;
	}
	
	public static void main(String[] args) {
		Player player1 = new Player("Thiva","X");
		System.out.println(player1.toString());
		
		Player player2 = new Player("Computer", "O");
		System.out.println(player2.toString());
	}
}
