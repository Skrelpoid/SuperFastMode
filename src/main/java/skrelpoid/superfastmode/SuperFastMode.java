package skrelpoid.superfastmode;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.BaseMod;
import basemod.ReflectionHacks;

@SpireInitializer
public class SuperFastMode {

	public static final String AUTHOR = "Skrelpoid";
	public static final String MOD_NAME = "SuperFastMode";
	public static final String DESCRIPTION = MOD_NAME;

	public static final Logger logger = LogManager.getLogger(SuperFastMode.class.getName());

	public static float deltaMultiplier = 2;
	public static Field deltaField;
	public static boolean isDeltaMultiplied = true;
	public static boolean isInstantLerp = true;
	public static SpireConfig config;

	// TODO UI rendering should not be affected by multiplied delta. WIP

	public static void initialize() {
		logger.info("Initializing SuperFastMode");
		BaseMod.subscribe(new UIManager());
		try {
			config = new SpireConfig(MOD_NAME, MOD_NAME + "Config");
			initConfig();
			loadConfig();
			deltaField = LwjglGraphics.class.getDeclaredField("deltaTime");
			if (Loader.LWJGL3_ENABLED) {
			    deltaField = Class.forName("com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics").getDeclaredField("deltaTime");
			}
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

	public static float getMultDelta(Graphics graphics) {
		float mult = isDeltaMultiplied ? SuperFastMode.deltaMultiplier : 1;
		return mult * getDelta(graphics);
	}

	public static float getMultDelta() {
		return getMultDelta(Gdx.graphics);
	}

	// Gets multiplied delta but can't be higher than max
	public static float getMultDelta(float max) {
		return Math.min(max, getMultDelta());
	}

	// Gets current delta but can't be higher than max
	public static float getDelta(float max) {
		return Math.min(max, getDelta());
	}

	/**
	 * This is the so called Raw Delta Time, or delta time before multiplying.
	 * This is because we get the value from the deltaTime Field of the graphics object.
	 * We patch the getDeltaTime of Graphics to override that logic to always be multiplied.
	 * This method gets called by the game. The internal field is an implementation detail we use to 
	 * have both the modified and the unmodified delta
	 * 
	 * @param graphics The Graphics object to get the delta from
	 * @return the unmodified delta time
	 */
	public static float getDelta(Graphics graphics) {
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
	
	public static void tickDuration(AbstractGameAction a) {
		float duration = (float) ReflectionHacks.getPrivate(a, AbstractGameAction.class, "duration");
		duration -= getDelta();
		ReflectionHacks.setPrivate(a, AbstractGameAction.class, "duration", duration);
        if (duration < 0.0f) {
            a.isDone = true;
        }
	}

	public static void instantLerp(float[] start, float target) {
		if (isInstantLerp) {
			start[0] = target;
		}
	}

	public static void updateVFX(AbstractGameEffect effect) {
		// Copied from AbstractGameEffect.update()
		effect.duration -= getDelta();
		Color c = (Color) (ReflectionHacks.getPrivate(effect, AbstractGameEffect.class, "color"));
		if (effect.duration < effect.startingDuration / 2.0F) {
			c.a = (effect.duration / (effect.startingDuration / 2.0F));
		}

		if (effect.duration < 0.0F) {
			effect.isDone = true;
			c.a = 0.0F;
		}
	}
}
