import React from "react";
import "./TableData.css";

export default function TableData({ columns, rows }) {
  if (!columns || !rows) return null;

  return (
    <div className="table-data-container">
      <h2>Table Data</h2>
      <table>
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={col.name}>{col.name}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((row, idx) => (
            <tr key={idx}>
              {columns.map((col) => (
                <td key={col.name}>{row[col.name]}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
