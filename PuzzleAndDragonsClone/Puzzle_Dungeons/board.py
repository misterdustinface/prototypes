import random
from globalVariables import *
from orbs import *

# REALLY IMPORTANT GAME STUFF (THAT SHOULD PROBABLY NOT BE IN HERE)#
def MAKE_COMBOS_AND_THEN_REFILL_BOARD(board, renderer):
    matches = _CHECK_MATCHES(board)
    combos = len(matches)
    pointMap = MAKE_NEW_POINTMAP()
    renderer.updatePointMap(pointMap)
    while len(matches) > 0: # combos increases always, len(matches) varies
    ##    print(board.toString())
        pointMap = _CLEAR_MATCHES(matches, board, pointMap)
        renderer.updatePointMap(pointMap)
        renderer.updateCombo(combos)
        renderer.render()
        pygame.time.wait(ORB_DROP_DELAY)
        
    ##    print("------------------------")
    ##    print(board.toString())
    ##    print("------------------------")
    ##    print("Combos: " + str(combos))
    ##    print(pointMap)
        
        didGravityWork = _GRAVITY(board)
        renderer.render()
        while didGravityWork:
    ##        print("------------------------")
    ##        print(board.toString())
            pygame.time.wait(ORB_DROP_DELAY)
            didGravityWork = _GRAVITY(board)
            renderer.render()
            
        while _ADD_RANDOM_ORBS_TO_EMPTY_SPACES_AFTER_GRAVITY(board):
    ##        print("------------------------")
    ##        print(board.toString())
            pygame.time.wait(ORB_DROP_DELAY)
            _GRAVITY(board)
            renderer.render()
            
        matches = _CHECK_MATCHES(board)
        combos += len(matches)
    return combos, pointMap

####################################################
#-----------------------------#
#     AWKWARD TEMP THINGS     #
#-----------------------------#

def _GRAVITY(board):
    # 0 0 at top left.
    didWork = False
    for r in range(ROWS-1):
        for c in range(COLS):
            if board.get(r,c) != NONE and board.get(r+1,c) == NONE:
                board.swap(r,c, r+1,c)
                didWork = True
    return didWork
def _ADD_RANDOM_ORBS_TO_EMPTY_SPACES_AFTER_GRAVITY(board):
    didWork = False
    for c in range(COLS):
        if board.get(0,c) == NONE:
            board.setOrb(GENERATE_RANDOM_ORB(),0,c)
            didWork = True
    return didWork

####################################################
#----------------------#
#     INITIALIZERS     #
#----------------------#

def DEFAULT_BOARD_INITALIZER():
    return [[GENERATE_RANDOM_ORB() for c in range(COLS)] for r in range(ROWS)]

def _TEST_BOARD_INIT_A():
    return [['H','G','R','G','H','B'],
            ['Y','B','G','R','R','P'],
            ['P','B','G','P','G','Y'],
            ['B','G','G','G','G','R'],
            ['Y','G','Y','Y','G','Y']]

def _TEST_BOARD_INIT_B():
    return [['H','H','H','H','H','H'],
            ['H','H','H','H','H','H'],
            ['H','H','H','H','H','H'],
            ['H','H','H','H','H','H'],
            ['H','H','H','H','H','H']]

def _TEST_BOARD_INIT_C():
    return [['B','B','B','G','G','G'],
            ['G','G','G','B','B','B'],
            ['B','B','B','G','G','G'],
            ['G','G','G','B','B','B'],
            ['B','B','B','G','G','G']]

def MAKE_NEW_POINTMAP():
    return {RED:0, BLUE:0, GREEN:0, YELLOW:0, PURPLE:0, HEAL:0, BLOCK:0, WHITE:0}

#######################################################################   
#--------------------------#
#     HELPER FUNCTIONS     #
#--------------------------#
    
def _CHECK_MATCHES(board):
    matchList = set()
    checked = [[False for c in range(COLS)] for r in range(ROWS)]
    for r in range(ROWS):
        for c in range(COLS):
            if not checked[r][c]:
                orbMatcher = OrbMatch(board.get(r,c),checked)
                orbMatcher.add(r, c)
                _RECURSIVE_ORB_MATCHER(orbMatcher,board,r,c)
                checked = orbMatcher.getCheckedGrid()
                
                if orbMatcher.getOrbCount() >= 3:
                    matchList.add(orbMatcher)
    return matchList

