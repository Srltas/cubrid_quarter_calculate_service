import {useEffect, useState} from 'react';
import axios from 'axios'; 

function AdminDashboardService(test) {
	
	console.log("AdminDashboardService!!!! " + test);
	
	const URL_PATH = "/api/adminDashboard";
	const [employees, setEmployees] = useState([]);
	
	useEffect(() => {
		axios.get(URL_PATH, {
			params:{
				test : test,
			}
		})
        .then(response => {
			setEmployees(response.data)
		})
        .catch(error => console.log(error))
    }, [test]);
    
    //console.log("employees!!!! : " + JSON.stringify(employees));
    
    return employees;
    
  
}

export default AdminDashboardService;