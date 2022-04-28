import java.awt.event.KeyEvent;

public class GeorgeChachkesGame extends AbstractGame {
    
    private static final int INTRO = 0; 

    private String PLAYER_IMG = "images/user.gif";// All the new images added below
    private String PLAYER_YELLOW_IMG = "images/userYellow.gif";
    private String PLAYER_BLUE_IMG = "images/userBlue.gif";
    private String PLAYER_RED_IMG = "images/userRed.gif";
    private String PLAYER_GREEN_IMG = "images/userGreen.gif";
    private String SPLASH_IMG = "images/startScreen.png"; 
    //private String AVOID_IMG = "images/avoid.gif";
    private String GET_IMG = "images/get.gif";
    private String YELLOW_BLOCK_IMG = "images/yellowBlock.png";
    private String BLUE_BLOCK_IMG = "images/blueBlock.png";
    private String RED_BLOCK_IMG = "images/redBlock.png";
    private String GREEN_BLOCK_IMG = "images/greenBlock.png";
    // ADD others for Avoid/Get items 
    private String YELLOW_CHANGE_IMG = "images/changeYellow.gif";
    private String BLUE_CHANGE_IMG = "images/changeBlue.gif";
    private String RED_CHANGE_IMG = "images/changeRed.gif";
    private String GREEN_CHANGE_IMG = "images/changeGreen.gif";
    
    
    private int direction = 0;
    
    // default number of vertical/horizontal cells: height/width of grid
    private static final int DEFAULT_GRID_H = 5;
    private static final int DEFAULT_GRID_W = 10;
    
    private static final int DEFAULT_TIMER_DELAY = 100;
    
    // default location of user at start
    private static final int DEFAULT_PLAYER_ROW = 0;
    
    protected static final int STARTING_FACTOR = 3;      // you might change that this when working on timing
    
    protected int factor = STARTING_FACTOR;
    
    protected Location player;
    
    protected int screen = INTRO;
    
    protected GameGrid grid;
    
    protected int timer; //timer time is declared in initGame method
    
    private int timerHelper = 0; //this helps the timer be accurate
    
    protected int collide;
    
    protected boolean paused = false;
    
    protected int pausedCount = 0;
    
    protected boolean userYellow = false; //the 4 booleans that help the setColor method
    protected boolean userBlue = false;
    protected boolean userRed = false;
    protected boolean userGreen = false;
    
    public GeorgeChachkesGame() {
        this(DEFAULT_GRID_H, DEFAULT_GRID_W);
    }
    
    public GeorgeChachkesGame(int grid_h, int grid_w){
         this(grid_h, grid_w, DEFAULT_TIMER_DELAY);
    }

    
    
    public GeorgeChachkesGame(int hdim, int wdim, int init_delay_ms) {
        super(init_delay_ms);
        //set up our "board" (i.e., game grid) 
        grid = new GameGrid(hdim, wdim);   
        
    }
    
    /******************** Methods **********************/
    
    protected void initGame(){
         
         // store and initialize user position
         player = new Location(DEFAULT_PLAYER_ROW, 0);
         grid.setCellImage(player, PLAYER_IMG);
         this.timer = 45;
         this.collide = 0;
         updateTitle();                           
    }
    
    private String setColor(Location player){//updates the correct color of the player
    	if (userYellow==true){
    		return PLAYER_YELLOW_IMG;
    	}else if (userBlue==true){
    		return PLAYER_BLUE_IMG;
    	}else if (userRed==true){
    		return PLAYER_RED_IMG;
    	}else if (userGreen==true){
    		return PLAYER_GREEN_IMG;
    	}
    return PLAYER_IMG;
    }
        
    // Display the intro screen: not too interesting at the moment
    // Notice the similarity with the while structure in play()
    // sleep is required to not consume all the CPU; going too fast freezes app 
    protected void displayIntro(){
     
       grid.setSplash(SPLASH_IMG);
       while (screen == INTRO) {
          super.sleep(timerDelay);
          // Listen to keep press to break out of intro 
          // in particular here --> space bar necessary
          handleKeyPress();
       }
       grid.setSplash(null);
    }
  
