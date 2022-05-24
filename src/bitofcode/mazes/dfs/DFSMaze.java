package bitofcode.mazes.dfs;
import java.util.*;

public class DFSMaze {

    public static void main(String[] args) {
        DFSMaze test = new DFSMaze(3, 3);
        for(int i = 0 ; i < 10; i++) {
            System.out.println(Arrays.toString(test.maze[i]));
        }
    }


    public final byte[][] maze;
    public DFSMaze(int w, int h) {
        maze = new byte[h][w];

        for(int i = 0 ; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = 15;
            }
        }

        Pos current = new Pos(0, 0);
        boolean[][] visited = new boolean[h][w];
        ArrayDeque<Pos> stack = new ArrayDeque<>();
        stack.push(current);
        while(current != null) {
            visited[current.row][current.col] = true;
            List<Pos> neighbors = getNeighbors(current, maze, visited);
            if(!neighbors.isEmpty()) {
                int n = (int)(Math.random() * neighbors.size());
                Pos next = neighbors.get(n);
                byte dir = current.getDir(next);
                maze[current.row][current.col] = (byte)(maze[current.row][current.col] & ~dir);
                maze[next.row][next.col] = (byte)(maze[next.row][next.col] & ~invert(dir));
                stack.push(current);
                current = next;
            } else {
                current = stack.isEmpty() ? null : stack.pop();
            }
        }
    }

    private byte invert(byte b) {
        if(b == 1) return 4;
        if(b == 2) return 8;
        if(b == 4) return 1;
        if(b == 8) return 2;
        else return 0;
    }

    private List<Pos> getNeighbors(Pos pos, byte[][] grid, boolean[][] visited) {
        List<Pos> result = new ArrayList<>(4);
        byte square = grid[pos.row][pos.col];
        if((square & 1) > 0 && pos.row > 0                        && !visited[pos.row - 1][pos.col]) result.add(pos.up()   ); 
        if((square & 2) > 0 && pos.col < grid[pos.row].length - 1 && !visited[pos.row][pos.col + 1]) result.add(pos.right()); 
        if((square & 4) > 0 && pos.row < grid.length - 1          && !visited[pos.row + 1][pos.col]) result.add(pos.down() ); 
        if((square & 8) > 0 && pos.col > 0                        && !visited[pos.row][pos.col - 1]) result.add(pos.left() ); 


        

        return result;
    }

    private class Pos {
        public int row, col;
        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Pos up() {
            return new Pos(this.row - 1, this.col);
        }
        public Pos down() {
            return new Pos(this.row + 1, this.col);
        }
        public Pos left() {
            return new Pos(this.row, this.col - 1);
        }
        public Pos right() {
            return new Pos(this.row, this.col + 1);
        }

        public byte getDir(Pos other) {
            if(this.col == other.col) {
                if(this.row > other.row) return 1;
                else return 4;
            } else {
                if(this.col < other.col) return 2;
                else return 8;
            }
        }

        public String toString() {
            return "(" + this.row + "," + this.col + ")";
        }
    }


}