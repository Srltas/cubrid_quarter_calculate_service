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
  		name: "id",
  		label: "ID",
  		options: {
		    filter: false,
		    sort: false,
	    }
 	},
 	{
  		name: "department",
  		label: "부서",
  		options: {
		    filter: true,
		    sort: true,
	    }
 	},
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

const checkbox = {
	zoom: "2.0",
};

export default function TeamManagement(props) {
  var classes = useStyles();
  
  //console.log("TeamManagement_props : " + props.location.userRole);
  
  var userDepartment = "";
  
  /**팀장 or admin 권한 차이 */
  if(props.location.userRole === "admin"){
	userDepartment = "ALL";
  } else{
	userDepartment = props.location.userDepartment;
  }

  var datatableData = TeamManagementService(userDepartment);
  //console.log("TeamManagement : " + JSON.stringify(datatableData));
  
  /**컬럼 초기 값 */
  var [rowValue, setRowValue] = useState("");
  var [rowIDValue, setRowIDValue] = useState("");
  var [rowDepartmentValue, setRowDepartmentValue] = useState("");
  var [rowNameValue, setRowNameValue] = useState("");
  var [rowFdayworkValue, setRowFdayworkValue] = useState(new Date());
  var [rowLdayworkValue, setRowLdayworValue] = useState(new Date());
  var [passwordInitialization, setpasswordInitialization] = useState(false);
  var SelectOptions= [];
  
  /**DB 데이터 가공 */
  datatableData.forEach(data =>{
	if(data.last_day_of_work === undefined || data.last_day_of_work === null){
		data.last_day_of_work = "9999-12-31";	
	}
	
	SelectOptions.push(data.department);
  });
  
  /** selectbox 값 저장 */
  var set = new Set(SelectOptions);
  var newSelectOptions = [...set];
  
  const onSelectChange = (e) => {
    const {value} = e.target
    setRowDepartmentValue(value)
  };
  
  /**onRowClick 값 split 수행 */
  var valuesplit = JSON.stringify(rowValue).split(',');
  var l_day_work = "9999-12-31";
  	  l_day_work = new Date(l_day_work);
  if (valuesplit.length >= 3) {
	var id = valuesplit[0];
		id = id.replace("[","");
		id = id.replace(/"/gi,"");
	var department = valuesplit[1];
		department = department.replace(/"/gi,"");
	var names = valuesplit[2];
	    names = names.replace(/"/gi,"");
  	var f_day_work = valuesplit[3];
  		f_day_work = new Date(f_day_work);
  	l_day_work = valuesplit[4];
	l_day_work = l_day_work.replace("]","");
	l_day_work = new Date(l_day_work);
	valuesplit="";
  }
  
  /**컬럼 입력시 동적으로 값 변하게 하기 위한 훅 */
  useEffect(() => {
	setRowIDValue(id);
	setRowDepartmentValue(department);
	setRowNameValue(names);
	setRowFdayworkValue(f_day_work);
	setRowLdayworValue(l_day_work);
  }, [id,department,names,datatableData]);
  
  /**수정&추가 버튼 비활성화 */
  var rowNameCount = 0;
  var rowFdayworkCount = 0;
  var rowLdayworkCount = 0;
  var rowIDCount = 0;
  var rowDepartmentCount = 0;
  
  /** usetate 값은 비동기적이며 처음 랜더링 하기 전에 동작해 항상 값이 undefined 으로 length등의 함수 사용 불가능 */
  if(rowNameValue !== undefined && rowFdayworkValue !== undefined && rowLdayworkValue !== undefined && rowIDValue !== undefined && rowDepartmentValue !== undefined){
	  rowNameCount = rowNameValue;
	  rowNameCount = rowNameCount.length;
	  
	  rowFdayworkCount = rowFdayworkValue;
	  rowFdayworkCount = rowFdayworkCount.length;
	  
	  rowLdayworkCount = rowLdayworkValue;
	  rowLdayworkCount = rowLdayworkCount.length
	  
	  rowIDCount = rowIDValue;
	  rowIDCount = rowIDCount.length
	  
	  rowDepartmentCount = rowDepartmentValue;
	  rowDepartmentCount = rowDepartmentCount.length
  }
  
  /** 입력 값 초기화 onClick */
  const onReset = () => {
	setRowValue("");
	setRowIDValue("");
	setRowDepartmentValue("");
    setRowNameValue("");
    setRowFdayworkValue("");
    setRowLdayworValue("");
    setpasswordInitialization("");
  };
  
  /**달력 값 로직 */
  registerLocale("ko", ko);
  
  var rowFdayworkString = moment(rowFdayworkValue).format("YYYY-MM-DD");
  var rowLdayworkString = moment(rowLdayworkValue).format("YYYY-MM-DD");
  
  /**직원 추가 or 수정 Merge 기능*/
  const URL_PATH = "/api/teammanagement/merge";
  const mergeSubmit = () =>{
	if(window.confirm(
		"ID : " + rowIDValue + ",\n"
		+ "부서 : " + rowDepartmentValue + ",\n"
		+ "이름 : " + rowNameValue + ",\n"
		+ "입사일 : " + rowFdayworkString + ",\n"
		+ "퇴사일 : " + rowLdayworkString + ",\n"
		+ "패스워드 초기화 : " + passwordInitialization
	)){
		axios.get(URL_PATH, {
			params:{
			id: rowIDValue,
			department: rowDepartmentValue,
			name: rowNameValue,
			front_first_day_of_work: rowFdayworkString,
			front_last_day_of_work: rowLdayworkString,
			passwdcheck: passwordInitialization,
			}
		}).then(()=>{
			alert("수정 or 추가 완료!");
			/**강제 새로고침 (DB 최신 데이터 다시 가져와야함)*/
			const history = createHistory();
			history.go(0)
		})
	} else {
		alert("취소합니다.");
	}
  };
  
  /**패스워드 초기화 이벤트*/
  const switchChecked = (e) => {
	if (e.target.checked) {
		setpasswordInitialization(true);
	} else {
		setpasswordInitialization(false);
	}
  }
  
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
      		직원 정보 수정 or 추가
    	  </Typography>
    	  <hr color="black"/>
    	  <br/>
    	  <Typography componet="h2" variant="h5" gutterBottom>
      		ID
    	  </Typography>
		  <TextField
		  	type="text"
		    id="ID"
		    variant="outlined"
		    value={rowIDValue}
		    onChange={e => setRowIDValue(e.target.value)}
		    margin="normal"
		    fullWidth
		  />
		  <br/><br/>
    	  <Typography componet="h2" variant="h5" gutterBottom>
      		부서
    	  </Typography>
		  <select
				className={classes.select}
				value={rowDepartmentValue}
				onChange={onSelectChange}
			>
			{
				newSelectOptions.map(departmentValue => (
				    <option key={departmentValue} value={departmentValue}>{departmentValue}</option>
				))
	         }
	      	</select>
		  <br/><br/>
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
	      <br/><br/>
		  <Typography componet="h2" variant="h5" gutterBottom>
      		패스워드 초기화
    	  </Typography>
    	  <Typography
              className={classes.profileMenuLink}
              color="primary"
          >
            ※신규 입사자는 자동으로 패스워드 초기화 됨※
          </Typography>
    	  <input
    	  	 style={checkbox}
	         type='checkbox'
	         id="passwordInitialization"
	         onClick={switchChecked}
	         checked={passwordInitialization} 
	      />
		  
		  <div className={classes.formButtons}>
            <Button
                disabled={
                  rowNameCount === 0 || rowFdayworkCount === 0 || rowLdayworkCount === 0 || rowIDCount === 0 || rowDepartmentCount === 0
                }
                onClick={mergeSubmit}
                variant="contained"
                color="primary"
                size="large"
              >
                수정 or 추가
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