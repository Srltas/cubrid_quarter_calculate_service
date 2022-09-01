import React, { useState, useEffect } from "react";
import { Drawer, IconButton, List } from "@material-ui/core";
import {
  Home as HomeIcon,
  NotificationsNone as NotificationsIcon,
  FormatSize as TypographyIcon,
  FilterNone as UIElementsIcon,
  BorderAll as TableIcon,
  People as PeopleIcon,
  ListAlt as ListAltIcon,
  ArrowBack as ArrowBackIcon,
  CloudDownload as CloudDownloadIcon,
  CloudUpload as CloudUploadIcon,
} from "@material-ui/icons";
import { useTheme } from "@material-ui/styles";
import { withRouter } from "react-router-dom";
import classNames from "classnames";

// styles
import useStyles from "./styles";

// components
import SidebarLink from "./components/SidebarLink/SidebarLink";

// context
import {
  useLayoutState,
  useLayoutDispatch,
  toggleSidebar,
} from "../../context/LayoutContext";
import { useUserState } from "../../context/UserContext";

const structure = [
  { id: 0, label: "대시보드", link: "/app/dashboard", icon: <HomeIcon /> },
  {
    id: 1,
    label: "Typography",
    link: "/app/typography",
    icon: <TypographyIcon />,
  },
  { id: 2, label: "Tables", link: "/app/tables", icon: <TableIcon /> },
  {
    id: 3,
    label: "Notifications",
    link: "/app/notifications",
    icon: <NotificationsIcon />,
  },
  {
    id: 4,
    label: "UI Elements",
    link: "/app/ui",
    icon: <UIElementsIcon />,
    children: [
      { label: "Icons", link: "/app/ui/icons" },
      { label: "Charts", link: "/app/ui/charts" },
      { label: "Maps", link: "/app/ui/maps" },
    ],
  },
  { id: 5, type: "divider" },
  
  { id: 6, type: "title", label: "관리자 페이지" },
  { id: 7, label: "팀 관리", link: "/app/teammanagement", icon: <PeopleIcon /> },
  { id: 8, label: "전체보기", link: "/app/admindashboard", icon: <ListAltIcon /> },
  { id: 9, label: "콩체크 엑셀 로드", link: "http://localhost:8080/upload", icon: <CloudUploadIcon /> },
  { id: 10, label: "콩체크 엑셀 다운로드", link: "/app/exceldownload", icon: <CloudDownloadIcon /> },
];

