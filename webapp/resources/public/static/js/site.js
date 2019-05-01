
function Analyze(){
    start = document.getElementById("startMeasure").value;
    end = document.getElementById("endMeasure").value;
    sync = document.getElementById("syncopation").value;
    comp = document.getElementById("complexity").value;
    score_name = $("#score-name").text();
    // console.log(start + "," + end+","+sync+","+comp + score_name)
    uri = "/generate-instrument/" + score_name
    data = {start_measure: start, end_measure: end, syncopation: sync, complexity: comp}
    console.log(start + "," + end+","+sync+","+comp)
    $.ajax({
	url: uri,
	type: "POST",
	data: data,
	success: function(result) {
    	    location.href = result;
	}
    });
}


function parseQueryString(){
 var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=');
        if (decodeURIComponent(pair[0]) == 'start_measure') {
        	console.log("start_measure" + decodeURIComponent(pair[1]));
            document.getElementById("startMeasure").value = decodeURIComponent(pair[1]);
        }
        if (decodeURIComponent(pair[0]) == 'end_measure') {
        	console.log("end_measure" + decodeURIComponent(pair[1]));
            document.getElementById('endMeasure').value = decodeURIComponent(pair[1]);
        }
        if (decodeURIComponent(pair[0]) == 'complexity') {
        	console.log("complexity" + decodeURIComponent(pair[1]));
            document.getElementById('complexity').value = decodeURIComponent(pair[1]);
        }
        if (decodeURIComponent(pair[0]) == 'syncopation') {
        	console.log("syncopation" + decodeURIComponent(pair[1]));
            document.getElementById('syncopation').value = decodeURIComponent(pair[1]);
        }
    }

    setBoxColors(document.getElementById("startMeasure").value,document.getElementById("endMeasure").value);
}



function getMeasureBoxCoordinates(){
	var coordinates = [];
	var verticalCoordinates = [];
	var measureLines = document.getElementsByClassName("bW");
	var musicSvg = document.getElementsByClassName('music');

	var firstSplit = [];
	for (var i = 0; i < measureLines.length; i++){
		// console.log(measureLines[i].attributes.d.value);
		coordinates[i] = [];
		firstSplit[i] = measureLines[i].attributes.d.value.split("M");

		coordinates[i].push(20);

		verticalCoordinates[i] =  parseFloat(measureLines[i].attributes.d.value.split(" ",2)[1].toString().split("v",1).toString()) - 34;
		var secondSplit = [];
		// console.log(firstSplit);
		// console.log(verticalCoordinates);

		for(var j = 0; j < firstSplit[i].length; j++){
			secondSplit[j] = firstSplit[i][j].split(" ",1);
			if(secondSplit[j] != "" && secondSplit[j].toString() != secondSplit[j-1].toString()){
				coordinates[i].push(secondSplit[j].toString());
			}

		}
	}

	return [coordinates, verticalCoordinates];
}

