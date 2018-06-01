package skrelpoid.superfastmode.patches;

import com.badlogic.gdx.backends.lwjgl.LwjglInput;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.ReflectionHacks;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import skrelpoid.superfastmode.SuperFastMode;

//@formatter:off
public class LwjglInputPatches {

	// If the current input request is from SFM, do not update time
	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "update")
	public static class DeltaPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("updateTime")) {
						m.replace("{ if (!skrelpoid.superfastmode.SuperFastMode.isSFMInput) { $proceed($$); } }");
					}
				}
			};
		}
	}

	// If the current input request is from SFM, do not alter value off
	// justTouched
//	This is disabled because it throws VerifyError!
	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "updateMouse")
	public static class MousePatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(FieldAccess f) throws CannotCompileException {
					if (f.getFieldName().equals("justTouched") && f.getLineNumber() == 796) {
						f.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.isJustTouched(); }");
					}
				}
			};
		}
	}

	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "updateMouse")
	public static class MousePatch2 {
		@SpireInsertPatch(rloc = 1)
		public static void Insert(LwjglInput input) {
			ReflectionHacks.setPrivate(input, input.getClass(), "justTouched", SuperFastMode.isJustTouched());
		}
	}

	// If the current input request is from SFM, do not alter value off
	// justKeyPressed
	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "updateKeyboard")
	public static class KeyboardPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(FieldAccess f) throws CannotCompileException {
					if (f.getFieldName().equals("keyJustPressed") && f.getLineNumber() == 853) {
						f.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.isKeyJustPressed(); }");
					}
				}
			};
		}
	}
}
