from globalVariables import *
from orbs import *
from monster import *
from monsterTeam import *
from imgLoader import *

import random
import pygame
from pygame.locals import *

pygame.init()

class MonsterTeamRenderer():
    def __init__(self, screen, font, monsterTeam):
        self.screen = screen
        self.font   = font

        self.healthBarBox = pygame.Surface((HEALTH_BAR_WIDTH,HEALTH_BAR_HEIGHT))
        self.healthBarBox.convert()
        self.healthBarBox.fill(HEALTH_BAR_BACKGROUND_COLOR)

        self.healthBarPosition = (HEALTH_BAR_START_X_OFFSET,HEALTH_BAR_START_Y_OFFSET)

        self.healthBarFace = pygame.Surface((HEALTH_BAR_WIDTH,HEALTH_BAR_HEIGHT))
        self.healthBarFace.convert()
        self.healthBarFace.fill(HEALTH_BAR_COLOR)

        self.healthBarFont = pygame.font.SysFont(FONT_TYPE,HEALTH_BAR_FONT_SIZE)
        self.healthBarFont.set_bold(True)
	
        self.monsterTypeABackground = pygame.Surface((MONSTER_TEAM_SINGLE_MONSTER_WIDTH,MONSTER_TEAM_SINGLE_MONSTER_HEIGHT/2))
        self.monsterTypeABackground.convert()
        self.monsterTypeBBackground = pygame.Surface((MONSTER_TEAM_SINGLE_MONSTER_WIDTH,MONSTER_TEAM_SINGLE_MONSTER_HEIGHT/2))
        self.monsterTypeBBackground.convert()

        self.TEAM = monsterTeam
        self.MAX_HEALTH = self.TEAM.getTotalCombinedMaxHealth()
        self.currentReadHealth = int(self.TEAM.getTotalCombinedCurrentHealth())
        self.healthModifier = 0
    def render(self):

        self.screen.blit(self.healthBarBox, self.healthBarPosition)

        ############### HEALTH BAR ANIMATION ############################
        if self.currentReadHealth > int(self.TEAM.getTotalCombinedCurrentHealth()):
            if self.healthModifier == 0:
                self.healthModifier = int((self.currentReadHealth - int(self.TEAM.getTotalCombinedCurrentHealth())) / FPS * SECONDS_TO_DISPLAY_HEALTH_CHANGE)
            self.currentReadHealth -= self.healthModifier
            if self.currentReadHealth < int(self.TEAM.getTotalCombinedCurrentHealth()):
                self.currentReadHealth = int(self.TEAM.getTotalCombinedCurrentHealth())
        elif self.currentReadHealth < int(self.TEAM.getTotalCombinedCurrentHealth()):
            if self.healthModifier == 0:
                self.healthModifier = int((int(self.TEAM.getTotalCombinedCurrentHealth()) - self.currentReadHealth) / FPS * SECONDS_TO_DISPLAY_HEALTH_CHANGE)
            self.currentReadHealth += self.healthModifier
            if self.currentReadHealth > int(self.TEAM.getTotalCombinedCurrentHealth()):
                self.currentReadHealth = int(self.TEAM.getTotalCombinedCurrentHealth())
        else:
            self.healthModifier = 0
        #################################################################
            
        healthLeft = int(self.currentReadHealth/self.MAX_HEALTH * HEALTH_BAR_WIDTH)
        self.screen.blit(pygame.transform.scale(self.healthBarFace, (healthLeft, HEALTH_BAR_HEIGHT)), self.healthBarPosition)

        hitpoints = self.healthBarFont.render("["+ str(int(self.currentReadHealth)) + "/" + str(int(self.MAX_HEALTH)) +"]", True, (255,255,255))
        #self.screen.blit(hitpoints,(HEALTH_BAR_START_X_OFFSET + 2, HEALTH_BAR_START_Y_OFFSET - FONT_SIZE - 2))
        self.screen.blit(hitpoints,(HEALTH_BAR_START_X_OFFSET + 2, HEALTH_BAR_START_Y_OFFSET - 1))

        #TODO add team member images and whatnot
        numberOfMonsters = self.TEAM.getTeamSize()
        xPos = MONSTER_TEAM_START_X_OFFSET
        for monster in self.TEAM.getMonsters():
            self.monsterTypeABackground.fill(ORB_TO_COLOR_MAP[monster.typeA()])
            self.screen.blit(self.monsterTypeABackground, (xPos, MONSTER_TEAM_START_Y_OFFSET))
            if monster.typeB() != NONE:
                self.monsterTypeBBackground.fill(ORB_TO_COLOR_MAP[monster.typeB()])
                self.screen.blit(self.monsterTypeBBackground, (xPos, MONSTER_TEAM_START_Y_OFFSET + MONSTER_TEAM_SINGLE_MONSTER_HEIGHT/2))
            xPos += MONSTER_TEAM_SINGLE_MONSTER_WIDTH + MONSTER_TEAM_SPACER

if __name__ == "__main__":
    pygame.display.set_caption("Monster Team Renderer Active Test")
    screen = pygame.display.set_mode((SCREEN_WIDTH,SCREEN_HEIGHT),0,32)
    font = pygame.font.SysFont(FONT_TYPE, FONT_SIZE)
    renderer = MonsterTeamRenderer(screen, font, MAKE_RANDOM_FULL_TEAM())
    renderer.render()
    pygame.display.update()
