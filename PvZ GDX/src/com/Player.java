package com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {

    private int vx;

    public Player (Texture p, int vx) {
        super(p);
        this.vx = vx;
    }

    public boolean canMoveRight(int x, int w) {
        if (x <= Gdx.graphics.getWidth() - w) {
            return true;
        } else {
            return false;
        }
    }
    public boolean canMoveLeft(int x, int w) {
        if (x >= 0) {
            return true;
        } else {
            return false;
        }
    }
    public void moveRight() {
        translateX(vx);
    }
    public void moveLeft() {
        translateX(0 - vx);
    }
}