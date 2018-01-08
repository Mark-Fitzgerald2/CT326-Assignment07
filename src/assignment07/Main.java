package assignment07;
//Mark Fitzgerald 15456198
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
	
	public static void main(String args[]) throws IOException {
		//create a linkedList to hold orders
		//LL is easier than arrayList as we have 
		//a pointer to the head 
		//therefore we can remove the first order first
		LinkedList<String> lList = new LinkedList<String>();
		//create a file reader and give it the name
		//of the file it needs to read
		FileReader fr = new FileReader("orderList.txt");
		//create a buffered reader and give it to the fr
		BufferedReader br = new BufferedReader(fr);
		//create a string line
		String line;
		//while the line being read is not null
		while ((line = br.readLine())!=null) {
			//store the contents of the line
			//in the LL
			//this stores each order individually
			lList.add(line);
		}
		//close the buffered reader
		br.close();
		//create an arrayBlockingQueue with size of 3
		//this means that if the chefs make three things
		//in a row a server has to remove one of these
		//before a chef can put out another meal
		ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<String>(3);
		
		//create our two chefs
		Chef john = new Chef(abq, lList, "John");
		Chef mark = new Chef(abq, lList, "Mark");
		//create our three servers
		Server katie = new Server(abq, "Katie");
		Server andrew = new Server(abq, "Andrew");
		Server emily = new Server(abq, "Emily");
		//create threads for them all
		Thread chefJohnThread = new Thread(john);
		Thread chefMarkThread = new Thread(mark);
		Thread serverKatieThread = new Thread(katie);
		Thread serverAndrewThread = new Thread(andrew);
		Thread serverEmilyThread = new Thread(emily);
		//start each thread
		chefJohnThread.start();
		chefMarkThread.start();
		serverKatieThread.start();
		serverAndrewThread.start();
		serverEmilyThread.start();
	}

}
