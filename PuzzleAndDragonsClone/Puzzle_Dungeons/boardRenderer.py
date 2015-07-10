#http://www.pygame.org/docs/tut/newbieguide.html
#
#http://www.pygame.org/docs/ref/cursors.html
#
#http://www.pygame.org/docs/ref/mouse.html
#
from globalVariables import *
from orbs import *
from board import *
from imgLoader import *
from boardOrbSwapper import *

import pygame
from pygame.locals import *
from sys import exit

pygame.init()

BOARD_BACKGROUND_COLOR = (25,25,25)
SWAPPER_CURSOR__HIGHLIGHT_COLOR = (80,80,80, 128)

BORDER_IMAGES_FOLDER = "boardBorderImages"
BORDER_IMAGE_FILE_MAP  = {'R':"red.png",      'B':"polaroid.png", 'G':"green.png",
                          'Y':"simple.png",   'P':"dark.png",     'H':"polaroid.png",
                          'X':"polaroid.png", 'W':"polaroid.png"}
def ADD_BORDER_IMAGES_TO_IMAGE_LOADER(loader):
    for orb in ORBS:
        loader.load(BORDER_IMAGES_FOLDER, BORDER_IMAGE_FILE_MAP[orb], orb)

class BoardRenderer():
    def __init__(self, screen, font, board, swapper):
        self.screen = screen
        self.font   = font

        self.borderImages = imgLoader()
        ADD_BORDER_IMAGES_TO_IMAGE_LOADER(self.borderImages)
        self.currentBorderImage = pygame.transform.scale(self.borderImages.getRandom(), (BOARD_ORB_FRAME_IMAGE_WIDTH,BOARD_ORB_FRAME_IMAGE_HEIGHT))
        
        #self.boardBackground = pygame.Surface((ORB_MOUSE_AREA_WIDTH,ORB_MOUSE_AREA_HEIGHT))
        #self.boardBackground.convert()
        #self.boardBackground.fill(BOARD_BACKGROUND_COLOR)

        self.swapperCursor = pygame.Surface((ORB_WIDTH,ORB_HEIGHT))
        #self.swapperCursor.convert()
        self.swapperCursor.fill(SWAPPER_CURSOR__HIGHLIGHT_COLOR)

        self.orbImages = imgLoader()
        ADD_ORB_IMAGES_TO_IMAGE_LOADER(self.orbImages)

        self.board   = board
        self.swapper = swapper

        #self.combo      = 0
        #self.pointMap   = MAKE_NEW_POINTMAP()

    def setStyle(self, orbType):
        self.currentBorderImage = pygame.transform.scale(self.borderImages.get(orbType), (BOARD_ORB_FRAME_IMAGE_WIDTH,BOARD_ORB_FRAME_IMAGE_HEIGHT))

    def render(self):

        self.screen.blit(self.currentBorderImage,(BOARD_ORB_FRAME_START_X_OFFSET,BOARD_ORB_FRAME_START_Y_OFFSET))

        self.screen.blit(self.swapperCursor, (self.swapper.getCol() * ORB_WIDTH
                                            + self.swapper.getCol() * ORB_HORIZONTAL_SPACING
                                            + ORBS_START_X_OFFSET,
                                              self.swapper.getRow() * ORB_HEIGHT
                                            + self.swapper.getRow() * ORB_VERTICAL_SPACING
                                            + ORBS_START_Y_OFFSET), None, BLEND_RGBA_ADD)
        
        for r in range(ROWS):
            for c in range(COLS):
                #.get(self.board.get(r,c))
                if self.board.get(r,c) != EMPTY:
                    self.screen.blit(self.orbImages.get(self.board.get(r,c)),
                                     (c*ORB_WIDTH  + c*ORB_HORIZONTAL_SPACING + ORBS_START_X_OFFSET,
                                      r*ORB_HEIGHT + r*ORB_VERTICAL_SPACING   + ORBS_START_Y_OFFSET))

        # displays cursor position #
        #cursorPos = self.font.render(str(pygame.mouse.get_pos()), True, (255,255,255))
        #self.screen.blit(cursorPos,(FRAME_OFFSET/2,FRAME_OFFSET/2))

##    def updateCombo(self, combo):
##        self.combo = combo
##    def updatePointMap(self, points):
##        self.pointMap = points

if __name__ == "__main__":
    pygame.display.set_caption("Board Renderer Active Test")
    board = Board(DEFAULT_BOARD_INITALIZER())
    print(board.toString())
    swapper = BoardOrbSwapper(board)
    screen = pygame.display.set_mode((SCREEN_WIDTH,SCREEN_HEIGHT),0,32)
    font = pygame.font.SysFont("monospace", FONT_SIZE)
    renderer = BoardRenderer(screen, font, board, swapper)
    renderer.render()
    pygame.display.update()
