package com.example.demo.trainprice.utils.h12306.core;

import com.example.demo.trainprice.utils.h12306.pojo.NoticeBlock;
import com.example.demo.trainprice.utils.h12306.utils.Common;
import com.example.demo.trainprice.utils.h12306.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZZCXCenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZZCXCenter.class);

    public static int trainStateToday(int today, int ksrq, int jsrq, int yxzq, int kxgl, int yxts, boolean tkMode, NoticeBlock noticeBlock) {
        int ksrqAtThisStation = DateUtils.dayOfTomorrow(ksrq, yxts);
        int jsrqAtThisStation = DateUtils.dayOfTomorrow(jsrq, yxts);
        if (today < ksrqAtThisStation) {
            return 2;
        }
        if (today > jsrqAtThisStation) {
            return -1;
        }
        if (!tkMode) {
            return Common.RunToday(today, yxzq, kxgl, ksrq, jsrq, yxts) ? 1 : 0;
        }
        boolean isTrue = Common.RunToday(today, yxzq, kxgl, ksrq, jsrq, yxts);
        if (isTrue) {
            return 0;
        }
        if (noticeBlock != null) {
            return trainStateToday(today, noticeBlock.getKsrq(), noticeBlock.getJsrq(), noticeBlock.getKxzq(), noticeBlock.getKxgl(), noticeBlock.getDay(), false, noticeBlock);
        }
        //LogPrint.v(LogPrint.TAG, "走到这里就表示不对了,说明没命中停开规律 应该train本身的规律计算");
        LOGGER.error("走到这里就表示不对了,说明没命中停开规律 应该train本身的规律计算");
        return 1;
    }

}
