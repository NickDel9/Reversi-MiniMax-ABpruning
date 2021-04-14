
//This class contains the main methods for our board initialization

class Board {

     protected int a,b;
     private final char [][] array;


     public Board(int a, int b)
     {
        this.a = a;
        this.b = b;
        this.array = new char[a][b];
        this.setArray();
    }

    public Board(Board board)
    {
         this.a = board.a;
         this.b = board.b;
         this.array = new char[a][b];
         this.SaveArray(board.array);
    }

    private void setArray()
    {
        for (int i = 0; i< this.a; i++)
        {
            for (int j = 0; j<this.b; j++)
            {
                if(i == this.a/2-1 && j==this.b/2-1)
                {
                    this.array[i][j] = 'O';
                }
                else if(i == this.a/2-1 && j==this.b/2)
                {
                    this.array[i][j] = 'X';
                }
                else if(i == this.a/2 && j==this.b/2-1)
                {
                    this.array[i][j] = 'X';
                }
                else if(i == this.a/2 && j==this.b/2)
                {
                    this.array[i][j] = 'O';
                }
                else
                    this.array[i][j] = '-';
            }
        }
    }

    public char[][] getArray(){ return this.array; }

    private void SaveArray(char[][] arr) {
        for (int i = 0; i < this.a; i++) {
            for (int f = 0; f < this.b; f++){
                this.array[i][f]=arr[i][f];
            }
        }
    }

    public void print()
    {
        System.out.println("- - - - - - - - -");
        for (int s = 0 ; s<this.a;s++){
            System.out.print("\t"+s);
        }
        System.out.print("\n");
        for (int i = 0; i < this.a; i++) {
            System.out.print(i);
            for (int j = 0; j < this.b; j++) {
                System.out.print("\t" +this.array[i][j]);
            }
            System.out.print("\n");
        }
    }

    public int change_current_board(int i , int j,char player)
    {
        return this.actions( i , j , player);
    }

    //method actions implements the changes for the board for each time a player decides to make a move , based from basics rules of reversi

