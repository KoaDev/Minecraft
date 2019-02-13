package fr.koadev.github;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.theshark34.swinger.textured.STexturedProgressBar;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class LauncherPanel
  extends JPanel
  implements SwingerEventListener
{
  private Image background = Swinger.getResource("background.png");
  private Saver saver = new Saver(new File(Launcher.AW_DIR, "launcher.properties"));
  private JTextField usernameField = new JTextField(this.saver.get("username"));
  private JSlider slider = new JSlider();
  private STexturedButton playButton = new STexturedButton(Swinger.getResource("launch.png"));
  private STexturedButton playhoverButton = new STexturedButton(Swinger.getResource("launchhov.png"));
  private STexturedButton hideButton = new STexturedButton(Swinger.getResource("hide.png"));
  private STexturedButton quitButton = new STexturedButton(Swinger.getResource("close.png"));
  private STexturedButton options = new STexturedButton(Swinger.getResource("option.png"));
  private JLabel infoProgress = new JLabel("Clique sur jouer !", 0);
  private JLabel infoLabel = new JLabel("", 0);
  private STexturedProgressBar progressBar = new STexturedProgressBar(Swinger.getResource("emptybar.png"), Swinger.getResource("fullbar.png"));
  private RamSelector ramSelector = new RamSelector(new File(Launcher.AW_DIR, "ram.txt"));
  Font f = new Font("Sanies Script", 0, 24);
  
  public LauncherPanel()
  {
    setLayout(null);
    
    this.usernameField.setFont(this.usernameField.getFont().deriveFont(20.0F));
    this.usernameField.setHorizontalAlignment(0);
    this.usernameField.setOpaque(false);
    this.usernameField.setBorder(null);
    this.usernameField.setFont(this.f);
    this.usernameField.setBounds(310, 258, 185, 37);
    add(this.usernameField);
    
    this.playButton.setBounds(258, 310);
    this.playButton.setTextureHover(Swinger.getResource("launchhov.png"));
    this.playButton.addEventListener(this);
    add(this.playButton);
    
    this.playhoverButton.setBounds(258, 310);
    this.playhoverButton.addEventListener(this);
    
    this.playhoverButton.setTextureHover(Swinger.getResource("launchhov.png"));
    
    this.quitButton.setBounds(735, 32, 38, 38);
    this.quitButton.addEventListener(this);
    this.quitButton.setTextureHover(Swinger.getResource("closehov.png"));
    add(this.quitButton);
    
    this.hideButton.setBounds(690, 32, 38, 38);
    this.hideButton.addEventListener(this);
    this.hideButton.setTextureHover(Swinger.getResource("hidehover.png"));
    add(this.hideButton);
    
    this.options.addEventListener(this);
    this.options.setBounds(50, 30, 233, 50);
    this.options.addEventListener(this);
    this.options.setTextureHover(Swinger.getResource("optionhov.png"));
    add(this.options);
    
    this.progressBar.setBounds(40, 455, 720, 22);
    add(this.progressBar);
    
    this.infoProgress.setFont(this.infoProgress.getFont().deriveFont(18.0F));
    this.infoProgress.setForeground(Color.LIGHT_GRAY);
    this.infoProgress.setBounds(-10, 420, 530, 47);
    add(this.infoProgress);
    
    this.infoLabel.setFont(this.infoLabel.getFont().deriveFont(14.0F));
    this.infoLabel.setForeground(Color.ORANGE);
    this.infoLabel.setBounds(370, 250, 530, 47);
    add(this.infoLabel);
  }
  
  public void onEvent(SwingerEvent e)
  {
    if (e.getSource() == this.playButton)
    {
      this.playButton.setVisible(false);
      add(this.playhoverButton);
      if (this.usernameField.getText().replaceAll(" ", "").length() == 0)
      {
        LauncherFrame.getInstance().getLauncherPanel().setInfoText1("Veuillez rentrer un pseudo valide!");
        setFieldsEnabled(true);
        this.playButton.setVisible(true);
        return;
      }
      Thread t = new Thread()
      {
        public void run()
        {
          try
          {
            Launcher.auth(LauncherPanel.this.usernameField.getText());
          }
          catch (Exception e)
          {
            Launcher.InterruptThread();
            LauncherFrame.getInstance().getLauncherPanel().setInfoText1("Impossible de se connecter au serveur.");
            LauncherPanel.this.setFieldsEnabled(true);
            LauncherPanel.this.playButton.setVisible(true);
            return;
          }
          LauncherPanel.this.saver.set("username", LauncherPanel.this.usernameField.getText());
          LauncherPanel.this.ramSelector.save();
          try
          {
            Launcher.update();
          }
          catch (Exception e)
          {
            Launcher.InterruptThread();
            LauncherFrame.getInstance().getLauncherPanel().setInfoText1("Impossible de mettre a jour CraftForLife");
            LauncherPanel.this.playButton.setVisible(true);
          }
          try
          {
            Launcher.launch();
          }
          catch (LaunchException e)
          {
            LauncherFrame.getInstance().getLauncherPanel().setInfoText1("Impossible de lancer CraftForLife");
            LauncherPanel.this.playButton.setVisible(true);
          }
        }
      };
      t.start();
    }
    else if (e.getSource() == this.quitButton)
    {
      System.exit(0);
    }
    else if (e.getSource() == this.hideButton)
    {
      LauncherFrame.getInstance().setState(1);
    }
    else if (e.getSource() == this.options)
    {
      this.ramSelector.display();
    }
  }
  
  Thread t = new Thread();
  
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    Swinger.drawFullsizedImage(graphics, this, this.background);
  }
  
  private void setFieldsEnabled(boolean enabled)
  {
    this.usernameField.setEnabled(enabled);
    this.playButton.setEnabled(enabled);
  }
  
  public STexturedProgressBar getProgressBar()
  {
    return this.progressBar;
  }
  
  public void setInfoText(String text)
  {
    this.infoProgress.setText(text);
  }
  
  public void setInfoText1(String text)
  {
    this.infoLabel.setText(text);
  }
  
  public RamSelector getRamSelector()
  {
    return this.ramSelector;
  }
}
