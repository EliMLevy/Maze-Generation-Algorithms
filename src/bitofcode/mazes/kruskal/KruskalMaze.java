package bitofcode.mazes.kruskal;

import java.util.*;

public class KruskalMaze {

    public byte[][] maze;
    private int rows, cols;

    public static void main(String[] args) {
        KruskalMaze test = new KruskalMaze(3, 3);
        for(int i  =0; i < 3; i++) {
            System.out.println(Arrays.toString(test.maze[i]));
        }
    }

    public KruskalMaze(int rows, int cols) {
        this.maze = new byte[rows][cols];

        this.rows = rows;
        this.cols = cols;

        Set<Edge> edges = new HashSet<>();

        for(int i = 0; i < rows; i++) {
            for(int j = 0 ; j < cols; j++) {
                if(i < rows - 1) edges.add(new Edge(i, j, i + 1, j));
                if(j < cols - 1) edges.add(new Edge(i, j, i, j + 1));

            }
        }

        List<Edge> bagOfEdges = new LinkedList<>(edges);
        Collections.shuffle(bagOfEdges);
        
        UF unionFind = new UF(rows * cols);
        while(unionFind.count() > 1) {
            Edge e = bagOfEdges.remove(0);
            int end1 = twoDToOneD(e.row1, e.col1);
            int end2 = twoDToOneD(e.row2, e.col2);
            if(unionFind.find(end1) != unionFind.find(end2)) {
                unionFind.union(end1, end2);
            } else {
                addEdge(e, this.maze);
            }
        }

        for(Edge e : bagOfEdges) {
            addEdge(e, maze);
        }

    }


    // returns {row, col}
    private int[] oneDToTwoD(int n) {
        return new int[] {(int)(n / this.cols), (n % this.cols)};
    }

    private int twoDToOneD(int row, int col) {
        return (row * this.cols) + col;
    }

    private void addEdge(Edge e, byte[][] maze ) {
        if(e.row1 < e.row2) {
            maze[e.row1][e.col1] = (byte)(maze[e.row1][e.col1] | 4);
            maze[e.row2][e.col2] = (byte)(maze[e.row2][e.col2] | 1);
        } else {
            maze[e.row1][e.col1] = (byte)(maze[e.row1][e.col1] | 2);
            maze[e.row2][e.col2] = (byte)(maze[e.row2][e.col2] | 8);
        }
    }


    private class Edge {

        public int row1, col1, row2, col2;
        public Edge(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof Edge)) return false;

            Edge otherEdge = (Edge)other;
            if(this.row1 == otherEdge.row1 &&
                this.col1 == otherEdge.col1 &&
                this.row2 == otherEdge.row2 && 
                this.col2 == otherEdge.col2) {
                return true;
            } else {
                return false;
            }

        }

        public String toString() {
            return "[(" + this.row1 + "," + this.col1 + ")(" + this.row2 + "," + this.col2 + ")]"; 
        }
    }

    
}
