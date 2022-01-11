package com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class MyGame extends Game {

    // Initializations
    SpriteBatch batch;
    ArrayList<Enemy> enemies;
    Player player;
    Texture playerTexture, enemyTexture, bulletTexture, eBulletTexture;
    ArrayList<Bullet> bullets;
    BitmapFont ui;
    float time, enemyTime;
    int score, enemyMove, level;
    ArrayList<EnemyBullet> eBullets;
    boolean gameOver, win, newLevel;
    Sprite goScreen, winScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Setting Textures
        playerTexture = new Texture("Sprites/Player.png");
        player = new Player(playerTexture, 5);
        bulletTexture = new Texture("Sprites/PlayerBullet.png");
        bullets = new ArrayList<>();
        enemyTexture = new Texture("Sprites/Enemy.png");
        enemies = new ArrayList<>();
        eBulletTexture = new Texture("Sprites/EnemyBullet.png");
        eBullets = new ArrayList<>();
        goScreen = new Sprite(new Texture("Sprites/GameOver.png"));
        goScreen.setPosition(Gdx.graphics.getWidth() / 2 - goScreen.getWidth() / 2, Gdx.graphics.getHeight() / 2 - goScreen.getHeight() / 2);
        winScreen = new Sprite(new Texture("Sprites/YouWin.png"));
        winScreen.setPosition(Gdx.graphics.getWidth() / 2 - winScreen.getWidth() / 2, Gdx.graphics.getHeight() / 2 - winScreen.getHeight() / 2);

        // Setting game booleans
        gameOver = false;
        win = false;

        // Score
        ui = new BitmapFont();

        player.setPosition(Gdx.graphics.getWidth() / 2 - player.getWidth() / 2, 0);
        score = 0;
        enemyMove = 1;
        level = 1;
        newLevel = true;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // When the game isn't over
        if (gameOver == false) {

            // Adding to the individual time values
            time += Gdx.graphics.getDeltaTime();
            enemyTime += Gdx.graphics.getDeltaTime();

            // To reset the level
            if (newLevel == true) {
                // Creating initial enemies
                for (int i = 0; i < 2 + level; i++) {
                    for (int j = 0; j < 8; j++) {
                        Enemy e = new Enemy(enemyTexture);
                        e.setPosition((64 * j) + 4, Gdx.graphics.getHeight() - (64 * i) - 32);
                        enemies.add(e);
                    }
                }
                newLevel = false;
            }

            // Game over when the enemy array is empty
            if (enemies.size() == 0) {
                gameOver = true;
                win = true;
            }

            // Creates bullets and add them to the array
            // Under the conditions: (certain amount of time passed after previous bullet)
            // (The bullet array is smaller than 3)
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && time > 0.5) {
                if (bullets.size() <= 2) {
                    Bullet b = new Bullet(bulletTexture, 5);
                    b.setPosition(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() - b.getHeight());
                    bullets.add(b);
                    time = 0;
                }
            }

            // Enemy bullets are shot every 1 second
            // Sets position to the position of the enemy who fired it
            // Picks a random enemy
            if (enemyTime >= 1) {
                if (eBullets.size() <= 1) {
                    EnemyBullet b = new EnemyBullet(eBulletTexture, 3);
                    Enemy e = enemies.get((int) (Math.random() * enemies.size()));
                    b.setPosition(e.getX() + (e.getWidth() / 2), e.getY());
                    eBullets.add(b);
                }
            }

            // Checks to see if player can move right
            // If they can, moves them
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (player.canMoveRight((int) player.getX(), (int) player.getWidth())) {
                    player.moveRight();
                }
            }

            // Same as above, but with left
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (player.canMoveLeft((int) player.getX(), (int) player.getWidth())) {
                    player.moveLeft();
                }
            }

            // If any enemy is hit by a bullet, both the bullet and the enemy disappear
            // Adds 100 to score
            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy e = enemies.get(i);
                for (int j = bullets.size() - 1; j >= 0; j--) {
                    Bullet b = bullets.get(j);
                    if (b.getBoundingRectangle().overlaps(e.getBoundingRectangle())) {
                        bullets.remove(j);
                        enemies.remove(i);
                        score += 100;
                        break;
                    }
                }
            }
            // Begins sprite batch
            batch.begin();
            player.draw(batch);
            // Draws bullets, checks if they're expired and moves bullets
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet b = bullets.get(i);
                b.draw(batch);
                if (b.isExpired((int) b.getY())) {
                    bullets.remove(i);
                }
                b.move();
                for (int j = eBullets.size() - 1; j >= 0; j--) {
                    EnemyBullet eb = eBullets.get(j);
                    if (b.getBoundingRectangle().overlaps(eb.getBoundingRectangle())) {
                        bullets.remove(i);
                        eBullets.remove(j);
                        break;
                    }
                }
            }
            // Same as above, but for enemy bullets
            for (int i = eBullets.size() - 1; i >= 0; i--) {
                EnemyBullet b = eBullets.get(i);
                b.rotate();
                b.move();
                b.draw(batch);
                if (b.isExpired((int) b.getY())) {
                    eBullets.remove(i);
                }
                if (b.hitPlayer(b.getBoundingRectangle(), player.getBoundingRectangle())) {
                    eBullets.remove(i);
                    gameOver = true;
                }
            }
            // Draws enemies and moves them according to the enemyTime int
            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy e = enemies.get(i);
                if (enemyTime >= 1) {
                    if (enemyMove <= 3 && enemyMove > 0) {
                        e.moveX();
                    } else if (enemyMove == 4) {
                        e.moveY();
                    } else if (enemyMove >= -3 && enemyMove < 0) {
                        e.moveXBack();
                    } else if (enemyMove == 0) {
                        e.moveY();
                    }
                }
                e.draw(batch);
            }
            // resets the enemy time when over 1 and sets the enemies move
            if (enemyTime > 1) {
                enemyTime = 0;
                if (enemyMove < 4) {
                    enemyMove++;
                } else if (enemyMove == 4) {
                    enemyMove = -3;
                }
            }
            // Draws the score
            ui.draw(batch, "Score: " + score, 16, 32);
            ui.draw(batch, "Level: " + level, 16, 16);
            batch.end();
        } else if (gameOver == true && win == false) {
            // Draws game over screen
            // Sets score to 0 because of loss
            score = 0;
            batch.begin();
            ui.draw(batch, "Score: " + score, 16, 32);
            ui.draw(batch, "Level: " + level, 16, 16);
            goScreen.draw(batch);
            batch.end();
        } else if (gameOver == true && win == true) {
            // Draws win screen and prints score
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                newLevel = true;
                if (level < 4) {
                    level++;
                } else {
                    level = 1;
                }
                gameOver = false;
                win = false;
            }
            batch.begin();
            ui.draw(batch, "Score: " + score, 16, 32);
            ui.draw(batch, "Level: " + level, 16, 16);
            ui.draw(batch, "Press ENTER to continue to next level.", 16, 64);
            winScreen.draw(batch);
            batch.end();
        }
    }
}