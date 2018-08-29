# Hey what do we wanna do?

### Broadly
Write a program that is capable of generating music that sounds
indistinguishible from music composed by a real person.

### Specifically

#### Melody Generation:
- Can we generate interesting melodies?
- What kind of parameters make one melody feel different than
  another melody?
  - syncopation, adherence to the key, length of notes, probs more
- What makes certain melodies infectiously catchy?
  - We could look at melodies of established popular songs and
	try to figure out some sort of formula for it.

#### [Arrangement](https://en.wikipedia.org/wiki/Arrangement) / [Orchestration](https://en.wikipedia.org/wiki/Orchestration)
###### I'm pretty sure those are accurate terms for the ideas expressed below

If we've already generated one melody, how do we generate one that
sounds interesting when played simultaneously to it? And interesting
in what way? You could copy a melody to a different instrument, and
leave it unchanged. Now this other instrument supports the first
melody. Or we could take a melody, and shift the whole thing up in
pitch, to get some harmony going on. Or what if we remove every other
note in the melody and shift it a half beat forward. What would that
sound like?

There are a huge number of mathematical transformations we can do once
we have a melody expressed as data. The key is finding which
transformations have musical impact, and what that impact is.

Or what about generating a melody to play after another
melody. Sometimes a melody _demands_ a follow up, and the conjunction
of the two have a greater effect than the sum of each individual melody.

The song [Blister in the Sun](https://www.youtube.com/watch?v=dRWHjsRjA2Y)
comes to mind. The main verse arrangement is minimal, playful, and reserved.
The chorus just lets loose with this cathartic release of emotion.

#### Music as Change over Time
A "good song" maintains listener interest throughout. Listener
interest can be lost if they become bored (song is too predictable)
or overwhelmed (song is too complicated).

Put another way:
Pattern recognition is the core of why music is interesting to listen
to. Your brain subconsciously expects what it's going to hear next,
and then is either satisfied or surpised by what it gets.

"Good" music balances these moments of
satisfaction and surprise. Too much of what the listener expects is
boring (nursery rhymes), too much surprise, and the listener will
become lost (improv jazz).

Note that lyrics throw a huge ugly wrench into all of this, so we'll
just pretend they don't really exist for now.

If we treat every single thing that happens in a song as a
quantifiable change, we can gain control over how much surprise or
satisfaction to give the listener.

###### Example:
We introduce a song by playing just a kick drum on every beat:

```
   1 - & - 2 - & - 3 - & - 4 - & -
k |*       *       *       *
```
This is about as simple as it gets. The listener will get bored
pretty quickly, so we have to add a change to maintain interest.

```
   1 - & - 2 - & - 3 - & - 4 - & -
k |*       *       *       *
hh|    *       *       *       * *
```
For the second measure, we have the same kick pattern, with off beat
hi hats added, and we threw in an extra hi hat on the 'uh' of 4 to
kinda lead us into the next measure.

So what are the differences between the second bar and the first?
We've introduced a new instrument, with a relative pattern similar to the
kick pattern, but it is shifted by half a beat. Additionally, we've
played two notes closer together than we'd previously had. 4 times
closer together, in fact!

I think that being able to reliably quantify this difference from
measure to measure (and probably, even within measures) is very
important. And to take it a step further, _in what way_ does a change
affect the song?


### What about user input?
I think there is enough stuff on the generative side to fill many
months of work, but for the sake of completeness and impressiveness,
it makes sense to have some sort of interface for controlling the
resulting composition. I haven't really given this any thought, so I
have no ideas about what it should entail.


### Composition vs Timbre
The way I see it, the two most important attributes of music are
composition (the notes being played), and timbre (the specific sounds
of the things playing the notes). I think that this program should be
limited only to control composition. This is because the things
that define a piece's composition are much more mathematical (and
thus programmable) than a piece's timbre. Things like:
- The notes themselves (identified either by midi or frequency)
- The amount of time between notes
- The ratio of notes to other notes

All timbrel qualities of the piece would be controlled outside the
program. Really, one could have the program generate sheet music, and
that could be played by real musicians, but I think for demonstration
purposes, midi signals that get output to software instruments will
work best.

The program would need knowledge of what instruments to use when
generating the piece. These may or may not be defined by the
user. User-defined probably makes more sense, but if you wanted that
to be selected on generation, you could have classes of
instruments like percussive, can-play-chords, melodic (but mostly
rhythm), can-play-chords-and-melodies, etc.
