package fr.paradoxal.launcher;

import static fr.theshark34.swinger.Swinger.getResource;
import static fr.theshark34.swinger.Swinger.setResourcePath;
import static fr.theshark34.swinger.Swinger.setSystemLookNFeel;

import java.io.File;
import java.util.Arrays;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;
@SuppressWarnings("serial")
public class Launcher extends JFrame
{

	public static Launcher instance;
	private static CrashReporter crashReporter;
	private static LauncherPanel launcherPanel;
	private static AuthInfos authInfos;
	
	private static Thread UpdateThread;
	
	public static RamSelector ramSelector = new RamSelector(new File(InfoLauncher.P_DIR,"ram.txt"));
	
	public static void main(String[] args) 
	{
			LanguageManager.setLang(LanguageManager.FRENCH);
			
			setSystemLookNFeel();			
			
			setResourcePath("/fr/paradoxal/launcher/ressources/");
			InfoLauncher.P_CRASH_DIR.mkdirs();
			crashReporter = new CrashReporter("Paradoxal Launcher", InfoLauncher.P_CRASH_DIR);
			
			instance = new Launcher();
			
			displayStatic();			
	}
	
	private static void displayStatic() 
	{
		instance.setTitle("Launcher Paradoxal");
		instance.setSize(975,625);
		instance.setDefaultCloseOperation(EXIT_ON_CLOSE);
		instance.setLocationRelativeTo(null);
		instance.setUndecorated(true);
		instance.setIconImage(getResource("Logo.png"));
		launcherPanel = new LauncherPanel();
		instance.setContentPane(launcherPanel);
		
		AWTUtilities.setWindowOpacity(instance, 0.0F);
		
		WindowMover mover = new WindowMover(instance);
		instance.addMouseListener(mover);
		instance.addMouseMotionListener(mover);
		
		instance.setVisible(true);
		
		Animator.fadeInFrame(instance,Animator.FAST);
	}
	
	public static void auth(String username) throws AuthenticationException
	{
			authInfos = new AuthInfos(username,"sry","nope");
	}
	
	public static void update() throws Exception{
		SUpdate su = new SUpdate(InfoLauncher.URL_UPDATE,InfoLauncher.P_DIR);
		su.addApplication(new FileDeleter());
		UpdateThread = new Thread() {
			private int val=0;
			private int max=0;
			
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				while(!this.isInterrupted()) {
					if(BarAPI.getNumberOfFileToDownload() ==0 ) {
						Launcher.getinstance().getLauncherPanel().setInfoLabel("Vérification des fichiers");
						continue;
					}
					
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes()/1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload()/1000);
					
					Launcher.getinstance().getLauncherPanel().getprogressbar().setMaximum(max);
					Launcher.getinstance().getLauncherPanel().getprogressbar().setValue(val);
					
					Launcher.getinstance().getLauncherPanel().setInfoLabel("Téléchargement des fichiers "+BarAPI.getNumberOfDownloadedFiles()+" / "+BarAPI.getNumberOfFileToDownload()+ " - "+Swinger.percentage(val, max) + "%"); 
				}
			}
		};
		UpdateThread.start();
		su.start();
		UpdateThread.interrupt();
	}
	
	public static void launch() throws LaunchException
	{
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(InfoLauncher.P_INFOS, GameFolder.BASIC, authInfos);
		profile.getVmArgs().addAll(Arrays.asList(ramSelector.getRamArguments()));
		
		ExternalLauncher launch= new ExternalLauncher(profile);
		Launcher.getinstance().setVisible(false);
		
		launch.launch();
		System.exit(0);	
	}
	
	public static Launcher getinstance() 
	{
		return instance;
	}
	
	public static CrashReporter getCrashReporter() 
	{
		return crashReporter;
	}
	
	public static LauncherPanel getLauncherPanel() 
	{
		return launcherPanel;
	}
	
	public static void interruptThread() {
		UpdateThread.interrupt();
	}
	
}
