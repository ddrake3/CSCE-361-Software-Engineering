package edu.unl.cse.csce361.yatzy.controller;

/**
 * Marker interface for scoring commands. While this interface extends the base interface to be able to obtain the
 * category's score, its principal purpose is to provide type safety.
 */
public interface ScoringCommand extends Command {
    int getCategoryScore();
}
