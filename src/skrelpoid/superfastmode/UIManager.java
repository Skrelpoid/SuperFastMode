package skrelpoid.superfastmode;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.BaseMod;
import basemod.ModButton;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.ModSlider;
import basemod.ModToggleButton;
import basemod.interfaces.PostInitializeSubscriber;

public class UIManager implements PostInitializeSubscriber {
	public static final String UNSAVED_SETTINGS = "Click Button to Save Settings:";
	public static final int DELTA_SLIDER_MULTI = 10;
	public static final float MIN_DELTA = 0.05f;

	public static ModPanel panel;
	public static ModLabel saveFeedback;
	public static boolean speedUpdated;
	public static Texture progressTexture;
	public static ProgressBar progressBar, multProgressBar;

	@Override
	public void receivePostInitialize() {
		speedUpdated = true;
		progressTexture = new Texture("img/progress.png");
		buildUI();
		BaseMod.registerModBadge(new Texture("img/modBadge.png"), SuperFastMode.MOD_NAME, SuperFastMode.AUTHOR,
				SuperFastMode.DESCRIPTION, panel);
	}

	public static void buildUI() {
		panel = new ModPanel();
		panel.addUIElement(deltaToggle());
		panel.addUIElement(skipToggle());
		panel.addUIElement(creatureToggle());
		panel.addUIElement(deltaSlider());
		panel.addUIElement(saveButton());
		panel.addUIElement(saveFeedback());
		panel.addUIElement(skipInfo());
		panel.addUIElement(deltaInfo());
		panel.addUIElement(speedInfo());
		panel.addUIElement(progress());
		panel.addUIElement(multProgress());
		panel.addUIElement(progressInfo());
		panel.addUIElement(multProgressInfo());
	}

	private static ModLabeledToggleButton deltaToggle() {
		final float x = 350;
		final float y = 550;
		return new ModLabeledToggleButton("Speed Up Game", x, y, Color.WHITE, FontHelper.tipBodyFont,
				SuperFastMode.isDeltaMultiplied, panel, l -> {
				}, UIManager::updateDeltaToggle);
	}

	private static void updateDeltaToggle(ModToggleButton b) {
		SuperFastMode.isDeltaMultiplied = b.enabled;
		SuperFastMode.logger.info("Toggling multiply delta to " + b.enabled);
		speedUpdated = true;
	}

	private static ModLabeledToggleButton skipToggle() {
		final float x = 350;
		final float y = 690;
		return new ModLabeledToggleButton("Skip Some Actions", x, y, Color.WHITE, FontHelper.tipBodyFont,
				SuperFastMode.isInstantLerp, panel, l -> {
				}, UIManager::updateSkipToggle);
	}

	private static void updateSkipToggle(ModToggleButton b) {
		SuperFastMode.isInstantLerp = b.enabled;
		SuperFastMode.logger.info("Toggling skip animations (lerp) to " + b.enabled);
		speedUpdated = true;
	}

	private static ModLabeledToggleButton creatureToggle() {
		final float x = 750;
		final float y = 690;
		return new ModLabeledToggleButton("Don't speed up creature's animations (Recommended)", x, y, Color.WHITE,
				FontHelper.tipBodyFont, SuperFastMode.dontSpeedUpCreatures, panel, l -> {
				}, UIManager::updateCreatureToggle);
	}

	private static void updateCreatureToggle(ModToggleButton b) {
		SuperFastMode.dontSpeedUpCreatures = b.enabled;
		SuperFastMode.logger.info("Toggling not speeding up creatures to " + b.enabled);
		speedUpdated = true;
	}

	private static ModSlider deltaSlider() {
		final float x = 530;
		final float y = 580;
		ModSlider deltaSlider = new ModSlider("Multiply by: ", x, y, DELTA_SLIDER_MULTI * 100, "%", panel,
				UIManager::updateDeltaSlider);
		deltaSlider.setValue(SuperFastMode.deltaMultiplier * 1 / DELTA_SLIDER_MULTI);
		return deltaSlider;
	}

	private static void updateDeltaSlider(ModSlider s) {
		// deltaMultiplier should never be 0, so never be > MIN_DELTA
		SuperFastMode.deltaMultiplier = Math.max(s.value * DELTA_SLIDER_MULTI, MIN_DELTA);
		speedUpdated = true;
	}

	private static ModButton saveButton() {
		final float x = 900;
		final float y = 290;
		return new ModButton(x, y, new Texture(Gdx.files.internal("img/save.png")), panel, b -> updateSaveButton());
	}

	private static void updateSaveButton() {
		try {
			SuperFastMode.writeConfig();
			SuperFastMode.config.save();
			saveFeedback.text = "Saved Settings!";
		} catch (IOException ex) {
			SuperFastMode.logger.catching(ex);
		}
	}

	private static ModLabel saveFeedback() {
		final float x = 430;
		final float y = 315;
		saveFeedback = new ModLabel(UNSAVED_SETTINGS, x, y, panel, l -> {
		});
		return saveFeedback;
	}

	private static ModLabel skipInfo() {
		final float x = 350;
		final float y = 750;
		return new ModLabel("Makes some Actions instant", x, y, FontHelper.tipBodyFont, panel, l -> {
		});
	}

	private static ModLabel deltaInfo() {
		final float x = 350;
		final float y = 610;
		return new ModLabel("Makes the game think more time has elapsed. Doesn't cause lag!", x, y,
				FontHelper.tipBodyFont, panel, l -> {
				});
	}

	private static ModLabel speedInfo() {
		final float x = 1050;
		final float y = 315;
		return new ModLabel("", x, y, FontHelper.buttonLabelFont, panel, UIManager::updateSpeedInfo);
	}

	private static void updateSpeedInfo(ModLabel l) {
		if (speedUpdated) {
			speedUpdated = false;
			saveFeedback.text = UNSAVED_SETTINGS;
			l.text = "Game Speed is " + (int) (calculateSpeed() * 100 + 0.5f) + "%";
			progressBar.resetProgress();
			multProgressBar.resetProgress();
		}
	}

	private static float calculateSpeed() {
		return SuperFastMode.isDeltaMultiplied ? SuperFastMode.deltaMultiplier : 1;
	}

	private static ProgressBar progress() {
		final float x = 600;
		final float y = 460;
		final float width = 600;
		final float height = 32;
		progressBar = new ProgressBar(panel, x, y, width, height, progressTexture, false);
		return progressBar;
	}

	private static ProgressBar multProgress() {
		final float x = 600;
		final float y = 390;
		final float width = 600;
		final float height = 32;
		multProgressBar = new ProgressBar(panel, x, y, width, height, progressTexture, true);
		return multProgressBar;
	}

	private static ModLabel progressInfo() {
		final float x = 450;
		final float y = 471;
		return new ModLabel("Normal Speed:", x, y, FontHelper.tipBodyFont, panel, l -> {
		});
	}

	private static ModLabel multProgressInfo() {
		final float x = 450;
		final float y = 401;
		return new ModLabel("Accelerated:", x, y, FontHelper.tipBodyFont, panel, l -> {
		});
	}

}
