package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class LwjglGraphicsPatches {

	@SpirePatch(clz = com.badlogic.gdx.backends.lwjgl.LwjglGraphics.class, method = "getDeltaTime")
	// If --imgui is set or StS is started on a steam deck, lwjgl3 is loaded instead
	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics", method = "getDeltaTime", optional = true)
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

}
