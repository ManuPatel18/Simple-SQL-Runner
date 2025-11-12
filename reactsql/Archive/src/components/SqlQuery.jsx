import React, { useState } from "react";
import "./SqlQuery.css";

export default function SqlQuery({ onExecute }) {
  const [query, setQuery] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (query.trim()) onExecute(query);
  };

  return (
    <div className="sql-query-container">
      <h2>Execute SQL Query</h2>
      <form onSubmit={handleSubmit}>
        <textarea
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Write your SQL query here..."
        />
        <button type="submit">Execute</button>
      </form>
    </div>
  );
}
