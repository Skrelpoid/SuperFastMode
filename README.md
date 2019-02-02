# SuperFastMode
Mod for Slay the Spire that speeds up the Game.

This is done by making the game think more time has elapsed than actually has.
Also, SuperFastMode tries to only speed up Animations and Actions that are a bottleneck to the games speed.
This means that you can still enjoy most Monster and Player Animations at normal speed, while the actual gameplay is lightning fast.

## Installation Instructions
[Download the latest release of SuperFastMode.](https://github.com/Skrelpoid/SuperFastMode/releases)

If you don't already have ModTheSpire installed, [follow the instructions here.](https://github.com/kiooeht/ModTheSpire/wiki)

[Download the latest release of BaseMod](https://github.com/daviscook477/BaseMod/releases) and copy the jar into your mods folder.

Copy the SuperFastMode.jar file into the mods folder too. Then, start up ModTheSpire. Make sure BaseMod and SuperFastMode are checked.

Enjoy your new lightning fast experience!

## Compatibility with other mods
Because SuperFastMode speeds up the game by means of patching the engine, other mods get sped up too.
This is good if the other mod does not depend on an accurate time difference between frames.
However if the other mods need this, e.g. to calculate when 5 minutes are over, this will screw up the measurement
For this, the other mods should use `Gdx.graphics.getRawDeltaTime()` instead of `Gdx.graphics.getDeltaTime()`.
The implementation is exactly the same (it just returns the graphics object's `deltaTime` field, but this method won't be patched by SuperFastMode

## Building (only important for Modders)
This Mod uses Gradle for building. After making changes, you can double click buildMod.cmd 
(or run ./gradlew build or gradlew build in a console) to 
compile it and build the jar. The Mod will be in the /build/libs folder. Copy it over to 
your mods directory and test it out.

If the task fails and there is no jar in /build/libs make sure you have a folder named 
lib next to the folder of this Project (not in it) where you have the jars of ModTheSpire, 
BaseMod and the Game's jar (desktop-1.0.jar)

Your directory structure should look like this:
```
git (or wherever you store your git projects)
   \_YourProject
                \_src...
   \_lib
        \_ModTheSpire.jar
          BaseMod.jar
          desktop1.0.jar
```
You can run "./gradlew listJars" (without the ") to see which jars are found in your 
libs folder. If there are no jars listed, you probably haven't configured it correctly

If you want to import your mod into an IDE, run:
 - For Eclipse: ./gradlew eclipse OR gradlew eclipse
 - For IntelliJ Idea: ./gradlew idea OR gradlew idea

And then import the project using your IDE's Wizard