import {useEffect, useState} from 'react';
import axios from 'axios'; 

function TeamManagementService(userDepartment) {
	
	const URL_PATH = "/api/teammanagement";
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

export default TeamManagementService;