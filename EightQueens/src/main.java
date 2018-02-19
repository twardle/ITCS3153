public class main {	
	
	public static void main(String[] args) {
		//constructor for board, default constructs an 8x8 board, overloaded with an integer value constructs an NxN board.
		board b = new board();  
		int rIts = 0;  //counter for restarts
		int total = 0; //counter for total iterations
		while(b.getH() > 0){
			//prints board after each iteration
			System.out.print("Current H:" + b.getH() + "\n" + b.toString() + "Neighbors with lower h:\t");
			
			//checks if the board is a solution
			if(b.getH() != 0) {
				//iterates the board and checks if the it is a local minima
				int h = b.iterate();
				if(h > 0) {
					System.out.println(h);
					System.out.println("Setting new Current State\n");
				}
				else {
					System.out.println(-h);
					System.out.println("RESTART\n");
					rIts++;
				}
			}
			//otherwise prints the board
			else
				System.out.println(b.toString());
			//iterates the counter
			total++;
		}
		
		//prints the solution
		System.out.println("\n" + b.toString() + "***SOLUTION FOUND***\nState Changes:\t" + total + "\nRestarts:\t" + rIts);

	}

}
