package scripts;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Npc;

import java.util.concurrent.Callable;

@Script.Manifest(name = "Fisher // LiL PROMETHAZiNE", description = "Fish", properties = "author=COVENS; topic=999; client=4;")
public class Fisher extends PollingScript<ClientContext> {

    final static int MAX_SPACE = 28;
    //constant value which represents maximum inventory space

    final static int FISHING_SPOT = 1525; //ID for the fishing spot (Draynor)
    final static int FISHING_OBJECT = 303; //Will update to an array and add feathers, fishing rods, cages, harpoons, etc.
    final static int FISH_ID[] = {317, 318, 319, 320}; // Will add more fish ID's for the FISHING_OBJECT

    int animation = ctx.players.local().animation(); //integer value which holds the current animation of the character ( -1 is idle)


    private boolean fishingSpotClicked;

    final Npc fishingSpot = ctx.npcs.select().id(FISHING_SPOT).select(new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            //interacts with the NPC
            return npc.interact("Small Net");

        }
    }).nearest().poll();

    @Override
    public void start(){
        System.out.println("Fishing Script Activated.");
        //simple message printing to the console that the script has started

        int currentInventorySpace = 28;
        /*
         *Assuming that the script starts with an empty inventory
         * This should update to the actual inventory space
         * */

        if(currentInventorySpace != ctx.inventory.count()){
            /*
            * Updates current inventory space integer if necessary*/
            currentInventorySpace = ctx.inventory.count();
        }


    }

    @Override
    public void stop(){
        System.out.println("Fishing Script Deactivated.");
        //simple message printing to the console that the script has stopped

    }


    @Override
    public void poll() {

        final Item fishes = ctx.inventory.select().id(FISH_ID).select(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return ctx.inventory.selectedItem().interact("Cancel");
            }
        }).poll();

        if (animation == -1) {
            smallNetFish();
            if(ctx.inventory.count() == MAX_SPACE){
                fishes.interact("Drop");
            }
        }
    }

    public void smallNetFish(){

        fishingSpot.interact("Small Net");
        //Fishes at the spot

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().inMotion();
            }
        }, 300, 30);


    }



    public void getCurrentInventorySpace(){

    }


}
