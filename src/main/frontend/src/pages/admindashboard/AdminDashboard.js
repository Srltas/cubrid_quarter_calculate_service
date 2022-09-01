import React from "react";
import { Grid } from "@material-ui/core";
import MUIDataTable from "mui-datatables";

// components
import PageTitle from "../../components/PageTitle";

// data
import AdminDashboardService from "../../service/AdminDashboardService";

const columns = [
	{
  		name: "year",
  		label: "년도",
 	},
 	{
  		name: "quarter",
  		label: "분기",
 	},
	{
  		name: "name",
  		label: "이름",
 	},
 	{
  		name: "quarterTotalTime",
  		label: "분기 근로시간",
	 	options: {
		    filter: false,
		    sort: true,
		    hint: "툴팁!@!@#",
	    }
 	},
 	{
  		name: "quarterLegalTime",
  		label: "법정 근로시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "quarterWorkTime",
  		label: "내 근로시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "regulationWorkOverTime",
  		label: "소정근로 연장",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "legalWorkOverTime",
  		label: "법정근로 연장",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "nightWorkTime",
  		label: "야간 근로시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "holidayWorkTime",
  		label: "휴일 근로시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "holiday8HOver",
  		label: "휴일 8시간 초과시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "leaveTime",
  		label: "사용한 휴가",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "compensationLeaveTime",
  		label: "보상휴가 발생시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "calculateMoney",
  		label: "수당 발생시간",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
 	{
  		name: "calculateTotal",
  		label: "정산 총계",
	 	options: {
		    filter: false,
		    sort: true,
	    }
 	},
];

export default function AdminDashboard(props) {

  console.log("AdminDashboard_props : " + props.location.userRole);
  var userDepartment = "";
  
  if(props.location.userRole === "admin"){
	userDepartment = "ALL";
  } else{
	userDepartment = props.location.userDepartment;
  }
  
  var datatableData = AdminDashboardService(userDepartment);
  //console.log("datatableData@@ : " + JSON.stringify(datatableData));
  
  var hour = 0;
  var min = 0;
  
  datatableData.forEach(data =>{
	/**분기 근로시간 */
	data.quarterTotalTime		=	parseInt(data.quarterTotalTime/3600) < 10 ?
									"0".concat(parseInt(data.quarterTotalTime/3600)) + ":00" 
									: parseInt(data.quarterTotalTime/3600) + ":00";
	/**법정 근로시간 */					
	data.quarterLegalTime		=	parseInt(data.quarterLegalTime/3600) < 10 ?
									"0".concat(parseInt(data.quarterLegalTime/3600)) + ":00" 
									: parseInt(data.quarterLegalTime/3600) + ":00";
	/**내 근로시간 */				
	hour =	parseInt(data.quarterWorkTime/3600);
	min =	parseInt(data.quarterWorkTime%3600/60) < 10 ?
		 	"0".concat(parseInt(data.quarterWorkTime%3600/60))
		 	: parseInt(data.quarterWorkTime%3600/60);
	data.quarterWorkTime		=	hour + ":" + min;
	/**소정 근로연장 */
	hour =	parseInt(data.regulationWorkOverTime/3600) < 10 ?
		 	"0".concat(parseInt(data.regulationWorkOverTime/3600))
		 	: parseInt(data.regulationWorkOverTime/3600);
	min =	parseInt(data.regulationWorkOverTime%3600/60) < 10 ?
		 	"0".concat(parseInt(data.regulationWorkOverTime%3600/60))
		 	: parseInt(data.regulationWorkOverTime%3600/60);
	data.regulationWorkOverTime	=	hour + ":" + min;
	/**법정 근로연장 */
	hour =	parseInt(data.legalWorkOverTime/3600) < 10 ?
		 	"0".concat(parseInt(data.legalWorkOverTime/3600))
		 	: parseInt(data.legalWorkOverTime/3600);
	min =	parseInt(data.legalWorkOverTime%3600/60) < 10 ?
		 	"0".concat(parseInt(data.legalWorkOverTime%3600/60))
		 	: parseInt(data.legalWorkOverTime%3600/60);
	data.legalWorkOverTime		=	hour + ":" + min;
	/**야간 근로시간 */
	hour =	parseInt(data.nightWorkTime/3600) < 10 ?
		 	"0".concat(parseInt(data.nightWorkTime/3600))
		 	: parseInt(data.nightWorkTime/3600);
	min =	parseInt(data.nightWorkTime%3600/60) < 10 ?
		 	"0".concat(parseInt(data.nightWorkTime%3600/60))
		 	: parseInt(data.nightWorkTime%3600/60);
	data.nightWorkTime			=	hour + ":" + min;
	/**휴일 근로시간 */
	hour =	parseInt(data.holidayWorkTime/3600) < 10 ?
		 	"0".concat(parseInt(data.holidayWorkTime/3600))
		 	: parseInt(data.holidayWorkTime/3600);
	min =	parseInt(data.holidayWorkTime%3600/60) < 10 ?
		 	"0".concat(parseInt(data.holidayWorkTime%3600/60))
		 	: parseInt(data.holidayWorkTime%3600/60);
	data.holidayWorkTime		=	hour + ":" + min;
	/**휴일 8시간 초과시간 */
	hour =	parseInt(data.holiday8HOver/3600) < 10 ?
		 	"0".concat(parseInt(data.holiday8HOver/3600))
		 	: parseInt(data.holiday8HOver/3600);
	min =	parseInt(data.holiday8HOver%3600/60) < 10 ?
		 	"0".concat(parseInt(data.holiday8HOver%3600/60))
		 	: parseInt(data.holiday8HOver%3600/60);
	data.holiday8HOver			=	hour + ":" + min;
	/**사용한 휴가 */
	hour =	parseInt(data.leaveTime/3600) < 10 ?
		 	"0".concat(parseInt(data.leaveTime/3600))
		 	: parseInt(data.leaveTime/3600);
	min =	parseInt(data.leaveTime%3600/60) < 10 ?
		 	"0".concat(parseInt(data.leaveTime%3600/60))
		 	: parseInt(data.leaveTime%3600/60);
	data.leaveTime				=	hour + ":" + min;
	/**보상휴가 발생시간 */
	data.compensationLeaveTime	=	parseInt(data.compensationLeaveTime/3600) < 10 ?
									"0".concat(parseInt(data.compensationLeaveTime/3600)) + ":00" 
									: parseInt(data.compensationLeaveTime/3600) + ":00"
	/**수당 발생시간 */
	data.calculateMoney			=	parseInt(data.calculateMoney/3600) < 10 ?
									"0".concat(parseInt(data.calculateMoney/3600)) + ":00" 
									: parseInt(data.calculateMoney/3600) + ":00"
	/**정산 총계 */
	data.calculateTotal			=	parseInt(data.calculateTotal/3600) < 10 ?
									"0".concat(parseInt(data.calculateTotal/3600)) + ":00" 
									: parseInt(data.calculateTotal/3600) + ":00"
  });
	
  return (
    <>
      <PageTitle title="관리자 대시보드" />
        <Grid item xs={12}>
          <MUIDataTable
            title="직원 리스트"
            data={datatableData}
            columns={columns}
            options={{
              filterType: "checkbox",
            }}
          />
        </Grid>
    </>
  );
}
