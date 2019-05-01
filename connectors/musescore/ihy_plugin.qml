import QtQuick 2.1
import QtQuick.Dialogs 1.0
import QtQuick.Controls 1.0
import MuseScore 1.0
import FileIO 1.0

MuseScore {
    menuPath: "Plugins.Ihy Import-Export"
    version: "2.0"
    description: qsTr("This plugin exports the score to Ihy and imports it back in.")
    pluginType: "dialog"

    id:window
    width:  400; height: 50;
    onRun: {}

    Button {
        id : exportScore
        text: qsTr("Export")
        anchors.bottom: window.bottom
        anchors.right: window.right
        anchors.topMargin: 10
        anchors.bottomMargin: 10
        anchors.rightMargin: 10
        onClicked: {
            // write to temp file
	    var score_name = curScore.name;
            var scorepath = "/home/barestides/dev/ihy/webapp/temp/" + score_name;
            writeScore(curScore, scorepath, "mxl");

            // send score name to server
            var requestABC = new XMLHttpRequest()
            var url = "http://localhost:8080/upload-score/" + score_name
            requestABC.open("POST", url, true)
            requestABC.send()
            }
        }

    Button {
        id : importScore
        text: qsTr("Import")
        anchors.bottom: window.bottom
        anchors.right: exportScore.left
        anchors.topMargin: 10
        anchors.bottomMargin: 10
        onClicked: {
	    var score_name = curScore.name;
            var score_file = "/home/barestides/dev/ihy/webapp/fake-db/" + score_name + "/" + score_name  + "-modified.xml";
            readScore(score_file);
            }
        }

    Button {
        id : buttonCancel
        text: qsTr("Cancel")
        anchors.bottom: window.bottom
        anchors.left: window.left
        anchors.topMargin: 10
        anchors.bottomMargin: 10
        onClicked: {
                Qt.quit();
            }
        }
    }
