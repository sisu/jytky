package fi.jytky;

import android.graphics.Color;

public class Player extends Unit {

	public Player() {
		pos = new Vector(.5f, .5f);
	}

	@Override
	public int getColor() {
		return Color.rgb(0,255,0);
	}
}
