package devan.rendering;

// for each [ray] 
// 		get first point of [ray collision]
//		// THE TRACING PART
//		for each [light], connect [lightline] from [ray collision] to [light] ** HERE **
//			if [lightline] distanceSquared < [light distance] squared
//				for each [wall] in staticWorld
//					if [lightline] intersects [wall]
//						** break out. LOOK AT NEXT LIGHT **
//				// light does not intersect
//				calculate light intensity over light distance or something like that (within the light itself)
//				get surface information from object and calculate what light of intensity does with it
//				// influence ray data with this information
//		// END TRACING PART
//		add to visible list
