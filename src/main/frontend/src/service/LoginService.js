import {useEffect, useState} from 'react';
import axios from 'axios'; 

function LoginService(log_id) {

	const URL_PATH = "/api/login";
	const [logins, setLogins] = useState([]);

	useEffect(() => {
		if(log_id !== null && log_id.length >= 3){
			axios.post(URL_PATH, {
				name : log_id
			})
	        .then(response => {
				setLogins(response.data)
			})
	        .catch(error => console.log(error))	
		}
    }, [log_id]);
    
    //console.log("logins!!!! : " + JSON.stringify(logins));
    //console.log("log_id!!!! " + log_id);
    //var first_key = Object.keys(logins)[0];
	//var first_value = logins[0];
	//console.log("first_key : " + first_key);
	//console.log("first_value : " + JSON.stringify(first_value));
	//for(first_key in logins)  {
	//  console.log(first_key + " : "  + logins[first_key]);
	//}
    
    return logins;
    
  
}

export default LoginService;