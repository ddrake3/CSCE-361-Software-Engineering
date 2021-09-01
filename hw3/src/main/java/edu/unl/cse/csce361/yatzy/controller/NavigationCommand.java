package edu.unl.cse.csce361.yatzy.controller;

/**
 * Marker interface for navigation commands. While this interface makes navigation commands sortable, its principal
 * purpose is to provide type safety.
 */
public interface NavigationCommand extends Command, Comparable<NavigationCommand> {
}
