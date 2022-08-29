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
  { id: 8, label: "전체보기", link: "/app/admindashboard", icon: <ListAltIcon /> },
  { id: 9, label: "콩체크 엑셀 로드", link: "", icon: <CloudUploadIcon /> },
  { id: 10, label: "콩체크 엑셀 다운로드", link: "/app/exceldownload", icon: <CloudDownloadIcon /> },
  { id: 11, type: "divider" },
  
  { id: 12, type: "title", label: "PROJECTS" },
  {
    id: 13,
    label: "My recent",
    link: "",
    icon: <Dot size="large" color="primary" />,
  },
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
  
  if(props.history.location.DB_logid === undefined || props.history.location.DB_logid === null || props.history.location.DB_logid === ""){
	props.history.location.DB_logid = userInfo.DB_logid;
	console.log("Sidebar_logid_r : " +  userInfo.DB_logid);
  } else {
	props.history.location.DB_logid = props.location.DB_logid;
	console.log("Sidebar_logid_f : " +  props.history.location.DB_logid);
  }
  
  if(props.history.location.select_year === undefined || props.history.location.select_year === null || props.history.location.select_year === ""){
	props.history.location.select_year = userInfo.Select_year;
	console.log("Sidebar_Select_year_r : " +  userInfo.Select_year);
  } else {
	props.history.location.select_year = props.location.select_year;
	console.log("Sidebar_Select_year_f : " +  props.history.location.select_year);
  }
  
  if(props.history.location.years === undefined || props.history.location.years === null || props.history.location.years === ""){
	years = '[' + userInfo.Years + ']';
	years = JSON.parse(years);
	props.history.location.years = years;
	console.log("Sidebar_years_r : " +  JSON.stringify(years));
  } else {
	props.history.location.years = props.location.years;
	console.log("Sidebar_years_f : " +  props.history.location.years);
  }
  
  if(props.history.location.last_quarter === undefined || props.history.location.last_quarter === null || props.history.location.last_quarter === ""){
	props.history.location.last_quarter = userInfo.Last_quarter;
	console.log("last_quarter_logid_r : " +  userInfo.Last_quarter);
  } else {
	props.history.location.last_quarter = props.location.last_quarter;
	console.log("last_quarter_logid_f : " +  props.history.location.last_quarter);
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
