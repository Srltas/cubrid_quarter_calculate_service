import React, { useState } from "react";
import {
  AppBar,
  Toolbar,
  IconButton,
  Menu,
  MenuItem,
} from "@material-ui/core";
import {
  Menu as MenuIcon,
  Person as AccountIcon,
  ArrowBack as ArrowBackIcon,
} from "@material-ui/icons";
import classNames from "classnames";

// styles
import useStyles from "./styles";

// components
import { Typography } from "../Wrappers/Wrappers";

// context
import {
  useLayoutState,
  useLayoutDispatch,
  toggleSidebar,
} from "../../context/LayoutContext";
import { useUserDispatch, signOut, useUserState } from "../../context/UserContext";

export default function Header(props) {
  var classes = useStyles();

  // global
  var layoutState = useLayoutState();
  var layoutDispatch = useLayoutDispatch();
  var userDispatch = useUserDispatch();

  // local
  var [profileMenu, setProfileMenu] = useState(null);
  var userInfo = useUserState();
  
  /** 여기부터 개인적으로 추가 */ 
  console.log("Header_props : " +  JSON.stringify(userInfo));
  //console.log("Header_props_history : " +  JSON.stringify(props.history));
  //console.log("Header_props.history.location.DB_logid : " +  props.history.location.DB_logid);
  
  /** localStorage 값 저장하는 곳 */
  var userId = "";
  var userDepartment = "";
  var userName = "";
  var userRole = "";
  
  if(props.history.location.userId === undefined || props.history.location.userId === null || props.history.location.userId === ""){
	userId = userInfo.userId;
	console.log("Header_userId_r : " +  userInfo.userId);
  } else {
	userId = props.history.location.userId;
	userInfo.userId = userId;
	console.log("Header_userId_f : " +  props.history.location.userId);
  }
  
  if(props.history.location.userDepartment === undefined || props.history.location.userDepartment === null || props.history.location.userDepartment === ""){
	userDepartment = userInfo.userDepartment;
	console.log("Header_userDepartment_r : " +  userInfo.userDepartment);
  } else {
	userDepartment = props.history.location.userDepartment;
	userInfo.userDepartment = userDepartment;
	console.log("Header_userDepartment_f : " +  props.history.location.userDepartment);
  }
  
  if(props.history.location.userName === undefined || props.history.location.userName === null || props.history.location.userName === ""){
	userName = userInfo.userName;
	console.log("Header_userName_r : " +  userInfo.userName);
  } else {
	userName = props.history.location.userName;
	userInfo.userName = userName;
	console.log("Header_userName_f : " +  props.history.location.userName);
  }
  
  if(props.history.location.userRole === undefined || props.history.location.userRole === null || props.history.location.userRole === ""){
	userRole = userInfo.userRole;
	console.log("Header_userRole_r : " +  userInfo.userRole);
  } else {
	userRole = props.history.location.userRole;
	userInfo.userRole = userRole;
	console.log("Header_userRole_f : " +  props.history.location.userRole);
  }
  

  return (
    <AppBar position="fixed" className={classes.appBar}>
      <Toolbar className={classes.toolbar}>
        <IconButton
          color="inherit"
          onClick={() => toggleSidebar(layoutDispatch)}
          className={classNames(
            classes.headerMenuButton,
            classes.headerMenuButtonCollapse,
          )}
        >
          {layoutState.isSidebarOpened ? (
            <ArrowBackIcon
              classes={{
                root: classNames(
                  classes.headerIcon,
                  classes.headerIconCollapse,
                ),
              }}
            />
          ) : (
            <MenuIcon
              classes={{
                root: classNames(
                  classes.headerIcon,
                  classes.headerIconCollapse,
                ),
              }}
            />
          )}
        </IconButton>
        
        <Typography variant="h6" weight="medium" className={classes.logotype}>
          CUBRID 분기정산
        </Typography>
        <div className={classes.grow} />
        
        <IconButton
          aria-haspopup="true"
          color="inherit"
          className={classes.headerMenuButton}
          aria-controls="profile-menu"
          onClick={e => setProfileMenu(e.currentTarget)}
        >
          <AccountIcon classes={{ root: classes.headerIcon }} />
        </IconButton>
        <Menu
          id="profile-menu"
          open={Boolean(profileMenu)}
          anchorEl={profileMenu}
          onClose={() => setProfileMenu(null)}
          className={classes.headerMenu}
          classes={{ paper: classes.profileMenu }}
          disableAutoFocusItem
        >
          <div className={classes.profileMenuUser}>
            <Typography variant="h4" weight="medium">
              {userName}
            </Typography>
            <Typography
              className={classes.profileMenuLink}
              component="a"
              color="primary"
            >
              {userDepartment}
            </Typography>
          </div>
          <MenuItem
            className={classNames(
              classes.profileMenuItem,
              classes.headerMenuItem,
            )}
          >
            <AccountIcon className={classes.profileMenuIcon} /> Profile
          </MenuItem>
          
          <div className={classes.profileMenuUser}>
            <Typography
              className={classes.profileMenuLink}
              color="primary"
              onClick={() => signOut(userDispatch, props.history, userInfo)}
            >
              로그아웃
            </Typography>
          </div>
        </Menu>
      </Toolbar>
    </AppBar>
  );
}
