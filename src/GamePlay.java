import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// In this class the minimax algorithm is implemented as well as the heuristic function "score"

class GamePlay {

    Scanner obj = new Scanner(System.in);
    private int minimaxDecisionForX , minimaxDecisionForY;
    protected char player;
    private final Board current_board;

    // each call of constructor creates a new object of class Board

    public GamePlay(int a, int b) {
        this.current_board = new Board(a, b);
    }

    public GamePlay(Board board) {
        this.current_board = new Board(board);
    }


    public Board get_board() {
        return this.current_board;
    }

    //for each time user plays is called this method

    protected int UserPlays(String user, char play)
    {
        System.out.println(user + "'s turn\n" + "Give your coordinates , e.g : 0 3 ");
        String str = obj.nextLine();
        List<Point<Integer>> has_moves;//list of class Point objects type Integer for storing in it the valid movements of the player
        has_moves = this.get_available_moves(play);
        if (has_moves.size() == 0) {
            return -1;
        }
        try {
            try {
                String[] pl = str.split(" ");
                int a = Integer.parseInt(pl[0]);
                int b = Integer.parseInt(pl[1]);
                return (this.current_board.change_current_board(a, b, play));//This function call will change the board according to the user's coordinates and will return an integer if the change completed or 0 if not
            } catch (ArrayIndexOutOfBoundsException e) {
                return 0;
            }
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //for each time PC plays is called this method

    protected int botPlays(int depth, char player)
    {
        GamePlay Tmp = new GamePlay(this.current_board);//a new snaphot of class GamePlay is created inheriting the the board obj of the current snapshot so that the original snaphot remain the shame
        if (Tmp.best_move(player, depth)) { // Minimax implementation is called , it will returns true if there are available moves for player or false if not
            System.out.println(player + " move : "+Tmp.minimaxDecisionForX + "     " + Tmp.minimaxDecisionForY);
            this.current_board.change_current_board(Tmp.minimaxDecisionForX, Tmp.minimaxDecisionForY, player);//We have the final moves from the minimax and we apply them to current state of board
            this.current_board.print();
            return 0;
        }
        this.current_board.print();
        return -1;
    }

    // in this method is checked if the move :(i,j) is valid for player who play
    public boolean validmove(int i, int j, char player)
    {
        Board temp = new Board(this.current_board);// a new snapshot of class Board is created inheriting the the board obj of the current snapshot so that the original snaphot remain the shame
        return temp.change_current_board(i, j, player) > 0;// if returns an integer (>0) is true
    }

    // in this method are stored the available movements for player on board obj of class GamePlay
    public List<Point<Integer>> get_available_moves(char player)
    {
        List<Point<Integer>> availablePts = new ArrayList<>();
        for (int i = 0; i < this.current_board.a; i++) {
            for (int j = 0; j < this.current_board.b; j++) {
                if (this.validmove(i, j, player)) {
                    availablePts.add(new Point<>(i, j));
                }
            }
        }
        return availablePts;
    }

    // in this method is checked if player has more moves on board obj of class GamePlay , if has the method returns false and true if has not
    public boolean isTerminal(char player) {
        for (int i = 0; i < this.current_board.a; i++) {
            for (int j = 0; j < this.current_board.b; j++) {
                if (this.validmove(i, j, player))
                    return false;
            }
        }
        return true;
    }

    // here is the heuristic method of our minimax algorithm , it returns the summary of points for player of the definition
    public int score(char[][] board, char player)
    {
        int sum = 0;
        for (int i = 0; i < this.current_board.a; i++) {
            for (int j = 0; j < this.current_board.b; j++) {
                if ((i == 0 || i == 7) && (j == 0 || j == 7)) {//if player is at the corner of the board heuristic method gives 4 points for the current position
                    if (board[i][j] == player)
                        sum += 4;
                } else if ((i == 0 || i == 7) || (j == 0 || j == 7)) {// if player is at the edge of the board heuristic gives 2 points
                    if (board[i][j] == player)
                        sum += 2;
                } else if (board[i][j] == player) // for others simple positions heuristic gives 1 point for each
                    sum++;
            }
        }
        return sum;
    }

    //here starts the minimax a-b pruning algorithm , we have 2 methods : best_move and minimax .
    //The reason that exists the method best_move is that we need to save somewhere the final decision of minimax for which points the computer will choose
    // The best_move implements the head of the tree which is the max and then finds it children and calls them through the minimax method reducing the depth by one and making the maximizer false
    // each child of current state has different minimax tree . The most value result of chilldren is the final move

    private boolean best_move(char player, int depth)
    {
        List<Point<Integer>> available_moves = this.get_available_moves(player);
        int best_value = Integer.MIN_VALUE, best_x = -1, best_y = -1;

        if (available_moves.size() == 0) { // if list is empty returns false
            return false;
        }
        for (Point<Integer> move : available_moves) { // for each child of current state
            Board newBoard = new Board(this.current_board); // create a new snapshot
            newBoard.change_current_board(move.getX(), move.getY(), player); // change the board of this snapshot
            this.player = player; // store the letter of player for our heuristic
            int value = minimax(newBoard, player, depth - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE); // calls the minimax and stores the result of it in "value"
            //System.out.println("value = " + value + "x "+ move.x + " y "+ move.y );
            if (value > best_value) { // cause best_move is just the header of the tree and is maximizer try to find the max values from the minimizer
                best_value = value;
                best_x = move.getX();
                best_y = move.getY();
            }
        }
        //at the end , the max point is stored on classes variables minimaxDecisionForX and minimaxDecisionForY and is returned true
        this.minimaxDecisionForX = best_x;
        this.minimaxDecisionForY = best_y;

        return true;
    }

    //in method minimax there are 2 possible situations : isMax or not isMax . The code is the same for both with the difference that in isMax
    // we keep the max value from minimax call of minimizer and if this value is bigger than a previus value we break the loop cause the next step is minimizer and it will
    // choose the min value from the maximizer's call
    // On definition there are the alpha and beta values for pruning

    private int minimax(Board board, char player, int depth, boolean isMax, int alpha, int beta) {

        if (depth == 0 || this.isTerminal(player)) {
            return score(board.getArray(), this.player);
        }

        int best_value = 0;

        //method changes the player's letter it's time it calls
        if (player == 'O') {
            player = 'X';
        } else {
            player = 'O';
        }

        if (isMax) {

            int max = Integer.MIN_VALUE;

            GamePlay newgameplay = new GamePlay(board);

            List<Point<Integer>> available_moves_MAX = newgameplay.get_available_moves(player);

            if (available_moves_MAX.isEmpty())
                return 0;

            for (Point<Integer> move : available_moves_MAX) {
                Board temp1 = new Board(newgameplay.current_board);
                temp1.change_current_board(move.getX(), move.getY(), player);
                best_value = minimax(temp1, player, depth - 1, false, alpha, beta);
                max = Math.max(best_value, max);
                alpha = Math.max(alpha, max);//alpha takes the max value for minimized children
                if (beta <= alpha) { // a-b pruning if alpha>=beta then cut the rest of children
                    // System.out.println("A-B Pruning : cut BETA");
                    break;
                }
            }
        } else {

            int min = Integer.MAX_VALUE;

            GamePlay newgameplay = new GamePlay(board);

            List<Point<Integer>> available_moves_MIN = newgameplay.get_available_moves(player);

            if (available_moves_MIN.isEmpty())
                return 0;

            for (Point<Integer> move : available_moves_MIN) {
                Board temp1 = new Board(newgameplay.current_board);
                temp1.change_current_board(move.getX(), move.getY(), player);
                best_value = minimax(temp1, player, depth - 1, true, alpha, beta);
                min = Math.min(best_value, min);
                beta = Math.min(beta, min); //beta takes the min value for maximized children
                if (beta <= alpha) { // a-b pruning if alpha>=beta then cut the rest of children
                    // System.out.println("A-B Pruning : cut ALPHA");
                    break;
                }
            }
        }
        return best_value;

    }

}
