package skrelpoid.superfastmode;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import basemod.IUIElement;
import basemod.ModPanel;

public class ProgressBar implements IUIElement {

	public ModPanel parent;
	public float x, y, width, height, progress;
	public Texture texture;
	public boolean multiplied;
	public float multiplier = 0.2f;
	public float start = 0.05f;

	public ProgressBar(ModPanel parent, float x, float y, float width, float height, Texture texture,
			boolean multiplied) {
		this.parent = parent;
		this.x = Settings.scale * x;
		this.y = Settings.scale * y;
		this.width = Settings.scale * width;
		this.height = Settings.scale * height;
		this.texture = texture;
		this.multiplied = multiplied;
		resetProgress();
	}

	public void resetProgress() {
		progress = start;
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		sb.draw(texture, x, y, width * progress, height);
	}

	@Override
	public void update() {
		float updateBy = multiplied ? SuperFastMode.getMultDelta() : SuperFastMode.getDelta();
		progress += updateBy * multiplier;
		if (progress > 1f) {
			resetProgress();
		}
	}

	@Override
	public int renderLayer() {
		return ModPanel.MIDDLE_LAYER;
	}

	@Override
	public int updateOrder() {
		return ModPanel.DEFAULT_UPDATE;
	}

}
