package com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends Sprite {

    private float vy;

    public Bullet(Texture t, float vy) {
        super(t);
        this.vy = vy;
    }

    public boolean isExpired(int y) {
        if (y >= Gdx.graphics.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
    public void move() {
        translateY(vy);
    }
}