    protected void updateGameLoop() {
        handleKeyPress();        // update state based on user key press
        handleMouseClick();      // when the game is running: 
        // click & read the console output 
        
        if (turnsElapsed % factor == 0) {  // if it's the FACTOR timer tick
        	// constant 3 initially
        	scrollLeft();
           	populateRightEdge();
        	
           	if (timer>15){//timer goes slow until 15 seconds left
           		if (timerHelper % 4 == 0){
        		this.timer--;
        		}
        	} else {//then goes faster in last 15 seconds
        		this.factor=2;
        		if (timerHelper % 6 == 0){
        		this.timer--;
        		}
        	}
        
        	timerHelper++;
        }     
        updateTitle();
    }
    private void move(int xChange, int yChange) {
    	if (paused == false){
    		this.grid.setCellImage(this.player, null);
    		int x = this.player.getRow() + xChange;
    		int y = this.player.getCol() + yChange;
    		this.player = new Location(x, y);
    		handleCollision();
    		
    	 	this.grid.setCellImage(this.player, setColor(this.player));
    	}
    }
    
    // update game state to reflect adding in new cells in the right-most column
    private void populateRightEdge() {//all the different walls and circles can be spawn in here
    	for (int i = 0; i<this.grid.getNumRows(); i++){
    		int pic = rand.nextInt(this.grid.getNumRows()*2+3);
    		if (pic == 0){
    			Location newLoc = new Location(i, this.grid.getNumCols() - 1);
    			int color = rand.nextInt(4);//equal chance that the circle will be each color
    			if (color ==0) {
    				this.grid.setCellImage(newLoc, this.YELLOW_CHANGE_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    			} else if (color ==1) {
    				this.grid.setCellImage(newLoc, this.BLUE_CHANGE_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    			} else if (color ==2) {
    				this.grid.setCellImage(newLoc, this.RED_CHANGE_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    			} else if (color ==3) {
    				this.grid.setCellImage(newLoc, this.GREEN_CHANGE_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    			}
    		}
    		else if (pic ==1){
    			Location newLoc = new Location(i, this.grid.getNumCols() - 1);
    			this.grid.setCellImage(newLoc, this.YELLOW_BLOCK_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    		}else if (pic ==2){
    			Location newLoc = new Location(i, this.grid.getNumCols() - 1);
    			this.grid.setCellImage(newLoc, this.GREEN_BLOCK_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    		}else if (pic ==3){
    			Location newLoc = new Location(i, this.grid.getNumCols() - 1);
    			this.grid.setCellImage(newLoc, this.BLUE_BLOCK_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    		}else if (pic ==4){
    			Location newLoc = new Location(i, this.grid.getNumCols() - 1);
    			this.grid.setCellImage(newLoc, this.RED_BLOCK_IMG);
    				if (newLoc.equals(this.player)){
    					handleCollision();
    					this.grid.setCellImage(this.player, setColor(this.player));
    				}
    		}
    		
    	}		
    }
    
    
    // updates the game state to reflect scrolling left by one column
    private void scrollLeft() {
        for (int i = 0; i < this.grid.getNumCols()-1; i++){
        	for (int j = 0; j < this.grid.getNumRows(); j++){
        		Location newLoc = new Location(j, i);
        		Location oldLoc = new Location(j, i+1);
        		
        		if (this.player.equals(oldLoc))
        			System.out.print("");
        		else{
        			this.grid.setCellImage(newLoc, this.grid.getCellImage(oldLoc));
        			this.grid.setCellImage(oldLoc, null);
        			if (this.player.equals(newLoc)){
        				handleCollision();
        				this.grid.setCellImage(newLoc, setColor(this.player));
        			}
        		}	 
        	} 
        } 
    }
    
    
    
    /* handleCollision()
     * handle a collision between the user and an object in the game
     */    
    private void handleCollision() {
        if (this.grid.getCellImage(this.player) == this.YELLOW_CHANGE_IMG){
        	userYellow = true;
        	userBlue = false;
        	userGreen = false;
        	userRed = false;
        } else if (this.grid.getCellImage(this.player) == this.BLUE_CHANGE_IMG){
        	userYellow = false;
        	userBlue = true;
        	userGreen = false;
        	userRed = false;
        } else if (this.grid.getCellImage(this.player) == this.GREEN_CHANGE_IMG){
        	userYellow = false;
        	userBlue = false;
        	userGreen = true;
        	userRed = false;
        } else if (this.grid.getCellImage(this.player) == this.RED_CHANGE_IMG){
        	userYellow = false;
        	userBlue = false;
        	userGreen = false;
        	userRed = true;
        } else if (this.grid.getCellImage(this.player) == this.YELLOW_BLOCK_IMG) {
        	if (userYellow == false){
        		collide++;
        	}
        }else if (this.grid.getCellImage(this.player) == this.BLUE_BLOCK_IMG) {
        	if (userBlue == false){
        		collide++;
        	}
        } else if (this.grid.getCellImage(this.player) == this.RED_BLOCK_IMG) {
        	if (userRed == false){
        		collide++;
        	}
        } else if (this.grid.getCellImage(this.player) == this.GREEN_BLOCK_IMG) {
        	if (userGreen == false){
        		collide++;
        	}
        }
        
    }
    
    //---------------------------------------------------//
    
    // handles actions upon mouse click in game
    private void handleMouseClick() {
        
        Location loc = grid.checkLastLocationClicked();
        
        if (loc != null) 
            System.out.println("You clicked on a square " + loc);
        
    }
    
    // handles actions upon key press in game
    protected void handleKeyPress() {
        
        int key = grid.checkLastKeyPressed();
        
        //use Java constant names for key presses
        //http://docs.oracle.com/javase/7/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_DOWN
        
        // Q for quit
        
        
        
        if (key == KeyEvent.VK_Q)
            System.exit(0);
        
        else if (key == KeyEvent.VK_P){
        	pausedCount+=1;
        	if (pausedCount%2==1){
        		paused = true;
        		this.factor = 10000000;
        	}
        	else if (pausedCount%2 == 0){
        		paused = false;
        		this.factor = 3;
        	}
        }
        //else if (key == 44)
        	//this.factor+=1;
        
        //else if (key == 46){
        	//if (this.factor>1)
        		//this.factor-=1;
        
        
        else if (key == KeyEvent.VK_S)
            System.out.println("could save the screen: add the call");
        
        else if (key == KeyEvent.VK_SPACE)
           screen += 1;
       
       	else if (key == KeyEvent.VK_UP){
       		if (0<this.player.getRow()){
       			move(-1, 0);
       		}
       	}
       	else if (key == KeyEvent.VK_DOWN){
       		if (this.grid.getNumRows()-1>this.player.getRow()){
       			move(1, 0);
       		}
       	}
       	else if (key == KeyEvent.VK_LEFT){
       		if (0<this.player.getCol()){
       			move(0, -1);
       		}
       	}
       	else if (key == KeyEvent.VK_RIGHT){
       		if (this.grid.getNumCols()-1>this.player.getCol()){
       			move(0, 1);
       		}
       	}
       	
       	   
        /* To help you with step 9: 
         use the 'T' key to help you with implementing speed up/slow down/pause
         this prints out a debugging message */
       
    }
    private int getTimer() {
    	return this.timer;
    }

    private int getCollide() {
        return this.collide;    //dummy for now
    }
    
    // update the title bar of the game window 
    private void updateTitle() {
        grid.setTitle("George's Color Game    Time Remaining: " + getTimer() + " seconds");
    }
    
    // return true if the game is finished, false otherwise
    //      used by play() to terminate the main game loop 
    protected boolean isGameOver() {
        return (this.collide == 1 || this.timer<=0);
    }

    
    // display the game over screen, blank for now
    protected void displayOutcome() {
    	if (this.timer == 0)
    		this.grid.setTitle("You Win!!!");
    	else
    		this.grid.setTitle("You Lose!!!");
    }
}
