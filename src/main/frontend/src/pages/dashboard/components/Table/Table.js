import React from "react";
import {
  Table,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@material-ui/core";

export default function TableComponent({ data }) {

  return (
    <Table className="mb-0">
      <TableHead>
        <TableRow>
          <TableCell>분기</TableCell>
          <TableCell>내 근로시간</TableCell>
          <TableCell>소정근로 연장</TableCell>
          <TableCell>법정근로 연장</TableCell>
          <TableCell>야간 근로시간</TableCell>
          <TableCell>휴일 근로시간</TableCell>
          <TableCell>휴일 8시간 초과시간</TableCell>
          <TableCell>사용한 휴가</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {data.map(({ 
			seq,
			quarter,
			quarterWorkTime,
			regulationWorkOverTime,
			legalWorkOverTime,
			nightWorkTime,
			holidayWorkTime,
			holiday8HOver,
			leaveTime
        }) => (
          <TableRow key={seq}>
            <TableCell>{quarter}</TableCell>
            <TableCell>
            	{parseInt(quarterWorkTime/3600)}
            	:
            	{parseInt(quarterWorkTime%3600/60) <= 10 ? "0".concat(parseInt(quarterWorkTime%3600/60)) : parseInt(quarterWorkTime%3600/60)}
            </TableCell>
            <TableCell>
            	{parseInt(regulationWorkOverTime/3600) <= 10 ? "0".concat(parseInt(regulationWorkOverTime/3600)) : parseInt(regulationWorkOverTime/3600)}
            	:
            	{parseInt(regulationWorkOverTime%3600/60) <= 10 ? "0".concat(parseInt(regulationWorkOverTime%3600/60)) : parseInt(regulationWorkOverTime%3600/60)}
            </TableCell>
            <TableCell>
            	{parseInt(legalWorkOverTime/3600) <= 10 ? "0".concat(parseInt(legalWorkOverTime/3600)) : parseInt(legalWorkOverTime/3600)}
            	:
            	{parseInt(legalWorkOverTime%3600/60) <= 10 ? "0".concat(parseInt(legalWorkOverTime%3600/60)) : parseInt(legalWorkOverTime%3600/60)}
            </TableCell>
            <TableCell>
            	{parseInt(nightWorkTime/3600) <= 10 ? "0".concat(parseInt(nightWorkTime/3600)) : parseInt(nightWorkTime/3600)}
            	:
            	{parseInt(nightWorkTime%3600/60) <= 10 ? "0".concat(parseInt(nightWorkTime%3600/60)) : parseInt(nightWorkTime%3600/60)}
            </TableCell>
            <TableCell>
            	{parseInt(holidayWorkTime/3600) <= 10 ? "0".concat(parseInt(holidayWorkTime/3600)) : parseInt(holidayWorkTime/3600)}
            	:
            	{parseInt(holidayWorkTime%3600/60) <= 10 ? "0".concat(parseInt(holidayWorkTime%3600/60)) : parseInt(holidayWorkTime%3600/60)}
            </TableCell>
            <TableCell>
            	{parseInt(holiday8HOver/3600) <= 10 ? "0".concat(parseInt(holiday8HOver/3600)) : parseInt(holiday8HOver/3600)}
            	:
            	{parseInt(holiday8HOver%3600/60) <= 10 ? "0".concat(parseInt(holiday8HOver%3600/60)) : parseInt(holiday8HOver%3600/60)}
            </TableCell>
            <TableCell>
            	{parseInt(leaveTime/3600) <= 10 ? "0".concat(parseInt(leaveTime/3600)) : parseInt(leaveTime/3600)}
            	:
            	{parseInt(leaveTime%3600/60) <= 10 ? "0".concat(parseInt(leaveTime%3600/60)) : parseInt(leaveTime%3600/60)}
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}
