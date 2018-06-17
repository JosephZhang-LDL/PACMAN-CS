package projects.Pacman;

import info.gridworld.actor.*;
import info.gridworld.grid.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by jonah on 5/31/18.
 */
public class PacManBoard extends ActorWorld{
    public static final int BOARDHEIGHT= 21;
    public static final int BOARDWIDTH = 19;

    private boolean gameOver = false;
    private int foodOnBoard;

    public PacManBoard(){
        super(new BoundedGrid<Actor>(BOARDHEIGHT, BOARDWIDTH));
        foodOnBoard = 0;
    }

    /**
     * Looks at step sequence for the entire board, to check for some game conditions like lose or win
     */
    @Override
    public void step(){
        if (!gameOver) {
            super.step();

            boolean isFood = false;
            boolean isPac = false;

            for (int i = 0; i < BOARDHEIGHT; i++) {
                for (int j = 0; j < BOARDWIDTH; j++) {
                    if (isSmallFood(new Location(i, j)) || isBigFood((new Location(i, j)))) {
                        isFood = true;
                    }
                }
            }

            if (!isFood) {
                System.out.println("Pacman Wins!");
                gameOver = true;
                JOptionPane.showMessageDialog(null,
                        "Pacman's a Winner",
                        "Pacman's a Winner",
                        JOptionPane.WARNING_MESSAGE);
            }

            for (int i = 0; i < BOARDHEIGHT; i++) {
                for (int j = 0; j < BOARDWIDTH; j++) {
                    Actor isItPacMan = (Actor)getGrid().get(new Location(i, j));
                    if (isItPacMan instanceof Player) {
                        isPac = true;
                    }
                }
            }

            if (!isPac) {
                loseStatement();
            }


            if (!isEnoughGhosts()) {
                findAndReplaceGhosts();
                loseStatement();
            }
        }
    }

    /**
     * Checks to see if a food is a small food
     * @param nextOne Checks the next location
     * @return whether or not it is small food
     */
    public boolean isSmallFood(Location nextOne) {
        Grid gr = this.getGrid();                              //Creates a grid
        if(gr == null) {
            return false;
        } else {
            if(!gr.isValid(nextOne)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(nextOne);
                return neighbor instanceof SmallFood;         //If neighbor is a small food, there you go
            }
        }
    }

    /**
     * Checks to see if a food is a big food
     * @param nextOne Checks the next location
     * @return whether or not it is big food
     */
    public boolean isBigFood(Location nextOne) {                     //Refer to comments for small food because this is
        Grid gr = this.getGrid();                                    //literally the same method lol
        if(gr == null) {
            return false;
        } else {
            if(!gr.isValid(nextOne)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(nextOne);
                return neighbor instanceof BigFood;
            }
        }
    }

    /**
     * Checks for ghosts
     * @return if there are four ghosts on the map
     */
    public boolean isEnoughGhosts() {                                             //Checks to see if there are enough
        int countGhosts = 0;                                                      //ghosts on the map

        for (int i = 0; i < BOARDHEIGHT; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                Actor isItGhost = (Actor)getGrid().get(new Location(i, j));
                if (isItGhost instanceof Enemy) {
                    countGhosts++;
                }
            }
        }

