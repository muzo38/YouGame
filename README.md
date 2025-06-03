# A game about You. 
-----------------
# Who we are:
We called ourselves UpDown, from an inside joke. 

Members and their roles:
Šarūnas Gromavičius - architect, designer and facilitator.
Mantas Lamauskas - builder, critic and exhibitor.

# Description of the game:

A medium length experience, where You the player can change the story based on your personality.
We are not sure yet, if we are going to do multiple big "branches" of the story, since
we want to work with some graphical stuff(more on that later). The inspiration for the project
comes from a video game called Firewatch(it's starting scene). The user get a sentence to continue
the story or gets two choices which change the outcome of the story. 

# The idea:

Game is based on your personality and the choices you make. Best case scenario, we have 6 scenes
representing each life period (childhood, adolescense so on...) in the players life. Not exactly
the users, but some fictional situations of a made up person. The story, atleast we think and wanted
it to be hard-hitting, emotional and something to think about before deciding on your choice.

# Use cases: ( ↓ TODO: A DIAGRAM OF THIS ↓ )

1. As intuitive as possible ( underlined possible options(sentences in the story) )
2. Most likely use of a mouse will be required to click certain choices.
3. Have a working escape button to close the game!
4. Need for a text block for helping the user write his name.

# Activities:

1. User launches the game.
2. Escape button is always available to close the game!
3. Main menu (something simple) greets the user.
4. Press any key to start.
5. Story starts.
6. Scene starts.
7. User can read through the current paragraph and when ready press a sentence to move forward.
8. This goes on until a choice is met.
9. When a choice is met, the user can pick from two different options.
10. The choice outcome is displayed (same as the story paragraph).
11. We move onto the next scene and these steps are repeated.
12. Once final and final choice hits maybe some ending credits or something.
13. Close the game.

# Tools and technologies: ( Put links if available for below mentioned. )

Git repository: Copy the link.
Language used: Java (the newest one, but specify the version!)
IDE: VSCODE
For graphical display we use Java Swing, which is a built in library. ( <- specify the version too )
Anything else you think is needed here...

# Files/classes ( OUTDATED )

Files/classes shown here are not fully yet developed and may be changed in the future.

1. Main.java - For initializing the game loop something like "new GameWindow();";
2. GameWindow.java - Responsible of showing a window when the program is launched;
3. SceneManager.java - To manage scenes(6 in total), as well as the menu. Basically what goes after what.
4. Different_scene_classes.java - Multiple files for handling the story as a text. Maybe even one huge file that stores the whole story(not sure).
5. TitlePanel.java - or MenuPanel, to show the menu and could be accessed through SceneManager.
6. Toolkit.java - The whole block of Java Swing methods for each text block. For example: The title of scenes, the story and the choices(not sure about this one too).

# Demonstration: ( :( )

Youtube link: 

# Workload for stage 1: ( )

Šarūnas Gromavičius:
1. Spent ~120minutes to come up with the story.
2. Spent ~40minutes to have a ready document with information about the project.

Mantas Lamauskas:
1. Spent ~20minutes creating the project and classes.
2. Spent ~140minutes to create PDF file.

# Workload for stage 2:

Šarūnas Gromavičius:
1. Spent ~180minutes coding a simple engine for the game.
2. Spent ~60minutes integrating the story into the code.
3. Spent ~20minutes discussing with the team about features.

Mantas Lamauskas:
1. Spent ~100minutes coding and testing.
2. Spent ~140minutes to create PDF file.
3. Spent ~20minutes discussing with the team about features.


Did do: implement the graphics
Didnt do: Sound design and fast skip :(

# Workload for stage 3:

Šarūnas Gromavičius:
1. Spent ~30minutes expirementing with game textures.
2. Spent ~120minutes creating and editing the textures.
3. Spent ~120minutes recording and editing all the demo videos.
4. Spent ~30minutes playtesting and getting feedback from other people.

Mantas Lamauskas:
1. Spent ~120minutes recording the videos.
2. Spent ~150minutes creating support for graphics in the code.
3. Spent ~30minutes unify the fonts.
