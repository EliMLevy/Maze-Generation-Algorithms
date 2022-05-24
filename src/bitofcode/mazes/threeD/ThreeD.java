package bitofcode.mazes.threeD;

import java.util.*;

public class ThreeD {

    public final byte[][][] maze;


    public static void main(String[] args) {
        ThreeD test = new ThreeD(30);

        // for(int row = 0; row < test.maze[0].length; row ++) {
        //     System.out.println("        " + Arrays.toString(test.maze[0][row]));
        // }
        // System.out.println();

        // for(int row = 0; row < test.maze[0].length; row ++) {
        //     System.out.println(Arrays.toString(test.maze[1][row]) + "  " + Arrays.toString(test.maze[2][row]) + "  " + Arrays.toString(test.maze[3][row]));
        // }
        // System.out.println();

        // for(int row = 0; row < test.maze[0].length; row ++) {
        //     System.out.println("        " + Arrays.toString(test.maze[4][row]));
        // }
        // System.out.println();

        // for(int row = 0; row < test.maze[0].length; row ++) {
        //     System.out.println("        " + Arrays.toString(test.maze[5][row]));
        // }
        // System.out.println();

        // System.out.println(test.faces.toString());

        

    }


    public int length;
    public Set<Edge> edges;
    public List<List<Edge>> faces;


    public ThreeD(int length) {

        this.length = length;

        this.edges = new HashSet<>();

        maze = new byte[6][length][length];
        for(int face = 0; face < 6; face++) {
            for(int i = 0 ; i < maze[face].length; i++) {
                for (int j = 0; j < maze[face][i].length; j++) {
                    maze[face][i][j] = 15;
                    Pos current = new Pos(i, j, face);
                    edges.add(new Edge(current, current.up()));
                    edges.add(new Edge(current, current.down()));
                    edges.add(new Edge(current, current.right()));
                    edges.add(new Edge(current, current.left()));
                }
            }
        }

        Pos current = new Pos(0,0,0);
        boolean[][][] visited = new boolean[6][length][length];
        ArrayDeque<Pos> stack = new ArrayDeque<>();
        stack.push(current);
        while(current != null) {
            visited[current.face][current.row][current.col] = true;
            List<Pos> neighbors = neighbors(current, visited);
            if(!neighbors.isEmpty()) {
                int n = (int)(Math.random() * neighbors.size());
                Pos next = neighbors.get(n);

                maze[current.face][current.row][current.col] = (byte)(maze[current.face][current.row][current.col] & ~current.getDir(next));
                maze[next.face][next.row][next.col] = (byte)(maze[next.face][next.row][next.col] & ~next.getDir(current));

                edges.remove(new Edge(current, next));
                stack.push(current);
                current = next;

            } else {
                current = stack.isEmpty() ? null : stack.pop();
            }
        }

        // Sort the edges based on face, spliting walls that straddle faces into two
        Iterator<Edge> iter = edges.iterator();
        faces = new ArrayList<>(6);
        faces.add(new LinkedList<>());
        faces.add(new LinkedList<>());
        faces.add(new LinkedList<>());
        faces.add(new LinkedList<>());
        faces.add(new LinkedList<>());
        faces.add(new LinkedList<>());

        while(iter.hasNext()) {
            Edge curr = iter.next();
            if(curr.a.face != curr.b.face) {
                if(curr.a.up().equals(curr.b)) {
                    faces.get(curr.a.face).add(new Edge(curr.a, new Pos(curr.a.row - 1, curr.a.col, curr.a.face)));
                } else if(curr.a.down().equals(curr.b)) {
                    faces.get(curr.a.face).add(new Edge(curr.a, new Pos(curr.a.row + 1, curr.a.col, curr.a.face)));
                } else if(curr.a.right().equals(curr.b)) {
                    faces.get(curr.a.face).add(new Edge(curr.a, new Pos(curr.a.row, curr.a.col + 1, curr.a.face)));
                } else if(curr.a.left().equals(curr.b)) {
                    faces.get(curr.a.face).add(new Edge(curr.a, new Pos(curr.a.row, curr.a.col - 1, curr.a.face)));
                }


                if(curr.b.up().equals(curr.a)) {
                    faces.get(curr.b.face).add(new Edge(curr.b, new Pos(curr.b.row - 1, curr.b.col, curr.b.face)));
                } else if(curr.b.down().equals(curr.a)) {
                    faces.get(curr.b.face).add(new Edge(curr.b, new Pos(curr.b.row + 1, curr.b.col, curr.b.face)));
                } else if(curr.b.right().equals(curr.a)) {
                    faces.get(curr.b.face).add(new Edge(curr.b, new Pos(curr.b.row, curr.b.col + 1, curr.b.face)));
                } else if(curr.b.left().equals(curr.a)) {
                    faces.get(curr.b.face).add(new Edge(curr.b, new Pos(curr.b.row, curr.b.col - 1, curr.b.face)));
                }

            } else {
                faces.get(curr.a.face).add(curr);
            }
        }

        // System.out.println(faces.toString());
        


    }

