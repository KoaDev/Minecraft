package fr.koadev.github;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;
import java.io.File;
import javax.swing.JFrame;

public class LauncherFrame
  extends JFrame
{
  private static LauncherFrame instance;
  private LauncherPanel launcherPanel;
  private static CrashReporter crashReporter;
  
  public LauncherFrame()
  {
    setTitle("CraftForLife");
    setSize(800, 510);
    setDefaultCloseOperation(3);
    setLocationRelativeTo(null);
    setUndecorated(true);
    setIconImage(Swinger.getResource("favicon.png"));
    setContentPane(this.launcherPanel = new LauncherPanel());
    
    WindowMover mover = new WindowMover(this);
    addMouseListener(mover);
    addMouseMotionListener(mover);
    
    setVisible(true);
  }
  
  public static void main(String[] args)
  {
    Swinger.setSystemLookNFeel();
    Swinger.setResourcePath("/fr/koadev/github/ressources/");
    Launcher.AW_CRASH_DIR.mkdirs();
    crashReporter = new CrashReporter("KoaLife", Launcher.AW_CRASH_DIR);
    
    instance = new LauncherFrame();
  }
  
  public static LauncherFrame getInstance()
  {
    return instance;
  }
  
  public static CrashReporter getCrashReporter()
  {
    return crashReporter;
  }
  
  public LauncherPanel getLauncherPanel()
  {
    return this.launcherPanel;
  }
}
