from globalVariables import *
from board import *
from boardRenderer import *

class BoardOrbSwapper():
    def __init__(self, board):
        self.board          = board

        self._cursorCol     = 0
        self._cursorRow     = 0
        self._selected      = False

        self._didSwap       = False

        self._mouseX, self._mouseY = pygame.mouse.get_pos()

        self._ALLOTED_SWAPS = DEFAULT_SWAPS_ALLOTED
        self._swapCounter   = 0

##    def toggleSelected(self):
##        self._selected = not self._selected
    def select(self):
        self._selected = True
    def unselect(self):
        self._selected = False
    
    def moveDown(self):
        if self._cursorRow < ROWS-1:
            self._move(self._cursorRow + 1, self._cursorCol)

    def moveUp(self):
        if self._cursorRow > 0:
            self._move(self._cursorRow - 1, self._cursorCol)

    def moveLeft(self):
        if self._cursorCol > 0:
            self._move(self._cursorRow, self._cursorCol - 1)

    def moveRight(self):
        if self._cursorCol < COLS-1:
            self._move(self._cursorRow, self._cursorCol + 1)

    def moveUpRight(self):
        if self._cursorCol < COLS-1 and self._cursorRow > 0:
            self._move(self._cursorRow - 1, self._cursorCol + 1)

    def moveDownRight(self):
        if self._cursorCol < COLS-1 and self._cursorRow < ROWS-1:
            self._move(self._cursorRow + 1, self._cursorCol + 1)

    def moveUpLeft(self):
        if self._cursorCol > 0 and self._cursorRow > 0:
            self._move(self._cursorRow - 1, self._cursorCol - 1)

    def moveDownLeft(self):
        if self._cursorCol > 0 and self._cursorRow < ROWS-1:
            self._move(self._cursorRow + 1, self._cursorCol - 1)

    def _move(self, newRow, newCol):
        if self._selected:
            self._swap(self._cursorRow, self._cursorCol, newRow, newCol)
        self._cursorRow = newRow
        self._cursorCol = newCol

    def _swap(self, priorRow, priorCol, newRow, newCol):
        self.board.swap(priorRow, priorCol, newRow, newCol)
        self._didSwap = True
        self._incrementSwapCounter()

    def _setPreviousRowCol(self):
        self._previousRow = self._cursorRow
        self._previousCol = self._cursorCol

    def _incrementSwapCounter(self):
        self._swapCounter += 1
        if(self._swapCounter >= self._ALLOTED_SWAPS):
            self.unselect()
            self._swapCounter = 0

    def swapsRemaining(self):
        return self._ALLOTED_SWAPS - self._swapCounter

    def setAllotedSwaps(self, amount):
        self._ALLOTED_SWAPS = amount

    def getRow(self):
        return self._cursorRow
    def getCol(self):
        return self._cursorCol

    def isSwapping(self):
        return self._selected

    def isDoneSwapping(self):
        result = self._didSwap and not self.isSwapping()
        if result:
            self._didSwap     = False #reset for next time.
            self._swapCounter = 0
        return result
        
    def update(self):

        self._setCursorPositionBasedOnMouseRelativeToBoardRendererOrbPlacements()
        
        for event in pygame.event.get():

            if event.type == KEYUP:          
                if event.key == K_SPACE:
                    self.unselect()
            if event.type == pygame.MOUSEBUTTONUP:
                self.unselect()
                    
            if event.type == KEYDOWN:
                if event.key == K_UP:
                    self.moveUp()
                elif event.key == K_DOWN:
                    self.moveDown()
                if event.key == K_RIGHT:
                    self.moveRight()
                elif event.key == K_LEFT:
                    self.moveLeft()

                if event.key == K_SPACE:
                    self.select()
            if event.type == pygame.MOUSEBUTTONDOWN:
                if self._mouseX > ORBS_START_X_OFFSET and self._mouseX < ORBS_END_X_OFFSET and self._mouseY > ORBS_START_Y_OFFSET and self._mouseY < ORBS_END_Y_OFFSET:
                    self.select()

    def _setCursorPositionBasedOnMouseRelativeToBoardRendererOrbPlacements(self):
        self._mouseX,self._mouseY = pygame.mouse.get_pos()
        x = self._mouseX
        y = self._mouseY

        previousCol = self._cursorCol
        previousRow = self._cursorRow

        if x > ORBS_START_X_OFFSET and x < ORBS_END_X_OFFSET:
            x -= ORBS_START_X_OFFSET
            if x != 0:
                self._cursorCol =  int(COLS * (x / ORB_MOUSE_AREA_WIDTH))
            if self._cursorCol >= COLS:
                self._cursorCol = COLS - 1
            elif self._cursorCol < 0:
                self._cursorCol = 0
        if y > ORBS_START_Y_OFFSET and y < ORBS_END_Y_OFFSET:
            y -= ORBS_START_Y_OFFSET
            if y != 0:
                self._cursorRow =  int(ROWS * (y / ORB_MOUSE_AREA_HEIGHT))
            if self._cursorRow >= ROWS:
                self._cursorRow = ROWS - 1
            elif self._cursorRow < 0:
                self._cursorRow = 0

        if self.isSwapping() and (previousCol != self._cursorCol or previousRow != self._cursorRow):
            self._swap(previousRow, previousCol, self._cursorRow, self._cursorCol)

if __name__ == "__main__":
    pygame.mouse.set_cursor(*pygame.cursors.broken_x)
    
    board = Board(DEFAULT_BOARD_INITALIZER())
    swapper = BoardOrbSwapper(board)
    screen = pygame.display.set_mode((SCREEN_WIDTH,SCREEN_HEIGHT),0,32)
    font = pygame.font.SysFont("monospace", FONT_SIZE)
    renderer = BoardRenderer(screen, font, board, swapper)
    pygame.display.set_caption("OrbSwapper Active Test")
     
    while not swapper.isDoneSwapping():
        pygame.display.update()
        swapper.update()
        renderer.render()
        

    print("Done!")
    
