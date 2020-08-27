package plugin.nextlife.dice.commands;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import plugin.nextlife.dice.Main;

import java.util.Arrays;

public class Dice /* extends DICE_EVENT */ implements CommandExecutor, Listener {
    protected static Inventory inv;
    protected int RandomNumber = 0;

    public void init() {
        inv.setItem(4, createItem(Material.STONE, "Dice roll", "주사위 숫자는 " + this.RandomNumber));
    }

    private static ItemStack createItem(Material material, String ItemName, String lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ItemName);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack createItem(ItemStack itemStack, String ItemName, String lore) {
        final ItemStack item = new ItemStack(itemStack);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ItemName);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            inv = Bukkit.createInventory(null, 9, "DICE GUI");
            init();
            player.openInventory(Dice.inv);
        } else {
            System.out.println("You are not a player.");
        }
        return true;
    }

    public ItemStack WoolColors(int number) {
        // DyeColor Colors[] = new DyeColor[16];
        DyeColor[] Colors = DyeColor.values();
        Wool wool = new Wool(Colors[number]);
        return wool.toItemStack(1);
    }

    // get Main instance
    public Main main;
    public Dice(Main main) {
        this.main = main;
    }

    // EVENT
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (event.getInventory().getTitle().equals("DICE GUI")) {
            if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                Player player = (Player) event.getWhoClicked();
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Dice roll")){
                    player.closeInventory();
                    this.RandomNumber = (int) (Math.random() * 9 + 1);
                    int i;
                    player.openInventory(Dice.inv);
                    for(i = 0; i <= 6; i++) {
                        int finalI = i;
                        Bukkit.getScheduler().runTaskLater(this.main, () -> {
                            if (finalI == 6) {
                                init();
                            } else {
                                int Count = (int) (Math.random() * 9) + 1;
                                inv.setItem(4, createItem(WoolColors(Count - 1), "Dice rolling", "Dice's number is " + Count));
                            }
                        }, 10L * i);
                        // Count = (int)(Math.random() * 4);
                        // inv.setItem(3, createItem(WoolColors(Count), "Dice rolling", "주사위 숫자는 " + Count));
                    }
                } else{
                    System.out.println("Dice roll이 아닙니다.");
                }
            }
        } else {
            System.out.println("DICE GUI가 아닙니다");
        }
    }
}

/*
class DICE_EVENT implements Listener {
    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (Dice.inv.getTitle().equals("DICE GUI")) {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem().getType() == Material.STONE) {
                player.closeInventory();
                setRandomNumber((int) (Math.random() * 9 + 1));
                Dice.init();
                player.openInventory(Dice.inv);
            }
        }
    }
}
*/
