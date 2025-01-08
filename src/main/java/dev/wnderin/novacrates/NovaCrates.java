/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.BlockDisplay
 *  org.bukkit.entity.Display
 *  org.bukkit.entity.Display$Billboard
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Interaction
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.TextDisplay
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryDragEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.persistence.PersistentDataType
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.util.Transformation
 *  org.bukkit.util.Vector
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package dev.wnderin.novacrates;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class NovaCrates
extends JavaPlugin
implements Listener {
    static final int[] MID = new int[]{10, 11, 12, 13, 14, 15, 16};
    static final String[] CANDLES = new String[]{"PURPLE_CANDLE", "MAGENTA_CANDLE", "PINK_CANDLE", "RED_CANDLE", "ORANGE_CANDLE", "YELLOW_CANDLE", "LIME_CANDLE", "GREEN_CANDLE", "CYAN_CANDLE", "LIGHT_BLUE_CANDLE", "BLUE_CANDLE", "WHITE_CANDLE", "LIGHT_GRAY_CANDLE", "GRAY_CANDLE", "BLACK_CANDLE", "BROWN_CANDLE", "CANDLE"};
    final Map<String, Crate> crates = new LinkedHashMap<String, Crate>();
    final Map<UUID, Map<String, Integer>> vkeys = new HashMap<UUID, Map<String, Integer>>();
    final Map<UUID, Spin> spins = new HashMap<UUID, Spin>();
    private final Random rnd = new Random();
    private NamespacedKey keyTag;
    private File cratesFile;
    private File keysFile;
    private static final String MARK = "\u00a71\u00a72\u00a73\u00a7r";
    private final Map<UUID, Long> lastPreview = new HashMap<UUID, Long>();
    final Map<String, Parts> parts = new HashMap<String, Parts>();
    final Map<UUID, Parts> partsByHit = new HashMap<UUID, Parts>();
    private static final Pattern HEX = Pattern.compile("&#([0-9a-fA-F]{6})");

    public void onEnable() {
        this.saveDefaultConfig();
        this.keyTag = new NamespacedKey((Plugin)this, "novacrates_key");
        this.cratesFile = new File(this.getDataFolder(), "crates.yml");
        this.keysFile = new File(this.getDataFolder(), "keys.yml");
        this.load();
        this.getLifecycleManager().registerEventHandler(io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents.COMMANDS, event -> {
            final io.papermc.paper.command.brigadier.Commands commands = event.registrar();
            commands.register("crate", "Manage and configure crates", java.util.List.of("crates"), new io.papermc.paper.command.brigadier.BasicCommand() {
                @Override
                public void execute(io.papermc.paper.command.brigadier.CommandSourceStack stack, String[] args) {
                    NovaCrates.this.handleCrate(stack.getSender(), args);
                }
                @Override
                public java.util.Collection<String> suggest(io.papermc.paper.command.brigadier.CommandSourceStack stack, String[] args) {
                    return NovaCrates.this.tabCompleteCrate(stack.getSender(), args);
                }
                @Override
                public boolean canUse(org.bukkit.command.CommandSender sender) {
                    return sender.hasPermission("novacrates.admin");
                }
            });
            commands.register("keys", "View virtual crate keys", java.util.List.of(), new io.papermc.paper.command.brigadier.BasicCommand() {
                @Override
                public void execute(io.papermc.paper.command.brigadier.CommandSourceStack stack, String[] args) {
                    if (stack.getSender() instanceof org.bukkit.entity.Player player) {
                        NovaCrates.this.openKeys(player);
                    } else {
                        stack.getSender().sendMessage(NovaCrates.this.pre("&cOnly players can open the keys menu."));
                    }
                }
            });
        });
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
        Bukkit.getScheduler().runTaskTimer((Plugin)this, this::tickSpins, 1L, 1L);
        Bukkit.getScheduler().runTaskTimer((Plugin)this, this::tickVisuals, 40L, 20L);
        Bukkit.getScheduler().runTaskTimer((Plugin)this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.convertKeyItems(player);
            }
        }, 60L, 60L);
        Bukkit.getScheduler().runTaskTimer((Plugin)this, this::dailyKeyall, 200L, 400L);
    }

    private void dailyKeyall() {
        File file;
        YamlConfiguration yamlConfiguration;
        LocalTime localTime;
        ZoneId zoneId;
        ConfigurationSection configurationSection = this.getConfig().getConfigurationSection("daily-keyall");
        if (configurationSection == null || !configurationSection.getBoolean("enabled", false)) {
            return;
        }
        try {
            zoneId = ZoneId.of(configurationSection.getString("timezone", "Europe/London"));
        }
        catch (Throwable throwable) {
            zoneId = ZoneId.of("Europe/London");
        }
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
        try {
            localTime = LocalTime.parse(configurationSection.getString("time", "18:00"));
        }
        catch (Throwable throwable) {
            localTime = LocalTime.of(18, 0);
        }
        if (localDateTime.toLocalTime().isBefore(localTime)) {
            return;
        }
        String string = localDateTime.toLocalDate().toString();
        if (string.equals((yamlConfiguration = YamlConfiguration.loadConfiguration((File)(file = new File(this.getDataFolder(), "daily.yml")))).getString("last", ""))) {
            return;
        }
        if (localDateTime.toLocalTime().isAfter(localTime.plusHours(1L))) {
            yamlConfiguration.set("last", (Object)string);
            try {
                yamlConfiguration.save(file);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return;
        }
        yamlConfiguration.set("last", (Object)string);
        try {
            yamlConfiguration.save(file);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        Crate crate = this.crates.get(configurationSection.getString("crate", "daily").toLowerCase(Locale.ROOT));
        if (crate == null) {
            this.getLogger().warning("daily-keyall: there's no crate called " + configurationSection.getString("crate", "daily"));
            return;
        }
        int n = Math.max(1, configurationSection.getInt("amount", 1));
        for (Object object : Bukkit.getOnlinePlayers()) {
            this.giveKeys((Player)object, crate, n, true);
        }
        String string2 = configurationSection.getString("broadcast", "&7Everyone online got &f%amount%x %crate% &fKey&7! &8(/keys)");
        if (string2 != null && !string2.isEmpty()) {
            Object object;
            object = this.pre(string2.replace("%amount%", String.valueOf(n)).replace("%crate%", this.disp(crate)));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage((String)object);
            }
        }
    }

    void convertKeyItems(Player player) {
        if (!player.isOnline()) {
            return;
        }
        try {
            if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null && player.getOpenInventory().getTopInventory().getHolder() instanceof Menu) {
                return;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (this.spins.containsKey(player.getUniqueId())) {
            return;
        }
        ItemStack[] itemStackArray = player.getInventory().getContents();
        int n = 0;
        for (int i = 0; i < itemStackArray.length; ++i) {
            String string = this.keyOf(itemStackArray[i]);
            if (string == null) continue;
            if (this.crates.containsKey(string)) {
                this.addVkeys(player.getUniqueId(), string, itemStackArray[i].getAmount());
                n += itemStackArray[i].getAmount();
            }
            player.getInventory().setItem(i, null);
        }
        if (n > 0) {
            player.sendMessage(this.pre("&7Your &f" + n + " &7key item" + (n == 1 ? " was" : "s were") + " turned into virtual keys &8- &#B895EC/keys"));
        }
    }

    @EventHandler
    public void onJoinConvert(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        Bukkit.getScheduler().runTaskLater((Plugin)this, () -> this.convertKeyItems(player), 20L);
    }

    public void onDisable() {
        for (Spin object : new ArrayList<Spin>(this.spins.values())) {
            this.finish(object, false);
        }
        for (Parts parts : new ArrayList<Parts>(this.parts.values())) {
            this.removeParts(parts);
        }
        this.parts.clear();
        this.saveCrates();
        for (Crate crate : this.crates.values()) {
            for (Location location : crate.locs) {
                try {
                    this.restoreShulker(crate, location);
                }
                catch (Throwable throwable) {}
            }
        }
        this.saveKeys();
    }

    ItemStack keyItem(Crate crate, int n) {
        ItemStack itemStack = new ItemStack(NovaCrates.mat(this.keyMatFor(crate), Material.STONE), Math.max(1, Math.min(64, n)));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(NovaCrates.color(this.disp(crate) + " &fKey"));
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(NovaCrates.color("&7Right-click the " + this.disp(crate) + " &7crate"));
            arrayList.add(NovaCrates.color("&7at spawn to open it."));
            itemMeta.setLore(arrayList);
            try {
                itemMeta.setEnchantmentGlintOverride(Boolean.valueOf(true));
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            itemMeta.getPersistentDataContainer().set(this.keyTag, PersistentDataType.STRING, crate.id);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    String keyOf(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == null || itemStack.getType().name().equals("AIR")) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        try {
            return (String)itemMeta.getPersistentDataContainer().get(this.keyTag, PersistentDataType.STRING);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    int vkeys(UUID uUID, String string) {
        Map<String, Integer> map = this.vkeys.get(uUID);
        return map == null ? 0 : map.getOrDefault(string, 0);
    }

    void addVkeys(UUID uUID2, String string, int n) {
        Map<String, Integer> map = this.vkeys.computeIfAbsent(uUID2, uUID -> new HashMap<String, Integer>());
        int n2 = Math.max(0, map.getOrDefault(string, 0) + n);
        if (n2 == 0) {
            map.remove(string);
        } else {
            map.put(string, n2);
        }
        this.saveKeys();
    }

    void giveKeys(Player player, Crate crate, int n, boolean bl) {
        boolean bl2 = true;
        if (bl2) {
            this.addVkeys(player.getUniqueId(), crate.id, n);
        } else {
            int n2;
            for (int i = n; i > 0; i -= n2) {
                n2 = Math.min(64, i);
                NovaCrates.giveItem(player, this.keyItem(crate, n2));
            }
        }
        player.sendMessage(this.pre("&7You got &f" + n + "x " + this.disp(crate) + " &fKey" + (n == 1 ? "" : "s") + (bl2 ? " &8(virtual - &#B895EC/keys&8)" : "")));
        NovaCrates.sound(player, "entity.experience_orb.pickup", 0.8f, 1.4f);
    }

    static void giveItem(Player player, ItemStack itemStack) {
        for (ItemStack itemStack2 : player.getInventory().addItem(new ItemStack[]{itemStack}).values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack2);
        }
    }

    private ItemStack glass() {
        return NovaCrates.named(NovaCrates.mat("GRAY_STAINED_GLASS_PANE", Material.STONE), " ", new String[0]);
    }

    static ItemStack named(Material material, String string, String ... stringArray) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(NovaCrates.color(string));
            if (stringArray.length > 0) {
                ArrayList<String> arrayList = new ArrayList<String>();
                for (String string2 : stringArray) {
                    arrayList.add(NovaCrates.color(string2));
                }
                itemMeta.setLore(arrayList);
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private void border(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); ++i) {
            inventory.setItem(i, this.glass());
        }
    }

    void openList(Player player) {
        ListMenu listMenu = new ListMenu();
        int n = Math.max(3, Math.min(6, (this.crates.size() + 6) / 7 + 2));
        listMenu.inv = Bukkit.createInventory((InventoryHolder)listMenu, (int)(n * 9), (String)NovaCrates.color("&8Crates"));
        this.border(listMenu.inv);
        int n2 = 0;
        for (Crate crate : this.crates.values()) {
            int n3 = 1 + n2 / 7;
            int n4 = 1 + n2 % 7;
            int n5 = n3 * 9 + n4;
            if (n5 >= listMenu.inv.getSize() - 9) break;
            listMenu.inv.setItem(n5, NovaCrates.named(NovaCrates.mat("CHEST", Material.STONE), this.disp(crate), "&7Prizes: &f" + NovaCrates.count(crate) + "&7/7", "&7Crate blocks: &f" + crate.locs.size(), "", "&#B895ECLeft-click &7to edit prizes", "&#B895ECRight-click &7to get a key"));
            listMenu.ids.add(crate.id);
            ++n2;
        }
        if (this.crates.isEmpty()) {
            listMenu.inv.setItem(13, NovaCrates.named(NovaCrates.mat("BARRIER", Material.STONE), "&cNo crates yet", "&7Make one with &f/crate create <name>"));
        }
        player.openInventory(listMenu.inv);
        NovaCrates.sound(player, "block.chest.open", 0.6f, 1.2f);
    }

    private static int count(Crate crate) {
        int n = 0;
        for (ItemStack itemStack : crate.items) {
            if (itemStack == null) continue;
            ++n;
        }
        return n;
    }

    void openEditor(Player player, Crate crate) {
        EditMenu editMenu = new EditMenu();
        editMenu.crate = crate;
        editMenu.inv = Bukkit.createInventory((InventoryHolder)editMenu, (int)27, (String)(NovaCrates.color("&8Edit: ") + NovaCrates.color(this.disp(crate))));
        this.border(editMenu.inv);
        this.drawEditorItems(editMenu);
        editMenu.inv.setItem(4, NovaCrates.named(NovaCrates.mat("BOOK", Material.STONE), "&#B895EC&lHow to edit", "&7Put prizes in the middle row (7 slots).", "&#B895ECRight-click &7a prize to set its chance.", "&7Saves when you close it."));
        editMenu.inv.setItem(22, this.keyButton(crate));
        editMenu.inv.setItem(26, NovaCrates.named(NovaCrates.mat("TRIPWIRE_HOOK", Material.STONE), "&#B895EC&lGet a key", "&7Gives you 1 key for this crate.", "&7Put keys from other crates in here", "&7to make a key crate."));
        editMenu.inv.setItem(18, NovaCrates.named(NovaCrates.mat("ARROW", Material.STONE), "&7Back", new String[0]));
        player.openInventory(editMenu.inv);
    }

    private ItemStack withChance(Crate crate, int n) {
        ItemStack itemStack = crate.items[n].clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return itemStack;
        }
        ArrayList<String> arrayList = itemMeta.hasLore() && itemMeta.getLore() != null ? new ArrayList<String>(itemMeta.getLore()) : new ArrayList<String>();
        arrayList.add(MARK);
        arrayList.add(MARK + NovaCrates.color("&7Chance: " + this.accent("&#B895EC", crate, crate.locs.isEmpty() ? null : crate.locs.get(0)) + NovaCrates.fmt(crate.realPercent(n)) + "%"));
        arrayList.add(MARK + NovaCrates.color("&8Right-click to change"));
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    static ItemStack strip(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !itemMeta.hasLore() || itemMeta.getLore() == null) {
            return itemStack;
        }
        List<String> list = itemMeta.getLore();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < list.size(); ++i) {
            String string = list.get(i);
            if (string == null || string.startsWith(MARK) || NovaCrates.isEditorLine(string) || NovaCrates.plain(string).trim().isEmpty() && i + 1 < list.size() && NovaCrates.isEditorLine((String)list.get(i + 1))) continue;
            arrayList.add(string);
        }
        if (arrayList.size() == list.size()) {
            return itemStack;
        }
        ItemStack itemStack2 = itemStack.clone();
        ItemMeta meta2 = itemStack2.getItemMeta();
        if (meta2 != null) {
            meta2.setLore(arrayList.isEmpty() ? null : arrayList);
            itemStack2.setItemMeta(meta2);
        }
        return itemStack2;
    }

    private static boolean isEditorLine(String string) {
        if (string == null) {
            return false;
        }
        String string2 = NovaCrates.plain(string).trim();
        return string2.equals("Right-click to change") || string2.matches("Chance: [0-9.,]+%");
    }

    private static String plain(String string) {
        return string == null ? "" : string.replaceAll("(?i)\u00a7[0-9a-fk-orx]", "");
    }

    private void drawEditorItems(EditMenu editMenu) {
        for (int i = 0; i < 7; ++i) {
            editMenu.inv.setItem(MID[i], editMenu.crate.items[i] == null ? null : this.withChance(editMenu.crate, i));
        }
    }

    private void refreshEditorSoon(Player player, EditMenu editMenu) {
        Bukkit.getScheduler().runTask((Plugin)this, () -> {
            this.cleanPlayer(player);
            if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() != editMenu.inv) {
                return;
            }
            this.syncEditor(editMenu);
            this.drawEditorItems(editMenu);
        });
    }

    private void cleanPlayer(Player player) {
        ItemStack itemStack;
        ItemStack[] itemStackArray = player.getInventory().getContents();
        for (int i = 0; i < itemStackArray.length; ++i) {
            itemStack = NovaCrates.strip(itemStackArray[i]);
            if (itemStack == itemStackArray[i]) continue;
            player.getInventory().setItem(i, itemStack);
        }
        ItemStack itemStack2 = player.getItemOnCursor();
        itemStack = NovaCrates.strip(itemStack2);
        if (itemStack != itemStack2) {
            player.setItemOnCursor(itemStack);
        }
    }

    private void syncEditor(EditMenu editMenu) {
        Crate crate = editMenu.crate;
        for (int i = 0; i < 7; ++i) {
            ItemStack itemStack = NovaCrates.strip(editMenu.inv.getItem(MID[i]));
            if (itemStack != null && itemStack.getType() != null && itemStack.getType().name().equals("AIR")) {
                itemStack = null;
            }
            if (itemStack != null && crate.items[i] == null) {
                crate.chance[i] = 10.0;
                crate.virtual[i] = false;
            }
            crate.items[i] = itemStack == null ? null : itemStack.clone();
        }
        this.saveCrates();
    }

    void openChance(Player player, Crate crate, int n) {
        ChanceMenu chanceMenu = new ChanceMenu();
        chanceMenu.crate = crate;
        chanceMenu.index = n;
        chanceMenu.inv = Bukkit.createInventory((InventoryHolder)chanceMenu, (int)27, (String)NovaCrates.color("&8Chance"));
        this.border(chanceMenu.inv);
        this.drawChance(chanceMenu);
        player.openInventory(chanceMenu.inv);
    }

    private void drawChance(ChanceMenu chanceMenu) {
        Crate crate = chanceMenu.crate;
        int n = chanceMenu.index;
        ItemStack itemStack = crate.items[n] == null ? new ItemStack(NovaCrates.mat("BARRIER", Material.STONE)) : crate.items[n].clone();
        NovaCrates.addLore(itemStack, "", "&7Chance: &#B895EC" + NovaCrates.fmt(crate.chance[n]) + "%", "&8(real chance with the other prizes: " + NovaCrates.fmt(crate.realPercent(n)) + "%)");
        chanceMenu.inv.setItem(13, itemStack);
        chanceMenu.inv.setItem(10, NovaCrates.named(NovaCrates.mat("RED_STAINED_GLASS_PANE", Material.STONE), "&c&l-10%", new String[0]));
        chanceMenu.inv.setItem(11, NovaCrates.named(NovaCrates.mat("RED_STAINED_GLASS_PANE", Material.STONE), "&c-1%", new String[0]));
        chanceMenu.inv.setItem(12, NovaCrates.named(NovaCrates.mat("RED_STAINED_GLASS_PANE", Material.STONE), "&c-0.1%", new String[0]));
        chanceMenu.inv.setItem(14, NovaCrates.named(NovaCrates.mat("LIME_STAINED_GLASS_PANE", Material.STONE), "&a+0.1%", new String[0]));
        chanceMenu.inv.setItem(15, NovaCrates.named(NovaCrates.mat("LIME_STAINED_GLASS_PANE", Material.STONE), "&a+1%", new String[0]));
        chanceMenu.inv.setItem(16, NovaCrates.named(NovaCrates.mat("LIME_STAINED_GLASS_PANE", Material.STONE), "&a&l+10%", new String[0]));
        chanceMenu.inv.setItem(22, NovaCrates.named(NovaCrates.mat("ARROW", Material.STONE), "&7Back", new String[0]));
        if (this.keyOf(crate.items[n]) != null) {
            chanceMenu.inv.setItem(4, NovaCrates.named(NovaCrates.mat("ENDER_EYE", Material.STONE), "&#B895EC&lGiven as: VIRTUAL key", "&7Goes straight to their &f/keys&7."));
        } else {
            chanceMenu.inv.setItem(4, this.glass());
        }
    }

    void openPreview(Player player, Crate crate) {
        PreviewMenu previewMenu = new PreviewMenu();
        previewMenu.inv = Bukkit.createInventory((InventoryHolder)previewMenu, (int)27, (String)NovaCrates.color(this.disp(crate)));
        this.border(previewMenu.inv);
        for (int i = 0; i < 7; ++i) {
            if (crate.items[i] == null) continue;
            ItemStack itemStack = crate.items[i].clone();
            NovaCrates.addLore(itemStack, "", "&7Chance: &#B895EC" + NovaCrates.fmt(crate.realPercent(i)) + "%" + (this.keyOf(crate.items[i]) != null ? " &8(virtual key)" : ""));
            previewMenu.inv.setItem(MID[i], itemStack);
        }
        player.openInventory(previewMenu.inv);
        NovaCrates.sound(player, "block.chest.open", 0.5f, 1.4f);
    }

    /*
     * WARNING - void declaration
     */
    void openKeys(Player player) {
        KeysMenu keysMenu = new KeysMenu();
        ArrayList<Crate> arrayList = new ArrayList<Crate>(this.crates.values());
        int n4 = arrayList.size();
        int n5 = Math.max(1, Math.min(4, (n4 + 6) / 7));
        int n6 = n5 + 2;
        int n7 = 0;
        for (Crate c : arrayList) {
            n7 += this.keysHeld(player, c);
        }
        keysMenu.inv = Bukkit.createInventory((InventoryHolder)keysMenu, (int)(n6 * 9), (String)NovaCrates.color("&8Your Keys"));
        ItemStack itemStack = NovaCrates.named(NovaCrates.mat("BLACK_STAINED_GLASS_PANE", Material.STONE), " ", new String[0]);
        ItemStack corner = NovaCrates.named(NovaCrates.mat("PURPLE_STAINED_GLASS_PANE", Material.STONE), " ", new String[0]);
        for (int slot = 0; slot < keysMenu.inv.getSize(); ++slot) {
            keysMenu.inv.setItem(slot, itemStack);
        }
        for (int cornerSlot : new int[]{0, 8, (n6 - 1) * 9, n6 * 9 - 1}) {
            keysMenu.inv.setItem(cornerSlot, corner);
        }
        ItemStack itemStack2 = new ItemStack(NovaCrates.mat("PLAYER_HEAD", Material.STONE));
        try {
            SkullMeta i2 = (SkullMeta)itemStack2.getItemMeta();
            i2.setOwningPlayer((OfflinePlayer)player);
            i2.setDisplayName(NovaCrates.color("&#B895EC&l" + player.getName() + "'s Keys"));
            ArrayList<String> arrayList2 = new ArrayList<String>();
            arrayList2.add(NovaCrates.color("&7Total keys: &f" + n7));
            arrayList2.add("");
            arrayList2.add(NovaCrates.color("&7Use them at the crates at spawn."));
            i2.setLore(arrayList2);
            itemStack2.setItemMeta((ItemMeta)i2);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        keysMenu.inv.setItem(4, itemStack2);
        int n, n2, n32;
        for (int j = 0; j < n5 && (n2 = (n32 = Math.min(n4, (n = j * 7) + 7)) - n) > 0; ++j) {
            int n8;
            int[] nArray;
            if (n2 <= 4) {
                int[][] nArrayArray = new int[][]{{4}, {3, 5}, {2, 4, 6}, {1, 3, 5, 7}};
                nArray = nArrayArray[n2 - 1];
            } else {
                n8 = 1 + (7 - n2) / 2;
                nArray = new int[n2];
                for (int k = 0; k < n2; ++k) {
                    nArray[k] = n8 + k;
                }
            }
            for (n8 = 0; n8 < n2; ++n8) {
                Crate crate = (Crate)arrayList.get(n + n8);
                int n9 = (1 + j) * 9 + nArray[n8];
                keysMenu.inv.setItem(n9, this.keyIcon(player, crate));
                keysMenu.slots.put(n9, crate.id);
            }
        }
        keysMenu.close = n6 * 9 - 5;
        keysMenu.inv.setItem(keysMenu.close, NovaCrates.named(NovaCrates.mat("BARRIER", Material.STONE), "&cClose", new String[0]));
        player.openInventory(keysMenu.inv);
        NovaCrates.sound(player, "block.chest.open", 0.5f, 1.4f);
    }

    private ItemStack keyIcon(Player player, Crate crate) {
        int n = this.vkeys(player.getUniqueId(), crate.id);
        int n2 = this.keysHeld(player, crate) - n;
        int n3 = n + n2;
        String string = this.accent("&#B895EC", crate, crate.locs.isEmpty() ? null : crate.locs.get(0));
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(NovaCrates.color("&7Keys: " + (n3 > 0 ? string : "&8") + n3));
        if (n2 > 0) {
            arrayList.add(NovaCrates.color("&8" + n + " stored, " + n2 + " in your inventory"));
        }
        arrayList.add("");
        arrayList.add(NovaCrates.color(string + "Click &7to see the prizes"));
        ItemStack itemStack = new ItemStack(NovaCrates.mat(this.keyMatFor(crate), Material.STONE), Math.max(1, Math.min(64, n3)));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(NovaCrates.color(this.disp(crate) + " &fKey" + (n3 == 1 ? "" : "s")));
            itemMeta.setLore(arrayList);
            if (n3 > 0) {
                try {
                    itemMeta.setEnchantmentGlintOverride(Boolean.valueOf(true));
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private static void addLore(ItemStack itemStack, String ... stringArray) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        ArrayList<String> arrayList = itemMeta.hasLore() && itemMeta.getLore() != null ? new ArrayList<String>(itemMeta.getLore()) : new ArrayList<String>();
        for (String string : stringArray) {
            arrayList.add(NovaCrates.color(string));
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        boolean bl;
        Inventory inventory = inventoryClickEvent.getInventory();
        if (inventory == null || !(inventory.getHolder() instanceof Menu)) {
            return;
        }
        Menu menu = (Menu)inventory.getHolder();
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        int n = inventoryClickEvent.getRawSlot();
        boolean bl2 = bl = n >= 0 && n < inventory.getSize();
        if (menu instanceof EditMenu) {
            EditMenu editMenu = (EditMenu)menu;
            if (inventoryClickEvent.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                inventoryClickEvent.setCancelled(true);
                return;
            }
            if (!bl) {
                this.refreshEditorSoon(player, editMenu);
                return;
            }
            int n2 = NovaCrates.indexOf(n);
            if (n2 >= 0) {
                boolean bl3;
                ItemStack itemStack = inventoryClickEvent.getCurrentItem();
                boolean bl4 = bl3 = inventoryClickEvent.getCursor() == null || inventoryClickEvent.getCursor().getType().name().equals("AIR");
                if (inventoryClickEvent.isRightClick() && itemStack != null && !itemStack.getType().name().equals("AIR") && bl3) {
                    inventoryClickEvent.setCancelled(true);
                    this.syncEditor(editMenu);
                    Bukkit.getScheduler().runTask((Plugin)this, () -> this.openChance(player, editMenu.crate, n2));
                    return;
                }
                this.refreshEditorSoon(player, editMenu);
                return;
            }
            inventoryClickEvent.setCancelled(true);
            if (n == 22) {
                int n3 = 0;
                if (this.shulkerColour(editMenu.crate) != null) {
                    return;
                }
                for (int i = 0; i < CANDLES.length; ++i) {
                    if (!CANDLES[i].equals(editMenu.crate.keyMat)) continue;
                    n3 = i;
                }
                editMenu.crate.keyMat = CANDLES[(n3 + 1) % CANDLES.length];
                inventory.setItem(22, this.keyButton(editMenu.crate));
                this.saveCrates();
                this.refreshHolos(editMenu.crate);
                NovaCrates.sound(player, "ui.button.click", 0.6f, 1.4f);
            } else if (n == 26) {
                NovaCrates.giveItem(player, this.keyItem(editMenu.crate, 1));
                NovaCrates.sound(player, "entity.item.pickup", 0.6f, 1.2f);
            } else if (n == 18) {
                this.syncEditor(editMenu);
                Bukkit.getScheduler().runTask((Plugin)this, () -> this.openList(player));
            }
            return;
        }
        inventoryClickEvent.setCancelled(true);
        if (!bl) {
            return;
        }
        if (menu instanceof ListMenu) {
            ListMenu listMenu = (ListMenu)menu;
            int n4 = n;
            int n5 = n4 / 9;
            int n6 = n4 % 9;
            if (n5 < 1 || n6 < 1 || n6 > 7) {
                return;
            }
            int n7 = (n5 - 1) * 7 + (n6 - 1);
            if (n7 >= listMenu.ids.size()) {
                return;
            }
            Crate crate = this.crates.get(listMenu.ids.get(n7));
            if (crate == null) {
                return;
            }
            if (inventoryClickEvent.isRightClick()) {
                NovaCrates.giveItem(player, this.keyItem(crate, 1));
                NovaCrates.sound(player, "entity.item.pickup", 0.6f, 1.2f);
            } else {
                Bukkit.getScheduler().runTask((Plugin)this, () -> this.openEditor(player, crate));
            }
        } else if (menu instanceof KeysMenu) {
            Crate crate;
            KeysMenu keysMenu = (KeysMenu)menu;
            if (n == keysMenu.close) {
                Bukkit.getScheduler().runTask((Plugin)this, () -> player.closeInventory());
                return;
            }
            String string = keysMenu.slots.get(n);
            Crate crate2 = crate = string == null ? null : this.crates.get(string);
            if (crate != null) {
                Bukkit.getScheduler().runTask((Plugin)this, () -> this.openPreview(player, crate));
            }
        } else if (menu instanceof ChanceMenu) {
            ChanceMenu chanceMenu = (ChanceMenu)menu;
            double d = 0.0;
            switch (n) {
                case 10: {
                    d = -10.0;
                    break;
                }
                case 11: {
                    d = -1.0;
                    break;
                }
                case 12: {
                    d = -0.1;
                    break;
                }
                case 14: {
                    d = 0.1;
                    break;
                }
                case 15: {
                    d = 1.0;
                    break;
                }
                case 16: {
                    d = 10.0;
                    break;
                }
                case 22: {
                    Bukkit.getScheduler().runTask((Plugin)this, () -> this.openEditor(player, chanceMenu.crate));
                    return;
                }
                case 4: {
                    return;
                }
                default: {
                    return;
                }
            }
            double d2 = (double)Math.round((chanceMenu.crate.chance[chanceMenu.index] + d) * 10.0) / 10.0;
            chanceMenu.crate.chance[chanceMenu.index] = Math.max(0.1, Math.min(100.0, d2));
            this.saveCrates();
            this.drawChance(chanceMenu);
            NovaCrates.sound(player, "ui.button.click", 0.5f, d > 0.0 ? 1.6f : 1.0f);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent inventoryDragEvent) {
        Inventory inventory = inventoryDragEvent.getInventory();
        if (inventory == null || !(inventory.getHolder() instanceof Menu)) {
            return;
        }
        if (inventory.getHolder() instanceof EditMenu) {
            Iterator iterator = inventoryDragEvent.getRawSlots().iterator();
            while (iterator.hasNext()) {
                int n = (Integer)iterator.next();
                if (n >= inventory.getSize() || NovaCrates.indexOf(n) >= 0) continue;
                inventoryDragEvent.setCancelled(true);
                return;
            }
            this.refreshEditorSoon((Player)inventoryDragEvent.getWhoClicked(), (EditMenu)inventory.getHolder());
            return;
        }
        inventoryDragEvent.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        Inventory inventory = inventoryCloseEvent.getInventory();
        if (inventory == null) {
            return;
        }
        if (inventory.getHolder() instanceof EditMenu) {
            this.syncEditor((EditMenu)inventory.getHolder());
            if (inventoryCloseEvent.getPlayer() instanceof Player) {
                Player player = (Player)inventoryCloseEvent.getPlayer();
                Bukkit.getScheduler().runTask((Plugin)this, () -> this.cleanPlayer(player));
            }
        } else if (inventory.getHolder() instanceof Spin) {
            Spin spin = (Spin)inventory.getHolder();
            if (!spin.given && !spin.landed && spin.p.isOnline() && !spin.toldClosed) {
                spin.toldClosed = true;
                spin.p.sendMessage(this.pre("&7Your crate is still rolling... &8(right-click the crate to watch)"));
            }
        }
    }

    private static int indexOf(int n) {
        for (int i = 0; i < 7; ++i) {
            if (MID[i] != n) continue;
            return i;
        }
        return -1;
    }

    private int pick(Crate crate) {
        double d = crate.total();
        double d2 = this.rnd.nextDouble() * d;
        int n = -1;
        for (int i = 0; i < 7; ++i) {
            if (crate.items[i] == null) continue;
            n = i;
            if (!((d2 -= crate.chance[i]) < 0.0)) continue;
            return i;
        }
        return n;
    }

    void startSpin(Player player, Crate crate) {
        Spin spin = new Spin();
        spin.p = player;
        spin.crate = crate;
        spin.prize = this.pick(crate);
        int n = 38 + this.rnd.nextInt(6);
        spin.reel = new int[n];
        for (int i = 0; i < n; ++i) {
            spin.reel[i] = this.pick(crate);
        }
        spin.reel[n - 5] = spin.prize;
        spin.steps = n - 9;
        spin.inv = Bukkit.createInventory((InventoryHolder)spin, (int)27, (String)(NovaCrates.color("&8Opening ") + NovaCrates.color(this.disp(crate))));
        this.border(spin.inv);
        ItemStack itemStack = NovaCrates.named(NovaCrates.mat("PURPLE_STAINED_GLASS_PANE", Material.STONE), "&#B895EC\u25bc", new String[0]);
        spin.inv.setItem(4, itemStack);
        spin.inv.setItem(22, NovaCrates.named(NovaCrates.mat("PURPLE_STAINED_GLASS_PANE", Material.STONE), "&#B895EC\u25b2", new String[0]));
        this.drawReel(spin);
        this.spins.put(player.getUniqueId(), spin);
        player.openInventory(spin.inv);
        NovaCrates.sound(player, "block.chest.open", 0.7f, 1.0f);
    }

    private void drawReel(Spin spin) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = spin.crate.items[spin.reel[spin.offset + i]];
            spin.inv.setItem(9 + i, itemStack == null ? this.glass() : itemStack.clone());
        }
    }

    private void tickSpins() {
        if (this.spins.isEmpty()) {
            return;
        }
        for (Spin spin : new ArrayList<Spin>(this.spins.values())) {
            int n;
            if (!spin.p.isOnline()) {
                this.finish(spin, false);
                continue;
            }
            if (spin.landed) {
                if (--spin.closeIn > 0) continue;
                this.spins.remove(spin.p.getUniqueId());
                if (spin.p.getOpenInventory() == null || spin.p.getOpenInventory().getTopInventory() != spin.inv) continue;
                spin.p.closeInventory();
                continue;
            }
            if (spin.wait-- > 0) continue;
            ++spin.offset;
            ++spin.step;
            this.drawReel(spin);
            double d = (double)spin.step / (double)spin.steps;
            NovaCrates.sound(spin.p, "ui.button.click", 0.35f, (float)(1.9 - d * 0.7));
            spin.wait = (int)Math.round(d * d * d * 9.0);
            if (spin.step < spin.steps) continue;
            spin.landed = true;
            spin.closeIn = 40;
            for (n = 0; n < 27; ++n) {
                if (n >= 9 && n < 18) continue;
                spin.inv.setItem(n, NovaCrates.named(NovaCrates.mat("PURPLE_STAINED_GLASS_PANE", Material.STONE), " ", new String[0]));
            }
            for (n = 9; n < 18; ++n) {
                if (n == 13) continue;
                spin.inv.setItem(n, this.glass());
            }
            this.finish(spin, true);
        }
    }

    private void finish(Spin spin, boolean bl) {
        if (spin.given) {
            return;
        }
        spin.given = true;
        if (!bl) {
            this.spins.remove(spin.p.getUniqueId());
        }
        Crate crate = spin.crate;
        ItemStack itemStack = crate.items[spin.prize];
        Player player = spin.p;
        if (itemStack == null) {
            return;
        }
        String string = this.keyOf(itemStack);
        if (string == null || !this.crates.containsKey(string)) {
            string = this.keyByName(itemStack);
        }
        if (string != null && this.crates.containsKey(string)) {
            this.addVkeys(player.getUniqueId(), string, itemStack.getAmount());
            if (player.isOnline()) {
                player.sendMessage(this.pre("&7You won &f" + itemStack.getAmount() + "x " + this.disp(this.crates.get(string)) + " &fKey" + (itemStack.getAmount() == 1 ? "" : "s") + " &7from the " + this.disp(crate) + " &7crate! &8(added to &#B895EC/keys&8)"));
                NovaCrates.sound(player, "entity.player.levelup", 0.8f, 1.3f);
            }
            return;
        }
        NovaCrates.giveItem(player, itemStack.clone());
        if (player.isOnline()) {
            player.sendMessage(this.pre("&7You won &f" + NovaCrates.nameOf(itemStack) + (String)(itemStack.getAmount() > 1 ? " &7x" + itemStack.getAmount() : "") + " &7from the " + this.disp(crate) + " &7crate!"));
            NovaCrates.sound(player, "entity.player.levelup", 0.8f, 1.3f);
        }
    }

    Crate crateAt(Block block) {
        if (block == null) {
            return null;
        }
        for (Crate crate : this.crates.values()) {
            for (Location location : crate.locs) {
                if (location.getWorld() != block.getWorld() || location.getBlockX() != block.getX() || location.getBlockY() != block.getY() || location.getBlockZ() != block.getZ()) continue;
                return crate;
            }
        }
        return null;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onUse(PlayerInteractEvent playerInteractEvent) {
        Block block = playerInteractEvent.getClickedBlock();
        if (block == null) {
            return;
        }
        Crate crate = this.crateAt(block);
        if (crate == null) {
            return;
        }
        Player player = playerInteractEvent.getPlayer();
        if (playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.hasPermission("novacrates.admin") && player.isSneaking()) {
                return;
            }
            playerInteractEvent.setCancelled(true);
            if (playerInteractEvent.getHand() == EquipmentSlot.HAND) {
                this.preview(player, crate);
            }
            return;
        }
        if (playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        playerInteractEvent.setCancelled(true);
        if (playerInteractEvent.getHand() != EquipmentSlot.HAND) {
            return;
        }
        this.tryOpen(player, crate, block.getLocation());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onUseEntity(PlayerInteractEntityEvent playerInteractEntityEvent) {
        Parts parts = this.partsByHit.get(playerInteractEntityEvent.getRightClicked().getUniqueId());
        if (parts == null) {
            return;
        }
        playerInteractEntityEvent.setCancelled(true);
        if (playerInteractEntityEvent.getHand() != EquipmentSlot.HAND) {
            return;
        }
        this.tryOpen(playerInteractEntityEvent.getPlayer(), parts.crate, parts.anchor);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onHitEntity(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Parts parts = this.partsByHit.get(entityDamageByEntityEvent.getEntity().getUniqueId());
        if (parts == null) {
            return;
        }
        entityDamageByEntityEvent.setCancelled(true);
        if (entityDamageByEntityEvent.getDamager() instanceof Player) {
            this.preview((Player)entityDamageByEntityEvent.getDamager(), parts.crate);
        }
    }

    private void preview(Player player, Crate crate) {
        long l = System.currentTimeMillis();
        Long l2 = this.lastPreview.get(player.getUniqueId());
        if (l2 != null && l - l2 < 300L) {
            return;
        }
        this.lastPreview.put(player.getUniqueId(), l);
        this.openPreview(player, crate);
    }

    void tryOpen(Player player, Crate crate, Location location) {
        Spin spin = this.spins.get(player.getUniqueId());
        if (spin != null) {
            if (!spin.landed) {
                player.openInventory(spin.inv);
            }
            return;
        }
        if (crate.empty()) {
            player.sendMessage(this.pre("&cThis crate has no prizes yet."));
            return;
        }

        // --- SHIFT + RIGHT CLICK: OPEN ALL KEYS AT ONCE ---
        if (player.isSneaking()) {
            int totalKeys = this.keysHeld(player, crate);
            if (totalKeys <= 0) {
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                String string = this.keyOf(itemStack);
                Crate crate2 = string == null ? null : this.crates.get(string);
                player.sendMessage(this.pre(crate2 != null ? "&cThat's a " + this.disp(crate2) + " &ckey - you need a " + this.disp(crate) + " &cKey." : "&cYou need a " + this.disp(crate) + " &cKey &cto open this. &7Left-click to see the prizes."));
                NovaCrates.sound(player, "block.note_block.bass", 0.8f, 0.6f);
                try {
                    Vector vector = new Vector(player.getLocation().getX() - ((double)location.getBlockX() + 0.5), 0.0, player.getLocation().getZ() - ((double)location.getBlockZ() + 0.5));
                    double d = Math.sqrt(vector.getX() * vector.getX() + vector.getZ() * vector.getZ());
                    if (d > 0.01) {
                        player.setVelocity(new Vector(vector.getX() / d * 0.6, 0.25, vector.getZ() / d * 0.6));
                    }
                } catch (Throwable ignored) {}
                return;
            }

            this.openAll(player, crate, location, totalKeys);
            return;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String string = this.keyOf(itemStack);
        if (crate.id.equals(string)) {
            if (itemStack.getAmount() <= 1) {
                player.getInventory().setItemInMainHand(null);
            } else {
                itemStack.setAmount(itemStack.getAmount() - 1);
            }
            this.startSpin(player, crate);
            return;
        }
        if (this.vkeys(player.getUniqueId(), crate.id) > 0) {
            this.addVkeys(player.getUniqueId(), crate.id, -1);
            this.startSpin(player, crate);
            return;
        }
        Crate crate2 = string == null ? null : this.crates.get(string);
        player.sendMessage(this.pre(crate2 != null ? "&cThat's a " + this.disp(crate2) + " &ckey - you need a " + this.disp(crate) + " &cKey." : "&cYou need a " + this.disp(crate) + " &cKey &cto open this. &7Left-click to see the prizes."));
        NovaCrates.sound(player, "block.note_block.bass", 0.8f, 0.6f);
        try {
            Vector vector = new Vector(player.getLocation().getX() - ((double)location.getBlockX() + 0.5), 0.0, player.getLocation().getZ() - ((double)location.getBlockZ() + 0.5));
            double d = Math.sqrt(vector.getX() * vector.getX() + vector.getZ() * vector.getZ());
            if (d > 0.01) {
                player.setVelocity(new Vector(vector.getX() / d * 0.6, 0.25, vector.getZ() / d * 0.6));
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    void openAll(Player player, Crate crate, Location location, int totalKeys) {
        int taken = this.takeKeys(player.getUniqueId(), player, crate, totalKeys);
        if (taken <= 0) {
            return;
        }

        Map<String, Integer> rewardsWon = new LinkedHashMap<String, Integer>();
        Map<String, Integer> keysWon = new LinkedHashMap<String, Integer>();

        for (int i = 0; i < taken; ++i) {
            int prizeIdx = this.pick(crate);
            if (prizeIdx < 0 || prizeIdx >= crate.items.length) continue;
            ItemStack prize = crate.items[prizeIdx];
            if (prize == null) continue;

            String keyId = this.keyOf(prize);
            if (keyId == null || !this.crates.containsKey(keyId)) {
                keyId = this.keyByName(prize);
            }

            if (keyId != null && this.crates.containsKey(keyId)) {
                int keyAmt = prize.getAmount();
                this.addVkeys(player.getUniqueId(), keyId, keyAmt);
                keysWon.put(keyId, keysWon.getOrDefault(keyId, 0) + keyAmt);
            } else {
                NovaCrates.giveItem(player, prize.clone());
                String prizeName = NovaCrates.nameOf(prize);
                rewardsWon.put(prizeName, rewardsWon.getOrDefault(prizeName, 0) + prize.getAmount());
            }
        }

        player.sendMessage(this.pre("&7Opened &f" + taken + "x " + this.disp(crate) + " &7crate" + (taken == 1 ? "" : "s") + " at once!"));
        if (!rewardsWon.isEmpty()) {
            player.sendMessage(this.pre("&7Items won:"));
            for (Map.Entry<String, Integer> entry : rewardsWon.entrySet()) {
                player.sendMessage(this.pre("  &8\u25cf &f" + entry.getValue() + "x &7" + entry.getKey()));
            }
        }
        if (!keysWon.isEmpty()) {
            player.sendMessage(this.pre("&7Keys won &8(added to &#B895EC/keys&8):"));
            for (Map.Entry<String, Integer> entry : keysWon.entrySet()) {
                Crate wonCrate = this.crates.get(entry.getKey());
                String cName = wonCrate != null ? this.disp(wonCrate) : entry.getKey();
                player.sendMessage(this.pre("  &8\u25cf &f" + entry.getValue() + "x " + cName + " &fKey" + (entry.getValue() == 1 ? "" : "s")));
            }
        }

        NovaCrates.sound(player, "entity.player.levelup", 1.0f, 1.2f);
        NovaCrates.sound(player, "entity.firework_rocket.blast", 0.8f, 1.1f);
        NovaCrates.sound(player, "block.chest.open", 0.7f, 1.0f);
        if (location != null && location.getWorld() != null) {
            try {
                location.getWorld().spawnParticle(org.bukkit.Particle.valueOf("FIREWORK"), location.clone().add(0.5, 1.0, 0.5), 25, 0.3, 0.3, 0.3, 0.05);
            } catch (Throwable ignored) {}
        }
        this.saveKeys();
        this.refreshHolos(crate);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent blockBreakEvent) {
        Crate crate = this.crateAt(blockBreakEvent.getBlock());
        if (crate == null) {
            return;
        }
        Player player = blockBreakEvent.getPlayer();
        if (!player.hasPermission("novacrates.admin") || !player.isSneaking()) {
            blockBreakEvent.setCancelled(true);
            return;
        }
        Block block = blockBreakEvent.getBlock();
        crate.locs.removeIf(location -> location.getWorld() == block.getWorld() && location.getBlockX() == block.getX() && location.getBlockY() == block.getY() && location.getBlockZ() == block.getZ());
        Parts parts = this.parts.remove(NovaCrates.key(block.getLocation()));
        if (parts != null) {
            this.removeParts(parts);
        }
        crate.looks.remove(NovaCrates.key(block.getLocation()));
        this.saveCrates();
        player.sendMessage(this.pre("&7Removed that " + this.disp(crate) + " &7crate block."));
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent blockPlaceEvent) {
        if (this.keyOf(blockPlaceEvent.getItemInHand()) != null) {
            blockPlaceEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        Spin spin = this.spins.get(playerQuitEvent.getPlayer().getUniqueId());
        if (spin != null) {
            this.finish(spin, false);
        }
        this.spins.remove(playerQuitEvent.getPlayer().getUniqueId());
    }

    static String key(Location location) {
        return (location.getWorld() == null ? "?" : location.getWorld().getName()) + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }

    private static String colorName(String string) {
        return string.equals("CANDLE") ? "" : string.substring(0, string.length() - "_CANDLE".length());
    }

    private static int[] rgb(String string) {
        switch (string) {
            case "WHITE": {
                return new int[]{240, 240, 240};
            }
            case "LIGHT_GRAY": {
                return new int[]{190, 190, 190};
            }
            case "GRAY": {
                return new int[]{140, 140, 140};
            }
            case "BLACK": {
                return new int[]{110, 110, 110};
            }
            case "BROWN": {
                return new int[]{190, 130, 80};
            }
            case "RED": {
                return new int[]{255, 85, 85};
            }
            case "ORANGE": {
                return new int[]{255, 160, 60};
            }
            case "YELLOW": {
                return new int[]{255, 225, 80};
            }
            case "LIME": {
                return new int[]{140, 240, 80};
            }
            case "GREEN": {
                return new int[]{90, 190, 90};
            }
            case "CYAN": {
                return new int[]{60, 210, 220};
            }
            case "LIGHT_BLUE": {
                return new int[]{120, 200, 255};
            }
            case "BLUE": {
                return new int[]{100, 130, 255};
            }
            case "MAGENTA": {
                return new int[]{230, 100, 230};
            }
            case "PINK": {
                return new int[]{255, 160, 210};
            }
            case "PURPLE": {
                return new int[]{184, 149, 236};
            }
        }
        return new int[]{200, 170, 230};
    }

    private static String colorOf(Crate crate, Location location) {
        String string;
        String string2 = string = location == null || location.getWorld() == null ? "" : crate.looks.getOrDefault(NovaCrates.key(location), location.getBlock().getType().name());
        if (string.endsWith("SHULKER_BOX")) {
            return string.equals("SHULKER_BOX") ? "" : string.substring(0, string.length() - "_SHULKER_BOX".length());
        }
        return NovaCrates.colorName(crate.keyMat);
    }

    private static Color glowFor(String string) {
        int[] nArray = NovaCrates.rgb(string);
        return Color.fromRGB((int)nArray[0], (int)nArray[1], (int)nArray[2]);
    }

    private static String hexFor(String string) {
        int[] nArray = NovaCrates.rgb(string);
        return String.format("&#%02X%02X%02X", nArray[0], nArray[1], nArray[2]);
    }

    private static Material shulkerFor(String string) {
        String string2 = NovaCrates.colorName(string);
        return NovaCrates.mat((String)(string2.isEmpty() ? "SHULKER_BOX" : string2 + "_SHULKER_BOX"), Material.STONE);
    }

    private String mainText(Crate crate, Location location) {
        List<String> arrayList = this.getConfig().getStringList("hologram.lines");
        if (arrayList.isEmpty()) {
            arrayList = new ArrayList<String>();
            arrayList.add("%crate%");
            arrayList.add("%subtitle%");
            arrayList.add("");
            arrayList.add("&#B895EC\u25b6 &fLeft-Click to &#B895ECPreview");
            arrayList.add("&#B895EC\u25c0 &fRight-Click to &#B895ECOpen");
            arrayList.add("&#B895EC\u25c0 &fShift Right-Click to &#B895ECOpen All");
        }
        String string = crate.subtitle != null ? crate.subtitle : this.getConfig().getString("hologram.default-subtitle", "&7Get keys @ /store");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrayList.size(); ++i) {
            if (i > 0) {
                stringBuilder.append('\n');
            }
            stringBuilder.append(((String)arrayList.get(i)).replace("%crate%", this.pumpkins(crate)).replace("%subtitle%", string));
        }
        return NovaCrates.color(this.accent(stringBuilder.toString(), crate, location));
    }

    private String playerText(Crate crate, Player player, Location location) {
        Object object = this.getConfig().getString("hologram.keys-line", "&fYou have &#B895EC%keys% &fkeys!").replace("%keys%", String.valueOf(this.keysHeld(player, crate)));
        if (crate.stars > 0.0) {
            object = (String)object + "\n" + this.starLine(crate.stars, crate.starsMax);
        }
        return NovaCrates.color(this.accent((String)object, crate, location));
    }

    String shulkerColour(Crate crate) {
        for (Location location : crate.locs) {
            String string = crate.looks.getOrDefault(NovaCrates.key(location), location.getWorld() == null ? "" : location.getBlock().getType().name());
            if (!string.endsWith("SHULKER_BOX")) continue;
            return string.equals("SHULKER_BOX") ? "" : string.substring(0, string.length() - "_SHULKER_BOX".length());
        }
        return null;
    }

    String keyMatFor(Crate crate) {
        String string = this.shulkerColour(crate);
        if (string == null) {
            return crate.keyMat;
        }
        return string.isEmpty() ? "CANDLE" : string + "_CANDLE";
    }

    private ItemStack keyButton(Crate crate) {
        return this.shulkerColour(crate) != null ? NovaCrates.named(NovaCrates.mat(this.keyMatFor(crate), Material.STONE), "&#B895EC&lKey colour", "&7Matches the crate's shulker.") : NovaCrates.named(NovaCrates.mat(this.keyMatFor(crate), Material.STONE), "&#B895EC&lKey colour", "&7Click to change the candle colour.");
    }

    String disp(Crate crate) {
        return this.accent(this.pumpkins(crate), crate, crate.locs.isEmpty() ? null : crate.locs.get(0));
    }

    private String pumpkins(Crate crate) {
        if (!this.getConfig().getBoolean("halloween-emojis", true) || crate.display == null) {
            return crate.display;
        }
        String string = (crate.id + " " + crate.display.replaceAll("(?i)&#[0-9a-f]{6}|&[0-9a-fk-or]", "")).toLowerCase(Locale.ROOT);
        boolean bl = false;
        for (String string2 : this.getConfig().getStringList("halloween-words").isEmpty() ? Arrays.asList("halloween", "spooky", "pumpkin") : this.getConfig().getStringList("halloween-words")) {
            if (!string.contains(string2.toLowerCase(Locale.ROOT))) continue;
            bl = true;
        }
        if (!bl || crate.display.contains("\ud83c\udf83")) {
            return crate.display;
        }
        return "&#FF8A1F\ud83c\udf83 " + crate.display + " &#FF8A1F\ud83c\udf83";
    }

    private String accent(String string, Crate crate, Location location) {
        if (!this.getConfig().getBoolean("hologram.match-colour", true)) {
            return string.replace("%color%", "&#B895EC");
        }
        String string2 = NovaCrates.hexFor(NovaCrates.colorOf(crate, location));
        return string.replace("&#B895EC", string2).replace("&#b895ec", string2).replace("%color%", string2);
    }

    int keysHeld(Player player, Crate crate) {
        int n = this.vkeys(player.getUniqueId(), crate.id);
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null || !crate.id.equals(this.keyOf(itemStack))) continue;
            n += itemStack.getAmount();
        }
        return n;
    }

    private void spawnParts(Crate crate, Location location) {
        World world = location.getWorld();
        if (world == null || !world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
            return;
        }
        this.clearTagged(location);
        Parts parts = new Parts();
        parts.crate = crate;
        parts.anchor = location;
        parts.blockType = location.getBlock().getType().name();
        double d = (double)location.getBlockX() + 0.5;
        double d2 = location.getBlockY() + 1;
        double d3 = (double)location.getBlockZ() + 0.5;
        double d4 = this.getConfig().getDouble("hologram.height", 0.0);
        Block block = location.getBlock();
        try {
            String string = crate.looks.get(NovaCrates.key(location));
            if (string != null && block.getType().name().equals("BARRIER")) {
                Material innerMat = NovaCrates.mat(string, Material.STONE);
                float f2 = (float)Math.max(0.3, Math.min(1.0, this.getConfig().getDouble("shulker-size", 0.8)));
                float f = (1.0f - f2) / 2.0f;
                parts.inner = (BlockDisplay)world.spawn(new Location(world, (double)location.getBlockX() + 0.5, (double)location.getBlockY(), (double)location.getBlockZ() + 0.5, 0.0f, 0.0f), BlockDisplay.class, arg_0 -> NovaCrates.initInnerDisplay(innerMat, f, f2, arg_0));
            }
            if (this.getConfig().getBoolean("glass-case", true) && !block.getType().name().equals("AIR")) {
                String colorObj = NovaCrates.colorOf(crate, location);
                Material material = NovaCrates.mat(colorObj.isEmpty() ? "GLASS" : colorObj + "_STAINED_GLASS", Material.STONE);
                float glassSize = (float)this.getConfig().getDouble("glass-size", 1.0);
                float f3 = (1.0f - glassSize) / 2.0f;
                parts.shulker = (BlockDisplay)world.spawn(new Location(world, (double)location.getBlockX() + 0.5, (double)location.getBlockY(), (double)location.getBlockZ() + 0.5, 0.0f, 0.0f), BlockDisplay.class, arg_0 -> this.initShulkerDisplay(material, f3, glassSize, colorObj, arg_0));
            }
            parts.text = (TextDisplay)world.spawn(new Location(world, d, d2 + 0.65 + d4, d3, 0.0f, 0.0f), TextDisplay.class, textDisplay -> {
                textDisplay.addScoreboardTag("novacrates_holo");
                textDisplay.setPersistent(false);
                textDisplay.setBillboard(Display.Billboard.CENTER);
                textDisplay.setText(this.mainText(crate, location));
                this.styleText((TextDisplay)textDisplay);
                textDisplay.setLineWidth(400);
                textDisplay.setBackgroundColor(Color.fromARGB((int)0, (int)0, (int)0, (int)0));
            });
        }
        catch (Throwable throwable) {
            this.getLogger().warning("Couldn't spawn crate visuals: " + String.valueOf(throwable));
        }
        this.parts.put(NovaCrates.key(location), parts);
        if (parts.hit != null) {
            this.partsByHit.put(parts.hit.getUniqueId(), parts);
        }
    }

    private static void visible(Display display) {
        try {
            display.setDisplayWidth(1.6f);
            display.setDisplayHeight(1.6f);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        try {
            display.setViewRange(2.0f);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void clearTagged(Location location) {
        if (location.getWorld() == null) {
            return;
        }
        Location location2 = new Location(location.getWorld(), (double)location.getBlockX() + 0.5, (double)location.getBlockY() + 1.5, (double)location.getBlockZ() + 0.5, 0.0f, 0.0f);
        for (Entity entity : location.getWorld().getNearbyEntities(location2, 1.0, 2.5, 1.0)) {
            if (!entity.getScoreboardTags().contains("novacrates_holo")) continue;
            entity.remove();
        }
    }

    void removeParts(Parts parts) {
        for (Entity entity : new Entity[]{parts.shulker, parts.inner, parts.hit, parts.text}) {
            if (entity == null) continue;
            try {
                entity.remove();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        for (TextDisplay textDisplay : parts.mine.values()) {
            try {
                textDisplay.remove();
            }
            catch (Throwable throwable) {}
        }
        parts.mine.clear();
        if (parts.hit != null) {
            this.partsByHit.remove(parts.hit.getUniqueId());
        }
    }

    private void captureShulker(Crate crate, Location location) {
        if (!this.getConfig().getBoolean("shrink-shulker", true)) {
            return;
        }
        Block block = location.getBlock();
        String string = block.getType().name();
        if (!string.endsWith("SHULKER_BOX")) {
            return;
        }
        crate.looks.put(NovaCrates.key(location), string);
        block.setType(NovaCrates.mat("BARRIER", Material.STONE), false);
        this.saveCrates();
    }

    void restoreShulker(Crate crate, Location location) {
        String string = crate.looks.get(NovaCrates.key(location));
        if (string == null || location.getWorld() == null) {
            return;
        }
        Block block = location.getBlock();
        if (block.getType().name().equals("BARRIER")) {
            block.setType(NovaCrates.mat(string, Material.STONE), false);
        }
    }

    void refreshHolos(Crate crate) {
        for (Location location : crate.locs) {
            Parts parts = this.parts.remove(NovaCrates.key(location));
            if (parts != null) {
                this.removeParts(parts);
            }
            this.spawnParts(crate, location);
        }
    }

    private double viewBlocks() {
        return 32.0;
    }

    private void styleText(TextDisplay textDisplay) {
        textDisplay.setShadowed(true);
        textDisplay.setSeeThrough(false);
        float f = (float)Math.max(0.2, Math.min(3.0, this.getConfig().getDouble("hologram.scale", 0.8)));
        textDisplay.setTransformation(new Transformation(new Vector3f(0.0f, 0.0f, 0.0f), new Quaternionf(), new Vector3f(f, f, f), new Quaternionf()));
    }

    void tickVisuals() {
        HashSet<String> hashSet = new HashSet<String>();
        for (Crate object : this.crates.values()) {
            for (Location location : object.locs) {
                String string = NovaCrates.key(location);
                hashSet.add(string);
                World world = location.getWorld();
                Parts parts = this.parts.get(string);
                if (world == null || !world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                    if (parts == null) continue;
                    this.removeParts(parts);
                    this.parts.remove(string);
                    continue;
                }
                this.captureShulker(object, location);
                if (parts == null || parts.crate != object || parts.shulker != null && !parts.shulker.isValid() || parts.inner != null && !parts.inner.isValid() || parts.text == null || !parts.text.isValid() || !location.getBlock().getType().name().equals(parts.blockType)) {
                    if (parts != null) {
                        this.removeParts(parts);
                    }
                    this.spawnParts(object, location);
                    parts = this.parts.get(string);
                    if (parts == null) continue;
                }
                this.updatePlayerLines(parts);
            }
        }
        for (String string : new ArrayList<String>(this.parts.keySet())) {
            if (hashSet.contains(string)) continue;
            this.removeParts(this.parts.remove(string));
        }
    }

    private void updatePlayerLines(Parts parts) {
        World world = parts.anchor.getWorld();
        double d = (double)parts.anchor.getBlockX() + 0.5;
        double d2 = parts.anchor.getBlockY() + 1;
        double d3 = (double)parts.anchor.getBlockZ() + 0.5;
        Location location = new Location(world, d, d2 + 0.12 + this.getConfig().getDouble("hologram.height", 0.0), d3, 0.0f, 0.0f);
        HashSet<UUID> hashSet = new HashSet<UUID>();
        for (Player object : world.getPlayers()) {
            double d4 = this.viewBlocks();
            if (object.getLocation().distanceSquared(location) > d4 * d4) continue;
            hashSet.add(object.getUniqueId());
            String string = this.playerText(parts.crate, object, parts.anchor);
            TextDisplay textDisplay2 = parts.mine.get(object.getUniqueId());
            if (textDisplay2 == null || !textDisplay2.isValid()) {
                try {
                    textDisplay2 = (TextDisplay)world.spawn(location, TextDisplay.class, textDisplay -> {
                        textDisplay.addScoreboardTag("novacrates_holo");
                        textDisplay.setPersistent(false);
                        textDisplay.setVisibleByDefault(false);
                        textDisplay.setBillboard(Display.Billboard.CENTER);
                        textDisplay.setText(string);
                        this.styleText((TextDisplay)textDisplay);
                        textDisplay.setBackgroundColor(Color.fromARGB((int)0, (int)0, (int)0, (int)0));
                    });
                    object.showEntity((Plugin)this, (Entity)textDisplay2);
                    parts.mine.put(object.getUniqueId(), textDisplay2);
                    parts.lastText.put(object.getUniqueId(), string);
                }
                catch (Throwable throwable) {}
                continue;
            }
            if (string.equals(parts.lastText.get(object.getUniqueId()))) continue;
            textDisplay2.setText(string);
            parts.lastText.put(object.getUniqueId(), string);
        }
        Iterator<Map.Entry<UUID, TextDisplay>> iterator = parts.mine.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, TextDisplay> object = iterator.next();
            if (hashSet.contains(object.getKey())) continue;
            try {
                object.getValue().remove();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            parts.lastText.remove(object.getKey());
            iterator.remove();
        }
    }

    public boolean handleCrate(CommandSender commandSender, String[] stringArray) {
        String string2;

        if (!commandSender.hasPermission("novacrates.admin")) {
            commandSender.sendMessage(this.pre("&cYou don't have permission."));
            return true;
        }
        if (stringArray.length == 0) {
            if (commandSender instanceof Player) {
                this.openList((Player)commandSender);
            } else {
                this.help(commandSender);
            }
            return true;
        }
        switch (string2 = stringArray[0].toLowerCase(Locale.ROOT)) {
            case "create": {
                if (stringArray.length < 2) {
                    commandSender.sendMessage(this.pre("&7Usage: &#B895EC/crate create <name> [display name]"));
                    return true;
                }
                String string3 = stringArray[1].toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_-]", "");
                if (string3.isEmpty() || this.crates.containsKey(string3)) {
                    commandSender.sendMessage(this.pre("&cThat crate already exists or the name is invalid."));
                    return true;
                }
                Crate crate = new Crate();
                crate.id = string3;
                crate.display = stringArray.length > 2 ? NovaCrates.join(stringArray, 2) : "&#B895EC&l" + string3.toUpperCase(Locale.ROOT) + " CRATE";
                crate.keyMat = CANDLES[this.crates.size() % CANDLES.length];
                this.crates.put(string3, crate);
                this.saveCrates();
                commandSender.sendMessage(this.pre("&7Made the " + this.disp(crate) + " &7crate. Look at a block and do &#B895EC/crate set " + string3 + "&7, then &#B895EC/crate &7to add prizes."));
                return true;
            }
            case "delete": {
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                for (Location location : crate.locs) {
                    Parts parts = this.parts.remove(NovaCrates.key(location));
                    if (parts != null) {
                        this.removeParts(parts);
                    }
                    this.restoreShulker(crate, location);
                }
                this.crates.remove(crate.id);
                this.saveCrates();
                commandSender.sendMessage(this.pre("&7Deleted " + this.disp(crate) + "&7."));
                return true;
            }
            case "rename": {
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                if (stringArray.length < 3) {
                    commandSender.sendMessage(this.pre("&7Usage: &#B895EC/crate rename <crate> <display name>"));
                    return true;
                }
                crate.display = NovaCrates.join(stringArray, 2);
                this.saveCrates();
                this.refreshHolos(crate);
                commandSender.sendMessage(this.pre("&7Renamed to " + this.disp(crate) + "&7."));
                return true;
            }
            case "set": {
                if (stringArray.length > 1 && (stringArray[1].equalsIgnoreCase("rarity") || stringArray[1].equalsIgnoreCase("stars"))) {
                    Crate crate = this.arg(commandSender, stringArray, 2);
                    if (crate != null) {
                        this.setRarity(commandSender, crate, stringArray.length > 3 ? stringArray[3] : "0");
                    }
                    return true;
                }
                if (!(commandSender instanceof Player)) {
                    return true;
                }
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                Block block = ((Player)commandSender).getTargetBlockExact(6);
                if (block == null || block.getType().name().equals("AIR")) {
                    commandSender.sendMessage(this.pre("&cLook at the block you want to be the crate."));
                    return true;
                }
                Crate crate2 = this.crateAt(block);
                if (crate2 != null) {
                    commandSender.sendMessage(this.pre("&cThat block is already the " + this.disp(crate2) + " &ccrate."));
                    return true;
                }
                crate.locs.add(block.getLocation());
                this.saveCrates();
                this.refreshHolos(crate);
                commandSender.sendMessage(this.pre("&7That block is now the " + this.disp(crate) + " &7crate. &8(sneak + break it to remove it)"));
                return true;
            }
            case "key": 
            case "givekey": {
                if (stringArray.length < 3) {
                    commandSender.sendMessage(this.pre("&7Usage: &#B895EC/crate key <player> <crate> [amount]"));
                    return true;
                }
                Player player = Bukkit.getPlayerExact((String)stringArray[1]);
                if (player == null) {
                    commandSender.sendMessage(this.pre("&c" + stringArray[1] + " isn't online."));
                    return true;
                }
                Crate crate = this.arg(commandSender, stringArray, 2);
                if (crate == null) {
                    return true;
                }
                int n = stringArray.length > 3 ? NovaCrates.parse(stringArray[3]) : 1;
                boolean bl = stringArray.length > 4 && stringArray[4].toLowerCase(Locale.ROOT).startsWith("v");
                this.giveKeys(player, crate, n, bl);
                commandSender.sendMessage(this.pre("&7Gave &f" + player.getName() + " " + n + "x " + this.disp(crate) + " &7key" + (n == 1 ? "" : "s") + "&7."));
                return true;
            }
            case "daily": {
                if (stringArray.length < 2) {
                    boolean bl = this.getConfig().getBoolean("daily-keyall.enabled", false);
                    commandSender.sendMessage(this.pre((String)(bl ? "&7Daily key: &f" + this.getConfig().getInt("daily-keyall.amount", 1) + "x " + this.getConfig().getString("daily-keyall.crate", "?") + " &7at &f" + this.getConfig().getString("daily-keyall.time", "18:00") + " &7to everyone online." : "&7Daily key is &coff&7.")));
                    commandSender.sendMessage(this.pre("&7Usage: &#B895EC/crate daily <crate> [amount] [time like 18:00] &7or &#B895EC/crate daily off"));
                    return true;
                }
                if (stringArray[1].equalsIgnoreCase("off")) {
                    this.getConfig().set("daily-keyall.enabled", (Object)false);
                    this.saveConfig();
                    commandSender.sendMessage(this.pre("&7Daily key turned &coff&7."));
                    return true;
                }
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                int n = stringArray.length > 2 ? Math.max(1, NovaCrates.parse(stringArray[2])) : 1;
                String string4 = stringArray.length > 3 ? stringArray[3] : this.getConfig().getString("daily-keyall.time", "18:00");
                try {
                    string4 = LocalTime.parse((CharSequence)(string4.length() == 4 ? "0" + string4 : string4)).toString();
                }
                catch (Throwable throwable) {
                    commandSender.sendMessage(this.pre("&cTime has to look like &f18:00&c."));
                    return true;
                }
                this.getConfig().set("daily-keyall.enabled", (Object)true);
                this.getConfig().set("daily-keyall.crate", (Object)crate.id);
                this.getConfig().set("daily-keyall.amount", (Object)n);
                this.getConfig().set("daily-keyall.time", (Object)string4);
                this.saveConfig();
                commandSender.sendMessage(this.pre("&7Every day at &f" + string4 + " &7(UK time) everyone online gets &f" + n + "x " + this.disp(crate) + " &fKey&7."));
                return true;
            }
            case "keyall": {
                if (stringArray.length < 2) {
                    commandSender.sendMessage(this.pre("&7Usage: &#B895EC/crate keyall <crate> [amount]"));
                    return true;
                }
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                int n = stringArray.length > 2 ? NovaCrates.parse(stringArray[2]) : 1;
                boolean bl = stringArray.length > 3 && stringArray[3].toLowerCase(Locale.ROOT).startsWith("v");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    this.giveKeys(player, crate, n, bl);
                }
                commandSender.sendMessage(this.pre("&7Gave everyone &f" + n + "x " + this.disp(crate) + " &7key" + (n == 1 ? "" : "s") + "&7."));
                return true;
            }
            case "resetkeys": {
                if (stringArray.length < 2 || !stringArray[1].equalsIgnoreCase("confirm")) {
                    commandSender.sendMessage(this.pre("&cThis takes EVERY key from EVERYONE. Type &f/crate resetkeys confirm"));
                    return true;
                }
                int n = this.vkeys.size();
                this.vkeys.clear();
                int n2 = 0;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (Crate crate : this.crates.values()) {
                        n2 += this.takeKeys(player.getUniqueId(), player, crate, Integer.MAX_VALUE);
                    }
                }
                this.saveKeys();
                commandSender.sendMessage(this.pre("&7Reset keys for &f" + n + " &7players" + (String)(n2 > 0 ? " &8(+" + n2 + " key items taken from online players)" : "") + "&7."));
                return true;
            }
            case "takekey": {
                boolean bl;
                Object object;
                String string5;
                UUID uUID;
                if (stringArray.length < 3) {
                    commandSender.sendMessage(this.pre("&7Usage: &#B895EC/crate takekey <player> <crate|all> [amount|all]"));
                    return true;
                }
                Player player = Bukkit.getPlayerExact((String)stringArray[1]);
                if (player != null) {
                    uUID = player.getUniqueId();
                    string5 = player.getName();
                } else {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(stringArray[1]);
                    if (!op.hasPlayedBefore()) {
                        commandSender.sendMessage(this.pre("&c" + stringArray[1] + " has never joined."));
                        return true;
                    }
                    uUID = op.getUniqueId();
                    string5 = op.getName() == null ? stringArray[1] : op.getName();
                }
                if (stringArray[2].equalsIgnoreCase("all")) {
                    int n = 0;
                    for (Crate crate : this.crates.values()) {
                        n += this.takeKeys(uUID, player, crate, Integer.MAX_VALUE);
                    }
                    commandSender.sendMessage(this.pre("&7Took all keys from &f" + string5 + " &8(" + n + ")"));
                    return true;
                }
                object = this.arg(commandSender, stringArray, 2);
                if (object == null) {
                    return true;
                }
                boolean bl2 = bl = stringArray.length > 3 && stringArray[3].equalsIgnoreCase("all");
                int n = bl ? Integer.MAX_VALUE : (stringArray.length > 3 ? NovaCrates.parse(stringArray[3]) : 1);
                int n3 = this.takeKeys(uUID, player, (Crate)object, n);
                commandSender.sendMessage(this.pre("&7Took &f" + n3 + " " + this.disp((Crate)object) + " &7key" + (n3 == 1 ? "" : "s") + " from &f" + string5 + "&7."));
                return true;
            }
            case "rarity": 
            case "stars": {
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                this.setRarity(commandSender, crate, stringArray.length > 2 ? stringArray[2] : "0");
                return true;
            }
            case "subtitle": {
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate == null) {
                    return true;
                }
                crate.subtitle = stringArray.length > 2 ? NovaCrates.join(stringArray, 2) : null;
                this.saveCrates();
                this.refreshHolos(crate);
                commandSender.sendMessage(this.pre("&7Subtitle set."));
                return true;
            }
            case "edit": {
                if (!(commandSender instanceof Player)) {
                    return true;
                }
                Crate crate = this.arg(commandSender, stringArray, 1);
                if (crate != null) {
                    this.openEditor((Player)commandSender, crate);
                }
                return true;
            }
        }
        this.help(commandSender);
        return true;
    }

    private void help(CommandSender commandSender) {
        commandSender.sendMessage(this.pre("&#B895EC&lCrates"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate &7- edit crates (GUI)"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate create <name> [display] &7- new crate"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate set <crate> &7- the block you're looking at becomes that crate"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate key <player> <crate> [amount] &7- give keys"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate keyall <crate> [amount] &7- keys for everyone online"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate takekey <player> <crate|all> [amount|all] &7- take keys"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate resetkeys confirm &7- everyone's keys back to 0"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate rename <crate> <display> &7| &#B895EC/crate delete <crate>"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate subtitle <crate> <text>"));
        commandSender.sendMessage(NovaCrates.color("&#B895EC/crate rarity <crate> <4.5/5> &7- the stars under the crate"));
    }

    private Crate arg(CommandSender commandSender, String[] stringArray, int n) {
        if (stringArray.length <= n) {
            commandSender.sendMessage(this.pre("&cSay which crate. &7Crates: &f" + String.join((CharSequence)"&7, &f", this.crates.keySet())));
            return null;
        }
        Crate crate = this.crates.get(stringArray[n].toLowerCase(Locale.ROOT));
        if (crate == null) {
            commandSender.sendMessage(this.pre("&cNo crate called &f" + stringArray[n] + "&c. &7Crates: &f" + String.join((CharSequence)"&7, &f", this.crates.keySet())));
        }
        return crate;
    }

    private void setRarity(CommandSender commandSender, Crate crate, String string) {
        double d;
        String[] stringArray = string.split("/");
        int n = stringArray.length > 1 ? Math.max(1, Math.min(10, NovaCrates.parse0(stringArray[1]))) : 5;
        try {
            d = Double.parseDouble(stringArray[0].replace(',', '.'));
        }
        catch (NumberFormatException numberFormatException) {
            d = 0.0;
        }
        crate.stars = d = Math.max(0.0, Math.min((double)n, (double)Math.round(d * 2.0) / 2.0));
        crate.starsMax = n;
        this.saveCrates();
        this.refreshHolos(crate);
        commandSender.sendMessage(this.pre("&7" + this.disp(crate) + " &7rarity: " + this.starLine(d, n) + " &8(" + NovaCrates.fmt(d) + "/" + n + ")"));
    }

    String starLine(double d, int n) {
        int n2;
        int n3 = (int)Math.floor(d);
        boolean bl = d - (double)n3 >= 0.5;
        StringBuilder stringBuilder = new StringBuilder("&6");
        for (n2 = 0; n2 < n3; ++n2) {
            stringBuilder.append('\u2605');
        }
        if (bl) {
            stringBuilder.append(this.getConfig().getString("half-star-colour", "&#A0762A")).append('\u2605');
        }
        if ((n2 = n - n3 - (bl ? 1 : 0)) > 0) {
            stringBuilder.append("&8");
            for (int i = 0; i < n2; ++i) {
                stringBuilder.append('\u2605');
            }
        }
        return stringBuilder.toString();
    }

    int takeKeys(UUID uUID, Player player, Crate crate, int n) {
        int n2 = 0;
        int n3 = this.vkeys(uUID, crate.id);
        int n4 = Math.min(n3, n);
        if (n4 > 0) {
            this.addVkeys(uUID, crate.id, -n4);
            n2 += n4;
        }
        if (player != null && n2 < n) {
            ItemStack[] itemStackArray = player.getInventory().getContents();
            for (int i = 0; i < itemStackArray.length && n2 < n; ++i) {
                ItemStack itemStack = itemStackArray[i];
                if (itemStack == null || !crate.id.equals(this.keyOf(itemStack))) continue;
                int n5 = Math.min(itemStack.getAmount(), n - n2);
                n2 += n5;
                if (n5 >= itemStack.getAmount()) {
                    player.getInventory().setItem(i, null);
                    continue;
                }
                itemStack.setAmount(itemStack.getAmount() - n5);
            }
            player.updateInventory();
        }
        return n2;
    }

    private static int parse0(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }

    private static int parse(String string) {
        try {
            return Math.max(1, Math.min(10000, Integer.parseInt(string)));
        }
        catch (NumberFormatException numberFormatException) {
            return 1;
        }
    }

    private static String join(String[] stringArray, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = n; i < stringArray.length; ++i) {
            if (i > n) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(stringArray[i]);
        }
        return stringBuilder.toString();
    }

    public List<String> tabCompleteCrate(CommandSender commandSender, String[] stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (!commandSender.hasPermission("novacrates.admin")) {
            return arrayList;
        }
        String string2 = stringArray.length == 0 ? "" : stringArray[stringArray.length - 1].toLowerCase(Locale.ROOT);
        ArrayList<String> arrayList2 = new ArrayList<String>();
        if (stringArray.length == 1) {
            for (String string3 : new String[]{"create", "set", "key", "keyall", "daily", "takekey", "edit", "rename", "subtitle", "rarity", "delete"}) {
                arrayList2.add(string3);
            }
        } else {
            boolean bl;
            String string4 = stringArray[0].toLowerCase(Locale.ROOT);
            boolean bl2 = bl = string4.equals("key") || string4.equals("givekey") || string4.equals("takekey");
            if (stringArray.length == 2 && bl) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    arrayList2.add(player.getName());
                }
            } else if (stringArray.length == 2 && !bl && !string4.equals("create") || stringArray.length == 3 && bl) {
                if (string4.equals("takekey")) {
                    arrayList2.add("all");
                }
                if (string4.equals("daily")) {
                    arrayList2.add("off");
                }
                arrayList2.addAll(this.crates.keySet());
            } else if (stringArray.length == 4 && string4.equals("daily")) {
                arrayList2.add("12:00");
                arrayList2.add("18:00");
                arrayList2.add("20:00");
            } else if (stringArray.length == 3 && (string4.equals("keyall") || string4.equals("daily")) || stringArray.length == 4 && bl) {
                if (string4.equals("takekey")) {
                    arrayList2.add("all");
                }
                arrayList2.add("1");
                arrayList2.add("5");
                arrayList2.add("10");
            }
        }
        for (String string5 : arrayList2) {
            if (!string5.toLowerCase(Locale.ROOT).startsWith(string2)) continue;
            arrayList.add(string5);
        }
        return arrayList;
    }

    private void load() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File)this.cratesFile);
        ConfigurationSection configurationSection2 = yamlConfiguration.getConfigurationSection("crates");
        if (configurationSection2 != null) {
            for (String object2 : configurationSection2.getKeys(false)) {
                ConfigurationSection object3 = configurationSection2.getConfigurationSection(object2);
                if (object3 == null) continue;
                Crate crate = new Crate();
                crate.id = object2;
                crate.display = object3.getString("display", "&#B895EC&l" + NovaCrates.cap(object2));
                crate.keyMat = object3.getString("key-material", "PURPLE_CANDLE");
                crate.subtitle = object3.getString("subtitle", null);
                crate.stars = object3.getDouble("stars", 0.0);
                crate.starsMax = (int)object3.getLong("stars-max", 5L);
                ConfigurationSection configurationSection = object3.getConfigurationSection("items");
                if (configurationSection != null) {
                    for (int i = 0; i < 7; ++i) {
                        ConfigurationSection string = configurationSection.getConfigurationSection(String.valueOf(i));
                        if (string == null) continue;
                        crate.items[i] = NovaCrates.strip(string.getItemStack("item"));
                        crate.chance[i] = string.getDouble("chance", 10.0);
                        crate.virtual[i] = string.getBoolean("virtual", false);
                    }
                }
                for (String string : object3.getStringList("locations")) {
                    World world;
                    String[] stringArray = string.split(";");
                    if (stringArray.length < 4 || (world = Bukkit.getWorld(stringArray[0])) == null) continue;
                    try {
                        Location location = new Location(world, (double)Integer.parseInt(stringArray[1]), (double)Integer.parseInt(stringArray[2]), (double)Integer.parseInt(stringArray[3]), 0.0f, 0.0f);
                        crate.locs.add(location);
                        if (stringArray.length <= 4) continue;
                        crate.looks.put(NovaCrates.key(location), stringArray[4]);
                    }
                    catch (NumberFormatException numberFormatException) {}
                }
                this.crates.put(object2, crate);
            }
        }
        YamlConfiguration yamlConfiguration2 = YamlConfiguration.loadConfiguration((File)this.keysFile);
        for (String object3 : yamlConfiguration2.getKeys(false)) {
            ConfigurationSection object = yamlConfiguration2.getConfigurationSection(object3);
            if (object == null) continue;
            try {
                Map<String, Integer> keyMap = new HashMap<String, Integer>();
                for (String string : object.getKeys(false)) {
                    int n = (int)object.getLong(string, 0L);
                    if (n <= 0) continue;
                    keyMap.put(string, n);
                }
                this.vkeys.put(UUID.fromString(object3), keyMap);
            }
            catch (IllegalArgumentException illegalArgumentException) {}
        }
    }

    void saveCrates() {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        for (Crate crate : this.crates.values()) {
            String string = "crates." + crate.id;
            yamlConfiguration.set(string + ".display", (Object)crate.display);
            yamlConfiguration.set(string + ".key-material", (Object)crate.keyMat);
            if (crate.subtitle != null) {
                yamlConfiguration.set(string + ".subtitle", (Object)crate.subtitle);
            }
            yamlConfiguration.set(string + ".stars", (Object)crate.stars);
            yamlConfiguration.set(string + ".stars-max", (Object)crate.starsMax);
            for (int i = 0; i < 7; ++i) {
                if (crate.items[i] == null) continue;
                yamlConfiguration.set(string + ".items." + i + ".item", (Object)crate.items[i]);
                yamlConfiguration.set(string + ".items." + i + ".chance", (Object)crate.chance[i]);
                yamlConfiguration.set(string + ".items." + i + ".virtual", (Object)crate.virtual[i]);
            }
            ArrayList<CallSite> arrayList = new ArrayList<CallSite>();
            for (Location location : crate.locs) {
                if (location.getWorld() == null) continue;
                String string2 = crate.looks.get(NovaCrates.key(location));
                arrayList.add((CallSite)((Object)(location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + (String)(string2 == null ? "" : ";" + string2))));
            }
            yamlConfiguration.set(string + ".locations", arrayList);
        }
        try {
            yamlConfiguration.save(this.cratesFile);
        }
        catch (IOException iOException) {
            this.getLogger().warning("Could not save crates.yml: " + iOException.getMessage());
        }
    }

    void saveKeys() {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        for (Map.Entry<UUID, Map<String, Integer>> entry : this.vkeys.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                if (entry2.getValue() <= 0) continue;
                yamlConfiguration.set(String.valueOf(entry.getKey()) + "." + entry2.getKey(), (Object)entry2.getValue());
            }
        }
        try {
            yamlConfiguration.save(this.keysFile);
        }
        catch (IOException iOException) {
            this.getLogger().warning("Could not save keys.yml: " + iOException.getMessage());
        }
    }

    static Material mat(String string, Material material) {
        Material material2 = string == null ? null : Material.matchMaterial((String)string);
        return material2 != null ? material2 : material;
    }

    String keyByName(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return null;
        }
        String string = itemMeta.getDisplayName().replaceAll("(?i)\u00a7[0-9a-fk-orx]", "").toLowerCase(Locale.ROOT).trim();
        if (!string.endsWith("key") && !string.endsWith("keys")) {
            return null;
        }
        string = string.replaceAll("\\s*keys?$", "").replaceAll("\\s*crate$", "").trim();
        for (Crate crate : this.crates.values()) {
            String string2 = this.disp(crate).replaceAll("(?i)[\u00a7&][0-9a-fk-orx]", "").replaceAll("(?i)&#[0-9a-f]{6}", "").toLowerCase(Locale.ROOT).replaceAll("\\s*crate$", "").trim();
            if (!string.equals(crate.id) && !string.equals(string2)) continue;
            return crate.id;
        }
        return null;
    }

    static String nameOf(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            return itemMeta.getDisplayName();
        }
        String string = itemStack.getType().name().toLowerCase(Locale.ROOT).replace('_', ' ');
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : string.split(" ")) {
            if (string2.isEmpty()) continue;
            if (stringBuilder.length() > 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(Character.toUpperCase(string2.charAt(0))).append(string2.substring(1));
        }
        return stringBuilder.toString();
    }

    static String cap(String string) {
        return string.isEmpty() ? string : Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    static String fmt(double d) {
        if (Math.abs(d - (double)Math.round(d)) < 0.001) {
            return String.valueOf(Math.round(d));
        }
        return String.format(Locale.ROOT, "%.1f", d);
    }

    static void sound(Player player, String string, float f, float f2) {
        try {
            player.playSound(player.getLocation(), string, f, f2);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    String pre(String string) {
        return NovaCrates.color(this.getConfig().getString("prefix", "&#00d2ff&lNOVACRATES &8\u25cf &f") + string);
    }

    static String color(String string) {
        if (string == null) {
            return "";
        }
        Matcher matcher = HEX.matcher(string);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            StringBuilder hexBuf = new StringBuilder("\u00a7x");
            for (char c : matcher.group(1).toLowerCase().toCharArray()) {
                hexBuf.append('\u00a7').append(c);
            }
            matcher.appendReplacement(stringBuilder, Matcher.quoteReplacement(hexBuf.toString()));
        }
        matcher.appendTail(stringBuilder);
        char[] chars = stringBuilder.toString().toCharArray();
        for (int i = 0; i < chars.length - 1; ++i) {
            if (chars[i] != '&' || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(chars[i + 1]) <= -1) continue;
            chars[i] = '\u00a7';
            chars[i + 1] = Character.toLowerCase(chars[i + 1]);
        }
        return new String(chars);
    }

    private void initShulkerDisplay(Material material, float f, float f2, String string, BlockDisplay blockDisplay) {
        blockDisplay.addScoreboardTag("novacrates_holo");
        blockDisplay.setPersistent(false);
        blockDisplay.setBlock(Bukkit.createBlockData((Material)material));
        NovaCrates.visible((Display)blockDisplay);
        blockDisplay.setTransformation(new Transformation(new Vector3f(f - 0.5f, f, f - 0.5f), new Quaternionf(), new Vector3f(f2, f2, f2), new Quaternionf()));
        if (this.getConfig().getBoolean("glow", false)) {
            blockDisplay.setGlowing(true);
            blockDisplay.setGlowColorOverride(NovaCrates.glowFor(string));
        }
    }

    private static void initInnerDisplay(Material material, float f, float f2, BlockDisplay blockDisplay) {
        blockDisplay.addScoreboardTag("novacrates_holo");
        blockDisplay.setPersistent(false);
        blockDisplay.setBlock(Bukkit.createBlockData((Material)material));
        NovaCrates.visible((Display)blockDisplay);
        blockDisplay.setTransformation(new Transformation(new Vector3f(f - 0.5f, f, f - 0.5f), new Quaternionf(), new Vector3f(f2, f2, f2), new Quaternionf()));
    }

    static final class Crate {
        String id;
        String display;
        String keyMat = "PURPLE_CANDLE";
        String subtitle = null;
        double stars = 0.0;
        int starsMax = 5;
        final ItemStack[] items = new ItemStack[7];
        final double[] chance = new double[]{10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
        final boolean[] virtual = new boolean[7];
        final List<Location> locs = new ArrayList<Location>();
        final Map<String, String> looks = new HashMap<String, String>();

        Crate() {
        }

        double total() {
            double d = 0.0;
            for (int i = 0; i < 7; ++i) {
                if (this.items[i] == null) continue;
                d += this.chance[i];
            }
            return d;
        }

        double realPercent(int n) {
            double d = this.total();
            return this.items[n] == null || d <= 0.0 ? 0.0 : this.chance[n] * 100.0 / d;
        }

        boolean empty() {
            for (ItemStack itemStack : this.items) {
                if (itemStack == null) continue;
                return false;
            }
            return true;
        }
    }

    static abstract class Menu
    implements InventoryHolder {
        Inventory inv;

        Menu() {
        }

        public Inventory getInventory() {
            return this.inv;
        }
    }

    static final class Spin
    extends Menu {
        Player p;
        Crate crate;
        int prize;
        int[] reel;
        int offset;
        int steps;
        int step;
        int wait;
        boolean landed;
        boolean given;
        boolean toldClosed;
        int closeIn = -1;

        Spin() {
        }
    }

    static final class Parts {
        Crate crate;
        Location anchor;
        String blockType;
        BlockDisplay shulker;
        BlockDisplay inner;
        Interaction hit;
        TextDisplay text;
        final Map<UUID, TextDisplay> mine = new HashMap<UUID, TextDisplay>();
        final Map<UUID, String> lastText = new HashMap<UUID, String>();

        Parts() {
        }
    }

    static final class ListMenu
    extends Menu {
        final List<String> ids = new ArrayList<String>();

        ListMenu() {
        }
    }

    static final class EditMenu
    extends Menu {
        Crate crate;

        EditMenu() {
        }
    }

    static final class ChanceMenu
    extends Menu {
        Crate crate;
        int index;

        ChanceMenu() {
        }
    }

    static final class PreviewMenu
    extends Menu {
        PreviewMenu() {
        }
    }

    static final class KeysMenu
    extends Menu {
        final Map<Integer, String> slots = new HashMap<Integer, String>();
        int close = -1;

        KeysMenu() {
        }
    }
}

