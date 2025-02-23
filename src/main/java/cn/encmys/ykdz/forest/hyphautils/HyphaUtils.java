package cn.encmys.ykdz.forest.hyphautils;

import org.bukkit.plugin.java.JavaPlugin;

public class HyphaUtils extends JavaPlugin {
    public static HyphaUtils INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }
}
