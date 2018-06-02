package skrelpoid.superfastmode;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;

@SpireInitializer
public class SuperFastMode {

	public static final String AUTHOR = "Skrelpoid";
	public static final String MOD_NAME = "SuperFastMode";
	public static final String DESCRIPTION = MOD_NAME;

	public static final Logger logger = LogManager.getLogger(SuperFastMode.class.getName());

	public static float deltaMultiplier = 3;
	public static Field deltaField;
	public static boolean isDeltaMultiplied = true;
	public static boolean isInstantLerp = true;
	public static SpireConfig config;

	// if the current request for the input to update is called from

	// TODO Add Changelog
	// TODO player and monster idle should not be affected by multiplied delta
	// TODO UI rendering should not be affected by multiplied delta

	public static void initialize() {
		logger.info("Initializing SuperFastMode");
		BaseMod.subscribe(new UIManager());
		try {
			config = new SpireConfig(MOD_NAME, MOD_NAME + "Config");
			initConfig();
			loadConfig();
			deltaField = LwjglGraphics.class.getDeclaredField("deltaTime");
			deltaField.setAccessible(true);
		} catch (Exception ex) {
			logger.catching(ex);
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
				logger.catching(ex);
			}
		}
	}

	private static void loadConfig() {
		isDeltaMultiplied = config.getBool("isDeltaMultiplied");
		isInstantLerp = config.getBool("isInstantLerp");
		deltaMultiplier = config.getFloat("deltaMultiplier");
	}

	public static void writeConfig() {
		config.setBool("isDeltaMultiplied", isDeltaMultiplied);
		config.setBool("isInstantLerp", isInstantLerp);
		config.setFloat("deltaMultiplier", deltaMultiplier);
	}

	public static float getMultDelta(Object graphics) {
		float mult = isDeltaMultiplied ? SuperFastMode.deltaMultiplier : 1;
		return mult * getDelta(graphics);
	}

	public static float getMultDelta() {
		return getMultDelta(Gdx.graphics);
	}

	// Gets current delta but can't be higher than max
	public static float getDelta(float max) {
		return Math.min(max, getDelta());
	}

	public static float getDelta(Object graphics) {
		float delta = 0.016f;
		try {
			delta = deltaField.getFloat(graphics);
		} catch (Exception ex) {
			logger.catching(ex);
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

}
