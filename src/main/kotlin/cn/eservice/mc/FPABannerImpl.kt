package cn.eservice.mc

import me.lucko.fabric.api.permissions.v0.PermissionCheckEvent
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.server.level.ServerPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.slf4j.LoggerFactory

object FPABannerImpl : ModInitializer {
    private val logger = LoggerFactory.getLogger("fpa-banner")

    override fun onInitialize() {
        logger.info("[FPA] Initialize FPA with Banner implement")
        PermissionCheckEvent.EVENT.register(this::onPermissionCheck)
        logger.info("[FPA] FPA Loaded")
    }

    fun onPermissionCheck(source: SharedSuggestionProvider, permission: String): TriState {
        var player: Player? = null
        if (source is CommandSourceStack) {
            if (source.isPlayer) {
                player = Bukkit.getPlayer(source.player!!.uuid)
            }
        } else if (source is ServerPlayer) {
            player = Bukkit.getPlayer(source.uuid)
        }
        if (player != null) {
            return if (player.hasPermission(permission)) TriState.TRUE
            else TriState.FALSE
        }
        logger.warn(
            "[FPA] Reached unreachable code!!! Please send feedback to developer."
        )
        logger.warn(
            "[FPA] Source type is: {}, permission is: {}",
            source.javaClass.name,
            permission
        )
        return TriState.DEFAULT
    }
}