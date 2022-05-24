package bitofcode.mazes;
import javax.swing.JFrame;

import bitofcode.mazes.division.DivisionMaze;
import bitofcode.mazes.kruskal.KruskalMaze;
import bitofcode.mazes.threeD.ThreeD;
import bitofcode.mazes.dfs.DFSMaze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;

public class Display extends JFrame {

    private final byte[][] maze;
    private final int rows, cols;
    private  int width, height;
    private  int displaywidth, displayheight;
    private final int colWidth, rowHeight;

    private int translateX, translateY;
    

    public Display(byte[][] maze, int width, int height, String title) {
        this.maze = maze;

        this.width = width;
        this.height = height;
        this.displaywidth = width;
        this.displayheight = height;

        this.rows = this.maze.length;
        this.cols = this.maze[0].length;

        this.colWidth = (this.width / this.cols);
        this.rowHeight = (this.height / this.rows);
        
        setTitle(title);
        setSize(this.width, this.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setResizable(false);
        setLayout(null);
        setVisible(true);
        Insets insets = getInsets();
        this.translateX = insets.left;
        this.translateY = insets.top;
        setSize(this.width + insets.left, this.height + insets.top );
        this.width = getWidth();
        this.height = getHeight();
        repaint();
        // resizeToInternalSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        // System.out.println(DISPLAY_X + ", " + DISPLAY_Y);
    }

    @Override
    public void paint(Graphics g) {

        BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        Insets insets = getInsets();

        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, width, height);

        g2.setColor(new Color(0, 0, 0));
        g2.translate(translateX, translateY );
        
        // g2.drawLine(0, 0, 0, this.height);
        // g2.drawLine(0, 0, this.width, 0);
        // g2.drawLine(this.displaywidth - insets.left, 0, this.displaywidth - insets.left, this.displayheight);
        // g2.drawLine(0, this.displayheight - insets.bottom, this.displaywidth, this.displayheight - insets.bottom);
        g2.setStroke(new BasicStroke(3));

        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {

                if ((this.maze[r][c] & 1) > 0) g2.drawLine(c * colWidth, r * rowHeight, c * colWidth + colWidth, r * rowHeight);
                if ((this.maze[r][c] & 2) > 0) g2.drawLine(c * colWidth + colWidth, r * rowHeight, c * colWidth + colWidth, r * rowHeight + rowHeight);
                if ((this.maze[r][c] & 4) > 0) g2.drawLine(c * colWidth, r * rowHeight + rowHeight, c * colWidth + colWidth, r * rowHeight + rowHeight);
                if ((this.maze[r][c] & 8) > 0) g2.drawLine(c * colWidth, r * rowHeight, c * colWidth, r * rowHeight + rowHeight);

            }
        }

        Graphics2D g2dComponent = (Graphics2D) g;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);

    }

    public static void main(String[] args) {

        int rows = 30;
        int cols = 30;
        // KruskalMaze maze1 = new KruskalMaze(cols, rows);
        // DivisionMaze maze2 = new DivisionMaze(cols, rows);
        // DFSMaze maze3 = new DFSMaze(cols, rows);

        ThreeD maze = new ThreeD(3);
        // for(int i = 0 ; i < 3; i++) {
        //     System.out.println(Arrays.toString(maze.maze[i]));
        // }
        new Display(maze.maze[0], 200, 200, "0");
        new Display(maze.maze[1], 200, 200, "1");
        new Display(maze.maze[2], 200, 200, "2");
        new Display(maze.maze[3], 200, 200, "3");
        new Display(maze.maze[4], 200, 200, "4");
        new Display(maze.maze[5], 200, 200, "5");
    }

}