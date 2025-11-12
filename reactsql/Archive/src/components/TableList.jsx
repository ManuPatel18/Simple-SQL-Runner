import React from "react";
import "./TableList.css";
export default function TableList({ tables, onSelect }) {
  return (
    <div className="table-list-container">
      <h2>Tables</h2>
      <ul>
        {tables.map((table) => (
          <li key={table}>
            <button onClick={() => onSelect(table)}>{table}</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
