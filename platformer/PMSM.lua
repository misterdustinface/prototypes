local PlayerMovementStateMachine = require 'State.StateMachine'
local MovementState = require 'PlatformerMovement.MovementState'
local ActionState = require 'PlatformerMovement.MovementTransitionState'
local PMD = {
    xPos = 0, 
    yPos = 0,
    xVel = 0, 
    yVel = 0,
    direction = 'right',
    turn = 
    function(this)
        if direction == 'right' then
            direction = 'left'
        else
            direction = 'right'
        end
    end,
    gravity = 2,
    jumpSpeed = 20,
    walkSpeed = 3,
}

local STATIONARY = MovementState("STATIONARY")
local WALKING = MovementState("WALKING")
local JUMPING = MovementState("JUMPING")
local FALLING = MovementState("FALLING")
local SLIDING = MovementState("SLIDING")

local JUMP = ActionState("JUMP", JUMPING)
local WALK = ActionState("WALK", WALKING)
local HALT = ActionState("HALT", STATIONARY)
local LAND = ActionState("LAND", STATIONARY)
local FALL = ActionState("FALL", FALLING)
local SLIP = ActionState("SLIP", SLIDING)
local SLIDE = ActionState("SLIDE", SLIDING)

STATIONARY:setTransitionStates({FALL, SLIDE, WALK})
WALKING:setTransitionStates({FALL, SLIP, JUMP, HALT})
JUMPING:setTransitionStates({FALL, HALT})
FALLING:setTransitionStates({LAND})
SLIDING:setTransitionStates({FALL, HALT, JUMP})

STATIONARY:setUpdateFunction(function(xState) end)
WALKING:setUpdateFunction(function(xState) 
    PMD.xPos = PMD.xPos + PMD.xVel
    PMD.yPos = PMD.yPos + PMD.yVel
end)
JUMPING:setUpdateFunction(function(xState) 
    PMD.xPos = PMD.xPos + PMD.xVel
    PMD.yPos = PMD.yPos + PMD.yVel
    PMD.yVel = PMD.yVel - PMD.gravity
end)
FALLING:setUpdateFunction(  function(xState) 
    PMD.xPos = PMD.xPos + PMD.xVel
    PMD.yPos = PMD.yPos + PMD.yVel
    PMD.yVel = PMD.yVel + PMD.gravity
end)
SLIDING:setUpdateFunction(  function(xState) 
    PMD.xPos = PMD.xPos + PMD.xVel
    PMD.yPos = PMD.yPos + PMD.yVel
end)

JUMP:setUpdateFunction(function(xState) 
    PMD.yVel = PMD.jumpSpeed
end)
WALK:setUpdateFunction(function(xState) 
    if PMD.direction == 'right' then
        PMD.xVel = PMD.walkSpeed
    else
        PMD.xVel = -PMD.walkSpeed
    end
end)
HALT:setUpdateFunction(function(xState) 
    PMD.xVel = 0
    PMD.yVel = 0
end)
LAND:setUpdateFunction(function(xState) 
    PMD.xVel = 0
    PMD.yVel = 0
end)
FALL:setUpdateFunction(function(xState) 
    PMD.yVel = 0
end)
SLIP:setUpdateFunction(function(xState) 
    -- set xVel and yVel relative to surface normal
end)
SLIDE:setUpdateFunction(function(xState) 
    -- set xVel and yVel relative to surface normal
end)

JUMP.isApplicable = function(xAction) 
  
end
WALK.isApplicable = function(xAction) 
  
end
HALT.isApplicable = function(xAction) 
  
end
LAND.isApplicable = function(xAction) 
  
end
FALL.isApplicable = function(xAction) 
  
end
SLIP.isApplicable = function(xAction) 
  
end
SLIDE.isApplicable = function(xAction) 
  
end

JUMP.isComplete = function(xAction) 
  return true
end
WALK.isComplete = function(xAction) 
  return true
end
HALT.isComplete = function(xAction) 
  return true
end
LAND.isComplete = function(xAction) 
  return true
end
FALL.isComplete = function(xAction) 
  return true
end
SLIP.isComplete = function(xAction) 
  return true
end
SLIDE.isComplete = function(xAction) 
  return true
end

local PMSM = PlayerMovementStateMachine()
PMSM:addState(STATIONARY)
PMSM:addState(WALKING)
PMSM:addState(JUMPING)
PMSM:addState(FALLING)
PMSM:addState(SLIDING)
PMSM:forceState(STATIONARY)

return PMSM, PMD