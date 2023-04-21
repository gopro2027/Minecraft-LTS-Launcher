package com.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



//import com.main.mega.MegaHandler;

public class Main {
	
	public static String username = "";
	public static String settingsFile = "";
	public static boolean installAetherMod = false;
	public static boolean installTMIMod = false;
	public static void createFrame() throws URISyntaxException{    
		JFrame f=new JFrame("Minecraft 1.7.3 Beta Standalone Launcher"); 
		int width = 500;
		int height = 300;
		
		//image background
		URL resource = Main.class.getResource("bg.jpg");
		BufferedImage myImage;
		try {
			myImage = resize(ImageIO.read(resource),width,height);
			f.setContentPane(new ImagePanel(myImage));
		} catch (IOException e2) {
			System.out.println("Could not set image");
			e2.printStackTrace();
		}
		
		//launch button
		JButton b=new JButton("Launch");    
		b.setBounds(10,150,200, 40);
		
		
		final JCheckBox aetherCheckBox = new JCheckBox("Aether Mod");
		final JCheckBox tmiCheckBox = new JCheckBox("Too Many Items Mod");
		aetherCheckBox.setSelected(installAetherMod);
		tmiCheckBox.setSelected(installTMIMod);
		aetherCheckBox.setToolTipText("Installs the Aether, a magical and heavenly world above!");
		aetherCheckBox.setBounds(width/2,170,200, 20);
		tmiCheckBox.setBounds(width/2,150,200, 20);
		tmiCheckBox.setToolTipText("Allows you to spawn in any item and let your creative mind go wild!");
		
		JButton bOpenUpdateLink=new JButton("<HTML><font color=\"white\">Extract Server File");
		bOpenUpdateLink.setBounds(width/2,height-70,200,20);
		bOpenUpdateLink.setHorizontalAlignment(SwingConstants.LEADING);
		bOpenUpdateLink.setBorderPainted(false);
		bOpenUpdateLink.setOpaque(false);
		bOpenUpdateLink.setToolTipText("Extracts the server file so you can run it and play with friends"/*linkURI.toString()*/);
		bOpenUpdateLink.setBackground(Color.WHITE);
		bOpenUpdateLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bOpenUpdateLink.setMargin(new Insets(0, 0, 0, 0));
		
		
		//enter name label
		JLabel label = new JLabel("Enter User Name: ");
		label.setBounds(10, 0, 150, 50);
		
		//textfield to enter name
		final JTextField textfield= new JTextField();
		textfield.setBounds(width/2, (50-20)/2, 200, 20);
		if ("".equals(username)) {
			username = "Default"+((int)(Math.random()*100));
		}
		textfield.setText(username);
		
		//label for mem size
		JLabel label2 = new JLabel("Enter Memory Max Size: ");
		label2.setBounds(10, 50, 150, 50);
		
		//text field for mem size
		final JTextField textfield2= new JTextField();
		textfield2.setBounds(width/2, (50-20)/2+50, 200, 20);
		textfield2.setText("2048");
		
		//label for maker
		
		JButton gopro2027Button=new JButton("<html><font color='white'>Launcher by gopro_2027</font>");
		gopro2027Button.setBounds(10,height-70,150,20);
		gopro2027Button.setHorizontalAlignment(SwingConstants.LEADING);
		gopro2027Button.setBorderPainted(false);
		gopro2027Button.setOpaque(false);
		gopro2027Button.setToolTipText("Extracts the server file so you can run it and play with friends"/*linkURI.toString()*/);
		gopro2027Button.setBackground(Color.WHITE);
		gopro2027Button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		gopro2027Button.setMargin(new Insets(0, 0, 0, 0));
		
		
		
		
		//label for the debug text
		final JLabel label4 = new JLabel("");
		label4.setBounds(10,height-90,150,40);
		
		//add to frame
		f.add(textfield);
		f.add(label);
		f.add(textfield2);
		f.add(label2);
		f.add(b);
		//f.add(bDownload);
		//f.add(baseModGroupPanel);
		f.add(aetherCheckBox);
		f.add(tmiCheckBox);
		f.add(gopro2027Button);
		f.add(label4);
		//f.add(bInstallVanilla);
		//f.add(bInstallAether);
		f.add(bOpenUpdateLink);
		//f.add(bDownloadAether);
		//f.add(bDownloadWindows);
		f.setSize(width,height);
		f.setLayout(null);    
		f.setVisible(true); 
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		
		
		
		//action listener
		b.addActionListener(new ActionListener() {
	        
			public void actionPerformed(ActionEvent arg0) {
				installAetherMod = aetherCheckBox.isSelected();
				installTMIMod = tmiCheckBox.isSelected();
				if (installAetherMod) {
					if (installTMIMod) {
						installJar("minecraft_aether_tmi.jar");
					} else {
						installJar("minecraft_aether.jar");
					}
				} else {
					if (installTMIMod) {
						installJar("minecraft_vanilla_tmi.jar");
					} else {
						installJar("minecraft_vanilla.jar");
					}
				}
				
				Properties saveProps = new Properties();
			    saveProps.setProperty("aether", String.valueOf(installAetherMod));
			    saveProps.setProperty("tmi", String.valueOf(installTMIMod));
			    
			    username = textfield.getText();
			    if (!username.contains("Default")) {
			    	saveProps.setProperty("username", username);
			    }
			    try {
					saveProps.storeToXML(new FileOutputStream(settingsFile), "");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				boolean successful = true;
				String binDirectory = getUserDir()+"/.minecraft/bin";
				
		        if (new File(binDirectory).exists()) {
		        	int size = 1024;
		        	try {
		        		size = Integer.parseInt(textfield2.getText());
		        	} catch (Exception e){}
		        	
		        	String sessionId = ObtainAccessToken("username","password");
		        	System.out.println("Session id: "+sessionId);
		        	successful = execute(username,sessionId,size);
		        	if (successful == false) {
		        		label4.setText("<html><font color='red'>Error starting game</font>");
		        	}
		        } else {
		        	successful = false;
					if (successful == false) {
		        		label4.setText("<html><font color='red'>Install core files first!</font>");
		        	}
		        }
		        
		        
				if (successful == true) {
					System.exit(0);//close launcher
				}
			}          
		});
		
		
		
		
		bOpenUpdateLink.addActionListener(new ActionListener() {
	        
			public void actionPerformed(ActionEvent arg0) {
				//open(linkURI);
				new File(getUserDir()+"/server").mkdirs();
				String zipFilePath = getUserDir()+"/server/1.7.3 beta server.jar";
				File file = new File(zipFilePath);
				if (file.exists()) {
					file.delete();
				}
				InputStream link = (getClass().getResourceAsStream("1.7.3 beta server.jar"));
				try {
					Files.copy(link, file.getAbsoluteFile().toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		gopro2027Button.addActionListener(new ActionListener() {
	        
			public void actionPerformed(ActionEvent arg0) {
				open("https://github.com/gopro2027");
			}
		});
		
		
	}
	
	public static void open(String uri) {
		    if (Desktop.isDesktopSupported()) {
		      try {
		        try {
					Desktop.getDesktop().browse(new URI(uri));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
		      } catch (IOException e) {}
		    } else {}
		  }
	
	public static boolean installJar(String jarFileName) {
		String binDirectory = getUserDir()+"/.minecraft/bin/";
		
        if (new File(binDirectory+jarFileName).exists()) {
        	try {
				Files.copy(Paths.get(binDirectory+jarFileName), Paths.get(binDirectory+"minecraft.jar"), (CopyOption)StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        } else {
        	return false;
        }
        return true;
	}
	
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	public static String getRawURLText(String link) {
		try {
			URL url = new URL(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			String totalText = "";
			while ((line = in.readLine()) != null) {
				totalText += line;
			}
			in.close();
			return totalText;
		} catch (Exception e){
			e.printStackTrace();
		}
		return "";

	}
	

	
	public static boolean extractFile(String zipFilePath, String destinationDirectory) {
		UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(zipFilePath, destinationDirectory);
            return true;
        } catch (Exception ex) {
            System.out.println("Error extracting");
            ex.printStackTrace();
            return false;
        }
	}
	
	
	
	public static boolean extractAndInstallOriginalFiles() {
		File minecraftFolder = new File(getMinecraftFolder());
		if (minecraftFolder.exists()) {
			System.out.println("Minecraft folder already exists!");
			return true;
		}
		boolean success = true;
		
		String zipFilePath = getUserDir()+"/minecraft.zip";
		String java8FilePath = getUserDir()+"/java8.zip";
        String destDirectory = getUserDir();
		
		
		try {
			File file = new File(zipFilePath);
			InputStream link = (Main.class.getResourceAsStream("minecraft.zip"));
			Files.copy(link, file.getAbsoluteFile().toPath());
			
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		try {
			File file = new File(java8FilePath);
			InputStream link = (Main.class.getResourceAsStream("java8.zip"));
			Files.copy(link, file.getAbsoluteFile().toPath());
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		//File file = new File(destDirectory);
		//if (!file.exists()) {
			extractFile(zipFilePath,destDirectory);
			extractFile(java8FilePath,destDirectory);
		//}
		
		success = installJar("minecraft_vanilla.jar");
		
		//return success;
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		//https://login.minecraft.net?user=<username>&password=<password>&version=13
		//execute("Jeff",512,0);
		new File(getUserDir()).mkdirs();
		
		extractAndInstallOriginalFiles();
		
		settingsFile = getUserDir()+"/settings.xml";
		File settingsf = new File(settingsFile);
		try {
			settingsf.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 Properties loadProps = new Properties();
		 try {
		    loadProps.loadFromXML(new FileInputStream(settingsFile));
		 } catch(Exception e){}
		    try {
		    installAetherMod = Boolean.parseBoolean(loadProps.getProperty("aether"));
		    } catch(Exception e){}
		    try {
		    installTMIMod = Boolean.parseBoolean(loadProps.getProperty("tmi"));
		    } catch (Exception e){}
		    try {
			    username = loadProps.getProperty("username");
			    if (username == null) {
			    	username = "";
			    }
			} catch (Exception e){}
		
		
		createFrame();
		
	}
	
	public static String getUserDir() {
		return System.getProperty("user.dir")+"/mcdata";
	}
	
	public static String getBinFolder() {
		return getUserDir()+"/.minecraft/bin";
	}
	
	public static String getMinecraftFolder() {
		return getUserDir()+"/.minecraft";
	}
	
	public static boolean execute(String name, String sessionID, int memSize) {
		//String ex = "java -Duser.home="+System.getProperty("user.dir")+" -Xms"+memSize+"m -Xmx1g -Djava.library.path=natives/ -cp \"minecraft.jar;lwjgl.jar;lwjgl_util.jar\" net.minecraft.client.Minecraft "+name+" "+sessionID;
		//ex = "java -Duser.home=\""+System.getProperty("user.dir")+"\" -jar Test.jar";
		
		System.out.println(""+System.getProperty("os.name"));//prints "Linux" on ubuntu
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

		String commandLine = (isWindows?getUserDir()+"/java8/bin/java.exe":"java")+" "+"-Duser.home="+getUserDir()+" "+"-Xms"+memSize+"m"+" "+"-Xmx1g"+" "+"-Djava.library.path=natives/"+" "+"-cp"+" "+(isWindows?"\"minecraft.jar;lwjgl.jar;lwjgl_util.jar\"" : "\"minecraft.jar:lwjgl.jar:lwjgl_util.jar\"")+" "+"net.minecraft.client.Minecraft"+" "+name+" "+sessionID+"";
		System.out.println("commandLine: "+commandLine);
		
		final ProcessBuilder builder;
		//if (isWindows) {
		builder = new ProcessBuilder(
				
							isWindows?getUserDir()+"/java8/bin/java.exe":"java",
							"-Duser.home="+getUserDir(),//used on other os and windows if appdata is missing for some reason
							"-Xmx"+memSize+"m",
							"-Xms512m",
							"-Djava.library.path=natives/",
							"-cp",
							isWindows?"\"minecraft.jar;lwjgl.jar;lwjgl_util.jar\"" : "\"minecraft.jar:lwjgl.jar:lwjgl_util.jar\"",//osx also uses : like linux, according to this post: https://stackoverflow.com/questions/1675765/adding-to-the-classpath-on-osx
							"net.minecraft.client.Minecraft",
							name,
							sessionID+""
						
				);
		
		Map<String, String> env = builder.environment();
		env.put("APPDATA", getUserDir());//for windows
		builder.directory(new File(getBinFolder()));//specify executable directory (probably)
		
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			//This is a linux only fix
			
			List<String> lines = Arrays.asList("cd .minecraft/bin/", commandLine);
			Path file = Paths.get("linux_run.sh");
			try {
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e) {
				System.out.println("Count not create the sh file");
				e.printStackTrace();
			}
			System.out.println("Creating file at "+file.getParent());
			//ProcessBuilder pb = new ProcessBuilder(getUserDir()+"/linux_run.sh");
			ProcessBuilder pb = new ProcessBuilder("sh","linux_run.sh");
			pb.directory(new File(getUserDir()));
			try {
				Process p = pb.start();
			} catch (IOException e) {
				System.out.println("Could not start the process");
				e.printStackTrace();
			}
		} else {
		
		//Process process;
		try {
			System.out.println("Running on windows version");
			//process = 
			Process process = builder.start();//start process
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		}
		return true;
		
	}
	
	
	public static String ObtainAccessToken(String username, String password)
	{
		return "disabled";
		//with the microsoft migration idk how to do this sorry :P
		/*
		 try {
			String rawData = "{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\""+username+"\",\"password\":\""+password+"\",\"clientToken\":\"6c9d237d-8fbf-44ef-b46b-0b8a854bf391\"}";
			String type = "application/x-www-form-urlencoded";
			String encodedData = URLEncoder.encode( rawData, "UTF-8" ); 
			URL u = new URL("https://authserver.mojang.com/authenticate");
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
			OutputStream os = conn.getOutputStream();
			os.write(encodedData.getBytes());
		
			return new BufferedReader(new InputStreamReader(conn.getInputStream()))
				   .lines().collect(Collectors.joining("\n"));
				   
		} catch (Exception e) {
			return "Error Getting Session ID";
		}
		*/
	}
	
}

@SuppressWarnings("serial")
class ImagePanel extends JComponent {
	private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}