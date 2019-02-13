package fr.koadev.github;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.textured.STexturedProgressBar;
import java.io.File;

public class Launcher
{
  public static final GameVersion AW_VER = new GameVersion("1.7.10", GameType.V1_7_10);
  public static final GameInfos AW_INF = new GameInfos("KoaLife", AW_VER, new GameTweak[] { GameTweak.FORGE });
  public static final File AW_DIR = AW_INF.getGameDir();
  public static final File AW_CRASH_DIR = new File(AW_DIR, "crash(s)");
  private static AuthInfos authInfos;
  private static Thread updateThread;
  private static CrashReporter crashReporter = new CrashReporter("KoaLife", AW_CRASH_DIR);
  private RamSelector ramSelector = new RamSelector(new File(AW_DIR, "ram.txt"));
  
  public static void auth(String usernameField)
    throws AuthenticationException
  {
    authInfos = new AuthInfos(usernameField, "sry", "nope");
  }
  
  public static void update()
    throws Exception
  {
    SUpdate su = new SUpdate("http://notifia.fr:82/craftforlife/", AW_DIR);
    su.addApplication(new FileDeleter());
    
    updateThread = new Thread()
    {
      private int val;
      private int max;
      
      public void run()
      {
        while (!isInterrupted()) {
          if (BarAPI.getNumberOfFileToDownload() == 0)
          {
            LauncherFrame.getInstance().getLauncherPanel().setInfoText("Chargement..");
          }
          else
          {
            this.val = ((int)(BarAPI.getNumberOfTotalDownloadedBytes() / 1000L));
            this.max = ((int)(BarAPI.getNumberOfTotalBytesToDownload() / 1000L));
            
            LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(this.max);
            LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(this.val);
            
            LauncherFrame.getInstance().getLauncherPanel().setInfoText(Swinger.percentage(
              this.val, this.max) + "%");
          }
        }
      }
    };
    updateThread.start();
    
    su.start();
    updateThread.interrupt();
  }
  
  public static void launch()
    throws LaunchException
  {
    ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(AW_INF, GameFolder.BASIC, authInfos);
    
    ExternalLauncher launcher = new ExternalLauncher(profile);
    
    LauncherFrame.getInstance().setVisible(false);
    
    launcher.launch();
    System.exit(0);
  }
  
  public static void InterruptThread()
  {
    if (updateThread != null) {
      updateThread.interrupt();
    }
  }
  
  public static CrashReporter getCrashReporter()
  {
    return crashReporter;
  }
}
