package plugin.nextlife.dice;

import org.bukkit.plugin.java.JavaPlugin;
import plugin.nextlife.dice.commands.Dice;

public class Main extends JavaPlugin {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        Commands();

        getServer().getPluginManager().registerEvents(new Dice(), this);
        System.out.println("command /Dice 시작합니다.");
    }
    private void Commands() {
        this.getCommand("Dice").setExecutor(new Dice());
    }

}
