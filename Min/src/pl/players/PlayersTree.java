package pl.players;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_MULTIPLYPeer;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayersTree {

    private Graphics2D ig2;
    private FontMetrics fontMetrics;
    private BufferedImage bi;
    private List<String> players;

    private void drawTreeLevel(int level, List<String> players){
        //1. Print names
        //2. Draw lines above the names
    };

    private void generateTree(){
        int treeLevels = (int)Math.round(Math.ceil(Math.log(players.size())/Math.log(2))) + 1;
        System.out.println("Tree levels:" + treeLevels);
        int w2, h2 = 50 + treeLevels * 100;

        long currentTreeLevel = treeLevels;
        int stringColumnWidth = 100;

        do {
            currentTreeLevel--;
            System.out.println("Drawing the " + currentTreeLevel + " tree level ....");

            w2 = 10;
            for (String s : players) {
                int stringLength = fontMetrics.stringWidth(s);
                int stringHeigth = fontMetrics.getAscent();

                ig2.drawString(s, w2, h2);

                int x1 = w2 + stringLength/2;
                int y1 = h2 - stringHeigth/2 - 10;
                int y2 = h2 - 25;
                ig2.drawLine(x1,y1,x1,y2);

                System.out.println(s + " " + stringLength);
                w2 += stringLength + (stringColumnWidth - stringLength);
            }

            w2 = 10;
            List<String> losers = new ArrayList<String>();
            Random r = new Random();
            for(int i=0; i<players.size(); i=i+2){
                String player1 = players.get(i);
                int player1StringLength = fontMetrics.stringWidth(player1);

                String player2 = players.get(i + 1);
                int player2StringLength = fontMetrics.stringWidth(player2);

                int x1 = w2 + player1StringLength/2;
                w2 += player1StringLength + (stringColumnWidth - player1StringLength);
                int x2 = w2 + player2StringLength/2;
                int y = h2 - 25;

                ig2.drawLine(x1,y, x2, y);
                w2 += player2StringLength + (stringColumnWidth - player2StringLength);

                //Find winner
                int playerToRemove = r.nextInt(2);
                losers.add(playerToRemove==0?player1:player2);
            }

            h2 -= 100;
            stringColumnWidth *= 2;

            //Remove half elements
            for(String loser : losers){
                players.remove(loser);
            };
            System.out.println(players);

            if(players.size() == 1)
            break;
        } while(currentTreeLevel != 0);
    }

    private void run(){
        try {
            players = new ArrayList<String>();
            players.add("Robert"); players.add("Patrick");players.add("Anna");players.add("John");
            players.add("Gosia");players.add("Mati");players.add("Patric");players.add("Shivs");

            initializeGraphic(players);

            generateTree();

            ImageIO.write(bi, "PNG", new File("d:\\playersTree.png"));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void initializeGraphic(List<String> players){
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
}
