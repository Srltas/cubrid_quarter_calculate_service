import {useEffect, useState} from 'react';
import axios from 'axios'; 

function AdminDashboardService(userDepartment) {
	
	//console.log("AdminDashboardService!!!! " + userDepartment);
	
	const URL_PATH = "/api/admindashboard";
	const [employees, setEmployees] = useState([]);
	
	useEffect(() => {
		axios.get(URL_PATH, {
			params:{
				department : userDepartment,
			}
		})
        .then(response => {
			setEmployees(response.data)
		})
        .catch(error => console.log(error))
    }, [userDepartment]);
    
    //console.log("employees!!!! : " + JSON.stringify(employees));
    
    return employees;
    
  
}

export default AdminDashboardService;