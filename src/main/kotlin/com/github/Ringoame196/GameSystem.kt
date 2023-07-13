package com.github.Ringoame196

import jdk.jfr.Event
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

class GameSystem {
    private var gameTask: BukkitTask? = null

    fun system(plugin: Plugin, player: Player, item_name: String) {
        PlayerSend().playsound(player, Sound.UI_BUTTON_CLICK)
        when (item_name) {
            "${ChatColor.AQUA}ゲームスタート" -> start(plugin, player)
            "${ChatColor.RED}終了" -> stop(plugin, player)
        }
    }

    fun start(plugin: Plugin, player: Player) {
        if (Data.DataManager.gameData.status) {
            player.sendMessage("${ChatColor.RED}既にゲームはスタートしています")
            return
        }
        Bukkit.broadcastMessage("${ChatColor.GREEN}攻防戦ゲームスタート！！")
        Data.DataManager.gameData.status = true
        gameTask = Bukkit.getScheduler().runTaskTimer(
            plugin,
            Runnable {
                if (Data.DataManager.gameData.status == false) { return@Runnable }
                Data.DataManager.gameData.time += 1
                Schedule(Data.DataManager.gameData.time)
            },
            0L, 20L
        )
    }

    fun stop(plugin: Plugin, player: Player) {
        if (!Data.DataManager.gameData.status) {
            player.sendMessage("${ChatColor.RED}ゲームは開始していません")
            return
        }
        Bukkit.broadcastMessage("${ChatColor.RED}攻防戦ゲーム強制終了！！")
        Data.DataManager.gameData.status = false
    }

    fun gameend() {
        Bukkit.broadcastMessage("${ChatColor.RED}攻防戦ゲーム終了！！")
        Data.DataManager.gameData.status = false
        gameTask?.cancel()
        gameTask = null
    }

    fun adventure(e: org.bukkit.event.Event, player: Player) {
        val team = GET().getTeamName(player)
        if (team != "red" && team != "blue") {
            return
        }
        if (player.gameMode == GameMode.CREATIVE) {
            return
        }
        if (e !is Cancellable) {
            return
        }
        e.isCancelled = true
    }

    fun Schedule(time: Int) {
        when (time) {
            30 -> PlayerSend().teammessage("${ChatColor.YELLOW}ゾンビ購入解放")
        }
    }
}
