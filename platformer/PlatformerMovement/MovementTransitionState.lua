local State = require 'State.State'

MovementTransitionState = State {
  onUpdate = function(xTransitionState) end,
}

function MovementTransitionState:init(xName, xMovementState)
  this.name = xName
  this.movementState = xMovementState
end

function MovementTransitionState:isApplicable()
  return false
end

function MovementTransitionState:isComplete()
  return true
end

function MovementTransitionState:transition()
  if this:isComplete() then
    return this.movementState
  else
    return this
  end
end

return MovementTransitionState