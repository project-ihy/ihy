# User Manual

## Installation
Ihy in its current state is made up of 2 main components:

##### Webapp
This is the meat of the program. It contains a web-based interface for
making selections, utilities for converting scores to ABC Notation,
and the python generation modules.

##### MuseScore Connector
Ihy was designed to be modular, so that many different composition
tools can be used with it, such as Sibelius, or even DAWs like Logic
and Ableton. Ideally, developers could write their own connectors for
any tool they want to use. The connector just needs to be able to send
the score as ABC, and import generated ABC scores.

### Up and Running
Currently, Ihy can only be run from a development environment. A
primary next step is to prepare the project to be packaged up as a
jar, with all dependencies included.

To start the webserver, you will need
[leiningen](https://leiningen.org/) installed.

Navigate to the `project-root/webapp` and execute the command:
`lein repl`

Start the webapp by running `(start)` in the repl.

In Musescore, import the ihy connector plugin. This plugin is located
at: `PROJECT-ROOT/connectors/musescore/ihy_plugin.qml`

To run this, open musescore, and then go to Plugins->Plugin Creator.
Paste the entirety of the ihy_plugin.qml file into the creator and
click "run".

There should now be a dialog window with two buttons: Import and Export.
![](https://i.imgur.com/Ey9Dqre.png)

## Usage

Once the melody you've created is complete in MuseScore, complete the following steps to secure an IHY harmonic element.

### Export the Score to the Ihy webapp

Follow the above directions to get the connector plugin running.

Click the Export button in the plugin dialog.

### Navigate to the IHY Web Application
If everything has gone correctly, the score should be viewable at
`localhost:8080/name-of-score`.

### Select Music Window

1. Identify the first measure of the desired music selection and select it
2. Identify the last measure of the desired music selection and select it
3. Verify that the selection is highlighted on the screen

### Configure Settings

1. Move the slider corresponding to the Syncopation window to the desired scale
2. Move the slider corresponding to the Complexity window to the desired scale
3. Select `Apply`
![](https://i.imgur.com/zQUaIwa.png)

### Import the New ABC File
Go back to the Musescore connector plugin and select the `import` option.
![](https://i.imgur.com/lIkFnCM.png)

The score, with generated accompaniment, should now be back in
Musescore and playable.
