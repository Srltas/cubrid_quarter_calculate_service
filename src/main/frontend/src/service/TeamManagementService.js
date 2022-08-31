import {useEffect, useState} from 'react';
import axios from 'axios'; 

function TeamManagementService() {
	
	const URL_PATH = "/api/teammanagement";
	const [employees, setEmployees] = useState([]);
	
	useEffect(() => {
		axios.get(URL_PATH)
        .then(response => {
			setEmployees(response.data)
		})
        .catch(error => console.log(error))
    }, []);
    
    //console.log("employees!!!! : " + JSON.stringify(employees));
    
    return employees;
}

export default TeamManagementService;