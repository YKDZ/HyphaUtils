package cn.encmys.ykdz.forest.hyphautils.utils;

import org.jetbrains.annotations.NotNull;

public class HyphaLogUtils {
    public static void info(@NotNull String prefix, @NotNull String log) {
        HyphaAdventureUtils.sendConsoleMessage(prefix + " " + log);
    }

    public static void warn(@NotNull String prefix, @NotNull String log) {
        HyphaAdventureUtils.sendConsoleMessage(prefix + " <yellow>" + log);
    }

    public static void error(@NotNull String prefix, @NotNull String log) {
        HyphaAdventureUtils.sendConsoleMessage(prefix + " <red>" + log);
    }
}
