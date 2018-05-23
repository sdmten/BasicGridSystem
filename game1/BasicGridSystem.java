package game1;

import game1.hsa.Console;
import game1.lib.ErrorMessage;
import game1.lib.SpriteImport;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

public class BasicGridSystem
{
	static Console c = new Console("GameWindow - By Squishy Enterprises");
	static SpriteImport spriteImport = new SpriteImport();
	static BufferedImage tile;
	static BufferedImage map;
	static String mapNameImage = "/game1/map2.jpg";
	static String mapNameText = "/game1/Testmap2.txt";
	static int x1;
	static int y1;
	static int x2 = 0;
	static int y2 = 0;
	static int u;
	static int v;
	static char ch;
	static char ch2;
	static Runnable r;
	static String direction = "s";
	static String[][] mapArray = new String[20][16];
	static Thread loop2;
	static int exp;
	static int herohp = 30;
	static int heromp = 30;
	static int heroxp = 0;
	static int heroLevel = 1;
	static int heroAtt = 5;
	static int enemyAtt = 2;
	static int heroDef = 4;
	static int enemyDef = 2;
	static int enemyhp = 10;
	static int enemymp = 10;
	static int enemyhp2;
	static int herohp2 = herohp;
	static Random ran = new Random();
	static Thread loop;
	static int enemyPer = 100;
	static int heroPer = 100;

	public static void main(String[] args)
			throws Exception
	{
		titleScreen();
		drawMap(mapNameImage);
		storeMap(mapNameText);
		drawCharacter(x2, y2, direction);
		Runnable r1 = new Runnable()
		{
			public void run()
			{
				for (;;)
				{
					BasicGridSystem.x1 = BasicGridSystem.x2;
					BasicGridSystem.y1 = BasicGridSystem.y2;
					BasicGridSystem.ch = BasicGridSystem.c.getKey();
					BasicGridSystem.ch = Character.toLowerCase(BasicGridSystem.ch);
					if (BasicGridSystem.herohp2 <= 0) {
						try
						{
							BasicGridSystem.gameOver();
						}
						catch (Exception localException) {}
					}
					switch (BasicGridSystem.ch)
					{
					case 'w': 
						BasicGridSystem.currentMapPosition(BasicGridSystem.x1, BasicGridSystem.y1);
						BasicGridSystem.y1 -= 32;
						BasicGridSystem.direction = "n";
						break;
					case 'a': 
						BasicGridSystem.currentMapPosition(BasicGridSystem.x1, BasicGridSystem.y1);
						BasicGridSystem.x1 -= 32;
						BasicGridSystem.direction = "w";
						break;
					case 's': 
						BasicGridSystem.currentMapPosition(BasicGridSystem.x1, BasicGridSystem.y1);
						BasicGridSystem.y1 += 32;
						BasicGridSystem.direction = "s";
						break;
					case 'd': 
						BasicGridSystem.currentMapPosition(BasicGridSystem.x1, BasicGridSystem.y1);
						BasicGridSystem.x1 += 32;
						BasicGridSystem.direction = "e";
						break;
					case 'i': 
						try
						{
							BasicGridSystem.inventory();
						}
						catch (IOException localIOException1) {}
					case 'h': 
						BasicGridSystem.herohp2 = BasicGridSystem.herohp;
						BasicGridSystem.heroPer = 100;
						break;
					case 'b': 
						BasicGridSystem.heroAtt = 100;
						break;
					}
					if ((BasicGridSystem.ch == 'w') || (BasicGridSystem.ch == 'a') || (BasicGridSystem.ch == 's') || (BasicGridSystem.ch == 'd')) {
						try
						{
							BasicGridSystem.randomEncounter(BasicGridSystem.ch);
						}
						catch (Exception localException1) {}
					}
					if (BasicGridSystem.x1 == -32) {
						BasicGridSystem.x1 = 608;
					}
					if (BasicGridSystem.y1 == -32) {
						BasicGridSystem.y1 = 480;
					}
					if (BasicGridSystem.x1 == 640) {
						BasicGridSystem.x1 = 0;
					}
					if (BasicGridSystem.y1 == 512) {
						BasicGridSystem.y1 = 0;
					}
					if (BasicGridSystem.ch != 0) {
						try
						{
							BasicGridSystem.drawLastPos(BasicGridSystem.x2, BasicGridSystem.y2);
							BasicGridSystem.drawCharacter(BasicGridSystem.x1, BasicGridSystem.y1, BasicGridSystem.direction);
							BasicGridSystem.x2 = BasicGridSystem.x1;
							BasicGridSystem.y2 = BasicGridSystem.y1;
						}
						catch (IOException ex)
						{
							System.out.println("Error");
						}
					}
					BasicGridSystem.ch = '\000';
				}
			}
		};
		loop = new Thread(r1, "Game Input");
		loop.start();
	}

