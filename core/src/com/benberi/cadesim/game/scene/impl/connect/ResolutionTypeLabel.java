package com.benberi.cadesim.game.scene.impl.connect;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ResolutionTypeLabel extends Label {

    public static final int sevenfifty = 1;
    public static final int defaultsize = 2;
    public static final int teneighty = 3;
    public static final int fourteenforty = 4;
    public static final int fourk = 5;

    private int type;

    public ResolutionTypeLabel(int type, CharSequence text, LabelStyle style) {
        super(text, style);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Resolution: " + getText();
    }

    public int getType() {
        return type;
    }
}
