public class main {	
	
	public static void main(String[] args) {
		board b = new board();
		int rIts = 0;
		int total = 0;
		while(b.getH() > 0){
			System.out.print("Current H:" + b.getH() + "\n" + b.toString() + "Neighbors with lower h:\t");
			
			if(b.getH() != 0) {
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
			else
				System.out.println(b.toString());
			
			total++;
		}
		
		System.out.println("\n" + b.toString() + "***SOLUTION FOUND***\nState Changes:\t" + total + "\nRestarts:\t" + rIts);

	}

}
