local Shape = require '_libs/HardonCollider.shapes'
local Class = require '_libs/hump.class'
local Ray   = require 'Ray'

Player = Class {

  rays = {
    downRays  = {},
    upRays    = {},
    leftRays  = {},
    rightRays = {},
  },

  -- Player constants
  speed = 360,
  gravity = 50.8,
  jumpSpeed = 10,
}

function Player:init(x, y, width, height)
  self.width  = width or 10
  self.height = height or 10
  
  self.velocityX = 0
  self.velocityY = 0
  
  isGrounded = false
  wantsToJump = false
  wantsToMoveLeft = false
  wantsToMoveRight = false
  
  -- Down Rays
  local downRayY  = self.height / 2 - self.height / 10
  local downRayDx = 0
  local downRayDy = self.height / 5
  self.rays.downRays = {
    Ray(-width / 3, downRayY, downRayDx, downRayDy),
    Ray(0,          downRayY, downRayDx, downRayDy),
    Ray(width / 3,  downRayY, downRayDx, downRayDy),
  }
  
  -- Horizontial Rays
  local horizontialRaysX  = width / 2
  local horizontialRaysY  = height / 2.7
  local horizontialRaysDx = width / 4
  local horizontialRaysDy = 0
  self.rays.headRight = Ray(horizontialRaysX, -horizontialRaysY, horizontialRaysDx, horizontialRaysDy)
  self.rays.feetRight = Ray(horizontialRaysX, horizontialRaysY, horizontialRaysDx, horizontialRaysDy)
  self.rays.headLeft  = Ray(-horizontialRaysX, -horizontialRaysY, -horizontialRaysDx, horizontialRaysDy)
  self.rays.feetLeft  = Ray(-horizontialRaysX, horizontialRaysY, -horizontialRaysDx, horizontialRaysDy)
    
  self.shape = Shape.newPolygonShape(x, y, x + width,y, x + width, y + height, x, y + height)
  self.shape.name = 'player'
end

function Player:getReleventShapes()
  local objects = {}
  for neighbor in pairs(self.shape:neighbors()) do
    for groupName, _ in pairs(neighbor._groups) do
      if groupName == 'statics' then
        objects[#objects + 1] = neighbor
        break
      end
    end
  end
  
  return objects
end

function Player:updateVelocity(dt)
  if self.isGrounded and self.wantsToJump then
    print('jump')
    self.velocityY = -self.jumpSpeed
    self.isGrounded = false
  end
  self.velocityY = self.velocityY + self.gravity * dt
  
  if self.wantsToMoveRight or self.wantsToMoveLeft then
    if self.wantsToMoveRight then
      self.velocityX = self.speed * dt
    elseif self.wantsToMoveLeft then
      self.velocityX = -self.speed * dt
    else
      self.velocityX = 0
    end
  else
    self.velocityX = 0
  end
end

function stupidThing(self, shapes, dt)
  local x, y = self.shape:center()
    
  local maxSlope = 0  

  if self.velocityY >= 0 then
    local lastY = 0
    local lastX = 0
    local totalY = 0
    local totalIntersect = 0
    local slope = 0
    
    for _, ray in ipairs(self.rays.downRays) do
      
      local maxY = y + ray.y + ray.dy
      
      local closestY = maxY
      local closestX
      
      for _, object in pairs(shapes) do
        
        local isIntersecting, vx, vy = ray:intersectsShape(object, x, y)
        
        if isIntersecting then
          if math.abs(ray.y + y - vy) <= ray.dy + self.velocityY then

            if vy < closestY then
              closestY = vy
              closestX = vx
            end
          end
        end
      end
      
      if closestY < maxY then
        totalY = totalY + closestY
        
        if(totalIntersect > 0) then
          slope = ((lastY - closestY) / (lastX - closestX)) + slope
        end
        
        totalIntersect = totalIntersect + 1
        lastY = closestY
        lastX = closestX
      end
    
    end
  
    if totalIntersect > 0 then
        y = totalY / totalIntersect - self.height / 2

        self.velocityY = 0
        self.isGrounded = true
        
        if totalIntersect > 1 then
          maxSlope = slope / (totalIntersect - 1)
          --print('slope', maxSlope)
        end

      end
  end
  --[[
  if self.wantsToMoveRight then
    for _, object in pairs(shapes) do
        local isIntersecting, vx, vy = self.rays.headRight:intersectsShape(object, x, y)
        if isIntersecting then
          if math.abs(self.rays.headRight.x + x - vx) <= self.rays.headRight.dx + self.velocityX then
            self.velocityX = 0
            break
          end
        else
          local isIntersecting, vx, vy = self.rays.feetRight:intersectsShape(object, x, y)
          if isIntersecting then
            if math.abs(self.rays.headRight.x + x - vx) <= self.rays.headRight.dx + self.velocityX then
              self.velocityX = 0
            break
  
          end
          end
        end
    end
  end
  --]]
  if math.abs(maxSlope) > 0.1 then
    local speed = self.speed * dt
    
    if self.wantsToMoveRight then
      y = y - (speed * maxSlope) 
      x = x + speed
    elseif self.wantsToMoveLeft then
      y = y + (speed * maxSlope) 
      x = x - speed
    end
  else
    x = x + self.velocityX
    y = y + self.velocityY
  end
  
  return x, y
end

function Player:update(dt)
  local shapes = self:getReleventShapes()
  
  self:updateVelocity(dt)
  local x, y = self.shape:center()

  x, y = stupidThing(self, shapes, dt)
  

  self.shape:moveTo(x, y)  
end

function Player:debugDraw()
  for _, other in pairs(self:getReleventShapes()) do
    other:draw('fill')
  end
  
  -- draw bounds
  love.graphics.setColor(0,255,0)
    self.shape:draw('line')
  
  -- draw rays
  love.graphics.setColor(255,0,255)
    local x, y = self.shape:center()
    if self.velocityY >= 0 then
      for _, ray in ipairs(self.rays.downRays) do
        ray:draw(x, y)
      end
    end
    
    if self.velocityX > 0 then
      for _, ray in ipairs(self.rays.rightRays) do
        ray:draw(x, y)
      end
    elseif self.velocityX < 0 then
      for _, ray in ipairs(self.rays.leftRays) do
        ray:draw(x, y)
      end
    end
  self.rays.headRight:draw(x, y)
  self.rays.feetRight:draw(x, y)
  self.rays.headLeft:draw(x, y)
  self.rays.feetLeft:draw(x, y)
end

return Player