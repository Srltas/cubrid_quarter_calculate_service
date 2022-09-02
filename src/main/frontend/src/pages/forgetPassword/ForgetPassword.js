import React, { useState } from "react";
import {
  Grid,
  Typography,
  Button,
  Tabs,
  Tab,
  TextField,
  Fade,
} from "@material-ui/core";
import { withRouter } from "react-router-dom";
import axios from 'axios'; 

// styles
import useStyles from "./styles";

// logo
import logo from "./cubrid_logo.png";

// context
import { useUserDispatch, useUserState, signOut } from "../../context/UserContext";

function ForgetPassword(props) {
  var classes = useStyles();

  // global
  var userDispatch = useUserDispatch();
  var userInfo = useUserState();
  
  // local
  var error = null;
  var [activeTabId, setActiveTabId] = useState(0);
  var [newPassword, setNewPassword] = useState("");
  var [newConfirmNewPassword, setNewConfirmNewPassword] = useState("");
  
  //console.log("ForgetPassword_props : " +  JSON.stringify(props));
  /** localStorage 값 저장하는 곳 */
  var userId = "";
  var userName = "";
  
  if(props.location.userId === undefined || props.location.userId === null || props.location.userId === ""){
	userId = userInfo.userId;
	console.log("Dashboard_userId_r : " +  userInfo.userId);
  } else {
	userId = props.location.userId;
	userInfo.userId = userId;
	console.log("Dashboard_userId_f : " +  props.location.userId);
  }
  
  if(props.location.userName === undefined || props.location.userName === null || props.location.userName === ""){
	userName = userInfo.userName;
	console.log("Dashboard_userName_r : " +  userInfo.userName);
  } else {
	userName = props.location.userName;
	userInfo.userName = userName;
	console.log("Dashboard_userName_f : " +  props.location.userName);
  }
  
  /**패스워드 변경*/
  const URL_PATH = "/api/forgetpassword";
  const pwChangeSubmit = () =>{
	axios.post(URL_PATH, {
		id: userId,
		passwd: newPassword,
	}).then(()=>{
		alert('패스워드 변경 완료!');
		/**로그인 화면으로 이동*/
		signOut(userDispatch, props.history, userInfo);
	})
  };
  
  /**패스워드 확인*/
  const hasNotSameError = passwordEntered =>
        newPassword !== newConfirmNewPassword ? true : false;
  
  return (
    <Grid container className={classes.container}>
      <div className={classes.logotypeContainer}>
        <img src={logo} alt="logo" className={classes.logotypeImage} />
        <Typography className={classes.logotypeText}>CUBRID</Typography>
      </div>
      <div className={classes.formContainer}>
        <div className={classes.form}>
          <Tabs
            value={activeTabId}
            onChange={(e, id) => setActiveTabId(id)}
            indicatorColor="primary"
            textColor="primary"
            centered
          >
            <Tab label="패스워드 변경" classes={{ root: classes.tab }} />
          </Tabs>
          {activeTabId === 0 && (
            <React.Fragment>
              <Typography variant="h2" className={classes.subGreeting}>
                CUBRID 분기정산
              </Typography>
              <Fade in={error}>
                <Typography color="secondary" className={classes.errorMessage}>
                  2 :(
                </Typography>
              </Fade>
              <TextField
                id="id"
                InputProps={{
                  classes: {
                    underline: classes.textFieldUnderline,
                    input: classes.textField,
                  },
                }}
                value={"ID : " + userId}
                margin="normal"
                fullWidth
              />
              <TextField
                id="name"
                InputProps={{
                  classes: {
                    underline: classes.textFieldUnderline,
                    input: classes.textField,
                  },
                }}
                value={"이름 : " + userName}
                margin="normal"
                fullWidth
              />
              <TextField
                id="password"
                InputProps={{
                  classes: {
                    underline: classes.textFieldUnderline,
                    input: classes.textField,
                  },
                }}
                value={newPassword}
                onChange={e => setNewPassword(e.target.value)}
                margin="normal"
                placeholder="새 패스워드"
                type="password"
                fullWidth
              />
              <TextField
                id="passwordckeck"
                InputProps={{
                  classes: {
                    underline: classes.textFieldUnderline,
                    input: classes.textField,
                  },
                }}
                value={newConfirmNewPassword}
                onChange={e => setNewConfirmNewPassword(e.target.value)}
                margin="normal"
                placeholder="새 패스워드 확인"
                type="password"
                fullWidth
                className={classes.createAccountButton}
                error={hasNotSameError(newPassword)} // 해당 텍스트필드에 error 핸들러 추가
                helperText={
                    hasNotSameError(newPassword) ? "입력한 비밀번호와 일치하지 않습니다." : null
                }
                autoComplete="current-password"
              />
              <div className={classes.creatingButtonContainer}>
	              <Button
	                onClick={pwChangeSubmit}
	                disabled={
	                  newPassword.length === 0 ||
	                  newConfirmNewPassword.length === 0 ||
	                  newPassword !== newConfirmNewPassword
	                }
	                size="large"
	                variant="contained"
	                color="primary"
	                fullWidth
	              >
	                패스워드 변경
	              </Button>
              </div>
            </React.Fragment>
          )}
        </div>
        <Typography color="primary" className={classes.copyright}>
          © 2022 CUBRID,jmw,dmk
        </Typography>
      </div>
    </Grid>
  );
}

export default withRouter(ForgetPassword);
