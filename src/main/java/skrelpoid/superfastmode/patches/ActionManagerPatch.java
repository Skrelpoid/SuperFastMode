package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import basemod.ReflectionHacks;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = GameActionManager.class, method = "update")
public class ActionManagerPatch {
	public static ExprEditor Instrument() {
		return new ExprEditor() {
			@Override
			public void edit(MethodCall m) throws CannotCompileException {
				if (m.getMethodName().equals("update")) {
					m.replace(
							"{ skrelpoid.superfastmode.patches.ActionManagerPatch.updateActionUntilFinished($0); }");
				}
			}
		};
	}

	public static void updateActionUntilFinished(AbstractGameAction action) {
		int times = 0;
		do {
			action.update();
			if ((float) ReflectionHacks.getPrivate(action, AbstractGameAction.class,
					"duration") < 0.0F) {
				break;
			}
		} while (!action.isDone && times++ < 50);
	}

}

