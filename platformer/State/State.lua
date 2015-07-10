local Class = require '_libs/hump.class'

State = Class {
  onUpdate = function(xState) end,
}

function State:init(xName)
  this.name = xName
end

function State:setUpdateFunction(onUpdate)
  this.onUpdate = onUpdate
end

function State:update()
  this.onUpdate(this)
end

function State:transition()
  return this
end

return State