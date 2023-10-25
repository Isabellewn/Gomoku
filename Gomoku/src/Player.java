import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	private String name;
	private String colour;
	//private String winner;
	private ArrayList<Move> moves;
	private String[][] playerBoard = new String[15][15];
	private String startTime;
	private String endTime;
	private String timeElapsed;
	

	public String getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(String timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Player(String name) {
		this.name = name;
		moves = new ArrayList<Move>();
	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}


	public ArrayList<Move> getMoves() {
		return moves;
	}

	public void setMoves(int row, int column) {
		Move move = new Move(row, column);
		this.moves.add(move);
	}

	
	public String[][] getPlayerBoard(){
		return playerBoard;
	}
	
	public void setPlayerBoard() {
		for(int i=0; i<playerBoard.length; i++) {
			for(int j=0; j< playerBoard[0].length; j++) {
				this.playerBoard[i][j] = " ";
			}	
		}
		
		for(int i=0; i<getMoves().size(); i++) {
			int row = getMoves().get(i).getRow();
			int column = getMoves().get(i).getColumn();
			this.playerBoard[row][column] = getColour();
		}
		//System.out.println(playerBoard[7][5]);
		

	}

	public boolean isWinner() {
		// check row
		for(int i=0; i<playerBoard.length; i++) {
			for(int j=0; j<playerBoard[0].length-4; j++) {
				if(playerBoard[i][j].equals(getColour()) 
						&& playerBoard[i][j].equals(playerBoard[i][j+1]) 
						&& playerBoard[i][j].equals(playerBoard[i][j+2])
						&& playerBoard[i][j].equals(playerBoard[i][j+3])
						&& playerBoard[i][j].equals(playerBoard[i][j+4])) {
					return true;
				}
			}
		}
		
		//check column
		for(int j=0; j<playerBoard[0].length; j++) {
			for(int i=0; i<playerBoard.length-4; i++) {
				if(playerBoard[i][j].equals(getColour()) 
						&& playerBoard[i][j].equals(playerBoard[i+1][j]) 
						&& playerBoard[i][j].equals(playerBoard[i+2][j])
						&& playerBoard[i][j].equals(playerBoard[i+3][j])
						&& playerBoard[i][j].equals(playerBoard[i+4][j])) {
					return true;
				}
			}
		}
		// diagonal from left to right
		for(int i=0; i<playerBoard.length-4; i++) {
			for(int j=0; j<playerBoard[0].length-4; j++) {
				if(playerBoard[i][j].equals(getColour()) 
						&& playerBoard[i][j].equals(playerBoard[i+1][j+1]) 
						&& playerBoard[i][j].equals(playerBoard[i+2][j+2])
						&& playerBoard[i][j].equals(playerBoard[i+3][j+3])
						&& playerBoard[i][j].equals(playerBoard[i+4][j+4])) {
					return true;
				}
			}
		}
		
		//diagonal from right to left
		for(int i=0; i<playerBoard.length-4; i++) {
			for(int j=4; j<playerBoard[0].length; j++) {
				if(playerBoard[i][j].equals(getColour()) 
						&& playerBoard[i][j].equals(playerBoard[i+1][j-1]) 
						&& playerBoard[i][j].equals(playerBoard[i+2][j-2])
						&& playerBoard[i][j].equals(playerBoard[i+3][j-3])
						&& playerBoard[i][j].equals(playerBoard[i+4][j-4])) {
					return true;
				}
			}
		}
		return false;	
	}

	

	
	
	
	

}
