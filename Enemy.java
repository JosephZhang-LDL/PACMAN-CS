package projects.Pacman;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;


public class Enemy extends Critter {
    //private Location prevLoc = getLocation();
    
    public Enemy(){
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
    /*
    public boolean isCollision(int direction) {
        Grid gr = getGrid();
        if(gr == null) {
            return false;
        } else {
            Location loc = this.getLocation();
            Location next = loc.getAdjacentLocation(direction);
            if(!gr.isValid(next)) {
                return false;
            } else {
                Actor neighbor = (Actor)gr.get(next);
                return neighbor instanceof Enemy;
            }
        }
    }
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

    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> PossibleMoves = new ArrayList<Location>();
        for (int degree = 0; degree < 360; degree += 90) {
            if (this.getGrid().get(this.getLocation().getAdjacentLocation(degree)) instanceof Wall ||
                    this.getGrid().get(this.getLocation().getAdjacentLocation(degree)) instanceof Enemy) {

            }
            /*
            else if(getGrid().get(getLocation().getAdjacentLocation(degree)).equals(prevLoc)) {

            } */
            else { PossibleMoves.add(this.getLocation().getAdjacentLocation(degree)); }
        }

        //prevLoc = getLocation();
        return checkValidty(PossibleMoves);

    }
    public Location checkTeleport(Location chosen){

        if(chosen.equals(new Location(9,0))) {
            chosen = new Location(9,17);
        }
        else if(chosen.equals(new Location(9,18))) {
            chosen = new Location(9,1);
        }
        return chosen;
    }

    public ArrayList<Location> checkValidty(ArrayList<Location> locs) {
        for (Location location : locs) {
            if (!getGrid().isValid(location)) {
                locs.remove(location);
            }
        }
        return locs;
    }
}