var express = require('express');
var mysql = require('mysql');
var fs = require("fs");
var cors = require('cors');
var bodyParser = require('body-parser');
var app = express();

const io = require('socket.io')(server);

app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static('static'));
app.use(express.static(__dirname + '/public'));

io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('disconnect', () => {
        console.log('user disconnected');
    });
});

app.get('/detections', function (req, res) {
    const con = mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "root",
        database: "ready"
    });
    con.connect(function(err) {
        if (err) {
            throw err;
        }
        con.query("SELECT * FROM ready.t_detections ORDER BY ID DESC", function (err, result, fields) {
            if (err) {
                throw err; 
            }
            for (let currResult = 0; currResult < result.length; currResult++) {
                result[currResult]["DETECTION_TIME"] = prettyDate(result[currResult]["DETECTION_TIME"]);
                result[currResult]["IS_OURS"] = handleBoolean(result[currResult]["IS_OURS"]);
                result[currResult]["IS_SHUTDOWN"] = handleBoolean(result[currResult]["IS_SHUTDOWN"]);
                result[currResult]["IMAGE_URL"] = handleUrl(result[currResult]["IMAGE_URL"]);
            }
            res.json(result);
        });
        con.end();
    });
});

app.post('/detections', function(req, res) {
    console.log("Detection time: " + req.body.detectionTime);
    console.log("Height: " + req.body.planeHeight);
    console.log("Distance: " + req.body.planeDistance);
    console.log("Ours: " + req.body.isOurs);
    console.log("Shutdown: " + req.body.isShutdown);
    const con = mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "root",
        database: "ready"
    });
    con.connect(function(err) {
        if (err) {
            throw err;
        }
        var sql = "INSERT INTO ready.t_detections (DETECTION_TIME, HEIGHT, DISTANCE, IS_OURS, IS_SHUTDOWN, IMAGE_URL) " +
                  "VALUES ('" + new Date() + "', " + req.body.planeHeight + ", " + req.body.planeDistance + ", " + req.body.isOurs + ", " + req.body.isShutdown + ", 'blah.png')";
        //var sql = "INSERT INTO ready.t_detections (DETECTION_TIME, HEIGHT, DISTANCE, IS_OURS, IS_SHUTDOWN, IMAGE_URL) " +
        //          "VALUES (" + new Date() + ", " + req.body.planeHeight + ", " + req.body.planeDistance + ", " + req.body.isOurs + ", " + req.body.isShutdown + ", 'blah.png')";
        console.log(sql);
        con.query(sql, function (err, result) {
            if (err) {
                throw err;
            }
            console.log("1 record inserted");
        });
    });
    con.end();
    res.send(req.body);
});

app.get('/images/:fileName', function (req, res) {
    res.sendFile(__dirname + '/images/' + req.params.fileName);
});

const handleBoolean = (value) => {
    let booleanInfo;
    if (value == 1) {
        booleanInfo = 'כן';
    } else if (value == 0) {
        booleanInfo = 'לא';
    } else {
        booleanInfo = '';
    }
    return (booleanInfo);
}

const prettyDate = (date) => {
    console.log("Entered pretty date");
    const dateObject = new Date(date);
    let hours = String(dateObject.getHours());
    let minutes = String(dateObject.getMinutes());
    let seconds = String(dateObject.getSeconds());
    if (hours.length == 1) {
        hours = "0" + hours;
    }
    if (minutes.length == 1) {
        minutes = "0" + minutes;
    }
    if (seconds.length == 1) {
        seconds = "0" + seconds;
    }
    return (dateObject.getDate() + "/" + (dateObject.getMonth() + 1) + "/" + dateObject.getFullYear() +
            " " + hours + ":" + minutes + ":" + seconds); 
    return date;
}

const handleUrl = (fileName) => {
    return 'http://localhost:3000/images/' + fileName;
}

app.get("/url", (req, res, next) => {
    res.json(["Tony","Lisa","Michael","Ginger","Food"]);
});

var server = app.listen(3000, () => {
   console.log("Server listening on port 3000.")
});