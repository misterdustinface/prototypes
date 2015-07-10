local HC = require '_libs/HardonCollider'
local Class = require '_libs/hump.class'

local Collider = Class{}

Collider.hc = {}
Collider.statics = {}

Collider.on_collide = function(dt, shape_a, shape_b)
  
--  print('collision', shape_a.name, shape_b.name) 

end

function Collider:init()
  self.hc = HC(100, self.on_collide)

end

function Collider:update(dt) 
  self.hc:update(dt)
end

local StaticShapeCreator = {
  ['rectangle'] = function(self, o)
    return self.hc:addRectangle(o.x, o.y, o.width, o.height)
  end,

  ['polygon'] = function(self, o)
    local verticies = {}

    for _, v in pairs(o.polygon) do
      verticies[#verticies + 1] = v.x
      verticies[#verticies + 1] = v.y
    end
    
    return self.hc:addPolygon(unpack(verticies))
  end,
}

function Collider:loadStaticsFromMap(map, layerName)
  statics = map.layers[layerName]
    
  for _, object in ipairs(statics.objects) do
    local newColliderShape
    
    newColliderShape = StaticShapeCreator[object.shape](self, object)
    
    if newColliderShape ~= nil then
      self.hc:setPassive(newColliderShape)
      self.hc:addToGroup('statics', newColliderShape)
      newColliderShape.name = 'statics#' .. tostring(#self.statics + 1)
      newColliderShape.isWall = true
      
      if object.properties.notWall then
        newColliderShape.isWall = false
      end
      self.statics[#self.statics + 1] = newColliderShape
    end
  end
end

function Collider:drawStatics(drawType)
  for k,v in pairs(self.statics) do
    v:draw(drawType)
  end
end

function Collider:addShape(shape)
  self.hc:addShape(shape)
end

function Collider:addToGroup(group, shape)
  self.hc:addToGroup(group, shape)
end

return Collider