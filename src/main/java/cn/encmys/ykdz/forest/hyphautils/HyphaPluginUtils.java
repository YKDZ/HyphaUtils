package cn.encmys.ykdz.forest.hyphautils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class HyphaPluginUtils {
    public static boolean isExist(@NotNull String pluginName) {
        return Bukkit.getPluginManager().getPlugin(pluginName) != null;
    }
}
