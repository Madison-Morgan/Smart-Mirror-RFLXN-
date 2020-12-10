import React, { Component } from 'react'
import CameraModule from '../../Components/CameraModule/CameraModule';

export default class VerifyPage extends Component {
    constructor(props){
        super(props);
        this.verify = this.verify.bind(this);
        this.state = {name:'', showResult: false, emotion: ''}
    }

    verify(res){
        let emotion = res[0].faceAttributes.emotion;
        this.getEmotion(emotion);
        let faceId = res[0].faceId;
        fetch(process.env.REACT_APP_API_ENDPOINT_IDENTIFY, {
            method: 'post',
            headers: {
                "Content-Type":"application/json",
                "Ocp-Apim-Subscription-Key": process.env.REACT_APP_API_KEY
            }, body:JSON.stringify({
                personGroupId: 'test',
                faceIds: [
                    faceId
                ],
                maxNumOfCandidatesReturned: 1,
                confidenceThreshold: 0.5
              })
        }).then(res => res.json())
          .then(json => this.getName(json))
          .catch(err => console.log('Identify_Request Fail', err));
    }

    getEmotion(emotion){
        console.log(emotion)
        let keys = Object.keys(emotion);
        let max = emotion[keys[0]];
        let result = '';
        for (let i = 0; i < keys.length; i++){
            let value = emotion[keys[i]];
            if (value > max) {
                max = value;
                result = keys[i];
            }
        }
        this.setState({emotion: result});
    }
    //add confidence check
    getName(res){
        let personId = res[0].personId;
        let url = process.env.REACT_APP_API_ENDPOINT_PERSON_NAME + personId;
        fetch(process.env.REACT_APP_API_ENDPOINT_PERSON_NAME, {
            method: 'get',
            headers: {
                "Ocp-Apim-Subscription-Key": process.env.REACT_APP_API_KEY
            }
        }).then(res => res.json())
          .then(json => this.showResult(json))
          .catch(err => console.log('Get_Name_Request Fail', err));
    }

    showResult(res){
        let name = res[0].name;
        this.setState({name: name, showResult: true})
    }

    render() {
        if(this.state.showResult){
            return(
                <div className = 'pageBody'>
                    <h1 className = 'greetTxt'> Hi {this.state.name}👋,  you look {this.state.emotion}</h1>
                    <h1 className = 'greetTxt'> Smile more and have a nice day!!! 😁</h1> 
                </div>
            )
        }else{
            return (
                <div className = 'pageBody'>
                    <h1 className = 'greetTxt'> Hi 👋,  I am trying to recognize you</h1>
                    <h1 className = 'greetTxt'> Please hold still</h1>
                    <CameraModule isEnroll = {false}  verification = {this.verify} />
                </div>
            )
        }
    }
}
