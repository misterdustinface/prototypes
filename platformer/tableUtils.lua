local tableUtils = {}

tableUtils.copy = function(orig)
  local orig_type = type(orig)
  local copy
  if orig_type == 'table' then
    copy = {}
    for orig_key, orig_value in next, orig, nil do
      copy[tableUtils.copy(orig_key)] = tableUtils.copy(orig_value)
    end
    setmetatable(copy, tableUtils.copy(getmetatable(orig)))
  else
    copy = orig
  end
  return copy
end

tableUtils.reverse = function(table)
  local reverse = {}
  for i = 1, #table do
    reverse[#table - i] = table[i]
  end
  return reverse
end

tableUtils.print = function(table)
  for k, v in pairs(table) do
    print(k, v)
  end
end

tableUtils.printAll = function(table)
  for k, v in pairs(table) do
    print(k, v)
    io:flush()
    if type(v) == "table" then
      tableUtils.printAll(v)
    end
  end
end

return tableUtils