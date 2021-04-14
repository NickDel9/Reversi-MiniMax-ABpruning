import java.util.Scanner;

public class Reversi {

    public static void main(String[] args) {

        Scanner ob = new Scanner(System.in);

        System.out.println("            Welcome to the Reversi !"+"\n"+"                Main menu" +"\n"+ "1 : Human vs Human " +
                "|| 2 : Human vs AI || 3: AI vs AI ");

        int Answer = ob.nextInt();

        new Play(Answer);

    }

}
