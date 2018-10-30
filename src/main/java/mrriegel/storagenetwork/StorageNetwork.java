package mrriegel.storagenetwork;

import org.apache.logging.log4j.Logger;
import mrriegel.storagenetwork.config.ConfigHandler;
import mrriegel.storagenetwork.proxy.CommonProxy;
import mrriegel.storagenetwork.registry.RegistryEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = StorageNetwork.MODID, name = StorageNetwork.MODNAME, version = StorageNetwork.VERSION, updateJSON = "https://raw.githubusercontent.com/PrinceOfAmber/Storage-Network/master/update.json")
public class StorageNetwork {

  public Logger logger;
  public static final String MODID = "storagenetwork";
  public static final String MODNAME = "Simple Storage Network";
  public static final String VERSION = "@VERSION@";
  @Instance(StorageNetwork.MODID)
  public static StorageNetwork instance;
  @SidedProxy(clientSide = "mrriegel.storagenetwork.proxy.ClientProxy", serverSide = "mrriegel.storagenetwork.proxy.CommonProxy")
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    proxy.preInit(event);
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(new RegistryEvents());
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }

  public static void chatMessage(EntityPlayer player, String message) {
    if (player.world.isRemote)
      player.sendMessage(new TextComponentString(lang(message)));
  }

  public static void statusMessage(EntityPlayer player, String message) {
    if (player.world.isRemote)
      player.sendStatusMessage(new TextComponentString(lang(message)), true);
  }

  @SuppressWarnings("deprecation")
  public static String lang(String message) {
    return net.minecraft.util.text.translation.I18n.translateToLocal(message);
  }

  private static long lastTime;

  public static void log(String s) {
    if (ConfigHandler.logEverything) {
      instance.logger.info(s);
    }
  }

  public static void error(String s) {
    instance.error(s);
  }

  private static void benchmark(String s) {
    long now = System.currentTimeMillis();
    long DIFF = now - lastTime;
    lastTime = now;
    StorageNetwork.log(now
        + " [" + DIFF + "]" + " : " + s);
  }
}
