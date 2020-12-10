import React, { Component } from 'react';
import Webcam from 'react-webcam';
import { Button } from 'antd';
import './CameraModuleStyle.css';

export default class CameraModule extends Component {

    constructor(props) {
        super(props)
    }
    //set up ref for the webcam, so we can take screenshot
    setRef = webcam => {
        this.webcam = webcam;
    };

      
    //Azure face api does not support 64 based econded image.
    toBlob(imageSrc){
        var BASE64_MARKER = ';base64,';
            if (imageSrc.indexOf(BASE64_MARKER) == -1) {
                var parts = imageSrc.split(',');
                var contentType = parts[0].split(':')[1];
                var raw = decodeURIComponent(parts[1]);
                return new Blob([raw], { type: contentType });
            }
            var parts = imageSrc.split(BASE64_MARKER);
            var contentType = parts[0].split(':')[1];
            var raw = window.atob(parts[1]);
            var rawLength = raw.length;

            var uInt8Array = new Uint8Array(rawLength);

            for (var i = 0; i < rawLength; ++i) {
                uInt8Array[i] = raw.charCodeAt(i);
            }

            return new Blob([uInt8Array], { type: contentType });
    }

    //add verify face function(return faceId and face attributes)
    verifyFace(e){
        let imageSrc = this.webcam.getScreenshot();
        console.log(this.webcam);
        let imageBlob = this.toBlob(imageSrc);
        fetch(process.env.REACT_APP_API_ENDPOINT_DETECT_EMOTION, {
            method: 'post',
            headers: {
                "Content-Type":"application/octet-stream",
                "Ocp-Apim-Subscription-Key": process.env.REACT_APP_API_KEY
            },
            body: imageBlob
        }).then(response => response.json())
          .then(json => {
              this.props.verification(json)
          })
          .catch(err => console.log('Detect_Face Request Fail', err));
    }

    //return 400 if there is no face in the image(return faceId)
    addFace(e){
        let imageSrc = this.webcam.getScreenshot();
        let imageBlob = this.toBlob(imageSrc);
        let url = process.env.REACT_APP_API_ENDPOINT_ADD_FACE + this.props.personId + '/persistedFaces?detectionModel=detection_01';
        fetch(url , {
            method: 'post',
            headers: {
                "Content-Type":"application/octet-stream",
                "Ocp-Apim-Subscription-Key": process.env.REACT_APP_API_KEY
            },
           body: imageBlob
        }).then(response => this.props.enrollment(response))
          .catch(err => console.log('Add_Face Request Fail', err));
    }

    render() {
        let videoConstraints = {
            width: 1280,
            height: 720,
            facingMode: "user"
          };
        return (
            <div className = "cameraWindow">
                 <Webcam
                    audio = {false}
                    screenshotFormat = "image/jpeg"
                    videoConstraints = {videoConstraints}
                    ref={this.setRef}
                    style={{
                        height: "600px",
                        width: "500px",
                        position: 'absolute',
                        top: '30%',
                        left: '50%',
                        transform: 'translate(-50%, -30%)',
                     }}
                />
                <Button danger className = "cameraBtn" onClick = {(e) => {this.verifyFace(e)}}>This is my face 👍</Button>
            </div>
        )
    }
}
