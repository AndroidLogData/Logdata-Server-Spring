import React from 'react';
import {Link} from 'react-router-dom';

class MainPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            logData: []
        };

        this.fetchPackageName();
    }

    fetchPackageName() {
        $.ajax({
            url: '/main',
            dataType: "json",
            cache: false,
            success: function (data) {
                this.setState({logData: data.logData});
            }.bind(this),
            error: function (data) {
                console.log("error");
            }.bind(this)
        });
    }

    shouldComponentUpdate(nextProps, nextState) {
        this.fetchPackageName();
        return nextState.length !== this.state.logData.length;
    }

    render() {
        let i;
        let mainData = [];

        for (i = 0; i < this.state.logData.length; i++) {
            mainData.push(
                <div className="card">
                    <div className="card-block">
                        <h3 className="card-title">{this.state.logData[i].packageName}</h3>
                        <p className="card-text">로그 데이터 갯수 : {this.state.logData[i].logDataCount}</p>
                        <p className="card-text">최근 크래쉬 데이터
                            : {this.state.logData[i].recentCrashTime == null ? 'null' : this.state.logData[i].recentCrashTime}</p>
                        <Link to={{pathname: '/packagenamefilter/' + this.state.logData[i].packageName}}
                              className="btn btn-primary" role="button">Show</Link>
                    </div>
                </div>
            );
        }

        if (mainData.length === 0) {
            return null;
        } else {
            return (
                <div className="container-fluid">
                    <div className="row">
                        <main className="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                            <h1>Dashboard</h1>

                            <div className="jumbotron">
                                <h1>Android Log Data Viewer</h1>
                                <hr className="my-4"/>
                                <p>안드로이드 로그 데이터와 메모리 관리를 위한 페이지</p>
                            </div>
                            <div className="col-6 col-sm-3 placeholder">
                                <div className="row">
                                    {mainData}
                                </div>
                            </div>
                        </main>
                    </div>
                </div>
            );
        }
    }
}

export default MainPage;