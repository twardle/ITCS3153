import java.util.Arrays;
import java.util.Random;

public class board {
	
	boolean DEBUG = false;
	
	private int[][] cBoard;
	private int[][] nBoard;
	private int h = 0;
	private int bSize;
	private int[] qLocs;
	private int[] qNext;
	
	board(){
		bSize = 8;
		Random rand = new Random();
		cBoard = new int[bSize][bSize];
		nBoard = new int[bSize][bSize];
		setBoard();
		
		qLocs = new int[bSize];
		qNext = new int[bSize];
		
		for(int col = 0; col < qLocs.length; col++){
			int rNum = rand.nextInt(bSize);
			cBoard[rNum][col] = 1;
			qLocs[col] = rNum;
			qNext[col] = rNum;
		}
		
		h = calcH(true);
	}
	
	board(int n){
		bSize = n;
		Random rand = new Random();
		cBoard = new int[bSize][bSize];
		nBoard = new int[bSize][bSize];
		setBoard();
		
		qLocs = new int[bSize];
		qNext = new int[bSize];
		
		for(int col = 0; col < qLocs.length; col++){
			int rNum = rand.nextInt(bSize);
			cBoard[rNum][col] = 1;
			qLocs[col] = rNum;
			qNext[col] = rNum;
		}
		
		h = calcH(true);
	}
	
	private void setBoard(){
		for(int row = 0; row < cBoard.length; row++){
			for(int col = 0; col < cBoard[row].length; col++){
				cBoard[row][col] = 0;
			}
		}
	}
	
	private void update() {
		for(int row = 0; row < cBoard.length; row++) {
			for(int col = 0; col < cBoard[row].length; col++){
				cBoard[row][col] = nBoard[row][col];
			}
		}
	}
	
	private void reset() {
		for(int row = 0; row < nBoard.length; row++) {
			for(int col = 0; col < nBoard[row].length; col++){
				nBoard[row][col] = cBoard[row][col];
			}
		}
	}
	
	private void restart() {
		Random rand = new Random();
		
		setBoard();
		
		for(int col = 0; col < qLocs.length; col++){
			int rNum = rand.nextInt(bSize);
			cBoard[rNum][col] = 1;
			qLocs[col] = rNum;
			qNext[col] = rNum;
		}
		
		h = calcH(true);
	}
	
	private int moveQueen(int index, int row, boolean direction, boolean save) {
		if(direction && row+1 < bSize) {
			nBoard[row][index] = 0;
			nBoard[row+1][index] = 1;
			qNext[index] = row+1;
		}
		else if(!direction && row-1 > 0) {
			nBoard[row][index] = 0;
			nBoard[row-1][index] = 1;
			qNext[index] = row-1;
		}
		else return 100;
		
		
		int heuristic = calcH(false);
		
		if(save)
			update();
		else
			reset();
		
		return heuristic;
	}
	
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
	//TODO: FIX DIAGONALS
	//TODO: Consider implementing queens checker rather than board checker
	private int calcH(boolean board){
		int heuristic = 0;
		int thisBoard[][] = new int[bSize][bSize];
		
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
		
		//TODO: USE TARGET BOARD
		//TODO: FIX DIAGONALS
		for(int row = 0; row < thisBoard.length; row++) {
			int numQueens = 0;
			
			for(int col = 0; col < thisBoard.length; col++) {
				if(thisBoard[row][col] == 1)
					numQueens++;
			}
			
			if(numQueens > 1)
				heuristic+= numQueens - 1;
		}
		
		for(int row = 1; row < thisBoard.length-1; row++) {
			int numQueens = 0;
			
			for(int col = 0; col < row; col++) {
				if(thisBoard[row-col][col] == 1)
					numQueens++;
			}
			
			if(numQueens > 1)
				heuristic+= numQueens - 1;
			
		}
		
		for(int row = thisBoard.length-2; row > 1; row--) {
			int numQueens = 0;
			
			for(int col = row; col < thisBoard.length-1; col++) {
				if(thisBoard[row+(thisBoard.length-1-col)][col] == 1)
					numQueens++;
			}
			
			if(numQueens > 1)
				heuristic+= numQueens - 1;
			
		}
		
		return heuristic;
	}
	
	public int getH(){
		return h;
	}
	
	public String toString(){
		String board = "Current State\n";
		
		for(int row = 0; row < cBoard.length; row++){
			for(int col = 0; col < bSize; col++){
				board += cBoard[row][col] + ",";
			}
			board = board.substring(0, board.length()-1);
			board += "\n";
		}
		
		return board;
	}
}
