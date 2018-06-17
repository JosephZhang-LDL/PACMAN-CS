package projects.Pacman;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
        import info.gridworld.grid.Location;

        import javax.swing.*;
        import java.awt.*;

/**
 * Created by 20ZhangJ on 6/1/2018.
 */
public class Player extends Actor {
    int lives;
    int scoreValue;
    boolean isSuper = false;
    public static final int SUPERLENGTH = 40;
    private int superSteps;
    private final int LEFT = 1;
    private final int RIGHT= -1;
    private final int UP = 2;
    private final int DOWN  = -2;
    private int checkirection;
    private boolean teleported;
    private boolean isCherries;
    private JLabel p;
    JFrame scoreBoard;

    /**
     * Sets everything up
     */
    public Player() {
        //Basic Stuff
        lives = 3;
        scoreValue = 0;
        isSuper = false;
        setColor(Color.YELLOW);
        checkirection = 0;
        teleported = false;
        isCherries = false;
        superSteps = 0;

        //Scoreboard stuff
        scoreBoard = new JFrame();
        scoreBoard.setSize(new Dimension(200, 100));
        scoreBoard.setVisible(true);
        p = new JLabel();
        scoreBoard.getContentPane().add(p);
        p.setBackground(Color.WHITE);
        p.setOpaque(true);
        p.setForeground(Color.BLUE);
        p.setFont(new Font("CALIBRI", Font.BOLD, 18));
        p.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        p.setText("Score: " + scoreValue);
    }

    /**
     * More specific constructor to implement when the game is more complete
     * @param newLives number of lives
     * @param newScore player's score
     */
    public Player(int newLives, int newScore) {
        lives = newLives;
        scoreValue = newScore;
        setColor(Color.YELLOW);
        teleported  = false;
    }

    /**
     * What Pacman does in a step, decides whether it's a normal step, super step, or that weird situation where he's
     * about to teleport
     */
    public void act() {
        Grid<Actor> gr = getGrid();
        //Act like this if Pacman is super
        if(isSuper)
        {
            doSuper();
            superSteps--;

            if(superSteps == 0){
                isSuper = false;
                isCherries = false;
                gr.remove(new Location(2, 1));
                gr.remove(new Location(1, 17));
                gr.remove(new Location(19, 1));
                gr.remove(new Location(19, 17));
                System.out.println("SUPER IS " + isSuper);
            }
        }

        else if (getLocation().equals(new Location(9,0)) && !teleported)               //Teleport from left side
        {
            if(isSmallFood(new Location(9, 18)))
            {
                scoreValue++;
                System.out.println("Score is: " + scoreValue);
            }
            else if (isBigFood(new Location(9, 18)))
            {
                scoreValue += 10;
                isSuper = true;
                superSteps = SUPERLENGTH;
            }

            moveTo(new Location(9, 18));
            teleported = true;
        }

        else if (getLocation().equals(new Location(9,18)) && !teleported)              //Teleport from right side
        {
            if(isSmallFood(new Location(9, 0)))
            {
                scoreValue++;
                System.out.println("Score is: " + scoreValue);
            }
            else if (isBigFood(new Location(9, 0)))
            {
                scoreValue += 10;
                isSuper = true;
                superSteps = SUPERLENGTH;
                System.out.println("SUPER IS " + isSuper);
            }

            moveTo(new Location(9,0));
            teleported = true;
        }
        //Normal movement otherwise
        else if (canMove())
        {
            move();
        }
        //Wait what
        else {

        }

        p.setText("Score: " + scoreValue);
    }

    public int trackFood() {
        return 1;
    }


