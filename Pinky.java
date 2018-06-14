/**
 * Created by 19ZalevA on 6/6/2018.
 */
package sample;
import info.gridworld.grid.Location;
import java.awt.*;
import java.util.ArrayList;

public class Pinky extends Enemy {
    int totalMoves = 0;
    Location previous;
    boolean last = false;
    public Pinky() {
        previous = this.getLocation();
        this.setDirection(360);
        this.setColor(Color.RED);
    }

    public void act() {
        //if(!previous.equals(this.getLocation()))
        if(totalMoves < 20){}
        else if(totalMoves == 20){
            if(this.getGrid().get(new Location(7,9)) == null || getGrid().get(new Location(7,9)) instanceof SmallFood)
            {
                moveTo(new Location(7, 9));
            }
        }
        else {
            SmallFood food = new SmallFood();
            if (last == true)
                food.putSelfInGrid(getGrid(), previous);
            moveTo(selectMoveLocation(getMoveLocations()));
        }
        totalMoves++;
    }
    public int Getdirection() {
       int direction = 0;

        ArrayList<Object> actors = getActorList();
        for (Object a : actors) {
            if (a instanceof Player) {
                direction = ((Player) a).getDirection();
                break;
            }
        }
        return direction;


    }
    public Location selectMoveLocation(ArrayList<Location> locs) {

        if(totalMoves%4 != 0) {
            Location target = findPacMan();
            int direction = Getdirection();
            if(direction == 0){
                target = new Location(target.getCol(),target.getRow() + 4);
            }
            else target = new Location(target.getCol() + 4,target.getRow() + 4);
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
            return chosen;
        }

        // previous = this.getLocation();
        return this.getLocation();
    }


}