# Task List

Note: Components = interface, analysis of existing melodies, synthesizing the new music, packaging the new music

## Thadeus - Interface
1. Research Musescore plugins.
2. Create a Musescore plugin that will allow the user to specify settings for the music they wish to generate and input musical snippets to act as a base for the generated music.
3. Integrate the plugin with music analyzer so that the so that the user’s inputs will be sent to the analyzer in order to generate music.
4. Test the plugin.
5. Document the plugin at https://musescore.org/en/plugins

## Eric - Packaging new music
1. Research tools and strategies for packaging musical pieces into midi files.
2. Implement a packaging technique, incorporating it into the existing musescore code base.
3. Test the implementation to ensure expected functionality.
4. Integrate the packager with other components of the Ihy project to validate that each component interfaces correctly with the other.
5. Document design practices and decisions throughout the process researching, developing, testing and integrating.

## Grace - Analysis
1. Research tools and strategies for analyzing components of musical pieces.
2. Specify and implement a music analyzer design strategy, incorporating it into the existing musescore code base.
3. Test the implementation of the music analyzer to ensure expected functionality.
4. Integrate the music analyzer with other components of the Ihy project to validate that each component interfaces correctly with the other.
5. Document design practices and decisions throughout the process researching, developing, testing and integrating.

## Braden - Synthesis
1. Research existing generation strategies.
2. Design and implement synthesis of new music given analysis of existing music and generation attributes.
3. Test that generated scores are within constraints defined by the attributes.
4. Integrate into rest of project. Consider approaches: within Musescore plugin environment or external?
5. Document the generation process, including any core abstractions used.
