package bitofcode.mazes.division;
import java.util.*;

public class DivisionMaze {

    public final byte[][] maze;

    
    public static void main(String[] args) {
        DivisionMaze dmaze = new DivisionMaze(3, 3);
        for(int i = 0 ; i < 3; i++) {
            System.out.println(Arrays.toString(dmaze.maze[i]));
        }
    }
    
    
    
    public DivisionMaze(int rows, int cols) {



        maze = new byte[rows][cols];


        ArrayDeque<Chamber> chambers = new ArrayDeque<>();
        chambers.push(new Chamber(0, 0, rows, cols));

        while(!chambers.isEmpty()) {
            // System.out.println(chambers.toString());
            // for(int i = 0 ; i < 3; i++) {
            //     System.out.println(Arrays.toString(this.maze[i]));
            // }
            // System.out.println();
            Chamber chamber = chambers.remove();
            chamber.split(maze);
            if(chamber.childA != null) chambers.push(chamber.childA);
            if(chamber.childB != null) chambers.push(chamber.childB);
        }
    }


    private class Chamber {
        public int rows, cols, row, col;
        public Chamber childA, childB;
        public Chamber(int row, int col, int rows, int cols) {
            this.row = row;
            this.col = col;
            this.rows = rows;
            this.cols = cols;

        }

        public void split(byte[][] maze) {
            // boolean horizontal = Math.random() < 0.5;
            boolean horizontal = this.rows > this.cols;
            if((horizontal || this.cols < 2) && this.rows >= 2) {
                // System.out.println("Splitting " + this.toString() + " horizontally");
                int rowsOfChildA = (int)(Math.random() * (this.rows - 1)) + 1; 
                this.childA = new Chamber(this.row, this.col, rowsOfChildA, this.cols);
                this.childB = new Chamber(this.row + rowsOfChildA, this.col, this.rows - rowsOfChildA, this.cols);

                int i = 0;
                for(; i < (int)(Math.random() * this.cols); i++) {
                    maze[this.row + rowsOfChildA - 1][this.col + i] = (byte)(maze[this.row + rowsOfChildA - 1][this.col + i] | 4);
                    maze[this.row + rowsOfChildA    ][this.col + i] = (byte)(maze[this.row + rowsOfChildA    ][this.col + i] | 1);
                }
                i++;
                for(; i < this.cols; i++) {
                    maze[this.row + rowsOfChildA - 1][this.col + i] = (byte)(maze[this.row + rowsOfChildA - 1][this.col + i] | 4);
                    maze[this.row + rowsOfChildA    ][this.col + i] = (byte)(maze[this.row + rowsOfChildA    ][this.col + i] | 1);
                }

            } else if(this.cols >= 2){
                // System.out.println("Splitting " + this.toString() + " vetically");

                int colsOfChildA = (int)(Math.random() * (this.cols - 1)) + 1; 
                this.childA = new Chamber(this.row, this.col, this.rows, colsOfChildA);
                this.childB = new Chamber(this.row, this.col + colsOfChildA, this.rows, this.cols - colsOfChildA);

                int i = 0;
                for(; i < (int)(Math.random() * this.rows); i++) {
                    maze[this.row + i][this.col + colsOfChildA - 1] = (byte)(maze[this.row + i][this.col + colsOfChildA - 1] | 2);
                    maze[this.row + i][this.col + colsOfChildA    ] = (byte)(maze[this.row + i][this.col + colsOfChildA    ] | 8);
                }
                i++;
                for(; i < this.rows; i++) {
                    maze[this.row + i][this.col + colsOfChildA - 1] = (byte)(maze[this.row + i][this.col + colsOfChildA - 1] | 2);
                    maze[this.row + i][this.col + colsOfChildA    ] = (byte)(maze[this.row + i][this.col + colsOfChildA    ] | 8);
                }
            }
        }


        public String toString() {
            return "[(" + this.row + "," + this.col + ")("+ this.rows + "," + this.cols + ")]";
        }
    }
}
