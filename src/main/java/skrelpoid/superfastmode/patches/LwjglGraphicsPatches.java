package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class LwjglGraphicsPatches {

	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglGraphics", method = "getDeltaTime")
	public static class DeltaPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(FieldAccess f) throws CannotCompileException {
					if (f.getFieldName().equals("deltaTime")) {
						f.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.getMultDelta($0); }");
					}
				}
			};
		}
	}

	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglGraphics", method = "getRawDeltaTime")
	public static class RawDeltaPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(FieldAccess f) throws CannotCompileException {
					if (f.getFieldName().equals("deltaTime")) {
						f.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.getMultDelta($0); }");
					}
				}
			};
		}
	}
}
