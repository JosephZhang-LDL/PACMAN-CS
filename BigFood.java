package projects.Pacman;
/**
 * Created by jonah on 6/6/18.
 */
public class BigFood extends Food {
    public BigFood(){
        super(500);
    }

    public int giveFood(){
        //player.doSuper();
        removeSelfFromGrid();
        return super.getPointValue();
    }
}
