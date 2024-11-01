
public class ResultScreen {

public static void main (String[] args) {
	System.out.println("---- Result Screen ----");
	
	if (user.score() > cpu.score()) {
		System.out.println(user + " is the winner!!!");
		System.out.println(user.score);
	}
	else {}
	System.out.println("CPU wins! Try again!");
	System.out.println(cpu.score);
}

}
