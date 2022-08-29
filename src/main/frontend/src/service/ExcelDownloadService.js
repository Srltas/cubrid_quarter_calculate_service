import {useEffect, useState} from 'react';
import axios from 'axios'; 

function ExcelDownloadService(year, quarter) {

	const URL_PATH = "/api/exceldownload";
	const [exceldownloads, setExceldownload] = useState([]);

	useEffect(() => {
		axios.get(URL_PATH, {
			params:{
				year: year,
				quarter: quarter,
			}
		})
        .then(response => {
			setExceldownload(response.data)
		})
        .catch(error => console.log(error))	
    }, [year, quarter]);
    
    //console.log("exceldownloads!!!! : " + JSON.stringify(exceldownloads));
    
    return exceldownloads;
    
  
}

export default ExcelDownloadService;