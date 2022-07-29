package com.example.demo.trainprice;


import java.math.BigDecimal;

public class CalucateTicketPrice {

    // static double[] rate = new double[]{0.05861, 0.05275, 0.04689, 0.04102, 0.03517, 0.02931};
    //static BigDecimal[] rate = new BigDecimal[]{new BigDecimal("0.05861"), new BigDecimal("0.05275"), new BigDecimal("0.04689"), new BigDecimal("0.04102"), new BigDecimal("0.03517"), new BigDecimal("0.02931")};
    //static double[] rate = new double[]{0.05861, 0.052749, 0.046888, 0.041027, 0.035166, 0.029305};

    static BigDecimal[] rate = new BigDecimal[]{new BigDecimal("0.05861"), new BigDecimal("0.052749"), new BigDecimal("0.046888"), new BigDecimal("0.041027"), new BigDecimal("0.035166"), new BigDecimal("0.029305")};


    public static void main(String[] args) {

        /**
         * 根据里程获取卧铺价格
         */
        totalPrice("K468", com.login.price.TicketPriceMillsPeriod.caculateMills(1204), SeatTypeEnum.YINGWOXIA);

        /**
         * 根据硬座价格获取卧铺价格
         */
        totalPrice("K468", SeatTypeEnum.YINGWOXIA, new BigDecimal("152.5"));
    }


    private static BigDecimal totalPrice(String trainNo, SeatTypeEnum seatTypeEnum, BigDecimal yingZuoPrice) {

        boolean isJiaKuai = false;
        if (trainNo.startsWith("K") || trainNo.startsWith("T") || trainNo.startsWith("Z")) {
            isJiaKuai = true;
        }
        // 1.基本票价
        // (硬座价格 - 软票费 - 候车室空调费)/1.5=基本票价 + 基本票价 * 0.2(0.4) + 基本票价 * 0.25 = 基本票价 * (1+0.2(0.4)+0.25)
        // 基本票价 = (硬座价格 - 软票费 - 候车室空调费)/1.5/(1+0.2(0.4)+0.25)
        BigDecimal basicTicketPrice = yingZuoPrice.subtract(new BigDecimal("1")).subtract(new BigDecimal("1")).divide(new BigDecimal("1.5"), 2, BigDecimal.ROUND_UP).divide(isJiaKuai ? new BigDecimal("1.65") : new BigDecimal("1.45"), 2, BigDecimal.ROUND_UP);
        //basicTicketPrice = basicTicketPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("基本票价 = " + basicTicketPrice);

        BigDecimal insurePrice = basicTicketPrice.multiply(new BigDecimal("0.02")).setScale(1, BigDecimal.ROUND_UP);
        System.out.println("保险费 = " + insurePrice);


        // 2.加快票价
        /**
         * 如果是普快列车，加快票价 = 基本票价 * 20%
         * 如果是K(快速列车)、T(特快列车)、Z(直达特快列车)，加快票价 = 基本票价 * 40%
         */
        BigDecimal jiakuai = basicTicketPrice.multiply(new BigDecimal("0.2"));
        if (isJiaKuai) {
            jiakuai = basicTicketPrice.multiply(new BigDecimal("0.4"));
        }
        jiakuai = jiakuai.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("加快票价 = " + jiakuai);


        // 3.空调票价
        /**
         * 有空调的火车，空调票价 = 基本票价 * 25%
         */
        boolean hasKongtiao = true;
        BigDecimal kongtiaoPrice = BigDecimal.ZERO;
        if (hasKongtiao) {
            kongtiaoPrice = basicTicketPrice.multiply(new BigDecimal("0.25"));
        }
        kongtiaoPrice = kongtiaoPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("空调票价 = " + kongtiaoPrice);

        // 4.卧铺票价
        /**
         * 硬卧上卧铺价格 = 基本票价 * 110%
         * 硬卧中卧铺价格 = 基本票价 * 120%
         * 硬卧下卧铺价格 = 基本票价 * 130%
         * 软卧上卧铺价格 = 基本票价 * 175%
         * 软卧下卧铺价格 = 基本票价 * 195%
         */
        BigDecimal wopuPrice = new BigDecimal("0");
        if (isSleepingBerth(seatTypeEnum)) {
            wopuPrice = wopuPrice(basicTicketPrice, seatTypeEnum);
        }
        wopuPrice = wopuPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println(seatTypeEnum.desc + "价格 = " + wopuPrice);

        // 5.新型空调列车票价
        /**
         * 新型空调列车票价 = （基本票价 + 加快票价 + 空调票价 + 卧铺票价） * 50%
         */

        BigDecimal tempBasicTicketPrice = basicTicketPrice;
        if (seatTypeEnum == SeatTypeEnum.RUANWOXIA || seatTypeEnum == SeatTypeEnum.RUANWOSHANG) {
            tempBasicTicketPrice = tempBasicTicketPrice.multiply(new BigDecimal("2"));
        }
        tempBasicTicketPrice = tempBasicTicketPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("变更后基本票价 = " + tempBasicTicketPrice);
        BigDecimal newTypePrice = (tempBasicTicketPrice.add(jiakuai).add(kongtiaoPrice).add(wopuPrice))
                .multiply(new BigDecimal("0.5")).setScale(1, BigDecimal.ROUND_HALF_UP);

        System.out.println("新型空调列车票价 = " + newTypePrice);

        // 6.软票费
        /**
         * 凡是电脑打印的车票，要付1元软票费
         */
        BigDecimal ruanPiaoPrice = new BigDecimal("1");
        System.out.println("软票费 = " + ruanPiaoPrice);
        // 7.卧铺订票费
        /**
         * 卧铺票要加收订票费10元
         */
        BigDecimal orderTicketPrice = new BigDecimal("0");
        if (isSleepingBerth(seatTypeEnum)) {
            orderTicketPrice = new BigDecimal("10");
        }
        System.out.println("卧铺订票费 = " + orderTicketPrice);

        // 8.候车室空调费
        /**
         * 里程超过200公里的长途旅客列车，收候车室空调费1元
         */
        BigDecimal hcktf = new BigDecimal("1");
        System.out.println("候车室空调费 = " + hcktf);


        BigDecimal totalPrice = tempBasicTicketPrice.add(jiakuai).add(kongtiaoPrice)
                .add(wopuPrice).add(newTypePrice).add(ruanPiaoPrice).add(orderTicketPrice)
                .add(hcktf).setScale(1, BigDecimal.ROUND_HALF_UP);


        System.out.println(seatTypeEnum.desc + "总价((变更后基本票价 + 加快票价 + 空调票价 + " + seatTypeEnum.desc + "票价) * 1.5 + 软票费 + 卧铺订票费 + 候车室空调费) = " + totalPrice);

        return totalPrice;
    }


