package com;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class EnemyBullet extends Sprite {
    private float rotate;

    public EnemyBullet(Texture t, float rotate) {
        super(t);
        this.rotate = rotate;
    }
    public boolean isExpired(int y) {
        if (y <= 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean hitPlayer(Rectangle r, Rectangle n) {
        if (r.overlaps(n)) {
            return true;
        } else {
            return false;
        }
    }
    public void rotate() {
        rotate(rotate);
    }
    public void move() {
        translateY(0 - rotate);
    }
}
