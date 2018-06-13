/**
 * Created by 19ZalevA on 6/6/2018.
 */
package sample;
import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Critter;
import java.awt.*;
import java.util.ArrayList;

public class Blinky extends Enemy {
    Location Pacman = new Location(0, 0);
    int totalMoves = 0;
    public Blinky() {

        this.setDirection(360);
        this.setColor(Color.RED);
    }

    public void act() {
        moveTo(selectMoveLocation(getMoveLocations()));
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


}