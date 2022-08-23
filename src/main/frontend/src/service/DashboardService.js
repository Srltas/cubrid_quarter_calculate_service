import {useEffect, useState} from 'react';
import axios from 'axios'; 

function DashboardService(log_id, year) {

	const URL_PATH = "/api/main_dashboard";
	const [dashboards, setDashboards] = useState([]);

	useEffect(() => {
		if(log_id !== null && log_id.length >= 3){
			axios.get(URL_PATH, {
				params:{
					name : log_id,
					year : year
				}
			})
	        .then(response => {
				setDashboards(response.data)
			})
	        .catch(error => console.log(error))	
		}
    }, [log_id, year]);
    
    //console.log("dashboards!!!! : " + JSON.stringify(dashboards));
    
    return dashboards;
    
  
}

export default DashboardService;