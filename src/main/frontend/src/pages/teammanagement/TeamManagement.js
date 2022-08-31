import React, {useState, useEffect} from "react";
import axios from 'axios'; 
import {
  Grid,
  Typography,
  Button,
  TextField,
} from "@material-ui/core";
import MUIDataTable from "mui-datatables";
import DatePicker, { registerLocale } from "react-datepicker";
import ko from 'date-fns/locale/ko';
import moment from 'moment';
import createHistory from 'history/createBrowserHistory'

// styles
import useStyles from "../login/styles";
import "react-datepicker/dist/react-datepicker.css";

// components
import PageTitle from "../../components/PageTitle";

// data
import TeamManagementService from "../../service/TeamManagementService";

const columns = [
	{
  		name: "name",
  		label: "이름",
  		options: {
		    filter: true,
		    sort: true,
	    }
 	},
 	{
  		name: "first_day_of_work",
  		label: "입사일",
  		options: {
		    filter: false,
		    sort: false,
	    }
 	},
 	{
  		name: "last_day_of_work",
  		label: "퇴사일",
  		options: {
		    filter: false,
		    sort: false,
	    }
 	},
];

export default function TeamManagement(props) {
  var classes = useStyles();

  var datatableData = TeamManagementService();
  //console.log("TeamManagement@@ : " + JSON.stringify(datatableData));
  
  /**테이블 데이터 가공 */
  datatableData.forEach(data =>{
	if(data.last_day_of_work === undefined || data.last_day_of_work === null){
		data.last_day_of_work = "9999-12-31";	
	}
  });
  
  /**컬럼 초기 값 */
  var [rowValue, setRowValue] = useState("");
  var [rowNameValue, setRowNameValue] = useState("");
  var [rowFdayworkValue, setRowFdayworkValue] = useState(new Date());
  var [rowLdayworkValue, setRowLdayworValue] = useState(new Date());
  
  /**onRowClick 값 split 수행 */
  var valuesplit = JSON.stringify(rowValue).split(',');
  var l_day_work = "9999-12-31";
  	  l_day_work = new Date(l_day_work);
  if (valuesplit.length >= 3) {
    var names = valuesplit[0];
	    names = names.replace("[","");
	    names = names.replace(/"/gi,"");
  	var f_day_work = valuesplit[1];
  		f_day_work = new Date(f_day_work);
  	l_day_work = valuesplit[2];
	l_day_work = l_day_work.replace("]","");
	l_day_work = new Date(l_day_work);
	valuesplit="";
  }
  
  /**컬럼 입력시 동적으로 값 변하게 하기 위한 훅 */
  useEffect(() => {
	setRowNameValue(names);
	setRowFdayworkValue(f_day_work);
	setRowLdayworValue(l_day_work);
  }, [names,datatableData]);
  
  /**수정&추가 버튼 비활성화 */
  var rowNameCount = 0;
  var rowFdayworkCount = 0;
  var rowLdayworkCount = 0;
  
  /** usetate 값은 비동기적이며 처음 랜더링 하기 전에 동작해 항상 값이 undefined 으로 length등의 함수 사용 불가능 */
  if(rowNameValue !== undefined && rowFdayworkValue !== undefined && rowLdayworkValue !== undefined){
	  rowNameCount = rowNameValue;
	  rowNameCount = rowNameCount.length;
	  
	  rowFdayworkCount = rowFdayworkValue;
	  rowFdayworkCount = rowFdayworkCount.length;
	  
	  rowLdayworkCount = rowLdayworkValue;
	  rowLdayworkCount = rowLdayworkCount.length
  }
  
  /** 입력 값 초기화 onClick */
  const onReset = () => {
	setRowValue("");
    setRowNameValue("");
    setRowFdayworkValue("");
    setRowLdayworValue("");
  };
  
  /**달력 값 가지고놀기 */
  registerLocale("ko", ko);
  
  var rowFdayworkString = moment(rowFdayworkValue).format("YYYY-MM-DD");
  var rowLdayworkString = moment(rowLdayworkValue).format("YYYY-MM-DD");
  console.log("rowFdayworkValue2: " + rowFdayworkString);
  console.log("rowLdayworkValue2: " + rowLdayworkString);
  
  /**TeamManagementMergeService */
  const URL_PATH = "/api/teammanagement/merge";
  const mergeSubmit = () =>{
	axios.get(URL_PATH, {
		params:{
		name: rowNameValue,
		front_first_day_of_work: rowFdayworkString,
		front_last_day_of_work: rowLdayworkString,
		}
	}).then(()=>{
		alert('수정 or 추가 완료!');
		//window.location.replace("/app/teammanagement");
		const history = createHistory();
		history.go(0)
	})
  };
  
  return (
    <>
      <PageTitle title="팀관리" />
      <Grid container spacing={2}>
        <Grid item xs={7}>
          <MUIDataTable
            title="직원 리스트"
            data={datatableData}
            columns={columns}
            options={{
              filterType: "checkbox",
              viewColumns: false,
              print: false,
			  download: false,
			  onRowClick: rowData => setRowValue(rowData),
            }}
          />
        </Grid>
        <Grid item xs={5}>
          <Typography componet="h1" variant="h3" gutterBottom>
      		팀원 수정 & 추가
    	  </Typography>
    	  <hr color="black"/>
    	  <br/>
          <Typography componet="h2" variant="h5" gutterBottom>
      		이름
    	  </Typography>
		  <TextField
		  	type="text"
		    id="name"
		    variant="outlined"
		    value={rowNameValue}
		    onChange={e => setRowNameValue(e.target.value)}
		    margin="normal"
		    fullWidth
		  />
		  <br/><br/>
		  <Typography componet="h2" variant="h5" gutterBottom>
      		입사일
    	  </Typography>
    	  <DatePicker
		    locale="ko"
		    dateFormat="yyyy-MM-dd"
	        selected={rowFdayworkValue}
	        onChange={(date) => setRowFdayworkValue(date)}
	        placeholderText="입사일 클릭"
	        dateFormatCalendar= {"yyyy년 MM월"}
	      />
	      <br/><br/>
		  <Typography componet="h2" variant="h5" gutterBottom>
      		퇴사일
    	  </Typography>
		  <DatePicker
		    locale="ko"
		    dateFormat="yyyy-MM-dd"
	        selected={rowLdayworkValue}
	        onChange={(date) => setRowLdayworValue(date)}
	        placeholderText="퇴사일 클릭"
	        dateFormatCalendar= {"yyyy년 MM월"}
	      />
		  
		  
		  <div className={classes.formButtons}>
            <Button
                disabled={
                  rowNameCount === 0 || rowFdayworkCount === 0 || rowLdayworkCount === 0
                }
                onClick={mergeSubmit}
                variant="contained"
                color="primary"
                size="large"
              >
                수정 & 추가
            </Button>
            <Button
              variant="contained"
              color="primary"
              size="large"
              onClick={onReset}
              className={classes.forgetButton}
            >
              입력 내용 초기화
            </Button>
          </div>
        </Grid>
       </Grid>
    </>
  );
}

// ###########################################################