function insertMeasureBoxes(){
	var svgns = "http://www.w3.org/2000/svg";
	var musicSvg = document.getElementsByClassName('music');
	var measureLines = document.getElementsByClassName('bW');
	var coordinatesArray = getMeasureBoxCoordinates();
	var coordinates = coordinatesArray[0];
	var verticalCoordinates = coordinatesArray[1];
	// console.log(coordinates);

	var idCount = 0;
	for (var i = 0; i < coordinates.length; i++){
		var defs = document.createElementNS(svgns, 'defs');
		for(var j = 0; j < coordinates[i].length; j++){
			if(j == coordinates[i].length -1){
				continue;
			}

			var rect = document.createElementNS(svgns, 'rect');
			var rightRoundedClipPath = document.createElementNS(svgns, 'clipPath');
			var rightRounded = document.createElementNS(svgns, 'rect');
			var leftRoundedClipPath = document.createElementNS(svgns, 'clipPath');
			var leftRounded = document.createElementNS(svgns, 'rect');
			var roundedClipPath = document.createElementNS(svgns, 'clipPath');
			var rounded = document.createElementNS(svgns, 'rect');

			var border = document.createElementNS(svgns, 'rect');

			var x = coordinates[i][j];
			var y = verticalCoordinates[i];
			var width = coordinates[i][j + 1] - coordinates[i][j];
			var height = 45;


			rect.setAttributeNS(null, 'id', idCount);
			rect.setAttributeNS(null, 'class', 'measureBox');
			rect.setAttributeNS(null, 'height', height);
			rect.setAttributeNS(null, 'width', width);
			rect.setAttributeNS(null, 'x', x);
			rect.setAttributeNS(null, 'y', y);
			rect.setAttributeNS(null, 'fill', 'red');
			rect.setAttributeNS(null, 'fill-opacity', "0.0" );
			rect.setAttributeNS(null, 'onclick', 'measureBoxClicked(this.id)' );
			musicSvg[i].appendChild(rect);

			border.setAttributeNS(null, 'id', "border" + idCount);
			border.setAttributeNS(null, 'name',  idCount);
			border.setAttributeNS(null, 'class', 'measureBoxBorder');
			border.setAttributeNS(null, 'height', height + 4);
			border.setAttributeNS(null, 'width', width + 4);
			border.setAttributeNS(null, 'x', x -2);
			border.setAttributeNS(null, 'y', y -2);
			border.setAttributeNS(null, 'rx', 20);
			border.setAttributeNS(null, 'ry', 20);
			border.setAttributeNS(null, 'stroke', '#2962FF');
			border.setAttributeNS(null, 'fill', "red" );
			border.setAttributeNS(null, 'fill-opacity', "0" );
			border.setAttributeNS(null, 'stroke-opacity', '0.0');
			border.setAttributeNS(null, 'onclick', 'measureBoxClicked(this.attributes["name"].value)' );
			musicSvg[i].appendChild(border);


			roundedClipPath.setAttributeNS(null, 'id', "rounded" + idCount);
			rounded.setAttributeNS(null, 'height', height );
			rounded.setAttributeNS(null, 'width', width );
			rounded.setAttributeNS(null, 'x', x );
			rounded.setAttributeNS(null, 'y', y );
			rounded.setAttributeNS(null, 'rx', 15);
			rounded.setAttributeNS(null, 'ry', 15);
			roundedClipPath.appendChild(rounded);
			defs.appendChild(roundedClipPath);

			rightRoundedClipPath.setAttributeNS(null, 'id', "rightRounded" + idCount);
			rightRounded.setAttributeNS(null, 'height', height);
			rightRounded.setAttributeNS(null, 'width', width + 20);
			rightRounded.setAttributeNS(null, 'x', x - 20);
			rightRounded.setAttributeNS(null, 'y', y );
			rightRounded.setAttributeNS(null, 'rx', 15);
			rightRounded.setAttributeNS(null, 'ry', 15);
			rightRoundedClipPath.appendChild(rightRounded);
			defs.appendChild(rightRoundedClipPath);

			leftRoundedClipPath.setAttributeNS(null, 'id', "leftRounded" + idCount);
			leftRounded.setAttributeNS(null, 'height', height);
			leftRounded.setAttributeNS(null, 'width', width + 20);
			leftRounded.setAttributeNS(null, 'x', x);
			leftRounded.setAttributeNS(null, 'y', y);
			leftRounded.setAttributeNS(null, 'rx', 15);
			leftRounded.setAttributeNS(null, 'ry', 15);
			leftRoundedClipPath.appendChild(leftRounded);
			defs.appendChild(leftRoundedClipPath);

			idCount++;
		}
		musicSvg[i].appendChild(defs);

	}

// console.log("append rectangles");
// console.log(measureLines);
	parseQueryString();

}


function measureBoxClicked(boxId){
	// console.log("boxId " +boxId);
	setMeasures(boxId);

};

var lastMeasureChanged;