    private int actions(int Xposition, int Yposition, char player){

        if (this.array[Xposition][Yposition]!='-'){
            return 0;
        }
        String [] TypicallMoves = {"up" , "down" , "left" , "right"};
        int temp ,sum , validMove = 0;
        boolean hasEnd ;
        for (String typicallMove : TypicallMoves) {
            if (typicallMove.equals("up")) {
                sum =0;
                hasEnd = false;
                temp = Xposition - 1;
                try {
                    int k = 0,kap;
                    for(int n = temp ; n>=0; n--){
                        if (this.array[n][Yposition] == player) {
                            hasEnd = true; // check if player's letter follows , if yes break
                            break;
                        }
                        k++;
                    }
                    kap =k;
                    while(k>0){ // check if between the 2 player's letters there are only oponent letters , if there are sum++
                        if (this.array[temp][Yposition] != '-' && this.array[temp][Yposition] != player){ sum++; }k--;temp--;}

                    if (kap>0 && sum == kap && hasEnd) {//if there are between 2 player's letters only opponnent letters make validmove > 0 ,
                                                        // mark this position with palyer's letter and call the "normal" method to apply the changes to board
                        validMove = 1;                  // --- The same logic is followed and for the other movements ---
                        this.array[Xposition][Yposition] = player;
                        this.normal(Xposition,Yposition,player, typicallMove);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            if (typicallMove.equals("down")) {
                sum = 0;
                hasEnd = false;
                temp = Xposition + 1;
                try {
                    int k = 0, kap ;
                    for(int n = temp ; n<=this.a-1; n++){
                        if (this.array[n][Yposition] == player) {
                            hasEnd = true;
                            break;
                        }
                        k++;
                    }
                    kap=k;
                    while(k>0){
                        if (this.array[temp][Yposition] != '-' && this.array[temp][Yposition] != player){ sum++; }k--;temp++;}

                    if (kap>0 && sum == kap && hasEnd) {
                        validMove = 2;
                        this.array[Xposition][Yposition] = player;
                        this.normal(Xposition,Yposition,player, typicallMove);}
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            if (typicallMove.equals("left")) {
                sum =0;
                hasEnd = false;
                temp = Yposition - 1;
                try {
                    int k = 0 ,kap;
                    for(int n = temp ; n>=0; n--){
                        if (this.array[Xposition][n] == player) {
                            hasEnd = true;
                            break;
                        }k++; }
                    kap=k;
                    while (k>0){
                        if (this.array[Xposition][temp] != '-' && this.array[Xposition][temp] != player){ sum++; }k--;temp--;}

                    if (kap>0 && sum == kap && hasEnd) { validMove = 3;
                        this.array[Xposition][Yposition] = player;
                        this.normal(Xposition,Yposition,player, typicallMove); }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
            if (typicallMove.equals("right")) {
                sum =0;
                hasEnd = false;
                temp = Yposition + 1;
                try {
                    int k = 0 ,kap;
                    for(int n = temp ; n<=this.b-1; n++){
                        if (this.array[Xposition][n] == player) {
                            hasEnd = true;
                            break;
                        } k++;}
                    kap = k;
                    while (k>0){
                        if (this.array[Xposition][temp] != '-' && this.array[Xposition][temp] != player){ sum++; }k--; temp++;}
                    if (kap>0 && sum == kap && hasEnd) {
                        validMove =4;
                        this.array[Xposition][Yposition] = player;
                        this.normal(Xposition,Yposition,player, typicallMove);}
                } catch (ArrayIndexOutOfBoundsException ignored) {}
            }
        }
        String [] diagonalMoves = {"upleft" , "upright" , "downleft" , "downright"};

        for (String diagonalMove : diagonalMoves){
            int x = 0 , y = 0;
            sum = 0;
            hasEnd = false;
            if(diagonalMove.equals("upleft")) {
                x = Xposition - 1; y = Yposition - 1;}
            if(diagonalMove.equals("upright") ) {
                x = Xposition - 1; y = Yposition + 1;}
            if(diagonalMove.equals("downleft")) {
                x = Xposition + 1; y = Yposition - 1;}
            if(diagonalMove.equals("downright")) {
                x = Xposition + 1; y = Yposition + 1; }
            try {
                int o = x;
                int n = y;
                if( this.array[o][n] != '-' && this.array[o][n] != player){
                    sum++;
                }
                while (this.array[o][n] != '-') {
                    if (this.array[o][n] == player) {
                        hasEnd = true;
                        break;
                    }if(diagonalMove.equals("upleft")) {
                        o--; n--;}
                    if((diagonalMove.equals("downleft"))){
                        o++; n--; }
                    if(diagonalMove.equals("upright")) {
                        o--; n++;}
                    if((diagonalMove.equals("downright"))){
                        o++; n++;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
            try{
                if (hasEnd && sum>0) {
                    validMove = 5;
                    this.array[Xposition][Yposition] = player;
                    this.diagonial(Xposition,Yposition,player, diagonalMove);
                }
            } catch (ArrayIndexOutOfBoundsException ignored){}
        }

        return validMove;
    }

    // here the changes that the "actions" decided to did for a diagonial move , be applied
    private void diagonial(int Xposition , int Yposition ,char player , String mov){
        int x ,y ;
        char cond ;
        if (mov.equals("upleft") || mov.equals("downleft")){ y = Yposition - 1;}
        else { y = Yposition + 1; }
        if (mov.equals("upleft") || mov.equals("upright")){x= Xposition - 1;}
        else { x = Xposition + 1; }

        cond = this.array[x][y];
        while(cond != player){
            if (player == 'O') {
                if (player != this.array[x][y] && this.array[x][y] != '-') {
                    this.array[x][y] = 'O';
                }
            }else {
                if (player != this.array[x][y] && this.array[x][y] != '-') {
                    this.array[x][y] = 'X';
                }
            }
            if (mov.equals("upleft") || mov.equals("downleft")){ y=y-1; }
            else { y=y+1; }
            if (mov.equals("upleft") || mov.equals("upright")) { x=x-1;}
            else { x=x+1; }
            cond = this.array[x][y];
        }
    }

    // here the changes that the "actions" decided to did for a normal move , be applied
    private void normal(int Xposition , int Yposition ,char player , String mov) {
        int temp1;
        char cond;
        switch (mov) {
            case "up":
                temp1 = Xposition - 1;
                break;
            case "down":
                temp1 = Xposition + 1;
                break;
            case "left":
                temp1 = Yposition - 1;
                break;
            case "right":
                temp1 = Yposition + 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mov);
        }
        if (player == 'O') {
            cond = 'X';
        } else {
            cond = 'O';
        }
        while (cond != player) {
            if (mov.equals("up") || mov.equals("down")) {
                if (player != this.array[temp1][Yposition] && this.array[temp1][Yposition] != '-') {
                    if (player == 'O')
                        this.array[temp1][Yposition] = 'O';
                    else
                        this.array[temp1][Yposition] = 'X';
                }
            } else {
                if (player != this.array[Xposition][temp1] && this.array[Xposition][temp1] != '-') {
                    if (player == 'O')
                        this.array[Xposition][temp1] = 'O';
                    else
                        this.array[Xposition][temp1] = 'X';
                }
            }
            if (mov.equals("up") || mov.equals("left")) {
                temp1 = temp1 - 1;
            } else {
                temp1 = temp1 + 1;
            }
            if (mov.equals("up") || mov.equals("down")) {
                cond = this.array[temp1][Yposition];
            } else {
                cond = this.array[Xposition][temp1];
            }
        }
    }

}
