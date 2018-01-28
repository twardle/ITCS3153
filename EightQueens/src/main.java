public class main {	
	
	public static void main(String[] args) {
		board b = new board();
		int iterations = 0;
		while(b.getH() > 0){
			System.out.print("Current H:" + b.getH() + "\n" + b.toString() + "Neighbors with lower h:\t");
			
			if(b.getH() != 0) {
				System.out.println(b.iterate());
			}
			else
				System.out.println(b.toString());
			
			iterations++;
		}
		
		System.out.println("\n\n\n***SOLUTION FOUND***\n\n" + b.toString());

	}

}
