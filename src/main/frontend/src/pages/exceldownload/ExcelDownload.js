import React from "react";
import { Grid } from "@material-ui/core";
import MUIDataTable from "mui-datatables";

// components
import PageTitle from "../../components/PageTitle";

// data
import ExcelDownloadService from "../../service/ExcelDownloadService";

const columns = [
	{
  		name: "name",
  		label: "근로자 이름",
  		options: {
		    filter: true,
		    sort: true,
		    download: true,
	    }
 	},
 	{
  		name: "vacationType",
  		label: "휴가 종류",
  		options: {
		    filter: false,
		    sort: false,
		    download: true,
	    }
 	},
	{
  		name: "compensationLeaveTime",
  		label: "부여 시간",
  		options: {
		    filter: false,
		    sort: true,
		    download: true,
	    }
 	},
 	{
  		name: "deductionTime",
  		label: "차감 시간",
	 	options: {
		    filter: false,
		    sort: false,
		    download: true,
		    hint: "툴팁!@!@#",
	    }
 	},
 	{
  		name: "expiryDate",
  		label: "만료일",
	 	options: {
		    filter: false,
		    sort: false,
		    download: true,
	    }
 	},
 	{
  		name: "causeOfOccurrence",
  		label: "발생 사유",
	 	options: {
		    filter: false,
		    sort: false,
		    download: true,
	    }
 	},
];

export default function ExcelDownload(props) {

  //console.log("ExcelDownload_props@@ : " + JSON.stringify(props.history.location));

  var datatableData = ExcelDownloadService(props.history.location.years[0], props.history.location.last_quarter);
  //console.log("ExcelDownload@@ : " + JSON.stringify(datatableData));
  
  /**발생 사유 */
  var causeOfOccurrence = props.history.location.years[0] +"년 " + props.history.location.last_quarter + "분기 보상휴가";
  
  /**테이블 데이터 가공 */
  datatableData.forEach(data =>{
	/**휴가 종류 */
	data.vacationType = "보상 휴가";
	/**보상휴가 발생시간 */
	data.compensationLeaveTime	=	parseInt(data.compensationLeaveTime/3600) <= 10 ?
									"0".concat(parseInt(data.compensationLeaveTime/3600)) 
									: parseInt(data.compensationLeaveTime/3600)
	/**차감 시간 */
	data.deductionTime = 0;
	/**만료일 */
	data.expiryDate=0;
	/**발생 사유 */
	data.causeOfOccurrence= causeOfOccurrence;
	
  });
	
  return (
    <>
      <PageTitle title="콩체크 엑셀 다운로드" />
        <Grid item xs={12}>
          <MUIDataTable
            title={props.history.location.years[0] + "년 " + props.history.location.last_quarter + "분기 보상휴가 엑셀 다운로드"}
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
