local Class    = require '_libs/hump.class'
local Pointlight = require 'PointLight'

Lighting = Class {
  lights = {},
  collider = nil,
  ambientLight,
  
  -- Constants
  lightingCollisionGroup = 'lights',
}

function Lighting:init(collider)
  self.collider = collider
  self.ambientLight = love.graphics.newShader[[
    vec4 effect(vec4 color, Image texture, vec2 texture_coords, vec2 pixel_coords) {
      vec4 c = color;
      c.r *= 0.3;
      c.b *= 0.3;
      c.g *= 0.3;
   
      return c * Texel(texture, texture_coords);
    }
  ]]
  
  self.light = love.graphics.newShader [[
    vec4 effect(vec4 color, Image texture, vec2 texture_coords, vec2 pixel_coords) {

      vec4 c = vec4( 2.0, 1.4, 1.0, 0.7 );
    
      c *= abs( 1.0 / ( sin( length(texture_coords / 2.0 -1) ) * .01 ) );
    
      return c * Texel(texture, texture_coords);
    }
  ]]
end

function Lighting:loadMap(map, layerName)
  lightingLayer = map.layers[layerName]
  
  for _, light in ipairs(lightingLayer.objects) do
    if light.type == 'point' then  
      local x = light.x + light.width / 2
      local y = light.y + light.height / 2
      local radius = light.width / 2
      
      local circle = self.collider.hc:addCircle(x, y, radius)
      self.collider:addToGroup(self.lightingCollisionGroup, circle)
      self.collider.hc:setPassive(circle)
      table.insert(self.lights, PointLight(x, y, radius, circle))
    end
  end
end
  
function Lighting:update(dt)

end

function Lighting:preDraw()
  --love.graphics.setShader(self.ambientLight) --draw something here
end

function Lighting:draw()
  --love.graphics.setShader(self.light)

  for _, light in pairs(self.lights) do
    light:draw()
  end
  --love.graphics.setShader()
end

return Lighting