    /**
     * Separate sort of act method that changes some things up if pacman is super
     */
    public void doSuper() {
        Grid<Actor> gr = getGrid();

        if (!isCherries) {
            gr.put(new Location(2, 1), new Cherry());
            gr.put(new Location(1, 17), new Cherry());
            gr.put(new Location(19, 1), new Cherry());
            gr.put(new Location(19, 17), new Cherry());
            isCherries = true;

        }

        if (getLocation().equals(new Location(9,0)) && !teleported)               //Teleport from left side
        {
            if(isSmallFood(new Location(9, 18)))
            {
                scoreValue+=2;
                System.out.println("Score is: " + scoreValue);
            }
            else if (isBigFood(new Location(9, 18)))
            {
                scoreValue += 20;
                isSuper = true;
            }

            moveTo(new Location(9, 18));
            teleported = true;
        }

        else if (getLocation().equals(new Location(9,18)) && !teleported)              //Teleport from right side
        {
            if(isSmallFood(new Location(9, 0)))
            {
                scoreValue+= 2;
                System.out.println("Score is: " + scoreValue);
            }
            else if (isBigFood(new Location(9, 0)))
            {
                scoreValue += 20;
                isSuper = true;
            }

            moveTo(new Location(9,0));
            teleported = true;
        }

        //Normal movement otherwise, but it's super this happens
        else if (canMove())
        {
            Location loc = getLocation();
            Location next = loc.getAdjacentLocation(getDirection());
            teleported  = false;


            if(isSmallFood(next))
            {
                scoreValue+=2;
                System.out.println("Score is: " + scoreValue);
            }
            else if (isBigFood(next))
            {
                scoreValue += 20;
                isSuper = true;
            }
            else if (isCherry(next)) {
                gr.remove(next);
                scoreValue += 100;
            }

            Actor inFront = gr.get(next);
            moveTo(next);
        }
        else {

        }
    }

    public boolean canMove() {
        Grid gr = this.getGrid();
        if(gr == null) {
            return false;
        } else {
            Location loc = this.getLocation();
            Location next = loc.getAdjacentLocation(this.getDirection());
            if(!gr.isValid(next)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(next);
                /*if (neighbor instanceof Food) {
                    return true;
                }*/
                return neighbor == null || !(neighbor instanceof Wall);
            }
        }
    }

    public boolean isSmallFood(Location nextOne) {
        Grid gr = this.getGrid();
        if(gr == null) {
            return false;
        } else {
            Location loc = this.getLocation();
            //Location next = loc.getAdjacentLocation(this.getDirection());
            if(!gr.isValid(nextOne)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(nextOne);
                /*if (neighbor instanceof Food) {
                    return true;
                }*/
                return neighbor instanceof SmallFood;
            }
        }
    }

    public boolean isBigFood(Location nextOne) {
        Grid gr = this.getGrid();
        if(gr == null) {
            return false;
        } else {
            Location loc = this.getLocation();
            //Location next = loc.getAdjacentLocation(this.getDirection());
            if(!gr.isValid(nextOne)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(nextOne);
                /*if (neighbor instanceof Food) {
                    return true;
                }*/
                return neighbor instanceof BigFood;
            }
        }
    }

    public boolean isCherry(Location nextOne) {
        Grid gr = this.getGrid();
        if(gr == null) {
            return false;
        } else {
            Location loc = this.getLocation();
            //Location next = loc.getAdjacentLocation(this.getDirection());
            if(!gr.isValid(nextOne)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(nextOne);
                /*if (neighbor instanceof Food) {
                    return true;
                }*/
                return neighbor instanceof Cherry;
            }
        }
    }
    public void move() {
        Grid<Actor> gr = getGrid();

        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        teleported  = false;

        if(isSmallFood(next))
        {
            scoreValue++;
            System.out.println("Score is: " + scoreValue);
        }
        else if (isBigFood(next))
        {
            isSuper = true;
            scoreValue+=10;
            superSteps = SUPERLENGTH;
            System.out.println("SUPER IS " + isSuper);
        }


        Actor inFront = gr.get(next);
        moveTo(next);
    }

    protected void paintComponent(Graphics g) {
        p.setText("Score:  " + scoreValue);
    }

}