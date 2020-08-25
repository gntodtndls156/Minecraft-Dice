package plugin.nextlife.dice.commands;

import org.bukkit.Bukkit;
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

import java.util.Arrays;

public class Dice /* extends DICE_EVENT */ implements CommandExecutor, Listener {
    protected static Inventory inv;
    protected int RandomNumber = 0;

    public void init() {
        inv.setItem(4, createItem(Material.STONE, "Dice roll", "주사위 숫자는 " + this.RandomNumber));
    }

    private static ItemStack  createItem(Material material, String ItemName, String lore){
        final ItemStack item = new ItemStack(material, 1);
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


    // EVENT
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem().getItemMeta() ==  null) {
            return;
        }
        if (event.getInventory().getTitle().equalsIgnoreCase("DICE GUI")/*Dice.inv.getTitle().equals("DICE GUI")*/) {
            final Player player = (Player) event.getWhoClicked();
                if ("Dice roll".equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                    player.closeInventory();
                    this.RandomNumber = (int) (Math.random() * 9 + 1);
//                    for(int i = 0; i < 13; i++) {
//                        Bukkit.getScheduler().runTaskLater(new Main(), () -> {
//                            int Count = (int) (Math.random() * 9) + 1;
//                            inv.setItem(4, createItem(Material.getMaterial("35:" + Count), "Dice rolling", "" + Count));
//                        }, 3L);
//                    }
                    player.openInventory(Dice.inv);
                    init();
                }
        } else {
            System.out.println("DICE GUI 가 아닙니다.");
        }
    }
}
