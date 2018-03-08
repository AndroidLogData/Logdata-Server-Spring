import React from 'react';
import LogData from './logdata';

const PackageFilter = ({match}) => {
    let packageFilterUrl = '/logdata/filter/packageName/query?packagename=' + match.params.packagename;

    return (
        <div>
            <LogData url={packageFilterUrl}/>
        </div>
    );
};

export default PackageFilter;