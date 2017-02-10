package Week2LAB;

import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;


class MedianThread {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException  {
		// TODO: read data from external file and store it in an array
		// Note: you should pass the file as a first command line argument at runtime.
		//System.out.println(args[0]);
		String a=null;
		String fname = "input.txt";
		fname = args[0];
		BufferedReader f = new BufferedReader(new FileReader(fname));
		ArrayList<Integer> text = new ArrayList<>();
		System.out.println("parsing file now");
		a = f.readLine();
		for(String s: a.split(" ")) {
			//System.out.println(s);
			text.add(Integer.parseInt(s));
		}
		System.out.println("Text is: "+text);

	// define number of threads
	int NumOfThread = Integer.valueOf(args[1]);// this way, you can pass number of threads as
	     // a second command line argument at runtime.

	// TODO: partition the array list into N subArrays, where N is the number of threads
	ArrayList<ArrayList<Integer>> subArrays = new ArrayList<>();
	int ctr=0;
	int flag =0;
	System.out.println("Text size is: "+text.size());
	while(flag==0) {
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 0; i < text.size()/NumOfThread; i++) {
			temp.add(text.get(ctr));
			ctr++;
		}
		subArrays.add(temp);
		if(ctr==text.size())
			flag=1;
	}
		System.out.println("DONE PARTITIONING");


	// TODO: start recording time
	long initTime = System.nanoTime();
	// TODO: create N threads and assign subArrays to the threads so that each thread sorts
	    // its repective subarray. For example,

	ArrayList<MedianMultiThread> threads= new ArrayList<>();
	ctr = 0;
	for(ArrayList<Integer> u: subArrays) {
		threads.add(new MedianMultiThread(u));
		System.out.println(threads.get(ctr++).getInternal().size());
	}
		System.out.println("ADDED THREADS");
	//Tip: you can't create big number of threads in the above way. So, create an array list of threads.

	// TODO: start each thread to execute your sorting algorithm defined under the run() method, for example,
		for(MedianMultiThread m: threads) {
		m.start(); //start thread1 on from run() function
		}
		for(MedianMultiThread m: threads) {
			m.join(); //start thread1 on from run() function
		}

		for(MedianMultiThread m: threads){
			System.out.println(m.getInternal());
		}

		System.out.println("MERGIN BEGINS");


	// TODO: use any merge algorithm to merge the sorted subarrays and store it to another array, e.g., sortedFullArray.
	ArrayList<Integer> bigArray = new ArrayList<>();
	ArrayList<Integer> smolBig = new ArrayList<>();
	ArrayList<Integer> tempr;
	ArrayList<Integer> curr;
	smolBig = threads.get(0).getInternal();
	for (MedianMultiThread k: threads){
		int ctr1 = 0;
		int ctr2 = 0;
		tempr = new ArrayList<>();
		curr = k.getInternal();
		while(ctr1!=smolBig.size()+1 && ctr2!=curr.size()+1){
			if(ctr1==smolBig.size()+1){
				tempr.add(curr.get(ctr2));
				ctr2++;
			}
			else if(ctr2==curr.size()+1){
				tempr.add(smolBig.get(ctr1));
				ctr1++;
			}
			else {
				if (smolBig.get(ctr1) < curr.get(ctr2)) {
					tempr.add(smolBig.get(ctr1));
					ctr1++;
				} else {
					tempr.add(curr.get(ctr2));
					ctr2++;
				}
			}
		}
		bigArray = tempr;
		smolBig = bigArray;
	}

	//TODO: get median from sortedFullArray
		double median = computeMedian(bigArray);

	    //e.g, computeMedian(sortedFullArray);

	// TODO: stop recording time and compute the elapsed time
		long runningTime = (System.nanoTime() - initTime)% 1000000;

	// TODO: printout the final sorted array
		System.out.println(bigArray);

	// TODO: printout median
	System.out.println("The Median value is "+median);
	System.out.println("Running time is " + runningTime + " milliseconds\n");
	}

	public static double computeMedian(ArrayList<Integer> bigArray) {
	  //TODO: implement your function that computes median of values of an array
		double median;
		if (bigArray.size() % 2 == 0)
			median = ((double)bigArray.get(bigArray.size()/2) + (double)bigArray.get(bigArray.size()/2-1))/2;
		else
			median = (double)bigArray.get(bigArray.size()/2);
		return median;
	}

}

// extend Thread
class MedianMultiThread extends Thread {
	private ArrayList<Integer> list;

	public ArrayList<Integer> getInternal() {
		return list;
	}

	MedianMultiThread(ArrayList<Integer> array) {
		list = array;
	}

	public void run() {
		// called by object.start()
		int left = 0;
		int right = list.size()-1;
		int a[] = new int[list.size()];
		int[] arr = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				arr[i] = list.get(i);
			}
		}
		MergeSort_Recursive(a,left,right);
	}



	static public void DoMerge(int [] numbers, int left, int mid, int right)
	{
		int [] temp = new int[25];
		int i, left_end, num_elements, tmp_pos;

		left_end = (mid - 1);
		tmp_pos = left;
		num_elements = (right - left + 1);

		while ((left <= left_end) && (mid <= right))
		{
			if (numbers[left] <= numbers[mid])
				temp[tmp_pos++] = numbers[left++];
			else
				temp[tmp_pos++] = numbers[mid++];
		}

		while (left <= left_end)
			temp[tmp_pos++] = numbers[left++];

		while (mid <= right)
			temp[tmp_pos++] = numbers[mid++];

		for (i = 0; i < num_elements; i++)
		{
			numbers[right] = temp[right];
			right--;
		}
	}

	static public void MergeSort_Recursive(int [] numbers, int left, int right)
	{
		int mid;

		if (right > left)
		{
			mid = (right + left) / 2;
			MergeSort_Recursive(numbers, left, mid);
			MergeSort_Recursive(numbers, (mid + 1), right);

			DoMerge(numbers, left, (mid+1), right);
		}
	}

}
