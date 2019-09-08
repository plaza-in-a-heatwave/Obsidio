package com.benberi.cadesim.game.scene.impl.connect;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ShipTypeLabel extends Label {

	public static final int WB = 2;
    public static final int WF = 3;
	public static final int XEBEC = 4;
	public static final int JUNK = 5;
    public static final int WG = 6;

    private int type;

    public ShipTypeLabel(int type, CharSequence text, LabelStyle style) {
        super(text, style);
        this.type = type;
    }

    @Override
    public String toString() {
        return "" + getText();
    }

    public int getType() {
        return type;
    }
}
