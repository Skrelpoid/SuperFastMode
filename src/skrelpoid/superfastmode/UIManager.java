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

	public static ModPanel panel;
	public static ModLabel saveFeedback;

	@Override
	public void receivePostInitialize() {
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
	}

	private static void doNothing() {
	}

	private static ModLabeledToggleButton fpsToggle() {
		final float x = 350;
		final float y = 550;
		ModLabeledToggleButton fpsToggle = new ModLabeledToggleButton("Accelerate FPS", x, y, Color.WHITE,
				FontHelper.buttonLabelFont, SuperFastMode.isAcceleratedFPS, panel, (l) -> doNothing(),
				(b) -> updateFPSToggle(b));
		return fpsToggle;
	}

	private static void updateFPSToggle(ModToggleButton b) {
		SuperFastMode.isAcceleratedFPS = b.enabled;
		SuperFastMode.logger.info("Toggling accelerate FPS to " + b.enabled);
	}

	private static ModLabeledToggleButton deltaToggle() {
		final float x = 350;
		final float y = 420;
		ModLabeledToggleButton fpsToggle = new ModLabeledToggleButton("Multiply Delta", x, y, Color.WHITE,
				FontHelper.buttonLabelFont, SuperFastMode.isDeltaMultiplied, panel, (l) -> doNothing(),
				(b) -> updateDeltaToggle(b));
		return fpsToggle;
	}

	private static void updateDeltaToggle(ModToggleButton b) {
		SuperFastMode.isDeltaMultiplied = b.enabled;
		SuperFastMode.logger.info("Toggling multiply delta to " + b.enabled);
		saveFeedback.text = UNSAVED_SETTINGS;
	}

	private static ModLabeledToggleButton skipToggle() {
		final float x = 350;
		final float y = 690;
		ModLabeledToggleButton skipToggle = new ModLabeledToggleButton("Skip Some Animations", x, y, Color.WHITE,
				FontHelper.buttonLabelFont, SuperFastMode.isInstantLerp, panel, (l) -> doNothing(),
				(b) -> updateSkipToggle(b));
		return skipToggle;
	}

	private static void updateSkipToggle(ModToggleButton b) {
		SuperFastMode.isInstantLerp = b.enabled;
		SuperFastMode.logger.info("Toggling skip animations (lerp) to " + b.enabled);
		saveFeedback.text = UNSAVED_SETTINGS;
	}

	private static ModSlider fpsSlider() {
		final float x = 530;
		final float y = 580;
		ModSlider fpsSlider = new ModSlider("Multiply FPS by ", x, y, 5, "", panel, (s) -> updateFpsSlider(s));
		fpsSlider.setValue((SuperFastMode.additionalRenders + 1) * 0.1f);
		return fpsSlider;
	}

	private static void updateFpsSlider(ModSlider s) {
		// rounding and minus 1
		SuperFastMode.additionalRenders = (int) (s.value * 5 - 0.5f);
		saveFeedback.text = UNSAVED_SETTINGS;
	}

	private static ModSlider deltaSlider() {
		final float x = 530;
		final float y = 460;
		ModSlider deltaSlider = new ModSlider("Multiply Delta by ", x, y, 500, "%", panel, (s) -> updateDeltaSlider(s));
		deltaSlider.setValue(SuperFastMode.deltaMultiplier * 0.2f);
		return deltaSlider;
	}

	private static void updateDeltaSlider(ModSlider s) {
		SuperFastMode.deltaMultiplier = s.value * 5;
		saveFeedback.text = UNSAVED_SETTINGS;
	}

	private static ModButton saveButton() {
		final float x = 900;
		final float y = 290;
		ModButton saveButton = new ModButton(x, y, new Texture(Gdx.files.internal("img/save.png")), panel,
				(b) -> updateSaveButton(b));
		return saveButton;
	}

	private static void updateSaveButton(ModButton b) {
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
		saveFeedback = new ModLabel(UNSAVED_SETTINGS, x, y, panel, (l) -> doNothing());
		return saveFeedback;
	}

	private static ModLabel sliderInfo() {
		final float x = 1050;
		final float y = 700;
		ModLabel sliderInfo = new ModLabel("Turn everything on the left off before sliding!", x, y,
				FontHelper.tipBodyFont, panel, (l) -> doNothing());
		return sliderInfo;
	}

	private static ModLabel skipInfo() {
		final float x = 350;
		final float y = 750;
		ModLabel skipInfo = new ModLabel("Recommended! Can glitch out Cursor and Cards if off.", x, y,
				FontHelper.tipBodyFont, panel, (l) -> doNothing());
		return skipInfo;
	}

	private static ModLabel fpsInfo() {
		final float x = 350;
		final float y = 610;
		ModLabel fpsInfo = new ModLabel("Increases FPS. Can cause lag! (Disable Vsync for best results)", x, y,
				FontHelper.tipBodyFont, panel, (l) -> doNothing());
		return fpsInfo;
	}

	private static ModLabel deltaInfo() {
		final float x = 350;
		final float y = 470;
		ModLabel deltaInfo = new ModLabel("Makes the game think more time has elapsed.", x, y, FontHelper.tipBodyFont,
				panel, (l) -> doNothing());
		return deltaInfo;
	}
}
