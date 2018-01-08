package assignment07;
//Mark Fitzgerald 15456198
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
	//create reference to sharedABQ
	private ArrayBlockingQueue<String> sharedABQ;
	//create variable for servers name
	private String serverName;
	//create variables to keep track 
	//of totals
	private int numPizza = 0;
	private int numBurger = 0;
	private int numFishChips = 0;
	private int totalOrder = 0;
	//create variable to tell a 
	//server when they're working
	private boolean working = true;

	public Server(ArrayBlockingQueue<String> abq, String name) {
		//store values passed in locally
		this.sharedABQ = abq;
		this.serverName = name;
	}

	public void run() {
		//while a server is working
		//do this
		while(working) {
			try {
				//call serve method
				serve();
				//server spends random time serving 
				//the meal
				sleep((int) (Math.random() * 50));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//print out their score
		System.out.println("Server " + serverName + " finished serving " + (totalOrder) 
				+ " orders including: " + numBurger + " burgers, " + numPizza 
				+ " pizzas and " + numFishChips + " fish n chips");
	}
	
	public void serve() throws InterruptedException {
		//peek to see if there is anything on the queue
		String toServe = sharedABQ.peek();
		//if null is returned there is nothing
		if(toServe == null){
			//poll tells the server to wait for a period of time
			//before removing something from the queue
			//this is basically a safety in case a chef is delayed 
			//while making a meal
			toServe = (String) sharedABQ.poll(500, TimeUnit.MILLISECONDS);
			if(toServe==null) {
				//if there is still nothing
				//the server is finished work
				//end the while loop by changing this to false
				working = false;
			} else {
				//else something appeared on the queue
				//print out the serving details of this
				System.out.println("Server " + serverName + " is serving: " + toServe);
				
				//split the order into a string array
				String[] foodTypes = toServe.split(" ");
				//check what type of food they ordered
				//this is to keep tally of what they served
				if (foodTypes[1].equals("Pizza")){
					//if it was pizza add this to the 
					//chefs total
					numPizza++;
					totalOrder++;
				} else if (foodTypes[1].equals("Burger")){
					//if it was burger add this to the 
					//chefs total
					numBurger++;
					totalOrder++;
				} else if (foodTypes[0].equals("Fish")){
					//if it was fish n chips add this to 
					//the chefs total
					numFishChips++;
					totalOrder++;
				} 
			}
		} else {
			//when we originally peeked
			//if this was not null we want to retrieve
			//and remove the element
			//we use poll() for this
			toServe = sharedABQ.poll();
			//print out the serving details of this
			System.out.println("Server " + this.serverName + " is serving: " + toServe);
			
			//split the order into a string array
			String[] foodTypes = toServe.split(" ");
			//check what type of food they ordered
			//this is to keep tally of what they served
			if (foodTypes[1].equals("Pizza")){
				//if it was pizza add this to the 
				//chefs total
				numPizza++;
				totalOrder++;
			} else if (foodTypes[1].equals("Burger")){
				//if it was burger add this to the 
				//chefs total
				numBurger++;
				totalOrder++;
			} else if (foodTypes[0].equals("Fish")){
				//if it was fish n chips add this to 
				//the chefs total
				numFishChips++;
				totalOrder++;
			} 
		}
	}

}