# orbMatcher is passed by reference.
# must be inclusive to gather 3-in-a-row combinations.
def _RECURSIVE_ORB_MATCHER(orbMatcher, board, r, c):

    if 0 < r < (ROWS - 1):
        if board.get(r-1,c) == board.get(r,c) == board.get(r+1,c):
            if not orbMatcher.wasChecked(r-1,c):
                orbMatcher.add(r-1,c)
                _RECURSIVE_ORB_MATCHER(orbMatcher, board, r-1, c)
            else:
                orbMatcher.add(r-1,c)
            if not orbMatcher.wasChecked(r+1,c):
                orbMatcher.add(r+1,c)
                _RECURSIVE_ORB_MATCHER(orbMatcher, board, r+1, c)
            else:
                orbMatcher.add(r+1,c)
    elif r == 0:
        if board.get(r,c) == board.get(r+1,c) == board.get(r+2,c):
            orbMatcher.add(r+1,c)
            _RECURSIVE_ORB_MATCHER(orbMatcher,board,r+1,c)
    else: #r == (ROWS-1)
        if board.get((ROWS-1),c) == board.get((ROWS-2),c) == board.get((ROWS-3),c):
            orbMatcher.add((ROWS-2),c)
            _RECURSIVE_ORB_MATCHER(orbMatcher,board,(ROWS-2),c)

    if 0 < c < (COLS - 1):
        if board.get(r,c-1) == board.get(r,c) == board.get(r,c+1):
            if not orbMatcher.wasChecked(r,c-1):
                orbMatcher.add(r,c-1)
                _RECURSIVE_ORB_MATCHER(orbMatcher, board, r, c-1)
            else:
                orbMatcher.add(r,c-1)
            if not orbMatcher.wasChecked(r,c+1):
                orbMatcher.add(r,c+1)
                _RECURSIVE_ORB_MATCHER(orbMatcher, board, r, c+1)
            else:
                orbMatcher.add(r,c+1)
    elif c == 0:
        if board.get(r,c) == board.get(r,c+1) == board.get(r,c+2):
            orbMatcher.add(r,c+1)
            _RECURSIVE_ORB_MATCHER(orbMatcher,board,r,c+1)
    else: #c == (COLS-1)
        if board.get(r,(COLS-1)) == board.get(r,(COLS-2)) == board.get(r,(COLS-3)):
            orbMatcher.add(r,(COLS-2))
            _RECURSIVE_ORB_MATCHER(orbMatcher,board,r,(COLS-2))
            
def _CLEAR_MATCHES(matchList, board, pointMap):
    while len(matchList) > 0:
        match = matchList.pop()
        pointMap[match.getOrbType()] += match.getOrbCount()

        #print(str(match.getOrbCount()))# For Testing
        
        while match.getOrbCount() > 0:
            coord = match.pop()
            board.remove(coord.row, coord.col)
    return pointMap

##def ADD_NEW_ORBS_TO_REPLACE_EMPTY_SPACES(board):
##    queuesToAdd = [[] for c in range(COLS)]
##    for c in range(COLS):
##        amountToEnqueue = 0
##        for r in range(ROWS):
##            if board.get(r,c) == EMPTY:
##                amountToEnqueue += 1
##        queue = [GENERATE_RANDOM_ORB() for i in range(amountToEnqueue)]
##        queuesToAdd[c] = queue
##    return queuesToAdd

##def GENERATE_BOTTOM_MOST_EMPTY_SPACE_LIST(board):
##    boardCoordinates = set()
##    for c in range(COLS):
##        for r in range(ROWS):
##            if board.get(r,c) == EMPTY:
##                boardCoordinates.add(BoardCoordinate(r,c))
##                break
##    return boardCoordinates

