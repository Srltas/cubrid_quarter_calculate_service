import {useEffect, useState} from 'react';
import axios from 'axios'; 

function DashboardService(userName, selectyear) {

	const URL_PATH = "/api/main_dashboard";
	const [dashboards, setDashboards] = useState([]);

	useEffect(() => {
		axios.get(URL_PATH, {
			params:{
				name : userName,
				year : selectyear
			}
		})
        .then(response => {
			setDashboards(response.data)
		})
        .catch(error => console.log(error))	
    }, [userName, selectyear]);
    
    //console.log("dashboards!!!! : " + JSON.stringify(dashboards));
    
    return dashboards;
    
  
}

export default DashboardService;