import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

public class Inky extends Enemy {
        int totalMoves = 0;
        Location previous;
        boolean last = false;

        public Inky() {
            previous = this.getLocation();
            this.setDirection(360);
            this.setColor(Color.CYAN);
        }

        public void act() {
            //if(!previous.equals(this.getLocation()))
            SmallFood food = new SmallFood();
            if (last == true)
                food.putSelfInGrid(getGrid(), previous);
            moveTo(selectMoveLocation(getMoveLocations()));
            totalMoves++;
        }

        public Location selectMoveLocation(ArrayList<Location> locs) {
            if (totalMoves % 4 != 0) {
                Location pacMan = findPacMan();
                Location blinky = new Location(0, 0);
                Location chosen = new Location(0, 0);
                for (Object a : getActorList()) {
                    if (a instanceof Blinky)
                        blinky = ((Blinky) a).getLocation();
                }

                int row = blinky.getRow();
                int col = blinky.getCol();

                //90 degrees is added so it works with the unit circle
                double dir = 90 + blinky.getDirectionToward(pacMan);
                int distance = 2 * (int) Math.sqrt(Math.pow(blinky.getCol() - pacMan.getCol(), 2) +
                        Math.pow(blinky.getRow() - pacMan.getRow(), 2));

                row += distance * Math.sin(dir);
                col += distance * Math.cos(dir);

                double min = 1000;

                for (Location location : locs) {
                    int temp = (int) Math.sqrt((location.getCol() - col) * (location.getCol() - col) + (location.getRow() - row) * (location.getRow() - row));
                    if (temp < min) {
                        chosen = location;
                        min = temp;
                    }
                }

                return chosen;
            }
            else return this.getLocation();
        }


}

