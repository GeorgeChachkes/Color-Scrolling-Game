George Chachkes
For my game the player controls a character that moves around the screen. When 
it hits a colored circle the character changes to that color. The character has
to avoid the colored walls coming at it, but it can pass through walls that are
the same color as itself. For example, if the users character was green, the user
is allowed to hit a green wall. If any other color walls are hit, though, the 
user loses the game. They have to survive 45 seconds to win, and in the last 15 
seconds of the game everything speeds up. I drew some of the images in Inkscape,
and others were modified versions of the already provided images. The implementation
I am most proud of is the setColor method I created. It might not seem very impressive,
but keeping the user the correct color was a big issue while I was writing the code.
This method allows the code to always check 4 booleans to see which color the user
is at the time, and updates it to be that color whenever needed. A lot of the changes
I made were adding more possibilities to the collision and populate right methods.
They both now allow for many more images to be spawned in. For the timer, I make
1 second go down every 4 ticks when the game is moving slowly and every 6 ticks when
the game is moving quickly. 