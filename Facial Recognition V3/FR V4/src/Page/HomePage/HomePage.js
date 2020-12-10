import React, { Component } from 'react';
import './HomePageStyle.css';
import { Button } from 'antd'
import { Link } from 'react-router-dom';

export default class HomePage extends Component {
    render() {
        return (
            <div className = "pageBody">
                <h1 className = 'titleTxt'> Heyyyy, who are you?</h1>
                <Link to = '/enroll'>
                    <Button danger className = "btn1">Introduce Myself 👋</Button>
                </Link>
                <Link to = '/verify'>
                    <Button danger className = "btn2">You already knew me 😅</Button>
                </Link>
            </div>
        )
    }
}

