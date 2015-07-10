#!/usr/bin/env python

import pygame
from pygame import locals
from puzzleDungeonsRenderer import *
from globalVariables import *
from orbs  import *
from board import *
from boardRenderer import *
from boardOrbSwapper import *
from monsterTeam import *
from monster import *
from dungeonMonsterRenderer import *
from combatMath import *

pygame.init()

class PuzzleDungeonsGame():
    def __init__(self):
        #pygame.cursors.tri_left
        #pygame.cursors.broken_x
        pygame.mouse.set_cursor(*pygame.cursors.broken_x)
        self._board    = Board(DEFAULT_BOARD_INITALIZER())
        self._swapper  = BoardOrbSwapper(self._board)
        self._monsterTeam = MAKE_RANDOM_FULL_TEAM()
        self._enemy       = GET_RANDOM_MONSTER()
        self._renderer = PuzzleDungeonsRenderer(self._board, self._swapper, self._monsterTeam, self._enemy)

    def _gameLoop(self):
        combos, pointMap = MAKE_COMBOS_AND_THEN_REFILL_BOARD(self._board, self._renderer)
        turn = 0
        #while True:
        while self._enemy.isAlive() and self._monsterTeam.isAlive():
            #renderer.updateCombo(0)
            if(self._swapper.isSwapping()):
                turn += 1
                print("STARTING TURN: " + str(turn))
                while (not self._swapper.isDoneSwapping()):
                    self._updater()
                self._swapper.unselect()
                self._combatStep()
                self._updater()
            else:
                self._updater()

        return self._monsterTeam.isAlive() # return winning data like monsters caught and stuff.        

    def _combatStep(self):
        combos, pointMap = MAKE_COMBOS_AND_THEN_REFILL_BOARD(self._board, self._renderer)
        ### USE COMBOS AND POINTMAP IN COMBAT ###
        healpoints = GET_HEAL_AMOUNT(pointMap, combos, self._monsterTeam.getTotalCombinedMaxHealth())
        #print("HEAL: " + str(healpoints))
        self._monsterTeam.heal(healpoints)
        
        damageMap = GET_MONSTER_TEAM_BATTLE_POWER(pointMap, combos, self._monsterTeam)
        #print(str(damageMap))
        ATTACK_MONSTER(self._monsterTeam, damageMap, self._enemy)

        if self._enemy.isAttackTurn() and self._enemy.isAlive():
            ENEMY_ATTACK_TEAM(self._enemy, self._monsterTeam)
        self._enemy.turnStep()
        
    def _updater(self):
        self._swapper.update()
        self._renderer.render()
        for event in pygame.event.get(QUIT):
            pygame.display.quit()
            pygame.quit()
            sys.exit()

    def run(self, orbType):
        self._renderer.setStyle(orbType)
        self._enemy       = GET_RANDOM_MONSTER_OF_TYPE(orbType)
        self._renderer.setEnemy(self._enemy)

        result = self._gameLoop()

        if result:
            self._renderer.fadeToWhite()
        else:
            self._renderer.fadeToBlack()
            
        while not self._renderer.isDoneFading():
            self._updater()
        return result

if __name__ == "__main__":
    game = PuzzleDungeonsGame()
    game.run(GENERATE_RANDOM_ORB_OF_MONSTER_TYPE())
   #game.run(PURPLE)

