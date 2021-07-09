//package com.aio.portable.swiss.middleware.canal;
//
//public enum EntryType {
//    TRANSACTIONBEGIN(0, 1),
//    ROWDATA(1, 2),
//    TRANSACTIONEND(2, 3),
//    HEARTBEAT(3, 4),
//    GTIDLOG(4, 5);
//
//    private final int index;
//    private final int value;
//
//    EntryType(int index, int value) {
//        this.index = index;
//        this.value = value;
//    }
//
//    public static EntryType valueOf(int value) {
//        switch(value) {
//            case 1:
//                return TRANSACTIONBEGIN;
//            case 2:
//                return ROWDATA;
//            case 3:
//                return TRANSACTIONEND;
//            case 4:
//                return HEARTBEAT;
//            case 5:
//                return GTIDLOG;
//            default:
//                return null;
//        }
//    }
//}
