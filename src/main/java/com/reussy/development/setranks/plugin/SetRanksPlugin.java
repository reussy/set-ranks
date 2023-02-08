package com.reussy.development.setranks.plugin;

import com.reussy.development.setranks.plugin.command.rank.RankCommand;
import com.reussy.development.setranks.plugin.command.rank.SetRankCommand;
import com.reussy.development.setranks.plugin.config.ConfigManager;
import com.reussy.development.setranks.plugin.config.MessageManager;
import com.reussy.development.setranks.plugin.controller.GroupController;
import com.reussy.development.setranks.plugin.integration.IPluginIntegration;
import com.reussy.development.setranks.plugin.integration.LuckPermsAPI;
import com.reussy.development.setranks.plugin.integration.PAPI;
import com.reussy.development.setranks.plugin.menu.element.ElementBuilder;
import com.reussy.development.setranks.plugin.utils.ExodusPluginStatus;
import com.reussy.development.setranks.plugin.utils.PluginStatus;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public class SetRanksPlugin extends JavaPlugin {

    private PluginStatus pluginStatus;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private ConfigManager rankMenuManager;
    private ElementBuilder elementBuilder;
    private LuckPermsAPI luckPermsAPI;
    private PAPI placeholderAPI;
    private GroupController groupController;

    @Override
    public void onEnable() {

        this.pluginStatus = new PluginStatus();

        this.configManager = new ConfigManager(this, null);
        this.messageManager = new MessageManager(this, null);
        this.rankMenuManager = new ConfigManager(this, "game-menus/rank-menu.yml");

        populateIntegrations(this.luckPermsAPI = new LuckPermsAPI(), this.placeholderAPI = new PAPI());

        this.elementBuilder = new ElementBuilder(this);

        this.groupController = new GroupController(this);

        populateCommands();
        this.pluginStatus.setStatus(ExodusPluginStatus.ENABLED);
    }

    @Override
    public void onDisable() {

    }

    public PluginStatus getPluginStatus() {
        return pluginStatus;
    }

    public LuckPermsAPI getLuckPermsAPI() {
        return luckPermsAPI;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public ConfigManager getRankMenuManager() {
        return rankMenuManager;
    }

    public PAPI getPlaceholderAPI() {
        return placeholderAPI;
    }

    public ElementBuilder getElementBuilder() {
        return elementBuilder;
    }

    public GroupController getGroupController() {
        return groupController;
    }

    private void populateIntegrations(IPluginIntegration... pluginIntegrations) {
        Stream.of(pluginIntegrations).forEach(IPluginIntegration::enable);
    }

    private void populateCommands() {
        new RankCommand(getConfigManager().get("commands", "rank"), this);
        new SetRankCommand(getConfigManager().get("commands", "set-rank"), this);
    }
}
