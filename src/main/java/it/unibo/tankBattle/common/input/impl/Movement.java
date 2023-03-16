package it.unibo.tankBattle.common.input.impl;


import it.unibo.tankBattle.common.input.api.Command;
import it.unibo.tankBattle.common.input.api.Directions;
import it.unibo.tankBattle.controller.api.GameEngine;
import it.unibo.tankBattle.controller.api.Player;

/**
 * That class manage the movement of the players
 */
public class Movement implements Command{

    private Directions dir;
    private Player player;
    private GameEngine controller;

    public Movement(final Directions dir, final Player player, final GameEngine controller){
        this.dir = dir;
        this.player = player;
        this.controller = controller;
    }

    @Override
    public void execute() {
        //player.getTrasform.SetDirection(dir);
        controller.getWorld().setDirection(dir, player);
        System.out.println(player.getCode() + " " + dir);
    }
    
}
