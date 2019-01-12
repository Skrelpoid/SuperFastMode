package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class TickDurationPatches {

	@SpirePatch(clz = DiscardAction.class, method = "update")
	public static class TickDurationPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("tickDuration")) {
						m.replace("{ skrelpoid.superfastmode.SuperFastMode.tickDuration($0); }");
					}
				}
			};
		}
	}

}
