package fr.paradoxal.launcher;

import static fr.theshark34.swinger.Swinger.getResource;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.ramselector.AbstractOptionFrame;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;

@SuppressWarnings("serial")
public class BootstrapRamSelecor extends AbstractOptionFrame implements ActionListener{

	private JLabel title = new JLabel("Param�trage", SwingConstants.CENTER);
	private JLabel ramlabel;
	private JLabel var;
	private JComboBox<String> ramBox;
	
	public static final File P_B_DIR = new File(GameDirGenerator.createGameDir("paradoxal"),"Launcher");
	public static File crack = new File(P_B_DIR,"crack.txt");
	public static File premiun = new File(P_B_DIR,"premiun.txt");
	public static File saver = new File(P_B_DIR,"launcher.properties");
	
	private JButton ok;
	private JButton change;
	public BootstrapRamSelecor(RamSelector selector) {
		super(selector);
		
		this.setTitle(LanguageManager.lang("Options"));
		this.setResizable(false);
		this.setSize(275, 355);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setUndecorated(true);
		this.setIconImage(getResource("Logo.png"));
		this.getContentPane().setBackground(Color.gray);
		
		UIManager.put("ComboBox.background", new ColorUIResource(Color.BLACK));
		UIManager.put("ComboBox.foreground", new ColorUIResource(Color.WHITE));
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.DARK_GRAY));
		UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));
		
		title.setFont(title.getFont().deriveFont(25F));
		title.setForeground(Color.WHITE);
		title.setBounds(5, 1, 250, 50);
		this.add(title);
		
		ramlabel = new JLabel("Mémoire");
		ramlabel.setForeground(Color.WHITE);
		ramlabel.setFont(ramlabel.getFont().deriveFont(19F));
		ramlabel.setBounds(this.getWidth()/2-45, 40, 209, 25);
		
		this.add(ramlabel);
		
		ramBox = new JComboBox<String>(RamSelector.RAM_ARRAY);
		ramBox.setForeground(Color.BLACK);
		ramBox.setFont(ramBox.getFont().deriveFont(19F));
		ramBox.setBounds(30,72,209,25);
		this.add(ramBox);
		
		if(crack.exists()){var = new JLabel("Version : Cracker");}else{var = new JLabel("Version : Prenium");}
		
		var.setForeground(Color.WHITE);
		var.setFont(ramlabel.getFont().deriveFont(19F));
		var.setBounds(40,200, 209, 25);
		this.add(var);
		
		
		change = new JButton("Changer de version");
		change.addActionListener(this);
		change.setFont(change.getFont().deriveFont(19F));
		change.setBounds(40,250,getWidth()/2+65,25);
		change.setBorderPainted(false);
		change.setContentAreaFilled(false);
		change.setOpaque(true);
		this.add(change);
		
		ok = new JButton("Valider");
		ok.addActionListener(this);
		ok.setFont(ramBox.getFont().deriveFont(19F));
		ok.setBounds(75,310,getWidth()/2,25);
		ok.setBorderPainted(false);
		ok.setContentAreaFilled(false);
		ok.setOpaque(true);
		this.add(ok);
	}
	
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ok) {
			this.setVisible(false);
			getSelector().save();
		}else if(e.getSource()==change) {
			crack.delete();
			premiun.delete();
			saver.delete();
			JOptionPane.showMessageDialog(this, "Merci de redémarrer votre launcher", "Action", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			getSelector().save();
		}
	}

	@Override
	public int getSelectedIndex() {
		return ramBox.getSelectedIndex();
	}

	@Override
	public void setSelectedIndex(int arg0) {
		ramBox.setSelectedIndex(arg0);
		
	}
	

}
