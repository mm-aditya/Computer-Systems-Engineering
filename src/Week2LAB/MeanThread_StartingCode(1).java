package Week2LAB;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class MeanThread {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// TODO: read data from external file and store it in an array
		       // Note: you should pass the file as a first command line argument at runtime.
        String filename = args[0];
        ArrayList<Integer> bossArray = new ArrayList<>();
        Scanner sc = new Scanner(new File(filename));
        while (sc.hasNext()) {
            bossArray.add(sc.nextInt());
        }

		// define number of threads
		int NumOfThread = Integer.valueOf(args[1]);// this way, you can pass number of threads as
		     // a second command line argument at runtime.

		// TODO: partition the array list into N subArrays, where N is the number of threads

        ArrayList<ArrayList<Integer>> subArrays = new ArrayList<>();
        int ctr=0;
        int flag =0;
        System.out.println("Text size is: "+bossArray.size()+"\n");
        while(flag==0) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < bossArray.size()/NumOfThread; i++) {
                temp.add(bossArray.get(ctr));
                ctr++;
            }
            subArrays.add(temp);
            if(ctr==bossArray.size())
                flag=1;
        }
        System.out.println("DONE PARTITIONING\n");

		// TODO: start recording time
        long initTime = System.nanoTime();

		// TODO: create N threads and assign subArrays to the threads so that each thread computes mean of
		    // its repective subarray. For example,

        ArrayList<MeanMultiThread> threads= new ArrayList<>();
        for(ArrayList<Integer> u: subArrays) {
            threads.add(new MeanMultiThread(u));
        }
        System.out.println("ADDED THREADS\n");

        //Tip: you can't create big number of threads in the above way. So, create an array list of threads.

		// TODO: start each thread to execute your computeMean() function defined under the run() methods
		   //so that the N mean values can be computed. for example,
        for(MeanMultiThread m: threads) {
            m.start(); //start thread1 on from run() function
        }
        for(MeanMultiThread m: threads) {
            m.join(); //start thread1 on from run() function
        }

		// TODO: show the N mean values
        ArrayList<Double> tempvals = new ArrayList<>();
		ctr = 1;
        for(MeanMultiThread m: threads){
            System.out.print("Temporal mean value of thread "+(ctr++)+" is ... ");
            tempvals.add(m.getMean());
            System.out.println(m.getMean());
        }

		// TODO: store the temporal mean values in a new array so that you can use that
		    /// array to compute the global mean.
        // DONE

		// TODO: compute the global mean value from N mean values.
        double globMean = 0;
        for(Double i: tempvals)
            globMean+=i;
        globMean = globMean/tempvals.size();

		// TODO: stop recording time and compute the elapsed time
        long timeTaken = System.nanoTime()-initTime;
		System.out.println("The global mean value is ... "+globMean);
        System.out.println("\nTime taken: "+(timeTaken/1000000000.0)+" seconds");

	}
}
//Extend the Thread class
class MeanMultiThread extends Thread {
	private ArrayList<Integer> list;
	private double mean;

	public ArrayList<Integer> getInternal(){
	    return list;
    }

	MeanMultiThread(ArrayList<Integer> array) {
		list = array;
	}
	public double getMean() {
		return mean;
	}
	public void run() {
		// TODO: implement your actions here, e.g., computeMean(...)
		mean = computeMean(list);
	}

	public double computeMean(ArrayList<Integer> list){
	    double sum=0;
	    for(Integer i: list){
	        sum+=i;
        }
        sum=sum/list.size();
        return sum;
    }
}
