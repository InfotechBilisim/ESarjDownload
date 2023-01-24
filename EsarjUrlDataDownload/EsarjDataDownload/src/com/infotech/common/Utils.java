package com.infotech.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileWriter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;

import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.sql.Timestamp;

import java.text.Normalizer;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import org.xml.sax.InputSource;

public class Utils {
  private static final String paramFile = "params";
  private static Properties paramValues = null;
    private static final int HTTP_CONNECT_TIMEOUT = 180000; //180 sn
    private static final String USER_AGENT = "Mozilla/5.0";
  public Utils() {}

  public static String getParameter( String key ) {
    if( paramValues == null )
      paramValues = loadParams( paramFile );
    if( paramValues == null )
      return null;

    return ( paramValues.getProperty( key ) );
  }

  public static String convToEnglish( String txt ) {
    if( txt == null )
      return null;

    String s = "";

    for( int i = 0; i < txt.length(); i++ ) {
      char ch = txt.charAt( i );
      switch( ch ) {
        case 0x11E:
          ch = 'G';
          break;
        case 0x11F:
          ch = 'g';
          break;
        case 0x15E:
          ch = 'S';
          break;
        case 0x15F:
          ch = 's';
          break;
        case 0x130:
          ch = 'I';
          break;
        case 0x131:
          ch = 'i';
          break;
      }
      s += ch;
    }
    return ( s );
  }

    public static String convEnglish( String str ) {
      str = Normalizer.normalize( str, Normalizer.Form.NFD );
      str = str.replaceAll( "[^\\p{ASCII}]", "" );
      str = str.replaceAll( "\\p{M}", "" );
      return str;
    }

  public static Properties loadParams( String file ) {
    Properties prop = null;

    try {
      prop = new Properties();
      ResourceBundle bundle = ResourceBundle.getBundle( file );
      Enumeration enu = bundle.getKeys();
      String key = null;
      while( enu.hasMoreElements() ) {
        key = ( String ) enu.nextElement();
        prop.put( key, bundle.getObject( key ) );
      }
    } catch( Exception ex ) {
      ex.printStackTrace();
      return null;
    }
    return ( prop );
  }

  public static String getQuery( List<NameValuePair> params ) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();
    boolean first = true;

    for( NameValuePair pair : params ) {
      if( first )
        first = false;
      else
        result.append( "&" );

      result.append( URLEncoder.encode( pair.getName(), "UTF-8" ) );
      result.append( "=" );
      result.append( URLEncoder.encode( pair.getValue(), "UTF-8" ) );
    }

