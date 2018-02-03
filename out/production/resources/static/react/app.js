import React from 'react';

// var LogDataList = App.Components.LogDataList;

// var LogDataBox = React.createClass({
//     getInitialState: function () {
//         return {
//             logData: []
//         };
//     },
//     fetchLogData: function () {
//         $.ajax({
//             url: this.props.url,
//             dataType: 'json',
//             cache: false,
//             success: function (data) {
//                 this.setState({logData: data.logData});
//             }.bind(this),
//             error: function (xhr, status, err) {
//                 console.error(this.props.url, status, err.toString());
//             }.bind(this)
//         });
//     },
//     componentDidMount: function () {
//         this.fetchLogData();
//     },
//     shouldComponentUpdate: function () {
//         this.fetchLogData();
//         return true;
//     },
//     componentWillReceiveProps: function () {
//         this.fetchLogData();
//     },
//     render() {
//         return (
//             <LogDataList logData={this.state.logData}/>
//         );
//     }
// });

class LogDataListRow extends React.Component {
    render() {
        if (this.props.logData.level === 'v') {
            return (
                <div className="panel panel-success">
                    <div className="panel-heading">Verb</div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'w') {
            return (
                <div className="panel panel-warning">
                    <div className="panel-heading">Warning</div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'i') {
            return (
                <div className="panel panel-info">
                    <div className="panel-heading">Info</div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'd') {
            return (
                <div className="panel panel-primary">
                    <div className="panel-heading">Debug</div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        } else if (this.props.logData.level === 'e') {
            return (
                <div className="panel panel-danger">
                    <div className="panel-heading">Error</div>
                    <div className="panel-body">
                        <p>Time : {this.props.logData.stringTime}</p>
                        <p>Tag : {this.props.logData.tag}</p>
                        <p>Message : {this.props.logData.message}</p>
                        <p>Level : {this.props.logData.level}</p>
                    </div>
                </div>
            );
        }
    }
}

class LogDataList extends React.Component {
    render() {
        var i;
        var logDataNodes = [];

        for (i = 0; i < this.props.logData.length; i++) {
            logDataNodes.push(
                <LogDataListRow logData={this.props.logData[i]} />
            );
        }

        if (logDataNodes.length === 0) {
            return (
                <div className="logDataList">
                    <h1>데이터가 없습니다!</h1>
                </div>
            );
        } else {
            return (
                <div className="logDataList">
                    {logDataNodes}
                </div>
            );
        }
    }
}

class LogDataBox extends React.Component {
    fetchLogData() {
        $.ajax({
            url: this.props.url,
            dataType: 'json',
            cache: false,
            success: function (data) {
                this.setState({logData: data.logData});
            }.bind(this),
            error: function (xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    }

    componentDidMount() {
        this.fetchLogData();
    }

    shouldComponentUpdate() {
        this.fetchLogData();
        return true;
    }

    componentWillReceiveProps() {
        this.fetchLogData();
    }

    render() {
        return (
            <div>
                <LogDataList/>
            </div>
        );
    }
}

class App extends React.Component {
    render() {
        return (
            <div>
                <LogDataBox url="/logdatalist"/>
            </div>
        );
    }
}

export default App;

// ReactDOM.render(<LogDataBox url="/logdatalist"/>, document.getElementById('root'));

// ReactDOM.render(<Router history={browserHistory}>
//     <LogDataBox path='/logdata' component={LogDataBox} url="/logdatalist"/>
// </Router>, document.getElementById('root'));