    /**
     * 是否是卧铺
     *
     * @param seatTypeEnum
     * @return
     */
    private static boolean isSleepingBerth(SeatTypeEnum seatTypeEnum) {
        return seatTypeEnum == SeatTypeEnum.YINGWOSHANG || seatTypeEnum == SeatTypeEnum.YINGWOZHONG || seatTypeEnum == SeatTypeEnum.YINGWOXIA
                || seatTypeEnum == SeatTypeEnum.RUANWOSHANG || seatTypeEnum == SeatTypeEnum.RUANWOXIA;
    }

    private static BigDecimal totalPrice(String trainNo, long distance, SeatTypeEnum seatTypeEnum) {
        // 1.基本票价
        BigDecimal basicTicketPrice = basicTicketPirce(distance);
        //basicTicketPrice = basicTicketPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("基本票价 = " + basicTicketPrice);

        BigDecimal insurePrice = basicTicketPrice.multiply(new BigDecimal("0.02")).setScale(1, BigDecimal.ROUND_UP);
        System.out.println("保险费 = " + insurePrice);


        // 2.加快票价
        /**
         * 如果是普快列车，加快票价 = 基本票价 * 20%
         * 如果是K(快速列车)、T(特快列车)、Z(直达特快列车)，加快票价 = 基本票价 * 40%
         */
        BigDecimal jiakuai = basicTicketPrice.multiply(new BigDecimal("0.2"));
        if (trainNo.startsWith("K") || trainNo.startsWith("T") || trainNo.startsWith("Z")) {
            jiakuai = basicTicketPrice.multiply(new BigDecimal("0.4"));
        }
        jiakuai = jiakuai.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("加快票价 = " + jiakuai);


        // 3.空调票价
        /**
         * 有空调的火车，空调票价 = 基本票价 * 25%
         */
        boolean hasKongtiao = true;
        BigDecimal kongtiaoPrice = BigDecimal.ZERO;
        if (hasKongtiao) {
            kongtiaoPrice = basicTicketPrice.multiply(new BigDecimal("0.25"));
        }
        kongtiaoPrice = kongtiaoPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("空调票价 = " + kongtiaoPrice);

        // 4.卧铺票价
        /**
         * 硬卧上卧铺价格 = 基本票价 * 110%
         * 硬卧中卧铺价格 = 基本票价 * 120%
         * 硬卧下卧铺价格 = 基本票价 * 130%
         * 软卧上卧铺价格 = 基本票价 * 175%
         * 软卧下卧铺价格 = 基本票价 * 195%
         */
        BigDecimal wopuPrice = new BigDecimal("0");
        if (isSleepingBerth(seatTypeEnum)) {
            wopuPrice = wopuPrice(basicTicketPrice, seatTypeEnum);
        }
        wopuPrice = wopuPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println(seatTypeEnum.desc + "价格 = " + wopuPrice);

        // 5.新型空调列车票价
        /**
         * 新型空调列车票价 = （基本票价 + 加快票价 + 空调票价 + 卧铺票价） * 50%
         */

        BigDecimal tempBasicTicketPrice = basicTicketPrice;
        if (seatTypeEnum == SeatTypeEnum.RUANWOXIA || seatTypeEnum == SeatTypeEnum.RUANWOSHANG) {
            tempBasicTicketPrice = tempBasicTicketPrice.multiply(new BigDecimal("2"));
        }
        tempBasicTicketPrice = tempBasicTicketPrice.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println("变更后基本票价 = " + tempBasicTicketPrice);
        BigDecimal newTypePrice = (tempBasicTicketPrice.add(jiakuai).add(kongtiaoPrice).add(wopuPrice))
                .multiply(new BigDecimal("0.5")).setScale(1, BigDecimal.ROUND_HALF_UP);

        System.out.println("新型空调列车票价 = " + newTypePrice);

        // 6.软票费
        /**
         * 凡是电脑打印的车票，要付1元软票费
         */
        BigDecimal ruanPiaoPrice = new BigDecimal("1");
        System.out.println("软票费 = " + ruanPiaoPrice);
        // 7.卧铺订票费
        /**
         * 卧铺票要加收订票费10元
         */
        BigDecimal orderTicketPrice = new BigDecimal("0");
        if (isSleepingBerth(seatTypeEnum)) {
            orderTicketPrice = new BigDecimal("10");
        }
        System.out.println("卧铺订票费 = " + orderTicketPrice);

        // 8.候车室空调费
        /**
         * 里程超过200公里的长途旅客列车，收候车室空调费1元
         */
        BigDecimal hcktf = (distance - 200) > 0 ? new BigDecimal("1") : BigDecimal.ZERO;
        System.out.println("候车室空调费 = " + hcktf);


        BigDecimal totalPrice = tempBasicTicketPrice.add(jiakuai).add(kongtiaoPrice)
                .add(wopuPrice).add(newTypePrice).add(ruanPiaoPrice).add(orderTicketPrice)
                .add(hcktf).setScale(1, BigDecimal.ROUND_HALF_UP);


        System.out.println(seatTypeEnum.desc + "总价((变更后基本票价 + 加快票价 + 空调票价 + " + seatTypeEnum.desc + "票价) * 1.5 + 软票费 + 卧铺订票费 + 候车室空调费) = " + totalPrice);

        return totalPrice;
    }

