var multer = require('multer');

module.exports = {uploadFile: uploadFile, afterUpload: afterUpload};

function uploadFile(){
    upload.single('upload_file', '_id');
}

function afterUpload(req, res, next) {
    console.log('Upload Successful');
}

var upload = multer({
    storage: multer.diskStorage({
        destination: './images',
        filename: function (req, file, cb) {
            var dest = './images';

            //query string params
            var _chatMessageID = req.query.chatMessageID;

            var _ext = file.originalname.substring(file.originalname.indexOf("."));
            var _fileName = _chatMessageID + _ext;

            cb(null, _fileName);
        }
    })
});