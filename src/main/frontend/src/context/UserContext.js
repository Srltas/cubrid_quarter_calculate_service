import React from "react";
import axios from 'axios'; 

var UserStateContext = React.createContext();
var UserDispatchContext = React.createContext();

function userReducer(state, action) {
	
  console.log("=====userReducer=====");
  console.log("userReducer_state : " + JSON.stringify(state));
  
  switch (action.type) {
    case "LOGIN_SUCCESS":
      return { ...state, isAuthenticated: true };
    case "LOGIN_FAILURE":
      return { ...state, isAuthenticated: false };
    case "SIGN_OUT_SUCCESS":
      return { ...state, isAuthenticated: false };
    default: {
      throw new Error(`Unhandled action type: ${action.type}`);
    }
  }
}

function UserProvider({ children }) {
  var [state, dispatch] = React.useReducer(userReducer, {
    isAuthenticated: !!localStorage.getItem("id_token"),
    DB_logid: localStorage.getItem("DB_logid"),
    Select_year : localStorage.getItem("select_year"),
    Years : localStorage.getItem("years"),
  });
  
  console.log("=====UserProvider=====");
  console.log("userReducer_state : " + JSON.stringify(state));
  console.log("userReducer_action : " + JSON.stringify(dispatch));
  
  return (
    <UserStateContext.Provider value={state}>
      <UserDispatchContext.Provider value={dispatch}>
        {children}
      </UserDispatchContext.Provider>
    </UserStateContext.Provider>
  );
}

function useUserState() {
  var context = React.useContext(UserStateContext);
  
  console.log("=====useUserState=====");
  if (context === undefined) {
    throw new Error("useUserState must be used within a UserProvider");
  }
  return context;
}

function useUserDispatch() {
  var context = React.useContext(UserDispatchContext);
  
  console.log("=====useUserDispatch=====");
  if (context === undefined) {
    throw new Error("useUserDispatch must be used within a UserProvider");
  }
  return context;
}

export { UserProvider, useUserState, useUserDispatch, loginUser, signOut };

// ###########################################################

async function loginUser(dispatch, loginid, password, history, setIsLoading, setError) {
  setError(false);
  setIsLoading(true);
  
  /**로그인시 DB 값 가져오기 */
  const URL_PATH = "/api/login";
  var loginDBData = await axios.post(URL_PATH, {name : loginid});
  //console.log("dbLoginData@@ : " + JSON.stringify(loginDBData.data));
  
  const first_name = loginDBData.data[0].name;
  const select_year = loginDBData.data[0].year;  
  var years = "";
  for(let i in loginDBData.data){
	if (i === "0"){
		years = years + loginDBData.data[i].year;
	} else {
		years = years + "," + loginDBData.data[i].year;
	}
  }
  years = '[' + years + ']';
  years = JSON.parse(years);
  
  if (first_name === loginid) {
	console.log("===========================================");
  	console.log("loginid! : " + loginid);
  	console.log("password! : " + password);
  	console.log("history! : " + JSON.stringify(history));
  	console.log("DB_logid! : " + first_name);
  	console.log("===========================================");
  }
	
  if (!!loginid && first_name === loginid && !!password) {
    setTimeout(() => {
      localStorage.setItem("id_token", "1");
      localStorage.setItem("DB_logid", first_name);
      localStorage.setItem("select_year", select_year);
      localStorage.setItem("years", years);
      dispatch({ type: "LOGIN_SUCCESS" });
      setError(null);
      setIsLoading(false);
      history.push({pathname:"/app/dashboard", DB_logid: first_name, select_year: select_year, years: years});
      //history.push("/app/dashboard");
    }, 2000);
  } else {
    dispatch({ type: "LOGIN_FAILURE" });
    setError(true);
    setIsLoading(false);
    history.push("/login");
  }
}

function signOut(dispatch, history) {
  localStorage.removeItem("id_token");
  localStorage.removeItem("DB_logid");
  localStorage.removeItem("select_year");
  localStorage.removeItem("years");
  dispatch({ type: "SIGN_OUT_SUCCESS" });
  history.push("/login");
}
