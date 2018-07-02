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

    final static int maxInventorySpace = 28;

    final static int FISHING_SPOT = 1525;
    final static int NET = 303;
    final static int FISH_ID[] = {317, 318, 319, 320};

    int animation = ctx.players.local().animation();

    private boolean fishingSpotClicked;

    final Npc fishingSpot = ctx.npcs.select().id(FISHING_SPOT).select(new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return npc.interact("Small Net");

        }
    }).nearest().poll();

    @Override
    public void start(){
        System.out.println("Fishing Script Activated.");
    }

    @Override
    public void stop(){
        System.out.println("Fishing Script Deactivated.");
    }


    @Override
    public void poll() {

        final Item fishes = ctx.inventory.select().id(FISH_ID).select(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return ctx.players.local().inMotion();
            }
        }).poll();

        if (animation == -1) {
            fish();
            if(ctx.inventory.count() == maxInventorySpace){
                fishes.interact("Drop");
            }
        }
    }

    public void fish(){

        fishingSpot.interact("Small Net");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().inMotion();
            }
        }, 300, 30);


    }


}
