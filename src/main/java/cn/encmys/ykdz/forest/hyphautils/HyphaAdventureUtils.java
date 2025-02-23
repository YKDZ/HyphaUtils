package cn.encmys.ykdz.forest.hyphautils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HyphaAdventureUtils {
    @NotNull
    public static List<Component> getComponentFromMiniMessage(@NotNull List<String> texts) {
        List<Component> result = new ArrayList<>();
        for (String text : texts) {
            result.add(getComponentFromMiniMessage(text));
        }
        return result;
    }

    @NotNull
    public static Component getComponentFromMiniMessage(@Nullable String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }
        return MiniMessage.miniMessage().deserialize(text).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String s) {
        if (sender instanceof Player player) sendPlayerMessage(player, s);
        else if (sender instanceof ConsoleCommandSender) sendConsoleMessage(s);
    }

    public static void sendPlayerMessage(@NotNull Player player, @NotNull String s) {
        player.sendMessage(getComponentFromMiniMessage(s));
    }

    public static void sendConsoleMessage(@NotNull String s) {
        Bukkit.getConsoleSender().sendMessage(getComponentFromMiniMessage(s));
    }

    public static void sendSound(@NotNull Player player, @NotNull Sound.Source source, @NotNull Key key, float volume, float pitch) {
        Sound sound = Sound.sound(key, source, volume, pitch);
        player.playSound(sound);
    }

    public static void sendSound(@NotNull Player player, @NotNull Sound sound) {
        player.playSound(sound);
    }

    @NotNull
    public static List<String> componentToLegacy(@NotNull List<Component> components) {
        List<String> result = new ArrayList<>();
        for (Component component : components) {
            result.add(componentToLegacy(component));
        }
        return result;
    }

    @NotNull
    public static String componentToLegacy(@NotNull Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component);
    }

    public static boolean isColorCode(char c) {
        return c == '§' || c == '&';
    }

    @NotNull
    public static String legacyToMiniMessage(@NotNull String legacy) {
        return MiniMessage.miniMessage().serialize(LegacyComponentSerializer.legacySection().deserialize(legacy));
    }
}
