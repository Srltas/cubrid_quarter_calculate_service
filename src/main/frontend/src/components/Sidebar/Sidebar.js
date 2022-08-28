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
  HelpOutline as FAQIcon,
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
import Dot from "./components/Dot";

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
  { id: 7, label: "팀 관리", link: "", icon: <PeopleIcon /> },
  { id: 8, label: "전체보기", link: "/app/adminDashboard", icon: <ListAltIcon /> },
  { id: 9, label: "콩체크 엑셀 로드", link: "", icon: <CloudUploadIcon /> },
  { id: 10, label: "콩체크 엑셀 다운로드", link: "", icon: <CloudDownloadIcon /> },
  { id: 11, type: "divider" },
  
  { id: 12, type: "title", label: "PROJECTS" },
  {
    id: 13,
    label: "My recent",
    link: "",
    icon: <Dot size="large" color="primary" />,
  },
];

function Sidebar({ location }) {
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
  
  console.log("Sidebar_location : " +  JSON.stringify(location));
  
  /** localStorage 값 저장하는 곳 */
  var logid = "";
  var select_year = "";
  var years = "";
  
  if(location.DB_logid === undefined || location.DB_logid === null || location.DB_logid === ""){
	location.DB_logid = userInfo.DB_logid;
	console.log("Sidebar_DB_logid_r : " +  userInfo.DB_logid);
  } else {
	location.DB_logid = location.DB_logid;
	console.log("Sidebar_DB_logid_f : " +  location.DB_logid);
  }
  
  if(location.select_year === undefined || location.select_year === null || location.select_year === ""){
	location.select_year = userInfo.Select_year;
	console.log("Sidebar_Select_year_r : " +  userInfo.Select_year);
  } else {
	location.select_year = location.select_year;
	console.log("Sidebar_Select_year_f : " +  location.select_year);
  }
  

  if(location.years === undefined || location.years === null || location.years === ""){
	location.years = '[' + userInfo.Years + ']';
	location.years = JSON.parse(location.years);
	console.log("Sidebar_years_r : " +  JSON.stringify(location.years));
	//years=["2022"];
  } else {
	location.years = location.years;
	localStorage.removeItem("years");
	localStorage.setItem("years", location.years);
	console.log("Sidebar_years_f : " +  location.years);
  }


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
        {structure.map(link => (
          <SidebarLink
            key={link.id}
            location={location}
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
