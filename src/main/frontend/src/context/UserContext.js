import React from "react";
// LoginService
//import LoginService from "../service/LoginService";

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
    setDB_logid: localStorage.getItem("DB_logid"),
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

function loginUser(dispatch, loginid, password, history, setIsLoading, setError, first_name, first_year, years, match) {
  setError(false);
  setIsLoading(true);
  
  if (first_name === loginid) {
	console.log("===========================================");
  	console.log("loginid! : " + loginid);
  	console.log("password! : " + password);
  	console.log("history! : " + JSON.stringify(history));
  	console.log("DB_logid! : " + first_name);
  	console.log("match! : " + JSON.stringify(match));
  	console.log("===========================================");
  }
	
  if (!!loginid && first_name === loginid && !!password) {
    setTimeout(() => {
      localStorage.setItem("id_token", "1");
      localStorage.setItem("DB_logid", first_name);
      dispatch({ type: "LOGIN_SUCCESS" });
      setError(null);
      setIsLoading(false);
      match.params.first_year ='';
      history.push({pathname:"/app/dashboard", search: first_year, DB_logid: first_name, years: years});
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
  dispatch({ type: "SIGN_OUT_SUCCESS" });
  history.push("/login");
}