    private class Pos {

        public int row, col, face;


        public Pos(int row, int col, int face) {
            this.row = row;
            this.col = col;
            this.face = face;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Pos))
                return false;

            Pos o = (Pos)other;

            if (this.row == o.row &&
                    this.col == o.col &&
                    this.face == o.face) {
                return true;
            } else {
                return false;
            }

        }

        @Override
        public int hashCode() {
            return this.row * 31 + this.col * 41 + this.face * 53;
        }

        public String toString() {
            return "{ \"row\":" + this.row + ", \"col\":" + this.col + ", \"face\":" + this.face + "}";
        }

        public Pos up() {
            
            if(this.row == 0) {
                if(this.face == 0) return new Pos(length - 1, this.col, 5);
                if(this.face == 1) return new Pos(this.col, 0, 0);
                if(this.face == 2) return new Pos(length - 1, this.col, 0);
                if(this.face == 3) return new Pos((length - 1) - this.col, length - 1, 0);
                if(this.face == 4) return new Pos(length - 1, this.col, 2);
                if(this.face == 5) return new Pos(length - 1, this.col, 4);
            } 
            
            return new Pos(this.row - 1, this.col, this.face);


            
        }

        public Pos down() {
            if(this.row == length - 1) {
                if(this.face == 0) return new Pos(0, this.col, 2);
                else if(this.face == 1) return new Pos((length - 1) - this.col, 0, 4);
                else if(this.face == 2) return new Pos(0, this.col, 4);
                else if(this.face == 3) return new Pos(this.col, length - 1,4);
                else if(this.face == 4) return new Pos(0, this.col, 5);
                else if(this.face == 5) return new Pos(0, this.col, 0);
            }

            return new Pos(this.row + 1, this.col, this.face);
        }

        public Pos left() {
            if(this.col == 0) {
                if(this.face == 0) return new Pos(0, this.row, 1);
                else if(this.face == 1) return new Pos((length - 1) - this.row, 0, 5);
                else if(this.face == 2) return new Pos(this.row, length - 1, 1);
                else if(this.face == 3) return new Pos(this.row, length - 1, 2);
                else if(this.face == 4) return new Pos(length - 1, (length - 1) - this.row, 1);
                else if(this.face == 5) return new Pos((length - 1) - this.row, 0, 1);
            }


            return new Pos(this.row, this.col - 1, this.face);
        }

        public Pos right() {

            if(this.col == length - 1) {
                if(this.face == 0) return new Pos(0, (length - 1) - this.row, 3);
                else if(this.face == 1) return new Pos(this.row, 0, 2);
                else if(this.face == 2) return new Pos(this.row, 0, 3);
                else if(this.face == 3) return new Pos((length - 1) - this.row, length - 1, 5);
                else if(this.face == 4) return new Pos(length - 1, this.row, 3);
                else if(this.face == 5) return new Pos((length - 1) - this.row, length - 1, 3);
            }

            return new Pos(this.row, this.col + 1, this.face);
        }

        public byte getDir(Pos other) {
            if(this.up().equals(other)) return 1;
            if(this.down().equals(other)) return 4;
            if(this.right().equals(other)) return 2;
            if(this.left().equals(other)) return 8;

            return 0;
        }

    }

    private byte invert(byte b) {
        if(b == 1) return 4;
        if(b == 2) return 8;
        if(b == 4) return 1;
        if(b == 8) return 2;
        else return 0;
    }

    public List<Pos> neighbors(Pos a, boolean[][][] visited) {

        List<Pos> result = new LinkedList<>();

        Pos up = a.up();
        if(!visited[up.face][up.row][up.col]) result.add(up);

        Pos down = a.down();
        if(!visited[down.face][down.row][down.col]) result.add(down);

        Pos left = a.left();
        if(!visited[left.face][left.row][left.col]) result.add(left);

        Pos right = a.right();
        if(!visited[right.face][right.row][right.col]) result.add(right);

        return result;

    }

    private class Edge {

        public Pos a, b;

        public Edge(Pos a, Pos b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Edge))
                return false;

            Edge otherEdge = (Edge) other;
            if ((this.a.equals(otherEdge.a) && this.b.equals(otherEdge.b)) ||
                (this.b.equals(otherEdge.a) && this.a.equals(otherEdge.b))) {
                return true;
            } else {
                return false;
            }

        }

        @Override
        public int hashCode() {
            return this.a.hashCode() + this.b.hashCode();
        }

        public String toString() {
            if(this.a.row < this.b.row || this.a.col < this.b.col) {
                return "[" + this.a.toString() + "," + this.b.toString() + "]";
            } else {
                return "[" + this.b.toString() + "," + this.a.toString() + "]";
            }

        }
    }

}
