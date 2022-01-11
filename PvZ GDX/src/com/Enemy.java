package com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy extends Sprite{

    public Enemy(Texture e) {
        super(e);
    }
    public void moveX() {
        translateX(8);
    }
    public void moveY() {
        translateY(-8);
    }
    public void moveXBack() {
        translateX(-8);
    }
}
