package com.example.policestation;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

    String date;
    int done_ccd;
    int pending_ccd;
    int done_summon;
    int pending_summon;

    Transaction(String date,int done_ccd,int pending_ccd,int done_summon,int pending_summon) {
        this.date = date;
        this.done_ccd=done_ccd;
        this.pending_ccd=pending_ccd;
        this.done_summon=done_summon;
        this.pending_summon=pending_summon;
    }

}
