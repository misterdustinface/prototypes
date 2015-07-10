from globalVariables import *
from orbs import *
from monster import *
from imgLoader import *

import random
import pygame
from pygame.locals import *
from sys import exit 

class DungeonMonsterRenderer():
    def __init__(self, screen, font, enemy):
        self.screen = screen
        self.font   = font
        self.imageLoader = imgLoader()
        ADD_MONSTER_IMAGES_TO_IMAGE_LOADER(self.imageLoader)

        self.enemy  = enemy
        self.currentImage = self.imageLoader.get(enemy.getName())

        self.healthBarBox = pygame.Surface((HEALTH_BAR_WIDTH,HEALTH_BAR_HEIGHT))
        self.healthBarBox.convert()
        self.healthBarBox.fill(HEALTH_BAR_BACKGROUND_COLOR)

        self.healthBarFace = pygame.Surface((HEALTH_BAR_WIDTH,HEALTH_BAR_HEIGHT))
        self.healthBarFace.convert()
        self.healthBarFace.fill(ORB_TO_COLOR_MAP[enemy.typeA()])

        self.healthBarFont = pygame.font.SysFont(FONT_TYPE,HEALTH_BAR_FONT_SIZE)
        self.healthBarFont.set_bold(True)

        self.countdownBox = pygame.Surface((COUNTDOWN_BOX_WIDTH,COUNTDOWN_BOX_HEIGHT))
        self.countdownBox.convert()
        self.countdownBox.fill(ORB_TO_COLOR_MAP[enemy.typeA()])

        self.currentReadHealth = int(self.enemy.getCurrentHealth())
        self.healthModifier = 0

    def setEnemy(self, enemy):
        self.enemy  = enemy
        self.currentImage = self.imageLoader.get(enemy.getName())
        self.healthBarFace.fill(ORB_TO_COLOR_MAP[enemy.typeA()])
        self.countdownBox.fill(ORB_TO_COLOR_MAP[enemy.typeA()])
        self.currentReadHealth = int(self.enemy.getCurrentHealth())

    def render(self):
        self.screen.blit(self.currentImage,(ENEMY_X_POS,ENEMY_Y_POS))

        ############### HEALTH BAR ANIMATION ############################
        if(int(self.enemy.getCurrentHealth()) < self.currentReadHealth):
            if self.healthModifier == 0:
                self.healthModifier = int((self.currentReadHealth - int(self.enemy.getCurrentHealth())) / FPS * SECONDS_TO_DISPLAY_HEALTH_CHANGE)
            self.currentReadHealth -= self.healthModifier
            if int(self.enemy.getCurrentHealth()) > self.currentReadHealth:
                self.currentReadHealth = int(self.enemy.getCurrentHealth())
        elif(int(self.enemy.getCurrentHealth()) > self.currentReadHealth):
            if self.healthModifier == 0:
                self.healthModifier = int((int(self.enemy.getCurrentHealth()) - self.currentReadHealth) / FPS * SECONDS_TO_DISPLAY_HEALTH_CHANGE)
            self.currentReadHealth += self.healthModifier
            if int(self.enemy.getCurrentHealth()) < self.currentReadHealth:
                self.currentReadHealth = int(self.enemy.getCurrentHealth())
        else:
            self.healthModifier = 0
        #################################################################

        #render enemy health with color based on type.        
        self.screen.blit(self.healthBarBox, (ENEMY_X_POS, ENEMY_Y_POS - FONT_SIZE))
        healthLeft = int((self.currentReadHealth / self.enemy.getMaxHealth()) * HEALTH_BAR_WIDTH)
        self.screen.blit(pygame.transform.scale(self.healthBarFace, (healthLeft, HEALTH_BAR_HEIGHT)), (ENEMY_X_POS, ENEMY_Y_POS - FONT_SIZE))

        #render enemy attack turn countdown
        self.screen.blit(self.countdownBox,(ENEMY_ATTACK_COUNTDOWN_X_POS - COUNTDOWN_BOX_WIDTH/4,ENEMY_INFO_Y_POS))
        countdown = self.font.render(str(self.enemy.getTurnStep()), True, INFO_TEXT_COLOR)
        self.screen.blit(countdown, (ENEMY_ATTACK_COUNTDOWN_X_POS,ENEMY_INFO_Y_POS))
        
        #render enemy name
        monsterName = self.healthBarFont.render(str(self.enemy.getName() + " - [LV: " + str(self.enemy.getCurrentLevel())+"]"), True, HEALTH_BAR_TEXT_COLOR)
        self.screen.blit(monsterName, (ENEMY_X_POS + 1, ENEMY_INFO_Y_POS + 1))
