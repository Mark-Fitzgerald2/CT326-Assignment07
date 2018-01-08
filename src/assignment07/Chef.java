package assignment07;
//Mark Fitzgerald 15456198
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

public class Chef extends Thread {

	//create local ABQ
	private ArrayBlockingQueue<String> sharedABQ;
	//create variable for chefName
	private String chefName;
	//create a linkedList for the remaining orders
	private LinkedList<String> ordersLeft;
	//create variables to keep a tally
	//of who did what
	private int numPizza = 0;
	private int numBurger = 0;
	private int numFishChips = 0;
	private int totalOrder = 0;

	public Chef(ArrayBlockingQueue<String> abq, LinkedList<String> lList, String name) {
		//store the values passed in locally
		this.sharedABQ = abq;
		this.chefName = name;
		this.ordersLeft = lList;
	}

	public void run() {
		//check if there is still an order to do
		while (!ordersLeft.isEmpty()) {
			try {
				//call cook method
				cook();
				//chef spends random time cooking the meal
				sleep((int) (Math.random() * 100));
			} catch (InterruptedException e) {

			}
		}
		//print out the details at the end
		System.out.println("Chef " + chefName + " finished preparing " + (totalOrder) 
				+ " orders including: " + numBurger + " burgers, " + numPizza 
				+ " pizzas and " + numFishChips + " fish n chips");
	}
	
	//cook method
	public void cook() throws InterruptedException {
		//remove the customers order from the LL
		String custOrder = (String) ordersLeft.removeFirst();
		//print the details to screen of the chef and the 
		//meal they are preparing
		System.out.println("Chef " + chefName + " is preparing " + custOrder); 
		
		//split up the customers order into a string array
		String[] foodTypes = custOrder.split(" ");
		//check which type of food they ordered
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
		//put the meal onto the 
		//shared ArrayBlockingQueue
		sharedABQ.put(custOrder);
	}
}
