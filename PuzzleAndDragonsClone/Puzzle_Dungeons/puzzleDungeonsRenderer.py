from globalVariables import *
from orbs import *
from board import *
from imgLoader import *
from boardOrbSwapper import *
from boardRenderer import *
from monsterTeam import *
from monsterTeamRenderer import *
from dungeonMonsterRenderer import *

import pygame
from pygame.locals import *
from sys import exit

pygame.init()

BACKGROUND_COLOR = (0,0,0)


BACKGROUND_IMAGES_FOLDER = "environmentImages"
BACKGROUND_IMAGE_FILE_MAP = {'R':"red.jpg",      'B':"blue.jpg", 'G':"green.jpg",
                             'Y':"yellow.png",   'P':"purple.png",'H':"yellow.png",
                             'X':"purple.png",   'W':"yellow.png"}

def ADD_BACKGROUND_IMAGES_TO_IMAGE_LOADER(loader):
    for orb in ORBS:
        loader.load(BACKGROUND_IMAGES_FOLDER, BACKGROUND_IMAGE_FILE_MAP[orb], orb)

class PuzzleDungeonsRenderer():
    def __init__(self, board, swapper, monsterTeam, enemy):
        self.board   = board
        self.swapper = swapper
        self.monsterTeam = monsterTeam
        
        self.screen = pygame.display.set_mode((800,600),0,32)
        #self.screen=pygame.display.set_mode((SCREEN_WIDTH,SCREEN_HEIGHT),HWSURFACE|DOUBLEBUF|RESIZABLE)

        pygame.display.set_caption("Puzzle Dungeons")
        self.font = pygame.font.SysFont(FONT_TYPE, FONT_SIZE)

        self.images = imgLoader()
        ADD_BACKGROUND_IMAGES_TO_IMAGE_LOADER(self.images)
        self.currentBackgroundImage = pygame.transform.scale(self.images.getRandom(), (SCREEN_WIDTH,SCREEN_HEIGHT))
        
        #self.background = pygame.Surface((SCREEN_WIDTH,SCREEN_HEIGHT))
        #self.background.convert()
        #self.background.fill(BACKGROUND_COLOR)

        self.boardRenderer = BoardRenderer(self.screen, self.font, self.board, self.swapper)
        self.monsterTeamRenderer = MonsterTeamRenderer(self.screen, self.font, self.monsterTeam)
        self.enemyRenderer = DungeonMonsterRenderer(self.screen, self.font, enemy)

        self.combo      = 0
        self.pointMap   = MAKE_NEW_POINTMAP()

        self.isFading   = False
        self.fadeStep   = 0
        self.fadeSurface = pygame.Surface((SCREEN_WIDTH,SCREEN_HEIGHT))
        self.fadeSurface.fill((0,0,0,0))

    def setStyle(self, orbType):
        self.boardRenderer.setStyle(orbType)
        self.currentBackgroundImage = pygame.transform.scale(self.images.get(orbType), (SCREEN_WIDTH,SCREEN_HEIGHT))

    def setEnemy(self, enemy):
        self.enemyRenderer.setEnemy(enemy)

    def fadeToBlack(self):
        self.isFading = True
        self.fadeSurface.fill((0,0,0,0))

    def fadeToWhite(self):
        self.isFading = True
        self.fadeSurface.fill((255,255,255,0))

    def isDoneFading(self):
        return self.fadeStep >= FPS * SECONDS_TO_FADE

    def render(self):

        #for event in pygame.event.get(VIDEORESIZE):
        #    self.screen=pygame.display.set_mode(event.dict['size'],HWSURFACE|DOUBLEBUF|RESIZABLE)
        #    self.screen.blit(pygame.transform.scale(self.background,event.dict['size']),(0,0))
        
        self.screen.blit(self.currentBackgroundImage,(0,0))
        
        self.boardRenderer.render()
        self.monsterTeamRenderer.render()
        self.enemyRenderer.render()

        swapsLeft = self.font.render("Swaps Remaining: " + str(self.swapper.swapsRemaining()), True, INFO_TEXT_COLOR)
        self.screen.blit(swapsLeft, (FRAME_OFFSET, SCREEN_HEIGHT - FRAME_OFFSET/2))

        if self.isFading:
            self.fadeSurface.set_alpha(255 * (self.fadeStep/ (FPS * SECONDS_TO_FADE)))
            self.screen.blit(self.fadeSurface, (0,0))
            self.fadeStep += 1

        #pointString = ""
        #for key in self.pointMap.keys():
        #    if self.pointMap[key] >= 3:
        #        pointString += " [" + ORB_NAMES[key] + ": " + str(self.pointMap[key]) + "]"

        #points = self.font.render(pointString, True, (255,255,255))
        ###points = self.font.render(str(self.pointMap), True, (255,255,255))
        #self.screen.blit(points,(4, SCREEN_HEIGHT - FRAME_OFFSET/2))

        #if self.combo > 0:
        #    comboText = self.font.render("Combo: " + str(self.combo), True, (255,255,255))
        #    self.screen.blit(comboText, (ORBS_START_X_OFFSET,
        #                                 FRAME_OFFSET/2 + DUNGEON_AREA_SCREEN_HEIGHT + MONSTER_TEAM_AREA_SCREEN_HEIGHT))

        pygame.display.update()

    def updateCombo(self, combo):
        self.combo = combo

    def updatePointMap(self, pointMap):
        self.pointMap = pointMap

if __name__ == "__main__":
    board = Board(DEFAULT_BOARD_INITALIZER())
    print(board.toString())
    swapper = BoardOrbSwapper(board)
    renderer = PuzzleDungeonsRenderer(board, swapper, MAKE_RANDOM_FULL_TEAM())
    pygame.display.set_caption("Renderer Active Test")
    renderer.render() 
