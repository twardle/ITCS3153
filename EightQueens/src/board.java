import java.util.Random;

public class board {
	
	boolean DEBUG = false;
	
	private int[][] layout;
	private int[][] targetLayout;
	private int tH = 0;
	private int h = 0;
	private int bSize;
	private int[] qLocs;
	
	board(){
		bSize = 8;
		Random rand = new Random();
		layout = new int[bSize][bSize];
		targetLayout = new int[bSize][bSize];
		setBoard();
		
		qLocs = new int[bSize];
		
		for(int i = 0; i < qLocs.length; i++){
			int rNum = rand.nextInt(bSize);
			targetLayout[rNum][i] = 1;
			layout[rNum][i] = 1;
			qLocs[i] = rNum;
		}
		
		calcH();
	}
	
	board(int n){
		bSize = n;
		Random rand = new Random();
		layout = new int[bSize][bSize];
		targetLayout = new int[bSize][bSize];
		setBoard();
		
		for(int i = 0; i < qLocs.length; i++){
			int rNum = rand.nextInt(bSize);
			targetLayout[rNum][i] = 1;
			layout[rNum][i] = 1;
			qLocs[i] = rNum;
		}
		
		calcH();
	}
	
	private void setBoard(){
		for(int i = 0; i < layout.length; i++){
			for(int j = 0; j < layout[i].length; j++){
				layout[i][j] = 0;
				targetLayout[i][j] = 0;
			}
		}
	}
	
	private int updateBoard(){
		int lowerH = 0;
		for(int i = 0; i < qLocs.length; i++){
			
			if(movePiece(qLocs[i], i, 1)){
				qLocs[i] += 1;
				if(calcNewH() < h){
					lowerH++;
					for(int j = 0; j < bSize; j++)
						for(int k = 0; k < bSize; k++){
							targetLayout[j][k] = layout[j][k];
						}
				}
			}
			
			movePiece(qLocs[i],i,-1);
			
			if(movePiece(qLocs[i], i, -1)){
				qLocs[i] += -1;
				if(calcNewH() < h){
					lowerH++;
				}
			}
			
			movePiece(qLocs[i],i,1);
		}
		
		return lowerH;
	}
	
	public boolean movePiece(int coords, int col, int deltaY) {		
		if(coords + deltaY < 0 || coords + deltaY > bSize-1)
			return false;
		
		layout[coords + deltaY][col] = 1;
		layout[coords][col] = 0;
		
		return true;
	}
	
	public int iterate(){
		int lowerH = updateBoard();
		h = 0;
		calcH();
		
		return lowerH;
	}
	
	private void calcH(){
		for(int q = 0; q < qLocs.length; q++){
			for(int q_ = 0; q_ < q; q_++){
				if(qLocs[q] == qLocs[q_]){
					h++;
					break;
				}
				
			}
			for(int x = 0; x < bSize; x++){
				if(x+qLocs[q] == 1 || -x+qLocs[q] == 1){
					h++;
					break;
				}
			}
		}
	}
	
	private int calcNewH(){
		int newH = 0;
		for(int q = 0; q < qLocs.length; q++){
			for(int q_ = 0; q_ < q; q_++){
				if(qLocs[q] == qLocs[q_]){
					newH++;
					break;
				}
				
			}
			for(int x = 0; x < bSize; x++){
				if(x+qLocs[q] == 1 || -x+qLocs[q] == 1){
					newH++;
					break;
				}
			}
		}
		
		return newH;
	}
	
	public int getPiece(int i, int j){
		return layout[i][j];
	}
	
	public int getH(){
		return h;
	}
	
	public String toString(){
		String board = "Current State\n";
		
		for(int i = 0; i < layout.length; i++){
			for(int j = 0; j < bSize; j++){
				board += targetLayout[i][j] + ",";
			}
			board = board.substring(0, board.length()-1);
			board += "\n";
		}
		
		return board;
	}
}
