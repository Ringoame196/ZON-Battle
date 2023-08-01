package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class point {
    fun add(player: Player, addpoint: Int) {
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        var point = GET().getpoint(player)
        point += addpoint
        player.sendMessage("${ChatColor.GREEN}+$addpoint (${point}ポイント)")
        Data.DataManager.playerDataMap[player.uniqueId]?.let { playerData ->
            playerData.point = point
        }
    }
    fun remove(player: Player, removepoint: Int) {
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        var point = GET().getpoint(player)
        point -= removepoint
        Data.DataManager.playerDataMap[player.uniqueId]?.let { playerData ->
            playerData.point = point
        }
    }
    fun ore(e: org.bukkit.event.Event, player: Player, block: Block, team: String, plugin: Plugin) {
        val block_type = block.type
        GameSystem().adventure(e, player)
        var cooltime = Data.DataManager.teamDataMap.getOrPut(team) { TeamData() }.blockTime
        val point: Int
        when (block_type) {
            Material.COAL_ORE -> { point = 1 }

            Material.IRON_ORE -> { point = 3 }

            Material.GOLD_ORE -> { point = 5 }

            Material.DIAMOND_ORE -> {
                point = 100
                cooltime = 7 // ダイヤモンドだけ別時間
            }

            else -> {
                return
            }
        }
        point().add(player, point)
        block.setType(Material.BEDROCK)
        // 復活
        Bukkit.getScheduler().runTaskLater(
            plugin,
            Runnable { block.setType(block_type) }, cooltime.toLong() * 20 // クールダウン時間をtick単位に変換
        )
    }
    fun purchase(player: Player, price: String): Boolean {
        val price_int: Int = price.replace("p", "").toInt()
        var possible = false
        val point = GET().getpoint(player)
        if (price_int > point) {
            player.sendMessage("${ChatColor.RED}" + (price_int - point) + "ポイント足りません")
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f)
            player.closeInventory()
        } else {
            player.playSound(player, Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f)
            remove(player, price_int)
            possible = true
        }

        return possible
    }
    fun fastbreaklevel(team_name: String, player: Player, item_name: String) {
        val set_time = Data.DataManager.teamDataMap.getOrPut(team_name) { TeamData() }.blockTime - 1
        Data.DataManager.teamDataMap[team_name]?.blockTime = set_time
        GUI().villagerlevelup(player.openInventory.topInventory, player)
        PlayerSend().TeamGiveEffect(player, item_name, null, null, 6 - set_time, 0)
    }
}
