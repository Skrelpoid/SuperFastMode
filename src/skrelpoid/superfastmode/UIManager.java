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
	public static final int FPS_SLIDER_MULTI = 5;
	public static final int DELTA_SLIDER_MULTI = 20;
	public static final float MIN_DELTA = 0.05f;

	public static ModPanel panel;
	public static ModLabel saveFeedback;
	public static boolean speedUpdated;

	@Override
	public void receivePostInitialize() {
		speedUpdated = true;
		buildUI();
		BaseMod.registerModBadge(new Texture("img/modBadge.png"), SuperFastMode.MOD_NAME, SuperFastMode.AUTHOR,
				SuperFastMode.DESCRIPTION, panel);
	}

	public static void buildUI() {
		panel = new ModPanel();
		panel.addUIElement(fpsToggle());
		panel.addUIElement(deltaToggle());
		panel.addUIElement(skipToggle());
		panel.addUIElement(fpsSlider());
		panel.addUIElement(deltaSlider());
		panel.addUIElement(saveButton());
		panel.addUIElement(saveFeedback());
		panel.addUIElement(sliderInfo());
		panel.addUIElement(skipInfo());
		panel.addUIElement(fpsInfo());
		panel.addUIElement(deltaInfo());
		panel.addUIElement(speedInfo());
	}

	private static ModLabeledToggleButton fpsToggle() {
		final float x = 350;
		final float y = 550;
		ModLabeledToggleButton fpsToggle = new ModLabeledToggleButton("Accelerate FPS", x, y, Color.WHITE,
				FontHelper.buttonLabelFont, SuperFastMode.isAcceleratedFPS, panel, (l) -> {
				}, (b) -> updateFPSToggle(b));
		return fpsToggle;
	}

	private static void updateFPSToggle(ModToggleButton b) {
		SuperFastMode.isAcceleratedFPS = b.enabled;
		SuperFastMode.logger.info("Toggling accelerate FPS to " + b.enabled);
		speedUpdated = true;
	}

	private static ModLabeledToggleButton deltaToggle() {
		final float x = 350;
		final float y = 690;
		ModLabeledToggleButton fpsToggle = new ModLabeledToggleButton("Multiply Delta", x, y, Color.WHITE,
				FontHelper.buttonLabelFont, SuperFastMode.isDeltaMultiplied, panel, (l) -> {
				}, (b) -> updateDeltaToggle(b));
		return fpsToggle;
	}

	private static void updateDeltaToggle(ModToggleButton b) {
		SuperFastMode.isDeltaMultiplied = b.enabled;
		SuperFastMode.logger.info("Toggling multiply delta to " + b.enabled);
		speedUpdated = true;
	}

	private static ModLabeledToggleButton skipToggle() {
		final float x = 350;
		final float y = 420;
		ModLabeledToggleButton skipToggle = new ModLabeledToggleButton("Skip Some Animations", x, y, Color.WHITE,
				FontHelper.buttonLabelFont, SuperFastMode.isInstantLerp, panel, (l) -> {
				}, (b) -> updateSkipToggle(b));
		return skipToggle;
	}

	private static void updateSkipToggle(ModToggleButton b) {
		SuperFastMode.isInstantLerp = b.enabled;
		SuperFastMode.logger.info("Toggling skip animations (lerp) to " + b.enabled);
		speedUpdated = true;

	}

	private static ModSlider fpsSlider() {
		final float x = 530;
		final float y = 460;
		ModSlider fpsSlider = new ModSlider("Multiply FPS by ", x, y, FPS_SLIDER_MULTI, "", panel,
				(s) -> updateFpsSlider(s));
		fpsSlider.setValue((SuperFastMode.additionalRenders + 1) / (float) FPS_SLIDER_MULTI);
		return fpsSlider;
	}

	private static void updateFpsSlider(ModSlider s) {
		// AdditionalRenders should never be less than 0;
		SuperFastMode.additionalRenders = Math.max((int) (s.value * FPS_SLIDER_MULTI - 0.5f), 0);
		speedUpdated = true;
	}

	private static ModSlider deltaSlider() {
		final float x = 530;
		final float y = 580;
		ModSlider deltaSlider = new ModSlider("Multiply Delta by ", x, y, DELTA_SLIDER_MULTI * 100, "%", panel,
				(s) -> updateDeltaSlider(s));
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
		ModButton saveButton = new ModButton(x, y, new Texture(Gdx.files.internal("img/save.png")), panel,
				(b) -> updateSaveButton());
		return saveButton;
	}

	private static void updateSaveButton() {
		try {
			SuperFastMode.writeConfig();
			SuperFastMode.config.save();
			saveFeedback.text = "Saved Settings!";
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static ModLabel saveFeedback() {
		final float x = 430;
		final float y = 315;
		saveFeedback = new ModLabel(UNSAVED_SETTINGS, x, y, panel, (l) -> {
		});
		return saveFeedback;
	}

	private static ModLabel sliderInfo() {
		final float x = 1050;
		final float y = 700;
		ModLabel sliderInfo = new ModLabel("Turn everything on the left off before sliding!", x, y,
				FontHelper.tipBodyFont, panel, (l) -> {
				});
		return sliderInfo;
	}

	private static ModLabel skipInfo() {
		final float x = 350;
		final float y = 750;
		ModLabel skipInfo = new ModLabel("Makes some Animations instant", x, y, FontHelper.tipBodyFont, panel, (l) -> {
		});
		return skipInfo;
	}

	private static ModLabel fpsInfo() {
		final float x = 350;
		final float y = 470;
		ModLabel fpsInfo = new ModLabel("EXPERIMENTAL! Can cause lag and delay Input! (Disable Vsync for best results)",
				x, y, FontHelper.tipBodyFont, panel, (l) -> {
				});
		return fpsInfo;
	}

	private static ModLabel deltaInfo() {
		final float x = 350;
		final float y = 610;
		ModLabel deltaInfo = new ModLabel("Makes the game think more time has elapsed. Doesn't cause lag!", x, y,
				FontHelper.tipBodyFont, panel, (l) -> {
				});
		return deltaInfo;
	}

	private static ModLabel speedInfo() {
		final float x = 1050;
		final float y = 315;
		ModLabel speedInfo = new ModLabel("", x, y, FontHelper.buttonLabelFont, panel, (l) -> updateSpeedInfo(l));
		return speedInfo;
	}

	private static void updateSpeedInfo(ModLabel l) {
		if (speedUpdated) {
			speedUpdated = false;
			saveFeedback.text = UNSAVED_SETTINGS;
			l.text = "Game Speed is " + (int) (calculateSpeed() * 100 + 0.5f) + "%";
		}
	}

	private static float calculateSpeed() {
		int fpsAccelerated = SuperFastMode.isAcceleratedFPS ? SuperFastMode.additionalRenders + 1 : 1;
		float deltaMult = SuperFastMode.isDeltaMultiplied ? SuperFastMode.deltaMultiplier : 1;
		return fpsAccelerated * deltaMult;
	}

}
