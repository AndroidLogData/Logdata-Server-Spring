import React from 'react';
import LogData from './logdata';

const PackageFilter = ({match}) => {
    let packageFilterUrl = '/logdatapackagenamefilter/query?packagename=' + match.params.packagename;

    console.log(packageFilterUrl);

    return (
        <div>
            <LogData url={packageFilterUrl}/>
        </div>
    );
};

export default PackageFilter;