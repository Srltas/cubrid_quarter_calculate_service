import React, { useState } from "react";
import {
  Grid,
} from "@material-ui/core";
//import { useTheme } from "@material-ui/styles";

// styles
import useStyles from "./styles";
import pageTitle_useStyles from "../../components/PageTitle/styles";

// components
import Widget from "../../components/Widget";
import { Typography } from "../../components/Wrappers";
import Table from "./components/Table/Table";

// context
import { useUserState } from "../../context/UserContext";

// DashboardService
import DashboardService from "../../service/DashboardService";


export default function Dashboard(props) {
  var classes = useStyles();
  
  var pageTitle_classes = pageTitle_useStyles();

  // local
  var userInfo = useUserState();
  
  /** 여기부터 개인적으로 추가 */ 
  console.log("Dashboard_props : " +  JSON.stringify(props));
  
  /** localStorage 값 저장하는 곳 */
  var logid = "";
  var select_year = "";
  var years = "";
  
  if(props.location.DB_logid === undefined || props.location.DB_logid === null || props.location.DB_logid === ""){
	logid = userInfo.DB_logid;
	console.log("Dashboard_DB_logid_r : " +  userInfo.DB_logid);
  } else {
	logid = props.location.DB_logid;
	localStorage.removeItem("DB_logid");
	localStorage.setItem("DB_logid", logid);
	console.log("Dashboard_DB_logid_f : " +  props.location.DB_logid);
  }
  
  if(props.location.select_year === undefined || props.location.select_year === null || props.location.select_year === ""){
	select_year = userInfo.Select_year;
	console.log("Dashboard_Select_year_r : " +  userInfo.Select_year);
  } else {
	select_year = props.location.select_year;
	console.log("Dashboard_Select_year_f : " +  props.location.select_year);
  }
  
  if(props.location.years === undefined || props.location.years === null || props.location.years === ""){
	years = '[' + userInfo.Years + ']';
	years = JSON.parse(years);
	console.log("Dashboard_years_r : " +  JSON.stringify(years));
	//years=["2022"];
  } else {
	years = props.location.years;
	localStorage.removeItem("years");
	localStorage.setItem("years", years);
	console.log("Dashboard_years_f : " +  props.location.years);
  }
  
  /** selectbox 값 저장 */
  const [selectyear, setSelectYear] = useState(select_year);
  
  const onSelectChange = (e) => {
    const {value} = e.target
    setSelectYear(value)
  };
  
  const SelectOptions= years;
  
  /** 년도 DB 데이터 가져옴 */
  var dashboardData = DashboardService(logid, selectyear);
  
  /** selectbox 선택 후 새로고침(f5) 하면 그 값 다시 불러오기 위해 사용 */
  localStorage.setItem("select_year", selectyear);
  
  /** 분기 정산결과 가져오기 */
  //const quarterData = dashboardData.map(x => x);
  var firstQuarter = [];
  var secondQuarter = [];
  var thirdQuarter = [];
  var fourthQuarter = [];
  
  dashboardData.forEach(quarterData =>{
	if(quarterData.quarter === "1"){
		firstQuarter = quarterData
	} 
	else if(quarterData.quarter === "2"){
		secondQuarter = quarterData
	}
	else if(quarterData.quarter === "3"){
		thirdQuarter = quarterData
	}
	else if(quarterData.quarter === "4"){
		fourthQuarter = quarterData
	}
  });
  
  /** json 데이터 꺼내오기 연습 */
	  //const first_year = dashboardData.map(x => x.year);
	  //console.log("dashboardData props : " + JSON.stringify(first_year[0]) );
	  //console.log("year props : " + props.location.search );
	  //props.location.search = "?2021";
	  //props.match.params.year = "/:2021";
  
  return (
    <>
      <div className={pageTitle_classes.pageTitleContainer}>
      	<Typography className={classes.typo} variant="h1" size="sm">
        	년도 선택 : 
      	</Typography>
      	&nbsp;&nbsp;&nbsp;&nbsp;
	    {years && (
			<select
				className={classes.select}
				value={selectyear}
				onChange={onSelectChange}
			>
			{
				SelectOptions.map(selectyear => (
				    <option key={selectyear} value={selectyear}>{selectyear}</option>
				))
	         }
	      	</select>
		)}
	  </div>
	  
      <Grid container spacing={4}>
        <Grid item lg={3} md={4} sm={6} xs={12}>
          <Widget
            title="1분기 정산결과"
            upperTitle
            bodyClass={classes.fullHeightBody}
            className={classes.card}
          >
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	보상휴가 발생시간 :
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(firstQuarter) ?
					parseInt(firstQuarter.compensationLeaveTime/3600) <= 10 ? 
	                	"0".concat(parseInt(firstQuarter.compensationLeaveTime/3600)) 
	                	: parseInt(firstQuarter.compensationLeaveTime/3600)
	                : "00"
                }:00
              </Typography>
            </div>
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	수당 발생시간 : 
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(firstQuarter) ?
					parseInt(firstQuarter.calculateMoney/3600) <= 10 ? 
	                	"0".concat(parseInt(firstQuarter.calculateMoney/3600)) 
	                	: parseInt(firstQuarter.calculateMoney/3600)
	                : "00"
                }:00
              </Typography>
            </div>
            <Grid
              container
              direction="row"
              justify="space-between"
              alignItems="center"
            >
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  분기 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(firstQuarter) ?
						parseInt(firstQuarter.quarterTotalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  법정 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(firstQuarter) ?
						parseInt(firstQuarter.quarterLegalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
            </Grid>
          </Widget>
        </Grid>
        
        <Grid item lg={3} md={4} sm={6} xs={12}>
          <Widget
            title="2분기 정산결과"
            upperTitle
            bodyClass={classes.fullHeightBody}
            className={classes.card}
          >
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	보상휴가 발생시간 :
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(secondQuarter) ?
					parseInt(secondQuarter.compensationLeaveTime/3600) <= 10 ? 
	                	"0".concat(parseInt(secondQuarter.compensationLeaveTime/3600)) 
	                	: parseInt(secondQuarter.compensationLeaveTime/3600)
	                : "00"
                }:00
              </Typography>
            </div>
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	수당 발생시간 : 
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(secondQuarter) ?
					parseInt(secondQuarter.calculateMoney/3600) <= 10 ? 
	                	"0".concat(parseInt(secondQuarter.calculateMoney/3600)) 
	                	: parseInt(secondQuarter.calculateMoney/3600)
	                : "00"
                }:00
              </Typography>
            </div>
            <Grid
              container
              direction="row"
              justify="space-between"
              alignItems="center"
            >
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  분기 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(secondQuarter) ?
						parseInt(secondQuarter.quarterTotalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  법정 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(secondQuarter) ?
						parseInt(secondQuarter.quarterLegalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
            </Grid>
          </Widget>
        </Grid>
        
        <Grid item lg={3} md={4} sm={6} xs={12}>
          <Widget
            title="3분기 정산결과"
            upperTitle
            bodyClass={classes.fullHeightBody}
            className={classes.card}
          >
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	보상휴가 발생시간 :
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(thirdQuarter) ?
					parseInt(thirdQuarter.compensationLeaveTime/3600) <= 10 ? 
	                	"0".concat(parseInt(thirdQuarter.compensationLeaveTime/3600)) 
	                	: parseInt(thirdQuarter.compensationLeaveTime/3600)
	                : "00"
                }:00
              </Typography>
            </div>
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	수당 발생시간 : 
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(thirdQuarter) ?
					parseInt(thirdQuarter.calculateMoney/3600) <= 10 ? 
	                	"0".concat(parseInt(thirdQuarter.calculateMoney/3600)) 
	                	: parseInt(thirdQuarter.calculateMoney/3600)
	                : "00"
	            }:00
              </Typography>
            </div>
            <Grid
              container
              direction="row"
              justify="space-between"
              alignItems="center"
            >
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  분기 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(thirdQuarter) ?
						parseInt(thirdQuarter.quarterTotalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  법정 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(thirdQuarter) ?
						parseInt(thirdQuarter.quarterLegalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
            </Grid>
          </Widget>
        </Grid>
        
        <Grid item lg={3} md={4} sm={6} xs={12}>
          <Widget
            title="4분기 정산결과"
            upperTitle
            bodyClass={classes.fullHeightBody}
            className={classes.card}
          >
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	보상휴가 발생시간 :
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(fourthQuarter) ?
					parseInt(fourthQuarter.compensationLeaveTime/3600) <= 10 ? 
                		"0".concat(parseInt(fourthQuarter.compensationLeaveTime/3600)) 
                		: parseInt(fourthQuarter.compensationLeaveTime/3600)
                	: "00"
                }:00
              </Typography>
            </div>
            <div className={classes.visitsNumberContainer}>
              <Typography color="text" colorBrightness="secondary" weight="bold">
              	수당 발생시간 : 
              </Typography>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <Typography size="xl" weight="medium">
                {isNaN(fourthQuarter) ?
					parseInt(fourthQuarter.calculateMoney/3600) <= 10 ? 
                		"0".concat(parseInt(fourthQuarter.calculateMoney/3600)) 
                		: parseInt(fourthQuarter.calculateMoney/3600)
                	: "00"
                }:00
              </Typography>
            </div>
            <Grid
              container
              direction="row"
              justify="space-between"
              alignItems="center"
            >
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  분기 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(fourthQuarter) ?
						parseInt(fourthQuarter.quarterTotalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
              <Grid item>
                <Typography color="text" colorBrightness="secondary">
                  법정 근로시간
                </Typography>
                <Typography size="md">
                	{isNaN(fourthQuarter) ?
						parseInt(fourthQuarter.quarterLegalTime/3600)
						: "00"
					}:00
                </Typography>
              </Grid>
            </Grid>
          </Widget>
        </Grid>
        
        <Grid item xs={12}>
          <Widget
            title="분기 상세정보"
            upperTitle
            noBodyPadding
            bodyClass={classes.tableWidget}
          >
            <Table data={dashboardData} />
          </Widget>
        </Grid>
      </Grid>
    </>
  );
}

// #######################################################################