	public static void animationRandomEncounter()
			throws Exception
	{
		int x = 0;
		int y = 0;
		while (x < c.getWidth())
		{
			c.fillRect(x, y, 64, 64);
			Thread.sleep(20L);
			y += 64;
			if (y >= c.getHeight())
			{
				x += 64;
				y = 0;
			}
		}
	}

	public static void attack(int heroAttack, int enemyAttack)
	{
		int dmg = ran.nextInt(heroAttack);
		c.setColor(Color.white);
		c.fillRect(500, 0, 140, 50);
		c.setColor(Color.black);
		c.drawString("Hero did: " + dmg + " points of damage!", 499, 45);
		enemyhp2 -= dmg;
		enemyPer = enemyhp2 * 100 / enemyhp;
		displayEnemyHP();
		enemyAttack(enemyAttack);
	}

	public static void currentMapPosition(int x, int y)
	{
		u = x / 32;
		v = y / 32;
	}

	public static void displayHP()
	{
		c.drawRoundRect(99, 299, 101, 12, 2, 2);
		if (heroPer > 49) {
			c.setColor(Color.green);
		} else if (heroPer > 19) {
			c.setColor(Color.yellow);
		} else {
			c.setColor(Color.red);
		}
		c.fillRoundRect(100, 300, heroPer, 11, 2, 2);
		c.setColor(Color.black);
	}

	public static void displayEnemyHP()
	{
		c.drawRoundRect(499, 3, 101, 12, 2, 2);
		if (enemyPer > 49) {
			c.setColor(Color.green);
		} else if (enemyPer > 19) {
			c.setColor(Color.yellow);
		} else {
			c.setColor(Color.red);
		}
		c.fillRoundRect(500, 4, enemyPer, 11, 2, 2);
		c.setColor(Color.black);
	}

	public static void drawCharacter(int x, int y, String direction)
			throws IOException
	{
		InputStream inputstream = BasicGridSystem.class
				.getResourceAsStream("/game1/Player Sprite ".concat(direction)
						.concat(".png"));
		Image sprite = SpriteImport.makeColorTransparent(inputstream, 
				Color.white);
		c.drawImage(sprite, x, y, null);
		inputstream.close();
	}

	public static void drawLastPos(int x, int y)
			throws IOException
	{
		terrain(mapArray[u][v]);
		c.drawImage(tile, x, y, null);
	}

	public static void drawMap()
			throws IOException
	{
		int width = c.getWidth();
		int height = c.getHeight();
		int x = 0;
		int y = 0;
		double grid = 0.0D;
		while (grid < width)
		{
			while (x < width)
			{
				c.drawImage(tile, x, y, null);
				x += 32;
			}
			x = 0;
			y += 32;
			grid += width / 18;
		}
	}

