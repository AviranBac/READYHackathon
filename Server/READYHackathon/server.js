var express = require('express');
var mysql = require('mysql');
var fs = require("fs");
var cors = require('cors');
var bodyParser = require('body-parser');
var multer = require('multer');
var imageUpload = require('./handler');
//var upload = multer({ dest: 'uploads/' })
var app = express();

const io = require('socket.io')(server);

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static('static'));
app.use(express.static(__dirname + '/public'));

io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('disconnect', () => {
        console.log('user disconnected');
    });
});

// const storage = multer.diskStorage({
//     destination: function (req, file, cb) {
//         cb(null, 'images/')
//     },
//     filename: function (req, file, cb) {
//         cb(null, file.originalname)
//     }
// });

// var uploading =  multer({storage: storage}).single('file');
// app.post(uploading, function (req, res, next) {
//     uploading(req, res, function (err) {
//         if (err) {
//            console.log(err);
//         }
//         res.send('Successfully uploaded ' + req.files.length + ' files!');
//     });
// });

//app.post('/uploadImage', imageUpload.uploadFile, imageUpload.afterUpload);

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
                result[currResult]["IMAGE_URL"] = handleUrl('airplane1.jpg');
            }
            res.json(result);
        });
        con.end();
    });
});

app.post('/detections', function(req, res) {
    console.log(req);
    console.log(req.body);
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
                  "VALUES ('" + req.body.detectionTime + "', " + req.body.planeHeight + ", " + req.body.planeDistance + ", " + req.body.isOurs + ", " + req.body.isShutdown + ", '" + req.body.imageUrl + "')";
        con.query(sql, function (err, result) {
            if (err) {
                throw err;
            }
            console.log("1 record inserted");
            con.end();
        });
    });
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