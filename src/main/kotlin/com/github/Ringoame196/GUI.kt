package com.github.Ringoame196

import com.github.Ringoame196.data.Data
import com.github.Ringoame196.data.TeamData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class GUI {
    fun setGUIitem(GUI: Inventory, number: Int, set_item: Material, displayname: String, lore: String) {
        // GUIにアイテムを楽にセットする
        val item = ItemStack(set_item)
        val itemMeta: ItemMeta? = item.itemMeta
        itemMeta?.setDisplayName(displayname)
        lore(itemMeta, lore)
        itemMeta?.isUnbreakable = true // 不破壊
        item.setItemMeta(itemMeta)
        GUI.setItem(number, item)
    }
    @Suppress("DEPRECATION")
    fun set_playerHead(GUI: Inventory, number: Int, name: String, displayname: String, lore: String) {
        // GUIにアイテムを楽にセットする
        val item = ItemStack(Material.PLAYER_HEAD)
        val itemMeta = item.itemMeta as SkullMeta
        itemMeta.owningPlayer = Bukkit.getOfflinePlayer(name) // プレイヤー名で設定
        itemMeta.setDisplayName(displayname)
        lore(itemMeta, lore)
        itemMeta.isUnbreakable = true // 不破壊
        item.setItemMeta(itemMeta)
        GUI.setItem(number, item)
    }
    fun set_potionGUIitem(GUI: Inventory, number: Int, item: Material, lore: String, typePotion: PotionEffectType, level: Int, time: Int) {
        // GUIにポーションを楽にセットする
        val itemStack = ItemStack(item)
        val potionMeta = itemStack.itemMeta as PotionMeta
        lore(potionMeta, lore)

        val regenerationEffect = PotionEffect(typePotion, time * 20, level)
        potionMeta.addCustomEffect(regenerationEffect, true)
        potionMeta.setDisplayName("スプラッシュポーション")
        itemStack.setItemMeta(potionMeta)
        GUI.setItem(number, itemStack)
    }
    fun set_enchant_GUIitem(GUI: Inventory, number: Int, lore: String, enchant: Enchantment, level: Int) {
        // GUIにエンチャント本を楽にセットする
        val item = ItemStack(Material.ENCHANTED_BOOK)
        val itemMeta: ItemMeta = item.itemMeta!!
        itemMeta.setLore(listOf(lore))
        if (itemMeta is EnchantmentStorageMeta) {
            itemMeta.addStoredEnchant(enchant, level, true)
        }
        item.setItemMeta(itemMeta)
        GUI.setItem(number, item)
    }

    fun lore(meta: ItemMeta?, lore: String) {
        val loreList: MutableList<String> = mutableListOf(lore)
        if (meta?.displayName?.contains("★") == true) loreList.addAll(listOf("", "クリックで発動"))
        meta?.lore = loreList
    }

    fun no_set(GUI: Inventory, number: Int) {
        setGUIitem(GUI, number, Material.OAK_SIGN, "${ChatColor.YELLOW}近日公開", "")
    }

    fun pickaxeshop(GUI: Inventory, player: Player) {
        dividing_line(GUI, 9)
        setGUIitem(GUI, 0, Material.WOODEN_PICKAXE, "[ツール]木のピッケル", "1p")
        if (player.inventory.contains(Material.WOODEN_PICKAXE)) {
            setGUIitem(GUI, 0, Material.STONE_PICKAXE, "[ツール]石ピッケル", "5p")
        } else if (player.inventory.contains(Material.STONE_PICKAXE)) {
            setGUIitem(GUI, 0, Material.IRON_PICKAXE, "[ツール]鉄ピッケル", "40p")
        } else if (player.inventory.contains(Material.IRON_PICKAXE)) {
            setGUIitem(GUI, 0, Material.DIAMOND_PICKAXE, "[ツール]ダイヤモンドピッケル", "300p")
        } else if (player.inventory.contains(Material.DIAMOND_PICKAXE)) {
            setGUIitem(GUI, 0, Material.NETHERITE_PICKAXE, "[ツール]ネザライトピッケル", "5000p")
        } else if (player.inventory.contains(Material.NETHERITE_PICKAXE)) {
            setGUIitem(GUI, 0, Material.BARRIER, "${ChatColor.RED}選択不可", "")
        }
        set_enchant_GUIitem(GUI, 18, "5p", Enchantment.DIG_SPEED, 1)
        set_enchant_GUIitem(GUI, 19, "20p", Enchantment.DIG_SPEED, 2)
        set_enchant_GUIitem(GUI, 20, "300p", Enchantment.DIG_SPEED, 3)
        set_enchant_GUIitem(GUI, 21, "500p", Enchantment.DIG_SPEED, 4)
        set_enchant_GUIitem(GUI, 22, "5000p", Enchantment.DIG_SPEED, 5)
    }
    fun axeshop(GUI: Inventory, player: Player) {
        dividing_line(GUI, 9)
        setGUIitem(GUI, 0, Material.STONE_AXE, "[斧]石斧", "5p")
        if (player.inventory.contains(Material.STONE_AXE)) {
            setGUIitem(GUI, 0, Material.IRON_AXE, "[斧]鉄斧", "40p")
        } else if (player.inventory.contains(Material.IRON_AXE)) {
            setGUIitem(GUI, 0, Material.DIAMOND_AXE, "[斧]ダイヤモンド斧", "300p")
        } else if (player.inventory.contains(Material.DIAMOND_AXE)) {
            setGUIitem(GUI, 0, Material.NETHERITE_AXE, "[斧]ネザライト斧", "5000p")
        } else if (player.inventory.contains(Material.NETHERITE_AXE)) {
            setGUIitem(GUI, 0, Material.BARRIER, "${ChatColor.RED}選択不可", "")
        }
        set_enchant_GUIitem(GUI, 18, "5p", Enchantment.DIG_SPEED, 1)
        set_enchant_GUIitem(GUI, 19, "20p", Enchantment.DIG_SPEED, 2)
        set_enchant_GUIitem(GUI, 20, "300p", Enchantment.DIG_SPEED, 3)
        set_enchant_GUIitem(GUI, 21, "500p", Enchantment.DIG_SPEED, 4)
        set_enchant_GUIitem(GUI, 22, "5000p", Enchantment.DIG_SPEED, 5)
    }
    fun weaponshop(GUI: Inventory, player: Player) {
        dividing_line(GUI, 9)
        setGUIitem(GUI, 0, Material.WOODEN_SWORD, "[武器]木の剣", "1p")
        if (player.inventory.contains(Material.WOODEN_SWORD)) {
            setGUIitem(GUI, 0, Material.STONE_SWORD, "[武器]石の剣", "5p")
        } else if (player.inventory.contains(Material.STONE_SWORD)) {
            setGUIitem(GUI, 0, Material.IRON_SWORD, "[武器]鉄の剣", "20p")
        } else if (player.inventory.contains(Material.IRON_SWORD)) {
            setGUIitem(GUI, 0, Material.DIAMOND_SWORD, "[武器]ダイヤモンドの剣", "100p")
        } else if (player.inventory.contains(Material.DIAMOND_SWORD)) {
            setGUIitem(GUI, 0, Material.NETHERITE_SWORD, "[武器]ネザーライトの剣", "1500p")
        } else if (player.inventory.contains(Material.NETHERITE_SWORD)) {
            setGUIitem(GUI, 0, Material.BARRIER, "${ChatColor.RED}選択不可", "")
        }
        setGUIitem(GUI, 2, Material.BOW, "弓", "100p")
        setGUIitem(GUI, 3, Material.CROSSBOW, "クロスボー", "300p")
        setGUIitem(GUI, 4, Material.ARROW, "矢", "1p")
        set_enchant_GUIitem(GUI, 18, "40p", Enchantment.DAMAGE_ALL, 1)
        set_enchant_GUIitem(GUI, 19, "300p", Enchantment.DAMAGE_ALL, 2)
        set_enchant_GUIitem(GUI, 20, "600p", Enchantment.DAMAGE_ALL, 3)
        set_enchant_GUIitem(GUI, 27, "40p", Enchantment.DAMAGE_UNDEAD, 1)
        set_enchant_GUIitem(GUI, 28, "300p", Enchantment.DAMAGE_UNDEAD, 2)
        set_enchant_GUIitem(GUI, 29, "600p", Enchantment.DAMAGE_UNDEAD, 3)
    }
    fun equipmentshop(GUI: Inventory) {
        dividing_line(GUI, 18)
        setGUIitem(GUI, 0, Material.GOLDEN_CHESTPLATE, "[装備]金のチェストプレート", "100p")
        setGUIitem(GUI, 1, Material.GOLDEN_LEGGINGS, "[装備]金のレギンス", "100p")
        setGUIitem(GUI, 2, Material.GOLDEN_BOOTS, "[装備]金のブーツ", "100p")
        setGUIitem(GUI, 4, Material.DIAMOND_CHESTPLATE, "[装備]ダイヤモンドのチェストプレート", "2500p")
        setGUIitem(GUI, 5, Material.DIAMOND_LEGGINGS, "[装備]ダイヤモンドのレギンス", "2500p")
        setGUIitem(GUI, 6, Material.DIAMOND_BOOTS, "[装備]ダイヤモンドのブーツ", "1500p")
        setGUIitem(GUI, 9, Material.IRON_CHESTPLATE, "[装備]鉄のチェストプレート", "300p")
        setGUIitem(GUI, 10, Material.IRON_LEGGINGS, "[装備]鉄のレギンス", "300p")
        setGUIitem(GUI, 11, Material.IRON_BOOTS, "[装備]鉄のブーツ", "300p")
        setGUIitem(GUI, 13, Material.NETHERITE_CHESTPLATE, "[装備]ネザーライトのチェストプレート", "10000p")
        setGUIitem(GUI, 14, Material.NETHERITE_LEGGINGS, "[装備]ネザーライトのレギンス", "8000p")
        setGUIitem(GUI, 15, Material.NETHERITE_BOOTS, "[装備]ネザーライトのブーツ", "5000p")
        set_enchant_GUIitem(GUI, 27, "10p", Enchantment.PROTECTION_ENVIRONMENTAL, 1)
        set_enchant_GUIitem(GUI, 28, "50p", Enchantment.PROTECTION_ENVIRONMENTAL, 2)
        set_enchant_GUIitem(GUI, 29, "150p", Enchantment.PROTECTION_ENVIRONMENTAL, 3)
        set_enchant_GUIitem(GUI, 30, "250p", Enchantment.PROTECTION_ENVIRONMENTAL, 4)
    }
    fun potionshop(GUI: Inventory, player: Player) {
        player.openInventory(GUI)
        set_potionGUIitem(GUI, 0, Material.SPLASH_POTION, "15p", PotionEffectType.HEAL, 0, 1)
        set_potionGUIitem(GUI, 1, Material.SPLASH_POTION, "50p", PotionEffectType.HEAL, 1, 1)
        set_potionGUIitem(GUI, 2, Material.LINGERING_POTION, "300p", PotionEffectType.HEAL, 0, 1)
        dividing_line(GUI, 9)
        setGUIitem(GUI, 18, Material.RED_DYE, "${ChatColor.YELLOW}★チーム全員に攻撃力UP(3分)", "300p")
        setGUIitem(GUI, 19, Material.MAGENTA_DYE, "${ChatColor.YELLOW}★チーム全員に再生UP(3分)", "300p")
        setGUIitem(GUI, 20, Material.ORANGE_DYE, "${ChatColor.YELLOW}★チーム全員に採掘速度UP(5分)", "300p")
        setGUIitem(GUI, 21, Material.GRAY_DYE, "${ChatColor.YELLOW}★チーム全員に耐性(3分)", "300p")
        setGUIitem(GUI, 22, Material.LIGHT_BLUE_DYE, "${ChatColor.YELLOW}★チーム全員に移動速度UP(3分)", "300p")
        setGUIitem(GUI, 23, Material.NETHER_STAR, "${ChatColor.YELLOW}★チーム全員に攻撃力UP&再生(1分)", "1000p")
    }
    fun zombieshop(GUI: Inventory) {
        setGUIitem(GUI, 0, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]ノーマルゾンビ", "30p")
        setGUIitem(GUI, 1, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]チビゾンビ", "40p")
        setGUIitem(GUI, 2, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]シールドゾンビ", "40p")
        setGUIitem(GUI, 3, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]ゾンビソルジャー", "40p")
        setGUIitem(GUI, 4, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]タンクマン", "300p")
        setGUIitem(GUI, 5, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]ダッシュマン", "250p")
        setGUIitem(GUI, 6, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]スケルトンマン", "100p")
        setGUIitem(GUI, 7, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]シャーマン", "500p")
        setGUIitem(GUI, 8, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]ネクロマンサー", "400p")
        setGUIitem(GUI, 9, Material.SLIME_BALL, "${ChatColor.YELLOW}[召喚]エンペラー", "200p")
    }
    fun general_merchandiseshop(GUI: Inventory, player: Player) {
        player.openInventory(GUI)
        setGUIitem(GUI, 0, Material.EMERALD, "${ChatColor.GREEN}10p", "10p")
        setGUIitem(GUI, 1, Material.EMERALD, "${ChatColor.GREEN}100p", "100p")
        dividing_line(GUI, 9)
        setGUIitem(GUI, 18, Material.ZOMBIE_HEAD, "${ChatColor.GREEN}敵対されない帽子", "8000p")
        setGUIitem(GUI, 19, Material.GOLDEN_APPLE, "金リンゴ", "300p")
        setGUIitem(GUI, 20, Material.BONE, "${ChatColor.YELLOW}★狼召喚", "100p")
    }
    fun villagerlevelup(GUI: Inventory, player: Player) {
        val team_name = GET().TeamName(player) ?: return
        val level = 6 - Data.DataManager.teamDataMap.getOrPut(team_name) { TeamData() }.blockTime
        val shop = Data.DataManager.teamDataMap[team_name]?.entities?.lastOrNull()
        shop.let { entity ->
            val maxHealthAttribute = shop?.getAttribute(Attribute.GENERIC_MAX_HEALTH)
            val maxHealth = maxHealthAttribute?.value?.toInt() ?: 0
            setGUIitem(GUI, 1, Material.RED_DYE, "${ChatColor.YELLOW}★村人体力増加", maxHealth.toString() + "p")
        }
        val price = level * 200
        if (level < 5) {
            setGUIitem(GUI, 0, Material.GOLDEN_PICKAXE, "${ChatColor.YELLOW}★鉱石復活速度UP", price.toString() + "p")
        } else {
            setGUIitem(GUI, 0, Material.AIR, "", "")
        }
        setGUIitem(GUI, 2, Material.POTION, "${ChatColor.YELLOW}★村人耐久1(3分)", "300p")
        setGUIitem(GUI, 3, Material.POTION, "${ChatColor.YELLOW}★村人再生1(3分)", "300p")
        dividing_line(GUI, 9)
        setGUIitem(GUI, 18, Material.IRON_BLOCK, "${ChatColor.YELLOW}アイアンゴーレム", "500p")
        setGUIitem(GUI, 19, Material.GOLD_BLOCK, "${ChatColor.YELLOW}ゴールデンゴーレム", "1500p")
        setGUIitem(GUI, 20, Material.DIAMOND_BLOCK, "${ChatColor.YELLOW}ダイヤモンドゴーレム", "8000p")
        player.openInventory(GUI)
    }
    fun disturbshop(GUI: Inventory) {
        setGUIitem(GUI, 0, Material.BLACK_CANDLE, "${ChatColor.YELLOW}★盲目(10秒)[妨害]", "300p")
        setGUIitem(GUI, 1, Material.LIGHT_GRAY_CANDLE, "${ChatColor.YELLOW}★弱体化(10秒)[妨害]", "300p")
        setGUIitem(GUI, 2, Material.BROWN_CANDLE, "${ChatColor.YELLOW}★採掘速度低下(10秒)[妨害]", "300p")
    }

    fun dividing_line(GUI: Inventory, beginning: Int) {
        for (i in beginning..beginning + 8) {
            setGUIitem(GUI, i, Material.RED_STAINED_GLASS_PANE, "", "")
        }
    }
    fun gamesettingGUI(player: Player) {
        val GUI = Bukkit.createInventory(null, 18, "${ChatColor.DARK_GREEN}設定画面[BATTLEGUI]")
        if (GET().status()) {
            setGUIitem(GUI, 0, Material.BARRIER, "${ChatColor.RED}終了", "")
        } else {
            setGUIitem(GUI, 0, Material.EMERALD, "${ChatColor.AQUA}ゲームスタート", "")
        }
        setGUIitem(GUI, 1, Material.VILLAGER_SPAWN_EGG, "${ChatColor.YELLOW}ショップ召喚", "")
        val location = Data.DataManager.LocationData
        setGUIitem(GUI, 2, Material.ENDER_EYE, "${ChatColor.RED}shop", GET().locationTitle(location.redshop))
        setGUIitem(GUI, 3, Material.ENDER_EYE, "${ChatColor.BLUE}shop", GET().locationTitle(location.blueshop))
        setGUIitem(GUI, 4, Material.ENDER_EYE, "${ChatColor.RED}spawn", GET().locationTitle(location.redspawn))
        setGUIitem(GUI, 5, Material.ENDER_EYE, "${ChatColor.BLUE}spawn", GET().locationTitle(location.bluespawn))
        setGUIitem(GUI, 6, Material.ENDER_EYE, "${ChatColor.YELLOW}ランダムチェスト", GET().locationTitle(location.randomChest))
        setGUIitem(GUI, 7, Material.DIAMOND, "${ChatColor.GREEN}参加", "")
        setGUIitem(GUI, 8, Material.REDSTONE_BLOCK, "${ChatColor.RED}プラグインリロード", "")
        setGUIitem(GUI, 9, Material.PLAYER_HEAD, "${ChatColor.BLUE}プレイヤー", "")
        setGUIitem(GUI, 10, Material.COMMAND_BLOCK, "${ChatColor.YELLOW}実験所へ", "")
        setGUIitem(GUI, 11, Material.COMMAND_BLOCK, "${ChatColor.YELLOW}ロビーへ", "")
        player.openInventory(GUI)
    }
    fun JoinPlayers(player: Player) {
        val gui = Bukkit.createInventory(null, 18, "${ChatColor.DARK_GREEN}参加プレイヤー[BATTLEGUI]")
        var i = 0
        for (loopPlayer in Bukkit.getOnlinePlayers()) {
            if (Data.DataManager.gameData.ParticipatingPlayer.contains(loopPlayer)) {
                set_playerHead(gui, i, loopPlayer.name, loopPlayer.name, "${ChatColor.GREEN}参加済み")
            } else {
                set_playerHead(gui, i, loopPlayer.name, loopPlayer.name, "${ChatColor.RED}未参加")
            }
            i++
            if (i == 18) { return }
        }
        player.openInventory(gui)
    }
    fun close(title: String, player: Player, inventory: Inventory) {
        when (title) {
            "${ChatColor.DARK_GREEN}チームチェスト" -> player.playSound(player, Sound.BLOCK_CHEST_CLOSE, 1f, 1f)
            "${ChatColor.DARK_GREEN}金床" -> anvil().close(player, inventory)
        }
    }
}
