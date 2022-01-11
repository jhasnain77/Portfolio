package com.fegdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class FEGDX extends ApplicationAdapter {
	SpriteBatch batch;

	TiledMap firstLevel;
	TiledMapRenderer firstLevelRenderer;

	MapProperties prop;
	int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, mapPixelWidth,
			mapPixelHeight;

	Music menuMusic;
	Music introMusic;
	Music mapMusic;
	Music battleMusic;

	Animation<TextureRegion> tikiMap;

	Sprite title;
	Sprite buttonSelect;
	Sprite startButton;
	Sprite newGameButton;
	Sprite optionsButton;
	Sprite quitGameButton;
	Sprite menuBG;
	Sprite textBox;
	Sprite choiceBox;
	Sprite selectArrow;
	Sprite mapBG;
	Sprite tikiTalkPortrait;

	Texture levelWarp;
	Sprite levelWarp1;

	String texts;
	
	float turnTime;

	String opening1;
	String opening2;
	String opening3;
	String opening4;
	String opening5;
	String opening6;
	String opening7;
	String opening8;
	String opening9;

	String choice1;
	String choice2;

	String mapText1;
	String mapText2;
	String mapText3;
	String mapText4;
	String mapText5;
	String mapText6;
	String mapText7;

	String gameTut1;
	String gameTut2;
	String gameTut3;
	String gameTut4;
	String gameTut5;
	String gameTut6;
	String gameTut7;
	String gameTut8;
	String gameTut9;

	String level1Start;

	String yes;
	String no;

	float menuTime;
	float gameTime;
	float choiceTime;

	boolean menu;
	boolean selectStart;
	boolean selectNew;
	boolean selectOptions;
	boolean selectQuit;

	boolean game;
	boolean scene1;
	boolean tikiTalk;
	boolean text;
	boolean text1;
	boolean text2;
	boolean text3;
	boolean text4;
	boolean text5;
	boolean text6;
	boolean text7;
	boolean text8;
	boolean text9;

	boolean playerMove;
	boolean tikiMove;

	boolean displayChoice1;
	boolean displayChoice2;

	boolean choice1Hover;
	boolean choice2Hover;

	boolean isBoy;
	boolean isGirl;

	boolean typeName;

	boolean map;
	boolean mapTutorial;

	boolean drawTiki;

	boolean levelStartOption;

	boolean level1;
	boolean battle;
	boolean battleTut;

	boolean forest;
	boolean plains;
	boolean water;
	boolean village;
	boolean impassable;

	boolean playerSelected;
	boolean tikiSelected;
	
	boolean turn1;
	boolean turn2;
	boolean turn3;
	boolean turn4;
	boolean turn5;
	
	String turn;

	enum TIKI_STATES {
		IDLE, LEFT, RIGHT, UP, DOWN
	};

	private TIKI_STATES state = TIKI_STATES.IDLE;

	OrthographicCamera camera;
	OrthographicCamera uiCamera;

	BitmapFont font;
	BitmapFont choose1;
	BitmapFont choose2;

	private int length = 0;

	int tikiX;
	int tikiY;

	int frame_cols = 6;
	int frame_rows = 1;

	int squareX;
	int squareY;

	int playerX;
	int playerY;

	int battleTikiX;
	int battleTikiY;

	int enemy1X;
	int enemy2X;
	int enemy3X;
	int enemy1Y;
	int enemy2Y;
	int enemy3Y;
	
	String turns1;
	String turns2;
	String turns3;
	String turns4;
	String turns5;

	// Animations
	Texture idleTikiSheet;
	Texture leftTikiSheet;
	Texture rightTikiSheet;
	Texture upTikiSheet;
	Texture downTikiSheet;

	Animation<TextureRegion> idleTikiAnimation;
	Animation<TextureRegion> leftTikiAnimation;
	Animation<TextureRegion> rightTikiAnimation;
	Animation<TextureRegion> downTikiAnimation;
	Animation<TextureRegion> upTikiAnimation;

	float stateTime;

	TextureRegion currentFrame;
	TextureRegion currentFrame1;
	TextureRegion currentFrame2;
	TextureRegion currentFrame3;
	TextureRegion currentFrame4;

	Texture selectSquareSheet;

	Animation<TextureRegion> squareAnimation;

	TextureRegion currentFrameSquare;

	// Battle Sprites

	Texture idleRobinFSheet;
	Texture tikiBattleSheet;
	Texture enemy1Sheet;

	Animation<TextureRegion> idleRobinFAnimation;
	Animation<TextureRegion> idleEnemy1Animation;
	Animation<TextureRegion> tikiBattleAnimation;

	TextureRegion currentRobinFFrame;
	TextureRegion currentEnemy1Frame;
	TextureRegion currentTikiBattleFrame;

	// Player Stats
	int playerHealth;
	int playerMov;
	int playerAtk;
	int playerSpd;
	int playerMag;
	int playerDef;
	int playerRes;

	int tikiHealth;
	int tikiMov;
	int tikiAtk;
	int tikiMag;
	int tikiSpd;
	int tikiDef;
	int tikiRes;

	// Move Squares

	Sprite movSquare;
	Sprite atkSquare;

	Texture blueSquare;
	Texture redSquare;

	String question;
	String move;
	String fight;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		turns1 = "Turn: 1";
		turns2 = "Turn: 2";
		turns3 = "Turn: 3";
		turns4 = "Turn: 4";
		turns5 = "Turn: 5";

		playerMove = true;

		move = "Move";
		fight = "Fight";

		question = "What would you like to do?";

		blueSquare = new Texture("movSquare.png");
		redSquare = new Texture("atkSquare.png");

		choiceTime = 0f;

		firstLevel = new TmxMapLoader().load("FEMap1.tmx");
		firstLevelRenderer = new OrthogonalTiledMapRenderer(firstLevel);
		prop = firstLevel.getProperties();

		mapWidth = prop.get("width", Integer.class);
		mapHeight = prop.get("height", Integer.class);
		tilePixelWidth = prop.get("tilewidth", Integer.class);
		tilePixelHeight = prop.get("tileheight", Integer.class);

		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, 1024, 768);

		// Music
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Victory2.mp3"));
		introMusic = Gdx.audio.newMusic(Gdx.files
				.internal("Atmosphere- Diving.mp3"));
		mapMusic = Gdx.audio.newMusic(Gdx.files
				.internal("Atmosphere- Mild.mp3"));
		battleMusic = Gdx.audio.newMusic(Gdx.files
				.internal("Twilight of the Gods.mp3"));

		// Squares

		turnTime = 0f;
		turn1 = false;
		turn2 = false;
		turn3 = false;
		turn4 = false;
		turn5 = false;
		
		movSquare = new Sprite(new Texture("movSquare.png"));
		atkSquare = new Sprite(new Texture("atkSquare.png"));

		// Tiki idle animation
		idleTikiSheet = new Texture(Gdx.files.internal("Tiki Idle Sheet.png"));

		TextureRegion[][] tmp = TextureRegion.split(idleTikiSheet,
				idleTikiSheet.getWidth() / frame_cols,
				idleTikiSheet.getHeight() / frame_rows);

		TextureRegion[] idleTikiFrames = new TextureRegion[frame_cols
				* frame_rows];
		int index = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				idleTikiFrames[index++] = tmp[i][j];
			}
		}

		idleTikiAnimation = new Animation<TextureRegion>(0.25f, idleTikiFrames);

		stateTime = 0f;

		// Left, Right, Up and Down Tiki animations
		leftTikiSheet = new Texture(Gdx.files.internal("Tiki Walk Left.png"));
		rightTikiSheet = new Texture(Gdx.files.internal("Tiki Walk Right.png"));
		upTikiSheet = new Texture(Gdx.files.internal("Tiki Walk Up.png"));
		downTikiSheet = new Texture(Gdx.files.internal("Tiki Walk Down.png"));

		TextureRegion[][] tmp1 = TextureRegion.split(leftTikiSheet,
				leftTikiSheet.getWidth() / frame_cols,
				leftTikiSheet.getHeight() / frame_rows);

		TextureRegion[][] tmp2 = TextureRegion.split(rightTikiSheet,
				rightTikiSheet.getWidth() / frame_cols,
				rightTikiSheet.getHeight() / frame_rows);

		TextureRegion[][] tmp3 = TextureRegion.split(upTikiSheet,
				upTikiSheet.getWidth() / frame_cols, upTikiSheet.getHeight()
						/ frame_rows);

		TextureRegion[][] tmp4 = TextureRegion.split(downTikiSheet,
				downTikiSheet.getWidth() / frame_cols,
				downTikiSheet.getHeight() / frame_rows);

		TextureRegion[] leftTikiFrames = new TextureRegion[frame_cols
				* frame_rows];
		int index1 = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				leftTikiFrames[index1++] = tmp1[i][j];
			}
		}
		TextureRegion[] rightTikiFrames = new TextureRegion[frame_cols
				* frame_rows];
		int index2 = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				rightTikiFrames[index2++] = tmp2[i][j];
			}
		}
		TextureRegion[] upTikiFrames = new TextureRegion[frame_cols
				* frame_rows];
		int index3 = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				upTikiFrames[index3++] = tmp3[i][j];
			}
		}
		TextureRegion[] downTikiFrames = new TextureRegion[frame_cols
				* frame_rows];
		int index4 = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				downTikiFrames[index4++] = tmp4[i][j];
			}
		}

		leftTikiAnimation = new Animation<TextureRegion>(0.15f, leftTikiFrames);
		rightTikiAnimation = new Animation<TextureRegion>(0.15f,
				rightTikiFrames);
		upTikiAnimation = new Animation<TextureRegion>(0.15f, upTikiFrames);
		downTikiAnimation = new Animation<TextureRegion>(0.15f, downTikiFrames);

		// Tiki movement
		tikiX = Gdx.graphics.getWidth() / 2 - 32;
		tikiY = Gdx.graphics.getHeight() / 2;

		// Select Square animation

		selectSquareSheet = new Texture(Gdx.files.internal("SelectSquare.png"));
		TextureRegion[][] tmpSquare = TextureRegion
				.split(selectSquareSheet, selectSquareSheet.getWidth() / 2,
						selectSquareSheet.getHeight());
		TextureRegion[] squareFrames = new TextureRegion[2 * 1];
		int index5 = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 2; j++) {
				squareFrames[index5++] = tmpSquare[i][j];
			}
		}
		squareAnimation = new Animation<TextureRegion>(0.5f, squareFrames);

		// Battle Animations
		// Female Character
		idleRobinFSheet = new Texture(
				Gdx.files.internal("Female Player Idle.png"));

		TextureRegion[][] tmpF = TextureRegion.split(idleRobinFSheet,
				idleRobinFSheet.getWidth() / frame_cols,
				idleRobinFSheet.getHeight() / frame_rows);

		TextureRegion[] idleRobinFFrames = new TextureRegion[frame_cols
				* frame_rows];
		int indexF = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				idleRobinFFrames[indexF++] = tmpF[i][j];
			}
		}

		idleRobinFAnimation = new Animation<TextureRegion>(0.25f,
				idleRobinFFrames);

		// Male Character

		// Tiki battle Sprite

		tikiBattleSheet = new Texture(
				Gdx.files.internal("Idle Tiki Sheet 2.png"));

		TextureRegion[][] tmpTiki = TextureRegion.split(tikiBattleSheet,
				tikiBattleSheet.getWidth() / frame_cols,
				tikiBattleSheet.getHeight() / frame_rows);

		TextureRegion[] idleTikiBattleFrames = new TextureRegion[frame_cols
				* frame_rows];
		int indexTiki = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				idleTikiBattleFrames[indexTiki++] = tmpTiki[i][j];
			}
		}

		tikiBattleAnimation = new Animation<TextureRegion>(0.25f,
				idleTikiBattleFrames);

		// Enemy 1 animations

		enemy1Sheet = new Texture(Gdx.files.internal("Enemy 1.png"));

		TextureRegion[][] tmpE = TextureRegion.split(enemy1Sheet,
				enemy1Sheet.getWidth() / frame_cols, enemy1Sheet.getHeight()
						/ frame_rows);

		TextureRegion[] enemy1Frames = new TextureRegion[frame_cols
				* frame_rows];
		int indexE = 0;
		for (int i = 0; i < frame_rows; i++) {
			for (int j = 0; j < frame_cols; j++) {
				enemy1Frames[indexE++] = tmpE[i][j];
			}
		}

		idleEnemy1Animation = new Animation<TextureRegion>(0.25f, enemy1Frames);

		// Battle Sprites Positions

		playerX = 256 - 4;
		playerY = 256;

		battleTikiX = 12 * 32 - 4;
		battleTikiY = 6 * 32;
		
		tikiMove = true;
		playerMove = true;

		enemy1X = (32 - 4) * 32 - 4;
		enemy2X = (32 - 6) * 32 - 4;
		enemy3X = (32 - 3) * 32 - 4;
		enemy1Y = (26 - 4) * 32;
		enemy2Y = (26 - 7) * 32;
		enemy3Y = (26 - 5) * 32;

		// Character Stats

		playerHealth = 21;
		playerMov = 7;
		playerAtk = 8;
		playerMag = 9;
		playerDef = 7;
		playerRes = 8;
		playerSpd = 9;

		tikiHealth = 22;
		tikiMov = 7;
		tikiAtk = 9;
		tikiMag = 9;
		tikiDef = 8;
		tikiRes = 10;
		tikiSpd = 10;

		// Text
		font = new BitmapFont();
		font.getData().setScale(4f);
		choose1 = new BitmapFont();
		choose1.getData().setScale(3f);
		choose2 = new BitmapFont();
		choose2.getData().setScale(3f);
		texts = "";
		textBox = new Sprite(new Texture("TextBox.png"));

		// Menu
		menu = true;
		selectStart = true;
		selectNew = false;
		selectOptions = false;
		selectQuit = false;

		menuTime = 0f;

		title = new Sprite(new Texture("Title.png"));
		title.setPosition(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2,
				Gdx.graphics.getHeight() - title.getHeight());
		buttonSelect = new Sprite(new Texture("ButtonSelect.png"));
		startButton = new Sprite(new Texture("StartButton.png"));
		startButton.setPosition(
				Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
				Gdx.graphics.getHeight() - title.getHeight()
						- startButton.getHeight());
		newGameButton = new Sprite(new Texture("NewGameButton.png"));
		newGameButton.setPosition(
				Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
				Gdx.graphics.getHeight() - title.getHeight()
						- (2 * startButton.getHeight()) - 8);
		optionsButton = new Sprite(new Texture("OptionsButton.png"));
		optionsButton.setPosition(
				Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
				Gdx.graphics.getHeight() - title.getHeight()
						- (3 * startButton.getHeight()) - (2 * 8));
		quitGameButton = new Sprite(new Texture("QuitButton.png"));
		quitGameButton.setPosition(
				Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
				Gdx.graphics.getHeight() - title.getHeight()
						- (4 * startButton.getHeight()) - (3 * 8));
		menuBG = new Sprite(new Texture("BG.png"));

		// Game - scene 1
		gameTime = 0f;

		game = false;
		scene1 = true;
		text = false;
		text1 = true;
		text2 = false;
		text3 = false;
		text4 = false;
		text5 = false;
		text6 = false;
		text7 = false;
		text8 = false;
		text9 = false;
		tikiTalk = false;

		displayChoice1 = false;
		displayChoice2 = false;
		isBoy = false;
		isGirl = false;
		choice1Hover = true;
		choice2Hover = false;

		typeName = false;

		opening1 = "Welcome to the world of Fire Emblem.";
		opening2 = "This is a medieval world full of wonder.";
		opening3 = "You're about to embark on a journey";
		opening4 = "that will take you across the lands.";
		opening5 = "First things first...";
		opening6 = "Are you a boy, or a girl";
		choice1 = "Boy";
		choice2 = "Girl";
		opening7 = "Okay, and what might your name be?";
		opening8 = "Nice to meet you. I will be your partner.";
		opening9 = "Now! Welcome to the world of Aevium!";

		choiceBox = new Sprite(new Texture("ChoiceBox.png"));
		choiceBox.setPosition(Gdx.graphics.getWidth() - choiceBox.getWidth(),
				textBox.getHeight());
		selectArrow = new Sprite(new Texture("SelectArrow.png"));
		selectArrow.setScale(0.5f);
		tikiTalkPortrait = new Sprite(new Texture("Tiki Talk Portrait.png"));
		tikiTalkPortrait.setPosition(tikiTalkPortrait.getWidth() / 2,
				textBox.getHeight() + tikiTalkPortrait.getHeight() / 2);
		tikiTalkPortrait.setScale(2f);

		// Game tutorial
		map = false;
		mapTutorial = false;

		mapText1 = "Welcome to the world map!";
		mapText2 = "This is the map of Aevium. This is";
		mapText3 = "where you'll select which level to play.";
		mapText4 = "How about we try this first level?";
		mapText5 = "Move your character along the map";
		mapText6 = "with the arrow keys. Press Z to select.";
		mapText7 = "Okay, time to enter the first level.";

		mapBG = new Sprite(new Texture("Map.png"));

		gameTut1 = "Oh! My name? The name is Tiki!";
		gameTut2 = "Silly me! Sorry about that!";
		gameTut3 = "I will be fighting alongside you.";
		gameTut4 = "Look! Three enemies! Let's beat them!";
		gameTut5 = "To battle, select a unit and move them";
		gameTut6 = "to the position you choose.";
		gameTut7 = "If you're in range of an enemy, you";
		gameTut8 = "can select to fight them!";
		gameTut9 = "Alright, I'll leave it up to you!";

		battleTut = false;

		levelWarp = new Texture("Level Warp.png");
		levelWarp1 = new Sprite(levelWarp);
		levelWarp1.setPosition(160, Gdx.graphics.getHeight() / 2);

		levelStartOption = false;
		level1Start = "Would you like to start level 1?";

		yes = "Yes";
		no = "No";

		squareX = Gdx.graphics.getWidth() / 2 - 32;
		squareY = Gdx.graphics.getHeight() / 2;

		level1 = false;
		battle = false;

		forest = false;
		plains = false;
		water = false;
		village = false;
		impassable = false;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Menu
		if (menu == true) {
			menuMusic.play();
			menuMusic.setVolume(0.75f);
			menuTime += 0.01666f;
			if (selectStart == true) {
				buttonSelect
						.setPosition(startButton.getX(), startButton.getY());
				if (Gdx.input.isKeyJustPressed(Keys.DOWN) && menuTime > 0.05) {
					selectNew = true;
					selectStart = false;
					menuTime = 0f;
				}
				if (Gdx.input.isKeyJustPressed(Keys.Z)) {
					game = true;
					menu = false;
				}
			}
			if (selectNew == true) {
				buttonSelect.setPosition(newGameButton.getX(),
						newGameButton.getY());
				if (Gdx.input.isKeyJustPressed(Keys.UP) && menuTime > 0.05) {
					selectStart = true;
					selectNew = false;
					menuTime = 0f;
				}
				if (Gdx.input.isKeyJustPressed(Keys.DOWN) && menuTime > 0.05) {
					selectOptions = true;
					selectNew = false;
					menuTime = 0f;
				}
			}
			if (selectOptions == true) {
				buttonSelect.setPosition(startButton.getX(),
						optionsButton.getY());
				if (Gdx.input.isKeyJustPressed(Keys.UP) && menuTime > 0.05) {
					selectNew = true;
					selectOptions = false;
					menuTime = 0f;
				}
				if (Gdx.input.isKeyJustPressed(Keys.DOWN) && menuTime > 0.05) {
					selectQuit = true;
					selectOptions = false;
					menuTime = 0f;
				}
			}
			if (selectQuit == true) {
				buttonSelect.setPosition(startButton.getX(),
						quitGameButton.getY());
				if (Gdx.input.isKeyJustPressed(Keys.UP) && menuTime > 0.05) {
					selectOptions = true;
					selectQuit = false;
					menuTime = 0f;
				}
			}
		}
		if (menu == false) {
			menuMusic.stop();
		}

		if (game == true) {
			gameTime += 0.01666f;
			if (scene1 == true) {
				introMusic.play();
				introMusic.setVolume(5f);
				tikiTalk = true;
				if (text1 == true) {
					text = true;
					texts = new String(opening1);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime >= 0.05) {
						text1 = false;
						text2 = true;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text2 == true) {
					text = true;
					texts = new String(opening2);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime >= 0.05) {
						text2 = false;
						text3 = true;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text3 == true) {
					text = true;
					texts = new String(opening3);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime >= 0.05) {
						text3 = false;
						text4 = true;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text4 == true) {
					text = true;
					texts = new String(opening4);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime >= 0.05) {
						text4 = false;
						text5 = true;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text5 == true) {
					text = true;
					texts = new String(opening5);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime >= 0.05) {
						text5 = false;
						text6 = true;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text6 == true) {
					text = true;
					texts = new String(opening6);
					if (Gdx.input.isKeyJustPressed(Keys.Z)) {
						displayChoice1 = true;
						displayChoice2 = true;
					}
					if (Gdx.input.isKeyJustPressed(Keys.UP)) {
						choice2Hover = false;
						choice1Hover = true;
					}
					if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
						choice1Hover = false;
						choice2Hover = true;
					}
					if (displayChoice1 == true && displayChoice2 == true) {
						if (choice1Hover == true) {
							selectArrow.setPosition(choiceBox.getX() + 8,
									choiceBox.getY() + 80);
						}
						if (choice2Hover == true) {
							selectArrow.setPosition(choiceBox.getX() + 8,
									choiceBox.getY() + 24);
						}
					}
					if (choice1Hover == true) {
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							isBoy = true;
							text6 = false;
							text7 = true;
							displayChoice1 = false;
							displayChoice2 = false;
							gameTime = 0f;
							length = 0;
							choice1Hover = true;
							choice2Hover = false;
						}
					}
					if (choice2Hover == true) {
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							isGirl = true;
							text6 = false;
							text7 = true;
							displayChoice1 = false;
							displayChoice2 = false;
							gameTime = 0f;
							length = 0;
							choice1Hover = true;
							choice2Hover = false;
						}
					}
				}
				if (text7 == true) {
					text = true;
					texts = new String(opening7);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						typeName = true;
					}
					if (typeName == true) {
						if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
							text7 = false;
							text8 = true;
							gameTime = 0f;
							length = 0;
						}
					}
				}
				if (text8 == true) {
					text = true;
					texts = new String(opening8);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text8 = false;
						text9 = true;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text9 == true) {
					text = true;
					texts = new String(opening9);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text9 = false;
						text = false;
						scene1 = false;
						mapTutorial = true;
						tikiTalk = false;
						gameTime = 0f;
						length = 0;
						text1 = true;
						map = true;
					}
				}

			}
			if (scene1 == false) {
				introMusic.stop();
			}
			if (mapTutorial == true) {
				text = true;
				gameTime += 0.01666f;
				if (text1 == true) {
					texts = new String(mapText1);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text2 = true;
						text1 = false;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text2 == true) {
					texts = new String(mapText2);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text3 = true;
						text2 = false;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text3 == true) {
					texts = new String(mapText3);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text4 = true;
						text3 = false;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text4 == true) {
					texts = new String(mapText4);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text5 = true;
						text4 = false;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text5 == true) {
					texts = new String(mapText5);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text6 = true;
						text5 = false;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text6 == true) {
					texts = new String(mapText6);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text7 = true;
						text6 = false;
						gameTime = 0f;
						length = 0;
					}
				}
				if (text7 == true) {
					texts = new String(mapText7);
					if (Gdx.input.isKeyJustPressed(Keys.Z) && gameTime > 0.5f) {
						text7 = false;
						mapTutorial = false;
						text = false;
						length = 0;
						text1 = true;
					}
				}
			}
			if (map == true) {
				mapMusic.play();
				drawTiki = true;
				if (drawTiki == true) {
					stateTime += Gdx.graphics.getDeltaTime();

					currentFrame = idleTikiAnimation.getKeyFrame(stateTime,
							true);
					currentFrame1 = leftTikiAnimation.getKeyFrame(stateTime,
							true);
					currentFrame2 = rightTikiAnimation.getKeyFrame(stateTime,
							true);
					currentFrame3 = upTikiAnimation
							.getKeyFrame(stateTime, true);
					currentFrame4 = downTikiAnimation.getKeyFrame(stateTime,
							true);
				}
				if (Gdx.input.isKeyPressed(Keys.RIGHT) && mapTutorial == false
						&& levelStartOption == false) {
					state = TIKI_STATES.RIGHT;
					if (tikiX < mapBG.getWidth() - 64) {
						tikiX += 5;
					}
				} else if (Gdx.input.isKeyPressed(Keys.LEFT)
						&& mapTutorial == false && levelStartOption == false) {
					state = TIKI_STATES.LEFT;
					if (tikiX > 0) {
						tikiX -= 5;
					}
				} else if (Gdx.input.isKeyPressed(Keys.UP)
						&& mapTutorial == false && levelStartOption == false) {
					state = TIKI_STATES.UP;
					if (tikiY < mapBG.getHeight()) {
						tikiY += 5;
					}
				} else if (Gdx.input.isKeyPressed(Keys.DOWN)
						&& mapTutorial == false && levelStartOption == false) {
					state = TIKI_STATES.DOWN;
					if (tikiY > 0) {
						tikiY -= 5;
					}
				} else {
					state = TIKI_STATES.IDLE;
				}
				if (tikiX > 128 && tikiX < 224
						&& tikiY > Gdx.graphics.getHeight() / 2 - 64
						&& tikiY < Gdx.graphics.getHeight() / 2 + 32
						&& Gdx.input.isKeyJustPressed(Keys.Z)) {
					levelStartOption = true;
					gameTime = 0f;
				}
				if (levelStartOption == true) {
					choiceTime += 0.01f;
					text = true;
					texts = new String(level1Start);
					choice1 = new String(yes);
					choice2 = new String(no);
					displayChoice1 = true;
					displayChoice2 = true;
					if (choice1Hover == true) {
						if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
							selectArrow.setPosition(choiceBox.getX() + 8,
									choiceBox.getY() + 24);
							choice1Hover = false;
							choice2Hover = true;
						}
						System.out.println(choiceTime);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& choiceTime > 0.05f) {
							levelStartOption = false;
							choice1Hover = true;
							choice2Hover = false;
							displayChoice1 = false;
							displayChoice2 = false;
							drawTiki = false;
							battle = true;
							text = false;
							length = 0;
							map = false;
							level1 = true;
							text1 = true;
							gameTime = 0f;
							battleTut = true;
							turn1 = true;
						}
					}
					if (choice2Hover == true) {
						if (Gdx.input.isKeyJustPressed(Keys.UP)) {
							selectArrow.setPosition(choiceBox.getX() + 8,
									choiceBox.getY() + 80);
							choice2Hover = false;
							choice1Hover = true;
						}
						if (Gdx.input.isKeyJustPressed(Keys.Z)) {
							levelStartOption = false;
							choice1Hover = true;
							selectArrow.setPosition(choiceBox.getX() + 8,
									choiceBox.getY() + 80);
							choice2Hover = false;
							displayChoice1 = false;
							displayChoice2 = false;
							text = false;
							length = 0;
							choiceTime = 0f;
						}
					}
				}
			}
			if (level1 == true) {
				gameTime += 0.016666f;
				turnTime += 0.5f;
				battleMusic.play();
				stateTime += Gdx.graphics.getDeltaTime();

				currentRobinFFrame = idleRobinFAnimation.getKeyFrame(stateTime,
						true);

				currentTikiBattleFrame = tikiBattleAnimation.getKeyFrame(
						stateTime, true);

				currentEnemy1Frame = idleEnemy1Animation.getKeyFrame(stateTime,
						true);

				currentFrameSquare = squareAnimation.getKeyFrame(stateTime,
						true);
				if (battleTut == true) {
					tikiTalk = true;
					text = true;
					if (text1 == true) {
						texts = new String(gameTut1);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text2 = true;
							text1 = false;
							gameTime = 0f;
							length = 0;
						}
					}
					if (text2 == true) {
						texts = new String(gameTut2);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text3 = true;
							text2 = false;
							gameTime = 0f;
							length = 0;
						}
					}
					if (text3 == true) {
						texts = new String(gameTut3);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text4 = true;
							text3 = false;
							gameTime = 0f;
							length = 0;
						}
					}
					if (text4 == true) {
						texts = new String(gameTut4);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text5 = true;
							text4 = false;
							gameTime = 0f;
							length = 0;
						}
					}
					if (text5 == true) {
						texts = new String(gameTut5);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text6 = true;
							text5 = false;
							gameTime = 0f;
							length = 0;
						}
					}
					if (text6 == true) {
						texts = new String(gameTut6);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text7 = true;
							text6 = false;
							gameTime = 0f;
							length = 0;
						}
					}
					if (text7 == true) {
						texts = new String(gameTut7);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text7 = false;
							text8 = true;
							length = 0;
							gameTime = 0f;
						}
					}
					if (text8 == true) {
						texts = new String(gameTut8);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text8 = false;
							text9 = true;
							length = 0;
							gameTime = 0f;
						}
					}
					if (text9 == true) {
						texts = new String(gameTut9);
						if (Gdx.input.isKeyJustPressed(Keys.Z)
								&& gameTime > 0.5f) {
							text9 = false;
							text = false;
							length = 0;
							battleTut = false;
							gameTime = 0f;
							tikiTalk = false;
						}
					}
				}
				if (turn1 == true && turnTime < 1) {
					turn = new String(turns1);
					playerMove = true;
					tikiMove = true;
				}
				if (playerMove == false && tikiMove == false && turn1 == true) {
					turn2 = true;
					turnTime = 0f;
				}
				if (turn2 == true && turnTime < 1) {
					turn = new String(turns2);
					playerMove = true;
					tikiMove = true;
				}
				if (playerMove == false && tikiMove == false && turn2 == true) {
					turn3 = true;
					turnTime = 0f;
				}
				if (turn3 == true && turnTime < 1) {
					turn = new String(turns3);
					playerMove = true;
					tikiMove = true;
				}
				if (playerMove == false && tikiMove == false && turn3 == true) {
					turn4 = true;
					turnTime = 0f;
				}
				if (turn4 == true && turnTime < 1) {
					turn = new String(turns4);
					playerMove = true;
					tikiMove = true;
				}
				if (Gdx.input.isKeyJustPressed(Keys.UP) && text == false) {
					squareY += 32;
				}
				if (Gdx.input.isKeyJustPressed(Keys.DOWN) && text == false) {
					squareY -= 32;
				}
				if (Gdx.input.isKeyJustPressed(Keys.LEFT) && text == false) {
					squareX -= 32;
				}
				if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && text == false) {
					squareX += 32;
				}
				if (playerMove == true) {
					if (squareX == playerX + 4 && squareY == playerY) {
						if (Gdx.input.isKeyJustPressed(Keys.Z)) {
							playerSelected = true;
							gameTime = 0f;
						}
					}
				}
				if (tikiMove == true) {
					if (squareX == battleTikiX + 4 && squareY == battleTikiY) {
						if (Gdx.input.isKeyJustPressed(Keys.Z)) {
							tikiSelected = true;
							gameTime = 0f;
						}
					}
				}

				if (playerSelected == true) {
					gameTime += 0.0166f;
					if (Gdx.input.isKeyJustPressed(Keys.X)) {
						playerSelected = false;
					}
					for (int i = playerMov; i > 0; i -= 1) {
						for (int j = playerMov - (i); j >= 0 - playerMov + (i); j -= 1) {
							if (squareX == playerX + 4 + i * 32
									&& squareY == playerY + j * 32) {
								if (Gdx.input.isKeyJustPressed(Keys.Z)
										&& gameTime > 0.5f && text == false) {
									text = true;
									displayChoice1 = true;
									displayChoice2 = true;
									gameTime = 0f;
									length = 0;
									choice1Hover = true;
									choice2Hover = false;
									texts = new String(question);
									choice1 = new String(move);
									choice2 = new String(fight);
								}
								if (text == true) {
									if (choice1Hover == true) {
										if (Gdx.input
												.isKeyJustPressed(Keys.DOWN)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 24);
											choice1Hover = false;
											choice2Hover = true;
										}
										if (Gdx.input.isKeyJustPressed(Keys.Z)
												&& gameTime > 0.5f) {
											playerX = squareX - 4;
											playerY = squareY - 4;
											text = false;
											playerSelected = false;
										}
									}
									if (choice2Hover == true) {
										if (Gdx.input.isKeyJustPressed(Keys.UP)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 80);
											choice2Hover = false;
											choice1Hover = true;
										}
									}
								}
							}
						}
					}
					for (int i = 0 - playerMov; i < 1; i++) {
						for (int j = playerMov + (i); j >= 0 - playerMov - (i); j--) {
							if (squareX == playerX + 4 + i * 32
									&& squareY == playerY + j * 32) {
								if (Gdx.input.isKeyJustPressed(Keys.Z)
										&& gameTime > 0.5f && text == false) {
									text = true;
									displayChoice1 = true;
									displayChoice2 = true;
									gameTime = 0f;
									length = 0;
									choice1Hover = true;
									choice2Hover = false;
									texts = new String(question);
									choice1 = new String(move);
									choice2 = new String(fight);
								}
								if (text == true) {
									if (choice1Hover == true) {
										if (Gdx.input
												.isKeyJustPressed(Keys.DOWN)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 24);
											choice1Hover = false;
											choice2Hover = true;
										}
										if (Gdx.input.isKeyJustPressed(Keys.Z)
												&& gameTime > 0.5f) {
											playerX = squareX - 4;
											playerY = squareY - 4;
											text = false;
											playerSelected = false;
										}
									}
									if (choice2Hover == true) {
										if (Gdx.input.isKeyJustPressed(Keys.UP)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 80);
											choice2Hover = false;
											choice1Hover = true;
										}
									}
								}
							}
						}
					}
				}
				if (tikiSelected == true) {
					gameTime += 0.0166f;
					if (Gdx.input.isKeyJustPressed(Keys.X)) {
						tikiSelected = false;
					}
					for (int i = tikiMov; i > 0; i -= 1) {
						for (int j = tikiMov - (i); j >= 0 - tikiMov + (i); j -= 1) {
							if (squareX == battleTikiX + 4 + i * 32
									&& squareY == battleTikiY + j * 32) {
								if (Gdx.input.isKeyJustPressed(Keys.Z)
										&& gameTime > 0.5f && text == false) {
									text = true;
									displayChoice1 = true;
									displayChoice2 = true;
									gameTime = 0f;
									length = 0;
									choice1Hover = true;
									choice2Hover = false;
									texts = new String(question);
									choice1 = new String(move);
									choice2 = new String(fight);
								}
								if (text == true) {
									if (choice1Hover == true) {
										if (Gdx.input
												.isKeyJustPressed(Keys.DOWN)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 24);
											choice1Hover = false;
											choice2Hover = true;
										}
										if (Gdx.input.isKeyJustPressed(Keys.Z)
												&& gameTime > 0.5f) {
											tikiX = squareX - 4;
											tikiY = squareY - 4;
											text = false;
											tikiSelected = false;
										}
									}
									if (choice2Hover == true) {
										if (Gdx.input.isKeyJustPressed(Keys.UP)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 80);
											choice2Hover = false;
											choice1Hover = true;
										}
									}
								}
							}
						}
					}
					for (int i = tikiMov; i > 0; i -= 1) {
						for (int j = tikiMov - (i); j >= 0 - tikiMov + (i); j -= 1) {
							if (squareX == battleTikiX + 4 + i * 32
									&& squareY == battleTikiY + j * 32) {
								if (Gdx.input.isKeyJustPressed(Keys.Z)
										&& gameTime > 0.5f && text == false) {
									text = true;
									displayChoice1 = true;
									displayChoice2 = true;
									gameTime = 0f;
									length = 0;
									choice1Hover = true;
									choice2Hover = false;
									texts = new String(question);
									choice1 = new String(move);
									choice2 = new String(fight);
								}
								if (text == true) {
									if (choice1Hover == true) {
										if (Gdx.input
												.isKeyJustPressed(Keys.DOWN)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 24);
											choice1Hover = false;
											choice2Hover = true;
										}
										if (Gdx.input.isKeyJustPressed(Keys.Z)
												&& gameTime > 0.5f) {
											battleTikiX = squareX - 4;
											battleTikiY = squareY - 4;
											text = false;
											tikiSelected = false;
										}
									}
									if (choice2Hover == true) {
										if (Gdx.input.isKeyJustPressed(Keys.UP)) {
											selectArrow.setPosition(
													choiceBox.getX() + 8,
													choiceBox.getY() + 80);
											choice2Hover = false;
											choice1Hover = true;
										}
									}
								}
							}
						}
					}
				}

			}
		}

		if (map == false) {
			mapMusic.stop();
		}

		if (map == true) {
			camera.position.x = Math.max(tikiX + 32,
					Gdx.graphics.getWidth() / 2);
			camera.position.y = Math.max(tikiY + 32,
					Gdx.graphics.getHeight() / 2);
			camera.position.x = Math.min(camera.position.x, mapBG.getWidth()
					- Gdx.graphics.getWidth() / 2);
			camera.position.y = Math.min(camera.position.y, mapBG.getHeight()
					- Gdx.graphics.getHeight() / 2);
		}
		if (battle == true) {
			camera.position.x = Gdx.graphics.getWidth() / 2;
			camera.position.y = Gdx.graphics.getHeight() / 2;
		}
		if (map == true || battle == true) {
			camera.update();
			batch.setProjectionMatrix(camera.combined);
		}

		// Drawing level 1 map
		if (level1 == true) {
			firstLevelRenderer.setView(camera);
			firstLevelRenderer.render();
		}

		batch.begin();
		if (game == true && map == true) {
			mapBG.draw(batch);
			levelWarp1.draw(batch);
		}

		if (battle == true) {
			TiledMapTileLayer layer = (TiledMapTileLayer) firstLevel
					.getLayers().get(0);
			Cell cell = layer.getCell((int) (squareX / 32),
					(int) (squareY / 32));
			int cellID = cell.getTile().getId();
			System.out.println(cellID);
			if (cellID == 783) {
				forest = true;
			} else if (cellID == 99) {
				plains = true;
			} else if (cellID == 214) {
				water = true;
			} else if (cellID == 808 || cellID == 809 || cellID == 810
					|| cellID == 840 || cellID == 841 || cellID == 842
					|| cellID == 872 || cellID == 873 || cellID == 874) {
				village = true;
			} else {
				impassable = true;
			}
			if (playerSelected == true) {
				for (int i = playerMov + 1; i > 0; i -= 1) {
					for (int j = playerMov + 1 - (i); j >= 0 - playerMov - 1
							+ (i); j -= 1) {
						atkSquare = new Sprite(redSquare);
						atkSquare.setPosition(playerX + 4 + i * 32, playerY + j
								* 32);
						atkSquare.draw(batch);
					}
				}
				for (int i = 0 - playerMov - 1; i < 1; i++) {
					for (int j = playerMov + 1 + (i); j >= 0 - playerMov - 1
							- (i); j--) {
						atkSquare = new Sprite(redSquare);
						atkSquare.setPosition(playerX + 4 + i * 32, playerY + j
								* 32);
						atkSquare.draw(batch);
					}
				}
				for (int i = playerMov; i > 0; i -= 1) {
					for (int j = playerMov - (i); j >= 0 - playerMov + (i); j -= 1) {
						movSquare = new Sprite(blueSquare);
						movSquare.setPosition(playerX + 4 + i * 32, playerY + j
								* 32);
						movSquare.draw(batch);
					}
				}
				for (int i = 0 - playerMov; i < 1; i++) {
					for (int j = playerMov + (i); j >= 0 - playerMov - (i); j--) {
						movSquare = new Sprite(blueSquare);
						movSquare.setPosition(playerX + 4 + i * 32, playerY + j
								* 32);
						movSquare.draw(batch);
					}
				}

			}
			if (tikiSelected == true) {
				for (int i = tikiMov + 1; i > 0; i -= 1) {
					for (int j = tikiMov + 1 - (i); j >= 0 - tikiMov - 1 + (i); j -= 1) {
						atkSquare = new Sprite(redSquare);
						atkSquare.setPosition(battleTikiX + 4 + i * 32, battleTikiY + j
								* 32);
						atkSquare.draw(batch);
					}
				}
				for (int i = 0 - tikiMov - 1; i < 1; i++) {
					for (int j = tikiMov + 1 + (i); j >= 0 - tikiMov - 1 - (i); j--) {
						atkSquare = new Sprite(redSquare);
						atkSquare.setPosition(battleTikiX + 4 + i * 32, battleTikiY + j
								* 32);
						atkSquare.draw(batch);
					}
				}
				for (int i = tikiMov; i > 0; i -= 1) {
					for (int j = tikiMov - (i); j >= 0 - tikiMov + (i); j -= 1) {
						movSquare = new Sprite(blueSquare);
						movSquare.setPosition(battleTikiX + 4 + i * 32, battleTikiY + j
								* 32);
						movSquare.draw(batch);
					}
				}
				for (int i = 0 - tikiMov; i < 1; i++) {
					for (int j = tikiMov + (i); j >= 0 - tikiMov - (i); j--) {
						movSquare = new Sprite(blueSquare);
						movSquare.setPosition(battleTikiX + 4 + i * 32, battleTikiY + j
								* 32);
						movSquare.draw(batch);
					}
				}

			}
			batch.draw(currentEnemy1Frame, enemy1X, enemy1Y);
			batch.draw(currentEnemy1Frame, enemy2X, enemy2Y);
			batch.draw(currentEnemy1Frame, enemy3X, enemy3Y);
			batch.draw(currentTikiBattleFrame, battleTikiX, battleTikiY);
			batch.draw(currentRobinFFrame, playerX, playerY);
			batch.draw(currentFrameSquare, squareX, squareY);
		}

		if (drawTiki == true) {
			switch (state) {
			case UP:
				batch.draw(currentFrame3, tikiX, tikiY);
				break;
			case DOWN:
				batch.draw(currentFrame4, tikiX, tikiY);
				break;
			case LEFT:
				batch.draw(currentFrame1, tikiX, tikiY);
				break;
			case RIGHT:
				batch.draw(currentFrame2, tikiX, tikiY);
				break;
			case IDLE:
				batch.draw(currentFrame, tikiX, tikiY);
				break;
			}
		}

		batch.end();

		uiCamera.update();
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();

		// Drawing menu
		if (menu == true) {
			menuBG.draw(batch);
			title.draw(batch);
			startButton.draw(batch);
			newGameButton.draw(batch);
			optionsButton.draw(batch);
			quitGameButton.draw(batch);
			buttonSelect.draw(batch);
		}

		// Drawing the game
		if (game == true && scene1 == true) {
			menuBG.draw(batch);
		}

		// Drawing all texts
		if (game == true && text == true) {
			if (length < texts.length()) {
				length++;
			}
			if (displayChoice1 == true && displayChoice2 == true) {
				choiceBox.draw(batch);
				choose1.draw(batch, choice1, choiceBox.getX() + 32,
						choiceBox.getY() + 112);
				choose2.draw(batch, choice2, choiceBox.getX() + 32,
						choiceBox.getY() + 56);
				selectArrow.draw(batch);
			}
			font.draw(batch, texts.substring(0, length), 16, 112);
			textBox.draw(batch);
		}
		if (battle == true) {
			font.draw(batch, turn, 0, 0);
		}

		// Drawing Tiki
		if (tikiTalk == true) {
			tikiTalkPortrait.draw(batch);
		}
		batch.end();

	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		menuMusic.dispose();
		introMusic.dispose();
		mapMusic.dispose();
		firstLevel.dispose();
		battleMusic.dispose();
	}
}