function setMeasures(boxId){
	var start_measure = parseInt(document.getElementById("startMeasure").value);
	var end_measure = parseInt(document.getElementById("endMeasure").value);


	if(boxId == start_measure){
		console.log("removing start measure" + boxId +"," + start_measure +"," + end_measure);
		if(end_measure != -1){
			document.getElementById("startMeasure").value = end_measure;
			document.getElementById("endMeasure").value = -1;
		}else{
			document.getElementById("startMeasure").value = -1;
		}
	}else if(boxId == end_measure){
		console.log("removing end measure" + boxId +"," + start_measure +"," + end_measure);
		document.getElementById("endMeasure").value = -1;
	}else if(start_measure == -1){
		console.log("setting start measure" + boxId +"," + start_measure);
		document.getElementById("startMeasure").value = boxId;
	}else if(end_measure == -1){
		if(boxId > start_measure){
			console.log("setting end measure" + boxId+ "," + end_measure);
			document.getElementById("endMeasure").value = boxId;
		}else{
			console.log("flipping measures" + boxId +"," + start_measure +"," + end_measure);
			document.getElementById("startMeasure").value = boxId;
			document.getElementById("endMeasure").value = start_measure;
		}
	}else if(parseInt(boxId) < parseInt(start_measure)){
		console.log("modifying start measure" + boxId +"," + start_measure);
		document.getElementById("startMeasure").value = boxId;
	}else if(parseInt(boxId) > parseInt(end_measure)){
		console.log("modifying end measure" + boxId +"," + end_measure);
		document.getElementById("endMeasure").value = boxId;
	}else{
		console.log("modifying last changed measure" + boxId +"," + lastMeasureChanged);
		if(lastMeasureChanged == start_measure){
			document.getElementById("startMeasure").value = boxId;
		}else{
			document.getElementById("endMeasure").value = boxId;

		}
	}

	if((parseInt(document.getElementById("startMeasure").value) >  parseInt(document.getElementById("startMeasure").value)) && (document.getElementById("startMeasure").value !=-1) && (document.getElementById("endMeasure").value != -1)){
		console.log("fixing fucked up measures");
		var tmp = document.getElementById("startMeasure").value;
		document.getElementById("startMeasure").value = document.getElementById("endMeasure").value;
		document.getElementById("endMeasure").value = tmp;
	}

	lastMeasureChanged = boxId;


	setBoxColors(document.getElementById("startMeasure").value,document.getElementById("endMeasure").value);
}

function setBoxColors(start_measure, end_measure){
	console.log("setBoxColors" + start_measure + ","+ end_measure);


	measures = document.getElementsByClassName('measureBox');

	for(var i = 0; i < measures.length; i++){
		document.getElementById(i).style.clipPath = null;
		if(start_measure == i && start_measure != -1){
			document.getElementById(i).style.fill = "#2962FF";
			document.getElementById(i).style.fillOpacity = "0.4";
			if(end_measure == -1){
				document.getElementById(i).style.clipPath = "url(#rounded" + i +")";
			}else{
				document.getElementById(i).style.clipPath = "url(#leftRounded" + i +")";
			}



		}else if(end_measure == i && end_measure != -1){
			document.getElementById(i).style.fill = "#2962FF";
			document.getElementById(i).style.fillOpacity = "0.4";
			document.getElementById(i).style.clipPath = "url(#rightRounded" + i +")";
		}else if(i > start_measure && i < end_measure && end_measure != -1 && start_measure != -1){
			document.getElementById(i).style.fill = "#82B1FF";
			document.getElementById(i).style.fillOpacity = "0.4";
			document.getElementById(i).style.clipPath = null;
		}else{
			document.getElementById(i).style.fillOpacity = "0.0";
		}
	}


	// if(start_measure != -1 && end_measure != -1)
	// 	showSubmitPanel();
	// else{
	// 	hideSubmitPanel();
	// }


}

function showSubmitPanel(){
	document.getElementById('submitPanel').style.display='block'
}

// function hideSubmitPanel(){
// 	document.getElementById('submitPanel').style.display='none'
// }


$(document).ready(function(){
	insertMeasureBoxes();
});