function Sidebar(props) {
  var classes = useStyles();
  var theme = useTheme();

  // global
  var { isSidebarOpened } = useLayoutState();
  var layoutDispatch = useLayoutDispatch();

  // local
  var [isPermanent, setPermanent] = useState(true);

  useEffect(function() {
    window.addEventListener("resize", handleWindowWidthChange);
    handleWindowWidthChange();
    return function cleanup() {
      window.removeEventListener("resize", handleWindowWidthChange);
    };
  });

  var userInfo = useUserState();
  //console.log("Sidebar_location : " +  JSON.stringify(props.history.location));
  
  /** localStorage 값 저장하는 곳 */
  var years = "";
  
  if(props.history.location.userId === undefined || props.history.location.userId === null || props.history.location.userId === ""){
	props.history.location.userId = userInfo.userId;
	console.log("Sidebar_userId_r : " +  userInfo.userId);
  } else {
	props.history.location.userId = props.location.userId;
	console.log("Sidebar_userId_f : " +  props.history.location.userId);
  }
  
  if(props.history.location.userDepartment === undefined || props.history.location.userDepartment === null || props.history.location.userDepartment === ""){
	props.history.location.userDepartment = userInfo.userDepartment;
	console.log("Sidebar_userDepartment_r : " +  userInfo.userDepartment);
  } else {
	props.history.location.userDepartment = props.location.userDepartment;
	console.log("Sidebar_userDepartment_f : " +  props.history.location.userDepartment);
  }
  
  if(props.history.location.userName === undefined || props.history.location.userName === null || props.history.location.userName === ""){
	props.history.location.userName = userInfo.userName;
	console.log("Sidebar_userName_r : " +  userInfo.userName);
  } else {
	props.history.location.userName = props.location.userName;
	console.log("Sidebar_userName_f : " +  props.history.location.userName);
  }
  
  if(props.history.location.userRole === undefined || props.history.location.userRole === null || props.history.location.userRole === ""){
	props.history.location.userRole = userInfo.userRole;
	console.log("Sidebar_userRole_r : " +  userInfo.userRole);
  } else {
	props.history.location.userRole = props.location.userRole;
	console.log("Sidebar_userRole_f : " +  props.history.location.userRole);
  }
  
  if(props.history.location.select_year === undefined || props.history.location.select_year === null || props.history.location.select_year === ""){
	props.history.location.select_year = userInfo.select_year;
	console.log("Sidebar_Select_year_r : " +  userInfo.select_year);
  } else {
	props.history.location.select_year = props.location.select_year;
	console.log("Sidebar_Select_year_f : " +  props.history.location.select_year);
  }
  
  if(props.history.location.last_quarter === undefined || props.history.location.last_quarter === null || props.history.location.last_quarter === ""){
	props.history.location.last_quarter = userInfo.last_quarter;
	console.log("last_quarter_logid_r : " +  userInfo.last_quarter);
  } else {
	props.history.location.last_quarter = props.location.last_quarter;
	console.log("last_quarter_logid_f : " +  props.history.location.last_quarter);
  }
  
  if(props.history.location.years === undefined || props.history.location.years === null || props.history.location.years === ""){
	years = '[' + userInfo.years + ']';
	years = JSON.parse(years);
	props.history.location.years = years;
	console.log("Sidebar_years_r : " +  JSON.stringify(years));
  } else {
	props.history.location.years = props.location.years;
	console.log("Sidebar_years_f : " +  props.history.location.years);
  }
  
  /** 권한에 따라 사이드바 보이는 값 조정 */
  const newStructure = [];
  structure.forEach(data => {
	if(props.history.location.userRole === "user"){
		if(data.id === 0 ){
			newStructure.push(data);
		}
	} else if (props.history.location.userRole === "teamleader"){
		if(data.id === 0 || data.id === 5 || data.id === 6 || data.id === 7 || data.id === 8 ){
			newStructure.push(data);
		}
	} else {
		newStructure.push(data);
	}
  });

  return (
    <Drawer
      variant={isPermanent ? "permanent" : "temporary"}
      className={classNames(classes.drawer, {
        [classes.drawerOpen]: isSidebarOpened,
        [classes.drawerClose]: !isSidebarOpened,
      })}
      classes={{
        paper: classNames({
          [classes.drawerOpen]: isSidebarOpened,
          [classes.drawerClose]: !isSidebarOpened,
        }),
      }}
      open={isSidebarOpened}
    >
      <div className={classes.toolbar} />
      <div className={classes.mobileBackButton}>
        <IconButton onClick={() => toggleSidebar(layoutDispatch)}>
          <ArrowBackIcon
            classes={{
              root: classNames(classes.headerIcon, classes.headerIconCollapse),
            }}
          />
        </IconButton>
      </div>
      <List className={classes.sidebarList}>
        {newStructure.map(link => (
          <SidebarLink
            key={link.id}
            location={props.location}
            isSidebarOpened={isSidebarOpened}
            {...link}
          />
        ))}
      </List>
    </Drawer>
  );

  // ##################################################################
  function handleWindowWidthChange() {
    var windowWidth = window.innerWidth;
    var breakpointWidth = theme.breakpoints.values.md;
    var isSmallScreen = windowWidth < breakpointWidth;

    if (isSmallScreen && isPermanent) {
      setPermanent(false);
    } else if (!isSmallScreen && !isPermanent) {
      setPermanent(true);
    }
  }
}

export default withRouter(Sidebar);
