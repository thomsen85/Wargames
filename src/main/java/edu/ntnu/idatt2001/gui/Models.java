package edu.ntnu.idatt2001.gui;

import edu.ntnu.idatt2001.models.Battle;

import java.util.Objects;

/**
 * Singleton class which holds the model data. To get the object use
 *
 * This class was reused form out IDATT1002 Project, it was originally written by me.
 */
public class Models {
    private static Models instance;

    private Battle currentBattle;

    /**
     * Private constructor for maintaining a singleton design pattern.
     */
    private Models() {
    }

    /**
     * Method for getting the instance of the Models class. Only one of the
     * 
     * @return the instance
     */
    public static synchronized Models getInstance() {
        if (instance == null) {
            instance = new Models();
        }
        return instance;
    }

    /**
     * Method for getting the current battle.
     * 
     * @return the battle
     */
    public Battle getCurrentBattle() {
        return currentBattle;
    }

    /**
     * Method for setting the current battle.
     * 
     * @throws NullPointerException
     *             if null is given.
     * @param currentBattle
     *            battle to be set. Can not be null.
     *
     */
    public void setCurrentBattle(Battle currentBattle) {
        Objects.requireNonNull(currentBattle);
        this.currentBattle = currentBattle;
    }
}
