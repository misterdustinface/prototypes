
local GestureInput = {
  touchDown = function (x, y, poer, button) 
        world:flap()  
  
    return false
   end,

  tap = function (x, y, count, button)   
    return false
  end,

  longPress = function(x, y) 
    world:glide()
    return false;
  end,

  fling = function(velocityX, velocityY, button)
    
    return false;
  end,

  pan = function(x, y, deltaX, deltaY) 
    --world:speedBoost(deltaX / Globals.BAT_SPEED_UP_DELTA_MAGIC_SCALE);
    return false;
  end,

  panStop = function( x, y, poer, button) 
    return false;
  end,

  zoom = function( initialDistance, distance) 
    return false;
  end,

  pinch = function(initialPoer1, initialPoer2, poer1, poer2) 
    return false;
  end
}

local Wrapper = {
  touchDown = function (x, y, poer, button)   
    return GestureInput:touchDown(x, y, poer, button)
   end,

  tap = function (x, y, count, button)   
    return GestureInput:tap(x,y,count,button)
  end,

  longPress = function(x, y) 
    return GestureInput:longPress(x,y)
  end,

  fling = function(velocityX, velocityY, button)
    return GestureInput:fling(velocityX, velocityY, button);
  end,

  pan = function(x, y, deltaX, deltaY) 
    return GestureInput:pan(x,y,deltaX, deltaY);
  end,

  panStop = function( x, y, poer, button) 
    return GestureInput:panStop(x,y,poer,button)
  end,

  zoom = function( initialDistance, distance) 
    return GestureInput:zoom(initialDistance, distance);
  end,

  pinch = function(initialPoer1, initialPoer2, poer1, poer2) 
    return GestureInput:pinch(initialPoer, initialPoer2, poer1, poer2);
  end
}
local listener = luajava.createProxy("lua.GdxGestureListener", Wrapper)
local detector = luajava.new(luajava.bindClass("com.badlogic.gdx.input.GestureDetector"), listener)

detector:setLongPressSeconds(0.3)
--detector:setMaxFlingDelay(2)
--detector:setTapCountInterval(0.3)
--detector:setTapSquareSize(1)

return detector