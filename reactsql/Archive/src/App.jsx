import React, { useEffect, useState } from "react";
import TableList from "./components/TableList.jsx";
import TableData from "./components/TableData.jsx";
import SqlQuery from "./components/SqlQuery.jsx";
import { fetchTables, fetchTableData, executeQuery } from "./api.js";
import "./App.css";

export default function App() {
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);
  const [columns, setColumns] = useState([]);
  const [rows, setRows] = useState([]);

  useEffect(() => {
    fetchTables().then((res) => setTables(res.data));
  }, []);

  const handleSelectTable = (tableName) => {
    setSelectedTable(tableName);
    fetchTableData(tableName).then((res) => {
      setColumns(res.data.columns);
      setRows(res.data.sampleData);
    });
  };

  const handleExecuteQuery = (query) => {
    executeQuery(query).then((res) => {
      if (res.data.length && !res.data[0].error) {
        const keys = Object.keys(res.data[0]).map((k) => ({ name: k }));
        setColumns(keys);
        setRows(res.data);
      } else {
        alert(res.data[0].error || "Query failed");
      }
    });
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>SQL Runner</h1>
      <TableList tables={tables} onSelect={handleSelectTable} />
      <TableData columns={columns} rows={rows} />
      <SqlQuery onExecute={handleExecuteQuery} />
    </div>
  );
}
