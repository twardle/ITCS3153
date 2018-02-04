import java.util.Random;

public class board {
	
	private int[][] cBoard;
	private int[][] nBoard;
	private int h = 0;
	private int bSize;
	private int[] qLocs;
	
	//default constructor for board, sets the board size to 8x8 and initializes all the variables
	board(){
		bSize = 8;
		cBoard = new int[bSize][bSize];
		nBoard = new int[bSize][bSize];
		qLocs = new int[bSize];
		
		//initializes the board
		restart();
	}
	
	board(int n){
		bSize = n;
		cBoard = new int[bSize][bSize];
		nBoard = new int[bSize][bSize];
		qLocs = new int[bSize];

		//initializes the board
		restart();
	}

	//initializes the entire board to 0s
	private void setBoard(){
		for(int row = 0; row < cBoard.length; row++){
			for(int col = 0; col < cBoard[row].length; col++){
				cBoard[row][col] = 0;
			}
		}
	}

	//updates the current board equal to the test board
	private void update() {
		for(int row = 0; row < cBoard.length; row++) {
			for(int col = 0; col < cBoard[row].length; col++){
				cBoard[row][col] = nBoard[row][col];
			}
		}
	}

	//resets the test state
	private void reset() {
		for(int row = 0; row < nBoard.length; row++) {
			for(int col = 0; col < nBoard[row].length; col++){
				nBoard[row][col] = cBoard[row][col];
			}
		}
	}
	
	//resets the entire board to a new board and generates the new h
	private void restart() {
		Random rand = new Random();
		
		setBoard();
		
		for(int col = 0; col < qLocs.length; col++){
			int rNum = rand.nextInt(bSize);
			cBoard[rNum][col] = 1;
			qLocs[col] = rNum;
		}
		
		h = calcH(true);
	}
	
	//function to move the queen in a direction (either up or down) and checks whether the board should be saved or reset
	private int moveQueen(int index, int row, boolean direction, boolean save) {
		if(direction && row+1 < bSize) {
			nBoard[row][index] = 0;
			nBoard[row+1][index] = 1;
		}
		else if(!direction && row-1 > 0) {
			nBoard[row][index] = 0;
			nBoard[row-1][index] = 1;
		}
		else return 100;
		
		
		int heuristic = calcH(false);
		
		if(save)
			update();
		else
			reset();
		
		return heuristic;
	}
	
	//function to iterate the board, 
	public int iterate(){
		int minH = h;
		int minIndex = -1;
		boolean minDir = false;
		int newH;
		int lowerH = 0;
		
		for(int i = 0; i < qLocs.length; i++) {
			newH = moveQueen(i,qLocs[i],true,false);
			if(newH < minH) {
				minH = newH;
				minIndex = i;
				minDir = true;
				
			}
			if(newH < h)
				lowerH++;
			
			newH = moveQueen(i,qLocs[i],false,false);
			if(newH < minH) {
				minH = newH;
				minIndex = i;
				minDir = false;
			}
			if(newH < h)
				lowerH++;
		}
		
		if(minIndex != -1) {
			h = moveQueen(minIndex,qLocs[minIndex],minDir,true);
		}
		else
			restart();
		
		return lowerH;
	}
	
	private int calcH(boolean board){
		int heuristic = 0;
		int thisBoard[][] = new int[bSize][bSize];
		//sets the board equal to either the current board or the test board (whichever is relevant to be checked
		if(board) {
			for(int row = 0; row < bSize; row++) {
				for(int col = 0; col < bSize; col++) {
					thisBoard[row][col] = cBoard[row][col];
				}
			}
		}
		
		else {
			for(int row = 0; row < bSize; row++) {
				for(int col = 0; col < bSize; col++) {
					thisBoard[row][col] = nBoard[row][col];
				}
			}
		}
		//checks rows
		for(int row = 0; row < thisBoard.length; row++) {
			int numQueens = 0;
			
			for(int col = 0; col < thisBoard.length; col++) {
				if(thisBoard[row][col] == 1)
					numQueens++;
			}
			
			if(numQueens > 1)
				heuristic+= numQueens - 1;
		}
		//checks primary diagonals
		for(int y = thisBoard.length*-1; y < thisBoard.length; y++) {
			int numQueens = 0;
			
			for(int x = 0; x < thisBoard.length; x++) {
				if(x+y < bSize && x+y >= 0)
					if(thisBoard[x+y][x] == 1)
						numQueens++;
			}
			
			if(numQueens > 1)
				heuristic+= numQueens - 1;
			
		}
		//checks inverse diagonals
		for(int y = 0; y < thisBoard.length*2; y++) {
			int numQueens = 0;
			
			for(int x = 0; x < thisBoard.length; x++) {
				if(y-x < bSize && y-x >= 0)
					if(thisBoard[y-x][x] == 1)
						numQueens++;
			}
			
			if(numQueens > 1)
				heuristic+= numQueens - 1;
			
		}
		//returns the heuristic value
		return heuristic;
	}
	
	public int getH(){
		return h;
	}
	
	public String toString(){
		String board = "Current State\n";
		
		for(int row = 0; row < cBoard.length; row++){
			for(int col = 0; col < bSize; col++){
				board += cBoard[row][col] + "|";
			}
			board = board.substring(0, board.length()-1);
			board += "\n";
		}
		
		return board;
	}
}