	public static void drawMap(String mapName)
			throws IOException
	{
		int x = 0;
		int y = 0;
		int j = 0;

		String line = "";
		if (mapName.endsWith(".txt"))
		{
			InputStream inputstream = BasicGridSystem.class
					.getResourceAsStream(mapName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputstream));
			line = reader.readLine();
			while (line != null)
			{
				int i = 0;
				StringTokenizer token = new StringTokenizer(line);
				while (token.hasMoreTokens())
				{
					String tileType = token.nextToken();
					mapArray[i][j] = tileType;
					if (tileType.equals("g")) {
						terrain("g");
					} else if (tileType.equals("s")) {
						terrain("s");
					}
					c.drawImage(tile, x, y, null);
					x += 32;
					i++;
				}
				y += 32;
				x = 0;
				j++;
				line = reader.readLine();
			}
			inputstream.close();
		}
		else if ((mapName.endsWith(".jpg") | mapName.endsWith(".png")))
		{
			InputStream inputstream = BasicGridSystem.class
					.getResourceAsStream(mapName);
			map = ImageIO.read(inputstream);
			c.drawImage(map, 0, 0, null);

			inputstream.close();
		}
		else
		{
			new ErrorMessage("Error when loading map");
		}
	}

	public static void enemyAttack(int enemyAttack)
	{
		if (enemyAttack <= 1) {
			enemyAttack = 2;
		}
		int dmg = ran.nextInt(enemyAttack);
		c.setColor(Color.white);
		c.fillRect(100, 200, 140, 150);
		c.setColor(Color.black);
		c.drawString("Enemy did: " + dmg + " points of damage!", 100, 265);
		herohp2 -= dmg;
		heroPer = herohp2 * 100 / herohp;
		displayHP();
	}

	public static void escape(int enemyLevel)
			throws Exception
	{
		if (heroLevel > enemyLevel)
		{
			drawMap(mapNameImage);
			drawCharacter(x1, y1, direction);
			loop.start();
		}
		else if (heroLevel <= enemyLevel)
		{
			Random ran = new Random();
			int chance = ran.nextInt(3) + 1;
			if (chance == 1)
			{
				drawMap(mapNameImage);
				drawCharacter(x1, y1, direction);
				loop.start();
			}
			else
			{
				c.drawString("Escape attempt failed.", 275, 160);
				Thread.sleep(1000L);
				c.setColor(Color.white);
				c.fillRect(275, 160, 110, 15);
				c.setColor(Color.black);
			}
		}
	}

	public static void gameOver()
			throws Exception
	{
		c.clear();
		for (int i = 0; i < 50; i++) {
			c.println("GAME OVER");
		}
		Thread.sleep(3000L);
		System.exit(0);
	}

	public static void inventory()
			throws IOException
	{
		String armour = null;
		String[] armourarray = new String[6];

		InputStream inputstream = BasicGridSystem.class
				.getResourceAsStream("/game1/lib/SaveData");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputstream));

		c.clear();
		String line = reader.readLine();
		for (int i = 0; i < 6; i++)
		{
			StringTokenizer token = new StringTokenizer(line);
			while (token.hasMoreTokens()) {
				armour = token.nextToken();
			}
			line = reader.readLine();
			armourarray[i] = armour;
			c.println(armourarray[i]);
		}
		do
		{
			ch = c.getKey();
		} while (ch != 'i');
		inputstream.close();
		drawMap(mapNameImage);
	}

	public static void levelUp()
	{
		heroLevel += 1;
		heroAtt += 1;
		heroDef += 1;
		herohp += 10;

		herohp2 = herohp;
		heroPer = 100;
		c.clear();
		c.drawString("Level Up! You are now level: " + heroLevel, 250, 245);
		c.getChar();
	}

	public static void randomEncounter(char pos)
			throws Exception
	{
		int num = ran.nextInt(20);
		if (num == 15)
		{
			if (pos == 'w') {
				y1 += 32;
			} else if (pos == 'a') {
				x1 += 32;
			} else if (pos == 's') {
				y1 -= 32;
			} else if (pos == 'd') {
				x1 -= 32;
			}
			animationRandomEncounter();
			startBattle();
		}
	}

	public static void spellMenu() {}

	public static void startBattle()
			throws Exception
	{
		Thread.sleep(1000L);
		String monster = null;
		int choice = 1;
		int num = ran.nextInt(2);
		int level = 1;
		int heroAttack = heroAtt - enemyDef;
		int enemyAttack = enemyAtt - heroDef;
		if (num == 1)
		{
			level = ran.nextInt(15) + 1;
			monster = "Rabid Dog";
		}
		else if (num == 0)
		{
			level = ran.nextInt(15) + 1;
			monster = "Rat";
		}
		if ((level == 12) || (level == 13) || (level == 14))
		{
			enemyhp = 20;
			level = 2;
		}
		if (level == 15)
		{
			enemyhp = 30;
			level = 3;
		}
		else
		{
			enemyhp = 10;
			level = 1;
		}
		enemyhp2 = enemyhp;
		if (heroAttack < 2) {
			heroAttack = 2;
		}
		c.clear();
		c.drawString("A Wild " + monster + " Appeared!", 250, 245);
		Thread.sleep(3000L);
		c.clear();
		c.drawLine(0, 350, 640, 350);
		c.drawLine(240, 350, 240, 500);
		c.drawRect(70, 375, 60, 22);
		c.drawString("ATTACK", 75, 390);
		c.drawString("SPELL", 75, 430);
		c.drawString("RUN", 75, 470);
		c.drawString("Level: " + level, 499, 30);

		displayHP();
		displayEnemyHP();
		for (;;)
		{
			if (enemyhp2 <= 0)
			{
				c.drawString("You defeated the Wild " + monster + "!", 250, 160);
				Thread.sleep(5000L);
				xpGain();
				break;
			}
			ch2 = c.getKey();
			switch (ch2)
			{
			case 'w': 
				choice--;
				if (choice == 0) {
					choice = 1;
				}
				c.setColor(Color.white);
				c.drawRect(70, 415, 60, 22);
				c.drawRect(70, 455, 60, 22);
				c.setColor(Color.black);
				if (choice == 2) {
					c.drawRect(70, 415, 60, 22);
				} else if (choice == 1) {
					c.drawRect(70, 375, 60, 22);
				}
				break;
			case 's': 
				choice++;
				if (choice == 4) {
					choice = 3;
				}
				if (choice == 2)
				{
					c.setColor(Color.white);
					c.drawRect(70, 375, 60, 22);
					c.drawRect(70, 455, 60, 22);
					c.setColor(Color.black);
					c.drawRect(70, 415, 60, 22);
				}
				else if (choice == 3)
				{
					c.setColor(Color.white);
					c.drawRect(70, 415, 60, 22);
					c.setColor(Color.black);
					c.drawRect(70, 455, 60, 22);
				}
				break;
			case ' ': 
				if (choice == 1) {
					attack(heroAttack, enemyAttack);
				}
				if (choice == 2) {
					spellMenu();
				}
				if (choice == 3) {
					escape(level);
				}
				break;
			}
		}
		enemyPer = 100;

		drawMap(mapNameImage);
		drawCharacter(x1, y1, direction);
	}

	public static void storeMap(String mapName)
			throws IOException
	{
		String line = "";

		int j = 0;

		InputStream inputstream = BasicGridSystem.class
				.getResourceAsStream(mapName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputstream));
		line = reader.readLine();
		while (line != null)
		{
			int i = 0;
			StringTokenizer token = new StringTokenizer(line);
			while (token.hasMoreTokens())
			{
				String tileType = token.nextToken();
				mapArray[i][j] = tileType;
				i++;
			}
			j++;
			line = reader.readLine();
		}
		inputstream.close();
	}

	public static void terrain(String type)
			throws IOException
	{
		if (type.equalsIgnoreCase("g"))
		{
			InputStream inputstream = BasicGridSystem.class
					.getResourceAsStream("/game1/Grass2.jpg");
			tile = ImageIO.read(inputstream);
			inputstream.close();
		}
		if (type.equalsIgnoreCase("s"))
		{
			InputStream inputstream = BasicGridSystem.class
					.getResourceAsStream("/game1/Stone.jpg");
			tile = ImageIO.read(inputstream);
			inputstream.close();
		}
	}

	public static void titleScreen()
	{
		c.drawString("TITLE", 305, 100);
		c.drawString("NEW GAME", 289, 250);
		c.drawString("EXIT", 309, 350);
		c.drawRect(286, 235, 72, 20);
		int choice;
		do
		{
			choice = 1;
			ch = c.getKey();
			switch (ch)
			{
			case 'w': 
				choice--;
				if (choice == 0) {
					choice = 1;
				}
				c.setColor(Color.white);
				c.drawRect(306, 335, 32, 20);
				c.setColor(Color.black);
				c.drawRect(286, 235, 72, 20);
				break;
			case 's': 
				choice++;
				if (choice == 3) {
					choice = 2;
				}
				c.setColor(Color.white);
				c.drawRect(286, 235, 72, 20);
				c.setColor(Color.black);
				c.drawRect(306, 335, 32, 20);
				break;
			case ' ': 
				if (choice == 1) {
					choice = 4;
				} else if (choice == 2) {
					System.exit(0);
				}
				break;
			}
		} while (choice != 4);
	}

	public static void xpGain()
	{
		heroxp += 10;
		if (heroxp >= 100) {
			levelUp();
		}
	}

	public static void xpGain(int gain)
	{
		heroxp += gain;
		if (heroxp >= 100) {
			levelUp();
		}
	}
}
