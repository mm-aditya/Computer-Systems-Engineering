package Week2LAB;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


class MedianThread {

	public static void main(String[] args) throws InterruptedException, IOException  {
		// TODO: read data from external file and store it in an array
		// Note: you should pass the file as a first command line argument at runtime.
		String filename = args[0];
		ArrayList<Integer> text = new ArrayList<>();
		Scanner sc = new Scanner(new File(filename));
		while (sc.hasNext()) {
			text.add(sc.nextInt());
		}

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
	for(ArrayList<Integer> u: subArrays) {
		threads.add(new MedianMultiThread(u));
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


	// TODO: use any merge algorithm to merge the sorted subarrays and store it to another array, e.g., sortedFullArray.
		Integer[] bigArray = new Integer[text.size()];
		Integer[] tempArray = new Integer[text.size()];
		int alen = 0;
		for(MedianMultiThread m: threads){
			merge(tempArray, bigArray,m.getInternal(),alen);
			bigArray = tempArray;
			tempArray = new Integer[text.size()];
			alen = alen+m.getInternal().length;
		}

	//TODO: get median from sortedFullArray
		double median = computeMedian(bigArray);

	// TODO: stop recording time and compute the elapsed time
		long runningTime = (System.nanoTime() - initTime);

	// TODO: printout the final sorted array
		System.out.println(Arrays.toString(bigArray));

	// TODO: printout median
	System.out.println("The Median value is "+median);
	System.out.println("Running time is " + runningTime/1000000000.0 + " seconds\n");
	}


	private static double computeMedian(Integer[] bigArray) {
	  //TODO: implement your function that computes median of values of an array
		double median;
		if ((bigArray.length) % 2 == 0)
			median = (bigArray[bigArray.length/2] + bigArray[bigArray.length/2-1])/2.0;
		else
			median = bigArray[bigArray.length/2];
		return median;
	}

	private static void merge(Integer[] dest, Integer[] a, Integer[] b,int alen) {
		Integer i = 0;
		Integer j = 0;
		while(i < alen && j < b.length) {
			if(a[i] < b[j]) {
				dest[i + j] = a[i];
				++i;
			} else {
				dest[i + j] = b[j];
				++j;
			}
		}
		for(; i < alen; i++) dest[i + j] = a[i];
		for(; j < b.length; j++) dest[i + j] = b[j];
	}


}

// extend Thread
class MedianMultiThread extends Thread {
	private Integer[] list;

	public Integer[] getInternal() {
		return list;
	}

	MedianMultiThread(ArrayList<Integer> array) {
		list = array.toArray(new Integer[array.size()]);
	}

	public void run() {
		// called by object.start()
		mergeSort(list);
	}

	// TODO: implement merge sort here, recursive algorithm
	public void mergeSort(Integer[] elts) {

		if (elts.length > 1) {
			// split the array into two pieces, as close to the same
			// size as possible.
			Integer[] first = extract(elts, 0, elts.length / 2);
			Integer[] last = extract(elts, elts.length / 2, elts.length);

			// sort each of the two halves recursively
			mergeSort(first);
			mergeSort(last);

			// merge the two sorted halves together
			merge(elts, first, last);
		}
	}

		private static Integer[] extract(Integer[] elts, Integer start, Integer last) {
			Integer[] ret = new Integer[last - start];
			for(int i = 0; i < ret.length; i++) ret[i] = elts[start + i];
			return ret;
		}

	public static void merge(Integer[] dest, Integer[] a, Integer[] b) {
		Integer i = 0;
		Integer j = 0;
		while(i < a.length && j < b.length) {
			if(a[i] < b[j]) {
				dest[i + j] = a[i];
				++i;
			} else {
				dest[i + j] = b[j];
				++j;
			}
		}
		for(; i < a.length; i++) dest[i + j] = a[i];
		for(; j < b.length; j++) dest[i + j] = b[j];
	}


	}


