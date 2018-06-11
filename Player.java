package projects.Pacman;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * Created by 20ZhangJ on 6/1/2018.
 */
public class Player extends Actor {
    int lives;
    int scoreValue;
    boolean isSuper = false;
    private final int LEFT = 1;
    private final int RIGHT= -1;
    private final int UP = 2;
    private final int DOWN  = -2;
    private int checkirection;
    private boolean teleported;


    public Player() {
        lives = 3;
        scoreValue = 0;
        isSuper = false;
        setColor(Color.YELLOW);
        checkirection = 0;
        teleported = false;
    }

    public Player(int newLives, int newScore) {
        lives = newLives;
        scoreValue = newScore;
        setColor(Color.YELLOW);
        teleported  = false;
    }

    public void act() {
        Grid<Actor> gr = getGrid();
        if(isSuper)
        {
            doSuper();
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
                System.out.println("SUPER IS " + isSuper);
            }

            moveTo(new Location(9,0));
            teleported = true;
        }

        else if (canMove())
        {
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
                System.out.println("SUPER IS " + isSuper);
            }


            Actor inFront = gr.get(next);
            moveTo(next);
        }
        else {

        }

    }

    public int trackFood() {
        return 1;
    }

    public void die() {

    }

    public void doSuper() {
        Grid<Actor> gr = getGrid();
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

}
