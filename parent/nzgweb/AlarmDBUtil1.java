package com.iwhalecloud.oss.icm.collect.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.iwhalecloud.oss.icm.collect.vo.AlarmExpire;
import com.iwhalecloud.oss.icm.util.DatasourceManager;
import com.iwhalecloud.oss.icm.util.EnvironsUtil;

public final class AlarmDBUtil1 {
    private static final Logger logger = Logger.getLogger(AlarmDBUtil1.class);

    public static void main(String[] args) {
        try {
//            List<String> list = new ArrayList<>();
//            list.add("RME_EQP");
//            list.add("RME_COMPUTER_EQP");
//            list.add("RME_SWITCH_EQP");
//            list.add("RME_AUXILIARY_COMPONENT");
//            String sql = "select * from ";
//            Connection connection = getCon();
//            PreparedStatement  ps=connection.prepareStatement(" delete from rme_eqp where  eqp_id = '437XR1138R210'");
//            ps.execute();
//            for (String str : list) {
//                PreparedStatement ps = connection.prepareStatement("select * from " + str);
//                ResultSet resultSet = ps.executeQuery();
//                ResultSetMetaData metaData = resultSet.getMetaData();
//                System.out.println("=======================" + str.toLowerCase());
//                System.out.println(metaData.getColumnCount());
//                StringBuilder sb=new StringBuilder();
//                for (int i = 1; i <= metaData.getColumnCount(); i++) {
//                    sb.append("?,");
////                    System.out.println(getType(metaData.getColumnTypeName(i)) + metaData.getColumnName(i).toLowerCase() + ";");
//                    System.out.print(metaData.getColumnName(i).toLowerCase() + ",");
//                }
//                System.out.println();
//                System.out.println(sb);
//
//            }
//            PreparedStatement  ps=connection.prepareStatement(" delete from rme_eqp where  eqp_id = '437XR1138R210'");
//            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static String getType(String from) {
//        if (from.indexOf("CHAR") > -1) {
//            return "private String ";
//        }
//        else if (from.indexOf("NUMBER") > -1) {
//            return "private Double ";
//        }
//        else if (from.indexOf("DATE") > -1) {
//            return "private Date ";
//        }
//        return "";
//    }

    private AlarmDBUtil1() {
    }

    private static Connection getCon() throws SQLException {
        DatasourceManager.getInstance().configure(EnvironsUtil.FILE_AGENT_DS_ORACLE);
        DataSource dataSource = DatasourceManager.getInstance().getDatasource("icm_dest");
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public static boolean checkToken(String token) {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            ps = connection.prepareStatement("select NFVO_EXPIRYTIME from NFVO_TOKEN_INFO where NFVO_TOKEN=?");
            ps.setObject(1, token);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("NFVO_EXPIRYTIME");
                if (timestamp.getTime() > System.currentTimeMillis()) {
                    flag = true;
                    return flag;
                    // preparedStatement = connection.prepareStatement("update NFVO_TOKEN_INFO set NFVO_EXPIRYTIME =
                    // now()");
                    // preparedStatement.execute();
                }
            }
        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.checkToken", e);
            e.printStackTrace();
        }
        finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return flag;
    }

    public static String getIpPort(String vimId) {
        String ipPort = null;
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            pst = connection.prepareStatement("select vim_api from nfvo_vim_info where vim_id=?");
            pst.setObject(1, vimId);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                ipPort = resultSet.getString(1);
            }
        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.getVimToken", e);
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ipPort;
    }

    public static String getVimToken(String vimId) {
        String token = null;
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            pst = connection.prepareStatement("select vim_token from nfvo_vim_info where vim_id=?");
            pst.setObject(1, vimId);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                token = resultSet.getString(1);
            }
        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.getVimToken", e);
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return token;
    }

    private static Map<String, String> map = new ConcurrentHashMap<>();

    private static Map<String, String> getUrl() {
        map.clear();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            pst = connection.prepareStatement("select key_en_name,key_value from nfvo_access_url_info");
            resultSet = pst.executeQuery();
            while (resultSet.next()) {
                map.put(resultSet.getString(1), resultSet.getString(2));
            }
        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.getUrl", e);
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static volatile long preTime = System.currentTimeMillis();

    public static String getUrl(String key) {
        if (System.currentTimeMillis() - preTime > 100000L) {
            getUrl();
        }
        else if (map.isEmpty()) {
            getUrl();
        }
        preTime = System.currentTimeMillis();
        return map.get(key);
    }

    public static Map<String, String> getAuth(String vimId) {
        Map<String, String> map = new HashMap<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            pst = connection.prepareStatement("select params,AUTH_URL from nfvo_vim_auth_info where vid=?");
            pst.setObject(1, vimId);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                map.put("auth", resultSet.getString("params"));
                map.put("url", resultSet.getString("AUTH_URL"));
            }
        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.getAuth", e);
            e.printStackTrace();
        }
        finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    @SuppressWarnings("resource")
    public static long getAndUpdateMsgseq(long msgSeq, String vimId, Date date, String type) {
        long result = msgSeq;
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            pst = connection.prepareStatement("select msgSeq from nfvo_msg_seq where vimId=? and nfvoId=?");
            pst.setObject(1, vimId);
            pst.setObject(2, ConfigUtil.nfvoId);
            resultSet = pst.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getLong(1);
                pst = connection.prepareStatement(
                    "update nfvo_msg_seq set msgSeq = ?,eventTime=?,type=? where vimId =? and nfvoId=?");
                pst.setObject(1, msgSeq);
                pst.setObject(2, new Timestamp(date.getTime()));
                pst.setObject(3, type);
                pst.setObject(4, vimId);
                pst.setObject(5, ConfigUtil.nfvoId);
                pst.execute();
            }
            else {
                pst = connection.prepareStatement("insert into  nfvo_msg_seq values (?,?,?,?,?)");
                pst.setObject(1, vimId);
                pst.setObject(2, msgSeq);
                pst.setObject(3, new Timestamp(date.getTime()));
                pst.setObject(4, type);
                pst.setObject(5, ConfigUtil.nfvoId);
                pst.execute();
            }

        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.getAuth", e);
            e.printStackTrace();
        }
        finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static List<AlarmExpire> getAlarmExpire() {
        List<AlarmExpire> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        try {
            connection = getCon();
            pst = connection.prepareStatement("select vimId,type,eventTime from nfvo_msg_seq where nfvoId=?");
            pst.setObject(1, ConfigUtil.nfvoId);
            resultSet = pst.executeQuery();
            while (resultSet.next()) {
                AlarmExpire expire = new AlarmExpire();
                expire.setVimId(resultSet.getString("vimId"));
                expire.setType(resultSet.getString("type"));
                expire.setEventTime(resultSet.getTimestamp("eventTime"));
                list.add(expire);
            }

        }
        catch (SQLException e) {
            logger.error("AlarmDBUtil.getAuth", e);
            e.printStackTrace();
        }
        finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
