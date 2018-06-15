package projects.Pacman;

import com.sun.javafx.scene.traversal.Direction;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by 20ZhangJ on 6/15/2018.
 */
public class Clyde extends Enemy {
    private Location Pacman = new Location(0, 0);
    private int totalMoves = 0;
    private boolean isScattered = false;

    /**
     * Constructor
     */
    public Clyde() {
        this.setDirection(360);
        this.setColor(Color.ORANGE);
    }

    /**
     * Acts out by moving in the right place. CLyde moves into scatter mode normally, but when
     * Pacman distance <= 8, he will chase until pacman too far
     */
    public void act() {
        if (isCloseEnough())
        moveTo(selectMoveLocation(getMoveLocations()));
        else
            scatterMode(getMoveLocations());
        totalMoves++;
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

    public void processActors(ArrayList<Actor> actors) {
        for (Actor a : actors) {
            if (!(a instanceof Wall))
                a.removeSelfFromGrid();
        }
    }

    public Location findPacMan() {
        Location loc = new Location(0, 0);

        ArrayList<Object> actors = getActorList();
        for (Object a : actors) {
            if (a instanceof Player) {
                loc = ((Player) a).getLocation();
                break;
            }
        }
        return loc;


    }

    public Location selectMoveLocation(ArrayList<Location> locs) {
        if(totalMoves%4 != 0) {
            Location target = findPacMan();
            Location chosen = new Location(0, 0);
            double distance = 10000;
            double temp = 0;
            for (Location location : locs) {
                temp = Math.sqrt((location.getCol() - target.getCol()) * (location.getCol() - target.getCol()) + (location.getRow() - target.getRow()) * (location.getRow() - target.getRow()));
                if (temp < distance) {
                    chosen = location;
                    distance = temp;
                }
            }
            return chosen;
        }
        else return this.getLocation();
    }

    public void scatterMode(ArrayList<Location> locs) {
        if (!isScattered) {
            if (getLocation() == new Location(17, 1)) {
                isScattered = true;
            }
            else if (totalMoves % 4 != 0) {
                Location target = new Location(17, 1);
                Location chosen = new Location(0, 0);
                double distance = 10000;
                double temp = 0;
                for (Location location : locs) {
                    temp = Math.sqrt((location.getCol() - target.getCol()) * (location.getCol() - target.getCol()) + (location.getRow() - target.getRow()) * (location.getRow() - target.getRow()));
                    if (temp < distance) {
                        chosen = location;
                        distance = temp;
                    }
                }
                moveTo(chosen);
            }
            else moveTo(this.getLocation());
        }
        else {
            Location loc = getLocation();

            if(canMove())
                moveTo(loc.getAdjacentLocation(getDirection()));
            else {
                setDirection(Location.LEFT);
            }
        }
    }

    public boolean isCloseEnough() {
        Location current = getLocation();
        Location pacMan = findPacMan();

        double xCoor = Math.pow(current.getRow() - pacMan.getRow(), 2);
        double yCoor = Math.pow(current.getCol() - pacMan.getCol(), 2);


        if (Math.sqrt(xCoor + yCoor) <= 8)
            return true;
        else
            return false;
    }


}
