import React from "react";
// LoginService
//import LoginService from "../service/LoginService";

var UserStateContext = React.createContext();
var UserDispatchContext = React.createContext();

function userReducer(state, action) {
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
  });

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
  if (context === undefined) {
    throw new Error("useUserState must be used within a UserProvider");
  }
  return context;
}

function useUserDispatch() {
  var context = React.useContext(UserDispatchContext);
  if (context === undefined) {
    throw new Error("useUserDispatch must be used within a UserProvider");
  }
  return context;
}

export { UserProvider, useUserState, useUserDispatch, loginUser, signOut };

// ###########################################################

function loginUser(dispatch, loginid, password, history, setIsLoading, setError, DB_logid) {
  setError(false);
  setIsLoading(true);
  
  if (DB_logid === loginid) {
  	console.log("dispatch! : " + dispatch);
  	console.log("loginid! : " + loginid);
  	console.log("password! : " + password);
  	console.log("history! : " + history);
  	console.log("setIsLoading! : " + setIsLoading);
  	console.log("setError! : " + setError);
  	console.log("DB_logid! : " + DB_logid);
  } else {
  	console.log("dispatch@ : " + dispatch);
  	console.log("loginid@ : " + loginid);
  	console.log("password@ : " + password);
  	console.log("history@ : " + history);
  	console.log("setIsLoading@ : " + setIsLoading);
  	console.log("setError@ : " + setError);
  	console.log("DB_logid@ : " + DB_logid);
  }
	
  if (!!loginid && DB_logid === loginid && !!password) {
    setTimeout(() => {
      localStorage.setItem("id_token", "1");
      dispatch({ type: "LOGIN_SUCCESS" });
      setError(null);
      setIsLoading(false);
      history.push("/app/dashboard");
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
  dispatch({ type: "SIGN_OUT_SUCCESS" });
  history.push("/login");
}