    return result.toString();
  }

  public static String encodeUTF8( String txt ) {
    if( txt == null )
      return null;

    String s = "";
    for( int i = 0; i < txt.length(); i++ ) {
      int ch = txt.charAt( i );
      switch( ch ) {
        case 0xDE:
          s += "&#350;";
          break;
        case 0x15E:
          s += "&#350;";
          break;
        case 0xFE:
          s += "&#351;";
          break;
        case 0x15F:
          s += "&#351;";
          break;
        case 0xD0:
          s += "&#286;";
          break;
        case 0x11E:
          s += "&#286;";
          break;
        case 0xF0:
          s += "&#287;";
          break;
        case 0x11F:
          s += "&#287;";
          break;
        case 0xDD:
          s += "&#304;";
          break;
        case 0x130:
          s += "&#304;";
          break;
        case 0xFD:
          s += "&#305;";
          break;
        case 0x131:
          s += "&#305;";
          break;
        default:
          s += ( char ) ch;
          break;
      }
    }
    return s;
  }

  public static void showText( String text ) {
    System.out.println( text );
    Utils.logInfo( text );
    return;
  }

  public static void showError( String text ) {
    System.err.println( text );
    Utils.logInfo( text );
    return;
  }

  public static String convToAscii( String txt ) {
    if( txt == null )
      return null;

    String s = "";
    for( int i = 0; i < txt.length(); i++ ) {
      char ch = txt.charAt( i );
      switch( ch ) {
        case 0x07E:
          s += "~~";
          break;
        case 0x020:
          s += "~b";
          break;
        case 0x0C7:
          s += "~C";
          break;
        case 0x0E7:
          s += "~c";
          break;
        case 0x11E:
          s += "~G";
          break;
        case 0x11F:
          s += "~g";
          break;
        case 0x0D0:
          s += "~G";
          break;
        case 0x0F0:
          s += "~g";
          break;
        case 0x15E:
          s += "~S";
          break;
        case 0x15F:
          s += "~s";
          break;
        case 0x0DE:
          s += "~S";
          break;
        case 0x0FE:
          s += "~s";
          break;
        case 0x0EE:
          s += "~s";
          break;
        case 0x130:
          s += "~I";
          break;
        case 0x131:
          s += "~i";
          break;
        case 0x0DD:
          s += "~I";
          break;
        case 0x0FD:
          s += "~i";
          break;
        case 0x0DC:
          s += "~U";
          break;
        case 0x0FC:
          s += "~u";
          break;
        case 0x0D6:
          s += "~O";
          break;
        case 0x0F6:
          s += "~o";
          break;
        default:
          s += ch;
          break;
      }
    }
    return ( s );
  }

  public static String convToTurkish( String txt ) {
    if( txt == null )
      return null;

    String s = "";

    for( int i = 0; i < txt.length(); i++ ) {
      char ch = txt.charAt( i );
      if( ch == '~' ) {
        i++;
        ch = txt.charAt( i );
        switch( ch ) {
          case '~':
            ch = '~';
            break;
          case 'b':
            ch = ' ';
            break;
          case 'C':
            ch = ( char ) 0x0C7;
            break;
          case 'c':
            ch = ( char ) 0x0E7;
            break;
          case 'G':
            ch = ( char ) 0x11E;
            break;
          case 'g':
            ch = ( char ) 0x11F;
            break;
          case 'S':
            ch = ( char ) 0x15E;
            break;
          case 's':
            ch = ( char ) 0x15F;
            break;
          case 'I':
            ch = ( char ) 0x130;
            break;
          case 'i':
            ch = ( char ) 0x131;
            break;
          case 'U':
            ch = ( char ) 0x0DC;
            break;
          case 'u':
            ch = ( char ) 0x0FC;
            break;
          case 'O':
            ch = ( char ) 0x0D6;
            break;
          case 'o':
            ch = ( char ) 0x0F6;
            break;
        }
      }
      s += ch;
    }
    return ( s );
  }

  public static String convUtf8ToTurkish( String txt ) {
    if( txt == null )
      return null;

    String s = "";

    for( int i = 0; i < txt.length(); i++ ) {
      int ch = txt.charAt( i );
      switch( ch ) {
        case 0xDE:
          ch = 0x15E;
          break;
        case 0xFE:
          ch = 0x15F;
          break;
        case 0xEE:
          ch = 0x15F;
          break;
        case 0xDD:
          ch = 0x130;
          break;
        case 0xFD:
          ch = 0x131;
          break;
        case 0xD0:
          ch = 0x11E;
          break;
        case 0xF0:
          ch = 0x11F;
          break;
        case 0xC3:
          i++;
          ch = txt.charAt( i );
          switch( ch ) {
            case 0x0087:
              ch = 0x0C7;
              break;
            case 0x00A7:
              ch = 0x0E7;
              break;
            case 0x009C:
              ch = 0x0DC;
              break;
            case 0x00BC:
              ch = 0x0FC;
              break;
            case 0x0096:
              ch = 0x0D6;
              break;
            case 0x00B6:
              ch = 0x0F6;
              break;
            case 0x2021:
              ch = 0x0C7;
              break;
            case 0x2013:
              ch = 0x0D6;
              break;
            case 0x0153:
              ch = 0x0DC;
              break;
          }
          break;
        case 0xC4:
          i++;
          ch = txt.charAt( i );
          switch( ch ) {
            case 0x009E:
              ch = 0x11E;
              break;
            case 0x009F:
              ch = 0x11F;
              break;
            case 0x00B0:
              ch = 0x130;
              break;
            case 0x00B1:
              ch = 0x131;
              break;
            case 0x0178:
              ch = 0x11F;
              break;
          }
          break;
        case 0xC5:
          i++;
          ch = txt.charAt( i );
          switch( ch ) {
            case 0x009E:
              ch = 0x15E;
              break;
            case 0x009F:
              ch = 0x15F;
              break;
            case 0x017E:
              ch = 0x15E;
              break;
            case 0x0178:
              ch = 0x15F;
              break;
          }
          break;
        default:
          break;
      }
      s += ( char ) ch;
    }
    return ( s );
  }

  public static String encodeEscape( String txt ) {
    if( txt == null )
      return null;

    String s = "";
    for( int i = 0; i < txt.length(); i++ ) {
      int ch = txt.charAt( i );
      switch( ch ) {
        case 0xDE:
          s += "%u015E";
          break;
        case 0x15E:
          s += "%u015E";
          break;
        case 0xFE:
          s += "%u015F";
          break;
        case 0x15F:
          s += "%u015F";
          break;
        case 0xD0:
          s += "%u011E";
          break;
        case 0x11E:
          s += "%u011E";
          break;
        case 0xF0:
          s += "%u011F";
          break;
        case 0x11F:
          s += "%u011F";
          break;
        case 0xDD:
          s += "%u0130";
          break;
        case 0x130:
          s += "%u0130";
          break;
        case 0xFD:
          s += "%u0131";
          break;
        case 0x131:
          s += "%u0131";
          break;
        default:
          s += ( char ) ch;
          break;
      }
    }
    return s;
  }

  public static String decodeEscape( String txt ) {
    if( txt == null )
      return null;

    String s = "";
    for( int i = 0; i < txt.length(); i++ ) {
      int ch = txt.charAt( i );
      if( ch != '%' ) {
        s += ( char ) ch;
        continue;
      }

      if( i + 3 > txt.length() ) {
        s += ( char ) ch;
        continue;
      }

      String chk = txt.substring( i, i + 3 );
      if( chk.equals( "%C7" ) ) {
        ch = 0x0C7;
        i += 2;
      } else if( chk.equals( "%E7" ) ) {
        ch = 0x0E7;
        i += 2;
      } else if( chk.equals( "%DC" ) ) {
        ch = 0x0DC;
        i += 2;
      } else if( chk.equals( "%FC" ) ) {
        ch = 0x0FC;
        i += 2;
      } else if( chk.equals( "%D6" ) ) {
        ch = 0x0D6;
        i += 2;
      } else if( chk.equals( "%F6" ) ) {
        ch = 0x0F6;
        i += 2;
      } else {
        if( i + 4 > txt.length() ) {
          s += ( char ) ch;
          continue;
        }

        chk = txt.substring( i, i + 4 );
        if( chk.equals( "%xDD" ) ) {
          ch = 0x130;
          i += 3;
        } else {
          if( i + 6 > txt.length() ) {
            s += ( char ) ch;
            continue;
          }

          chk = txt.substring( i, i + 6 );
          if( chk.equals( "%u015E" ) ) {
            ch = 0x15E;
            i += 5;
          } else if( chk.equals( "%u015F" ) ) {
            ch = 0x15F;
            i += 5;
          } else if( chk.equals( "%u011E" ) ) {
            ch = 0x11E;
            i += 5;
          } else if( chk.equals( "%u011F" ) ) {
            ch = 0x11F;
            i += 5;
          } else if( chk.equals( "%u0130" ) ) {
            ch = 0x130;
            i += 5;
          } else if( chk.equals( "%u0131" ) ) {
            ch = 0x131;
            i += 5;
          } else
            ;

        }
      }

      s += ( char ) ch;
    }
    return s;
  }

  public static double calcDistance( double x1, double y1, double x2, double y2 ) {
    double dx, dy, dist;

    dx = ( x1 - x2 );
    dy = ( y1 - y2 );
    dist = Math.sqrt( dx * dx + dy * dy );
    return ( dist );
  }

  public static boolean isCoorsEqual( double x1, double y1, double x2, double y2 ) {
    double dx, dy, dist;

    dx = ( x1 - x2 );
    dy = ( y1 - y2 );
    dist = Math.sqrt( dx * dx + dy * dy );
    if( dist < 0.00001 )
      return true;

    return false;
  }

  public static String formatNumber( int num, int len ) {
    String s = "";
    for( int i = 0; i < len; i++ )
      s += "0";
    s += num;
    s = s.substring( s.length() - len );
    return ( s );
  }

  public static String formatNumber( long num, int len ) {
    String s = "";
    for( int i = 0; i < len; i++ )
      s += "0";
    s += num;
    s = s.substring( s.length() - len );
    return ( s );
  }

  public static String formatNumber( double num, int totlen, int len ) {
    String n = "";
    if( num >= 1 )
      n += num;
    else {
      num += 1;
      n += num;
      n = "0" + n.substring( 1 );
    }
    String s = "";
    for( int i = 0; i < totlen - len; i++ )
      s += "0";
    s += n;
    for( int i = 0; i < len; i++ )
      s += "0";
    int pos = s.indexOf( '.' );
    s = s.substring( pos - ( totlen - 1 - len ), pos + 1 + len );
    return ( s );
  }

  public static String formatNumeric( double num, int len ) {
    String s = "";
    String f = "0.";
    for( int i = 0; i < len; i++ ) {
      s += "0";
      f += "0";
    }
    f += "5";
    num += Double.parseDouble( f );
    s = num + s;
    int pos = s.indexOf( '.' );
    s = s.substring( 0, pos + 1 + len );
    return ( s );
  }

  public static boolean isNumber( String txt ) {
    if( txt == null || txt.length() <= 0 )
      return false;

    for( int i = 0; i < txt.length(); i++ )
      if( !Character.isDigit( txt.charAt( i ) ) )
        return false;

    return true;
  }

  public static String padLeftString( String txt, int len, char ch ) {
    if( txt == null )
      txt = "";
    String s = "";
    for( int i = 0; i < len; i++ )
      s += String.valueOf( ch );
    s += txt;
    s = s.substring( s.length() - len );
    return ( s );
  }

  public static String padRightString( String txt, int len, char ch ) {
    if( txt == null )
      txt = "";
    String s = txt;
    for( int i = 0; i < len - txt.length(); i++ )
      s += String.valueOf( ch );
    return ( s );
  }

  public static String getCurrentDate() {
    Calendar c = new GregorianCalendar();
    String dt = formatNumber( c.get( Calendar.YEAR ), 4 ) + formatNumber( c.get( Calendar.MONTH ) + 1, 2 ) +formatNumber( c.get( Calendar.DAY_OF_MONTH ), 2 );
    return dt;
  }

  public static String getCurrentDateNextYear() {
    Calendar c = new GregorianCalendar();
    String dt = formatNumber( c.get( Calendar.YEAR ) + 1, 4 ) + "-" + formatNumber( c.get( Calendar.MONTH ) + 1, 2 ) + "-" + formatNumber( c.get( Calendar.DAY_OF_MONTH ), 2 );
    return dt;
  }

  public static String getCurrentTimeStamp() {
    Timestamp ts = new Timestamp( System.currentTimeMillis() );
    return ( ts.toString() );
  }

  public static Timestamp toTimestamp( String datetime ) {
    Calendar c = null;

    try {
      int year = Integer.parseInt( datetime.substring( 0, 4 ) );
      if( year == 0 )
        year = 2000;
      int month = Integer.parseInt( datetime.substring( 5, 7 ) );
      if( month == 0 )
        month = 1;
      int day = Integer.parseInt( datetime.substring( 8, 10 ) );
      if( day == 0 )
        day = 1;
      int hour = 0;
      int minute = 0;
      if( datetime.length() >= 16 ) {
        hour = Integer.parseInt( datetime.substring( 11, 13 ) );
        minute = Integer.parseInt( datetime.substring( 14, 16 ) );
      }
      int second = 0;
      if( datetime.length() >= 19 ) {
        second = Integer.parseInt( datetime.substring( 17, 19 ) );
      }
      c = new GregorianCalendar( year, ( month - 1 ), day, hour, minute, second );
    } catch( Exception ex ) {
      ex.printStackTrace();
    }
    return ( new Timestamp( c.getTime().getTime() ) );
  }

  public synchronized static void logInfo( String txt ) {
    String fullPath = Utils.getParameter( "logfileprefix" ) + "_" + Utils.getCurrentDate().replaceAll( "-", "" ) + ".log";
    try {
      BufferedWriter out = new BufferedWriter( new FileWriter( fullPath, true ) );
      out.write( "\"" + getCurrentTimeStamp() + "\",\"" + txt + "\"" );
      out.newLine();
      out.close();
    } catch( Exception ex ) {
      ex.printStackTrace();
    }
    return;
  }

  public static String toUpperCase( String txt ) {
    String s = "";
    for( int i = 0; i < txt.length(); i++ ) {
      int ch = txt.charAt( i );
      switch( ch ) {
        case 0x0C7:
          break;
        case 0x0E7:
          ch = 0x0C7;
          break;

        case 0x0DE:
          ch = 0x15E;
          break;
        case 0x0FE:
          ch = 0x15E;
          break;
        case 0x0EE:
          ch = 0x15E;
          break;
        case 0x15E:
          break;
        case 0x15F:
          ch = 0x15E;
          break;

        case 0x0D0:
          ch = 0x11E;
          break;
        case 0x0F0:
          ch = 0x11E;
          break;
        case 0x11E:
          break;
        case 0x11F:
          ch = 0x11E;
          break;

        case 0x0DD:
          ch = 0x130;
          break;
        case 0x0FD:
          ch = 0x049;
          break;
        case 0x130:
          break;
        case 0x131:
          ch = 0x049;
          break;
        case 0x069:
          ch = 0x130;
          break;

        case 0x0D6:
          break;
        case 0x0F6:
          ch = 0x0D6;
          break;

        case 0x0DC:
          break;
        case 0x0FC:
          ch = 0x0DC;
          break;

        default:
          if( Character.isLowerCase( ch ) )
            ch = Character.toUpperCase( ch );
          break;
      }
      s += ( char ) ch;
    }
    return s;
  }

  public static String[] splitString( String splitStr, String delim ) {
    StringTokenizer toker;
    String[] result;
    int count, i;

    toker = new StringTokenizer( splitStr, delim );
    count = toker.countTokens();
    result = new String[ count ];
    for( i = 0; i < count; ++i ) {
      try {
        result[ i ] = toker.nextToken();
      } catch( NoSuchElementException ex ) {
        result = null;
        break;
      }

    }

    return result;
  }

  public static Document strToDocument( String xmlData ) {

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      InputSource is = new InputSource( new StringReader( xmlData ) );
      Document d = builder.parse( is );
      return d;

    } catch( Exception ex ) {
      ex.printStackTrace();
    }
    return null;
  }

  public static String getCurrentDateTime() {
    Calendar c = new GregorianCalendar();
    String dt;

    dt = formatNumber( c.get( Calendar.YEAR ), 4 ) + "-" + formatNumber( c.get( Calendar.MONTH ) + 1, 2 ) + "-" + formatNumber( c.get( Calendar.DAY_OF_MONTH ), 2 ) + " " + formatNumber( c.get( Calendar.HOUR_OF_DAY ), 2 ) + ":" + formatNumber( c.get( Calendar.MINUTE ), 2 ) + ":" + formatNumber( c.get( Calendar.SECOND ), 2 );
    return ( dt );
  }

  public static String formatDateTime( Calendar c ) {
    String dt;

    dt = formatNumber( c.get( Calendar.YEAR ), 4 ) + "-" + formatNumber( c.get( Calendar.MONTH ) + 1, 2 ) + "-" + formatNumber( c.get( Calendar.DAY_OF_MONTH ), 2 ) + " " + formatNumber( c.get( Calendar.HOUR_OF_DAY ), 2 ) + ":" + formatNumber( c.get( Calendar.MINUTE ), 2 ) + ":" + formatNumber( c.get( Calendar.SECOND ), 2 );
    return ( dt );
  }
  
    public static double convertStringToDoubleValue(String functionName, String variable) {
        double value = 0.00;
        try {
            if (!isStringDataNull(variable)) {
                variable = variable.replace(",", ".");
                value = Double.parseDouble(variable);
            }
        } catch (Exception e) {
            Utils.showError(functionName + " getStringToDoubleValue variable(" + variable + "): " + e.getMessage());
        }
        return value;
    }
    
    public static JSONArray getJSONArrayValueFromJSONObject(JSONObject object, String variable, boolean option) {
        JSONArray array = null;
        try {
            array = object.getJSONArray(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getJSONArrayValueFromJSONObject.getJSONObject(" + variable + "): " + object + " " + e.getMessage());
            }
        }
        return array;
    }

    public static JSONObject getJSONObjectValueFromJSONObject(JSONObject object, String variable, boolean option) {
        JSONObject obj = null;
        try {
            obj = object.getJSONObject(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getJSONObjectValueFromJSONObject.getJSONObject(" + variable + "): " + object + " " + e.getMessage());
            }
        }
        return obj;
    }

    public static String getStringValueFromJSONObject(JSONObject object, String variable, boolean option) {
        String value = "";
        try {
            value = object.getString(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getStringValueFromJSONObject variable(" + variable + "): " + object + " " + e.getMessage());
            }
        }

        return value;
    }

    public static double getDoubleValueFromJSONObject(JSONObject object, String variable, boolean option) {
        double value = 0.00;
        try {
            value = object.getDouble(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getDoubleValueFromJSONObject variable(" + variable + "): " + object + " " + e.getMessage());
            }
        }

        return value;
    }

    public static boolean getBooleanValueFromJSONObject(JSONObject object, String variable, boolean option) {
        boolean value = false;
        try {
            value = object.getBoolean(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getBooleanValueFromJSONObject variable: " + variable + " " + e.getMessage());
            }
        }

        return value;
    }

    public static int getIntValueFromJSONObject(JSONObject object, String variable, boolean option) {
        int value = 0;
        try {
            value = object.getInt(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getIntValueFromJSONObject variable(" + variable + "): " + object + " " + e.getMessage());
            }
        }

        return value;
    }

    public static long getLongValueFromJSONObject(JSONObject object, String variable, boolean option) {
        long value = 0;
        try {
            value = object.getLong(variable);
        } catch (Exception e) {
            if (!option) {
                Utils.showError("getLongValueFromJSONObject variable(" + variable + "): " + object + " " + e.getMessage());
            }
        }

        return value;
    }

    public static JSONArray getJSONArrayValue(JSONObject object, String variable) {
        JSONArray array = null;
        try {
            array = object.getJSONArray(variable);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showError("object.getJSONObject(" + variable + "): " + object + " " + e.getMessage());
        }
        return array;
    }

    public static JSONObject getJSONObjectValue(JSONObject object, String variable) {
        JSONObject obj = null;
        try {
            obj = object.getJSONObject(variable);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showError("object.getJSONObject(" + variable + "): " + object + " " + e.getMessage());
        }
        return obj;
    }

    public static String getStringValue(JSONObject object, String variable) {
        String value = "";
        try {
            value = object.getString(variable);
            value = value.trim();
        } catch (Exception e) {
            Utils.showError("getStringValue variable(" + variable + "): " + object + " " + e.getMessage());
        }

        return value;
    }

    public static boolean getBooleanValue(JSONObject object, String variable) {
        boolean value = false;
        try {
            value = object.getBoolean(variable);
        } catch (Exception e) {
            Utils.showError("getBoolean variable: " + variable + " " + e.getMessage());
        }

        return value;
    }

    public static boolean isStringDataNull(String txt) {
        if (txt == null || txt.trim().length() < 1 || txt.trim().equalsIgnoreCase(""))
            return true;
        return false;
    }

    public static String commonPostRequest(String requestUrl, String params, boolean authorization, String authorizationParam, boolean isContentTypeJson) {
        String line = null;
        BufferedReader inpBuffer = null;
        StringBuilder respDataBuilder = null;
        HttpURLConnection httpClient = null;
        InputStreamReader inpStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inpStream = null;
        int responseCode = 0;
        try {
            Utils.showText("POST requestURI : " + requestUrl + " params:" + params);
            httpClient = (HttpURLConnection) new URL(requestUrl).openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("User-Agent", USER_AGENT);
            httpClient.setRequestProperty("charset", StandardCharsets.UTF_8.toString());

            if (isContentTypeJson) {
                httpClient.setRequestProperty("Content-Type", "application/json");
            } else {
                httpClient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            httpClient.setRequestProperty("Accept", "application/json");
            if (authorization) {
                httpClient.setRequestProperty("Authorization", authorizationParam);
            }
            httpClient.setUseCaches(false);
            httpClient.setDoOutput(true);
            httpClient.setDoInput(true);
            httpClient.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            httpClient.setReadTimeout(HTTP_CONNECT_TIMEOUT);

            outputStreamWriter = new OutputStreamWriter(httpClient.getOutputStream());
            outputStreamWriter.write(params);
            outputStreamWriter.flush();
            responseCode = httpClient.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                respDataBuilder = new StringBuilder();
                inpStream = httpClient.getInputStream();
                inpStreamReader = new InputStreamReader(httpClient.getInputStream(), StandardCharsets.UTF_8);
                inpBuffer = new BufferedReader(inpStreamReader);
                
                while ((line = inpBuffer.readLine()) != null) {
                    respDataBuilder.append(line.toString());
                }

            } else {
                Utils.showError("responseCode:" + responseCode + " commonPostRequest POST request not worked: " + requestUrl);
            }
        } catch (Exception e) {
            Utils.showError("commonPostRequest: " + requestUrl + " params: " + params + e.getMessage());
        } finally {
            closeHttpConn(httpClient, inpStream, null, inpBuffer, outputStreamWriter);
        }
        return respDataBuilder.toString();
    }


    public static String commonGetRequest(String requestUrl, boolean authorization, String authorizationParam, boolean isContentTypeJson) {
        HttpURLConnection connection = null;
        String line = null;
        int responseCode = 0;
        BufferedReader inpBuffer = null;
        StringBuilder respDataBuilder = null;
        InputStreamReader inpStreamReader = null;
        try {
            HostNamesTrust.trustAllHostnames();
            Utils.showText("GET requestURI : " + requestUrl);
            URL lurl = new URL(requestUrl);
            connection = (HttpURLConnection) lurl.openConnection();
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            if (isContentTypeJson) {
                connection.setRequestProperty("Content-Type", "application/json");
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("charset", StandardCharsets.UTF_8.toString());
            connection.setRequestProperty("Content-Length", "" + Integer.toString("".getBytes().length));
            if (authorization) {
                connection.setRequestProperty("Authorization", authorizationParam);
            }
            connection.setUseCaches(false);
            connection.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
            connection.setReadTimeout(HTTP_CONNECT_TIMEOUT);

            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                respDataBuilder = new StringBuilder();
                inpStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                inpBuffer = new BufferedReader(inpStreamReader);
                while ((line = inpBuffer.readLine()) != null) {
                    respDataBuilder.append(line.toString());
                }

            } else {
                Utils.showError("responseCode:" + responseCode + " commonGetRequest GET request not worked: " + requestUrl);
            }
            

        } catch (Exception e) {
            Utils.showError("commonGetRequest: " + requestUrl + " " + e.getMessage());
            return null;
        } finally {
            closeHttpConn(connection, inpStreamReader, null, inpBuffer);
        }
        return respDataBuilder.toString();
    }


    public static void closeHttpConn(HttpURLConnection connection, InputStreamReader inp, PrintWriter out, BufferedReader br) {
        closeHttpConnection(connection, inp, out, br);
    }

    public static void closeHttpConn(HttpURLConnection connection, InputStreamReader inp, PrintWriter out, BufferedReader br, OutputStreamWriter writer) {
        closeHttpConnection(connection, inp, out, br);
        try {
            if (writer != null) {
                writer.close();
            }
            writer = null;
        } catch (Exception ex) {
            Utils.showError("closeHttpConn writer " + ex.getMessage());
            ex.fillInStackTrace();
        }
    }
    public static void closeHttpConn(HttpURLConnection connection, InputStream inp, PrintWriter out, BufferedReader br, OutputStreamWriter writer) {
        closeHttpConnection(connection, inp, out, br);
        try {
            if (writer != null) {
                writer.close();
            }
            writer = null;
        } catch (Exception ex) {
            Utils.showError("closeHttpConn writer " + ex.getMessage());
            ex.fillInStackTrace();
        }
    }
    private static void closeHttpConnection(HttpURLConnection connection, InputStream inp, PrintWriter out, BufferedReader br) {
        try {
            if (out != null) {
                out.close();
            }
            out = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn out" + e.getMessage());
            e.fillInStackTrace();
        }
        try {
            if (inp != null) {
                inp.close();
            }
            inp = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn in" + e.getMessage());
            e.fillInStackTrace();
        }
        try {
            if (br != null) {
                br.close();
            }
            br = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn InputStream br" + e.getMessage());
            e.fillInStackTrace();
        }
        try {
            if (connection != null) {
                connection.disconnect();
            }
            connection = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn connection" + e.getMessage());
            e.fillInStackTrace();
        }
    }

    private static void closeHttpConnection(HttpURLConnection connection, InputStreamReader inp, PrintWriter out, BufferedReader br) {
        try {
            if (out != null) {
                out.close();
            }
            out = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn out" + e.getMessage());
            e.fillInStackTrace();
        }
        try {
            if (inp != null) {
                inp.close();
            }
            inp = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn in" + e.getMessage());
            e.fillInStackTrace();
        }
        try {
            if (br != null) {
                br.close();
            }
            br = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn br" + e.getMessage());
            e.fillInStackTrace();
        }
        try {
            if (connection != null) {
                connection.disconnect();
            }
            connection = null;
        } catch (Exception e) {
            Utils.showError("closeHttpConn connection" + e.getMessage());
            e.fillInStackTrace();
        }
    }

    
}
