package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.bukkit.entity.Villager
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

class shop {
    fun open(e: PlayerInteractEntityEvent, player: Player, entity: Mob, team: String) {
        e.isCancelled = true
        Data.DataManager.teamDataMap[team]?.entities?.add(entity)
        if (Data.DataManager.teamDataMap[team]?.opening == true) {
            GUI(player)
        } else {
            unopened(player)
        }
    }
    fun system(item: ItemStack, player: Player) {
        if (item.type == Material.RED_STAINED_GLASS_PANE) {
            return
        }
        val item_name = item.itemMeta?.displayName
        val price = item.itemMeta?.lore?.get(0) // 値段取得
        if (!price!!.contains("p")) { return }
        if (!point().purchase(player, price)) { return }

        if (item_name?.contains("★")!!) {
            val set_team_name = GET().getTeamName(player) ?: return
            GUIClick().click_invocation(player, item_name, set_team_name)
        } else {
            val give_item = ItemStack(item)
            val meta = item.itemMeta
            meta?.lore = null
            give_item.setItemMeta(meta)
            player.inventory.addItem(give_item)
        }
    }
    fun GUI(player: Player) {
        val GUI = Bukkit.createInventory(null, 27, ChatColor.BLUE.toString() + "攻防戦ショップ[BATTLEGUI]")
        val point = Data.DataManager.playerDataMap.getOrPut(player.uniqueId) { PlayerData() }.point
        val GUIclass = GUI()

        GUIclass.set_GUIitem(GUI, 0, Material.EMERALD, "${ChatColor.GREEN}所持ポイント:" + point + "p", "")
        GUIclass.set_GUIitem(GUI, 1, Material.IRON_PICKAXE, "${ChatColor.YELLOW}ピッケル", "")
        GUIclass.set_GUIitem(GUI, 3, Material.IRON_SWORD, "${ChatColor.YELLOW}武器", "")
        GUIclass.set_GUIitem(GUI, 5, Material.IRON_CHESTPLATE, "${ChatColor.YELLOW}防具", "")
        GUIclass.set_GUIitem(GUI, 7, Material.TNT, "${ChatColor.YELLOW}お邪魔アイテム", "")
        GUIclass.set_GUIitem(GUI, 9, Material.ANVIL, "${ChatColor.YELLOW}金床", "エンチャント用")
        GUIclass.set_GUIitem(GUI, 10, Material.POTION, "${ChatColor.YELLOW}チーム強化", "")
        GUIclass.set_GUIitem(GUI, 12, Material.VILLAGER_SPAWN_EGG, "${ChatColor.YELLOW}村人強化", "")
        GUIclass.set_GUIitem(GUI, 14, Material.ZOMBIE_SPAWN_EGG, "${ChatColor.YELLOW}ゾンビ", "")
        GUIclass.set_GUIitem(GUI, 16, Material.BEACON, "${ChatColor.YELLOW}その他", "")
        GUIclass.set_GUIitem(GUI, 18, Material.CHEST, "${ChatColor.YELLOW}共通チェスト", "チーム共通")
        GUIclass.no_set(GUI, 19)
        GUIclass.no_set(GUI, 21)
        GUIclass.no_set(GUI, 23)
        GUIclass.no_set(GUI, 25)

        val time = Data.DataManager.gameData.time
        if (Data.DataManager.gameData.status && time <= 30) {
            GUIclass.set_GUIitem(GUI, 14, Material.BARRIER, "${ChatColor.RED}選択不可", "")
        }
        player.openInventory(GUI)
    }
    fun unopened(player: Player) {
        val GUI = Bukkit.createInventory(null, 9, "${ChatColor.DARK_GREEN}ショップ[BATTLEGUI]")
        GUI().set_GUIitem(GUI, 4, Material.GOLD_BLOCK, "${ChatColor.YELLOW}★ショップ解放", "10p")
        player.openInventory(GUI)
    }
    fun recovery(shop: Villager, amount: Double) {
        val maxHP = GET().getMaxHP(shop)
        val currentHP = shop.health + amount
        val newHP = maxHP?.let { if (currentHP >= it) it else currentHP }?.toString() ?: return
        name(shop, newHP, maxHP.toString())
    }
    fun name(shop: Villager, HP: String, maxHP: String) {
        shop.customName = "${ChatColor.RED}${HP}HP/${maxHP}HP"
    }
    fun attack(e: EntityDamageByEntityEvent, damager: Entity, shop: Villager) {
        if (damager is Player) {
            // プレイヤーが殴るのを禁止させる
            GameSystem().adventure(e, damager)
            if (damager.gameMode != GameMode.CREATIVE) { return }
        }
        // ダメージを受けたときにメッセージを出す
        val health = String.format("%.1f", shop.health - e.damage).toDouble()
        if (health <= 0) {
            return
        }
        val message = "${ChatColor.RED}ショップがダメージを食らっています (残りHP $health)"
        val maxHP = GET().getMaxHP(shop)
        name(shop, health.toString(), maxHP.toString())
        val blockBelow = shop.location.subtract(0.0, 1.0, 0.0).block.type
        var set_team_name = "red"
        if (blockBelow == Material.BLUE_WOOL) {
            set_team_name = "blue"
        }

        for (player in Bukkit.getServer().onlinePlayers) {
            val team_name = GET().getTeamName(player)
            if (team_name != set_team_name) {
                continue
            }
            player.sendMessage(message)
            PlayerSend().playsound(player, Sound.BLOCK_NOTE_BLOCK_BELL)
        }
    }
    fun summon(location: Location) {
        val Initial_HP = 100.0
        val world = location.world
        val villager: Villager = world!!.spawn(location, Villager::class.java)
        shop().name(villager, Initial_HP.toString(), Initial_HP.toString())
        villager.isCustomNameVisible = true
        villager.scoreboardTags.add("shop")
        villager.setAI(false)
        villager.isSilent = true

        val maxHPAttribute = villager.getAttribute(Attribute.GENERIC_MAX_HEALTH)
        maxHPAttribute?.baseValue = Initial_HP
        villager.health = Initial_HP
        villager.customName = "${ChatColor.RED}${villager.health}HP/${Initial_HP}HP" // カスタムネームの表示を更新

        // アーマースタンドを召喚
        location.add(0.0, 1.3, 0.0)
        val armorStand: ArmorStand = world.spawn(location, ArmorStand::class.java)

        // アーマースタンドの設定
        armorStand.isVisible = false // 可視化するかどうか
        armorStand.isSmall = true // サイズを小さくするかどうか
        armorStand.isInvulnerable = true
        armorStand.customName = "${ChatColor.GOLD}攻防戦ショップ"
        armorStand.isCustomNameVisible = true
        armorStand.setGravity(false)
    }
    fun delete_name(location: Location) {
        val world = location.world
        val nearbyEntities = world!!.getNearbyEntities(location, 0.0, 2.0, 0.0)
        for (armorStand in nearbyEntities.filterIsInstance<ArmorStand>()) {
            armorStand.remove()
            return
        }
    }
}