        return countGhosts == 4;
    }

    /**
     * Finds the location of the PacMan on the map
     * @return
     */
    public Location findPacMan() {
        Location loc = new Location(0, 0);                      //Temp location

        ArrayList<Object> actors = getActorList();                    //Gets the list of actors
        for (Object a : actors) {
            if (a instanceof Player) {                                //if its an instance of a player then it locks down the position
                loc = ((Player) a).getLocation();
                break;
            }
        }
        return loc;
    }

    /**
     * Prints the lose statement
     */
    public void loseStatement() {
        System.out.println("Pacman LOSES! ghost style");
        gameOver = true;
        JOptionPane.showMessageDialog(null,
                "Pacman's a Loser smh",
                "Pacman's a Loser smh",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Finds and replaces ghost on the map that pacman COULD'VE eaten
     */
    public void findAndReplaceGhosts() {
        Location hereisPacman = findPacMan();
        boolean isBlinky = false;
        boolean isInky = false;
        boolean isPinky = false;
        boolean isClyde = false;


        for (int i = 0; i < BOARDHEIGHT; i++) {                                    //Looks to see which ghost is missing
            for (int j = 0; j < BOARDWIDTH; j++) {
                Actor isItGhost = (Actor)getGrid().get(new Location(i, j));
                if (isItGhost instanceof Blinky)
                    isBlinky = true;
                else if (isItGhost instanceof Inky)
                    isInky = true;
                else if (isItGhost instanceof Pinky)
                    isPinky = true;
                else if (isItGhost instanceof Clyde)
                    isClyde = true;
            }
        }


        //If any of the ghosts are missing it replaces them on the map
        if (!isBlinky)
            getGrid().put(hereisPacman, new Blinky());
        if (!isInky)
            getGrid().put(hereisPacman, new Inky());
        if (!isPinky)
            getGrid().put(hereisPacman, new Pinky());
        if (!isClyde)
            getGrid().put(hereisPacman, new Clyde());

    }

    /**
     * Gets a list of actors on the map
     * @return returns an ArrayList of actors on the map
     */
    public ArrayList<Object> getActorList() {
        Location loc;
        Grid gr = getGrid();
        ArrayList<Object> actors = new ArrayList<Object>();
        int x = gr.getNumCols() - 1;
        int y = gr.getNumRows() - 1;

        for (int count = 1; count < x; count++) {
            for (int count_1 = 1; count_1 < y; count_1++) {
                loc = new Location(count, count_1);
                if (gr.isValid(loc))
                    if (gr.get(loc) != null) {
                        actors.add(gr.get(loc));
                    }
            }
        }
        return actors;
    }

    /**
     * Please don't expand this, because it builds the board
     */
    public void buildBoard(){
        for(int i = 0; i < PacManBoard.BOARDWIDTH; i++){
            this.add(new Location(0, i), new Wall());
            this.add(new Location(PacManBoard.BOARDHEIGHT - 1, i), new Wall());
        }

        for(int i = 0; i < PacManBoard.BOARDHEIGHT; i++){
            this.add(new Location(i, 0), new Wall());
            this.add(new Location(i, PacManBoard.BOARDWIDTH - 1), new Wall());
        }

        this.getGrid().get(new Location(9,0)).removeSelfFromGrid();
        this.getGrid().get(new Location(9,18)).removeSelfFromGrid();


        this.add(new Location(2, 2), new Wall());
        this.add(new Location(2, 3), new Wall());

        this.add(new Location(4, 2), new Wall());
        this.add(new Location(4, 3), new Wall());

        this.add(new Location(2, 5), new Wall());
        this.add(new Location(2, 6), new Wall());
        this.add(new Location(2, 7), new Wall());

        this.add(new Location(2, 9), new Wall());
        this.add(new Location(1, 9), new Wall());

        this.add(new Location(4, 7), new Wall());
        this.add(new Location(4, 8), new Wall());
        this.add(new Location(4, 9), new Wall());
        this.add(new Location(4, 10), new Wall());
        this.add(new Location(4, 11), new Wall());
        this.add(new Location(5, 9), new Wall());
        this.add(new Location(6, 9), new Wall());

        this.add(new Location(4, 5), new Wall());
        this.add(new Location(5, 5), new Wall());
        this.add(new Location(6, 5), new Wall());
        this.add(new Location(7, 5), new Wall());
        this.add(new Location(4, 5), new Wall());
        this.add(new Location(6, 6), new Wall());
        this.add(new Location(6, 7), new Wall());
        this.add(new Location(8, 5), new Wall());

        this.add(new Location(4, 13), new Wall());
        this.add(new Location(5, 13), new Wall());
        this.add(new Location(6, 13), new Wall());
        this.add(new Location(7, 13), new Wall());
        this.add(new Location(8, 13), new Wall());
        this.add(new Location(6, 12), new Wall());
        this.add(new Location(6, 11), new Wall());

        this.add(new Location(2, 11), new Wall());
        this.add(new Location(2, 12), new Wall());
        this.add(new Location(2, 13), new Wall());

        this.add(new Location(2, 15), new Wall());
        this.add(new Location(2, 16), new Wall());

        this.add(new Location(4, 15), new Wall());
        this.add(new Location(4, 16), new Wall());

        this.add(new Location(6, 1), new Wall());
        this.add(new Location(6, 2), new Wall());
        this.add(new Location(6, 3), new Wall());
        this.add(new Location(7, 1), new Wall());
        this.add(new Location(7, 2), new Wall());
        this.add(new Location(7, 3), new Wall());
        this.add(new Location(8, 1), new Wall());
        this.add(new Location(8, 2), new Wall());
        this.add(new Location(8, 3), new Wall());

        this.add(new Location(10, 1), new Wall());
        this.add(new Location(10, 2), new Wall());
        this.add(new Location(10, 3), new Wall());
        this.add(new Location(11, 1), new Wall());
        this.add(new Location(11, 2), new Wall());
        this.add(new Location(11, 3), new Wall());
        this.add(new Location(12, 1), new Wall());
        this.add(new Location(12, 2), new Wall());
        this.add(new Location(12, 3), new Wall());

        this.add(new Location(6, 15), new Wall());
        this.add(new Location(6, 16), new Wall());
        this.add(new Location(6, 17), new Wall());
        this.add(new Location(7, 15), new Wall());
        this.add(new Location(7, 16), new Wall());
        this.add(new Location(7, 17), new Wall());
        this.add(new Location(8, 15), new Wall());
        this.add(new Location(8, 16), new Wall());
        this.add(new Location(8, 17), new Wall());

        this.add(new Location(10, 15), new Wall());
        this.add(new Location(10, 16), new Wall());
        this.add(new Location(10, 17), new Wall());
        this.add(new Location(11, 15), new Wall());
        this.add(new Location(11, 16), new Wall());
        this.add(new Location(11, 17), new Wall());
        this.add(new Location(12, 15), new Wall());
        this.add(new Location(12, 16), new Wall());
        this.add(new Location(12, 17), new Wall());

        this.add(new Location(10, 5), new Wall());
        this.add(new Location(11, 5), new Wall());
        this.add(new Location(12, 5), new Wall());

        this.add(new Location(10, 13), new Wall());
        this.add(new Location(11, 13), new Wall());
        this.add(new Location(12, 13), new Wall());

        this.add(new Location(12, 7), new Wall());
        this.add(new Location(12, 8), new Wall());
        this.add(new Location(12, 9), new Wall());
        this.add(new Location(12, 10), new Wall());
        this.add(new Location(12, 11), new Wall());
        this.add(new Location(13, 9), new Wall());
        this.add(new Location(14, 9), new Wall());

        this.add(new Location(14, 5), new Wall());
        this.add(new Location(14, 6), new Wall());
        this.add(new Location(14, 7), new Wall());

        this.add(new Location(14, 11), new Wall());
        this.add(new Location(14, 12), new Wall());
        this.add(new Location(14, 13), new Wall());

        this.add(new Location(16, 7), new Wall());
        this.add(new Location(16, 8), new Wall());
        this.add(new Location(16, 9), new Wall());
        this.add(new Location(16, 10), new Wall());
        this.add(new Location(16, 11), new Wall());
        this.add(new Location(17, 9), new Wall());
        this.add(new Location(18, 9), new Wall());

        this.add(new Location(16, 1), new Wall());

        this.add(new Location(16, 17), new Wall());

        this.add(new Location(14, 2), new Wall());
        this.add(new Location(14, 3), new Wall());
        this.add(new Location(15, 3), new Wall());
        this.add(new Location(16, 3), new Wall());

        this.add(new Location(14, 16), new Wall());
        this.add(new Location(14, 15), new Wall());
        this.add(new Location(15, 15), new Wall());
        this.add(new Location(16, 15), new Wall());

        this.add(new Location(16, 5), new Wall());
        this.add(new Location(17, 5), new Wall());
        this.add(new Location(18, 5), new Wall());
        this.add(new Location(18, 6), new Wall());
        this.add(new Location(18, 7), new Wall());
        this.add(new Location(18, 4), new Wall());
        this.add(new Location(18, 3), new Wall());
        this.add(new Location(18, 2), new Wall());

        this.add(new Location(16, 13), new Wall());
        this.add(new Location(17, 13), new Wall());
        this.add(new Location(18, 13), new Wall());
        this.add(new Location(18, 12), new Wall());
        this.add(new Location(18, 11), new Wall());
        this.add(new Location(18, 14), new Wall());
        this.add(new Location(18, 15), new Wall());
        this.add(new Location(18, 16), new Wall());

        this.add(new Location(8, 7), new Wall());
        this.add(new Location(8, 8), new Wall());
        this.add(new Location(8, 9), new Wall());
        this.add(new Location(8, 10), new Wall());
        this.add(new Location(8, 11), new Wall());
        this.add(new Location(9, 11), new Wall());
        this.add(new Location(9, 7), new Wall());
        this.add(new Location(10, 7), new Wall());
        this.add(new Location(10, 8), new Wall());
        this.add(new Location(10, 9), new Wall());
        this.add(new Location(10, 10), new Wall());
        this.add(new Location(10, 11), new Wall());

        for(int i = 0; i < BOARDHEIGHT; i++){
            for(int j = 0; j < BOARDWIDTH; j++){
                if (this.getGrid().get(new Location(i, j)) == null) {
                    this.add(new Location(i,j), new SmallFood());
                    foodOnBoard++;
                }
            }
        }

        getGrid().get(new Location(9,8)).removeSelfFromGrid();
        getGrid().get(new Location(9,9)).removeSelfFromGrid();
        getGrid().get(new Location(9,10)).removeSelfFromGrid();
        foodOnBoard -= 3;

        this.show();
    }


}