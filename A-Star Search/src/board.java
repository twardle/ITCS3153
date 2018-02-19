import java.util.List;
import java.util.Random;
import java.math.*;

public class board {

	Node b[][];
	int bSize;
	double Opacity = 0.9;
	
	public board() {
		bSize = 15;
		b = new Node[bSize][bSize];
		Random rand = new Random();
		
		for(int row = 0; row < bSize; row++) {
			for(int col = 0; col < bSize; col++) {
				double rNum = rand.nextDouble();
				if(rNum >= Opacity)
					b[row][col] = new Node(row, col, 1);
				else
					b[row][col] = new Node(row, col, 0);
			}
		}
	}

	public board(int bSize_) {
		bSize = bSize_;
		b = new Node[bSize][bSize];
		Random rand = new Random();
		
		for(int row = 0; row < bSize; row++) {
			for(int col = 0; col < bSize; col++) {
				double rNum = rand.nextDouble();
				if(rNum >= Opacity)
					b[row][col] = new Node(row, col, 1);
				else
					b[row][col] = new Node(row, col, 0);
			}
		}
	}
	
	public int iterate(List<Node> openSet, List<Node> closedSet, Node end) {
		
		int winner = 0;
		
	    for (int i = 0; i < openSet.size(); i++){
		    if (openSet.get(i).getF() < openSet.get(winner).getF()) {
		        winner = i;
		    }
	    }
	    
	    Node current = openSet.get(winner);
	    
	    if(current == end) {
	    	return 1;
	    }
	    
	    openSet.remove(winner);
	    closedSet.add(current);
		current.setType(3);

	    checkNeighbors(1, 0, current, closedSet, openSet, end);
	    checkNeighbors(-1, 0, current, closedSet, openSet, end);
	    checkNeighbors(0, 1, current, closedSet, openSet, end);
	    checkNeighbors(0, -1, current, closedSet, openSet, end);
	    
		if(openSet.size() == 0)
			return -1;
	    
	    return 0;
	}
	
	private int heuristic(Node a, Node b) {
		int d = Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
		return d;
	}
	
	private void checkNeighbors(int row, int col, Node curr, List<Node> closedSet, List<Node> openSet, Node end) {
		Node neighbor = b[0][0];
		if (curr.getRow()+row < bSize && curr.getCol()+col < bSize) {
			if (curr.getRow()+row > -1 && curr.getCol()+col > -1) {
				neighbor = b[curr.getRow() + row][curr.getCol() + col];
				
				if(neighbor.getType() == 0) {
				
					if (!closedSet.contains(neighbor)){
							int tempG = curr.getG() + heuristic(neighbor, curr);
							boolean newPath = false;
							
							if(openSet.contains(neighbor)) {
								if(tempG < neighbor.getG()) {
									neighbor.setG(tempG);
									newPath = true;
								}
							}
							else {
								neighbor.setG(tempG);
								newPath = true;
								openSet.add(neighbor);
								neighbor.setType(2);
							}
							
							if( newPath ) {
								neighbor.setH(heuristic(neighbor, end));
								neighbor.setF();
								neighbor.setParent(curr);
							}
					}
				}
			}
		}
	}

	public String toString() {
		String boardState = "";
		
		for(int i = 0; i < bSize; i++) {
			for(int j = 0; j < bSize; j++) {
				if(b[i][j].getType() == 0) //space
					boardState += "  ";
				else if (b[i][j].getType() == 3) //closedset
					boardState += "+ ";
				else if (b[i][j].getType() == 2) //openset
					boardState += "o ";
				else if (b[i][j].getType() == 5) //part of path
					boardState += "% ";
				else //wall
					boardState += "x ";
			}
			boardState += "\n";
		}
		
		return boardState;
	}
}
