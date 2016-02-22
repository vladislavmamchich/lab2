package main;

import java.util.Vector;

public class TaskManager {

	private String start;
	private String end;
	private String hash;
	private int threadCount;
		
	public TaskManager(int threadCount, String start, String end, String hash){
		this.threadCount = threadCount;
		this.start = start;
		this.end = end;
		this.hash = hash;
	}
	
	public String exec(){
		try{
			Vector<String> ranges = MD5Task.slice(start, end, threadCount);
			ranges.insertElementAt(start, 0);
			
			MD5Task[] tasks = new MD5Task[threadCount];
			Thread[] threads = new Thread[threadCount];
						
			for (int i = 0; i < tasks.length; ++i){
				tasks[i] = new MD5Task(hash, ranges.elementAt(i), ranges.elementAt(i + 1));
				threads[i] = new Thread(tasks[i]);
				threads[i].start();
			}
			
			for (Thread thread : threads){
				thread.join();
			}
						
			for (MD5Task task : tasks){
				String result = task.getResult();
				if (result != null)
					return result;
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;		
	}
}
