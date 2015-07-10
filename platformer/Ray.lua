local Vector = require '_libs/hump.vector-light'
local Class = require '_libs/hump.class'

Ray = Class {
  x  = 0,
  y  = 0,
  dx = 0,
  dy = 0,
}

function Ray:init(x, y, dx, dy)
  self.x = x
  self.y = y
  self.dx = dx
  self.dy = dy
end

function Ray:intersectsShape(shape, xOffset, yOffset)
  xOffset = xOffset or 0
  yOffset = yOffset or 0
  
  local isIntersecting, t = shape:intersectsRay(self.x + xOffset, self.y + yOffset, self.dx, self.dy)
  local vx, vy = nil
  if isIntersecting then
    vx, vy = Vector.add(self.x + xOffset, self.y + yOffset, Vector.mul(t, self.dx, self.dy))
  end

  return isIntersecting, vx, vy, t
end

function Ray:draw(xOffset, yOffset, xScale, yScale)
  xOffset = xOffset or 0
  yOffset = yOffset or 0
  xScale  = xScale  or 1
  yScale  = yScale  or 1
    
  love.graphics.line(xOffset + self.x, 
                     yOffset + self.y, 
                     xOffset + self.x + self.dx * xScale, 
                     yOffset + self.y + self.dy * yScale)
end



return Ray