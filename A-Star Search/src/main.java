import java.util.*;

public class main {

	public static void main(String[] args) {
		List<Node> path = new ArrayList<Node>(); //TODO: DRAW PATH
		List<Node> openSet = new ArrayList<Node>();
		List<Node> closedSet = new ArrayList<Node>();
		Scanner in = new Scanner(System.in);
		board b = new board();
		int iterator = 0;
		int startX = 0;
		int startY = 0;
		System.out.print("Start X:\t");
		startX = in.nextInt();
		System.out.print("Start Y:\t");
		startY = in.nextInt();
		int endX = 14;
		int endY = 14;
		System.out.print("End X:\t");
		endX = in.nextInt();
		System.out.print("End Y:\t");
		endY = in.nextInt();
		Node start = b.b[startX][startY];
		Node end = b.b[endX][endY];
		
		openSet.add(start);
		
		while(iterator == 0) {
			System.out.println(b.toString() + "\n------------------------\n");
			iterator = b.iterate(openSet, closedSet, end);
		}
		if(iterator == -1)
			System.out.println("ERR: NO PATH");
		else
			System.out.println("PATH FOUND");

	}

}
