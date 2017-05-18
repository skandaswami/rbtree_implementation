import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class bbst {

	public static void main(String[] args) {
		
		EventTree rbTree = new EventTree();
		
		try {
			String line = null;
			File file = new File(args[0]);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			int numberOfEvents = Integer.parseInt(bufferedReader.readLine());
			
		
			
			long begin = System.currentTimeMillis();
			for(int i=0; i < numberOfEvents; i++) {
				line = bufferedReader.readLine();
				String[] eventInfo = line.split(" ");
				Event node = new Event(Integer.parseInt(eventInfo[0]), Integer.parseInt(eventInfo[1]));
				
				rbTree.insert(node);
			}
			long end = System.currentTimeMillis() - begin;
			//System.out.println("Time taken: " + (time/1000) + "s" );
			
			
			bufferedReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Scanner commandsInput = new Scanner(System.in);
		
		loop: while(commandsInput.hasNextLine()) {
			String[] commands = commandsInput.nextLine().split(" ");
			
			switch(commands[0]) {
			case "increase":
				rbTree.increase(Integer.parseInt(commands[1]),Integer.parseInt(commands[2]));
				break;
			case "count":
				rbTree.count(Integer.parseInt(commands[1]));
				break;
			case "inrange":
				rbTree.inrange(Integer.parseInt(commands[1]),Integer.parseInt(commands[2]));
				break;
			case "next":
				rbTree.next(Integer.parseInt(commands[1]));
				break;
			case "reduce":
				rbTree.reduce(Integer.parseInt(commands[1]),Integer.parseInt(commands[2]));
				break;
			case "previous":
				rbTree.previous(Integer.parseInt(commands[1]));
				break;
			case "quit":
				break loop;
			default:
				System.out.println("\nSorry you have entered and invalid command!");
				break;
			}
		}
		
	}
}
