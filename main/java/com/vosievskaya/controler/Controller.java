package com.vosievskaya.controler;

import com.vosievskaya.Request;
import com.vosievskaya.ViewModel;

public interface Controller {

    ViewModel process(Request req);
}
