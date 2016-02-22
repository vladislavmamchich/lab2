package main;

import java.util.Scanner;

public class MainClass {
	private static final String DEF_HASH = "187ef4436122d1cc2f40dc2b92f0eba0";
	private static final String DEF_START = "aa";
	private static final String DEF_END = "zz";
	private static final int DEF_THREADCOUNT = 1;
	
	public static void main(String[] args) {		
		int threadCount = DEF_THREADCOUNT;
		boolean isDef = false;
		String start = DEF_START;
		String end = DEF_END;
		String hash = DEF_HASH;
		if (args.length == 5 && args[0].equals("--count")){
			threadCount = Integer.valueOf(args[1]);
			start = args[2];
			end = args[3];
			hash = args[4];
		}
		else if (args.length == 3){
			start = args[0];
			end = args[1];
			hash = args[2];
		}
		else{
			isDef = true;
			System.out.println("No input parameters. Format:");
			System.out.println("\tlab3 [--count <count>] <range> <hash>");
			System.out.println("Example:");
			System.out.printf("\tlab3 --count %d %s %s %s\n", threadCount, start, end, hash);
			System.out.printf("\tlab3 %s %s %s\n", threadCount, start, end, hash);
			System.out.println("\nDefault values has been set:");
		}
		
		System.out.printf("Thread count:\t%d\n", threadCount);
		System.out.printf("Range:\t\t'%s'..'%s'\n", start, end);
		System.out.printf("MD5 hash:\t'%s'\n", hash);	
		
		Scanner sc = new Scanner(System.in);
		boolean isContinue = true;
		if (isDef){
			System.out.print("\nContinue? (y/n) > ");
			isContinue = sc.nextLine().equals("y");
		}
		sc.close();
		
		if (!isContinue)
			return;
		
		System.out.println("Processing...");
		
		TaskManager taskManager = new TaskManager(threadCount, start, end, hash);
		
		long time = System.currentTimeMillis();
		String result = taskManager.exec();
		float ftime = (float) ((System.currentTimeMillis() - time) / 1000.0);

		if (result != null){
			System.out.printf("SUCCESS! Password: '%s'\n", result);
		}
		else{
			System.out.println("Nothing interesting :-(");
		}
		
		System.out.printf("Total time: %1.2f s.\n", ftime);
		
	}

}
