package view;

public class ThreadCLI extends Thread {
CLI cl;
@Override
public void run(){
	RunCliInThread();
}
private void RunCliInThread(){
	new Thread(new Runnable(){
		public void run(){
			cl.start();
			
		}
	}).start();
	
}

}
