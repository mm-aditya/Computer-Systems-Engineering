package LAB3;// package Week3;

import com.sun.javafx.image.IntToIntPixelConverter;

public class BankImpl {
	private int numberOfCustomers;	// the number of customers
	private int numberOfResources;	// the number of resources

	private int[] available; 	// the available amount of each resource
	private int[][] maximum; 	// the maximum demand of each customer
	private int[][] allocation;	// the amount currently allocated
	private int[][] need;		// the remaining needs of each customer

	public BankImpl (int[] resources, int numberOfCustomers) {
		// TODO: set the number of resources
		numberOfResources = resources.length;

		// TODO: set the number of customers
		this.numberOfCustomers = numberOfCustomers;

		// TODO: set the value of bank resources to available
		available = resources;

		// TODO: set the array size for maximum, allocation, and need
		maximum = new int[this.numberOfCustomers][numberOfResources];
		allocation = new int[this.numberOfCustomers][numberOfResources];
		need = new int[this.numberOfCustomers][numberOfResources];

	}

	public int getNumberOfCustomers() {
		// TODO: return numberOfCustomers
		return numberOfCustomers;
	}

	public void addCustomer(int customerNumber, int[] maximumDemand) {
		// TODO: add customer, update maximum and need
		maximum[customerNumber] = maximumDemand;
		for(int i = 0; i < numberOfResources;i++) {
			need[customerNumber][i] = maximum[customerNumber][i];
		}
	}

	public void getState() {
		// TODO: print the current state with a tidy format
		System.out.println("--------Current state of the bank------------\n");

		// TODO: print available
		System.out.println("Available:");
		for(int i =0;i<numberOfResources;i++)
			System.out.print(String.format("%4d",available[i]));
		System.out.println("\n");

		// TODO: print maximum
		System.out.println("Maximum:");
		for(int i =0; i < numberOfCustomers;i++){
			for(int k=0;k<numberOfResources;k++)
				System.out.print(String.format("%4d",maximum[i][k]));
			System.out.print("\n");
		}
		System.out.print("\n");

		// TODO: print allocation
		System.out.println("Allocation:");
		for(int i =0; i < numberOfCustomers;i++){
			for(int k=0;k<numberOfResources;k++)
				System.out.print(String.format("%4d",allocation[i][k]));
			System.out.print("\n");
		}
		System.out.print("\n");

		// TODO: print need
		System.out.println("Need:");
		for(int i =0; i < numberOfCustomers;i++){
			for(int k=0;k<numberOfResources;k++)
				System.out.print(String.format("%4d",need[i][k]));
			System.out.print("\n");
		}
		System.out.print("\n");

	}

	public synchronized boolean requestResources(int customerNumber, int[] request) {
		// TODO: print the request
		System.out.println("Request is: ");
		for(int i = 0; i < request.length;i++)
			System.out.print(String.format("%4d",request[i]));
		System.out.println("\n");

		// TODO: check if request larger than need
		if(!lessThan(request,need[customerNumber]))
			return false;
		//System.out.println("need smaller than request!");

		// TODO: check if request larger than available
		if(!lessThan(request,available))
			return false;
		//System.out.println("Available smaller than request");

		// TODO: check if the state is safe or not
		if(!checkSafe(customerNumber,request))
			return false;

		//System.out.println("allocatin");
		// TODO: if it is safe, allocate the resources to customer customerNumber
		for(int i =0; i < numberOfResources;i++) {
			available[i] = available[i] - request[i];
			allocation[customerNumber][i] = allocation[customerNumber][i] + request[i];
			need[customerNumber][i] = need[customerNumber][i] - request[i];
		}
		return true;
	}

	public synchronized void releaseResources(int customerNumber, int[] release) {
		// TODO: print the release
		// release the resources from customer customerNumber
		System.out.println("Release requested is: ");
		for(int i = 0; i < release.length;i++)
			System.out.print(String.format("%4d",release[i]));

		if(lessThan(need[customerNumber],release))
			System.out.println("Cannot release!");
		else {
			for (int i = 0; i < numberOfResources; i++)
				allocation[customerNumber][i] = allocation[customerNumber][i] - release[i];
		}
	}

	private synchronized boolean checkSafe(int customerNumber, int[] request) {
		// TODO: check if the state is safe
//		int[] curravailable = available.clone();
//		int[][] currneed = need.clone();
//		int[][] currallocation = allocation.clone();
//		boolean[] finish = new boolean[numberOfCustomers];
		int[][] currneed = new int[10][10];
		int[] curravailable = new int[10];
		int[][] currallocation = new int[10][10];
		boolean[] finish = new boolean[10];
		int[] work = new int[10];

		for(int a = 0; a < this.numberOfResources; a++){
			curravailable[a] = this.available[a] - request[a];
			work[a] = curravailable[a];
			for(int i = 0; i < numberOfCustomers; i++){
				if (i == customerNumber){
					currneed[customerNumber][a] = this.need[customerNumber][a] - request[a];
					currallocation[customerNumber][a] = this.allocation[customerNumber][a] + request[a];
				}
				else{
					currneed[i][a] = this.need[i][a];
					currallocation[i][a] = this.allocation[i][a];
				}
			}
		}

		for(int i = 0; i < numberOfCustomers;i++)
			finish[i] = false;

		boolean possible = true;

		for(int i =0;i<numberOfResources;i++)
			curravailable[i] = available[i] - request[i];

		for(int i =0;i<numberOfResources;i++)
			currneed[customerNumber][i] = need[customerNumber][i] - request[i];

		for(int i =0;i<numberOfResources;i++) {
			currallocation[customerNumber][i] = allocation[customerNumber][i] + request[i];
		}


		while(possible){
			//System.out.println("running this!");
			//System.out.println(numberOfCustomers);
			possible = false;
			for(int i =0; i < numberOfCustomers;i++){
				if(!finish[i] && lessThan(currneed[i],work)){
					possible = true;
					for(int k =0; k < numberOfResources;k++)
						work[k]+=currallocation[i][k];
					finish[i] = true;
				}
			}
		}

		boolean res = true;
		for(int i = 0; i < numberOfCustomers;i++){
			if(!finish[i])
				res = !res;
		}
		return res;

	}

	private boolean lessThan(int[] a, int[] b){
		boolean res = true;
		for(int i = 0; i <a.length;i++){
			if(a[i] > b[i])
				res = !res;
		}
		return res;
	}
}