package edu.unl.cse.csce361.yatzy.controller.navigation;

import edu.unl.cse.csce361.yatzy.controller.NavigationCommand;

/**
 * Base class for navigation commands.
 */
public abstract class AbstractNavigationCommand implements NavigationCommand {
    @Override
    public int compareTo(NavigationCommand other) {
        return this.getClass().getSimpleName().compareTo(other.getClass().getSimpleName());
    }
}