    /*private static BigDecimal basicTicketPirce(long distance) {
        if (distance - 200 <= 0) {
            return BigDecimal.valueOf(distance).multiply(rate[0]);
        } else if (distance - 500 <= 0) {
            return BigDecimal.valueOf(200).multiply(rate[0]).add(new BigDecimal((distance - 200)).multiply(rate[1]));
        } else if (distance - 1000 <= 0) {
            return BigDecimal.valueOf(200 * rate[0]).add(new BigDecimal(300 * rate[1])).add(BigDecimal.valueOf((distance - 500) * rate[2]));
        } else if (distance - 1500 <= 0) {
            return BigDecimal.valueOf(200 * rate[0]).add(new BigDecimal(300 * rate[1])).add(BigDecimal.valueOf(500 * rate[2])).add(BigDecimal.valueOf((distance - 1000) * rate[3]));
        } else if (distance - 2500 <= 0) {
            return BigDecimal.valueOf(200 * rate[0]).add(new BigDecimal(300 * rate[1])).add(BigDecimal.valueOf(500 * rate[2])).add(BigDecimal.valueOf(500 * rate[3])).add(BigDecimal.valueOf((distance - 1500) * rate[4]));
        } else {
            return BigDecimal.valueOf(200 * rate[0]).add(new BigDecimal(300 * rate[1])).add(BigDecimal.valueOf(500 * rate[2])).add(BigDecimal.valueOf(500 * rate[3])).add(BigDecimal.valueOf(1000 * rate[4])).add(BigDecimal.valueOf((distance - 2500) * rate[5]));
        }
    }*/


