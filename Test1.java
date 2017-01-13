import java.util.Scanner;
class A extends Thread{
	Scanner  k = new Scanner(System.in);
	String g = "";
	public A(String g){
		this.g = g;
	}
	public void run(){
		for(int c = 0;c<5;c++){
			if(g.equals("1")){
				k.nextLine();
			}
			else{
				System.out.println("ami asi");
			}
		}
	}
}


public class Test1 {
	public static void main(String [] args){
		
		
		//new A("1").start();
		//new A("2").start();
	
		Thread t1 = new Thread(new Runnable(){
			Scanner k = new Scanner(System.in);
	
			@Override
			public void run() {
				for(int c = 0;c<5;c++){
					k.nextLine();
				}
				
			}
			
		});
		
		Thread t2 = new Thread(new Runnable(){
	
			@Override
			public void run() {
				for(int c = 0;c<5;c++){
					System.out.println("ami asi");
				}
				
			}
			
		});
		
		t1.start();
		t2.start();
		
		
		
	}

}
