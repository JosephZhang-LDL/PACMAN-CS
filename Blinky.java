/**
 * Created by 19ZalevA on 6/6/2018.
 */
package sample;
import info.gridworld.grid.Location;
import java.awt.*;
import java.util.ArrayList;

public class Blinky extends Enemy {
    int totalMoves = 0;
    Location previous;
    boolean last = false;
    public Blinky() {
        previous = this.getLocation();
        this.setDirection(360);
        this.setColor(Color.RED);
    }

    public void act() {
        //if(!previous.equals(this.getLocation()))
            SmallFood food = new SmallFood();
            if (last == true)
                food.putSelfInGrid(getGrid(),previous);
        moveTo(selectMoveLocation(getMoveLocations()));
        totalMoves++;
    }

    public Location selectMoveLocation(ArrayList<Location> locs) {

        if(totalMoves%4 != 0) {
            Location target = findPacMan();
            Location chosen = new Location(0, 0);
            double distance = 10000;
            double temp;
            for (Location location : locs) {
                temp = Math.sqrt((location.getCol() - target.getCol()) * (location.getCol() - target.getCol()) + (location.getRow() - target.getRow()) * (location.getRow() - target.getRow()));
                if (temp < distance) {
                    chosen = location;
                    distance = temp;
                }
            }


            //Checks if it was a food;
           if(getGrid().get(chosen) instanceof Food) last = true;
            else last = false;

            previous = this.getLocation();
            chosen = checkTeleport(chosen);
            return chosen;
        }

        // previous = this.getLocation();
        return this.getLocation();
}


}