# Path-Traversal

"Traverser.java" is where "main" is

A program to find the path of least resistance across a topographical map

The purpose of the program is to find a path across a topographical map that takes the least amount of energy - ie. least resistance.

The two files: Colorado_844x480.dat and Colorado_480x480.dat come from Nifty but were referenced from the website - https://maps.ngdc.noaa.gov/viewers/grid-extract/index.html

I created a file called "topMapGen.java" which creates topographical map data, only it does it randomly.. so it looks like tv screen static.

The program's main algorithm is a greedy algorithm that find the path across the map, which utilizes memoization to help the run-time.

The two images show examples if the output.

Image 1 shows the best path (green) by itself.

Image 2 shows both the best path (green) and a path from the lowest point in the first column (blue).
