package edu.ntnu.thosve.gui;


import edu.ntnu.thosve.Battle;

/**
 * Singleton class which holds the model data.
 * To get the object use
 */
public class Models {
    private static Models instance;

    private Battle currentBattle;

    private Models() {}

    public static synchronized Models getInstance() {
        if(instance == null){
            instance = new Models();
        }
        return instance;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(Battle currentBattle) {
        this.currentBattle = currentBattle;
    }
}
