package com.mu.tpunlocked;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class TpUnlockedMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> registerCommands(dispatcher, registryAccess));
    }

    private void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(CommandManager.literal("tp")
            .then(CommandManager.argument("target", EntityArgumentType.player())
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "target");
                    player.requestTeleport(target.getX(), target.getY(), target.getZ());
                    player.sendMessage(Text.literal("§a你已被傳送到玩家 " + target.getName().getString()), false);
                    return 1;
                }))
            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    Vec3d pos = Vec3ArgumentType.getVec3(ctx, "pos");
                    player.requestTeleport(pos.x, pos.y, pos.z);
                    player.sendMessage(Text.literal("§a你已被傳送到指定座標！"), false);
                    return 1;
                }))
        );
    }
}
