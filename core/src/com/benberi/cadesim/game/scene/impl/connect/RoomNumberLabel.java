// Decompiled with: CFR 0.150
package com.benberi.cadesim.game.scene.impl.connect;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class RoomNumberLabel
extends Label {
    public static final int ROOM1 = 0;
    public static final int ROOM2 = 1;
    public static final int ROOM3 = 2;
    public static final int ROOM4 = 3;
    public static final int ROOM5 = 4;
    public static final int ROOM6 = 5;
    private int type;

    
    public RoomNumberLabel(CharSequence text, Label.LabelStyle style, int type) {
        super(text, style);
        this.type = type;
    }

    @Override
    public String toString() {
        return "" + this.getText();
    }

    public int getType() {
        return this.type;
    }
}
