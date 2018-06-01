package skrelpoid.superfastmode;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglInput;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;

import basemod.BaseMod;

@SpireInitializer
public class SuperFastMode {

	public static final String AUTHOR = "Skrelpoid";
	public static final String MOD_NAME = "SuperFastMode";
	public static final String DESCRIPTION = MOD_NAME;

	public static final Logger logger = LogManager.getLogger(SuperFastMode.class.getName());

	public static float deltaMultiplier = 1.5f;
	public static int additionalRenders = 1;
	public static Field deltaField;
	public static Field keyField;
	public static Method inputProcessing;
	public static boolean isDeltaMultiplied = true;
	public static boolean isAcceleratedFPS = true;
	public static boolean isInstantLerp = true;
	public static SpireConfig config;

	// if the current request for the input to update is called from
	// SuperFastMode. Used in LwjglInputPatches to avoid conflicts
	public static boolean isSFMInput = false;

	// FIX: Mouse clicks (especially on the map) are sometimes not registered

	public static void initialize() {
		logger.info("Initializing SuperFastMode");
		Settings.isDebug = true; // TODO remove
		BaseMod.subscribe(new UIManager());
		try {
			config = new SpireConfig(MOD_NAME, MOD_NAME + "Config");
			initConfig();
			loadConfig();
			deltaField = LwjglGraphics.class.getDeclaredField("deltaTime");
			deltaField.setAccessible(true);
			keyField = LwjglInput.class.getDeclaredField("keyJustPressed");
			keyField.setAccessible(true);
			inputProcessing = LwjglInput.class.getDeclaredMethod("processEvents");
			inputProcessing.setAccessible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void initConfig() {
		String result = config.getString("EXISTS");
		String exists = "YES INDEED I EXIST";
		if (result == null || !result.equals(exists)) {
			config.setString("EXISTS", exists);
			writeConfig();
			try {
				config.save();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void loadConfig() {
		isDeltaMultiplied = config.getBool("isDeltaMultiplied");
		isAcceleratedFPS = config.getBool("isAcceleratedFPS");
		isInstantLerp = config.getBool("isInstantLerp");
		deltaMultiplier = config.getFloat("deltaMultiplier");
		additionalRenders = config.getInt("additionalRenders");
	}

	public static void writeConfig() {
		config.setBool("isDeltaMultiplied", isDeltaMultiplied);
		config.setBool("isAcceleratedFPS", isAcceleratedFPS);
		config.setBool("isInstantLerp", isInstantLerp);
		config.setFloat("deltaMultiplier", deltaMultiplier);
		config.setInt("additionalRenders", additionalRenders);
	}

	public static float getMultDelta(Object graphics) {
		float mult = isDeltaMultiplied ? SuperFastMode.deltaMultiplier : 1;
		return mult * getDelta(graphics);
	}

	public static float getMultDelta() {
		return getMultDelta(Gdx.graphics);
	}

	public static float getDelta(Object graphics) {
		float delta = 0.016f;
		try {
			delta = deltaField.getFloat(graphics);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return delta;
	}

	public static float getDelta() {
		return getDelta(Gdx.graphics);
	}

	public static void lerp(float[] start, float target) {
		if (isInstantLerp) {
			start[0] = target;
		}
	}

	public static void accelerateFPS() {
		if (isAcceleratedFPS) {
			isSFMInput = true;
			for (int i = 0; i < additionalRenders; i++) {
				try {
					Gdx.app.getApplicationListener().render();
					Display.update(false);
					((LwjglInput) Gdx.input).update();
					if (Gdx.input == null) {
						System.out.println("Gdx input null");
					}
					if (inputProcessing == null) {
						System.out.println("inputprocessing null");
					}
					inputProcessing.invoke(Gdx.input);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			isSFMInput = false;
		}
	}

	public static boolean isKeyJustPressed() {
		if (isSFMInput) {
			try {
				return keyField.getBoolean(Gdx.input);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public static boolean isJustTouched() {
		if (isSFMInput) {
			return Gdx.input.justTouched();
		}
		return false;
	}

}
