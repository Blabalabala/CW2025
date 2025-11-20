Github link:
https://github.com/AlvinChongChong/CW2025

Compilation Instruction : 
1. Download the source code.
2. Make sure the JavaFX SDK is set up.
3. Add this in VM ( --module-path "C:\Users\WINDOWS 11\Desktop\javafx\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml  )


Implemented and Working Properly:

  Hard Drop – Instantly drop the brick to the bottom.
 Line Clearing – Complete rows are removed and the score updates accordingly.
 Score and Line Counter – Tracks the player’s current score and lines cleared.
 Next Block Preview – Shows the upcoming brick.
 Pause/Resume – The game can be paused and resumed.
Background Music – Plays looping music during gameplay.
Ghost Brick-Show where the falling brick going to land at
Restart-The game can be restart

Implemented but Not Working Properly:
All working as expected

Features Not Implemented:
Hold brick feature-Not enough of time
Main Menu- Not enough of time
Difficulty-Not enough of time
Level-Not enough of time

New Java Classes:

Added MusicPlayerWav.java-Now has background music 


Modified Java Classes:
GuiController.java-Add score label,add timer,add pause and resumeand restart function,add ghost brick function,add using image as timer and score and line counter,add next brick preview

GameController.java-To display line clear score,prevent brick collision function

Score.java-Function to calculate score and line clear

SimpleBoard.java-Make brick spawn higher,add score and line clear

Main.java-Play background music,screen maximized itself as launch

InputEventListener.java-Add canMoveDown method

EventType.java-Add Hard drop type
 
Unexpected Problems:
Implemented ghost brick but as static brick stack up ghost brick start to overlay but is solve now
Ghost brick and static block float above the frame
Falling brick drop before it reach the end
Static brick and frame moving when changing falling brick position
