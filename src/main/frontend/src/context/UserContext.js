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
    userId: localStorage.getItem("userId"),
    userDepartment: localStorage.getItem("userDepartment"),
    userName: localStorage.getItem("userName"),
    userRole: localStorage.getItem("userRole"),
    select_year : localStorage.getItem("select_year"),
    last_quarter : localStorage.getItem("last_quarter"),
    years : localStorage.getItem("years"),
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
  var loginDBData = await axios.post(URL_PATH, {id : loginid, passwd: password});
  //console.log("loginDBData1 : " + JSON.stringify(loginDBData));
  
  //console.log("password! : " + loginDBData.data[0].passwd === password);
  
  /**DB 값이 있는지 체크 */
  if(loginDBData.data[0] === undefined){
	alert('아이디 또는 패스워드가 틀렸습니다.');
    dispatch({ type: "LOGIN_FAILURE" });
    setError(true);
    setIsLoading(false);
    history.push("/login");
  } else {
  
  //console.log("loginDBData2 : " + JSON.stringify(loginDBData.data));
  
  /**DB 값 가져와서 셋팅 */
  const userId = loginDBData.data[0].id;
  const userPasswd = loginDBData.data[0].passwd;
  const userDepartment = loginDBData.data[0].department;
  const userName = loginDBData.data[0].name;
  const userRole = loginDBData.data[0].role;
  const userEmploymentstatus = loginDBData.data[0].employmentstatus;
  const select_year = loginDBData.data[0].year;
  const last_quarter = loginDBData.data[0].quarter;
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
  
  if (userId === loginid) {
	console.log("===========================================");
  	console.log("userId! : " + userId);
  	console.log("password! : " + userPasswd === "0000");
  	console.log("userDepartment! : " + userDepartment);
  	console.log("userName! : " + userName);
  	console.log("userRole! : " + userRole);
  	console.log("userEmploymentstatus! : " + userEmploymentstatus);
  	console.log("===========================================");
  }
  
  /**로그인 성공&실패 비교 */
  /**현재는 무조건, id/pw가 있고 && id가 db 값이랑 같으면 로그인 */
  if (!!loginid && userId === loginid && !!password) {
    setTimeout(() => {
      localStorage.setItem("id_token", "1");
      localStorage.setItem("userId", userId);
      localStorage.setItem("userDepartment", userDepartment);
      localStorage.setItem("userName", userName);
      localStorage.setItem("userRole", userRole);
      localStorage.setItem("select_year", select_year);
      localStorage.setItem("last_quarter", last_quarter);
      localStorage.setItem("years", years);
      
      dispatch({ type: "LOGIN_SUCCESS" });
      setError(null);
      setIsLoading(false);
      
      history.push({
		pathname:"/app/dashboard",
		userId: userId,
		userDepartment: userDepartment,
		userName: userName,
		userRole: userRole,
		select_year: select_year,  
		last_quarter: last_quarter,
		years: years
	  });
	  
    }, 2000);
  } else {
	alert('아이디 또는 패스워드가 틀렸습니다.');
    dispatch({ type: "LOGIN_FAILURE" });
    setError(true);
    setIsLoading(false);
    history.push("/login");
  }
}
}

function signOut(dispatch, history, userInfo) {
  /**로그아웃 후 localStorage 초기화-1 */
  localStorage.removeItem("id_token");
  localStorage.removeItem("userId");
  localStorage.removeItem("userDepartment");
  localStorage.removeItem("userName");
  localStorage.removeItem("userRole");
  localStorage.removeItem("select_year");
  localStorage.removeItem("last_quarter");
  localStorage.removeItem("years");
  localStorage.clear();
  
  /**로그아웃 후 localStorage 초기화-2 */
  userInfo.userId = "";
  userInfo.userDepartment = "";
  userInfo.userName = "";
  userInfo.userRole = "";
  userInfo.select_year = "";
  userInfo.last_quarter = "";
  userInfo.years = "";
  
  //console.log("signOut_userInfo : " + JSON.stringify(userInfo));
  dispatch({ type: "SIGN_OUT_SUCCESS" });
  history.push("/login");
}
