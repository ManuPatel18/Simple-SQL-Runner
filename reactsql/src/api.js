import axios from "axios";

const API = axios.create({
  baseURL: "/api/sql"
});

export const fetchTables = () => API.get("/tables");
export const fetchTableData = (tableName) => API.get(`/table/${tableName}`);
export const executeQuery = (query) => API.post("/execute", { query });
