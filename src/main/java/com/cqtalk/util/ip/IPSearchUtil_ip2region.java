package com.cqtalk.util.ip;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 通过ip地址查找属地 第一种方案
@Component
public class IPSearchUtil_ip2region {

    private static Searcher searcher;

    private static final Logger logger = LoggerFactory.getLogger(IPSearchUtil_ip2region.class);

    // 域名
    /*@Value("${community.path.ipDBPath}")
    private String ipDBPath;*/

    // TODO:
    private String ipDBPath = "D:/Program_Files/ip2region-master/data/ip2region.xdb";

    public void preLoad() {
        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(ipDBPath);
        } catch (Exception e) {
            logger.error("failed to load content from" + ipDBPath + ": " + e + "\n");
            return;
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            logger.error("failed to create content cached searcher: " + e + "\n");
            return;
        }
    }

    public static String[] search(String ip) {
        IPSearchUtil_ip2region ipre = new IPSearchUtil_ip2region();
        ipre.preLoad();
        // 3、查询
        String[] strs = null;
        try {
            // long sTime = System.nanoTime();
            String region = searcher.search(ip);
            strs = region.split("\\|");
            // System.out.println("____________");
            // long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            // System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            logger.error("failed to search(" + ip + "): " + e + "\n");
        }

        // 备注：并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。

        return strs;
    }

    // 返回用户所在省份
    public String findProvince(String ip) {
        // IPSearchUtil_ip2region ip = new IPSearchUtil_ip2region();
        String[] res = search(ip);
        if(res[2] == null) {
            return "0";
        }
        return res[2];
    }

    // 返回用户所在城市
    public String findCity(String ip) {
        // IPSearchUtil_ip2region ip = new IPSearchUtil_ip2region();
        String[] res = search(ip);
        if(res[3] == null) {
            return "0";
        }
        return res[3];
    }

}
