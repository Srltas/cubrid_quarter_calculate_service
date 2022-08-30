import React, {useState, useEffect} from "react";
import {
  Grid,
  Typography,
  Button,
  TextField,
} from "@material-ui/core";
import MUIDataTable from "mui-datatables";
//import { DatePicker } from "@material-ui/pickers";

// styles
import useStyles from "../login/styles";

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
	data.last_day_of_work = "-";
  });
  
  /**컬럼 초기 값 */
  var [rowValue, setRowValue] = useState("");
  var [rowNameValue, setRowNameValue] = useState("");
  var [rowFdayworkValue, setRowFdayworkValue] = useState("");
  var [rowLdayworkValue, setRowLdayworValue] = useState("");
  
  /**onRowClick 값 split 수행 */
  var valuesplit = JSON.stringify(rowValue).split(',');
  if (valuesplit.length >= 3) {
    var names = valuesplit[0];
	    names = names.replace("[","");
	    names = names.replace(/"/gi,"");
  	var f_day_work = valuesplit[1];
	  	f_day_work = f_day_work.replace(/"/gi,"");
  	var l_day_work = valuesplit[2];
	  	l_day_work = l_day_work.replace(/"/gi,"");
	  	l_day_work = l_day_work.replace("]","");
	valuesplit="";
  }
  
  /**컬럼 입력시 동적으로 값 변하게 하기 위한 훅 */
  useEffect(() => {
	setRowNameValue(names);
	setRowFdayworkValue(f_day_work);
	setRowLdayworValue(l_day_work);
  }, [names,f_day_work,l_day_work]);
  
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
  
  console.log("rowValue: " + rowValue);
  console.log("rowNameValue: " + typeof rowNameValue);
  console.log("rowFdayworkValue: " + rowFdayworkValue);
  console.log("rowLdayworkValue: " + rowLdayworkValue);
  
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
		  <Typography componet="h2" variant="h5" gutterBottom>
      		입사일
    	  </Typography>
		  <TextField
		    id="first_day_of_work"
		    variant="outlined"
		    value={rowFdayworkValue}
		    onChange={e => setRowFdayworkValue(e.target.value)}
		    margin="normal"
		    fullWidth
		  />
		  <Typography componet="h2" variant="h5" gutterBottom>
      		퇴사일
    	  </Typography>
		  <TextField
		    id="last_day_of_work"
		    variant="outlined"
		    value={rowLdayworkValue}
		    onChange={e => setRowLdayworValue(e.target.value)}
		    margin="normal"
		    fullWidth
		  />
		  
		  
		  <div className={classes.formButtons}>
            <Button
                disabled={
                  rowNameCount === 0 || rowFdayworkCount === 0 || rowLdayworkCount === 0
                }
                //onClick={() =>}
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