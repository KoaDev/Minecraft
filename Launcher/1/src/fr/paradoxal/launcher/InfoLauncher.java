package fr.paradoxal.launcher;
import java.io.File;

import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;

public class InfoLauncher 
{
	
	public static final String MC_Version = "1.12.2";
	public static final String NAME = "paradoxal";
	public static final String DATE = "21 Juin 2018";
	public static final String URL_UPDATE = "http://paradoxalserveur.000webhostapp.com/minecraft/";
	public static final String COPYHIGHT_LAUNCHER ="Paradoxal";
	public static final String COPYRIGHT_Libs="Theshark34";
	public static final String WEB_SITE ="";
	
	public static final GameVersion P_VERSION = new GameVersion(MC_Version, GameType.V1_8_HIGHER);
	public static final GameInfos P_INFOS = new GameInfos(NAME, P_VERSION, new GameTweak[]{GameTweak.FORGE});
	public static final File P_DIR =  P_INFOS.getGameDir();
	public static final File P_CRASH_DIR = new File(P_DIR,"crash");
}
