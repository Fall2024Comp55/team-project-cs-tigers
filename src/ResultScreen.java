
/*public class ResultScreen {

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
*/
public class ResultScreen {

    public static void main(String[] args) {
        System.out.println("Result Screen");

        Player user = new Player("User", 50); // placeholder  
        Player cpu = new Player("CPU", 25);   //placeholder

        if (user.score() > cpu.score()) {
            System.out.println(user.getName() + " is the winner!!!");
            System.out.println("Score: " + user.score());
        } else {
            System.out.println("CPU wins! Try again!");
            System.out.println("Score: " + cpu.score());
        }
    }
}

// demo placerholder 
class Player {
    private String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int score() {
        return score;
    }

    public String getName() {
        return name;
    }
}
