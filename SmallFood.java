/**
 * Created by jonah on 6/6/18.
 */
public class SmallFood extends Food {
    public SmallFood(){
        super(100);
    }

    /**
     * This wasn't used either
     * @return
     */
    public int giveFood(){
        removeSelfFromGrid();
        return super.getPointValue();
    }

}
