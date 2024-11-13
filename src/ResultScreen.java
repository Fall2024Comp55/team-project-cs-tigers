
public class ResultScreen {
	
	public void ShowResult(Player user, Player cpu) {
		
		System.out.println("= = = = = Result Screen = = = = =\n\n"); //Main Heading 
		if (user.score() > cpu.score()) {
			System.out.println(user.getName() + " is the winer !!!\n"); //Congratulating the User on victory
			System.out.println("User Score: " + user.score() + "\n"); //Display user score
		}
		else {
			System.out.println("User did not win. Try again!!!\n"); //Try Again prompt for the user 
			System.out.println("User Score: " + user.score() + "\n"); //Displaying user score  
		}	
		//Displaying the Creators of the game.
		System.out.println("Creators:\n");
		System.out.println("Ibrahim");
		System.out.println("Paul");
		System.out.println("Faizah");
		System.out.println("Rogelio");
		System.out.println("Thank you for playing.");
		
	}
	
}

/*
    public static void main(String[] args) {
        System.out.println("----- Result Screen -----");

        Player user = new Player("User", 50); // placeholder  
        Player cpu = new Player("CPU", 25);   //placeholder

        if (user.score() > cpu.score()) {
            System.out.println(user.getName() + " is the winner!!!");
            System.out.println("User Score: " + user.score());
        } else {
            System.out.println("CPU wins! Try again!");
            System.out.println("Score: " + cpu.score());
            

        	//Credits - Made by Ibrahim, Faizah, Paul, Rogelio
            System.out.println("\n");
        	System.out.println("Thank you for playing\n");
        	System.out.println("This game was made by Ibrahim, Faizah, Paul and Rogelio\n");
        }
    }
}
// tester 
// demo placerholder 
class Player {
    private String name;
    private int score = 0;

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
} */
