package com.login.price;

import java.util.ArrayList;
import java.util.List;

public class TicketPriceMillsPeriod {

    private int min;

    private int max;

    /**
     * 每区段里程(km)
     */
    private int mileagePerZone;

    /**
     * 区段数
     */
    private int zone;

    public TicketPriceMillsPeriod(int min, int max, int mileagePerZone, int zone) {
        this.min = min;
        this.max = max;
        this.mileagePerZone = mileagePerZone;
        this.zone = zone;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMileagePerZone() {
        return mileagePerZone;
    }

    public void setMileagePerZone(int mileagePerZone) {
        this.mileagePerZone = mileagePerZone;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    static List<TicketPriceMillsPeriod> ticketPriceMillsPeriodList = new ArrayList<>();

    static {
         /*ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(1, 200, 10, 20));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(201, 400, 20, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(401, 700, 30, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(701, 1100, 40, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(1101, 1600, 50, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(1601, 2200, 60, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(2201, 2900, 70, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(2901, 3700, 80, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(3701, 4600, 90, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(4601, Integer.MAX_VALUE, 100, 10));*/


        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(0, 200, 10, 20));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(200, 400, 20, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(400, 700, 30, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(700, 1100, 40, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(1100, 1600, 50, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(1600, 2200, 60, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(2200, 2900, 70, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(2900, 3700, 80, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(3700, 4600, 90, 10));
        ticketPriceMillsPeriodList.add(new TicketPriceMillsPeriod(4600, Integer.MAX_VALUE, 100, 10));

    }

    public static int caculateMills(int distance) {


        TicketPriceMillsPeriod ticketPriceMillsPeriod = null;
        for (TicketPriceMillsPeriod ticketPriceMillsPeriod1 : ticketPriceMillsPeriodList) {
            if (ticketPriceMillsPeriod1.getMin() < distance && ticketPriceMillsPeriod1.getMax() >= distance) {
                ticketPriceMillsPeriod = ticketPriceMillsPeriod1;
                break;
            }
        }

        for (int i = 0; i < ticketPriceMillsPeriod.getZone() - 1; i++) {
            int tmp1 = ticketPriceMillsPeriod.getMin() + ticketPriceMillsPeriod.getMileagePerZone() * i;
            int tmp2 = ticketPriceMillsPeriod.getMin() + ticketPriceMillsPeriod.getMileagePerZone() * (i + 1);

            if (distance >= tmp1 && distance <= tmp2) {
                return (tmp1 + tmp2) / 2;
            }
        }
        return distance;
    }
}
