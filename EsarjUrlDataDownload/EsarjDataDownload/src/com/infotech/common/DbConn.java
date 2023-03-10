package com.infotech.common;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class DbConn {
  private static String datasource = null;
  private static Connection cnn = null;

  public DbConn() {}

  public static Connection getPooledConnection() {

    datasource = Utils.getParameter( "datasource" );
    if( datasource == null || datasource.length() <= 0 )
      return getPooledConnection_JDBC();

    try {
      Context initial = new InitialContext();
      DataSource ds = ( DataSource ) initial.lookup( datasource );
      Connection dsCnn = ds.getConnection();
      return ( dsCnn );

    } catch( Exception ex ) {
      ex.printStackTrace();
      Utils.showError( "Connection Error : " + ex.getMessage() );
    }

    return null;
  }

  public static Connection getPooledConnection_JDBC() {
    try {
      if( cnn != null && !cnn.isClosed() )
        return cnn;

      String driver = Utils.getParameter( "driver" );
      String url = Utils.getParameter( "url" );
      String userid = Utils.getParameter( "userid" );
      String password = Utils.getParameter( "password" );

      java.sql.Driver IfmxDrv = ( java.sql.Driver ) Class.forName( driver ).newInstance();
      cnn = java.sql
                .DriverManager
                .getConnection( url, userid, password );
    } catch( Exception ex ) {
      Utils.showError( "Connection Error : " + ex.getMessage() );
      ex.printStackTrace();
      cnn = null;
    }

    return cnn;
  }

  @SuppressWarnings( "oracle.jdeveloper.java.semantic-warning" )
  public static void waitTillConnect() {
    Connection test = null;

    while( test == null ) {
      try {
        Thread.currentThread().sleep( 10000 );
        test = getPooledConnection();
      } catch( Exception ex ) {
        ;
      }
    }
    return;
  }

  public static void closeConnection( Connection dsCnn ) {

    if( datasource == null || datasource.length() <= 0 )
      return;
    try {
      if( dsCnn == cnn )
        cnn = null;
      dsCnn.close();
    } catch( Exception ex ) {
      ex.printStackTrace();
    }
    return;
  }

  public static long getNextValueFromSequence( String sequenceName, Connection cnn ) {
    PreparedStatement pstmt = null;
    ResultSet rset = null;
    String sql = null;
    long nextSeqValue = 0;
    try {
      sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
      pstmt = cnn.prepareStatement( sql );
      pstmt.clearParameters();
      rset = pstmt.executeQuery();
      if( rset.next() ) {
        nextSeqValue = rset.getLong( 1 );
      }
      rset.close();
      pstmt.close();
    } catch( Exception ex ) {
      ex.printStackTrace();
    } finally {
      try {
        if( rset != null )
          rset.close();
      } catch( Exception e ) {
        ;
      }
      try {
        if( pstmt != null )
          pstmt.close();
      } catch( Exception e ) {
        ;
      }
    }
    return nextSeqValue;
  }
}
