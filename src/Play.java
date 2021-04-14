import java.util.Scanner;

//In this class the functions of the main menu are implemented

class Play {

    Scanner obj = new Scanner(System.in);
    GamePlay game;


    public Play(int a) {
        this.game = new GamePlay(8,8);

        switch (a){
            case 1:
                HumanvsHuman();
                break;
            case 2:
                HumanvsAI();
                break;
            case 3:
                AIvsAI();
                break;
            default:
                System.out.println("Wrong input !");
                break;
        }
    }

    private void AIvsAI() { // PC vs PC
        char[] players;
        int[] depths;
        Scanner obj1 = new Scanner(System.in);
        System.out.print("Give the depth for the minimax algorithm of O player: ");
        int depthO = obj1.nextInt();
        System.out.print("Give the depth for the minimax algorithm of X player: ");
        int depthX = obj1.nextInt();
        System.out.print("Who player do you want to start first ?");
        String Ans = this.obj.nextLine();

        if (Ans.equals("O")){
            players = new char[]{'O', 'X'};
            depths= new int[]{depthO, depthX};
        }
        else {
            players = new char[]{'X', 'O'};
            depths= new int[]{depthX, depthO};
        }

        this.game.get_board().print();
        int i = 0;
        while (!this.GameEnds()) {
            if (i==2){ i = 0;}
            System.out.println(players[i]+" play");
            this.game.botPlays(depths[i], players[i]);
            i++;
        }
        System.out.println();
        this.Who_Won();

    }

    private void HumanvsAI() { // User vs PC
        Scanner obj1 = new Scanner(System.in);
        System.out.print("Do you want to start first ? [Y/y for : yes , Another key for : no]: ");
        String answer = this.obj.nextLine();
        System.out.print("Give your Username : ");
        String user = this.obj.nextLine();
        System.out.print("Give the depth for the minimax algorithm : ");
        int depth = obj1.nextInt();

        this.game.get_board().print();

        if (this.turn(answer)) { // if true user starts first
            while (!this.GameEnds()) {
                int move = this.game.UserPlays(user, 'O');
                if (move < 0) { // if the function UserPlays returns a negative number , it means that the current player has not available moves and his lost his turn
                    System.out.println("No available moves for player " + user);
                } else if (move == 0) {
                    while (move == 0) { // if the function UserPlays returns 0 , it means that user gave wrong input and the programm gives him the opportunity to retry
                        System.out.println("Invalid input . Please give a valid move !");
                        move = this.game.UserPlays(user, 'O');
                        if (move < 0) {
                            System.out.println("No available moves for player " + user);
                        }
                    }
                }
                this.game.get_board().print();

                int move2 = this.game.botPlays(depth, 'X'); // it's time for the PC to play by calling the function botPlays
                if (move2 < 0) {
                    System.out.println("No available moves for player 'X'");
                }
            }
         }
         else{ // if first if is false , PC starts firsts and the structure of else is the same as above
            while (!this.GameEnds()) {
                int move2 = this.game.botPlays(depth, 'X');
                if (move2 < 0){
                    System.out.println("No available moves for player 'X'");
                }
                else {
                    int move = this.game.UserPlays(user,'O');
                    if (move < 0) {
                        System.out.println("No available moves for player " + user);
                    } else if (move == 0) {
                        while (move == 0) {
                            System.out.println("Invalid input . Please give a valid move !");
                            move = this.game.UserPlays(user,'O');
                            if (move < 0) {
                                System.out.println("No available moves for player " + user);
                            }
                        }
                    }
                    this.game.get_board().print();
                }
            }
        }
        System.out.println();
        this.Who_Won();

    }

    private void HumanvsHuman(){ // User vs User

        String user0 ,user1;
        System.out.print("First player is 'O' . Give your Username : ");
        user0 = this.obj.nextLine();
        System.out.print("Second player is 'X' . Give your Username : ");
        user1 = this.obj.nextLine();
        String[] User = {user0 ,user1};
        char[] UserVal = {'O' , 'X'};
        int i =0;
        this.game.get_board().print();
        while (!this.GameEnds()) {
            if (i == 2){ i = 0; }
            int move = this.game.UserPlays(User[i],UserVal[i]);
            if (move < 0) {  //if player has not available moves the move value is negative and he losts his turn
                System.out.println("No available moves for player " + User[i]);
            } else if (move == 0) {  //if player give wrong input the programm gives him another chance until input is correct
                while (move == 0) {
                    System.out.println("Invalid input . Please give a valid move !");
                    move = this.game.UserPlays(User[i],UserVal[i]);
                    if (move < 0) {
                        System.out.println("No available moves for player " + User[i]);
                    }
                }
            }
            this.game.get_board().print();
            i++;
        }
        System.out.println();
        this.Who_Won();
    }

    private boolean GameEnds(){ // a method that return true if game has ends or false if not
        return this.game.isTerminal('O') && this.game.isTerminal('X');
    }

    private void Who_Won(){ // a method that calculate the winner and his points
        char O = 'O';
        char X = 'X';
        int score1 = game.score(this.game.get_board().getArray(),O);
        int score2 = game.score(this.game.get_board().getArray(),X);
        if (score1 > score2)
            System.out.println(O + " Won with " + score1 + " points !");
        else if (score1 < score2)
            System.out.println(X + " Won with "+ score2 + " points !");
        else
            System.out.println("It's a tie !");
    }

    private boolean turn(String ch) {
        return ch.equals("Y") || ch.equals("y");
    }
}
