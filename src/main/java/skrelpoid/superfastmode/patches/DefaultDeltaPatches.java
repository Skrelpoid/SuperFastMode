package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

//All methods patched in this class should always get the real delta time

public class DefaultDeltaPatches {
	// @formatter:off

	@SpirePatch(clz = com.megacrit.cardcrawl.dungeons.AbstractDungeon.class, method = "update")
	// Fixes mouse events not registering on map and flickering "Select a Starting Room"
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "updateAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "updateMouse")
	@SpirePatch(clz = MapRoomNode.class, method = "update")
	@SpirePatch(clz = MapRoomNode.class, method = "oscillateColor")
	public static class DeltaPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getDeltaTime")) {
						m.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.getDelta(); }");
					}
				}
			};
		}
	}

	// Fixes Intents sometimes not being visible
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.combat.BattleStartEffect.class, method = "update")
	public static class IntentFix {
		@SpireInsertPatch(rloc = 10)
		public static void Insert(BattleStartEffect effect) {
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
				if (m.intent == Intent.DEBUG) {
					m.createIntent();
				}
			}
		}
	}
}
