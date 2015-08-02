local function sq(X)
  return X * X
end

local function dist(A, B)
  return math.sqrt(sq(A.x - B.x) + sq(A.y - B.y))
end

local function intersects(xCircleA, xCircleB)
  return dist(xCircleA, xCircleB) <= (xCircleA.radius + xCircleB.radius)
end

local function constructor()
  return {
    radius = 0,
    x = 0,
    y = 0,
    intersects = intersects,
  }
end

return constructor