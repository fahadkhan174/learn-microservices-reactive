import { Button, Stack } from "@mui/material";
import Paper from "@mui/material/Paper";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TextField from "@mui/material/TextField";
import React, { useState } from "react";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

const CustomizedTables = ({ rows }) => {
  return (
    <TableContainer component={Paper}>
      <Table aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Id</StyledTableCell>
            <StyledTableCell align="right">Description</StyledTableCell>
            <StyledTableCell align="right">Price</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <StyledTableRow key={row.id}>
              <StyledTableCell component="th" scope="row">
                {row.id}
              </StyledTableCell>
              <StyledTableCell align="right">{row.description}</StyledTableCell>
              <StyledTableCell align="right">{row.price}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

const ProductTable = () => {
  const [maxPrice, setMaxPrice] = useState(0);
  const [rows, setRows] = useState([]);

  const handleNotifyClicked = () => {
    const sse = new EventSource(
      "http://localhost:8091/product/stream/" + maxPrice
    );
    sse.onmessage = (e) => {
      setRows((rows) => [...rows, JSON.parse(e.data)]);
    };
    sse.onerror = (err) => {
      console.log(err);
      sse.close();
    };
  };

  return (
    <div>
      <h2>Product Stream Table</h2>
      <Stack>
        <Stack
          direction="row"
          justifyContent="center"
          alignItems="center"
          spacing={2}
          sx={{ m: 1 }}
        >
          <TextField
            required
            id="max-price"
            label="Max Price"
            type="number"
            onChange={(event) => setMaxPrice(event.target.value)}
          />
          <Button
            type="submit"
            onClick={handleNotifyClicked}
            color="secondary"
            size="medium"
            variant="contained"
          >
            Notify Me!
          </Button>
        </Stack>
        <Stack>
          <CustomizedTables rows={rows} />
        </Stack>
      </Stack>
    </div>
  );
};

export default ProductTable;
