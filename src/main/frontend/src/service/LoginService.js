import {useEffect, useState} from 'react';
import axios from 'axios'; 

function LoginService() {

	const URL_PATH = "/api/login";
	const [logins, setLogins] = useState([]);

	useEffect(() => {
        axios.get(URL_PATH)
        .then(response => {
			setLogins(response.data)
		})
        .catch(error => console.log(error))
    }, []);
    
    return logins;
    
  
}

export default LoginService;