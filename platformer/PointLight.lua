local Class = require '_libs/hump.class'
local Ray = require 'Ray'

PointLight = Class{}

PointLight.angleOffset = 0.00001

function PointLight:init(x, y, radius, circleShape)
  self.x = x
  self.y = y
  self.radius = radius
  self.shape = circleShape
  self.divisions = 10
end

function PointLight:draw()
  
  self.shape:draw()

  local intersects = self:getSightPolygon(self.x, self.y, self.shape:neighbors())
  
  local polygons = {{self.x, self.y}}
  local polygonNum = 1
  local verticiesPerPoly = 8
  local currentNumVerticies = 1
  for _, intersect in pairs(intersects) do
    table.insert(polygons[polygonNum], intersect.vx)
    table.insert(polygons[polygonNum], intersect.vy)
    
    currentNumVerticies = currentNumVerticies + 1
    if currentNumVerticies > verticiesPerPoly then
      currentNumVerticies = 2
      polygonNum = polygonNum + 1
      polygons[polygonNum] = {self.x, self.y, intersect.vx, intersect.vy}
    end
  end
  
  table.insert(polygons[polygonNum], intersects[1].vx)
  table.insert(polygons[polygonNum], intersects[1].vy)
  local i = 2

  while #polygons[polygonNum] < 6 do
    table.insert(polygons[polygonNum], intersects[i].vx)
    table.insert(polygons[polygonNum], intersects[i].vy)
    i = i + 1
  end
  
  love.graphics.setColor(255, 0,255,100)
  for _, polygon in pairs(polygons) do
    love.graphics.polygon('fill', unpack(polygon))
  end
  
  local lastAngle = 0
  verticies = {}
  for _, intersect in ipairs(intersects) do
    table.insert(verticies, intersect.vx)
    table.insert(verticies, intersect.vy)
  end
  
  love.graphics.setColor(0, 0,255)

  --love.graphics.polygon('fill', unpack(verticies))

  
    love.graphics.setColor(255,255,0)
    love.graphics.setPointSize(5)
  for _, intersect in pairs(intersects) do
    love.graphics.point(intersect.vx, intersect.vy)
  end
  
end

function PointLight:getSightPolygon(sightX, sightY, shapes)
  local points = {}
  for _, shape in pairs(shapes) do
    for _, v in pairs(shape._polygon.vertices) do
      table.insert(points, v)
    end
  end
  
  local angles = {}
  for _, point in pairs(points) do
    local angle = math.atan2(point.y - sightY, point.x - sightX);
		point.angle = angle;
    
    table.insert(angles, angle - self.angleOffset)

    table.insert(angles, angle)
    table.insert(angles, angle + self.angleOffset)
  end
  
  for i = 0, self.divisions do
    table.insert(angles, i * (2 * math.pi / self.divisions))
  end
  
  
  local presision = 1000000
  local set = {}
  for _, angle in pairs(angles) do
    set[math.floor(angle * presision)] = true
  end

  angles = {}
  for angle, _ in pairs(set) do
    table.insert(angles, angle / presision)
  end
  
  local intersects = {}
  for _, angle in pairs(angles) do
    local dx = math.cos(angle) * self.radius
    local dy = math.sin(angle) * self.radius
    
    local ray = Ray(sightX, sightY, dx, dy)
    local isIntersecting, vx, vy, t = ray:intersectsShape(self.shape)
    closestIntersect = {vx = vx, vy = vy, t = t, shape = self.shape}
    for _, shape in pairs(shapes) do
      local isIntersecting, vx, vy, t = ray:intersectsShape(shape)
      
			if isIntersecting and t > 0 and (not closestIntersect or t < closestIntersect.t) then
        closestIntersect = {vx = vx, vy = vy, t = t, shape = shape, ray = ray};
			end
    end
    
    if closestIntersect then
      if angle < 0 then
        angle = 2 * math.pi + angle
      end
      closestIntersect.angle = angle
      
      table.insert(intersects, closestIntersect)
    end
  end
  
  table.sort(intersects, function(a, b) return a.angle > b.angle end)
  
  return intersects
end


return PointLight