##def GENERATE_FIRST_OF_FALLING_ORBS_LIST(board):
##    boardCoordinates = set()
##    for c in range(COLS):
##        for r in range(1,ROWS):
##            if board.get(r-1,c) == EMPTY and board.get(r,c) != EMPTY:
##                boardCoordinates.add(BoardCoordinate(r,c))
##                break
##    return boardCoordinates
            
####################################################
#-------------------#
#   INNER CLASSES   #
#-------------------#

class OrbMatch():
    def __init__(self, orbType, checkedGrid):
        self._set = set()
        self._checkedGrid = checkedGrid
        self._ORBTYPE = orbType
    def add(self, row, col):
        self._checkedGrid[row][col] = True
        self._set.add(BoardCoordinate(row,col))
    def getOrbType(self):
        return self._ORBTYPE
    def getCheckedGrid(self):
        return self._checkedGrid
    def wasChecked(self, r, c):
        return self._checkedGrid[r][c]
    def pop(self):
        return self._set.pop()
    def getOrbCount(self):
        return len(self._set)

class BoardCoordinate():
    def __init__(self, row, col):
        self.row = row
        self.col = col
    def __repr__(self):
        return str((self.row*COLS + self.col))
    def __hash__(self):
        return hash(self.__repr__())
    def __eq__(self, other):
        if isinstance(other, BoardCoordinate):
            return ((self.row == other.row) and (self.col == other.col))
        else:
            return False
    def __ne__(self, other):
        return not self.__eq__(other)

####################################################
#-------------------#
#    MAIN OBJECT    #
#-------------------#

class Board():
    def __init__(self, AR):
        self._ARRAY = AR

    def get(self, r, c):
        return self._ARRAY[r][c]
    def remove(self, r, c):
        self._ARRAY[r][c] = NONE
    def swap(self, rA, cA, rB, cB):
        temp = self._ARRAY[rA][cA]
        self._ARRAY[rA][cA] = self._ARRAY[rB][cB]
        self._ARRAY[rB][cB] = temp
    def setOrb(self, orbType, r,c):
        self._ARRAY[r][c] = orbType
    def toString(self):
        return self.__str__()
    def __repr__(self):
        return self.__str__()
    def __str__(self):
        toReturn = ""
        for r in range(ROWS):
            line = ""
            for c in range(COLS):
                line += self._ARRAY[r][c]
            toReturn += line + "\n"
        return toReturn

#####################################################
#-------------------#
#     UNIT TESTS    #
#-------------------#

def BASIC_TEST_RUN(board):
    print(board.toString())
    matches = _CHECK_MATCHES(board)
    combos = len(matches)
    pointMap = _CLEAR_MATCHES(matches, board, MAKE_NEW_POINTMAP())
    print("------------------------")
    print(board.toString())
    print("------------------------")
    print("Combos: " + str(combos))
    print(pointMap)

##    print("adding:")
##    print(ADD_NEW_ORBS_TO_REPLACE_EMPTY_SPACES(board))
##    print("emptyBottomSpaces:")
##    print(GENERATE_BOTTOM_MOST_EMPTY_SPACE_LIST(board))
    
    didGravityWork = _GRAVITY(board)
    while didGravityWork:
        print("------------------------")
        print(board.toString())
        didGravityWork = _GRAVITY(board)
        
    while _ADD_RANDOM_ORBS_TO_EMPTY_SPACES_AFTER_GRAVITY(board):
        print("------------------------")
        print(board.toString())
        _GRAVITY(board)
    
if __name__ == "__main__":
    print("#----------------------#")
    print("#        DEFAULT       #")
    print("#----------------------#")
    BASIC_TEST_RUN(Board(DEFAULT_BOARD_INITALIZER()))
    print("#----------------------#")
    print("#        TEST A        #")
    print("#----------------------#")
    BASIC_TEST_RUN(Board(_TEST_BOARD_INIT_A()))
    print("#----------------------#")
    print("#        TEST B        #")
    print("#----------------------#")
    BASIC_TEST_RUN(Board(_TEST_BOARD_INIT_B()))
    print("#----------------------#")
    print("#        TEST C        #")
    print("#----------------------#")
    BASIC_TEST_RUN(Board(_TEST_BOARD_INIT_C()))

    
