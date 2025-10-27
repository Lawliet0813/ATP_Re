"""
Database Manager for ATP system.

Provides connection pooling, CRUD operations, and transaction management
for PostgreSQL database.
"""

import os
from typing import Optional, List, Dict, Any, Tuple
from contextlib import contextmanager
import psycopg2
from psycopg2 import pool, sql
from psycopg2.extras import RealDictCursor
import logging

logger = logging.getLogger(__name__)


class DatabaseManager:
    """
    Manages database connections and operations for the ATP system.
    
    Provides connection pooling, CRUD operations, transaction support,
    and error handling for PostgreSQL database operations.
    """
    
    def __init__(
        self,
        host: str,
        port: int,
        database: str,
        user: str,
        password: str,
        min_connections: int = 1,
        max_connections: int = 10
    ):
        """
        Initialize the database manager with connection parameters.
        
        Args:
            host: Database host address
            port: Database port number
            database: Database name
            user: Database user
            password: Database password
            min_connections: Minimum number of connections in pool
            max_connections: Maximum number of connections in pool
        """
        self.host = host
        self.port = port
        self.database = database
        self.user = user
        self.password = password
        
        # Initialize connection pool
        try:
            self._pool = psycopg2.pool.ThreadedConnectionPool(
                min_connections,
                max_connections,
                host=host,
                port=port,
                database=database,
                user=user,
                password=password
            )
            logger.info(f"Database connection pool created: {database}@{host}:{port}")
        except Exception as e:
            logger.error(f"Failed to create connection pool: {e}")
            raise
    
    @classmethod
    def from_env(cls) -> "DatabaseManager":
        """
        Create DatabaseManager from environment variables.
        
        Expected environment variables:
            - DB_HOST (default: localhost)
            - DB_PORT (default: 5432)
            - DB_NAME (default: atp_re)
            - DB_USER (default: postgres)
            - DB_PASSWORD (required)
            
        Returns:
            DatabaseManager instance
        """
        return cls(
            host=os.getenv("DB_HOST", "localhost"),
            port=int(os.getenv("DB_PORT", "5432")),
            database=os.getenv("DB_NAME", "atp_re"),
            user=os.getenv("DB_USER", "postgres"),
            password=os.getenv("DB_PASSWORD", ""),
            min_connections=int(os.getenv("DB_MIN_CONNECTIONS", "1")),
            max_connections=int(os.getenv("DB_MAX_CONNECTIONS", "10"))
        )
    
    @contextmanager
    def get_connection(self):
        """
        Get a database connection from the pool (context manager).
        
        Yields:
            Database connection
            
        Example:
            with db.get_connection() as conn:
                cursor = conn.cursor()
                cursor.execute("SELECT * FROM stations")
        """
        conn = None
        try:
            conn = self._pool.getconn()
            yield conn
        except Exception as e:
            if conn:
                conn.rollback()
            logger.error(f"Database operation error: {e}")
            raise
        finally:
            if conn:
                self._pool.putconn(conn)
    
    @contextmanager
    def get_cursor(self, dict_cursor: bool = True):
        """
        Get a database cursor (context manager).
        
        Args:
            dict_cursor: If True, returns RealDictCursor for dict-like results
            
        Yields:
            Database cursor
            
        Example:
            with db.get_cursor() as cursor:
                cursor.execute("SELECT * FROM stations")
                results = cursor.fetchall()
        """
        with self.get_connection() as conn:
            cursor_factory = RealDictCursor if dict_cursor else None
            cursor = conn.cursor(cursor_factory=cursor_factory)
            try:
                yield cursor
                conn.commit()
            except Exception as e:
                conn.rollback()
                logger.error(f"Cursor operation error: {e}")
                raise
            finally:
                cursor.close()
    
    def execute_query(
        self,
        query: str,
        params: Optional[Tuple] = None,
        fetch: bool = True
    ) -> Optional[List[Dict[str, Any]]]:
        """
        Execute a SQL query and optionally fetch results.
        
        Args:
            query: SQL query string
            params: Query parameters (for parameterized queries)
            fetch: Whether to fetch and return results
            
        Returns:
            List of result dictionaries if fetch=True, None otherwise
        """
        with self.get_cursor() as cursor:
            cursor.execute(query, params)
            if fetch:
                return cursor.fetchall()
            return None
    
    def execute_many(
        self,
        query: str,
        params_list: List[Tuple]
    ) -> int:
        """
        Execute a SQL query multiple times with different parameters.
        
        Args:
            query: SQL query string
            params_list: List of parameter tuples
            
        Returns:
            Number of rows affected
        """
        with self.get_cursor(dict_cursor=False) as cursor:
            cursor.executemany(query, params_list)
            return cursor.rowcount
    
    def insert(
        self,
        table: str,
        data: Dict[str, Any],
        returning: str = "id"
    ) -> Optional[Any]:
        """
        Insert a row into a table.
        
        Args:
            table: Table name
            data: Dictionary of column names and values
            returning: Column to return (typically "id")
            
        Returns:
            Value of the returning column
        """
        columns = list(data.keys())
        values = list(data.values())
        
        query = sql.SQL(
            "INSERT INTO {} ({}) VALUES ({}) RETURNING {}"
        ).format(
            sql.Identifier(table),
            sql.SQL(', ').join(map(sql.Identifier, columns)),
            sql.SQL(', ').join(sql.Placeholder() * len(values)),
            sql.Identifier(returning)
        )
        
        with self.get_cursor(dict_cursor=False) as cursor:
            cursor.execute(query, values)
            return cursor.fetchone()[0] if returning else None
    
    def insert_many(
        self,
        table: str,
        data_list: List[Dict[str, Any]]
    ) -> int:
        """
        Insert multiple rows into a table.
        
        Args:
            table: Table name
            data_list: List of dictionaries with column names and values
            
        Returns:
            Number of rows inserted
        """
        if not data_list:
            return 0
        
        columns = list(data_list[0].keys())
        values_list = [tuple(d[col] for col in columns) for d in data_list]
        
        query = sql.SQL(
            "INSERT INTO {} ({}) VALUES ({})"
        ).format(
            sql.Identifier(table),
            sql.SQL(', ').join(map(sql.Identifier, columns)),
            sql.SQL(', ').join(sql.Placeholder() * len(columns))
        )
        
        return self.execute_many(query.as_string(self._pool.getconn()), values_list)
    
    def update(
        self,
        table: str,
        data: Dict[str, Any],
        where: Dict[str, Any]
    ) -> int:
        """
        Update rows in a table.
        
        Args:
            table: Table name
            data: Dictionary of columns to update
            where: Dictionary of WHERE conditions
            
        Returns:
            Number of rows updated
        """
        set_clause = sql.SQL(', ').join(
            sql.SQL("{} = {}").format(sql.Identifier(k), sql.Placeholder())
            for k in data.keys()
        )
        
        where_clause = sql.SQL(' AND ').join(
            sql.SQL("{} = {}").format(sql.Identifier(k), sql.Placeholder())
            for k in where.keys()
        )
        
        query = sql.SQL(
            "UPDATE {} SET {} WHERE {}"
        ).format(
            sql.Identifier(table),
            set_clause,
            where_clause
        )
        
        values = list(data.values()) + list(where.values())
        
        with self.get_cursor(dict_cursor=False) as cursor:
            cursor.execute(query, values)
            return cursor.rowcount
    
    def delete(
        self,
        table: str,
        where: Dict[str, Any]
    ) -> int:
        """
        Delete rows from a table.
        
        Args:
            table: Table name
            where: Dictionary of WHERE conditions
            
        Returns:
            Number of rows deleted
        """
        where_clause = sql.SQL(' AND ').join(
            sql.SQL("{} = {}").format(sql.Identifier(k), sql.Placeholder())
            for k in where.keys()
        )
        
        query = sql.SQL(
            "DELETE FROM {} WHERE {}"
        ).format(
            sql.Identifier(table),
            where_clause
        )
        
        with self.get_cursor(dict_cursor=False) as cursor:
            cursor.execute(query, list(where.values()))
            return cursor.rowcount
    
    def select(
        self,
        table: str,
        columns: Optional[List[str]] = None,
        where: Optional[Dict[str, Any]] = None,
        order_by: Optional[List[str]] = None,
        limit: Optional[int] = None,
        offset: Optional[int] = None
    ) -> List[Dict[str, Any]]:
        """
        Select rows from a table.
        
        Args:
            table: Table name
            columns: List of columns to select (None for all)
            where: Dictionary of WHERE conditions
            order_by: List of columns to order by
            limit: Maximum number of rows to return
            offset: Number of rows to skip
            
        Returns:
            List of result dictionaries
        """
        # Build SELECT clause
        if columns:
            select_clause = sql.SQL(', ').join(map(sql.Identifier, columns))
        else:
            select_clause = sql.SQL('*')
        
        query_parts = [
            sql.SQL("SELECT"),
            select_clause,
            sql.SQL("FROM"),
            sql.Identifier(table)
        ]
        
        values = []
        
        # Add WHERE clause
        if where:
            where_clause = sql.SQL(' AND ').join(
                sql.SQL("{} = {}").format(sql.Identifier(k), sql.Placeholder())
                for k in where.keys()
            )
            query_parts.extend([sql.SQL("WHERE"), where_clause])
            values.extend(where.values())
        
        # Add ORDER BY clause
        if order_by:
            order_clause = sql.SQL(', ').join(map(sql.Identifier, order_by))
            query_parts.extend([sql.SQL("ORDER BY"), order_clause])
        
        # Add LIMIT and OFFSET
        if limit:
            query_parts.extend([sql.SQL("LIMIT"), sql.Literal(limit)])
        if offset:
            query_parts.extend([sql.SQL("OFFSET"), sql.Literal(offset)])
        
        query = sql.SQL(' ').join(query_parts)
        
        return self.execute_query(query.as_string(self._pool.getconn()), tuple(values))
    
    def initialize_schema(self, schema_file: str) -> None:
        """
        Initialize database schema from a SQL file.
        
        Args:
            schema_file: Path to SQL schema file
        """
        with open(schema_file, 'r') as f:
            schema_sql = f.read()
        
        with self.get_connection() as conn:
            cursor = conn.cursor()
            cursor.execute(schema_sql)
            conn.commit()
            cursor.close()
        
        logger.info(f"Database schema initialized from {schema_file}")
    
    def close(self) -> None:
        """Close all connections in the pool."""
        if self._pool:
            self._pool.closeall()
            logger.info("Database connection pool closed")
