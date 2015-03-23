I recently came across **rotonyms**: words that ROT-13 in to other words,
see: http://www.willmcgugan.com/2008/01/22/rotonym-what-the-heck-is-a-rotonym

Another way of rotating is rotating each letter by 180 or 90 degrees.

lowercase character mappings, rotation 180:
```
d	p
a	e
b	q
h	y
m	w
n	u
```
Note that this mapping works in two directions, i.e. letters on the right also count as inputs.

self-mapping: o,x

examples:
```
hexapod, yaxedop
unwaxed, numexap
anyhow, euhyom
daemon, peawou
```
upper case character mappings, character rotation 90 clockwise:
```
E	M
H	I
I       H
N	Z
U	C
W	E
Z	N
```
This mapping only works from left to right.

self-mapping: X, O

examples:
```
WHINE, EIHZM
ZINE, NHZM
NEW, ZME
```
Write a Java program that generates rotonyms


---


A concept I came up with is called **Rotodromes**: words that read the same when rotated 180 degrees (ideally real, existing words).

lowercase examples: pod, anyhue, opuanuendo

Write a Java program which generates rotodromes.


---


See also:
  * http://www.anagrammer.com/
  * http://www.rinkworks.com/words/palindromes.shtml