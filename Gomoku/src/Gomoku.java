import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Gomoku {

	public static void main(String[] args) {
		String[][] board = new String[15][15];
		assignBoard(board);
		displayBoard(board);
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter the name of player1: ");
		String name1 = input.next();
		Player player1 = new Player(name1);
		
		System.out.print("Enter the name of player2: ");
		String name2 = input.next();
		Player player2 = new Player(name2);
		
		System.out.println("1. Show the winners");
		System.out.println("2. Play");
		System.out.println("3. Exit");
		
		System.out.print("Enter your option(1, 2, or 3): ");
		int option = input.nextInt();
		
		
		if(option == 1) {
			System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
		}
		if(option == 2) {
			Play(player1, player2, board);
			int movesOfWinner = movesOfWinner(player1, player2);
			
			try {
				String destinationFile = "/Users/Isabellewn/eclipse-workspace/ProgramWork/src/GomokuResult.csv";
				FileWriter fw = new FileWriter(destinationFile, false);
				PrintWriter pw = new PrintWriter(fw);
				String winner = ShowWinner(player1, player2, allMoves(player1, player2));
				writeToFile(pw, player1, player2, winner, movesOfWinner);
				pw.close();
						
			} catch (Exception e) {
				e.getMessage();
			}	
			
		}
		if(option == 3) {
			Exit();
		}
		input.close();
			
	}



	private static void Play(Player player1, Player player2, String[][]board) {	
		java.util.Date startTimeInCal = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startTime = sdf.format(startTimeInCal);
		long startTimeInLong = startTimeInCal.getTime();
		player1.setStartTime(startTime);
		
	
		Scanner input = new Scanner(System.in);
		do {
			System.out.print("Enter the row(0-14) and column(0-14) for opening move, separating by a space(ex:7 7): ");
			int row = input.nextInt();
			int column = input.nextInt();
			
			if(row >=0 && row <=14 && column >=0 && column <=14) {
				player1.setMoves(row, column); 
				board[row][column] = "B";
				break;
			}
			else {
				System.out.println("Invalid input! Please enter numbers between 0 and 14!");
			}
		}while(true);
			
		for(int i=1; i<3; i++) {
			int[] currentMove = makeOpeningMove(player1, player2);
			int row = currentMove[0];
			int column = currentMove[1];
			if( i == 1) {
				player1.setMoves(row, column);
				board[row][column] = "B";
			}
			if( i == 2 ) {
				player2.setMoves(row, column);
				board[row][column] = "W";	
			}
	
		}	
		displayBoard(board);
		
		System.out.println("Option1 for Player2: Play as black(B)");
		System.out.println("Option2 for Player2: Play as white(W) and place another white stone");
		System.out.println("Option3 for Player2: Place two more stones, one white and one black, "
				+ "and let the first player choose the color");
		System.out.print("Choose your option(1, 2, or 3): ");
		
		int optionOfOpenningMove = input.nextInt();
		if(optionOfOpenningMove == 1) {
			System.out.println(player1.getName() +" plays as white, " + player2.getName() + " plays as black");
			player1.setColour("W");
			player2.setColour("B");
			
			swapMoves(player1.getMoves(), player2.getMoves()); 

			int count = 3;
			do{
				int[] currentMove1 = makeNextMove(player1, player2, 1);
				int row1 = currentMove1[0];
				int column1 = currentMove1[1];
				player1.setMoves(row1, column1);
				board[row1][column1] = "W";
				player1.setPlayerBoard();
				count++;
				displayBoard(board);
				
				if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
					System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
					break;
				}
				
				int[] currentMove2 = makeNextMove(player1, player2, 2);
				int row2 = currentMove2[0];
				int column2 = currentMove2[1];
				player2.setMoves(row2, column2);
				board[row2][column2] = "B";
				player2.setPlayerBoard();
				count++;
				displayBoard(board);
				
				if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
					System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
					break;
				}
				
			}while(true);
			
		}
		
		if(optionOfOpenningMove == 2) {
			player1.setColour("B");
			player2.setColour("W");
			System.out.println(player1.getName() + " plays as black, " + player2.getName() + " plays as white");		

			int count = 3;
			do{
				int[] currentMove2 = makeNextMove(player1, player2, 2);
				int row2 = currentMove2[0];
				int column2 = currentMove2[1];
				player2.setMoves(row2, column2);
				board[row2][column2] = "W";
				player2.setPlayerBoard();
				count++;
				displayBoard(board);
				
				if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
					System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
					break;
				}
				
				int[] currentMove1 = makeNextMove(player1, player2, 1);
				int row1 = currentMove1[0];
				int column1 = currentMove1[1];
				player1.setMoves(row1, column1);
				board[row1][column1] = "B";
				player1.setPlayerBoard();
				count++;
				displayBoard(board);
				
				if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
					System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
					break;
				}	
			}while(true);	
		}
		
		if(optionOfOpenningMove == 3) {
			for(int i=0; i<2; i++) {
				int[] currentMove = makeOpeningMove(player1, player2);
				int row = currentMove[0];
				int column = currentMove[1];
				if(i == 0) {
					board[row][column] = "B";
					player1.setMoves(row, column);	
				}
				if(i == 1) {
					board[row][column] = "W";
					player2.setMoves(row, column);
				}
			}
			displayBoard(board);
			
			
			System.out.println("Choose the colour for player1(Enter 'b' for black, 'w' for white): ");
			char letter = input.next().charAt(0);
			
			if(letter == 'b') {
				player1.setColour("B");
				player2.setColour("W");
				System.out.println(player1.getName() + " plays as black, " + player2.getName() + " plays as white");
				
				int count = 5;
				do{
					int[] currentMove2 = makeNextMove(player1, player2, 2);
					int row2 = currentMove2[0];
					int column2 = currentMove2[1];
					player2.setMoves(row2, column2);
					board[row2][column2] = "W";
					player2.setPlayerBoard();
					count++;
					displayBoard(board);
					
					if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
						System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
						break;
					}
					
					int[] currentMove1 = makeNextMove(player1, player2, 1);
					int row1 = currentMove1[0];
					int column1 = currentMove1[1];
					player1.setMoves(row1, column1);
					board[row1][column1] = "B";
					player1.setPlayerBoard();
					count++;
					displayBoard(board);
					
					if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
						System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
						break;
					}	
				}while(true);
		
			}
			
			if(letter == 'w') {
				System.out.println(player1.getName() + " plays as white, " + player2.getName() + " plays as black");
				player1.setColour("W");
				player2.setColour("B");
				
				swapMoves(player1.getMoves(), player2.getMoves());  

				int count = 5;
				do{
					int[] currentMove1 = makeNextMove(player1, player2, 1);
					int row1 = currentMove1[0];
					int column1 = currentMove1[1];
					player1.setMoves(row1, column1);
					board[row1][column1] = "W";
					player1.setPlayerBoard();
					count++;
					displayBoard(board);
					
					if(count >= 9 && (player1.isWinner()||player2.isWinner())) {
						System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
						break;
					}
					
					int[] currentMove2 = makeNextMove(player1, player2, 2);
					int row2 = currentMove2[0];
					int column2 = currentMove2[1];
					player2.setMoves(row2, column2);
					board[row2][column2] = "B";
					player2.setPlayerBoard();
					count++;
					displayBoard(board);
					
					if(count >= 10 && player1.isWinner()||player2.isWinner()) {
						System.out.print(ShowWinner(player1, player2, allMoves(player1, player2)));
						break;
					}
				}while(true);
			}
		}
		input.close();
		java.util.Date endTimeInCal = Calendar.getInstance().getTime();
		String endTime = sdf.format(endTimeInCal);
		long endTimeInLong = endTimeInCal.getTime();
		player1.setEndTime(endTime);
		
		
		long timeElapsedInMillis = endTimeInLong - startTimeInLong;
		String timeElapsed = milliesToHMS(timeElapsedInMillis);
		player1.setTimeElapsed(timeElapsed);
		
				
	}
	

	
	private static void writeToFile(PrintWriter pw, Player player1, Player player2, String winner, int movesOfWinner) {
		pw.print("Player1Name, Player2Name, Winner, MovesOfWinner, Date and StartTime, Date and EndTime, CompetitionTime\n");
		pw.printf("%5s,%5s,%5s,%5d,%25s,%25s,%25s", 
				player1.getName(), 
				player2.getName(), 
				winner, 
				movesOfWinner,
				player1.getStartTime(), 
				player1.getEndTime(), 
				player1.getTimeElapsed());
		
	}

	private static String milliesToHMS(long timeInMillis) {
		Long hour = timeInMillis / 1000 / 3600;
		long minute = timeInMillis / 1000 % 3600 / 60;
		long second = timeInMillis / 1000 % 3600 % 60;
		String time = hour + " hours " + minute + " minutes " + second + " seconds";
		
		return time;
	}

	private static int[] makeNextMove(Player player1, Player player2, int i) {
		Scanner input = new Scanner(System.in);
		do {
			int row = 0;
			int column = 0;
			if(i == 1) {
				System.out.print("Enter the row(0-14) and column(0-14) for " + player1.getName() + ": ");
				row = input.nextInt();
				column = input.nextInt();
			}
			if(i == 2) {
				System.out.print("Enter the row(0-14) and column(0-14) for " + player2.getName() + ": ");
				row = input.nextInt();
				column = input.nextInt();
			}
			
			
			if(isValid(row, column, allMoves(player1, player2))) {
				int[] validMove = {row, column};
				return validMove;
			}
		}while(true);	
	}
	

	private static void swapMoves(ArrayList<Move> list1, ArrayList<Move> list2) {
		ArrayList<Move> temp = new ArrayList<Move>(list1);
		list1.clear();
		list1.addAll(list2);
		list2.clear();
		list2.addAll(temp);
		
		
	}

	private static int[] makeOpeningMove(Player player1, Player player2) {
		Scanner input = new Scanner(System.in);
		do {
			System.out.print("Enter the row(0-14) and column(0-14) for opening move, separating by a space(ex:7 7): ");
			int row = input.nextInt();
			int column = input.nextInt();
			
			if(isValid(row, column, allMoves(player1, player2))) {
				int[] validMove = {row, column};
				return validMove;
			}
			
		}while(true);	
	}
	
	
	public static boolean isValid(int row, int column, ArrayList<Move> allMoves) {
		for(int i=0; i<allMoves.size(); i++) {
			if(row > 14 || column > 14 || row < 0 || column < 0) {
				System.out.println("Invalid input! Please enter numbers between 0 and 14!");
				return false;
			}
			else if (allMoves.get(i).getRow() == row && allMoves.get(i).getColumn() == column) {
				System.out.println("Invalid input! This coordinate is already taken!");
				return false;
			}
		}
		return true;
	}
	
	
	public static ArrayList<Move> allMoves(Player player1, Player player2){
		ArrayList<Move> allMoves = new ArrayList<Move>();
		for(int i=0; i<player1.getMoves().size(); i++) {
			allMoves.add(player1.getMoves().get(i));
		}
		for(int i=0; i<player2.getMoves().size(); i++) {
			allMoves.add(player2.getMoves().get(i));
		}
		return allMoves;
	}
	

	private static void assignBoard(String[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j< board[0].length; j++) {
				board[i][j] = " ";
			}	
		}
	}


	private static void displayBoard(String[][] board) {
		System.out.println("    0   1   2   3   4   5   6   7   8   9  10  11  12  13  14");
		for(int i=0; i<board.length; i++) {
			System.out.println("---------------------------------------------------------------");
			System.out.printf("%2d",i);
			for(int j=0; j< board[0].length; j++) {
				System.out.print("| " + board[i][j]+ " ");	
			}
			System.out.println("|");
		}
		System.out.println("---------------------------------------------------------------");
			
	}

	private static String ShowWinner(Player player1, Player player2, ArrayList<Move> allMoves) {

		
		if(allMoves.size()<9) {
			return "no winner yet";
		}
		else {
			if(player1.isWinner() && player2.isWinner()) {
				return "It's a tie!";
			}
			if(player1.isWinner()) {
				return "Winner is " + player1.getName();
			}
			if(player2.isWinner()) {
				return "Winner is " + player2.getName();
			}
				
		}
		
		return "The game is not started, no winner yet";
	}
	
	
	private static int movesOfWinner(Player player1, Player player2) {
		if(player1.isWinner()) {
			return player1.getMoves().size();
		}
		if(player2.isWinner()) {
			return player2.getMoves().size();
		}
		return 0;
	}
	
	private static void Exit() {
		System.out.println("The game is finished!");
		System.exit(0);	
	}

}
