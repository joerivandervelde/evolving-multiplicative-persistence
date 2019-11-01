# Evolving Multiplicative Persistence

Find big multiplicative persistent numbers by evolution.

See https://oeis.org/A003001 for more information on these numbers and 
Numberphile video: https://www.youtube.com/watch?v=Wim9WJeDTHQ.

We create a random individual (i.e. number) and from this founder a 
population of mutated clones. Each individual is scored for multiplicative 
persistence. The best fraction survives and produces new clones to fill a new
 generation.

There are two types of mutatios: point mutations, which alter the digit at
one place, and indel mutations, which are insertions of new digits or
deletions of existing digits.

Example output using default settings:

```
Generation 0 of 250 yielded 6681917222667468237268492883141877417666 with length 40 and a MP of 3
Generation 3 of 250 yielded 7177772288733788333337176661 with length 28 and a MP of 4
Generation 4 of 250 yielded 111111339443766297622772739932 with length 30 and a MP of 5
Generation 6 of 250 yielded 737621319 with length 9 and a MP of 7
Generation 8 of 250 yielded 888811184424442224296 with length 21 and a MP of 10
Generation 28 of 250 yielded 868477447778212976 with length 18 and a MP of 11
Best result is 868477447778212976 with length 18 and a MP of 11
Breakdown the multiplicative persistence of 868477447778212976:
MP 1: 8 x 6 x 8 x 4 x 7 x 7 x 4 x 4 x 7 x 7 x 7 x 8 x 2 x 1 x 2 x 9 x 7 x 6 = 4996238671872
MP 2: 4 x 9 x 9 x 6 x 2 x 3 x 8 x 6 x 7 x 1 x 8 x 7 x 2 = 438939648
MP 3: 4 x 3 x 8 x 9 x 3 x 9 x 6 x 4 x 8 = 4478976
MP 4: 4 x 4 x 7 x 8 x 9 x 7 x 6 = 338688
MP 5: 3 x 3 x 8 x 6 x 8 x 8 = 27648
MP 6: 2 x 7 x 6 x 4 x 8 = 2688
MP 7: 2 x 6 x 8 x 8 = 768
MP 8: 7 x 6 x 8 = 336
MP 9: 3 x 3 x 6 = 54
MP 10: 5 x 4 = 20
MP 11: 2 x 0 = 0

```
