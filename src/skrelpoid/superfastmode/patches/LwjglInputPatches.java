package skrelpoid.superfastmode.patches;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

//@formatter:off
// These methods/Patches seem not to be working! They are making things worse
// For now I've disabled them
public class LwjglInputPatches {

	// If the current input request is from SFM, do not update time
//	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "update")
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
//	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "updateMouse")
	public static class MousePatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(FieldAccess f) throws CannotCompileException {
					if (f.getFieldName().equals("justTouched") && f.getLineNumber() == 796) {
						f.replace("{ $proceed(skrelpoid.superfastmode.SuperFastMode.isJustTouched()); }");
					}
				}
			};
		}
	}

	// If the current input request is from SFM, do not alter value off
	// justKeyPressed
//	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglInput", method = "updateKeyboard")
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