    private static BigDecimal basicTicketPirce(long distance) {
        if (distance - 200 <= 0) {
            return BigDecimal.valueOf(distance).multiply(rate[0]).setScale(6, BigDecimal.ROUND_HALF_UP);
        } else if (distance - 500 <= 0) {
            return new BigDecimal("200").multiply(rate[0]).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal((distance - 200)).multiply(rate[1]).setScale(6, BigDecimal.ROUND_HALF_UP));
        } else if (distance - 1000 <= 0) {
            return new BigDecimal("200").multiply(rate[0]).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("300").multiply(rate[1])).setScale(6, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(distance - 500).multiply(rate[2]).setScale(6, BigDecimal.ROUND_HALF_UP));
        } else if (distance - 1500 <= 0) {
            return new BigDecimal("200").multiply(rate[0]).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("300").multiply(rate[1])).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("500").multiply(rate[2])).setScale(6, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(distance - 1000).multiply(rate[3]).setScale(6, BigDecimal.ROUND_HALF_UP));
        } else if (distance - 2500 <= 0) {
            return new BigDecimal("200").multiply(rate[0]).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("300").multiply(rate[1])).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("500").multiply(rate[2])).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("500").multiply(rate[3])).setScale(6, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(distance - 1500).multiply(rate[4]).setScale(6, BigDecimal.ROUND_HALF_UP));
        } else {
            return new BigDecimal("200").multiply(rate[0]).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("300").multiply(rate[1])).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("500").multiply(rate[2])).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("500").multiply(rate[3])).setScale(6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("1000").multiply(rate[4])).setScale(6, BigDecimal.ROUND_HALF_UP).add(BigDecimal.valueOf(distance - 2500).multiply(rate[5]).setScale(6, BigDecimal.ROUND_HALF_UP));
        }
    }

    private static BigDecimal wopuPrice(BigDecimal basicTicketPrice, SeatTypeEnum seatTypeEnum) {
        switch (seatTypeEnum) {
            case YINGWOSHANG:
                return basicTicketPrice.multiply(new BigDecimal("1.10"));

            case YINGWOZHONG:
                return basicTicketPrice.multiply(new BigDecimal("1.20"));

            case YINGWOXIA:
                return basicTicketPrice.multiply(new BigDecimal("1.30"));

            case RUANWOSHANG:
                return basicTicketPrice.multiply(new BigDecimal("1.75"));

            case RUANWOXIA:
                return basicTicketPrice.multiply(new BigDecimal("1.95"));
            case GAOJIRUANWOSHANG:
                return basicTicketPrice.multiply(new BigDecimal("2.10"));
            case GAOJIRUANWOXIA:
                return basicTicketPrice.multiply(new BigDecimal("2.30"));
        }
        return basicTicketPrice;
    }

    enum SeatTypeEnum {
        YINGWOSHANG(0, "硬卧上"),
        YINGWOZHONG(2, "硬卧中"),
        YINGWOXIA(3, "硬卧下"),
        RUANWOSHANG(4, "软卧上"),
        RUANWOXIA(5, "软卧下"),
        YINGZUO(6, "硬座"),
        RUANZUO(7, "软座"),
        YIDENGZUO(8, "一等座"),
        ERDENGZUO(9, "二等座"),
        GAOJIRUANWOSHANG(10, "高级软卧上"),
        GAOJIRUANWOXIA(11, "高级软卧下");
        private int type;
        private String desc;

        private SeatTypeEnum(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

    }
}
