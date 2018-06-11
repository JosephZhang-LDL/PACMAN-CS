package projects.Pacman;

/**
 * Created by jonah on 6/6/18.
 */
public class SmallFood extends Food {
    public SmallFood(){
        super(100);
    }

    public int giveFood(){
        removeSelfFromGrid();
        return super.getPointValue();
    }

}
