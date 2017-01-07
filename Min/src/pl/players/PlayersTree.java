package pl.players;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayersTree {

    private final int DISTANCE_BETWEEN_LEVELS = 30;
    private final int STANDARD_COLUMN_WIDTH = 60;

    private Graphics2D ig2;
    private FontMetrics fontMetrics;
    private BufferedImage bi;
    private List<Player> players;

    private void generateTree(){
        int treeLevels = (int)Math.round(Math.ceil(Math.log(players.size())/Math.log(2))) + 1;
        System.out.println("Tree levels:" + treeLevels);
        int w2, h2 = 50 + treeLevels * DISTANCE_BETWEEN_LEVELS;

        long currentTreeLevel = treeLevels;
        int stringColumnWidth = STANDARD_COLUMN_WIDTH;

        w2 = 10;
        int w2_temp = 0;
        do {
            currentTreeLevel--;
            System.out.println("Drawing the " + currentTreeLevel + " tree level ....");

            w2_temp = w2;
            for (Player p : players) {
                String s = p.getName();
                int stringLength = fontMetrics.stringWidth(s);
                int stringHeight = fontMetrics.getAscent();

                ig2.drawString(s, w2_temp, h2);

                if(players.size() != 1) {
                    int x1 = w2_temp + stringLength / 2;
                    int y1 = h2 - stringHeight / 2 - 10;
                    int y2 = h2 - 25;
                    ig2.drawLine(x1, y1, x1, y2);
                    p.setMiddle_x(x1);
                    p.setMiddle_y(y2);

                    w2_temp += stringLength + (stringColumnWidth - stringLength);
                }
            }

            if(players.size() != 1) {
                w2_temp = w2;
                List<Player> losers = new ArrayList<>();
                Random r = new Random();
                for (int i = 0; i < players.size(); i = i + 2) {
                    Player player1 = players.get(i);
                    String player1Name = player1.getName();
                    int player1StringLength = fontMetrics.stringWidth(player1Name);

                    Player player2 = players.get(i + 1);
                    String player2Name = player2.getName();
                    int player2StringLength = fontMetrics.stringWidth(player2Name);

                    int x1 = w2_temp + player1StringLength / 2;
                    w2_temp += player1StringLength + (stringColumnWidth - player1StringLength);
                    int x2 = w2_temp + player2StringLength / 2;
                    int y = h2 - 25;

                    ig2.drawLine(x1, y, x2, y);
                    w2_temp += player2StringLength + (stringColumnWidth - player2StringLength);

                    //Find winner
                    int playerToRemove = r.nextInt(2);
                    losers.add(playerToRemove == 0 ? player1 : player2);
                }

                h2 -= DISTANCE_BETWEEN_LEVELS;
                stringColumnWidth *= 2;
                w2 += (players.get(1).getMiddle_x() - players.get(0).getMiddle_x()) / 2
                        - fontMetrics.stringWidth(losers.get(0).getName().equals(players.get(0).getName()) ? players.get(1).getName() : players.get(0).getName()) / 2
                        + fontMetrics.stringWidth(players.get(0).getName()) / 2;

                //Remove half elements
                for (Player loser : losers) {
                    players.remove(loser);
                }

            }

        } while(currentTreeLevel != 0);
    }

    private void run(){
        try {
            players = new ArrayList<Player>();
            players.add(new Player("Robert")); players.add(new Player("Patrick"));players.add(new Player("Anna"));players.add(new Player("John"));
            players.add(new Player("Gosia"));players.add(new Player("Mati"));players.add(new Player("Patric"));players.add(new Player("Shivs"));
//            players.add(new Player("Robert")); players.add(new Player("Patrick"));players.add(new Player("Anna"));players.add(new Player("John"));
//            players.add(new Player("Gosia"));players.add(new Player("Mati"));players.add(new Player("Patric"));players.add(new Player("Shivs"));
//
//            players.add(new Player("Robert")); players.add(new Player("Patrick"));players.add(new Player("Anna"));players.add(new Player("John"));
//            players.add(new Player("Gosia"));players.add(new Player("Mati"));players.add(new Player("Patric"));players.add(new Player("Shivs"));
//            players.add(new Player("Robert")); players.add(new Player("Patrick"));players.add(new Player("Anna"));players.add(new Player("John"));
//            players.add(new Player("Gosia"));players.add(new Player("Mati"));players.add(new Player("Patric"));players.add(new Player("Shivs"));

            initializeGraphic(players);

            generateTree();

            ImageIO.write(bi, "PNG", new File("d:\\playersTree.png"));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void initializeGraphic(List<Player> players){
        int width = players.size() * 100 + players.size() * 25,
                height = 500;

        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        ig2 = bi.createGraphics();
        Font font = new Font("TimesRoman", Font.BOLD, 12);
        ig2.setFont(font);
        fontMetrics = ig2.getFontMetrics();
        ig2.setPaint(Color.black);
    }

    static public void main(String args[]) throws Exception {
        new PlayersTree().run();
    }

    private class Player {

        private String name;
        private int middle_x;
        private int middle_y;

        public Player(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getMiddle_x() {
            return middle_x;
        }

        public void setMiddle_x(int middle_x) {
            this.middle_x = middle_x;
        }

        public int getMiddle_y() {
            return middle_y;
        }

        public void setMiddle_y(int middle_y) {
            this.middle_y = middle_y;
        }
    }
}
